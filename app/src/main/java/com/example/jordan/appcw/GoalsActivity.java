package com.example.jordan.appcw;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GoalsActivity extends AppCompatActivity {//This activity is for the users to submit their goals and see their previous goals

    private SpendingDBHelper DBHelper;
    private SQLiteDatabase db;


    //Lists used for storing and operating with data from the database
    List<Integer> targets = new ArrayList<>();
    List<String> category = new ArrayList<>();
    List<Integer> averageMonthly = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Goals");//Sets title at the top of the app

        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.categorySelect);
        //create a list of items for the spinner.
        String[] items = new String[]{"General", "Food", "Games"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Setting up of the buttons on the page and the listners for when they are pressed
        Button generalConfirm = findViewById(R.id.ConfirmButton1);
        Button categoryConfirm = findViewById(R.id.ConfirmButton2);

        generalConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralGoalAdd();
            }
        });

        categoryConfirm.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryGoalAdd();
            }
        }));

        DBHelper = new SpendingDBHelper(getApplicationContext(),SpendingDBHelper.DB_NAME,null,SpendingDBHelper.DB_VERSION);

        final SQLiteDatabase db = DBHelper.getReadableDatabase();//Database used to retrieve information on current goals

        String[] columns = new String[]{"USERNAME", "AMOUNT", "CATEGORY", "AVERAGEMONTHLY"};
        String columnsWhere = new String("USERNAME=?");
        String[] username = new String[]{((MyApplication) this.getApplication()).getUsername()};


        Cursor cursor = db.query(
                "GOAL_TABLE", // Table to Query
                columns,
                columnsWhere, // Columns for the "where" clause
                username, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

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

                TableLayout goalTable = findViewById(R.id.goalTable);
                //Row which the data will be put into recursively
                TableRow row = new TableRow(this);
                //Textviews the data is put into
                TextView textGoal = new TextView(this);
                textGoal.setText(category.get(i));

                TextView textTarget = new TextView(this);
                textTarget.setText(String.valueOf(targets.get(i)));

                TextView textAverageMonthly = new TextView(this);
                textAverageMonthly.setText(String.valueOf(averageMonthly.get(i)));

                TextView textThisMonth = new TextView(this);
                textThisMonth.setText("0");
                //Textviews filled with data added to the row
                row.addView(textGoal);
                row.addView(textTarget);
                row.addView(textAverageMonthly);
                row.addView(textThisMonth);

                goalTable.addView(row);//row added to the table
                cursor.moveToNext();//moves cursor to the next item
            }
            cursor.close();

        }
    }



    public void CategoryGoalAdd() {//adds the goal for the category
        TableLayout goalTable = (TableLayout)findViewById(R.id.goalTable);
        EditText input = (EditText) findViewById(R.id.categoryTarget);
        String targetInput = input.getText().toString();
        Spinner spinner = (Spinner) findViewById(R.id.categorySelect);
        String categorySelected = spinner.getSelectedItem().toString();

        int indexOfCat = category.indexOf(categorySelected);
        if(indexOfCat!=-1){//sees if there already exists a goal for this category
            targets.set(indexOfCat, Integer.parseInt(targetInput));//If it does, replaces the target for that category with the new one

            int childCount = goalTable.getChildCount();

            // Remove all rows except the first one, which has the headers in
            if (childCount > 1) {
                goalTable.removeViews(1, childCount - 1);
            }
            for (int i = 0; i < targets.size();i++) {//Inserts all the data back into the table, with the updated data
                TableRow row = new TableRow(this);

                TextView textGoal = new TextView(this);
                textGoal.setText(category.get(i));

                TextView textTarget = new TextView(this);
                textTarget.setText(String.valueOf(targets.get(i)));

                row.addView(textGoal);
                row.addView(textTarget);

                goalTable.addView(row);
            }

            DBHelper = new SpendingDBHelper(getApplicationContext(),SpendingDBHelper.DB_NAME,null,SpendingDBHelper.DB_VERSION);

            db = DBHelper.getWritableDatabase();//Database used to add the goal to the goals table

            ContentValues values = new ContentValues();
            values.put("AMOUNT", targetInput);
            String username = new String(((MyApplication) this.getApplication()).getUsername());//gets the username
            String selection = "USERNAME=? AND CATEGORY=?";
            String[] selectionArgs = {username, categorySelected};

            int count = db.update(
                    "GOAL_TABLE",
                    values,
                    selection,
                    selectionArgs
            );

        }

        else {//If there is not a goal for the category
            category.add(categorySelected);//adds new category the list of categories with goals
            targets.add(Integer.valueOf(targetInput));
            averageMonthly.add(0);

            //Adds a new row with the new goal to the table the user sees
            TableRow row = new TableRow(this);

            TextView textGoal = new TextView(this);
            textGoal.setText(categorySelected);

            TextView textTarget = new TextView(this);
            textTarget.setText(targetInput);

            TextView textAverageMonthly = new TextView(this);
            textAverageMonthly.setText("0");

            TextView textThisMonth = new TextView(this);
            textThisMonth.setText("0");

            row.addView(textGoal);
            row.addView(textTarget);
            row.addView(textAverageMonthly);
            row.addView(textThisMonth);

            goalTable.addView(row);

            DBHelper = new SpendingDBHelper(getApplicationContext(),SpendingDBHelper.DB_NAME,null,SpendingDBHelper.DB_VERSION);

            db = DBHelper.getWritableDatabase();//Database which will be updated with the new data

            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());//todays date
            String username = new String(((MyApplication) this.getApplication()).getUsername());

            ContentValues values = new ContentValues();
            values.put("AMOUNT",targetInput);
            values.put("CATEGORY", categorySelected);
            values.put("USERNAME", username);
            values.put("DATECREATED", date);
            values.put("PROGRESS", 0);
            values.put("AVERAGEMONTHLY", 0);

            long newRowId = db.insert("GOAL_TABLE", null, values);//Inserts the new data into the table
        }

    }

    public void GeneralGoalAdd() {//Adds the goal for the general category, much the same as the previous function
        TableLayout goalTable = (TableLayout)findViewById(R.id.goalTable);
        EditText input = (EditText) findViewById(R.id.generalTarget);
        String targetInput = input.getText().toString();

        int indexOfCat = category.indexOf("General");
        if(indexOfCat!=-1){
            targets.set(indexOfCat, Integer.parseInt(targetInput));

            int childCount = goalTable.getChildCount();

            // Remove all rows except the first one
            if (childCount > 1) {
                goalTable.removeViews(1, childCount - 1);
            }
            for (int i = 0; i < targets.size();i++) {
                TableRow row = new TableRow(this);

                TextView textGoal = new TextView(this);
                textGoal.setText(category.get(i));

                TextView textTarget = new TextView(this);
                textTarget.setText(String.valueOf(targets.get(i)));

                TextView textAverageMonthly = new TextView(this);
                textAverageMonthly.setText(String.valueOf(averageMonthly.get(i)));

                TextView textThisMonth = new TextView(this);
                textThisMonth.setText("0");

                row.addView(textGoal);
                row.addView(textTarget);
                row.addView(textAverageMonthly);
                row.addView(textThisMonth);

                goalTable.addView(row);
            }

            DBHelper = new SpendingDBHelper(getApplicationContext(),SpendingDBHelper.DB_NAME,null,SpendingDBHelper.DB_VERSION);

            db = DBHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("AMOUNT", targetInput);
            String username = new String(((MyApplication) this.getApplication()).getUsername());
            String selection = "USERNAME=? AND CATEGORY=?";
            String[] selectionArgs = {username, "General"};

            int count = db.update(
                    "GOAL_TABLE",
                    values,
                    selection,
                    selectionArgs
            );
            System.out.println(count);

        }

        else {
            category.add("General");
            targets.add(Integer.valueOf(targetInput));
            averageMonthly.add(0);

            TableRow row = new TableRow(this);

            TextView textGoal = new TextView(this);
            textGoal.setText("General");

            TextView textTarget = new TextView(this);
            textTarget.setText(targetInput);

            TextView textAverageMonthly = new TextView(this);
            textAverageMonthly.setText("0");

            TextView textThisMonth = new TextView(this);
            textThisMonth.setText("0");

            row.addView(textGoal);
            row.addView(textTarget);
            row.addView(textAverageMonthly);
            row.addView(textThisMonth);

            goalTable.addView(row);

            DBHelper = new SpendingDBHelper(getApplicationContext(),SpendingDBHelper.DB_NAME,null,SpendingDBHelper.DB_VERSION);

            db = DBHelper.getWritableDatabase();

            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            String username = new String(((MyApplication) this.getApplication()).getUsername());

            ContentValues values = new ContentValues();
            values.put("AMOUNT",targetInput);
            values.put("CATEGORY", "General");
            values.put("USERNAME", username);
            values.put("DATECREATED", date);
            values.put("PROGRESS", 0);
            values.put("AVERAGEMONTHLY", 0);

            long newRowId = db.insert("GOAL_TABLE", null, values);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {//saves the data the user has entered when the activity is destroyed
        super.onSaveInstanceState(outState);
        String username = new String(((MyApplication) this.getApplication()).getUsername());
        outState.putString("USERNAME", username);
        EditText catinput = (EditText) findViewById(R.id.categoryTarget);
        EditText input = (EditText) findViewById(R.id.generalTarget);
        outState.putString("catinput",catinput.getText().toString());
        outState.putString("input",input.getText().toString());


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {//retrieves the data the user has entered when the activity is restarted
        super.onRestoreInstanceState(savedInstanceState);

        String usernametext = new String(savedInstanceState.getString("USERNAME"));
        ((MyApplication) this.getApplication()).setUsername(usernametext);

        EditText catinput = (EditText) findViewById(R.id.categoryTarget);
        EditText input = (EditText) findViewById(R.id.generalTarget);

        catinput.setText(savedInstanceState.getString("catinput"));
        input.setText(savedInstanceState.getString("input"));

    }
}
