package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.IOException;
public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    Cursor c = null;

    //Constructor initializing the Database Helper
    private DatabaseAccess(Context context){
        this.openHelper = new DatabaseHelper(context);
    }

    //Method that retrieves the database, or instanciates the database if it does not exist already
    public static DatabaseAccess getInstance(Context context){
        if(instance == null){
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    //open the connection to the database
    public void open(){

        this.db = openHelper.getWritableDatabase();
    }

    //safely closing the database connection
    public void close(){
        if(db!=null){
            this.db.close();
        }
    }

    //Method to retrieve the Description of the bird by its name (given as input from the user)
    public String getDescriptionBird(String name, String table){
        c = db.rawQuery("select Description from "+table+" where Name = '" + name +"'", new String[]{});
        StringBuffer buffer = new StringBuffer();
        while(c.moveToNext()){
            String des = c.getString(0);
            buffer.append(""+des);
        }
        return buffer.toString();
    }
    //Method to retrieve the Diet of the bird by its name
    public String getDiet(String name, String table){
        c = db.rawQuery("select Diet from "+table+" where Name = '" + name +"'", new String[]{});
        StringBuffer buffer = new StringBuffer();
        while(c.moveToNext()) {
            String des = c.getString(0);
            buffer.append("" + des);
        }
        return buffer.toString();
    }
    //Method to retrieve the habitat of the bird by its name
    public String getHabitat(String name, String table){
        c = db.rawQuery("select Habitat from "+table+" where Name = '" + name +"'", new String[]{});
        StringBuffer buffer = new StringBuffer();
        while(c.moveToNext()){
            String des = c.getString(0);
            buffer.append(""+des);
        }
        return buffer.toString();
    }
    //Method to retrieve the Description of the plant by its name
    public String getDescriptionPlant(String name,String table){
        c = db.rawQuery("select Description from "+table+" where ScientificName = '" + name +"'", new String[]{});
        StringBuffer buffer = new StringBuffer();
        while(c.moveToNext()){
            String des = c.getString(0);
            buffer.append(""+des);
        }
        return buffer.toString();
    }
    //Method to retrieve the family of the plant by its name
    public String getFamily(String name, String table){
        c = db.rawQuery("select Family from "+table+" where ScientificName = '" + name +"'", new String[]{});
        StringBuffer buffer = new StringBuffer();
        while(c.moveToNext()){
            String des = c.getString(0);
            buffer.append(""+des);
        }
        return buffer.toString();
    }
    //Method to retrieve the common name of the plant by its name
    public String getCommonName(String name, String table){
        c = db.rawQuery("select CommonName from "+table+" where ScientificName = '" + name +"'", new String[]{});
        StringBuffer buffer = new StringBuffer();
        while(c.moveToNext()){
            String des = c.getString(0);
            buffer.append(""+des);
        }
        return buffer.toString();
    }

    // This method populates an array of custom labels by reading lines from a BufferedReader.
    // It takes the BufferedReader and the desired array size 'n' as parameters.
    public String[] populateNames( BufferedReader bufferedReader, int n) {

        String[] CustomLabels = new String[n];
        int cnt = 0;
        try {
            String line = bufferedReader.readLine();
            while (line != null) {
                //labels[cnt] = line;
                CustomLabels[cnt] = line;
                cnt++;
                line = bufferedReader.readLine();
            }
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        return CustomLabels;
    }
    // This method takes a string 'n' as input and performs formatting.
    // It replaces underscores with spaces and capitalizes the first character.
    public String fix(String n){
        if(n==null){
            return "";
        }
        else if(n.length()>0){
            n = n.replace("_", " ");
            char first =Character.toUpperCase(n.charAt(0));
            n = first+n.substring(1);
        }
        return n;
    }



}
