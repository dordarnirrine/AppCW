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

public class SpendingActivity extends AppCompatActivity { // The purpose of this activity is to display uploaded spending by the user when they search for it

    //Array lists to store information from the database accesable from across the whole class
    ArrayList<String> ids = new ArrayList<>();
    ArrayList<String> Amounts = new ArrayList<>();
    ArrayList<String> Locations = new ArrayList<>();

    TableLayout spendingTable; //Table the spending goes into

    private SpendingDBHelper DBHelper;
    private SQLiteDatabase db; //database which will later be assigned to the one we are using for the app

    @Override
    protected void onCreate(Bundle savedInstanceState) {//Runs on start up of the activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spending);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Spending Search"); //Sets the title at the top of the activity

        //Sets up button user clicks when they search with a listener
        Button searchButton = findViewById(R.id.SearchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search();
            }
        });

        DBHelper = new SpendingDBHelper(getApplicationContext(),SpendingDBHelper.DB_NAME,null,SpendingDBHelper.DB_VERSION);

        final SQLiteDatabase db = DBHelper.getReadableDatabase(); //Asigns the database to the one we want to use

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
        if(cursor != null) { //This will fill the Arraylists from the start with the data fetched from the database for use in the activity
            cursor.moveToFirst();//Moves cursor to initial position
            for (int i = 0; i < cursor.getCount();i++){//Passes through all items in the cursor

                //Fetches all the information we want from the database
                int passIndex = cursor.getColumnIndex(columns[0]);
                String id = String.valueOf(cursor.getInt(passIndex));
                passIndex = cursor.getColumnIndex(columns[1]);
                String amount = cursor.getString(passIndex);
                passIndex = cursor.getColumnIndex(columns[2]);
                String location = cursor.getString(passIndex);

                ids.add(id);
                Amounts.add(amount);
                Locations.add(location);
                cursor.moveToNext();//Moves to next item in the cursor
            }
            cursor.close();
        }

        TableLayout spendingTable = (TableLayout)findViewById(R.id.spendingTable);//Assigns the table the data will be put into

        for(int i = 0; i<ids.size(); i++){//Populates the table with the data
            TableRow row = new TableRow(this);//Creates a new row to put data into

            //Textviews given their data
            TextView id = new TextView(this);
            id.setText(ids.get(i));

            TextView Amount = new TextView(this);
            Amount.setText(Amounts.get(i));

            TextView Location = new TextView(this);
            Location.setText(Locations.get(i));

            TextView attached = new TextView(this);
            attached.setText("NOTSET");

            //Textviews inserted into the rows
            row.addView(id);
            row.addView(Amount);
            row.addView(Location);
            row.addView(attached);
            //Rows added to the table
            spendingTable.addView(row);
        }

    }

    public void Search() {//This function passes information onto the match and print function, depending on what radio button is selected
        EditText searchInput = (EditText)findViewById(R.id.spendingSearch);
        String searchText = searchInput.getText().toString();//The users search input

        RadioGroup searchRadio = (RadioGroup)findViewById(R.id.radioSearchGroup);
        int radioID = searchRadio.getCheckedRadioButtonId();
        View radioButton = searchRadio.findViewById(radioID);
        int idx = searchRadio.indexOfChild(radioButton);//The radio button which the user has selected

        switch(idx){
            case 0://Runs when ID radio button is selected
                MatchAndPrint(ids,searchText);
                break;
            case 1://Runs when Amount radio button is selected
                MatchAndPrint(Amounts,searchText);
                break;
            case 2://Runs when Locations button is selected
                MatchAndPrint(Locations,searchText);
                break;
            default:
                Log.d("Debug","switchdefault runs");
                break;
        }
    }

    public void MatchAndPrint(List<String> searchedContent, String searchText){ //This function finds the matching spendings with the search term, and inserts them into the table
        TableLayout spendingTable = findViewById(R.id.spendingTable);
        DeleteCurrent();//Removes current data from table

        List<Integer> matching = new ArrayList<>();//List of all matching items

        for(int i=0; i<searchedContent.size();i++){
            if(searchedContent.get(i).contains(searchText)){
                matching.add(i); //Added to list if and only if the matchings are found in the searched content

            }
        }

        if(matching.size()!=0) {//adds all of the matching spending uploads to the table
            for (int i = 0; i < ids.size(); i++) {
                for(int y = 0; y < matching.size();y++) {
                    if (i == matching.get(y)) {
                        TableRow row = new TableRow(this);

                        //Inserts data into the text views
                        TextView id = new TextView(this);
                        id.setText(ids.get(i));

                        TextView Amount = new TextView(this);
                        Amount.setText(Amounts.get(i));

                        TextView Location = new TextView(this);
                        Location.setText(Locations.get(i));

                        TextView attached = new TextView(this);
                        attached.setText("NOTSET");

                        //Adds text views into the row
                        row.addView(id);
                        row.addView(Amount);
                        row.addView(Location);
                        row.addView(attached);
                         //Adds row to the table
                        spendingTable.addView(row);
                    }
                }
            }
        }


    }

    public void DeleteCurrent() {//Deletes all data from the table other than the headings
        TableLayout spendingTable = findViewById(R.id.spendingTable);
        int childCount = spendingTable.getChildCount();//Amount of rows in the table

        // Remove all rows except the first one
        if (childCount > 1) {
            spendingTable.removeViews(1, childCount - 1);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) { //saves data when app is closed
        super.onSaveInstanceState(outState);
        EditText searchInput = (EditText)findViewById(R.id.spendingSearch);
        String searchText = searchInput.getText().toString();
        //saves users current search input and their username
        outState.putString("SEARCHTEXT",searchText);
        outState.putString("USERNAME", ((MyApplication) this.getApplication()).getUsername());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {//retrieves stored data on restart
        super.onRestoreInstanceState(savedInstanceState);
        EditText searchInput = (EditText)findViewById(R.id.spendingSearch);
        //Resets the users search input and username
        searchInput.setText(savedInstanceState.getString("SEARCHTEXT"));
        ((MyApplication) this.getApplication()).setUsername(savedInstanceState.getString("USERNAME"));

    }


}
