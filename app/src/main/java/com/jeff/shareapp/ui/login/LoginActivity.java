package com.jeff.shareapp.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jeff.shareapp.R;
import com.jeff.shareapp.model.UserinfoModel;
import com.jeff.shareapp.ui.BasicActivity;
import com.jeff.shareapp.ui.CustomVIew.MyDialog;
import com.jeff.shareapp.ui.CustomVIew.OnBtnClickListemer;
import com.jeff.shareapp.ui.MainActivity;
import com.jeff.shareapp.util.FormatUtil;
import com.jeff.shareapp.util.MyApplication;
import com.jeff.shareapp.util.MyVolley;
import com.jeff.shareapp.util.MyVolleyListener;
import com.jeff.shareapp.util.StaticFlag;
import com.jeff.shareapp.util.UIUtils;

public class LoginActivity extends BasicActivity {


    // UI references.
    private EditText loginNameTextView;
    private EditText mPasswordView;
    private Button loginBtn;
    private TextView registerBtn;
    private TextView getPasswordBackBtn;


    private static final int LOGIN_SUCCESS=0;
    private final int LOGIN_FAILURE=1;

    public Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            endWait();
            switch (msg.what) {
                case LOGIN_SUCCESS:
                    Toast.makeText(LoginActivity.this,"登陆成功！",Toast.LENGTH_SHORT).show();
                    UserinfoModel u=(UserinfoModel) msg.getData().getSerializable("user_info");
                    MyApplication.getMyApplication().setUserinfo(u);
                    MyApplication.getMyApplication().saveUserInfoToPreference();
                    MainActivity.startActivity(LoginActivity.this,StaticFlag.INDEXPAGE_FRAGMENT);
                    finish();
                    break;
                case LOGIN_FAILURE:
                    Toast.makeText(LoginActivity.this,msg.getData().getString("failure_message"),Toast.LENGTH_SHORT).show();
                    break;
                case StaticFlag.ERROR:
                    try {
                        Thread.currentThread().sleep(1000);//毫秒
                    } catch (Exception e) {
                    }
                    Toast.makeText(LoginActivity.this, msg.getData().getString("message"), Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public static void startActivity(Activity activity){
        Intent i=new Intent(activity,LoginActivity.class);
        activity.startActivity(i);
        UIUtils.pushToRight(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        loginNameTextView = (EditText) findViewById(R.id.login_user_name);
        mPasswordView = (EditText) findViewById(R.id.login_password);
        getPasswordBackBtn = (TextView) findViewById(R.id.forgotten_password_btn);
        getPasswordBackBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, GetPasswordBackActivity.class);
                startActivity(i);
                UIUtils.popToTop(LoginActivity.this);
            }
        });
        registerBtn = (TextView) findViewById(R.id.register_lable);
        registerBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
                UIUtils.popToTop(LoginActivity.this);
                finish();
            }
        });
        loginBtn = (Button) findViewById(R.id.login_button);
        loginBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

    }


    private void attemptLogin() {

        // Reset errors.
        loginNameTextView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String loginName = loginNameTextView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(loginName)) {
            loginNameTextView.setError("登陆名不能为空！");
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError("密码不能为空！");
            focusView = mPasswordView;
            cancel = true;
        }


        if (cancel) {
            focusView.requestFocus();
        } else {
            //登陆操作
            loginToNet();
        }
    }

    private void loginToNet() {
        HashMap<String, String> mParams=new HashMap<String, String>();
        mParams.put("loginname",loginNameTextView.getText().toString().trim());
        mParams.put("password",mPasswordView.getText().toString().trim());

        startWait();
        MyVolley.getMyVolley().addStringRequest(new TypeToken<UserinfoModel>(){}.getType(),StaticFlag.LOGIN, mParams,
                new MyVolleyListener<UserinfoModel>() {
                    @Override
                    public void onSuccess(UserinfoModel data) {

                        Message message = Message.obtain();
                        message.what = LOGIN_SUCCESS;
                        Bundle b=new Bundle();
                        b.putSerializable("user_info",data);
                        message.setData(b);
                        myHandler.sendMessage(message);
                    }

                    @Override
                    public void onFailure(int failureCode, String failureMessage) {
                        Message message = Message.obtain();
                        message.what = LOGIN_FAILURE;
                        Bundle b=new Bundle();
                        b.putString("failure_message",failureMessage);
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
        ); ;
    }



}

