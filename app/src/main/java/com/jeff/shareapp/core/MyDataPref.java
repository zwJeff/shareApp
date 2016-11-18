package com.jeff.shareapp.core;

import android.app.Application;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.jeff.shareapp.util.FormatUtil;

import java.text.Format;
import java.util.HashMap;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

/**
 * 说明： 数据缓存类
 * 作者： 张武
 * 日期： 2016/11/9.
 * email:jeff_zw@qq.com
 */

public class MyDataPref {


    //数据缓存map
    private HashMap<String, Object> mLocalDatas;
    private SharedPreferences appSharePref;
    //sharePreference名字
    private String APP_SHARED_PREFERENCE_NAME="shareApp_sharePreference";

    private Object localDatalock = new Object();
    private Object preferDatalock = new Object();

    public MyDataPref() {
        synchronized (localDatalock) {
            if (mLocalDatas == null)
                mLocalDatas = new HashMap<>();
        }
    }

    /**
     * 增加数据到Map
     *   用对象的CanonicalName作为Key
     *
     * @param obj
     * @param <T>
     */
    public <T> void addToLocalData(T obj) {

        if (obj != null)
            mLocalDatas.put(obj.getClass().getCanonicalName(), obj);

    }

    /**
     * 从Map中读取数据
     *   用对象的CanonicalName作为Key
     *
     * @param obj
     * @param <T>
     */
    public <T> T getLocalData(Class<T> obj) {

        T result=null;

        if (obj != null) {
            result = (T) mLocalDatas.get(obj.getCanonicalName());
            if(result==null){
                //todo 如果Map中没有，从preference中取
                result=pullFromPref(obj);
                if(result!=null)
                    addToLocalData(result);
            }
        }
        return result;
    }

    /**
     * 将数据存入sharedPreference
     *  用CanonicalName作为key，数据以json串的形式存入
     * @param obj
     * @param <T>
     */
    public <T> void pushToPref(T obj){
        appSharePref= MyApplication.getMyApplication().getSharedPreferences(APP_SHARED_PREFERENCE_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor=appSharePref.edit();
        editor.putString(obj.getClass().getCanonicalName(), FormatUtil.objToJson(obj));
        editor.commit();
    }

    public <T> T pullFromPref(Class<T> obj){
        appSharePref= MyApplication.getMyApplication().getSharedPreferences(APP_SHARED_PREFERENCE_NAME,MODE_PRIVATE);
        String json=appSharePref.getString(obj.getCanonicalName(),"");
        if(TextUtils.isEmpty(json))
            return null;
        return (T)FormatUtil.jsonToObj(json,obj);
    }



}
