package com.jeff.shareapp.util;

import android.app.ActivityManager;
import android.content.Context;


public class StringUtil {

    /**
     * 字符串超过一定长度加省略号
     *
     * @param str
     * @param size
     * @return
     */
    public static String spilt(String str, int size) {
        if (str != null && str.length() != 0) {
            if (str.length() > size) {
                str = str.substring(0, size) + " ...";
            }
            return str;
        }
        return str;
    }


    public static boolean isTelephone(String telephone) {

        String regExp = "^(13|15|17|18)[0-9]{9}$";

        return telephone.matches(regExp);

    }


    public static boolean isEmail(String email) {

        String regExp = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        return email.matches(regExp);

    }

    /**
     * 将电话号码中间部分替换为星号
     */
    public static String encryptPhoneNumber(String s) {
        if (s.length() > 7) {
            return s.substring(0, 3) + "****" + s.substring(7);
        } else {
            return s;
        }
    }




   public  static int fileType(String fileUrl) {
       int start=fileUrl.lastIndexOf(".");
        String fileTypeString= fileUrl.substring(start+1);
        switch (fileTypeString.toLowerCase()) {
            case "jpg":
            case "png":
                return StaticFlag.FILE_TYPE_PIC;
            case "mp4":
            case "3gp":
                return StaticFlag.FILE_TYPE_VEDIO;
            case "doc":
            case "txt":
            case "docx":
                return StaticFlag.FILE_TYPE_TEXT;
            case "ppt":
            case "pptx":
                return StaticFlag.FILE_TYPE_PPT;
            case "mp3":
            case "arm":
                return StaticFlag.FILE_TYPE_AUDIO;
            default:
                return StaticFlag.FILE_TYPE_OTHER;
        }
    }

    /**
     * 获取当前显示的activity名称
     * @return
     */
//    public static String getRunningActivityName() {
//        String contextString = MyApplication.getMyApplication().getApplicationContext().toString();
//        return contextString.substring(contextString.lastIndexOf(".") + 1, contextString.indexOf("@"));
//    }
    public static String getRunningActivityName(){
        ActivityManager activityManager=(ActivityManager) MyApplication.getMyApplication().getSystemService(Context.ACTIVITY_SERVICE);
        String runningActivity=activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        return runningActivity;
    }

}
