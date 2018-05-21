package com.example.jordan.appcw;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditProfileActivity extends AppCompatActivity {//This activity is used to edit the information profile of the user

    //initialses the fields the users enter the data into
    EditText usernameEdit;
    EditText passwordEdit;
    EditText AgeEdit;

    private SpendingDBHelper DBHelper;
    private SQLiteDatabase db;//Database used to read and write from.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit Profile"); //Sets title at the top of the page

        Button doneButton = findViewById(R.id.doneButton);//sets up the button and the button listener

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                done();
            }
        });

        DBHelper = new SpendingDBHelper(getApplicationContext(),SpendingDBHelper.DB_NAME,null,SpendingDBHelper.DB_VERSION);

        final SQLiteDatabase db = DBHelper.getReadableDatabase();//database which data is read from to show the user what the database has on them at the moment

        String[] columns = new String[]{"USERNAME","AGE"};
        String columnsWhere = new String("USERNAME=?");
        String[] username = new String[]{((MyApplication) this.getApplication()).getUsername()};

        Cursor cursor = db.query(
                "USERS_TABLE", // Table to Query
                columns,
                columnsWhere, // Columns for the "where" clause
                username, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        usernameEdit = findViewById(R.id.usernameText1);
        passwordEdit = findViewById(R.id.passwordedit);
        AgeEdit = findViewById(R.id.ageField);

        usernameEdit.setText(username[0]);
        if(cursor.moveToFirst()) {//puts the information into strings to be put into the edit text
            int passIndex = cursor.getColumnIndex("AGE");
            String age = cursor.getString(passIndex);
            AgeEdit.setText(age);//edit text set to the current value
        }

        else{
            AgeEdit.setText("NOT SET.");
        }
        cursor.close();
    }

    public void done(){//This function is called when the user is done editing their profile, it will save all of the changes which they have made.
        Intent isEditingDoneIntent = new Intent( this,
                DashActivity.class);

        DBHelper = new SpendingDBHelper(getApplicationContext(),SpendingDBHelper.DB_NAME,null,SpendingDBHelper.DB_VERSION);

        final SQLiteDatabase db = DBHelper.getWritableDatabase();//Gets the database that we will write information to

        ContentValues values = new ContentValues();
        values.put("USERNAME",usernameEdit.getText().toString());
        values.put("PASSWORD",passwordEdit.getText().toString());
        values.put("AGE", AgeEdit.getText().toString());

        String selection = "USERNAME LIKE ?";
        String[] selectionArgs = { ((MyApplication) this.getApplication()).getUsername() };

        db.update(
                "USERS_TABLE",
                values,
                selection,
                selectionArgs);

        ((MyApplication) this.getApplication()).setUsername(usernameEdit.getText().toString());//updates the global user name variable

        startActivity(isEditingDoneIntent);//Takes the user back to the dashboard when they are done editing
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {//saves the users data when the activity is destroyed
        super.onSaveInstanceState(outState);
        outState.putString("usernameinput",usernameEdit.getText().toString());
        outState.putString("password", passwordEdit.getText().toString());
        outState.putString("age", AgeEdit.getText().toString());

        String username = new String(((MyApplication) this.getApplication()).getUsername());
        outState.putString("USERNAME", username);


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {//reloads the users data when the activity is restarted
        super.onRestoreInstanceState(savedInstanceState);

        String usernametext = new String(savedInstanceState.getString("USERNAME"));

        ((MyApplication) this.getApplication()).setUsername(usernametext);

        usernameEdit.setText(savedInstanceState.getString("usernameinput"));
        passwordEdit.setText(savedInstanceState.getString("password"));
        AgeEdit.setText(savedInstanceState.getString("age"));

    }
}
