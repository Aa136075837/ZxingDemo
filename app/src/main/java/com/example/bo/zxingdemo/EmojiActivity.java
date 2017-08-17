package com.example.bo.zxingdemo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.lucasurbas.listitemview.ListItemView;

import static com.example.bo.zxingdemo.R.id.action_heart;
import static com.example.bo.zxingdemo.R.id.action_info;
import static com.example.bo.zxingdemo.R.id.action_remove;

public class EmojiActivity extends BaseActivity  {

    private ListView mListView;
    private EditText mCommEt;
    private View mScroll;
    private InputMethodManager mImm;
    private View mRootView;

    @Override protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView (R.layout.activity_emoji);
        initView ();
        initListener ();
        initManager ();
    }

    private void initManager () {
        mImm = (InputMethodManager) getSystemService (Context.INPUT_METHOD_SERVICE);
    }

    private void initListener () {
    }

    private void initView () {
        mListView = (ListView) findViewById (R.id.listview);
        mCommEt = (EditText) findViewById (R.id.comment_et);
        mScroll = findViewById (R.id.scroll);

        mListView.setAdapter (new MyBaseAdapter ());
        setListViewHeightBasedOnChildren (mListView);
    }

    //根据ListView的item测量ListView的高度，解决Scrollview嵌套ListView只显示一行问题
    public void setListViewHeightBasedOnChildren (ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter ();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount (); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView (i, null, listView);
            // 计算子项View 的宽高
            listItem.measure (0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight ();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams ();
        params.height =
            totalHeight + (listView.getDividerHeight () * (listAdapter.getCount () - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams (params);
    }

    /**
     * 根据点击的位置判断是否隐藏键盘
     * @param ev
     * @return
     */
    @Override public boolean dispatchTouchEvent (MotionEvent ev) {
        if (ev.getAction () == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus ();
            if (inputIsShow (view, ev)) {
                if (null != mImm) {
                    mCommEt.setVisibility (View.GONE);
                    mImm.hideSoftInputFromWindow (mCommEt.getWindowToken (), 0);
                }
            }
        }

        return super.dispatchTouchEvent (ev);
    }

    /**
     * 判断点击位置
     * @param v
     * @param ev
     * @return
     */
    private boolean inputIsShow (View v, MotionEvent ev) {
        if (null != v && v instanceof EditText) {
            int[] leftTop = { 0, 0 };
            v.getLocationInWindow (leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight ();
            int right = left + v.getWidth ();
            if (ev.getX () > left
                && ev.getX () < right
                && ev.getY () < bottom
                && ev.getY () > top) {
                return false;//点击的是Edittext内部，不隐藏
            } else {
                return true;
            }
        }
        return false;
    }

    private class MyBaseAdapter extends BaseAdapter {
        @Override public int getCount () {
            return 100;
        }

        @Override public Object getItem (int position) {
            return null;
        }

        @Override public long getItemId (int position) {
            return position;
        }

        @Override public View getView (int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                convertView =
                    LayoutInflater.from (EmojiActivity.this).inflate (R.layout.item_listview, null);
                ListItemView listItemView = (ListItemView) convertView;
                listItemView.setTitle ("测试条目" + position);
                listItemView.setOnMenuItemClickListener (
                    new ListItemView.OnMenuItemClickListener () {
                        @Override public void onActionMenuItemSelected (MenuItem item) {
                            Log.e ("BO1", item.toString () + "--------");
                            switch (item.getItemId ()) {
                                case action_heart:
                                    mCommEt.setVisibility (View.VISIBLE);
                                    mCommEt.setFocusable (true);
                                    mCommEt.requestFocus ();
                                    mImm.showSoftInput (mCommEt, InputMethodManager.SHOW_FORCED);
                                    break;
                                case action_info:
                                    mCommEt.setVisibility (View.GONE);
                                    if (null != mImm) {
                                        mImm.hideSoftInputFromWindow (mCommEt.getWindowToken (), 0);

                                    }
                                    break;
                                case action_remove:
                                    finish ();
                                    break;
                            }
                        }
                    });
            }
            return convertView;
        }
    }


}
