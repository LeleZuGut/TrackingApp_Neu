package com.example.trackingapp.ui.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import com.example.trackingapp.R;

public class splashscreen extends AppCompatActivity {
private static int SPLASH_TIME_OUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final String name = prefs.getString("user", "notsignedin");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (name.equals("notsignedin")){
                    Intent homeIntent = new Intent(splashscreen.this, LoginFensterActivity.class);
                    startActivity(homeIntent);
                }else{
                    Intent homeIntent = new Intent(splashscreen.this, MainActivity.class);
                    startActivity(homeIntent);
                }
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}