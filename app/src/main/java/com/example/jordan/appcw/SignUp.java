package com.example.jordan.appcw;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SignUp extends AppCompatActivity {

    private SpendingDBHelper DBHelper;
    private SQLiteDatabase db;//Database used to read user data from to see if the user exists
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sign-Up");

        Button confirm = findViewById(R.id.donebuttonsignup);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

    }

    public void signup() {
        EditText usernameentry = findViewById(R.id.usernameEntered);
        EditText passwordentry = findViewById(R.id.passwordEntered);

        //Username and password entered by the user is fetched
        String[] username = new String[]{usernameentry.getText().toString()};
        String password = new String(passwordentry.getText().toString());

        Intent doneGoHome = new Intent(this,
                LoginActivity.class);
        DBHelper = new SpendingDBHelper(getApplicationContext(),SpendingDBHelper.DB_NAME,null,SpendingDBHelper.DB_VERSION);
        db = DBHelper.getReadableDatabase();//Database to read from
        String[] columns = {
                "PASSWORD"
        };

        String columnsWhere = "USERNAME=?";

        // A cursor is your primary interface to the query results.
        Cursor cursor = db.query(
                "USERS_TABLE", // Table to Query
                columns,
                columnsWhere, // Columns for the "where" clause
                username, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );
        if(cursor.moveToFirst()) {//Runs if there is a username the same as the one the user entered
            TextView signuptitle = findViewById(R.id.signUptext);
            signuptitle.setText("Username exists");
        }
        else {


            db = DBHelper.getWritableDatabase();//Gets the database we will be writing to

            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()); // Gets today's date

            //Inserts the entered data and other data into a row to be put into the database
            ContentValues values = new ContentValues();
            values.put("USERNAME",username[0]);
            values.put("PASSWORD",password);
            values.put("DATEJOINED", date.toString());

            long newRowId = db.insert("USERS_TABLE", null, values);//Inserts the new data f0or the user into the database

            startActivity(doneGoHome);//Takes user to login when this is done
        }
        cursor.close();
    }

}
