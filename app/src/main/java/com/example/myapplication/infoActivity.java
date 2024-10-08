package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class infoActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //sets content view to info_activity.xml, which is responsible
            // for displaying the information to the user
            setContentView(R.layout.info_activity);
            setTitle("Reach Us");

            ImageButton returnfrominfoButton = findViewById(R.id.infoReturnButton);
            returnfrominfoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Create an Intent to start homepage activity
                    Intent intent = new Intent(infoActivity.this, homepage.class);
                    startActivity(intent);
                }
            });
        }

}
