package com.example.bo.zxingdemo;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.DefaultLayoutHelper;
import com.alibaba.android.vlayout.layout.FixLayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.ScrollFixLayoutHelper;
import com.example.bo.zxingdemo.adapter.RecycleAdapter;
import com.example.bo.zxingdemo.helper.RecycleItemHelper;
import java.util.LinkedList;
import java.util.List;

public class EmojiconActivity extends BaseActivity
    implements RecycleAdapter.OnRecyItemClickListener, RecyclerView.OnItemTouchListener {

    private RecyclerView mRecyclerView;

    @Override protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_emojicon);
        initView ();
    }

    private void initView () {
        mRecyclerView = (RecyclerView) findViewById (R.id.recycleView);
        VirtualLayoutManager layoutManager = new VirtualLayoutManager (this);

        mRecyclerView.setLayoutManager (layoutManager);
        mRecyclerView.setItemAnimator (new DefaultItemAnimator ());
        mRecyclerView.addItemDecoration (new RecyclerView.ItemDecoration () {
            @Override public void getItemOffsets (Rect outRect, View view, RecyclerView parent,
                RecyclerView.State state) {
                outRect.set (10, 10, 10, 10);
            }
        });
        final List<LayoutHelper> helpers = new LinkedList<> ();

        final GridLayoutHelper gridLayoutHelper = new GridLayoutHelper (5);
        gridLayoutHelper.setItemCount (25);

        final ScrollFixLayoutHelper scrollFixLayoutHelper =
            new ScrollFixLayoutHelper (FixLayoutHelper.TOP_RIGHT, 100, 100);

        helpers.add (DefaultLayoutHelper.newHelper (7));
        helpers.add (scrollFixLayoutHelper);
        helpers.add (DefaultLayoutHelper.newHelper (2));
        helpers.add (gridLayoutHelper);
        helpers.add (DefaultLayoutHelper.newHelper (7));

        layoutManager.setLayoutHelpers (helpers);

        RecycleAdapter adapter = new RecycleAdapter (layoutManager, this);
        adapter.setOnRecyItemClickListener (this);

        mRecyclerView.setAdapter (adapter);

        ItemTouchHelper.Callback callback = new RecycleItemHelper (adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper (callback);
        itemTouchHelper.attachToRecyclerView (mRecyclerView);
/*new VirtualLayoutAdapter (layoutManager) {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
                return new MainViewHolder (new TextView (EmojiconActivity.this));
            }

            @Override public void onBindViewHolder (RecyclerView.ViewHolder holder, int position) {
                VirtualLayoutManager.LayoutParams layoutParams =
                    new VirtualLayoutManager.LayoutParams (ViewGroup.LayoutParams.MATCH_PARENT,
                        300);
                holder.itemView.setLayoutParams (layoutParams);

                ((TextView) holder.itemView).setText (Integer.toString (position));

                if (position == 7) {
                    layoutParams.height = 120;
                    layoutParams.width = 120;
                } else if (position > 35) {
                    layoutParams.height = 200 + (position - 30) * 100;
                }

                if (position > 35) {
                    holder.itemView.setBackgroundColor (0x66cc0000 + (position - 30) * 128);
                } else if (position % 2 == 0) {
                    holder.itemView.setBackgroundColor (0xaa00ff00);
                } else {
                    holder.itemView.setBackgroundColor (0xccff00ff);
                }
            }

            @Override public int getItemCount () {
                List<LayoutHelper> helpers = getLayoutHelpers ();
                if (helpers == null) {
                    return 0;
                }
                int count = 0;
                for (int i = 0, size = helpers.size (); i < size; i++) {
                    count += helpers.get (i).getItemCount ();
                }
                return count;
            }
        });

        new Handler (Looper.getMainLooper ()).postDelayed (new Runnable () {
            @Override public void run () {
                mRecyclerView.scrollToPosition (7);
                mRecyclerView.getAdapter ().notifyDataSetChanged ();
            }
        }, 6000*/
        //mRecyclerView.addOnItemTouchListener (this);
    }

    @Override public void onRecyItemCLick (View v, int position) {
        Toast.makeText (this, "点击------>" + position, Toast.LENGTH_SHORT).show ();
    }

    @Override public boolean onInterceptTouchEvent (RecyclerView rv, MotionEvent e) {
        return false;
    }

    @Override public void onTouchEvent (RecyclerView rv, MotionEvent e) {
        switch (e.getAction ()) {
            case MotionEvent.ACTION_DOWN:
                Toast.makeText (this, rv.getId () + "-------", Toast.LENGTH_SHORT).show ();
                break;
            default:
                break;
        }
    }

    @Override public void onRequestDisallowInterceptTouchEvent (boolean disallowIntercept) {

    }

    static class MainViewHolder extends RecyclerView.ViewHolder {

        public MainViewHolder (View itemView) {
            super (itemView);
        }
    }

    private void initRecycleViewPool () {
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool ();
        mRecyclerView.setRecycledViewPool (viewPool);
        viewPool.setMaxRecycledViews (0, 10);
    }
}
