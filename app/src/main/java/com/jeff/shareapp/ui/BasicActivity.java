package com.jeff.shareapp.ui;

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
import com.jeff.shareapp.util.StringUtil;
import com.jeff.shareapp.util.UIUtils;


public abstract class BasicActivity extends FragmentActivity {


    private MyWaitDialog waitDialog;

    public static TokenExpireReciever broadcastReceiver;
    protected IntentFilter filter;

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


    public class TokenExpireReciever extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int msg = intent.getIntExtra("msg", -1);

            Log.i("当前显示的activity--", StringUtil.getRunningActivityName());

            if (msg == 440 && !"com.jeff.shareapp.ui.WelcomeActivity".equals(StringUtil.getRunningActivityName().trim())) {

                MyDialog m = new MyDialog(BasicActivity.this, 1);
                m.setContentText("本账号已在其他设备上登陆，请您重新登录。");
                m.setBtnText("好  的");
                m.setBtnClick(new OnBtnClickListemer() {
                    @Override
                    public void OnOKBtnClick() {
                        Intent i = new Intent(MyApplication.getMyApplication().getApplicationContext(), LoginActivity.class);
                        startActivity(i);
                        finish();
                    }

                    @Override
                    public void OnCancleBtnClick() {

                    }
                });
                m.showDiglog();
            }
        }

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


    @Override
    protected void onDestroy() {

        //Activity出栈
        ActivityManager.getActivityManager().removeActivity(this);
        super.onDestroy();
    }
}
