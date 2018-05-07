package com.example.jordan.appcw;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class SpendingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spending);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String[] ids = new String[]{"1", "2", "3"};
        String[] Amounts = new String[]{"10.00", "2.50","3.70"};
        String[] Locations = new String[]{"LufbraSU","LondisCharnwood","Dominos"};
        String[] AttachedReceipt = new String[]{"Not attached", "Not attached", "Not attached"};

        TableLayout spendingTable = (TableLayout)findViewById(R.id.spendingTable);

        for(int i = 0; i<ids.length; i++){
            TableRow row = new TableRow(this);

            TextView id = new TextView(this);
            id.setText(ids[i]);

            TextView Amount = new TextView(this);
            Amount.setText(Amounts[i]);

            TextView Location = new TextView(this);
            Location.setText(Locations[i]);

            TextView attached = new TextView(this);
            attached.setText(AttachedReceipt[i]);

            row.addView(id);
            row.addView(Amount);
            row.addView(Location);
            row.addView(attached);

            spendingTable.addView(row);
        }

    }

}
