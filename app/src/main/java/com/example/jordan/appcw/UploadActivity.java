package com.example.jordan.appcw;

import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UploadActivity extends AppCompatActivity {//This class is for the user to upload their spending to the database

    private SpendingDBHelper DBHelper;
    private SQLiteDatabase db;

    List<Integer> IDS = new ArrayList<>();//For all the current
    int maxval=-1;//Maximum ID present in the database

    //Textviews and dropdown for uploading information
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

        getSupportActionBar().setTitle("Spending Upload"); //Sets the title at the top of the app page

        //Sets up the textviews with the front end
        id = (TextView)findViewById(R.id.NextexpendID);
        amountEntered = (TextView)findViewById(R.id.AmounteditText);
        LocationEntered = (TextView)findViewById(R.id.LocationeditText);

        DBHelper = new SpendingDBHelper(getApplicationContext(),SpendingDBHelper.DB_NAME,null,SpendingDBHelper.DB_VERSION);

        final SQLiteDatabase db = DBHelper.getReadableDatabase();//Gets the database we will be reading from for the ids

        String[] columns = {
                "_ID"
        };


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

        while(cursor.moveToNext()){//Puts all ids into the array list
            int passIndex = cursor.getColumnIndex("_ID");
            IDS.add(cursor.getInt(passIndex));
        }

        for(int i = 0;i<IDS.size();i++){//finds the largest id+1
            if(maxval<=IDS.get(i)){
                maxval = IDS.get(i)+1;
            }
        }

        id.setText(String.valueOf(maxval));//Shows the user what the id will be for this upload

        categorydropdown = (Spinner)findViewById(R.id.categoryspinner);

        String[] items = new String[]{"General", "Food", "Games"};//Categories to be put in the drop down menu
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        categorydropdown.setAdapter(adapter);

        Button confirmLog = (Button)findViewById(R.id.confirmLog); //Sets up the upload button and it's listener, user clicks this when they have finsihed their upload
        confirmLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmLog();
            }
        });

    }

    public void ConfirmLog() {//Triggers when the upload button is clicked, saves the data entered into a new row in the table
        Intent doneGoHome = new Intent(this,
                DashActivity.class);
        DBHelper = new SpendingDBHelper(getApplicationContext(),SpendingDBHelper.DB_NAME,null,SpendingDBHelper.DB_VERSION);

        db = DBHelper.getWritableDatabase();//Gets the database we will be writing to

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()); // Gets today's date
        String[] username = new String[]{(((MyApplication) this.getApplication()).getUsername())}; //Gets users username

        //Inserts the entered data and other data into a row to be put into the database
        ContentValues values = new ContentValues();
        values.put("_ID",maxval);
        values.put("AMOUNT",amountEntered.getText().toString());
        values.put("CATEGORY", categorydropdown.getSelectedItem().toString());
        values.put("LOCATION", LocationEntered.getText().toString());
        values.put("USERNAME", username[0]);
        values.put("DATESUBMITTED", date);

        long newRowId = db.insert("SPEND_TABLE", null, values);

        String[] columns = new String[]{"AMOUNT", "CATEGORY", "AVERAGEMONTHLY"};
        String columnsWhere = new String("USERNAME=?");
        db = DBHelper.getReadableDatabase();

        Cursor cursor = db.query(
                "GOAL_TABLE", // Table to Query
                columns,
                columnsWhere, // Columns for the "where" clause
                username, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );
        ArrayList<Integer> targets = new ArrayList<>();
        ArrayList<String> category = new ArrayList<>();
        ArrayList<Integer> averageMonthly = new ArrayList<>();

        if(cursor != null) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount();i++) {//Creates table and stores data for previous goals

                //Stores the data in the lists from the goal table
                int passIndex = cursor.getColumnIndex("AMOUNT");
                targets.add(cursor.getInt(passIndex));
                passIndex = cursor.getColumnIndex("CATEGORY");
                category.add(cursor.getString(passIndex));
                passIndex = cursor.getColumnIndex("AVERAGEMONTHLY");
                averageMonthly.add(cursor.getInt(passIndex));
            }
            cursor.close();

        }


        if((Integer.valueOf(amountEntered.getText().toString()))>targets.get(0)){

            notifyThis("AppCW","You have exceeded your goal!");

        }

        startActivity(doneGoHome);//Takes user to dashboard when this is done
    }

    public void notifyThis(String title, String message) {
        NotificationCompat.Builder b = new NotificationCompat.Builder(getApplicationContext());
        b.setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.your_icon)
                .setTicker("{your tiny message}")
                .setContentTitle(title)
                .setContentText(message)
                .setContentInfo("INFO");

        NotificationManager nm = (NotificationManager) getApplicationContext().getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        nm.notify(1, b.build());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {//Saves the data the user has entered when the activity is destroyed
        super.onSaveInstanceState(outState);
        EditText locationInput = findViewById(R.id.LocationeditText);
        EditText amountInput = findViewById(R.id.AmounteditText);
        TextView ID = findViewById(R.id.expendID);
        String username = new String(((MyApplication) this.getApplication()).getUsername());

        outState.putString("location",locationInput.getText().toString());
        outState.putString("amount", amountInput.getText().toString());
        outState.putString("id",ID.getText().toString());
        outState.putString("username",username);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {//Reclaims the data the user has entered when the app is restarted
        super.onRestoreInstanceState(savedInstanceState);
        EditText locationInput = findViewById(R.id.LocationeditText);
        EditText amountInput = findViewById(R.id.AmounteditText);
        TextView ID = findViewById(R.id.expendID);

        String usernametext = new String(savedInstanceState.getString("username"));

        ((MyApplication) this.getApplication()).setUsername(usernametext);

        locationInput.setText(savedInstanceState.getString("location"));
        amountInput.setText(savedInstanceState.getString("amount"));
        ID.setText(savedInstanceState.getString("id"));

    }

}
