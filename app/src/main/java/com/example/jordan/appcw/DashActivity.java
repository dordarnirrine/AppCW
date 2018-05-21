package com.example.jordan.appcw;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DashActivity extends AppCompatActivity {//This activity is the navigation hub for the app

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        if(savedInstanceState!=null){
            ((MyApplication) this.getApplication()).setUsername(savedInstanceState.getString("USERNAME"));
        }

        //Initialises and assigns all of the buttons
        ImageButton analyticsButton = findViewById(R.id.analyticsButton);
        ImageButton spendingButton = findViewById(R.id.spendingButton);
        ImageButton nonUploadButton = findViewById(R.id.nonUploadButton);
        ImageButton goalsButton = findViewById(R.id.goalsButton);
        Button editProfileButton = findViewById(R.id.editProfileButton);
        Button logOutButton = findViewById(R.id.logOutButton);
        Button userguide = findViewById(R.id.userguide);
        Button internetbutton = findViewById(R.id.internetbutton2);

        getSupportActionBar().setTitle("Dashboard");

        //Listeners for when the users click on the navigation buttons
        analyticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterAnalytics();
            }
        });

        spendingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterSpending();
            }
        });

        nonUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterUpload();
            }
        });

        goalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterGoals();
            }
        });

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterEditProfile();
            }
        });

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });

        userguide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserGuide();
            }
        });

        internetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open();
            }
        });


    }

    void open() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.co.uk/search?q=Budget+advice&oq=Budget+advice&aqs=chrome..69i57j0l5.2699j0j1&sourceid=chrome&ie=UTF-8"));
        startActivity(browserIntent);
    }

    public void openUserGuide() {
        Intent gotouserguide = new Intent(this, MyWebView.class);
        startActivity(gotouserguide);
    }

    public void enterAnalytics() {//Takes user from dashboard to analytics
        Intent gotoAnalIntent = new Intent(this,
                AnalyticsActivity.class);

        startActivity(gotoAnalIntent);

    }


    public void enterSpending() {//Takes user from dashboard to the spending search
        Intent gotoSpendingIntent = new Intent(this,
                SpendingActivity.class);

        startActivity(gotoSpendingIntent);

    }

    public void enterUpload() {//Takes user from dashboard to the spending upload page
        Intent gotononUploadIntent = new Intent(this,
                UploadActivity.class);

        startActivity(gotononUploadIntent);

    }

    public void enterGoals() {//Takes user from dashboard to the goal submission page
        Intent gotoGoalsIntent = new Intent(this,
                GoalsActivity.class);

        startActivity(gotoGoalsIntent);

    }

    public void enterEditProfile() {//Takes user from dashboard to the edit profile page
        Intent gotoEditProfileIntent = new Intent(this,
                EditProfileActivity.class);

        startActivity(gotoEditProfileIntent);

    }

    public void logOut() {//Logs user out
        Intent logOutIntent = new Intent(this,
                LoginActivity.class);
        ((MyApplication) this.getApplication()).setUsername(null);//Sets username to null because the user is now logged out

        startActivity(logOutIntent);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {//Saves the users usersname on destroy
        super.onSaveInstanceState(outState);
        String username = new String(((MyApplication) this.getApplication()).getUsername());
        outState.putString("USERNAME", username);

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {//Restores the users username on restart
        super.onRestoreInstanceState(savedInstanceState);

        TextView username = findViewById(R.id.usernameText1);
        String usernametext = new String(savedInstanceState.getString("USERNAME"));

        ((MyApplication) this.getApplication()).setUsername(usernametext);

        username.setText("Username:" + usernametext);



    }

}

