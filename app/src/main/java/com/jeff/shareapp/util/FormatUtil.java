package com.jeff.shareapp.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Created by 张武 on 2016/5/17.
 */
public class FormatUtil {

    public static String DateFormat(Date d) {
        // 给定模式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // public final String format(Date date)
        return sdf.format(d);
    }

    public static Gson getFormatGson() {
       return  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    }
}


