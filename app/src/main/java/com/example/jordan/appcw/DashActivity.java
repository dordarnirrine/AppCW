package com.example.jordan.appcw;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        Button analyticsButton = findViewById(R.id.analyticsButton);
        Button uploadButton = findViewById(R.id.uploadButton);
        Button spendingButton = findViewById(R.id.spendingButton);
        Button nonUploadButton = findViewById(R.id.nonUploadButton);
        Button goalsButton = findViewById(R.id.goalsButton);
        Button editProfileButton = findViewById(R.id.editProfileButton);
        Button logOutButton = findViewById(R.id.logOutButton);

        //Listener for when the enter button on login page is clicked
        analyticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterAnalytics();
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterUpload();
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
                enternonUpload();
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
    }

    public void enterAnalytics() {//Takes user from login page of APP to the dashboard
        Intent gotoAnalIntent = new Intent(this,
                AnalyticsActivity.class);

        //getNameScreenIntent.putExtra("weatherinfo", weatherInfo[i]);

        startActivity(gotoAnalIntent);

    }

    public void enterUpload() {//Takes user from login page of APP to the dashboard
        Intent gotoUploadIntent = new Intent(this,
                UploadActivity.class);

        //getNameScreenIntent.putExtra("weatherinfo", weatherInfo[i]);

        startActivity(gotoUploadIntent);

    }

    public void enterSpending() {//Takes user from login page of APP to the dashboard
        Intent gotoSpendingIntent = new Intent(this,
                SpendingActivity.class);

        //getNameScreenIntent.putExtra("weatherinfo", weatherInfo[i]);

        startActivity(gotoSpendingIntent);

    }

    public void enternonUpload() {//Takes user from login page of APP to the dashboard
        Intent gotononUploadIntent = new Intent(this,
                NonUploadActivity.class);

        //getNameScreenIntent.putExtra("weatherinfo", weatherInfo[i]);

        startActivity(gotononUploadIntent);

    }

    public void enterGoals() {//Takes user from login page of APP to the dashboard
        Intent gotoGoalsIntent = new Intent(this,
                GoalsActivity.class);

        //getNameScreenIntent.putExtra("weatherinfo", weatherInfo[i]);

        startActivity(gotoGoalsIntent);

    }

    public void enterEditProfile() {//Takes user from login page of APP to the dashboard
        Intent gotoEditProfileIntent = new Intent(this,
                EditProfileActivity.class);

        //getNameScreenIntent.putExtra("weatherinfo", weatherInfo[i]);

        startActivity(gotoEditProfileIntent);

    }

    public void logOut() {//Takes user from login page of APP to the dashboard
        Intent logOutIntent = new Intent(this,
                LoginActivity.class);

        //getNameScreenIntent.putExtra("weatherinfo", weatherInfo[i]);

        startActivity(logOutIntent);

    }
}

