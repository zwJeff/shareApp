package com.jeff.shareapp.ui.myPage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jeff.shareapp.R;
import com.jeff.shareapp.model.UserinfoModel;
import com.jeff.shareapp.ui.BasicActivity;
import com.jeff.shareapp.ui.login.GetPasswordBackActivity;
import com.jeff.shareapp.util.MyApplication;
import com.jeff.shareapp.util.MyImageLoader;
import com.jeff.shareapp.util.StaticFlag;
import com.jeff.shareapp.util.StringUtil;
import com.jeff.shareapp.util.UIUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AccountActivity extends BasicActivity implements View.OnClickListener {

    @InjectView(R.id.lable_title)
    TextView title;
    @InjectView(R.id.img_left)
    ImageView backPic;
    @InjectView(R.id.account_bind_email)
    RelativeLayout bindEmail;
    @InjectView(R.id.account_change_password)
    RelativeLayout changePassword;
    @InjectView(R.id.my_page_user_pic)
    RoundedImageView myHeadImage;
    @InjectView(R.id.my_page_username)
    TextView userNameView;
    @InjectView(R.id.my_page_user_telephone)
    TextView telephoneView;

    private UserinfoModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ButterKnife.inject(this);
        user= MyApplication.getMyApplication().getUserinfo();
        initView();
    }

    private void initView() {
        userNameView.setText(user.getUsername());
        telephoneView.setText(StringUtil.encryptPhoneNumber(user.getTelephone()));

        if(user.getUserHeadUrl()!=null)
            new MyImageLoader(this).loadImage(StaticFlag.FILE_URL+user.getUserHeadUrl(),myHeadImage);

        title.setText("账号管理");
        backPic.setOnClickListener(this);
        backPic.setVisibility(View.VISIBLE);
        bindEmail.setOnClickListener(this);
        changePassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_left:
                finish();
                break;
            case R.id.account_bind_email:
                Intent i=new Intent(this,BindEmailActivity.class);
                startActivity(i);
                UIUtils.popToLeft(this);
                break;

            case R.id.account_change_password:
                GetPasswordBackActivity.startActivity(this,2);
                break;
        }
    }
}
