package com.jeff.shareapp.util;

import android.app.Application;
import android.content.SharedPreferences;

import com.jeff.shareapp.model.UserinfoModel;

/**
 * Created by 张武 on 2016/5/29.
 */
public class MyApplication extends Application {

    private static  MyApplication singleInstance;

    private static  SharedPreferences userinfoSharePref;

    private UserinfoModel userinfo;

    public SharedPreferences getUserinfoSharePref() {
        return userinfoSharePref;
    }

    public void setUserinfoSharePref(SharedPreferences userinfoSharePref) {
        this.userinfoSharePref = userinfoSharePref;
    }

    public UserinfoModel getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserinfoModel userinfo) {
        this.userinfo = userinfo;
    }

    public static MyApplication getMyApplication(){
        if(singleInstance==null){
            singleInstance=new MyApplication();
        }
        return singleInstance;
    }

    public MyApplication() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleInstance=this;
        userinfoSharePref=getSharedPreferences("userinfo",MODE_PRIVATE);
        userinfo=new UserinfoModel();
        userinfo.setTelephone(userinfoSharePref.getString("telephone",null));
        userinfo.setUserId(userinfoSharePref.getInt("user_id",0));
        userinfo.setToken(userinfoSharePref.getString("token",null));
        userinfo.setUsername(userinfoSharePref.getString("name",null));
        userinfo.setPassword(userinfoSharePref.getString("password",null));
        userinfo.setEmail(userinfoSharePref.getString("email",null));
        userinfo.setUserHeadUrl(userinfoSharePref.getString("head_url",null));
        userinfo.setUserType(userinfoSharePref.getInt("type",StaticFlag.STUDENT_USER_TYPE));

    }

    public void saveToPreference(){
        SharedPreferences.Editor editor=userinfoSharePref.edit();
        editor.putString("telephone",userinfo.getTelephone());
        editor.putInt("user_id",userinfo.getUserId());
        editor.putString("token",userinfo.getToken());
        editor.putString("name",userinfo.getUsername());
        editor.putString("password",userinfo.getPassword());
        editor.putString("email",userinfo.getEmail());
        editor.putString("head_url",userinfo.getUserHeadUrl());
        editor.putInt("type",userinfo.getUserType());
        editor.commit();

    }

}
