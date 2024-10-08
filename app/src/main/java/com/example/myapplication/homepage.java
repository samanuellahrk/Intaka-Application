package com.example.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class homepage extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_layout);
        setTitle("Homepage");

        // Find the "Intaka Map" button by its ID
        Button intakaMapButton = findViewById(R.id.button);
        // Set an OnClickListener for the button
        intakaMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start MainActivity
                Intent intent = new Intent(homepage.this, MapActivity.class);
                startActivity(intent);
            }
        });

        // Find the "scanner" button by its ID
        Button scannerMapButton = findViewById(R.id.button2);
        // Set an OnClickListener for the button
        scannerMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start MainActivity
                Intent intent = new Intent(homepage.this, scanner.class);
                startActivity(intent);
            }
        });

        Button plantGalleryButton = findViewById(R.id.button3);
        // Set an OnClickListener for the button
        plantGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start new activity
                Intent intent = new Intent(homepage.this, PlantGalleryActivity.class);
                startActivity(intent);
            }
        });

        Button birdGalleryButton = findViewById(R.id.button4);
        // Set an OnClickListener for the button
        birdGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start new activity
                Intent intent = new Intent(homepage.this, BirdGalleryActivity.class);
                startActivity(intent);
            }
        });

        Button infoButton = findViewById(R.id.button7);
        // Set an OnClickListener for the button
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start new activity
                Intent intent = new Intent(homepage.this, infoActivity.class);
                startActivity(intent);
            }
        });

    }
}
