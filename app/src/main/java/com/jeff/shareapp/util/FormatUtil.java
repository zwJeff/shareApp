package com.jeff.shareapp.util;

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
}


