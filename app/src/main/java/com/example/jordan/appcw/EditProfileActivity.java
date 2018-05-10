package com.example.jordan.appcw;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditProfileActivity extends AppCompatActivity {

    EditText usernameEdit;
    EditText passwordEdit;
    EditText AgeEdit;

    private SpendingDBHelper DBHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button doneButton = findViewById(R.id.doneButton);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                done();
            }
        });

        DBHelper = new SpendingDBHelper(getApplicationContext(),SpendingDBHelper.DB_NAME,null,SpendingDBHelper.DB_VERSION);

        final SQLiteDatabase db = DBHelper.getReadableDatabase();

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

        usernameEdit = findViewById(R.id.usernameText);
        passwordEdit = findViewById(R.id.passwordedit);
        AgeEdit = findViewById(R.id.ageField);

        usernameEdit.setText(username[0]);
        if(cursor.moveToFirst()) {
            int passIndex = cursor.getColumnIndex("AGE");
            String age = cursor.getString(passIndex);
            AgeEdit.setText(age);
        }

        else{
            AgeEdit.setText("NOT SET.");
        }
    }

    public void done(){
        Intent isEditingDoneIntent = new Intent( this,
                DashActivity.class);

        DBHelper = new SpendingDBHelper(getApplicationContext(),SpendingDBHelper.DB_NAME,null,SpendingDBHelper.DB_VERSION);

        final SQLiteDatabase db = DBHelper.getWritableDatabase();

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

        ((MyApplication) this.getApplication()).setUsername(usernameEdit.getText().toString());

        startActivity(isEditingDoneIntent);
    }

}
