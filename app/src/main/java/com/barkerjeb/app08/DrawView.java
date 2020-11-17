package com.barkerjeb.app08;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DrawView extends View {
    Paint paint=new Paint();
    int gameSpeed = 10;
    int buffer = 500;
    Sprite player;
    Sprite lB1;
    Sprite lB2;
    Sprite rB1;
    Sprite rB2;
    ArrayList<Sprite> forks = new ArrayList<Sprite>();
    Bitmap im = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.spdbike);
    Bitmap fork = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.fork);
    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        player = new Sprite(100, 0, 300, 200);
        lB1 = new Sprite(0,0-buffer, 100, getHeight()+buffer, 0, gameSpeed, Color.LTGRAY);
        lB2 = new Sprite(0, getHeight()-buffer, 100, getHeight()*2 + buffer, 0, gameSpeed, Color.LTGRAY);
        rB1 = new Sprite(getMeasuredWidth()-100,0-buffer, getMeasuredWidth(), getHeight()+buffer, 0, gameSpeed, Color.LTGRAY);
        rB2 = new Sprite(getMeasuredWidth()-100, getHeight()-buffer, getMeasuredWidth(), getHeight()*2 + buffer, 0, gameSpeed, Color.LTGRAY);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        player.update();
        player.draw(canvas, im);
        //TODO: update list of all Sprites
        lB1.update();
        lB2.update();
        update(lB1);
        update(lB2);
        lB1.draw(canvas);
        lB2.draw(canvas);
        rB1.update();
        rB2.update();
        update(rB1);
        update(rB2);
        rB1.draw(canvas);
        rB2.draw(canvas);
        //add enemy x% of frames
        if((int)(Math.random()*100) == 1){
            int x = (int)(Math.random()*(getWidth()-364))+100;
            forks.add(new Sprite(x, -250, x+164, 0, 0, gameSpeed, Color.BLUE));
        }
        for(int x = 0; x < forks.size(); x++){
            forks.get(x).update();
            if(forks.get(x).top > getHeight()){
                forks.remove(x);
            }
            else {
                forks.get(x).draw(canvas, fork);
            }
        }

        invalidate();
    }

    public boolean update(Sprite s){
        if(s.top > getHeight()){
            s.top= -buffer-getHeight();
            s.bottom=buffer;
            s.setdY(gameSpeed);
            System.out.println(s);
            return true;
        }
        return false;
    }
}
