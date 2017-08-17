package com.example.bo.zxingdemo;

import android.app.Application;
import cn.smssdk.SMSSDK;

/**
 * @author bo.
 * @Date 2017/5/2.
 * @desc
 */

public class ZxingApplication extends Application {
    @Override public void onCreate () {
        super.onCreate ();
        SMSSDK.initSDK (this, "1d7fed409b967", "a655d01571005fd8612aeff05a0612c2");
    }
}
