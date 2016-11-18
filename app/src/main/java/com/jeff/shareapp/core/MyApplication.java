package com.jeff.shareapp.core;

import android.app.Application;
import android.content.SharedPreferences;

import com.jeff.shareapp.model.UserinfoModel;
import com.jeff.shareapp.util.StaticFlag;

/**
 * Created by 张武 on 2016/5/29.
 */
public class MyApplication extends Application {

    private static MyApplication singleInstance;

    //app缓存
    private MyDataPref dataPref;

    public static MyApplication getMyApplication() {
        if (singleInstance == null) {
            singleInstance = new MyApplication();

        }
        return singleInstance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        singleInstance = this;
        dataPref = new MyDataPref();
    }

    public MyDataPref getDataPref() {
        if(dataPref==null)
            dataPref=new MyDataPref();
        return dataPref;
    }
}
