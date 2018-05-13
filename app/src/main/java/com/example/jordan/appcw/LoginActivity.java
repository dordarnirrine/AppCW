package com.example.jordan.appcw;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {

    private SpendingDBHelper DBHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button enterButton = findViewById(R.id.enterButton);

        DBHelper = new SpendingDBHelper(getApplicationContext(),SpendingDBHelper.DB_NAME,null,SpendingDBHelper.DB_VERSION);


        final SQLiteDatabase db = DBHelper.getReadableDatabase();

        //DBHelper.Testdata();

        //Listener for when the enter button on login page is clicked
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterApp(db);
            }
        });

    }

    public void enterApp(SQLiteDatabase db) {//Takes user from login page of APP to the dashboard
        Intent gotoDashIntent = new Intent(this,
                DashActivity.class);

        EditText usernameInput = findViewById(R.id.usernameInput);
        EditText passwordInput = findViewById(R.id.passwordIInput);
        TextView loginDescrip = findViewById(R.id.loginDescrip);
        String[] username = new String[]{usernameInput.getText().toString()};
        String password = passwordInput.getText().toString();



        //load data from SQLite database
        String[] columns = {
                "PASSWORD"
        };

        String columnsWhere = "USERNAME=?";

        // A cursor is your primary interface to the query results.
        Cursor cursor = db.query(
                "USERS_TABLE", // Table to Query
                columns,
                columnsWhere, // Columns for the "where" clause
                username, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );
        if(cursor.moveToFirst()) {
            int passIndex = cursor.getColumnIndex("PASSWORD");
            String dbpassword = cursor.getString(passIndex);
            System.out.println(dbpassword);
            System.out.println(password);

            if (password.equals(dbpassword)) {
                ((MyApplication) this.getApplication()).setUsername(username[0]);
                startActivity(gotoDashIntent);
            }

            else {
                loginDescrip.setText("Password Incorrect");
                loginDescrip.setTextColor(Color.RED);
            }
        }
        else {
            loginDescrip.setText("Username does not exist");
            loginDescrip.setTextColor(Color.RED);
        }
        cursor.close();
    }


}
