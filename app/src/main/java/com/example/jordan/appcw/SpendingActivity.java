package com.example.jordan.appcw;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class SpendingActivity extends AppCompatActivity {

    String[] ids = new String[]{"1", "2", "3"};
    String[] Amounts = new String[]{"10.00", "2.50","3.70"};
    String[] Locations = new String[]{"LufbraSU","LondisCharnwood","Dominos"};
    String[] AttachedReceipt = new String[]{"Not attached", "Not attached", "Not attached"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spending);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button searchButton = findViewById(R.id.SearchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search();
            }
        });



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

    public void Search() {
        EditText searchInput = (EditText)findViewById(R.id.spendingSearch);
        String searchText = searchInput.getText().toString();

        RadioGroup searchRadio = (RadioGroup)findViewById(R.id.radioSearchGroup);
        int radioID = searchRadio.getCheckedRadioButtonId();
        Log.d("Debug","Search runs");
        System.out.println(radioID);
        switch(radioID){
            case 0:
                MatchAndPrint(ids,searchText);
                Log.d("Debug","switchcase0 runs");
                break;
            case 1:
                MatchAndPrint(Amounts,searchText);
                Log.d("Debug","switchcase1 runs");
                break;
            case 2:
                MatchAndPrint(Locations,searchText);
                Log.d("Debug","switchcase2 runs");
                break;
            default:
                Log.d("Debug","switchdefault runs");
                break;
        }
    }

    public void MatchAndPrint(String[] searchedContent,String searchText){
        int[] matching = new int[]{};
        int positionHolder = 0;
        Log.d("Debug","MatchAndPrint runs");

        for(int i=0; i<searchedContent.length;i++){
            if(searchedContent[i].contains(searchText)){
                matching[positionHolder] = i;
                positionHolder++;

            }
        }

        TableLayout spendingTable = (TableLayout)findViewById(R.id.spendingTable);

        for(int i = 0; i<ids.length; i++){
            if(i==matching[i]) {
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

}
