package com.terapanth.app;

/**
 * Created by EbitM9 on 10/3/2017.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.facebook.FacebookSdk;

public class MyApplication extends MultiDexApplication {

    public static final String TAG = MyApplication.class.getSimpleName();
    private static MyApplication mInstance;
    public static Typeface pb_typeface, pm_typeface, pr_typeface, psb_typeface;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        pb_typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/pb.ttf");
        pm_typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/pm.ttf");
        pr_typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/pr.ttf");
        psb_typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/psb.ttf");

        FacebookSdk.sdkInitialize(getApplicationContext());
    }

    public static boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

} 
