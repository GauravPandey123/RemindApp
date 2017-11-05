package com.reminderapp;

import android.content.Context;

import in.editsoft.api.util.App;

/**
 * Created by Gaurav Pandey on 05-11-2017.
 */

public class RemiderApp extends App {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }


    public static Context getContext() {
        return mContext;
    }

}
