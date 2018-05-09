package com.example.jordan.appcw;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SpendingDBHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DB_VERSION = 1;
    // db name as public as we use it in test later
    public static final String DB_NAME = "spending.db";

    public SQLiteDatabase myDB;

    public SpendingDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        myDB = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        myDB = db;
        String query = "CREATE TABLE IF NOT EXISTS USERS_TABLE ( _ID INTEGER PRIMARY KEY AUTOINCREMENT, PASSWORD TEXT NOT NULL, USERNAME TEXT NOT NULL);";
        db.execSQL(query);
        System.out.println("TESTING");
        ContentValues values = new ContentValues();
        values.put("USERNAME", "j");
        values.put("PASSWORD", "i");

// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert("USERS_TABLE", null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS USERS_TABLE");
        onCreate(db);
    }


    public void clearTable(String table_name){
        myDB.execSQL("DELETE FROM "+ table_name);
    }

    public void Testdata(){


    }
}
