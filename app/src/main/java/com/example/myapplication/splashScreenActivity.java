package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivitySplashScreenBinding;

public class splashScreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content view to the splash screen layout
        setContentView(R.layout.activity_splash_screen);
        setTitle("");

        // Create a new Handler to delay execution of code
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Code to be executed after a delay
                Intent intent = new Intent(splashScreenActivity.this, homepage.class);
                startActivity(intent);
                finish();
            }
        }, 1500); // Create a new Handler to delay execution of code



    }
}