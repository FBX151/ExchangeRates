package com.sanzharaubakir.exchangerateapp;

import android.app.Application;

import com.sanzharaubakir.exchangerateapp.di.component.AppComponent;
import com.sanzharaubakir.exchangerateapp.di.component.DaggerAppComponent;

public class MyApplication extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent.create();

    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
