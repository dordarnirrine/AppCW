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

public class GoalsActivity extends AppCompatActivity {

    private SpendingDBHelper DBHelper;
    private SQLiteDatabase db;

    List<Integer> targets = new ArrayList<>();
    List<String> category = new ArrayList<>();
    List<Integer> averageMonthly = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.categorySelect);
        //create a list of items for the spinner.
        String[] items = new String[]{"1", "2", "three"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        final SQLiteDatabase db = DBHelper.getReadableDatabase();

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
            for (int i = 0; i < cursor.getCount();i++) {
                int passIndex = cursor.getColumnIndex("AMOUNT");
                targets.add(cursor.getInt(passIndex));
                passIndex = cursor.getColumnIndex("CATEGORY");
                category.add(cursor.getString(passIndex));
                passIndex = cursor.getColumnIndex("AVERAGEMONTHLY");
                averageMonthly.add(cursor.getInt(passIndex));

                TableLayout goalTable = findViewById(R.id.goalTable);

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
                cursor.moveToNext();
            }
            cursor.close();

        }
    }



    public void CategoryGoalAdd() {
        TableLayout goalTable = (TableLayout)findViewById(R.id.goalTable);
        EditText input = (EditText) findViewById(R.id.categoryTarget);
        String targetInput = input.getText().toString();
        Spinner spinner = (Spinner) findViewById(R.id.categorySelect);
        String categorySelected = spinner.getSelectedItem().toString();
        System.out.println(category);

        int indexOfCat = category.indexOf(categorySelected);
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
            String[] selectionArgs = {username, categorySelected};

            int count = db.update(
                    "GOAL_TABLE",
                    values,
                    selection,
                    selectionArgs
            );
            System.out.println(count);

        }

        else {
            category.add(categorySelected);
            targets.add(Integer.valueOf(targetInput));
            averageMonthly.add(0);

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

            db = DBHelper.getWritableDatabase();

            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            String username = new String(((MyApplication) this.getApplication()).getUsername());

            ContentValues values = new ContentValues();
            values.put("AMOUNT",targetInput);
            values.put("CATEGORY", categorySelected);
            values.put("USERNAME", username);
            values.put("DATECREATED", date);
            values.put("PROGRESS", 0);
            values.put("AVERAGEMONTHLY", 0);

            long newRowId = db.insert("GOAL_TABLE", null, values);
        }

    }

    public void GeneralGoalAdd() {
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

}
