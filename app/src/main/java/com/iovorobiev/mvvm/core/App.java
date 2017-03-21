package com.iovorobiev.mvvm.core;

import android.app.Application;
import com.iovorobiev.mvvm.db.DbHelper;

public class App extends Application {
    public static DbHelper db;

    @Override
    public void onCreate() {
        super.onCreate();
        db = DbHelper.getInstance(this);
    }

}
