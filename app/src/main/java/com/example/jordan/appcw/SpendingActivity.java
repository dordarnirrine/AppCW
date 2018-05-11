package com.example.jordan.appcw;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SpendingActivity extends AppCompatActivity {

    List<String> ids = new ArrayList<>();
    List<String> Amounts = new ArrayList<>();
    List<String> Locations = new ArrayList<>();
    List<String> AttachedReceipt = new ArrayList<>();

    TableLayout spendingTable;

    private SpendingDBHelper DBHelper;
    private SQLiteDatabase db;

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

        DBHelper = new SpendingDBHelper(getApplicationContext(),SpendingDBHelper.DB_NAME,null,SpendingDBHelper.DB_VERSION);

        final SQLiteDatabase db = DBHelper.getReadableDatabase();

        String[] columns = {
                "_ID", "AMOUNT", "LOCATION"
        };

        String columnsWhere = "USERNAME=?";
        String[] username = new String[]{((MyApplication) this.getApplication()).getUsername()};

        // A cursor is your primary interface to the query results.
        Cursor cursor = db.query(
                "SPEND_TABLE", // Table to Query
                columns,
                columnsWhere, // Columns for the "where" clause
                username, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        while(cursor.moveToNext()){
            int passIndex = cursor.getColumnIndex(columns[0]);
            String id = String.valueOf(cursor.getInt(passIndex));
            passIndex = cursor.getColumnIndex(columns[1]);
            String amount = cursor.getString(passIndex);
            passIndex = cursor.getColumnIndex(columns[2]);
            String location = cursor.getString(passIndex);
            
            System.out.println(location);
            ids.add(id);
            Amounts.add(amount);
            Locations.add(location);
        }
        cursor.close();

        TableLayout spendingTable = (TableLayout)findViewById(R.id.spendingTable);

        for(int i = 0; i<ids.size(); i++){
            TableRow row = new TableRow(this);

            TextView id = new TextView(this);
            id.setText(ids.get(i));

            TextView Amount = new TextView(this);
            Amount.setText(Amounts.get(i));

            TextView Location = new TextView(this);
            Location.setText(Locations.get(i));

            TextView attached = new TextView(this);
            attached.setText("NOTSET");

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
        View radioButton = searchRadio.findViewById(radioID);
        int idx = searchRadio.indexOfChild(radioButton);
        Log.d("Debug","Search runs");
        System.out.println(idx);
        switch(idx){
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

    public void MatchAndPrint(List<String> searchedContent, String searchText){
        TableLayout spendingTable = findViewById(R.id.spendingTable);
        DeleteCurrent();

        List<Integer> matching = new ArrayList<>();
        Log.d("Debug","MatchAndPrint runs");

        for(int i=0; i<searchedContent.size();i++){
            if(searchedContent.get(i).contains(searchText)){
                matching.add(i);

            }
        }

        if(matching.size()!=0) {
            for (int i = 0; i < ids.size(); i++) {
                for(int y = 0; y < matching.size();y++) {
                    if (i == matching.get(y)) {
                        TableRow row = new TableRow(this);

                        TextView id = new TextView(this);
                        id.setText(ids.get(i));

                        TextView Amount = new TextView(this);
                        Amount.setText(Amounts.get(i));

                        TextView Location = new TextView(this);
                        Location.setText(Locations.get(i));

                        TextView attached = new TextView(this);
                        attached.setText("NotSet");

                        row.addView(id);
                        row.addView(Amount);
                        row.addView(Location);
                        row.addView(attached);

                        spendingTable.addView(row);
                    }
                }
            }
        }


    }

    public void DeleteCurrent() {
        TableLayout spendingTable = findViewById(R.id.spendingTable);
        spendingTable.removeAllViews();

    }

}
