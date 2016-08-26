package com.example.kaique.portalhunters.actors;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import game.Singleton;

/**
 * Created by kaique on 23/08/16.
 */
public class ZoomOutImage {

    private Bitmap image;

    private int x;
    private int y;

    private int width;
    private int height;

    public float imageScale;
    private float zoomRatio;

    private Rect src;
    private Rect dst;

    public ZoomOutImage(Bitmap _image, float InitialRatio, float _imageScale){
        image = _image;

        imageScale = _imageScale;
        setWidth(image.getWidth());
        setHeight(image.getHeight());

        setZoomRatio(InitialRatio);

        src = new Rect();
        dst = new Rect();
    }

    public void update(){
        //Log.d(" - ZOOM - ", String.valueOf(zoomRatio));
        src.top  = 0;
        src.left = 0;
        src.bottom = src.top + getHeight();
        src.right = src.left + getWidth();

        dst.top    = getY();
        dst.left   = getX() - (int)(imageScale * zoomRatio * getWidth()) ; // Centralize
        dst.right  = dst.left +
                (int)(getWidth() * imageScale * Singleton.RATIO * zoomRatio);
        dst.bottom = dst.top +
                (int)(getHeight() * imageScale * Singleton.RATIO * zoomRatio);

        if(zoomRatio>1)
            zoomRatio-=0.1;
    }

    public void render(Canvas canvas){
        //Log.d(" - ZOOM - ", "RENDER!");
        canvas.drawBitmap(image, src, dst, null);
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

    public float getZoomRatio() {
        return zoomRatio;
    }

    public void setZoomRatio(float zoomM) {
        this.zoomRatio = zoomM;
    }
}
