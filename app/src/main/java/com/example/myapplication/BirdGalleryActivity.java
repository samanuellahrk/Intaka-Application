package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BirdGalleryActivity extends AppCompatActivity {
    GridView gridViewBird;
    String[] CustomLabels = new String[112];
    List<ItemsModel> itemsList = new ArrayList<>();
    Adapter birdAdapter;
    public Integer[] images = {
            R.drawable.african_reed_warbler,
            R.drawable.african_snipe,
            R.drawable.african_spoonbill,
            R.drawable.african_swamphen,
            R.drawable.alpine_swift,
            R.drawable.avocet,
            R.drawable.banded_martin,
            R.drawable.blacksmith_plover,
            R.drawable.black_crake,
            R.drawable.black_crowned_night_heron,
            R.drawable.black_headed_heron,
            R.drawable.black_saw_wing_swallow,
            R.drawable.black_shouldered_kite,
            R.drawable.black_sparrowhawk,
            R.drawable.black_swift,
            R.drawable.black_winged_stilt,
            R.drawable.bokmakierie,
            R.drawable.brown_throated_martin,
            R.drawable.bully_canary,
            R.drawable.cape_batis,
            R.drawable.cape_bishop,
            R.drawable.cape_bulbul,
            R.drawable.cape_canary,
            R.drawable.cape_robin_chat,
            R.drawable.cape_shoveller,
            R.drawable.cape_siskin,
            R.drawable.cape_sparrow,
            R.drawable.cape_spurfowl,
            R.drawable.cape_teal,
            R.drawable.cape_turtle_dove,
            R.drawable.cape_wagtail,
            R.drawable.cape_weaver,
            R.drawable.cape_white_eye,
            R.drawable.cattle_egret,
            R.drawable.common_waxbill,
            R.drawable.darter,
            R.drawable.diederik_cuckoo,
            R.drawable.egyptian_goose,
            R.drawable.european_starling,
            R.drawable.european_swallow,
            R.drawable.fan_tailed_cisticola,
            R.drawable.feral_pigeon,
            R.drawable.fiscal_shrike,
            R.drawable.giant_kingfisher,
            R.drawable.glossy_ibis,
            R.drawable.greater_flamingo,
            R.drawable.greater_striped_swallow,
            R.drawable.great_crested_grebe,
            R.drawable.great_white_pelican,
            R.drawable.greenshank,
            R.drawable.grey_headed_gull,
            R.drawable.grey_heron,
            R.drawable.hadeda_ibis,
            R.drawable.hartlaub_gull,
            R.drawable.helmeted_guineafowl,
            R.drawable.hottentot_teal,
            R.drawable.house_sparrow,
            R.drawable.jackal_buzzard,
            R.drawable.kelp_gull,
            R.drawable.kittlitz_plover,
            R.drawable.klaas_cuckoo,
            R.drawable.lanner_falcon,
            R.drawable.laughing_dove,
            R.drawable.lesser_flamingo,
            R.drawable.lesser_swamp_warbler,
            R.drawable.levaillant_cisticola,
            R.drawable.little_bittern,
            R.drawable.little_egret,
            R.drawable.little_grebe,
            R.drawable.little_rush_warbler,
            R.drawable.little_swift,
            R.drawable.malachite_kingfisher,
            R.drawable.malachite_sunbird,
            R.drawable.marsh_sandpiper,
            R.drawable.masked_weaver,
            R.drawable.moorhen,
            R.drawable.olive_thrush,
            R.drawable.painted_snipe,
            R.drawable.paradise_flycatcher,
            R.drawable.peregrine_falcon,
            R.drawable.pied_crow,
            R.drawable.pied_kingfisher,
            R.drawable.pin_tailed_whydah,
            R.drawable.purple_heron,
            R.drawable.red_billed_teal,
            R.drawable.red_bishop,
            R.drawable.red_breasted_sparrowhawk,
            R.drawable.red_eyed_dove,
            R.drawable.red_knobbed_coot,
            R.drawable.red_winged_starling,
            R.drawable.reed_cormorant,
            R.drawable.rock_kestrel,
            R.drawable.sacred_ibis,
            R.drawable.southern_dbl_collared_sunbird,
            R.drawable.speckled_pigeon,
            R.drawable.spotted_eagle_owl,
            R.drawable.spotted_prinia,
            R.drawable.spotted_thick_knee,
            R.drawable.spur_winged_goose,
            R.drawable.squacco_heron,
            R.drawable.steppe_buzzard,
            R.drawable.swift_tern,
            R.drawable.three_banded_plover,
            R.drawable.water_thick_knee,
            R.drawable.white_backed_duck,
            R.drawable.white_breasted_cormorant,
            R.drawable.white_faced_duck,
            R.drawable.white_rumped_swift,
            R.drawable.white_throated_swallow,
            R.drawable.wood_sandpiper,
            R.drawable.yellow_billed_duck,
            R.drawable.yellow_billed_egret,
            R.drawable.yellow_canary
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content view to the 'activity_mainbird' layout
        setContentView(R.layout.activity_mainbird);
        setTitle("Birds Catalog");

        // Create an instance of DatabaseAccess to access the database
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

        try {
            // Create an instance of DatabaseAccess to access the database
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open("birdsList.txt")));
            // Populate the 'CustomLabels' array using the 'populateNames' method from the database
            CustomLabels = databaseAccess.populateNames(bufferedReader, 113);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        gridViewBird = findViewById(R.id.gridViewBirds);

        for (int i = 0; i < CustomLabels.length; i++) {
           // String name = fix(CustomLabels[i]);
            ItemsModel itemsModel = new ItemsModel(CustomLabels[i], images[i]);
            //Toast.makeText(getApplicationContext(),itemsModel.getName(), Toast.LENGTH_LONG).show();
            itemsList.add(itemsModel);
        }

        birdAdapter = new Adapter(itemsList, this, "Bird");
        gridViewBird.setAdapter(birdAdapter);

        ImageButton returnfromBirdButton = findViewById(R.id.button5);
        returnfromBirdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start homepage activity
                Intent intent = new Intent(BirdGalleryActivity.this, homepage.class);
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu with items using MenuInflator
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        // Initialise menu item search bar
        // with id and take its object
        MenuItem menuItem = menu.findItem(R.id.search_view);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setQueryHint("Search by Name");


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                birdAdapter.getFilter().filter(s);
                return false;
            }
        });

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){// This method is called when an options menu item is selected
        int id = item.getItemId();
        // This method is called when an options menu item is selected
        if(id==R.id.search_view){
            // This method is called when an options menu item is selected
            return true;
        }
        // This method is called when an options menu item is selected
        return super.onOptionsItemSelected(item);
    }

}