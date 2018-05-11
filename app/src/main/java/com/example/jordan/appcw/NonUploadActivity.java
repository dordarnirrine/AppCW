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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NonUploadActivity extends AppCompatActivity {

    private SpendingDBHelper DBHelper;
    private SQLiteDatabase db;

    List<Integer> IDS = new ArrayList<>();
    int maxval=-1;

    Spinner categorydropdown;
    TextView id;
    TextView amountEntered;
    TextView LocationEntered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_upload);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        id = (TextView)findViewById(R.id.NextexpendID);
        amountEntered = (TextView)findViewById(R.id.AmounteditText);
        LocationEntered = (TextView)findViewById(R.id.LocationeditText);

        DBHelper = new SpendingDBHelper(getApplicationContext(),SpendingDBHelper.DB_NAME,null,SpendingDBHelper.DB_VERSION);

        final SQLiteDatabase db = DBHelper.getReadableDatabase();

        String[] columns = {
                "_ID"
        };

        String columnsWhere = "MAX(_ID)";
        String[] username = new String[]{((MyApplication) this.getApplication()).getUsername()};

        // A cursor is your primary interface to the query results.
        Cursor cursor = db.query(
                "SPEND_TABLE", // Table to Query
                columns,
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        while(cursor.moveToNext()){
            int passIndex = cursor.getColumnIndex("_ID");
            IDS.add(cursor.getInt(passIndex));
        }

        for(int i = 0;i<IDS.size();i++){
            if(maxval<=IDS.get(i)){
                maxval = IDS.get(i)+1;
            }
        }

        id.setText(String.valueOf(maxval));

        categorydropdown = (Spinner)findViewById(R.id.categoryspinner);

        String[] items = new String[]{"1", "2", "three"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        categorydropdown.setAdapter(adapter);

        Button confirmLog = (Button)findViewById(R.id.confirmLog);
        confirmLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmLog();
            }
        });

    }

    public void ConfirmLog() {
        Intent doneGoHome = new Intent(this,
                DashActivity.class);
        DBHelper = new SpendingDBHelper(getApplicationContext(),SpendingDBHelper.DB_NAME,null,SpendingDBHelper.DB_VERSION);
        db = DBHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("_ID",maxval);
        values.put("AMOUNT",amountEntered.getText().toString());
        values.put("CATEGORY", categorydropdown.getSelectedItem().toString());
        values.put("LOCATION", LocationEntered.getText().toString());

        String selection = "_ID=?";
        String[] selectionArgs = {  };

        db.update(
                "SPEND_TABLE",
                values,
                selection,
                selectionArgs);


        startActivity(doneGoHome);
    }

}
