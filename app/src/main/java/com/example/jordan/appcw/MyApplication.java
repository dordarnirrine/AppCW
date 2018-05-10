package com.example.jordan.appcw;

import android.app.Application;

public class MyApplication extends Application {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }
}
