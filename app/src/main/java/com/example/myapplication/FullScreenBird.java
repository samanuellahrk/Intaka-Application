package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FullScreenBird extends AppCompatActivity {
    ImageButton back;
    ImageView imageView;
    TextView textView;
    ItemsModel itemsModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birds_full_screen);
        setTitle("");
        // Initialize ImageView and TextView elements.
        imageView = findViewById(R.id.imageViewBird);
        textView = findViewById(R.id.tvNameBird);

        // Get the Intent that started this activity.
        Intent intent = getIntent();

        itemsModel = (ItemsModel) intent.getSerializableExtra("item");
        imageView.setImageResource(itemsModel.getImage());

        // Retrieve and format the name of the bird from the database.
        String name = itemsModel.getName();
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        name = databaseAccess.fix(name);

        // Retrieve additional information about the bird (description, diet, habitat) from the database.
        String description = databaseAccess.getDescriptionBird(name, "IntakaBirds");
        String diet = databaseAccess.getDiet(name,"IntakaBirds");
        String habitat = databaseAccess.getHabitat(name,"IntakaBirds");

        // Set the formatted bird information in the TextView.
        textView.setText("Name: " + name + "\n\nDiet: " + diet + "\n\nHabitat: " + habitat + "\n\n\n" + description + "\n\n");
        textView.setMovementMethod(new ScrollingMovementMethod());
        this.setTitle(name);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), BirdGalleryActivity.class));
            }
        });
    }
}