package com.jeff.shareapp.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.android.volley.toolbox.Volley;
import com.jeff.shareapp.core.ActivityManager;
import com.jeff.shareapp.ui.CustomVIew.MyDialog;
import com.jeff.shareapp.ui.CustomVIew.MyWaitDialog;
import com.jeff.shareapp.ui.CustomVIew.OnBtnClickListemer;
import com.jeff.shareapp.ui.login.LoginActivity;
import com.jeff.shareapp.core.MyApplication;
import com.jeff.shareapp.net.MyVolley;
import com.jeff.shareapp.ui.myPage.SettingActivity;
import com.jeff.shareapp.ui.service.MyGetNotificationService;
import com.jeff.shareapp.ui.service.TokenExpireReciever;
import com.jeff.shareapp.util.StringUtil;
import com.jeff.shareapp.util.UIUtils;


public abstract class BasicActivity extends FragmentActivity {


    private MyWaitDialog waitDialog;

    public static TokenExpireReciever broadcastReceiver;


    public static boolean isReciev = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //设置浸入式状态栏
        UIUtils.openImmerseStatasBarMode(this);
        super.onCreate(savedInstanceState);

        //新建当前app的网络请求队列
        MyVolley.requestQueue = Volley.newRequestQueue(this);

        waitDialog = new MyWaitDialog(this);

        //Activity入栈
        ActivityManager.getActivityManager().addActivity(this);
    }


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        final View parent = getWindow().getDecorView();
        //    setTitleResource(parent, R.layout.title_bar_layout);
    }

    /**
     * 开启等待动画
     */
    public void startWait() {

        if (!waitDialog.isShowing())
            waitDialog.show();
    }

    /**
     * 关闭等待动画
     */
    public void endWait() {
        if (waitDialog.isShowing())
            waitDialog.dismiss();
    }


    /**
     * 模拟收到短信验证码
     */

    public String getVCode() {
        String code = "";
        for (int i = 0; i < 6; i++) {
            code += (int) (Math.random() * 10);
        }
        String message = "【慕课在线App】您的手机号验证码为" + code + ",请勿告知他人！";
        MyDialog m = new MyDialog(this, 1);
        m.setContentText(message);
        m.setBtnText("我知道了");
        m.setBtnClick(new OnBtnClickListemer() {
            @Override
            public void OnOKBtnClick() {

            }

            @Override
            public void OnCancleBtnClick() {

            }
        });
        m.showDiglog();
        return code;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if("com.jeff.shareapp.ui.WelcomeActivity".equals(StringUtil.getRunningActivityName().trim()))
            return true;

        if (keyCode == event.KEYCODE_BACK) {
            if (ActivityManager.activityStack.size() <= 1) {
                MyDialog m = new MyDialog(this);
                m.setContentText("您确定要退出“慕课在线”app吗？");
                m.setBtnClick(new OnBtnClickListemer() {
                    @Override
                    public void OnOKBtnClick() {
                        ActivityManager.getActivityManager().exitApp(getApplicationContext());
                    }

                    @Override
                    public void OnCancleBtnClick() {
                    }
                });
                m.showDiglog();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * 添加token过期的广播监听
     */
    public static void openTokenExpireReceiver(){
        IntentFilter filter;
        //添加token过期的广播接收
        broadcastReceiver = new TokenExpireReciever(ActivityManager.getActivityManager().activityStack.peek());
        filter = new IntentFilter("com.jeff.token_expire");
        //注册广播接收器
        if (!isReciev) {
            isReciev = true;
            Log.i("jeff","开始接收token过期的广播");
            MyApplication.getMyApplication().registerReceiver(broadcastReceiver, filter);
        }
    }

    /**
     * 关闭token过期的广播监听
     */
    public static void closeTokenExpireReceiver(){
        if (broadcastReceiver==null)
            return;
        //取消注册广播接收器
        if (isReciev) {
            isReciev = false;
            Log.i("jeff", "停止接收token过期的广播");
            MyApplication.getMyApplication().unregisterReceiver(broadcastReceiver);
        }
    }

    /**
     * 开启接受推送消息
     */
    public static void openGetNewNotification(){

        if (!MyGetNotificationService.isStartLoop) {
            //开启后台服务检测是否有新通知，每10s轮询一次
            Log.i("jeff","开启后台服务检测是否有新通知，每10s轮询一次");
            MyGetNotificationService.isStartLoop=true;
            Activity activity=ActivityManager.getActivityManager().activityStack.peek();
            activity.startService(new Intent(activity, MyGetNotificationService.class));
        }
    }

    /**
     * 关闭推送消息
     */
    public static void closeGetNewNotification(){

        if (MyGetNotificationService.isStartLoop) {
            //停止轮询服务
            Log.i("jeff", "停止接收token过期的广播");
            MyGetNotificationService.isStartLoop = false;
            Activity activity=ActivityManager.getActivityManager().activityStack.peek();
            activity.stopService(new Intent(activity, MyGetNotificationService.class));
        }
    }


    @Override
    protected void onDestroy() {

        //Activity出栈
        ActivityManager.getActivityManager().removeActivity(this);
        super.onDestroy();
    }
}
