package com.example.jordan.appcw;

import android.app.Application;

public class MyApplication extends Application {//This class is used to create a username variable accessible from anywhere on the app
    private String username;

    public String getUsername() {
        return username;
    }//getter function for the username

    public void setUsername(String username){
        this.username = username;
    }//setter function for the usrname
}
