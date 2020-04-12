package com.lpufoodie.lpufoodie;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class SplashScreen extends AppCompatActivity implements LpuFoodie {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        fadeIn.apply(findViewById(R.id.image));
        fadeIn.apply(findViewById(R.id.tagline));
        new Handler(getMainLooper()).postDelayed(() -> startActivity(new Intent(SplashScreen.this, MainActivity.class)), 3000);
    }
}

