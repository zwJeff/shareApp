package com.jeff.shareapp.ui.login;

import android.app.Activity;
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
import com.jeff.shareapp.ui.CustomVIew.MyDialog;
import com.jeff.shareapp.ui.CustomVIew.OnBtnClickListemer;
import com.jeff.shareapp.ui.MainActivity;
import com.jeff.shareapp.ui.myPage.ChangePasswordActivity;
import com.jeff.shareapp.util.FormatUtil;
import com.jeff.shareapp.util.MyApplication;
import com.jeff.shareapp.util.MyVolley;
import com.jeff.shareapp.util.MyVolleyListener;
import com.jeff.shareapp.util.StaticFlag;
import com.jeff.shareapp.util.StringUtil;
import com.jeff.shareapp.util.UIUtils;

import java.util.HashMap;


public class GetPasswordBackActivity extends BasicActivity implements OnClickListener {


    // UI references.

    private TextView title;
    private ImageView backPic;

    private EditText getPasswordBackNameTextView;
    private EditText getPasswordBackTelephoneTextView;
    private EditText getPasswordBackVerificationCodeView;
    private TextView getPasswordBackGetVerificationCodeViewBtn;
    private Button getPasswordBackBtn;

    private String getPasswordBackName;
    private String getPasswordBackTelephone;
    private String getPasswordBackVerificationCode;
    private String verificationCode="default";

    //弹窗显示密码还是跳至修改密码页？ 1-显示 2-跳至修改密码
    private int showPassword = 1;


    public Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            endWait();
            switch (msg.what) {
                case StaticFlag.SUCCESS:

                    if (showPassword == 2) {
                        Intent i = new Intent(GetPasswordBackActivity.this, ChangePasswordActivity.class);
                        startActivity(i);
                        UIUtils.popToLeft(GetPasswordBackActivity.this);
                        finish();
                    } else {
                        String yourPassword = msg.getData().getString("password");
                        MyDialog m = new MyDialog(GetPasswordBackActivity.this, 1);
                        m.setContentText("你的登陆密码是 " + yourPassword + "，请登陆app后到我的空间修改密码！");
                        m.setBtnText("我知道了");
                        m.setBtnClick(new OnBtnClickListemer() {
                            @Override
                            public void OnOKBtnClick() {
                                finish();
                            }

                            @Override
                            public void OnCancleBtnClick() {

                            }
                        });
                        m.showDiglog();
                    }
                    break;
                case StaticFlag.FAILURE:
                    Toast.makeText(GetPasswordBackActivity.this, msg.getData().getString("failure_message"), Toast.LENGTH_SHORT).show();
                    break;
                case StaticFlag.ERROR:
                    try {
                        Thread.currentThread().sleep(1000);//毫秒
                    } catch (Exception e) {
                    }
                    Toast.makeText(GetPasswordBackActivity.this, msg.getData().getString("message"), Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    };


    public static void startActivity(Activity activity, int showOrJump) {
        Intent i = new Intent(activity, GetPasswordBackActivity.class);
        i.putExtra("show_or_jump", showOrJump);
        activity.startActivity(i);
        UIUtils.popToLeft(activity);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_password_back);
        showPassword = getIntent().getIntExtra("show_or_jump", 1);
        // Set up the login form.
        title = (TextView) findViewById(R.id.lable_title);
        if (showPassword == 1)
            title.setText("找回密码");
        else
            title.setText("修改密码");
        backPic = (ImageView) findViewById(R.id.img_left);
        backPic.setVisibility(View.VISIBLE);
        backPic.setOnClickListener(this);
        getPasswordBackNameTextView = (EditText) findViewById(R.id.get_password_back_user_name);
        getPasswordBackTelephoneTextView = (EditText) findViewById(R.id.get_password_back_telephone);
        getPasswordBackVerificationCodeView = (EditText) findViewById(R.id.get_password_back_verification_code);
        getPasswordBackGetVerificationCodeViewBtn = (TextView) findViewById(R.id.get_password_back_get_verification_code_btn);
        getPasswordBackGetVerificationCodeViewBtn.setOnClickListener(this);
        getPasswordBackBtn = (Button) findViewById(R.id.get_password_back_button);
        if (showPassword == 1)
            getPasswordBackBtn.setText("找回密码");
        else
            getPasswordBackBtn.setText("修改密码");
        getPasswordBackBtn.setOnClickListener(this);

    }


    private void attemptGetPasswordBack() {

        // Reset errors.
        getPasswordBackNameTextView.setError(null);
        getPasswordBackTelephoneTextView.setError(null);
        getPasswordBackVerificationCodeView.setError(null);
        // Store values at the time of the login attempt.
        getPasswordBackName = getPasswordBackNameTextView.getText().toString();
        getPasswordBackTelephone = getPasswordBackTelephoneTextView.getText().toString();
        getPasswordBackVerificationCode = getPasswordBackVerificationCodeView.getText().toString();
        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(getPasswordBackName)) {
            getPasswordBackNameTextView.setError("用户名不能为空！");
            focusView = getPasswordBackNameTextView;
            cancel = true;
        }

        if (TextUtils.isEmpty(getPasswordBackTelephone)) {
            getPasswordBackTelephoneTextView.setError("手机号不能为空！");
            focusView = getPasswordBackTelephoneTextView;
            cancel = true;
        }

        if (!StringUtil.isTelephone(getPasswordBackTelephone)) {
            getPasswordBackTelephoneTextView.setError("手机号不正确！");
            focusView = getPasswordBackTelephoneTextView;
            cancel = true;
        }

        if (TextUtils.isEmpty(getPasswordBackVerificationCode)) {
            getPasswordBackVerificationCodeView.setError("验证码不能为空！");
            focusView = getPasswordBackVerificationCodeView;
            cancel = true;
        }

        if (!getPasswordBackVerificationCode.equals(verificationCode)) {
            getPasswordBackVerificationCodeView.setError("验证码不正确！");
            focusView = getPasswordBackVerificationCodeView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            checkToNet();
        }
    }


    private void checkToNet() {
        startWait();
        HashMap<String, String> mParams = new HashMap<String, String>();
        mParams.put("telephone", getPasswordBackTelephone);
        mParams.put("username", getPasswordBackName);
        mParams.put("token", MyApplication.getMyApplication().getUserinfo().getToken());

        new MyVolley(StaticFlag.CHECK_NAME_PHONE, mParams,
                new MyVolleyListener() {
                    @Override
                    public void onSuccess(Object data) {

                        Gson gson = FormatUtil.getFormatGson();
                        String jsonResult = gson.toJson(data);
                        String password = gson.fromJson(jsonResult, new TypeToken<String>() {
                        }.getType());

                        Message message = Message.obtain();
                        message.what = StaticFlag.SUCCESS;
                        Bundle b = new Bundle();
                        b.putString("password", password);
                        message.setData(b);
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
                finish();
                break;

            case R.id.get_password_back_get_verification_code_btn:
                getVerificationCode();
                break;

            case R.id.get_password_back_button:
                attemptGetPasswordBack();
                break;
        }

    }


    private void getVerificationCode() {

        new TimeCount (60000,1000,getPasswordBackGetVerificationCodeViewBtn).start();
        verificationCode = getVCode();
        getPasswordBackVerificationCodeView.setText(verificationCode);
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
            btn.setTextColor(getResources().getColor(R.color.orange));
            btn.setBackgroundResource(R.drawable.round_conner_line_gray);
            btn.setText("还剩" + millisUntilFinished / 1000 + "s");
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            finish();
            return true;
        }
        return false;
    }
}

