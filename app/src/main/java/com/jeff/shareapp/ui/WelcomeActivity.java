package com.jeff.shareapp.ui;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jeff.shareapp.R;
import com.jeff.shareapp.model.UserinfoModel;
import com.jeff.shareapp.ui.login.LoginActivity;
import com.jeff.shareapp.util.MyApplication;
import com.jeff.shareapp.util.MyVolley;
import com.jeff.shareapp.util.MyVolleyListener;
import com.jeff.shareapp.util.StaticFlag;
import com.jeff.shareapp.util.UIUtils;

import java.util.HashMap;

public class WelcomeActivity extends BasicActivity {

    private String token;


    public Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case StaticFlag.SUCCESS:
                    try {
                        Thread.currentThread().sleep(1000);//毫秒
                    } catch (Exception e) {
                    }
                    UserinfoModel u = (UserinfoModel) msg.getData().getSerializable("user_info");
                    u.setToken(token);
                    MyApplication.getMyApplication().setUserinfo(u);
                    MyApplication.getMyApplication().saveToPreference();
                    MainActivity.startActivity(WelcomeActivity.this);
                    finish();
                    break;
                case StaticFlag.FAILURE:
                    try {
                        Thread.currentThread().sleep(1000);//毫秒
                    } catch (Exception e) {
                    }
                    if (!TextUtils.isEmpty(msg.getData().getString("message")))
                        Toast.makeText(WelcomeActivity.this, "登陆验证已过期,请重新登录！", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(WelcomeActivity.this, LoginActivity.class);
                    startActivity(i);
                    UIUtils.popToLeft(WelcomeActivity.this);
                    finish();
                    break;
                case StaticFlag.ERROR:
                    try {
                        Thread.currentThread().sleep(1000);//毫秒
                    } catch (Exception e) {
                    }
                    Toast.makeText(WelcomeActivity.this, msg.getData().getString("message"), Toast.LENGTH_SHORT).show();
                    Intent i2 = new Intent(WelcomeActivity.this, LoginActivity.class);
                    startActivity(i2);
                    UIUtils.popToLeft(WelcomeActivity.this);
                    finish();
                    break;
            }
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        isLogin();
    }

    private void isLogin() {

        token = MyApplication.getMyApplication().getUserinfo().getToken();
        if (TextUtils.isEmpty(token)) {
            Message message = Message.obtain();
            message.what = StaticFlag.FAILURE;
            myHandler.sendMessage(message);
        }
        HashMap<String, String> mParams = new HashMap<String, String>();
        mParams.put("token", token);
        new MyVolley<UserinfoModel>(StaticFlag.AUTO_LOGIN, mParams,
                new MyVolleyListener() {
                    @Override
                    public void onSuccess(Object data) {

                        Gson gson = new Gson();
                        String jsonResult = gson.toJson(data);
                        UserinfoModel d = gson.fromJson(jsonResult, new TypeToken<UserinfoModel>() {
                        }.getType());

                        Message message = Message.obtain();
                        message.what = StaticFlag.SUCCESS;
                        Bundle b = new Bundle();
                        b.putSerializable("user_info", d);
                        message.setData(b);
                        myHandler.sendMessage(message);
                    }

                    @Override
                    public void onFailure(int failureCode, String failureMessage) {
                        Message message = Message.obtain();
                        message.what = StaticFlag.FAILURE;
                        Bundle b = new Bundle();
                        b.putSerializable("message", failureMessage);
                        message.setData(b);
                        myHandler.sendMessage(message);
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        Message message = Message.obtain();
                        message.what = StaticFlag.ERROR;
                        Bundle b = new Bundle();
                        b.putSerializable("message", errorMessage);
                        message.setData(b);
                        myHandler.sendMessage(message);
                    }
                }
        );
    }
}

