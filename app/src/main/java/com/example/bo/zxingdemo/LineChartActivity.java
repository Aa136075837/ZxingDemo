package com.example.bo.zxingdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.example.bo.zxingdemo.view.LineChartView;
import java.util.ArrayList;
import java.util.List;

public class LineChartActivity extends AppCompatActivity {

    private LineChartView mLcv;
    private List<LineChartView.ItemBean> mItems;
    int[] shadeColors;

    @Override protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_line_chart);
        initView();
        initData();
    }

    private void initData () {
        mItems = new ArrayList<> ();
        mItems.add(new LineChartView.ItemBean(1489507200, 23));
        mItems.add(new LineChartView.ItemBean(1489593600, 88));
        mItems.add(new LineChartView.ItemBean(1489680000, 60));
        mItems.add(new LineChartView.ItemBean(1489766400, 50));
        mItems.add(new LineChartView.ItemBean(1489680000, 60));
        mItems.add(new LineChartView.ItemBean(1489766400, 50));
        mItems.add(new LineChartView.ItemBean(1489852800, 70));
        mItems.add(new LineChartView.ItemBean(1489939200, 10));
        mItems.add(new LineChartView.ItemBean(1490025600, 33));
        mItems.add(new LineChartView.ItemBean(1490112000, 44));
        mItems.add(new LineChartView.ItemBean(1490198400, 99));
        mItems.add(new LineChartView.ItemBean(1490284800, 17));
        mItems.add(new LineChartView.ItemBean(1490025600, 33));
        mItems.add(new LineChartView.ItemBean(1490112000, 44));
        mItems.add(new LineChartView.ItemBean(1490198400, 77));
        mItems.add(new LineChartView.ItemBean(1490025600, 33));
        mItems.add(new LineChartView.ItemBean(1490112000, 44));
        mItems.add(new LineChartView.ItemBean(1490198400, 99));
        mItems.add(new LineChartView.ItemBean(1490112000, 44));
        mItems.add(new LineChartView.ItemBean(1490198400, 65));
        mItems.add(new LineChartView.ItemBean(1490284800, 17));

        shadeColors= new int[]{
            Color.argb(100, 255, 8, 6), Color.argb(55, 0, 255, 0),
            Color.argb(22, 0, 6, 255)};

        //  设置折线数据
        mLcv.setItems(mItems);
        //  设置渐变颜色
        mLcv.setGradientColor(shadeColors);
        //  开启动画
        mLcv.startAnim(mLcv,2000);
    }

    private void initView () {
        mLcv = (LineChartView) findViewById (R.id.lcv);
    }
}
