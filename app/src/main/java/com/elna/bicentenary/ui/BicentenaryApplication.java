package com.elna.bicentenary.ui;

import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;

public class BicentenaryApplication extends Application{
    @Override public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);

    }
}
