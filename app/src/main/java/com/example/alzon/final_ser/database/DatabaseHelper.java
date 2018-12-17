package com.example.alzon.final_ser.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.alzon.final_ser.modelclass.Upload;
import com.example.alzon.final_ser.modelclass.favorite_modelclass;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "UserManager.db";

    // User table name

    // create table sql query


    private String FAVOURITE = "CREATE TABLE " + "favorite_list" + "("
            + "id" + " INTEGER," + "audio_url" + " TEXT,"
            + "album_url" + " TEXT," + "song_name" + " TEXT" + ")";



    /**
     * Constructor
     *
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(FAVOURITE);


    }


        @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop User Table if exist

    }


    public void favoriteinsert(favorite_modelclass user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("audio_url", user.getAudiourl());
        values.put("album_url", user.getAlbumurl());
        values.put("song_name", user.getName());
        db.insert("favorite_list", null, values);
        db.close();
    }
    public List<favorite_modelclass> getfavorite() {
        List<favorite_modelclass> notes = new ArrayList<>();

        // Select All Query

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * " + " FROM " + "favorite_list", null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                favorite_modelclass  note = new favorite_modelclass();
                note.setAudiourl(cursor.getString(cursor.getColumnIndex("audio_url")));
                note.setUrl(cursor.getString(cursor.getColumnIndex("album_url")));
                note.setName(cursor.getString(cursor.getColumnIndex("song_name")));



                // note.setCarbon_waste_management(cursor.getString(cursor.getColumnIndex("carbon_waste_management")));

                //  note.setCarbon_footprint(cursor.getString(cursor.getColumnIndex("carbon_footprint")));
                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }


}