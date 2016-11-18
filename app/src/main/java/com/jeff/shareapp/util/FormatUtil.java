package com.jeff.shareapp.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Created by 张武 on 2016/5/17.
 */
public class FormatUtil {

    private static Gson gson;

    public static String DateFormat(Date d) {
        // 给定模式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(d);
    }

    public static Gson getFormatGson() {
        if (gson == null)
            gson=new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        return gson;
    }

    public static String objToJson(Object obj){
        return getFormatGson().toJson(obj);
    }
    public static <T> T jsonToObj(String jsonStr,Class<T> cls){
        return (T)getFormatGson().fromJson(jsonStr,cls);
    }
    public static <T> T jsonToObj(String jsonStr, Type type){
        return (T)getFormatGson().fromJson(jsonStr,type);
    }
}


