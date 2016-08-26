package com.example.kaique.portalhunters.actors;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by kaique on 23/08/16.
 */
public class SimpleImage {

    private Bitmap image;
    private int x;
    private int y;
    private int width;
    private int height;

    public SimpleImage(Bitmap _image){
        image = _image;
        setWidth(image.getWidth());
        setHeight(image.getHeight());
    }

    public void update(){

    }

    public void render(Canvas canvas){
        canvas.drawBitmap(image, getX(), getY(), null);
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
}
