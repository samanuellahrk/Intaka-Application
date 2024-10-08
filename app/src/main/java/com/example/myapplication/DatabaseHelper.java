package com.example.myapplication;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
public class DatabaseHelper extends SQLiteAssetHelper{
    public static final String DATABASE_NAME = "IntakaWildlifeDb.db";
    public static final int DATABASE_VERSION = 1;

    //calling the super constructor to build the database
    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
