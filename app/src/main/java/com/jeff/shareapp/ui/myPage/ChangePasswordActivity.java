package com.jeff.shareapp.ui.myPage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.jeff.shareapp.R;
import com.jeff.shareapp.model.UserinfoModel;
import com.jeff.shareapp.ui.BasicActivity;
import com.jeff.shareapp.ui.CustomVIew.MyDialog;
import com.jeff.shareapp.ui.CustomVIew.OnBtnClickListemer;
import com.jeff.shareapp.ui.login.LoginActivity;
import com.jeff.shareapp.core.MyApplication;
import com.jeff.shareapp.net.MyVolley;
import com.jeff.shareapp.net.MyVolleyListener;
import com.jeff.shareapp.util.StaticFlag;
import com.jeff.shareapp.util.UIUtils;

import java.util.HashMap;


public class ChangePasswordActivity extends BasicActivity implements OnClickListener {


    // UI references.

    private TextView title;
    private ImageView backPic;

    private EditText newLoginPasswordTextView;
    private EditText newLoginPasswordRepeatTextView;
    private Button newPasswordBtn;

    private String newLoginPassword;
    private String newLoginPasswordRepeat;
    public Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            endWait();
            switch (msg.what) {
                case StaticFlag.SUCCESS:
                    Toast.makeText(ChangePasswordActivity.this,"修改成功，请重新登录！", Toast.LENGTH_SHORT).show();
                    LoginActivity.startActivity(ChangePasswordActivity.this);
                    finish();
                    break;
                case StaticFlag.FAILURE:
                    Toast.makeText(ChangePasswordActivity.this, msg.getData().getString("failure_message"), Toast.LENGTH_SHORT).show();
                    break;
                case StaticFlag.ERROR:
                    try {
                        Thread.currentThread().sleep(1000);//毫秒
                    } catch (Exception e) {
                    }
                    Toast.makeText(ChangePasswordActivity.this, msg.getData().getString("message"), Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UIUtils.openImmerseStatasBarMode(this);
        setContentView(R.layout.activity_get_password_back2);
        // Set up the login form.
        title = (TextView) findViewById(R.id.lable_title);
        title.setText("修改密码");
        backPic = (ImageView) findViewById(R.id.img_left);
        backPic.setVisibility(View.VISIBLE);
        backPic.setOnClickListener(this);
        newLoginPasswordTextView=(EditText)findViewById(R.id.new_login_password);
        newLoginPasswordRepeatTextView=(EditText)findViewById(R.id.new_login_password_repeat);
        newPasswordBtn=(Button)findViewById(R.id.get_password_back_button) ;
        newPasswordBtn.setOnClickListener(this);

    }


    private void attemptGetPasswordBack() {

        // Reset errors.
        newLoginPasswordTextView.setError(null);
        newLoginPasswordRepeatTextView.setError(null);
        // Store values at the time of the login attempt.
        newLoginPassword = newLoginPasswordTextView.getText().toString();
        newLoginPasswordRepeat = newLoginPasswordRepeatTextView.getText().toString();
        boolean cancel = false;
        View focusView = null;


        if (TextUtils.isEmpty(newLoginPassword)) {
            newLoginPasswordTextView.setError("密码不能为空！");
            focusView = newLoginPasswordTextView;
            cancel = true;
        }
        if (TextUtils.isEmpty(newLoginPasswordRepeat)) {
            newLoginPasswordRepeatTextView.setError("密码不能为空！");
            focusView = newLoginPasswordTextView;
            cancel = true;
        }
        if (!newLoginPassword.equals(newLoginPasswordRepeat)) {
            newLoginPasswordRepeatTextView.setError("两次输入的密码不一致！");
            focusView = newLoginPasswordRepeatTextView;
            cancel = true;
        }




        if (cancel) {
            focusView.requestFocus();
        } else {

            MyDialog m=new MyDialog(this);
            m.setContentText("确认要修改密码吗？");
            m.setBtnClick(new OnBtnClickListemer() {
                @Override
                public void OnOKBtnClick() {

                    changePassword();
                }

                @Override
                public void OnCancleBtnClick() {

                }
            });
            m.showDiglog();
        }
    }




    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_left:
                Intent i = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                startActivity(i);
                UIUtils.pushToRight(ChangePasswordActivity.this);
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


    private void changePassword() {
        startWait();
        HashMap<String, String> mParams = new HashMap<String, String>();
        mParams.put("new_password", newLoginPassword);


         MyVolley.getMyVolley().addStringRequest(new TypeToken<Object>(){}.getType(),StaticFlag.CHANGE_PASSWORD, mParams,
                new MyVolleyListener() {
                    @Override
                    public void onSuccess(Object data) {

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
    private void getVerificationCode() {
    }
    
}

