package com.terapanth.app.Utills;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by EbiM10 on 6/17/2016.
 */
public class AppPreference {

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private Context mContext;
    private int PRIVATE_MODE = 0;

    private static final String PREFERENCE_NAME = "Terapanth";


    public AppPreference(Context context) {
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(PREFERENCE_NAME, PRIVATE_MODE);
        mEditor = mSharedPreferences.edit();
    }

    public void ClearSharedpreference() {
        this.mEditor.clear();
        this.mEditor.commit();
    }



}
