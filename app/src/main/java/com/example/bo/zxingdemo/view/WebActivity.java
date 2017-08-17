package com.example.bo.zxingdemo.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import com.example.bo.zxingdemo.R;

public class WebActivity extends AppCompatActivity {

    @Override protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_web);
        WebView webView = (WebView) findViewById (R.id.webView);
        //webView.loadUrl ();
    }
}
