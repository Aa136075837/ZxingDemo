package com.example.bo.zxingdemo;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.zxing.WriterException;
import com.google.zxing.activity.CaptureActivity;
import com.google.zxing.common.BitmapUtils;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final int REQ_CODE = 111;
    private EditText mEt;
    private Button mBtn1;
    private Button mBtn2;
    private Button mBtn3;
    private Button mBtn4;
    private Button mBtn5;
    private Button mLinechart;
    private ImageView mImage;

    @Override protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        initView();
        initActionBar();
        initListener();
    }

    private void initActionBar () {
        ActionBar actionBar = getActionBar ();
    }

    private void initListener () {
        mBtn1.setOnClickListener (this);
        mBtn2.setOnClickListener (this);
        mBtn3.setOnClickListener (this);
        mBtn4.setOnClickListener (this);
        mBtn5.setOnClickListener (this);
        mLinechart.setOnClickListener (this);
    }

    private void initView () {
        mEt = (EditText) findViewById (R.id.editText);
        mBtn1 = (Button) findViewById (R.id.button);
        mBtn2 = (Button) findViewById (R.id.button2);
        mBtn3 = (Button) findViewById (R.id.button3);
        mBtn4 = (Button) findViewById (R.id.button4);
        mBtn5 = (Button) findViewById (R.id.button5);
        mLinechart = (Button) findViewById (R.id.btn_linechart);

        mImage = (ImageView) findViewById (R.id.img_);
    }

    @Override public void onClick (View v) {
        switch (v.getId ()){
            case R.id.button:
                String content = mEt.getText().toString().trim();
                if (!TextUtils.isEmpty (content)){
                    Bitmap bitmap = null;
                    try {
                        bitmap = BitmapUtils.create2DCode(content);
                        mImage.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText (this, "输入内容为空", Toast.LENGTH_SHORT).show ();
                }
                break;
            case R.id.button2:
                startActivityForResult (new Intent (MainActivity.this, CaptureActivity.class),REQ_CODE);
                break;
            case R.id.button3:
                startActivity (new Intent (MainActivity.this, EmojiActivity.class));
                break;
            case R.id.button4:
                startActivity (new Intent (MainActivity.this,LanguageActivity.class));
                break;
            case R.id.button5:
                startActivity (new Intent (MainActivity.this,EmojiconActivity.class));
                break;
            case R.id.btn_linechart:
                startActivity (new Intent (MainActivity.this,LineChartActivity.class));
                break;
        }
    }

    @Override protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult (requestCode, resultCode, data);

        if(requestCode == REQ_CODE){
            String result = data.getStringExtra(CaptureActivity.SCAN_QRCODE_RESULT);
            Bitmap bitmap = data.getParcelableExtra(CaptureActivity.SCAN_QRCODE_BITMAP);

            mEt.setText (result);
            if (null != bitmap){
                mImage.setImageBitmap (bitmap);
            }

        }

    }

    //String result = data.getStringExtra(CaptureActivity.SCAN_QRCODE_RESULT);
    //Bitmap bitmap = data.getParcelableExtra(CaptureActivity.SCAN_QRCODE_BITMAP);
    //
    //mTvResult.setText("扫码结果："+result);
    //showToast("扫码结果：" + result);
    //if(bitmap != null){
    //    mImageCallback.setImageBitmap(bitmap);//现实扫码图片
    //}
}
