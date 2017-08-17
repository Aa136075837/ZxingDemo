package com.example.bo.zxingdemo.adapter;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;

/**
 * @author bo.
 * @Date 2017/4/8.
 * @desc
 */

public class RecycleDeleAdapter extends DelegateAdapter {
    public RecycleDeleAdapter (VirtualLayoutManager layoutManager) {
        super (layoutManager);
    }

    public RecycleDeleAdapter (VirtualLayoutManager layoutManager, boolean hasConsistItemType) {
        super (layoutManager, hasConsistItemType);
    }
}
