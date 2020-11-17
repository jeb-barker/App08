package com.barkerjeb.app08;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    DrawView dv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dv = findViewById(R.id.drawView);
        Drawable im = ContextCompat.getDrawable(getBaseContext(), R.drawable.background);
        im.setAlpha(69);
        dv.setBackground(im);
    }
}