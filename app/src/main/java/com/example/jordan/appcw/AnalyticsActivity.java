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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class AnalyticsActivity extends AppCompatActivity {//this activity shows the users spending on a graph


    private SpendingDBHelper DBHelper;
    private android.database.sqlite.SQLiteDatabase db;//database accessed to retrieve information for the graph

    //Data retrieved from the database is stored in these lists for manipulation later
    List<Integer> ids = new ArrayList<>();
    List<String> Amounts = new ArrayList<>();
    List<String> Categories = new ArrayList<>();
    List<Date> DateSubmitted = new ArrayList<>();
    Date userJoined;//Used later for the farthest left label on the x axis


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("General Analytics");//Sets the title for the page


        DBHelper = new SpendingDBHelper(getApplicationContext(),SpendingDBHelper.DB_NAME,null,SpendingDBHelper.DB_VERSION);

        final android.database.sqlite.SQLiteDatabase db = DBHelper.getReadableDatabase();//Gets the database used to read the data for the graph

        String[] columns = new String[]{"_ID","AMOUNT","CATEGORY","DATESUBMITTED"};
        String columnsWhere = new String("USERNAME=?");
        String[] username = new String[]{((MyApplication) this.getApplication()).getUsername()};

        android.database.Cursor cursor = db.query(
                "SPEND_TABLE", // Table to Query
                columns,
                columnsWhere, // Columns for the "where" clause
                username, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        if(cursor != null) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {//Saves the data from the database to the lists used to manipulate the data

                int passIndex = cursor.getColumnIndex(columns[0]);
                String id = String.valueOf(cursor.getInt(passIndex));
                passIndex = cursor.getColumnIndex(columns[1]);
                String amount = cursor.getString(passIndex);
                passIndex = cursor.getColumnIndex(columns[2]);
                String category = cursor.getString(passIndex);
                passIndex = cursor.getColumnIndex(columns[3]);
                String datesubmitted = cursor.getString(passIndex);
                Date date = null;

                try {//turns the date in the database into date format because sqlite doesn't support date format
                    date = new SimpleDateFormat("yyyy-MM-dd").parse(datesubmitted);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                ids.add(Integer.parseInt(id));
                Amounts.add(amount);
                Categories.add(category);
                DateSubmitted.add(date);
                cursor.moveToNext();//cursor moves onto next item

            }
            cursor.close();
        }

        columns = new String[]{"DATEJOINED"};
        columnsWhere = new String("USERNAME=?");

        android.database.Cursor cursor2 = db.query(
                    "USERS_TABLE", // Table to Query
                    columns,
                    columnsWhere, // Columns for the "where" clause
                    username, // Values for the "where" clause
                    null, // columns to group by
                    null, // columns to filter by row groups
                    null // sort order
        );

        //Gets the date that the user joined for the farthest left label
        if(cursor2 != null) {
            cursor2.moveToFirst();
            for (int i = 0; i < cursor2.getCount(); i++) {
                int passIndex = cursor2.getColumnIndex(columns[0]);
                String date = cursor2.getString(passIndex);
                try {
                    userJoined = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                    System.out.println(date);
                }

                cursor2.moveToNext();
            }
            cursor2.close();
        }
        GraphView graph = (GraphView)findViewById(R.id.graph);//sets up the graph view

        DataPoint[] dataPoints = new DataPoint[ids.size()];
        for(int i = 0; i<ids.size();i++) {
            dataPoints[i] = new DataPoint(DateSubmitted.get(i),Integer.valueOf(Amounts.get(i)));//These are all the data points added to the graph
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);//adds datapoints to the graph

        graph.addSeries(series);

        // set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space

        // set manual x bounds to have nice steps
        graph.getViewport().setMinX(userJoined.getTime());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        graph.getViewport().setMaxX(date.getTime());
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinY(0);

        // as we use dates as labels, the human rounding to nice readable numbers
        // is not necessary
        graph.getGridLabelRenderer().setHumanRounding(false);

    }

}
