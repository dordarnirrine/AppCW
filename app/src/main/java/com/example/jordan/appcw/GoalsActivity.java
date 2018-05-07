package com.example.jordan.appcw;

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

public class GoalsActivity extends AppCompatActivity {



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
    }



    public void CategoryGoalAdd() {
        TableLayout goalTable = (TableLayout)findViewById(R.id.goalTable);
        EditText input = (EditText) findViewById(R.id.categoryTarget);
        String target = input.getText().toString();
        Spinner spinner = (Spinner) findViewById(R.id.categorySelect);
        String category = spinner.getSelectedItem().toString();
        System.out.println(category);

        TableRow row = new TableRow(this);

        TextView textGoal = new TextView(this);
        textGoal.setText(category);

        TextView textTarget = new TextView(this);
        textTarget.setText(target);

        TextView textAverageMonthly = new TextView(this);
        textAverageMonthly.setText("0");

        TextView textThisMonth = new TextView(this);
        textThisMonth.setText("0");

        row.addView(textGoal);
        row.addView(textTarget);
        row.addView(textAverageMonthly);
        row.addView(textThisMonth);

        goalTable.addView(row);
    }

    public void GeneralGoalAdd() {
        TableLayout goalTable = (TableLayout)findViewById(R.id.goalTable);
        EditText input = (EditText) findViewById(R.id.generalTarget);
        String target = input.getText().toString();

        TableRow row = new TableRow(this);

        TextView textGoal = new TextView(this);
        textGoal.setText("General");

        TextView textTarget = new TextView(this);
        textTarget.setText(target);

        TextView textAverageMonthly = new TextView(this);
        textAverageMonthly.setText("0");

        TextView textThisMonth = new TextView(this);
        textThisMonth.setText("0");

        row.addView(textGoal);
        row.addView(textTarget);
        row.addView(textAverageMonthly);
        row.addView(textThisMonth);

        goalTable.addView(row);

    }

}
