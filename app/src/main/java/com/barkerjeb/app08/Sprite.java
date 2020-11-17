package com.barkerjeb.app08;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

class Sprite extends RectF {
    private int dX, dY, color;
    public Sprite() {
        this(0,0, Color.RED);
    }
    public Sprite(int dX, int dY, int color) {
        this(0,0,10,10,dX,dY,color);
    }

    public Sprite(float left, float top, float right, float bottom) {
        this(left, top, right, bottom,0,0,Color.BLUE);
    }

    public Sprite(float left, float top, float right, float bottom, int dX, int dY, int color) {
        super(left, top, right, bottom);
        this.dX = dX;
        this.dY = dY;
        this.color = color;
    }

    public void update(){
        offset(dX,dY);//moves dX to the right and dY downwards
    }
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(left, top, right, bottom, paint);
    }

    public void draw(Canvas canvas, Bitmap bm){
        Paint paint = new Paint();
        canvas.drawBitmap(Bitmap.createScaledBitmap(bm, (int)(right-left), (int)(bottom-top), false), left, top, paint);
    }

    public int getdX() {
        return dX;
    }

    public void setdX(int dX) {
        this.dX = dX;
    }

    public int getdY() {
        return dY;
    }

    public void setdY(int dY) {
        this.dY = dY;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
