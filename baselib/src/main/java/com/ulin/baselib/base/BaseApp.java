package com.ulin.baselib.base;

import androidx.multidex.MultiDexApplication;

public class BaseApp extends MultiDexApplication {
    private volatile static BaseApp phoenixApp;

    public synchronized static BaseApp getInstance(){
        if (phoenixApp==null){
            synchronized (BaseApp.class){
                if (phoenixApp==null){
                    phoenixApp=new BaseApp();
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
