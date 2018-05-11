package com.example.jordan.appcw;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.math.BigDecimal;

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
        String query = "CREATE TABLE IF NOT EXISTS USERS_TABLE ( PASSWORD TEXT NOT NULL, USERNAME TEXT PRIMARY KEY, AGE INTEGER, DATEJOINED);";
        db.execSQL(query);
        query = "CREATE TABLE IF NOT EXISTS SPEND_TABLE ( _ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT NOT NULL, AMOUNT INTEGER NOT NULL, LOCATION TEXT, RECEIPT TEXT, DATESUBMITTED TEXT NOT NULL, CATEGORY TEXT);";
        db.execSQL(query);
        System.out.println("TESTING");
        ContentValues values = new ContentValues();
        values.put("USERNAME", "j");
        values.put("PASSWORD", "i");
        values.put("AGE", "20");
        values.put("DATEJOINED", "2018-05-01");

// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert("USERS_TABLE", null, values);

        String username = new String("j");
        int[] ids = new int[]{1,2,3};
        int[] amount = new int[]{1000,2000,3000};
        String[] location = new String[]{"Lufbra" , "EHB" , "Postoffice"};
        String[] date = new String[]{"2018-05-07", "2018-05-08", "2018-05-09"};
        String[] category = new String[]{"General","General","General"};

        for (int i = 0; i<location.length; i++){
            ContentValues values2 = new ContentValues();

            values2.put("USERNAME", username);
            values2.put("_ID", ids[i]);
            values2.put("AMOUNT", amount[i]);
            values2.put("LOCATION", location[i]);
            values2.put("DATESUBMITTED", date[i]);
            values2.put("CATEGORY", category[i]);
            System.out.println(location[i]);
            System.out.println(i);

            long newRowId2 = myDB.insert("SPEND_TABLE", null, values2);
        }
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
        String username = new String("j");
        int[] ids = new int[]{1,2,3};
        int[] amount = new int[]{1000,2000,3000};
        String[] location = new String[]{"Lufbra","EHB","Postoffice"};
        String[] date = new String[]{"2018-05-07", "2018-05-08", "2018-05-09"};
        String[] category = new String[]{"General","General","General"};

        for (int i = 0; i<amount.length;i++){
            ContentValues values = new ContentValues();

            values.put("USERNAME", username);
            values.put("AMOUNT", amount[i]);
            values.put("LOCATION", location[i]);
            values.put("DATESUBMITTED", date[i]);
            values.put("CATEGORY", category[i]);

            String selection = "USERNAME LIKE ?";
            String[] selectionArgs = { username };

            myDB.update(
                    "SPEND_TABLE",
                    values,
                    selection,
                    selectionArgs
            );
        }

    }
}
