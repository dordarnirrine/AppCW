package com.example.jordan.appcw;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class AnalyticsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    GraphView graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.spinner1);
        //create a list of items for the spinner.
        String[] items = new String[]{"1", "2", "three"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        graph = findViewById(R.id.graph);
        switch (pos){
            case 0:

                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                        new DataPoint(2018-05-7, 1),
                        new DataPoint(2018-05-8, 5),
                        new DataPoint(2018-05-9, 3)
                });
                graph.addSeries(series);
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                    break;

        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

}
