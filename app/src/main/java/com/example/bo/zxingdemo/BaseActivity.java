package com.example.bo.zxingdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class BaseActivity extends AppCompatActivity {

    @Override protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_base);
        initToorBar ();
    }

    private void initToorBar () {
        Toolbar toolbar = (Toolbar) findViewById (R.id.toorbar);
        setSupportActionBar (toolbar);
    }
}
