package com.jeff.shareapp.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jeff.shareapp.R;
import com.jeff.shareapp.model.UserinfoModel;
import com.jeff.shareapp.ui.BasicActivity;
import com.jeff.shareapp.ui.MainActivity;
import com.jeff.shareapp.util.FormatUtil;
import com.jeff.shareapp.core.MyApplication;
import com.jeff.shareapp.net.MyVolley;
import com.jeff.shareapp.net.MyVolleyListener;
import com.jeff.shareapp.util.StaticFlag;
import com.jeff.shareapp.util.StringUtil;
import com.jeff.shareapp.util.UIUtils;

import java.util.HashMap;
import java.util.UUID;


public class RegisterActivity extends BasicActivity implements OnClickListener {


    // UI references.

    private TextView title;
    private ImageView backPic;

    private EditText registerNameTextView;
    private EditText registerTelephoneTextView;
    private EditText registerPasswordView;
    private EditText registerVerificationCodeView;
    private TextView registerGetVerificationCodeViewBtn;
    private EditText registerPasswordRepeatView;
    private Button registerBtn;
    private TextView loginTextView;
    private TextView studentView;
    private TextView teacherView;

    private String registerName;
    private String registerTelephone;
    private String registerPassword;
    private String registerVerificationCode;
    private String verificationCode="default";
    private String registerPasswordRepeat;
    private int userType = StaticFlag.STUDENT_USER_TYPE;

    private String token;

    private UserinfoModel u;

    public Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            endWait();
            switch (msg.what) {
                case StaticFlag.SUCCESS:
                    Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                    MyApplication.getMyApplication().getDataPref().addToLocalData(u);
                    MyApplication.getMyApplication().getDataPref().pushToPref(u);

                    finish();
                    MainActivity.startActivity(RegisterActivity.this);
                    break;
                case StaticFlag.FAILURE:
                    Toast.makeText(RegisterActivity.this, msg.getData().getString("failure_message"), Toast.LENGTH_SHORT).show();
                    break;
                case StaticFlag.ERROR:
                    try {
                        Thread.currentThread().sleep(1000);//毫秒
                    } catch (Exception e) {
                    }
                    Toast.makeText(RegisterActivity.this, msg.getData().getString("message"), Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UIUtils.openImmerseStatasBarMode(this);
        setContentView(R.layout.activity_register);
        // Set up the login form.
        title = (TextView) findViewById(R.id.lable_title);
        title.setText("注  册");
        backPic = (ImageView) findViewById(R.id.img_left);
        backPic.setVisibility(View.VISIBLE);
        backPic.setOnClickListener(this);
        registerNameTextView = (EditText) findViewById(R.id.register_user_name);
        registerTelephoneTextView = (EditText) findViewById(R.id.register_telephone);
        registerVerificationCodeView = (EditText) findViewById(R.id.register_verification_code);
        registerGetVerificationCodeViewBtn = (TextView) findViewById(R.id.register_get_verification_code_btn);
        registerGetVerificationCodeViewBtn.setOnClickListener(this);
        studentView = (TextView) findViewById(R.id.register_student_selected);
        studentView.setOnClickListener(this);
        teacherView = (TextView) findViewById(R.id.register_teacher_selected);
        teacherView.setOnClickListener(this);
        setSelect(StaticFlag.STUDENT_USER_TYPE);

        registerGetVerificationCodeViewBtn.setOnClickListener(this);
        registerPasswordView = (EditText) findViewById(R.id.login_password);
        registerPasswordRepeatView = (EditText) findViewById(R.id.login_password_repeat);
        registerBtn = (Button) findViewById(R.id.register_button);
        registerBtn.setOnClickListener(this);
        loginTextView = (TextView) findViewById(R.id.login_lable);
        loginTextView.setOnClickListener(this);

    }


    private void attemptRegister() {

        // Reset errors.
        registerNameTextView.setError(null);
        registerTelephoneTextView.setError(null);
        registerVerificationCodeView.setError(null);
        registerPasswordView.setError(null);
        registerPasswordRepeatView = (EditText) findViewById(R.id.login_password_repeat);
        // Store values at the time of the login attempt.
        registerName = registerNameTextView.getText().toString();
        registerTelephone = registerTelephoneTextView.getText().toString();
        registerPassword = registerPasswordView.getText().toString();
        registerVerificationCode = registerVerificationCodeView.getText().toString();
        registerPasswordRepeat = registerPasswordRepeatView.getText().toString();
        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(registerName)) {
            registerNameTextView.setError("用户名不能为空！");
            focusView = registerNameTextView;
            cancel = true;
        }

        if (TextUtils.isEmpty(registerTelephone)) {
            registerTelephoneTextView.setError("手机号不能为空！");
            focusView = registerTelephoneTextView;
            cancel = true;
        }

        if (!StringUtil.isTelephone(registerTelephone)) {
            registerTelephoneTextView.setError("手机号不正确！");
            focusView = registerTelephoneTextView;
            cancel = true;
        }

        if (TextUtils.isEmpty(registerVerificationCode)) {
            registerVerificationCodeView.setError("验证码不能为空！");
            focusView = registerVerificationCodeView;
            cancel = true;
        }
       else if (!registerVerificationCode.toString().equals(verificationCode)) {
            registerVerificationCodeView.setError("验证码不正确！");
            focusView = registerVerificationCodeView;
            cancel = true;
        }


        if (TextUtils.isEmpty(registerPassword)) {
            registerPasswordView.setError("密码不能为空！");
            focusView = registerPasswordView;
            cancel = true;
        }
        if (TextUtils.isEmpty(registerPasswordRepeat)) {
            registerPasswordRepeatView.setError("密码不能为空！");
            focusView = registerPasswordRepeatView;
            cancel = true;
        }
        if (!registerPasswordRepeat.equals(registerPassword)) {
            registerPasswordRepeatView.setError("两次输入的密码不一致！");
            focusView = registerPasswordRepeatView;
            cancel = true;
        }


        if (cancel) {
            focusView.requestFocus();
        } else {
            //注册操作
            register();

        }
    }

    private void register() {
        startWait();
        HashMap<String, String> mParams = new HashMap<String, String>();
        mParams.put("telephone", registerTelephone);
        mParams.put("password", registerPassword);
        mParams.put("user_type", userType + "");
        mParams.put("username", registerName);

        new MyVolley<UserinfoModel>(StaticFlag.REGISTER, mParams,
                new MyVolleyListener() {
                    @Override
                    public void onSuccess(Object data) {

                        Gson gson = FormatUtil.getFormatGson();
                        String jsonResult = gson.toJson(data);
                        u = gson.fromJson(jsonResult, new TypeToken<UserinfoModel>() {
                        }.getType());

                        Message message = Message.obtain();
                        message.what = StaticFlag.SUCCESS;
                        myHandler.sendMessage(message);
                    }

                    @Override
                    public void onFailure(int failureCode, String failureMessage) {
                        Message message = Message.obtain();
                        message.what = StaticFlag.FAILURE;
                        Bundle b = new Bundle();
                        b.putString("failure_message", failureMessage);
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


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_left:
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
                UIUtils.pushToRight(RegisterActivity.this);
                finish();
                break;

            case R.id.register_student_selected:
                setSelect(StaticFlag.STUDENT_USER_TYPE);
                break;

            case R.id.register_teacher_selected:
                setSelect(StaticFlag.TEACHER_USER_TYPE);
                break;

            case R.id.register_get_verification_code_btn:
                getVerificationCode();
                break;
            case R.id.register_button:
                attemptRegister();
                break;
            case R.id.login_lable:
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                UIUtils.pushToBotom(RegisterActivity.this);
                finish();
                break;
        }

    }

    private void setSelect(int flag) {
        userType = flag;
        if (userType == StaticFlag.STUDENT_USER_TYPE) {
            studentView.setTextColor(getResources().getColor(R.color.white));
            studentView.setSelected(true);
            teacherView.setTextColor(getResources().getColor(R.color.main_blue));
            teacherView.setSelected(false);
        } else {
            teacherView.setTextColor(getResources().getColor(R.color.white));
            teacherView.setSelected(true);
            studentView.setTextColor(getResources().getColor(R.color.main_blue));
            studentView.setSelected(false);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            UIUtils.pushToBotom(this);
            return true;
        }
        return false;
    }

    private void getVerificationCode() {

        registerGetVerificationCodeViewBtn.setClickable(false);// 防止重复点击
        new TimeCount (60000,1000,registerGetVerificationCodeViewBtn).start();
        verificationCode=getVCode();
        registerVerificationCodeView.setText(verificationCode);
    }

    class TimeCount extends CountDownTimer {
        public TextView btn;

        public TimeCount(long millisInFuture, long countDownInterval, TextView btn) {
            super(millisInFuture, countDownInterval);
            this.btn = btn;
        }

        @Override
        public void onFinish() {// 计时完毕
            btn.setClickable(true);// 防止重复点击
            btn.setTextColor(getResources().getColor(R.color.main_blue));
            btn.setBackgroundResource(R.drawable.round_conner_line_btn_bg);
            btn.setText("重新获取");
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            btn.setClickable(false);// 防止重复点击
            btn.setTextColor(getResources().getColor(R.color.gray));
            btn.setBackgroundResource(R.drawable.round_conner_line_gray);
            btn.setText("还剩" + millisUntilFinished / 1000 + "s");
        }
    }
}

