package com.example.kaique.portalhunters;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import game.Singleton;

/**
 * Created by kaique on 23/08/16.
 */
public class BattleActivity extends AppCompatActivity{

    BattleView battleView;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // Get screen size
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        Singleton.SCREEN_WIDTH  = metrics.widthPixels;
        Singleton.SCREEN_HEIGHT = metrics.heightPixels;

        Singleton.RATIO = (float) Singleton.SCREEN_HEIGHT / Singleton.GAME_HEIGHT;

        battleView = new BattleView(this);
        setContentView(battleView);
    }

    public void onStop(){
        super.onStop();

    }

    public void onDestroy(){
        super.onDestroy();
        Singleton.BATTLE_MODE = Singleton.BATTLE_NONE;
        battleView.active = false;
    }
}
