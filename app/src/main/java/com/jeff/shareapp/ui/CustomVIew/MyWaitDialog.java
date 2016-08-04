package com.jeff.shareapp.ui.CustomVIew;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeff.shareapp.R;

/**
 * Created by 张武 on 2016/2/24.
 */
public class MyWaitDialog extends Dialog {

    private TextView tv;

    private String waitText = "处理中，请稍后...";

    public MyWaitDialog(Context context) {
        super(context, R.style.dialog);
        initView();
    }

    public MyWaitDialog(Context context, String text) {
        super(context, R.style.dialog);
        initView();
    }


    private void initView() {
        Window window = this.getWindow();
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setContentView(R.layout.my_wait_dailog_layout);
        tv = (TextView) window.findViewById(R.id.wait_diaog_text);
        tv.setText(waitText);
        setCancelable(false);
    }

    public void isCancle(boolean flag) {
        this.setCancelable(flag);
    }

}
