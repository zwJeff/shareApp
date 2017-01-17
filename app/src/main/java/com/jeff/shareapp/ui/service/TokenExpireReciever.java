package com.jeff.shareapp.ui.service;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.jeff.shareapp.core.MyApplication;
import com.jeff.shareapp.ui.BasicActivity;
import com.jeff.shareapp.ui.CustomVIew.MyDialog;
import com.jeff.shareapp.ui.CustomVIew.OnBtnClickListemer;
import com.jeff.shareapp.ui.login.LoginActivity;
import com.jeff.shareapp.util.StringUtil;

/**
 * 说明：
 * 作者： 张武
 * 日期： 2017/1/17.
 * email:wuzhang4@creditease.cn
 */

public class TokenExpireReciever extends BroadcastReceiver {

    private Activity activity;

    public TokenExpireReciever(Activity activity) {
        super();
        this.activity=activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int msg = intent.getIntExtra("msg", -1);

        Log.i("当前显示的activity--", StringUtil.getRunningActivityName());

        if (msg == 440 && !"com.jeff.shareapp.ui.WelcomeActivity".equals(StringUtil.getRunningActivityName().trim())) {

            MyDialog m = new MyDialog(activity, 1);
            m.setContentText("本账号已在其他设备上登陆，请您重新登录。");
            m.setBtnText("好  的");
            m.setBtnClick(new OnBtnClickListemer() {
                @Override
                public void OnOKBtnClick() {
                    Intent i = new Intent(MyApplication.getMyApplication().getApplicationContext(), LoginActivity.class);
                    activity.startActivity(i);
                    activity.finish();
                }

                @Override
                public void OnCancleBtnClick() {

                }
            });
            m.showDiglog();
        }
    }

}
