package com.example.bo.zxingdemo;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Locale;

public class LanguageActivity extends BaseActivity implements TextToSpeech.OnInitListener {

    private Button mBtnPlay;
    private EditText mEditText;
    private TextToSpeech mTts;

    @Override protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_language);
        initView ();
        initListener ();
    }

    private void initListener () {
        mBtnPlay.setOnClickListener (new View.OnClickListener () {
            @Override public void onClick (View v) {
                mTts.setPitch (1.0f);
                mTts.setSpeechRate (1.0f);
                String s = mEditText.getText ().toString ().trim ();
                if (!TextUtils.isEmpty (s)) {
                    mTts.speak (s, TextToSpeech.QUEUE_FLUSH, null);
                } else {
                    Toast.makeText (LanguageActivity.this, "输入内容为空", Toast.LENGTH_SHORT).show ();
                }
            }
        });
    }

    private void initView () {
        mBtnPlay = (Button) findViewById (R.id.lang_btn);
        mEditText = (EditText) findViewById (R.id.lang_ed);
        mTts = new TextToSpeech (this, this);
    }

    @Override public void onInit (int status) {
        int resultChinese = mTts.setLanguage (Locale.CHINESE);
        int restltUS = mTts.setLanguage (Locale.US);
        if (resultChinese == TextToSpeech.LANG_MISSING_DATA
            || resultChinese == TextToSpeech.LANG_NOT_SUPPORTED
            || restltUS == TextToSpeech.LANG_MISSING_DATA
            || restltUS == TextToSpeech.LANG_NOT_SUPPORTED) {
            Toast.makeText (this, "数据丢失或不支持", Toast.LENGTH_SHORT).show ();
        }
    }
}
