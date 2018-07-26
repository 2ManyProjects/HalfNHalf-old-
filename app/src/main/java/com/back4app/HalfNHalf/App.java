package com.back4app.HalfNHalf;

import com.parse.Parse;
import com.parse.ParseObject;

import android.app.Application;


public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Profile.class);
        Parse.initialize(this);
    }
}