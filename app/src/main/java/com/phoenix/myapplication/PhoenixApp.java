package com.phoenix.myapplication;

import android.app.Application;

public class PhoenixApp extends Application {

    private volatile static PhoenixApp phoenixApp;

    public synchronized static PhoenixApp getInstance(){
        if (phoenixApp==null){
            synchronized (PhoenixApp.class){
                if (phoenixApp==null){
                    phoenixApp=new PhoenixApp();
                }
            }
        }

        return phoenixApp;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        phoenixApp=this;
    }
}
