package com.example.kaique.portalhunters;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.kaique.portalhunters.actors.SimpleFlipBook;
import com.example.kaique.portalhunters.actors.SimpleImage;
import com.example.kaique.portalhunters.actors.ZoomOutImage;

import java.util.Random;

import game.Singleton;

/**
 * Created by kaique on 23/08/16.
 */
public class BattleView extends View {

    public boolean active;
    private Paint paint;
    private Context ctx;

    private Bitmap bg;

    private ZoomOutImage BattleTitle;

    private SimpleImage battleModeElements;
    private SimpleImage battleModeMagics;
    private SimpleImage battleModeEquips;

    private SimpleImage chartElements;
    private SimpleImage chartMagic;
    private SimpleImage chartEquip;

    private SimpleFlipBook hitFX;

    private Rect rectBgSrc;
    private Rect rectBgDst;

    private AssetManager assetManager;

    private RefreshThread thread;
    private SoundPool soundPool;

    private int SOUND_BG;

    private MediaPlayer player;

    private boolean MusicPlaying = false;
    private int MusicReadyToPlay = 0;


    private float adjust;
    private int count;


    public BattleView(Context context) {
        super(context);

        active = true;
        Singleton.BATTLE_MODE = Singleton.BATTLE_NONE;

        paint = new Paint();
        adjust = 1;
        count = 0;
        ctx = context;

        // TODO: Add Sounds

        assetManager = context.getAssets();

        try{
            bg = BitmapFactory.decodeStream(assetManager.open("bg_ver.jpg"));

            BattleTitle = new ZoomOutImage(BitmapFactory.decodeStream(assetManager.open("battle.png")), 4, 0.25f);

            battleModeElements = new SimpleImage(BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.ic_magic_power));
            battleModeMagics = new SimpleImage(BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.ic_magic_battle));
            battleModeEquips = new SimpleImage(BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.ic_sword_axe));

            chartElements = new SimpleImage(BitmapFactory.decodeStream(assetManager.open("bat_el.png")));
            chartMagic = new SimpleImage(BitmapFactory.decodeStream(assetManager.open("bat_mg.png")));
            chartEquip = new SimpleImage(BitmapFactory.decodeStream(assetManager.open("bat_eq.png")));

            hitFX = new SimpleFlipBook(BitmapFactory.decodeStream(assetManager.open("hit128.png")), 4,1);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        rectBgSrc = new Rect();
        rectBgDst = new Rect();

        // Organize screen itens
        BattleTitle.setX(Singleton.SCREEN_WIDTH/2);

        battleModeElements.setX((Singleton.SCREEN_WIDTH/4)-(battleModeMagics.getWidth()/2));
        battleModeMagics.setX((Singleton.SCREEN_WIDTH/2)-(battleModeMagics.getWidth()/2));
        battleModeEquips.setX((3*Singleton.SCREEN_WIDTH/4)-(battleModeMagics.getWidth()/2));

        battleModeElements.setY(-128);
        battleModeMagics.setY(  -256);
        battleModeEquips.setY(  -512);

        chartElements.setX((Singleton.SCREEN_WIDTH/2)-(chartElements.getWidth()/2));
        chartElements.setY((Singleton.SCREEN_HEIGHT/2)-(chartElements.getHeight()/2));
        chartMagic.setX((Singleton.SCREEN_WIDTH/2)-(chartMagic.getWidth()/2));
        chartMagic.setY((Singleton.SCREEN_HEIGHT/2)-(chartMagic.getHeight()/2));
        chartEquip.setX((Singleton.SCREEN_WIDTH/2)-(chartEquip.getWidth()/2));
        chartEquip.setY((Singleton.SCREEN_HEIGHT/2)-(chartEquip.getHeight()/2));

        hitFX.active = false;

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(16);

        thread = new RefreshThread();
        thread.start();
    }

    public void onDraw(Canvas canvas){

        // Background
        rectBgSrc.top = 0;
        rectBgSrc.left = 0;
        rectBgSrc.right = bg.getWidth();
        rectBgSrc.bottom = bg.getHeight();

        rectBgDst.top = 0;
        rectBgDst.left = 0;
        rectBgDst.right = Singleton.SCREEN_WIDTH;
        rectBgDst.bottom = Singleton.SCREEN_HEIGHT;

        // Draw
        canvas.drawBitmap(bg, rectBgSrc, rectBgDst, null);
        BattleTitle.render(canvas);
        battleModeElements.render(canvas);
        battleModeMagics.render(canvas);
        battleModeEquips.render(canvas);
        hitFX.render(canvas);

        // Draw Line Bellow Selected Battle Mode
        if(Singleton.BATTLE_MODE == Singleton.BATTLE_MODE_ELEMENTS){
            canvas.drawLine(
                    battleModeElements.getX(),
                    battleModeElements.getY() + battleModeElements.getHeight(),
                    battleModeElements.getX() + battleModeElements.getWidth(),
                    battleModeElements.getY() + battleModeElements.getHeight(),
                    paint);
            chartElements.render(canvas);
        }
        else if(Singleton.BATTLE_MODE == Singleton.BATTLE_MODE_MAGIC){
            canvas.drawLine(
                    battleModeMagics.getX(),
                    battleModeMagics.getY() + battleModeMagics.getHeight(),
                    battleModeMagics.getX() + battleModeMagics.getWidth(),
                    battleModeMagics.getY() + battleModeMagics.getHeight(),
                    paint);
            chartMagic.render(canvas);
        }
        else if(Singleton.BATTLE_MODE == Singleton.BATTLE_MODE_EQUIP){
            canvas.drawLine(
                    battleModeEquips.getX(),
                    battleModeEquips.getY() + battleModeEquips.getHeight(),
                    battleModeEquips.getX() + battleModeEquips.getWidth(),
                    battleModeEquips.getY() + battleModeEquips.getHeight(),
                    paint);
            chartEquip.render(canvas);
        }
    }

    public void update(){
        BattleTitle.update();

        if(BattleTitle.getZoomRatio()<=1){
            if(adjust>=0){
                battleModeElements.setY((int)(battleModeElements.getY()*adjust + (BattleTitle.getY() + (BattleTitle.getHeight()*BattleTitle.imageScale) + 32)*(1-adjust)));
                battleModeMagics.setY(  (int)(battleModeMagics.getY()*adjust + (BattleTitle.getY() + (BattleTitle.getHeight()*BattleTitle.imageScale) + 32)*(1-adjust)));
                battleModeEquips.setY(  (int)(battleModeEquips.getY()*adjust + (BattleTitle.getY() + (BattleTitle.getHeight()*BattleTitle.imageScale) + 32)*(1-adjust)));
                adjust-=0.05;
            }
            else if(Singleton.BATTLE_MODE == Singleton.BATTLE_NONE){
                Log.d("-- BATTLE --", "Finish moving battle modes...");
                hitFX.setX(battleModeElements.getX());
                hitFX.setY(battleModeElements.getY());
                hitFX.active = true;

                // Random select a battle mode
                int r = (new Random()).nextInt(3);

                if(r==0){
                    Singleton.BATTLE_MODE = Singleton.BATTLE_MODE_ELEMENTS;
                }
                else if(r==1){
                    Singleton.BATTLE_MODE = Singleton.BATTLE_MODE_MAGIC;
                }
                else{
                    Singleton.BATTLE_MODE = Singleton.BATTLE_MODE_EQUIP;
                }
            }
            else if(count<15){
                Log.d("BV", String.valueOf(count));
                count++;
            }
            else{
                AppCompatActivity act = (AppCompatActivity)ctx;
                Intent intent;
                if(Singleton.BATTLE_MODE == Singleton.BATTLE_MODE_ELEMENTS){
                    intent = new Intent(act, BattleEL.class);
                }
                else if(Singleton.BATTLE_MODE == Singleton.BATTLE_MODE_EQUIP){
                    intent = new Intent(act, BattleEQ.class);
                }
                else{
                    intent = new Intent(act, BattleMG.class);
                }

                act.startActivity(intent);
                act.finish();
            }
            battleModeElements.update();
            battleModeMagics.update();
            battleModeEquips.update();
            hitFX.update();
        }
    }

    private class RefreshThread extends Thread{

        public void run(){
            try{
                while (active){
                    BattleView.this.update();
                    BattleView.this.postInvalidate();
                    Thread.sleep(80);
                }
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

}
