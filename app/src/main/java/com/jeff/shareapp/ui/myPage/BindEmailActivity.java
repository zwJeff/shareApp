package com.jeff.shareapp.ui.myPage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jeff.shareapp.R;
import com.jeff.shareapp.ui.BasicActivity;
import com.jeff.shareapp.util.StringUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class BindEmailActivity extends BasicActivity implements View.OnClickListener {

    @InjectView(R.id.lable_title)
    TextView title;
    @InjectView(R.id.img_left)
    ImageView backPic;
    @InjectView(R.id.bind_email_email)
    EditText myEmailTextView;
    @InjectView(R.id.bind_email_verification_code)
    EditText verificationCodeTextView;
    @InjectView(R.id.bind_email_get_verification_code_btn)
    TextView verificationCodeBtn;
    @InjectView(R.id.bind_email_button)
    Button okBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_email);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {

        title.setText("绑定邮箱");
        backPic.setVisibility(View.VISIBLE);
        backPic.setOnClickListener(this);
        verificationCodeBtn.setOnClickListener(this);
        okBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_left:
                finish();
                break;
            case R.id.bind_email_get_verification_code_btn:
                getVerificationCode();
                break;
            case R.id.bind_email_button:
                checkInput();
                break;
        }
    }

    private void getVerificationCode() {


        String email = myEmailTextView.getText().toString();

        boolean cancel = true;
        View focusView = null;
        if (TextUtils.isEmpty(email)) {
            myEmailTextView.setError("邮箱不能为空！");
            focusView = myEmailTextView;
            cancel = true;
        }

        if (StringUtil.isEmail(email)) {
            myEmailTextView.setError("邮箱格式不正确！");
            focusView = myEmailTextView;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {

            finish();
        }
    }

    private void checkInput() {

        String email = myEmailTextView.getText().toString();
        String code = verificationCodeTextView.getText().toString();

        boolean cancel = true;
        View focusView = null;
        if (TextUtils.isEmpty(email)) {
            myEmailTextView.setError("邮箱不能为空！");
            focusView = myEmailTextView;
            cancel = true;
        }

        if (StringUtil.isEmail(email)) {
            myEmailTextView.setError("邮箱格式不正确！");
            focusView = myEmailTextView;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(code)) {
            verificationCodeTextView.setError("验证码不能为空！");
            focusView = verificationCodeTextView;
            cancel = true;
        }


        if (cancel) {
            focusView.requestFocus();
        } else {
            bindEmail();
            finish();
        }
    }

    private void bindEmail() {

        Toast.makeText(this, "邮箱绑定成功！", Toast.LENGTH_SHORT).show();
        finish();

    }
}
