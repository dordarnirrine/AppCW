package com.example.jordan.appcw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class MyWebView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_web_view);

        String url="file:///android_asset/WebView.html";
        WebView home = (WebView) findViewById(R.id.mywebview);
        home.loadUrl(url);

    }
}
