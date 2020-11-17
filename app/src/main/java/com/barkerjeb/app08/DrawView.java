package com.barkerjeb.app08;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.sql.Time;
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
    int dodges = 0;
    int time = (int)System.nanoTime();
    boolean finished = false;
    ArrayList<Sprite> forks = new ArrayList<Sprite>();
    Bitmap im = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.spdbike);
    Bitmap fork = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.fork);
    Bitmap pillar = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.pillar);
    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        player = new Sprite(getWidth()/2-100, getHeight()-300, getWidth()/2+100, getHeight()-100);
        lB1 = new Sprite(0,0, 125, getHeight(), 0, 0, Color.LTGRAY);
        rB1 = new Sprite(getMeasuredWidth()-125,0, getMeasuredWidth(), getHeight(), 0, 0, Color.LTGRAY);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        player.update();
        player.draw(canvas, im);
        //TODO: update list of all Sprites
        lB1.draw(canvas, pillar);
        rB1.draw(canvas, pillar);
        //add enemy x% of frames
        if((int)(Math.random()*97) == 1 && !finished){
            int x = (int)(Math.random()*(getWidth()-240))+100;
            forks.add(new Sprite(x, -250, x+40, 0, 0, gameSpeed, Color.BLUE));
        }
        for(int x = 0; x < forks.size(); x++){
            forks.get(x).update();
            if(forks.get(x).top > getHeight()){
                forks.remove(x);
                dodges++;
            }
            else {
                if(player.intersect(forks.get(x))){
                    forks.get(x).top+=100;
                    forks.get(x).bottom+=100;
                    finished = true;
                    forks.get(x).draw(canvas, fork);
                    gameOver(canvas);
                }
                else {
                    forks.get(x).draw(canvas, fork);
                }
            }
        }
        if((int)(Math.random()*400) == 1 && !finished){
            gameSpeed *= 1.25;
        }
        if(!finished) {
            invalidate();
        }
    }

    public boolean onTouchEvent(MotionEvent e)
    {
        int x=(int)e.getX();
        if(x<getWidth()-200 && x>200) {
            player.left = x-100;
            player.right = x + 100;
        }
        if(finished && e.getAction() == MotionEvent.ACTION_DOWN){
            finished = false;
            dodges=0;
            gameSpeed=10;
            for(int i = 0; i < forks.size(); i++){
                forks.get(i).top=10000;
                forks.get(i).bottom=11000;
                forks.remove(i);
                i--;
            }
            player.left=getWidth()/2-100;
            player.top = getHeight()-300;
            player.right=getWidth()/2+100;
            player.bottom=getHeight()-100;
            invalidate();

        }

        switch (e.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                Log.d("DEBUG", "On touch (down)" + String.valueOf(x));
            case MotionEvent.ACTION_UP:
                Log.d("DEBUG", "On touch (up)" + String.valueOf(x));
            case MotionEvent.ACTION_MOVE:
                Log.d("DEBUG", "On touch (move)" + String.valueOf(x));
                break;
        }
        return true;

    }

    private void gameOver(Canvas canvas) {
        //TODO: GSON/JSON Leaderboard.
        Paint p = new Paint();
        p.setTextSize(50);
        p.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("The Flying Spaghetti Monster was eaten :(", getWidth()/2, getHeight()/2, p);
        p.setColor(Color.MAGENTA);
        p.setTextSize(69);
        canvas.drawText("Score: " + dodges*gameSpeed, getWidth()/2, getHeight()/2+69, p);

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
