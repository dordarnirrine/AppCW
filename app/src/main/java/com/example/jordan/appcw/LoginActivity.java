package com.example.jordan.appcw;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button enterButton = findViewById(R.id.enterButton);

        final Context context = getApplicationContext();

        //Listener for when the enter button on login page is clicked
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterApp();
            }
        });
    }

    public void enterApp() {//Takes user from login page of APP to the dashboard
        Intent gotoDashIntent = new Intent(this,
                DashActivity.class);

        //getNameScreenIntent.putExtra("weatherinfo", weatherInfo[i]);

        startActivity(gotoDashIntent);
    }


}
