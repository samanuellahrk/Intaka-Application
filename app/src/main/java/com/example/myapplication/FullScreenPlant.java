package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FullScreenPlant extends AppCompatActivity {

    ImageButton back;
    ImageView imageView;
    TextView textView;
    ItemsModel itemsModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_plant);
        setTitle("");

        // Initialize ImageView and TextView elements.
        imageView = findViewById(R.id.imageViewPlant);
        textView = findViewById(R.id.tvNamePlant);

        Intent intent = getIntent();

        itemsModel = (ItemsModel) intent.getSerializableExtra("item");
        imageView.setImageResource(itemsModel.getImage());

        // Initialize ImageView and TextView elements.
        String name = itemsModel.getName();
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        name = databaseAccess.fix(name);

        // Initialize ImageView and TextView elements.
        String description = databaseAccess.getDescriptionPlant(name, "IntakaPlants");
        String family = databaseAccess.getFamily(name, "IntakaPlants");
        String commonName = databaseAccess.getCommonName(name, "IntakaPlants");

        // Initialize ImageView and TextView elements.
        textView.setText("Scientific Name: " + name +"\n\nCommonName: "+ commonName + "\n\nFamily: "+ family + "\n\n\n"+ description +"\n\n");
        textView.setMovementMethod(new ScrollingMovementMethod());
        this.setTitle(name);

        back = findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PlantGalleryActivity.class));
            }
        });
    }
}