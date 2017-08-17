package com.example.bo.zxingdemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.EditText;
import com.example.bo.zxingdemo.R;

/**
 * Created by bo on 2017/3/29.
 */

public class MyEditText extends EditText {

    private boolean mBoolean;
    private Drawable mCompoundDrawable;

    public MyEditText (Context context) {
        this (context,null);
    }

    public MyEditText (Context context, AttributeSet attrs) {
        this (context, attrs,0);
    }

    public MyEditText (Context context, AttributeSet attrs, int defStyleAttr) {
        super (context, attrs, defStyleAttr);
        init( context,  attrs, defStyleAttr);
    }

    private void init (Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes (attrs, R.styleable.MyEditText);
        mBoolean = typedArray.getBoolean (R.styleable.MyEditText_isContent, false);

        Drawable[] compoundDrawables = getCompoundDrawables ();
        mCompoundDrawable = compoundDrawables[2];
        setDrawableVisible();
        typedArray.recycle ();
    }

    private void setDrawableVisible () {
        mCompoundDrawable.setVisible (mBoolean,true);
    }
}
