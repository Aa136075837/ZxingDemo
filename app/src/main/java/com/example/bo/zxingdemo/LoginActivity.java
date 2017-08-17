package com.example.bo.zxingdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import com.coorchice.library.SuperTextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mPhone;
    private EditText mCheckCode;
    private SuperTextView mGetCheckCode;
    private Handler mHandler = new Handler ();
    private SuperTextView mTest;

    @Override protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_login);
        initView ();
        initListener ();
    }

    private void initListener () {
        mGetCheckCode.setOnClickListener (this);
        mTest.setOnClickListener (this);
        //mGetCheckCode.setAdjuster (new SuperTextView.DefaultAdjuster ()).setAutoAdjust (true).startAnim ();

        //mHandler.postDelayed (new Runnable () {
        //    @Override public void run () {
        //        mGetCheckCode.stopAnim ();
        //    }
        //}, 3 * 1000);
    }

    private void initView () {
        mPhone = (EditText) findViewById (R.id.phone);
        mCheckCode = (EditText) findViewById (R.id.check_code);
        mGetCheckCode = (SuperTextView) findViewById (R.id.get_check_code);
        mTest = (SuperTextView) findViewById (R.id.test);
    }

    @Override public void onClick (View v) {
        switch (v.getId ()) {
            case R.id.test:
                startActivity (new Intent (this, MainActivity.class));
                break;
            case R.id.get_check_code:

                break;
            case R.id.login:

                break;
        }
    }
}
