package com.example.bo.zxingdemo.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import com.example.bo.zxingdemo.R;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * @author bo.
 * @Date 2017/4/14.
 * @desc
 */

public class LineChartView extends View {

    private int max = 100, min = 0;
    private String startTime = "2017-04-14";
    private String endTime = "2017-04-15";

    private int mAxisColor;
    private int mChartColor;
    private int mBgColor;
    private int mTextColor;
    private float mTextSize;
    private float mAxisWidth;
    private Paint mAxisPaint;
    private Paint mTextPaint;
    private Paint mChartPaint;
    private Path mPath;
    private Path mChartPath;
    private int mWidth;
    private int mHeight;
    private List<ItemBean> mItems;
    private int mMargin;
    private int mTimeWidth;
    private float mXOrigin;
    private float mYOrigin;
    private float mXSpacing;
    private float mYSpacing;
    private Paint mShaderPaint;
    private float mProgress;
    private int[] mGradientColor;

    public int[] getGradientColor () {
        return mGradientColor;
    }

    public void setGradientColor (int[] gradientColor) {
        mGradientColor = gradientColor;
    }

    public List<ItemBean> getItems () {
        return mItems;
    }

    public void setItems (List<ItemBean> items) {
        mItems = items;
    }

    public int getMax () {
        return max;
    }

    public void setMax (int max) {
        this.max = max;
    }

    public int getMin () {
        return min;
    }

    public void setMin (int min) {
        this.min = min;
    }

    public String getStartTime () {
        return startTime;
    }

    public void setStartTime (String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime () {
        return endTime;
    }

    public void setEndTime (String endTime) {
        this.endTime = endTime;
    }

    public int getAxisColor () {
        return mAxisColor;
    }

    public void setAxisColor (int axisColor) {
        mAxisColor = axisColor;
    }

    public int getChartColor () {
        return mChartColor;
    }

    public void setChartColor (int chartColor) {
        mChartColor = chartColor;
    }

    public int getBgColor () {
        return mBgColor;
    }

    public void setBgColor (int bgColor) {
        mBgColor = bgColor;
    }

    public int getTextColor () {
        return mTextColor;
    }

    public void setTextColor (int textColor) {
        mTextColor = textColor;
    }

    public float getTextSize () {
        return mTextSize;
    }

    public void setTextSize (float textSize) {
        mTextSize = textSize;
    }

    public float getAxisWidth () {
        return mAxisWidth;
    }

    public void setAxisWidth (float axisWidth) {
        mAxisWidth = axisWidth;
    }

    public LineChartView (Context context) {
        this (context, null);
        init();
    }

    public LineChartView (Context context, AttributeSet attrs) {
        this (context, attrs, 0);
        init ();
    }

    public LineChartView (Context context, AttributeSet attrs, int defStyleAttr) {
        super (context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes (attrs, R.styleable.LineChartView);
        mAxisColor = typedArray.getColor (R.styleable.LineChartView_axisColor, Color.parseColor ("#000000"));
        mChartColor = typedArray.getColor (R.styleable.LineChartView_lineColor, Color.parseColor ("#FF28D3E2"));
        mBgColor = typedArray.getColor (R.styleable.LineChartView_bgColor, Color.parseColor ("#ffffff"));
        mTextColor = typedArray.getColor (R.styleable.LineChartView_textColor, Color.parseColor ("#ff0000"));
        mAxisWidth = typedArray.getDimension (R.styleable.LineChartView_axisWidth, 2);
        mTextSize = typedArray.getDimension (R.styleable.LineChartView_textSize, 30);
        typedArray.recycle ();
        mGradientColor =
            new int[] { Color.argb (100, 255, 86, 86), Color.argb (15, 255, 86, 86), Color.argb (0, 255, 86, 86) };

        mMargin = 10;
        init ();
    }

    private void init () {

        //坐标轴画笔
        mAxisPaint = new Paint ();
        mAxisPaint.setColor (mAxisColor);
        mAxisPaint.setStrokeWidth (mAxisWidth);

        //文本画笔
        mTextPaint = new Paint ();
        mTextPaint.setColor (mTextColor);
        mTextPaint.setStyle (Paint.Style.FILL);
        mTextPaint.setAntiAlias (true);
        mTextPaint.setTextSize (mTextSize);
        mTextPaint.setTextAlign (Paint.Align.LEFT);

        //折线画笔
        mChartPaint = new Paint ();
        mChartPaint.setColor (mChartColor);
        mChartPaint.setAntiAlias (true);
        mChartPaint.setStyle (Paint.Style.STROKE);
        mChartPaint.setStrokeWidth (mAxisWidth / 2);

        //路径
        mPath = new Path ();
        mChartPath = new Path ();

        //阴影画笔
        mShaderPaint = new Paint ();
        mShaderPaint.setAntiAlias (true);
        mShaderPaint.setStrokeWidth (2f);
    }

    @Override protected void onLayout (boolean changed, int left, int top, int right, int bottom) {
        super.onLayout (changed, left, top, right, bottom);
        if (changed) {
            mWidth = getWidth ();
            mHeight = getHeight ();
            mTimeWidth = (int) mTextPaint.measureText (startTime);

            mXOrigin = 0 + mMargin;
            mYOrigin = mHeight - mTextSize - mMargin;
            setBackgroundColor (mBgColor);
        }
    }

    @Override protected void onDraw (Canvas canvas) {
        super.onDraw (canvas);
        // X轴刻度间距
        mXSpacing = (mWidth - mXOrigin) / (mItems.size () - 1);
        // Y轴刻度间距
        mYSpacing = (max - min) / (mYOrigin - mMargin);

        //画坐标轴
        drawAxis (canvas);
        //画文字
        drawText (canvas);
        //画折线
        drawLine (canvas);

        //设置动画
        setAnima(canvas);
    }

    private void setAnima (Canvas canvas) {
        PathMeasure measure = new PathMeasure(mPath, false);
        float pathLength = measure.getLength();
        PathEffect effect = new DashPathEffect (new float[]{pathLength,
            pathLength}, pathLength - pathLength * mProgress);
        mChartPaint.setPathEffect(effect);
        canvas.drawPath(mPath, mChartPaint);
    }

    private void drawLine (Canvas canvas) {
        for (int i = 0; i < mItems.size (); i++) {
            float x = i * mXSpacing + mXOrigin + mMargin;
            if (i == 0){
                mChartPath.moveTo (x,mYOrigin - (mItems.get(i).getVallue () - min) / mYSpacing);
                mPath.moveTo (x,mYOrigin - (mItems.get (i).getVallue () - min ) / mYSpacing);
            }else{
                mPath.lineTo(x - mMargin - mAxisWidth, mYOrigin - (mItems.get(i).getVallue() - min) / mYSpacing);
                mChartPath.lineTo(x - mMargin - mAxisWidth, mYOrigin - (mItems.get(i).getVallue() - min) / mYSpacing);
                if (i == mItems.size() - 1) {
                    mChartPath.lineTo(x - mMargin - mAxisWidth, mYOrigin);
                    mChartPath.lineTo(mXOrigin, mYOrigin);
                    mChartPath.close();
                }
            }
        }
        //  渐变阴影
        Shader mShader = new LinearGradient (0, 0, 0, getHeight(), mGradientColor, null, Shader.TileMode.CLAMP);
        mChartPaint.setShader(mShader);

        //  绘制渐变阴影
        canvas.drawPath(mChartPath, mChartPaint);
    }

    private void drawText (Canvas canvas) {
        String maxValue = String.format (Locale.getDefault (), "%.2f", max * 100 / 100.0) + "%";
        canvas.drawText (maxValue, mXOrigin + 6, 2 * mMargin, mTextPaint);

        String minValue = String.format (Locale.getDefault (), "%.2f", min * 100 / 100.0) + "%";
        canvas.drawText (minValue, mXOrigin + 6, mYOrigin - 6, mTextPaint);

        //  绘制中间值
        String midValue = String.format (Locale.getDefault (), "%.2f", (min + max) * 100 / 200.0) + "%";
        canvas.drawText (midValue, mXOrigin + 6, (mYOrigin + mMargin) / 2, mTextPaint);

        //  绘制开始日期
        canvas.drawText (startTime, mXOrigin, mHeight - mMargin, mTextPaint);
        //  绘制结束日期
        canvas.drawText (endTime, mWidth - mTimeWidth - mMargin, mHeight - mMargin, mTextPaint);
    }

    private void drawAxis (Canvas canvas) {
        canvas.drawLine (mXOrigin, mYOrigin, mWidth - mMargin, mYOrigin, mAxisPaint);

        canvas.drawLine (mXOrigin, mYOrigin / 2, mWidth - mMargin, mYOrigin / 2, mAxisPaint);
        //  绘制X上边线
        canvas.drawLine (mXOrigin, mMargin, mWidth - mMargin, mMargin, mAxisPaint);
        //  绘制画Y轴
        canvas.drawLine (mXOrigin, mYOrigin, mXOrigin, mMargin, mAxisPaint);
        //  绘制Y右边线
        canvas.drawLine (mWidth - mMargin, mMargin, mWidth - mMargin, mYOrigin, mAxisPaint);
    }
    public static String timeStampToString(Long num) {
        Timestamp ts = new Timestamp(num * 1000);
        DateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");
        return sdf.format(ts);
    }

    //  计算动画进度
    public void setPercentage(float percentage) {
        if (percentage < 0.0f || percentage > 1.0f) {
            throw new IllegalArgumentException(
                "setPercentage not between 0.0f and 1.0f");
        }
        mProgress = percentage;
        invalidate();
    }

    /**
     * @param lineChartView
     * @param duration      动画持续时间
     */
    public void startAnim(LineChartView lineChartView, long duration) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(lineChartView, "percentage", 0.0f, 1.0f);
        anim.setDuration(duration);
        anim.setInterpolator(new LinearInterpolator ());
        anim.start();
    }
    public static class ItemBean {
        private long timeStamp;
        private int vallue;

        public ItemBean () {
        }

        public ItemBean (long timeStamp, int vallue) {
            this.timeStamp = timeStamp;
            this.vallue = vallue;
        }

        public void setTimeStamp (long timeStamp) {
            this.timeStamp = timeStamp;
        }

        public void setVallue (int vallue) {
            this.vallue = vallue;
        }

        public long getTimeStamp () {
            return timeStamp;
        }

        public int getVallue () {
            return vallue;
        }
    }
}
