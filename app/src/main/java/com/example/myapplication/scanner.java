package com.example.myapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.ml.ModelUnquant;
import com.example.myapplication.ml.RefinedModel;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class scanner extends AppCompatActivity {

    Button selectBtn, predictBtn, captureBtn, moredetailsBtn, predictBtn2;
    TextView result;
    ImageView imageView;
    Bitmap bitmap;

    String[] databaseList = new String[233];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanner_layout);
        setTitle("Plant Scanner");

        //asking for permission to access the camera and gallery before anything else can be done
        getPermission();

        //String handling for the Image processing output
        //Takes in a textfile from Assets and matches the Identifies Plant to its appropriate name in the text file
        String[] CustomLabels = new String[233];
        int cnt = 0;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open("generalModelLabels.txt")));
            String line = bufferedReader.readLine();
            while(line != null){
                CustomLabels[cnt]= line;
                cnt++;
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String[] CustomLabels2 = new String[233];
        int cnt2 = 0;
        try {
            BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(getAssets().open("refinedModelLabels.txt")));
            String line2 = bufferedReader2.readLine();
            while(line2 != null){
                CustomLabels2[cnt2]= line2;
                cnt2++;
                line2 = bufferedReader2.readLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // User Interface Navigation
        selectBtn = findViewById(R.id.selectBtn);
        predictBtn = findViewById(R.id.predictBtn);
        predictBtn2 = findViewById(R.id.predictBtn2);
        captureBtn = findViewById(R.id.captureBtn);
        result = findViewById(R.id.result);
        imageView = findViewById(R.id.imageView);
        moredetailsBtn = findViewById(R.id.moreDetailsBtn);
        ImageButton returnBtn = findViewById(R.id.returnButton); // initialize return button

        //Create and open Database access object
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open("plantsList.txt"))); // get plant list
            databaseList = databaseAccess.populateNames(bufferedReader, 233);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //return button to go back to homepage
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(scanner.this, homepage.class));
            }
        });

        //the database button opens a new view for more information on the plants
        moredetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = databaseAccess.fix(String.valueOf(result.getText()));
                int index =0;
                boolean found = false;

                for(int i=0; i<databaseList.length;i++){
                    String current = databaseAccess.fix(databaseList[i]);
                    if(temp.equalsIgnoreCase(current)){
                        index = i;
                        found= true;
                        break;
                    }
                }

                if(found){ // plant exists in database and more inforamtion is available
                    LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                    View Myview = inflater.inflate(R.layout.row_items, null);
                    ImageView imageView = Myview.findViewById(R.id.imageView);
                    TextView tvNames = Myview.findViewById(R.id.tvName);
                    imageView.setImageResource(PlantGalleryActivity.images[index]);
                    tvNames.setText(temp);

                    ItemsModel item = new ItemsModel(temp, PlantGalleryActivity.images[index]);

                    startActivity(new Intent(scanner.this, FullScreenPlant.class).putExtra("item",item));
                }
                else{ //error handling
                    if(temp.equalsIgnoreCase("")){ //no photo inout provided by user
                        Toast.makeText(getApplicationContext(), "Please load a photo and predict first", Toast.LENGTH_SHORT).show();
                    }
                    else{ // plant not found in database 
                        Toast.makeText(getApplicationContext(), temp + " was not found in the database.", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

        //the select button opens the camera roll of the device
        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //opening new activity to access pictures on the device
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                //getting only type images
                intent.setType("image/*");
                startActivityForResult(intent, 10);
            }
        });

        //the capture button opens the camera for the user to take a photo
        captureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 12);

            }
        });

        // the predict button calls the image processing model
        predictBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageView.getDrawable()==null){ // no image uploaded by user
                    Toast.makeText(getApplicationContext(), "Please load an image before trying to predict.", Toast.LENGTH_SHORT).show();
                }
                else{

                try {
                    bitmap = Bitmap.createScaledBitmap(bitmap,224,224,true);

                    ModelUnquant model1 = ModelUnquant.newInstance(scanner.this); //create ModelUnquant object
                    // Creates inputs for reference.
                    TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
                    // handle image processing 
                    ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * 224 *224 * 3); 
                    byteBuffer.order(ByteOrder.nativeOrder());

                    int[] intVals = new int[224*224];
                    bitmap.getPixels(intVals,0,bitmap.getWidth(),0,0,bitmap.getWidth(),bitmap.getHeight());
                    int pixel =0;
                    for(int i=0; i<224; i++){
                        for (int j=0; j<224; j++){
                            //assigning the RGB
                            int val = intVals[pixel++];
                            byteBuffer.putFloat(((val>>16)& 0xFF)*(1.f/225.f));
                            byteBuffer.putFloat(((val>>8)& 0xFF)*(1.f/225.f));
                            byteBuffer.putFloat((val & 0xFF)*(1.f/225.f));
                        }
                    }

                    inputFeature0.loadBuffer(byteBuffer);

                    // Runs model inference and gets result.
                    ModelUnquant.Outputs output1 = model1.process(inputFeature0);
                    TensorBuffer outputFeature0 = output1.getOutputFeature0AsTensorBuffer();

                    float[] confidences = outputFeature0.getFloatArray(); // initialize array with confidences from model results
                    int maxPos=0;
                    float maxCon =0;
                    //finding the result with the highest confidence (the most likely result)
                    for(int i=0; i<confidences.length; i++){
                        if(confidences[i]>maxCon){
                            maxCon = confidences[i];
                            maxPos = i;
                        }
                    }
                    if (confidences[maxPos]<=0.7) { //set boundary for prediction

                        String textForRes = " Not Found";
                        // Create a SpannableStringBuilder
                        SpannableStringBuilder builder = new SpannableStringBuilder(textForRes);

                        // Apply bold style
                        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
                        builder.setSpan(boldSpan, 0, textForRes.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                        // Apply italic style
                        StyleSpan italicSpan = new StyleSpan(Typeface.ITALIC);
                        builder.setSpan(italicSpan, 0, textForRes.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                        // Set the text in your TextView or wherever you want to display it
                        result.setText(builder);
                        //result.setText(textForRes.substring(1));
                    }
                    else {
                        String textForRes = CustomLabels[getMax(outputFeature0.getFloatArray())]+""; //find highest confidence
                        String max = String.valueOf(getMax(outputFeature0.getFloatArray()));

                        SpannableStringBuilder builder = new SpannableStringBuilder(textForRes);

                        // Apply bold style
                        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
                        builder.setSpan(boldSpan, 0, textForRes.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                        // Apply italic style
                        StyleSpan italicSpan = new StyleSpan(Typeface.ITALIC);
                        builder.setSpan(italicSpan, 0, textForRes.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                        // Set the text in your TextView or wherever you want to display it
                        result.setText(builder);

                    }

                    //getting the matching name from the text file in assets based on the highest confidence
                    Log.d("MainActivity",String.valueOf(confidences[maxPos]) );


                    // Releases model resources if no longer used.
                    model1.close();

                } catch (IOException e) {
                }

                }}
        });

        //the predict button calls the custom identification model to name the plant
        predictBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageView.getDrawable()==null){ //error handling
                    Toast.makeText(getApplicationContext(), "Please load an image before trying to predict.", Toast.LENGTH_SHORT).show();
                }
                else{
                    try {
                        RefinedModel model = RefinedModel.newInstance(scanner.this); //create RefinedModel object

                        bitmap = Bitmap.createScaledBitmap(bitmap,224,224,true);
                        // Creates inputs for reference.
                        TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
                        
                        //handle image processing
                        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * 224 *224 * 3);
                        byteBuffer.order(ByteOrder.nativeOrder());

                        int[] intVals = new int[224*224];
                        bitmap.getPixels(intVals,0,bitmap.getWidth(),0,0,bitmap.getWidth(),bitmap.getHeight());
                        int pixel =0;
                        for(int i=0; i<224; i++){
                            for (int j=0; j<224; j++){
                                //assigning the RGB
                                int val = intVals[pixel++];
                                byteBuffer.putFloat(((val>>16)& 0xFF)*(1.f/225.f));
                                byteBuffer.putFloat(((val>>8)& 0xFF)*(1.f/225.f));
                                byteBuffer.putFloat((val & 0xFF)*(1.f/225.f));
                            }
                        }

                        inputFeature0.loadBuffer(byteBuffer);

                        // Runs model inference and gets result.
                        RefinedModel.Outputs outputs = model.process(inputFeature0);
                        TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                        float[] confidences = outputFeature0.getFloatArray(); // initialize array with confidences from model results
                        int maxPos=0;
                        float maxCon =0;
                        //finding the result with the highest confidence (the most likely result)
                        for(int i=0; i<confidences.length; i++){
                            if(confidences[i]>maxCon){
                                maxCon = confidences[i];
                                maxPos = i;
                            }
                        }

                        if (confidences[maxPos]<=0.7) { //plant not found

                            String textForRes = "Please try another image";
                            // Create a SpannableStringBuilder
                            SpannableStringBuilder builder = new SpannableStringBuilder(textForRes);

                            // Apply bold style
                            StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
                            builder.setSpan(boldSpan, 0, textForRes.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                            // Apply italic style
                            StyleSpan italicSpan = new StyleSpan(Typeface.ITALIC);
                            builder.setSpan(italicSpan, 0, textForRes.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                            // Set the text in your TextView or wherever you want to display it
                            result.setText(builder);
                            //result.setText(textForRes.substring(1));
                        }
                        else {
                                //determine highest probability plant
                                String textForRes = CustomLabels2[getMax(outputFeature0.getFloatArray())]+"";
                                String max = String.valueOf(getMax(outputFeature0.getFloatArray()));

                                SpannableStringBuilder builder = new SpannableStringBuilder(textForRes);

                                // Apply bold style
                                StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
                                builder.setSpan(boldSpan, 0, textForRes.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                                // Apply italic style
                                StyleSpan italicSpan = new StyleSpan(Typeface.ITALIC);
                                builder.setSpan(italicSpan, 0, textForRes.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                                result.setText(builder);
                            }

                        //getting the matching name from the text file in assets based on the highest confidence
                        Log.d("MainActivity",String.valueOf(confidences[maxPos]) );

                        // Releases model resources if no longer used.
                        model.close();
                    } catch (IOException e) {
                        // TODO Handle the exception
                    }
            }}
        });
    }

    //helper method to find highest confidence
    int getMax(float[] arr){
        int max = 0;
        for(int i=0; i<arr.length; i++){
            if(arr[i] > arr[max]){
                max = i;
            }
        }
        return max;
    }

    //helper method to request permission until it is accepted
    void getPermission(){
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(scanner.this, new String[]{android.Manifest.permission.CAMERA}, 11);
        }
    }

    //method to record the result of the permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==11){
            if(grantResults.length>0){
                if(grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    //ask again until permission is granted
                    getPermission();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //continuing after the permission has been given, getting the result and displaying them from the inference
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==10){
            if(data!=null){
                Uri uri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        else if(requestCode==12){
            bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}