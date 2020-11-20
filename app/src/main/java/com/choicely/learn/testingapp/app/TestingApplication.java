package com.choicely.learn.testingapp.app;

import android.app.Application;

import com.choicely.learn.testingapp.db.RealmHelper;

public class TestingApplication extends Application {
    private static final String TAG = "TestingApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        RealmHelper.init(this);
    }
}
