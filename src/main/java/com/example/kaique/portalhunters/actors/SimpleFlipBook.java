package com.example.kaique.portalhunters.actors;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import game.Singleton;

/**
 * Created by kaique on 23/08/16.
 */
public class SimpleFlipBook {

    private int x;
    private int y;
    private int width;
    private int height;
    private Bitmap img;
    private int curCol;
    private int curRow;
    private int MaxCol;
    private int MaxRow;
    private boolean loop;
    public boolean active;
    private Rect src;
    private Rect dst;

    public SimpleFlipBook(Bitmap _img, int _NumCol, int _NumRow){
        img = _img;

        MaxCol = _NumCol;
        MaxRow = _NumRow;

        setWidth(img.getWidth() / MaxCol);
        setHeight(img.getHeight() / MaxRow);

        src = new Rect();
        dst = new Rect();

        active = true;
        curCol = curRow = 0;
        loop = false;
    }

    public void update(){
        if(!active)
            return;
        src.top = curRow * getHeight();
        src.bottom = src.top + getHeight();
        src.left = curCol * getWidth();
        src.right = src.left + getWidth();

        dst.top = getY();
        dst.left = getX();
        dst.right = dst.left + (int)(getWidth() * Singleton.RATIO);
        dst.bottom= dst.top + (int)(getHeight() * Singleton.RATIO);

        curCol = (curCol + 1)%MaxCol;
        if(curCol==0) {
            curRow = (curRow + 1) % MaxRow;
            if(curRow==0 && !loop){
                active=false;
            }
        }
    }

    public void render(Canvas canvas){
        if(!active)
            return;
        canvas.drawBitmap(img, src, dst, null);
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isLoop() {
        return loop;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }
}
