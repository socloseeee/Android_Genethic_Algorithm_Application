package com.example.lab1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "my_database.db";
    private static final int DATABASE_VERSION = 1;

    public static final String UID = "_id";
    public static final String UNAME = "uname";
    public static final String TABLE_NAME = "contact_table";


    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_NAME + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT," + UNAME + " VARCHAR(255));";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("LOG_TAG", "Upgrading DB from version " + oldVersion + " to version " + newVersion);

        db.execSQL(SQL_DELETE_ENTRIES);

        onCreate(db);
    }

}
