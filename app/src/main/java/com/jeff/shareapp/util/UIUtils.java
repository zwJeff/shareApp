package com.jeff.shareapp.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Build;
import android.text.InputType;
import android.text.format.Time;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.jeff.shareapp.R;

import java.io.IOException;
import java.lang.reflect.Method;

public class UIUtils {

    /**
     * 界面返回动画效果
     *
     * @param activity
     */
    public static void pushToRight(Activity activity) {
        activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }


    /**
     * 界面前进动画效果
     *
     * @param activity
     */
    public static void popToLeft(Activity activity) {
        activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    /**
     * 界面返回动画效果
     *
     * @param activity
     */
    public static void pushToBotom(Activity activity) {
        activity.overridePendingTransition(R.anim.push_bottom_in, R.anim.push_bottom_out);
    }

    /**
     * 界面前进动画效果
     *
     * @param activity
     */
    public static void popToTop(Activity activity) {
        activity.overridePendingTransition(R.anim.push_top_in, R.anim.push_top_out);
    }

    /**
     * dip转px
     *
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        return (int) (dipValue * context.getResources().getDisplayMetrics().density + 0.5f);
    }



    /**
     * 开启沉浸式状态栏
     */
    public static void openImmerseStatasBarMode(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    public static int getPicByType(int resourceFileType) {

        switch (resourceFileType) {

            case StaticFlag.FILE_TYPE_PPT:
                return R.mipmap.ic_ppt;
            case StaticFlag.FILE_TYPE_AUDIO:
                return R.mipmap.ic_mp3;
            case StaticFlag.FILE_TYPE_VEDIO:
                return R.mipmap.ic_mpg;
            case StaticFlag.FILE_TYPE_TEXT:
                return R.mipmap.ic_doc;
            case StaticFlag.FILE_TYPE_PIC:
                return R.mipmap.ic_png;
            default:
                return R.mipmap.ic_other;

        }


    }
}
