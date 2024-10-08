package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback{
    public GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Maps");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);

        Button returnButton = findViewById(R.id.returnButton);

        // Set an OnClickListener for the button
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start homepage activity
                Intent intent = new Intent(MapActivity.this, homepage.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
            this.googleMap = googleMap;

            // Set the camera position and zoom level, to Intaka Island
            LatLng desiredLatLng = new LatLng(-33.8883, 18.5159);
            float desiredZoomLevel = 16; // Choose a value between 2.0 and 21.0, where higher values are closer zoom levels.

            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(desiredLatLng, desiredZoomLevel);
            googleMap.moveCamera(cameraUpdate);

            // the following code is responsible for the various markers marking the points of interest
            // in Intaka Island
            LatLng lapaPos = new LatLng(-33.888461545384175, 18.516037160984943);
            MarkerOptions lapa = new MarkerOptions()
                    .position(lapaPos)
                    .title("Lapa")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));;//.icon(BitmapDescriptorFactory.fromResource(R.drawable.lapa1));;
            googleMap.addMarker(lapa);

            LatLng ferryPos = new LatLng(-33.888135, 18.513445);
            MarkerOptions ferry = new MarkerOptions()
                    .position(ferryPos)
                    .title("Intaka Ferry")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));;
            googleMap.addMarker(ferry);

            LatLng birdPos = new LatLng(-33.88860329028589, 18.516676580711735);
            MarkerOptions bird = new MarkerOptions()
                    .position(birdPos)
                    .title("Bird Mountain")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));;
            googleMap.addMarker(bird);

            LatLng heronryPos = new LatLng(-33.88977158171513, 18.51619308545262);
            MarkerOptions heronry = new MarkerOptions()
                    .position(heronryPos)
                    .title("Heronry")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));;
            googleMap.addMarker(heronry);

            LatLng kingfishPos = new LatLng(-33.88964753723843, 18.515262015830316);
            MarkerOptions kingfish = new MarkerOptions()
                    .position(kingfishPos)
                    .title("Kingfisher Bird Hide")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));;
            googleMap.addMarker(kingfish);


            LatLng heronPos = new LatLng(-33.88956523839954, 18.515165747829247);
            MarkerOptions heron = new MarkerOptions()
                    .position(heronPos)
                    .title("Heron Bird Hide")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));;
            googleMap.addMarker(heron);


            //            ClassLoader context;
            //            Resources res = context.getResources();
            //            Drawable myImage = ResourcesCompat.getDrawable(res, R.drawable.garden1, null);
            LatLng gardenPos = new LatLng(-33.8882675302688, 18.51576490837323);
            MarkerOptions garden = new MarkerOptions()
                    .position(gardenPos)
                    .title("Educational Garden")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));;//.icon(BitmapDescriptorFactory.fromResource(R.drawable.garden1));;
            googleMap.addMarker(garden);

            LatLng pondPos = new LatLng(-33.88881619604186, 18.51610974897408);
            MarkerOptions pond = new MarkerOptions()
                    .position(pondPos)
                    .title("Dipping Pond")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));;
            googleMap.addMarker(pond);

            LatLng nurseryPos = new LatLng(-33.8894479086209, 18.51371605362151);
            MarkerOptions nursery = new MarkerOptions()
                    .position(nurseryPos)
                    .title("Nursery")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));;
            googleMap.addMarker(nursery);

            LatLng ecoPos = new LatLng(-33.888226727011514, 18.513452575039665);
            MarkerOptions eco = new MarkerOptions()
                    .position(ecoPos)
                    .title("Eco-Centre")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));;
            googleMap.addMarker(eco);

            LatLng viewPos = new LatLng(-33.886268841672326, 18.517460451181357);
            MarkerOptions view = new MarkerOptions()
                    .position(viewPos)
                    .title("Viewing Platform")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));;
            googleMap.addMarker(view);



    }


}