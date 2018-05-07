package com.example.jordan.appcw;

import android.content.Intent;
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

public class NonUploadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_upload);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView id = (TextView)findViewById(R.id.expendID);
        id.setText("#1");

        Spinner categorydropdown = (Spinner)findViewById(R.id.categoryspinner);

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

        //getNameScreenIntent.putExtra("weatherinfo", weatherInfo[i]);

        startActivity(doneGoHome);
    }

}
