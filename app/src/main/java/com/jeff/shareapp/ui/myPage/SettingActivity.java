package com.jeff.shareapp.ui.myPage;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jeff.shareapp.R;
import com.jeff.shareapp.model.UserinfoModel;
import com.jeff.shareapp.ui.BasicActivity;
import com.jeff.shareapp.ui.login.LoginActivity;
import com.jeff.shareapp.ui.service.MyGetNotificationService;
import com.jeff.shareapp.util.MyApplication;
import com.jeff.shareapp.util.MyImageLoader;
import com.jeff.shareapp.util.MyVolley;
import com.jeff.shareapp.util.MyVolleyListener;
import com.jeff.shareapp.util.StaticFlag;
import com.jeff.shareapp.util.StringUtil;
import com.jeff.shareapp.util.UIUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SettingActivity extends BasicActivity implements View.OnClickListener {

    @InjectView(R.id.logout_btn)
    Button logoutBtn;
    @InjectView(R.id.lable_title)
    TextView title;
    @InjectView(R.id.img_left)
    ImageView backPic;
    @InjectView(R.id.my_page_user_pic)
    RoundedImageView myHeadImage;
    @InjectView(R.id.my_page_username)
    TextView userNameView;
    @InjectView(R.id.my_page_user_telephone)
    TextView telephoneView;

    private UserinfoModel user;


    public Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
                endWait();
            switch (msg.what) {
                case 1:
//                    Intent i=new Intent(SettingActivity.this, LoginActivity.class);
//                    startActivity(i);
//                    UIUtils.pushToRight(SettingActivity.this);
//                    finish();

                    //停止轮询服务
                    stopService(new Intent(SettingActivity.this, MyGetNotificationService.class));

                    Intent i = new Intent(SettingActivity.this, LoginActivity.class);
                    startActivity(i);
                    UIUtils.pushToRight(SettingActivity.this);
                    finish();
                  //  ActivityManager.getActivityManager().closeAllExcept(LoginActivity.class);
                    break;
                case -1:
                    Toast.makeText(SettingActivity.this, msg.getData().getString("failure_message"), Toast.LENGTH_SHORT).show();
                    break;
                case 0:
                    Toast.makeText(SettingActivity.this, msg.getData().getString("message"), Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.inject(this);
        user = MyApplication.getMyApplication().getUserinfo();
        initView();
    }

    private void initView() {
        userNameView.setText(user.getUsername());
        telephoneView.setText(StringUtil.encryptPhoneNumber(user.getTelephone()));

        if (user.getUserHeadUrl() != null)
            new MyImageLoader(this).loadImage(StaticFlag.FILE_URL + user.getUserHeadUrl(), myHeadImage);
        title.setText("设  置");
        backPic.setVisibility(View.VISIBLE);
        backPic.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_left:
                finish();
                break;
            case R.id.logout_btn:
                logout();
                break;
        }
    }

    private void logout() {

        startWait();

        HashMap<String, String> mParams = new HashMap<String, String>();
        mParams.put("token", MyApplication.getMyApplication().getUserinfo().getToken());

        new MyVolley<UserinfoModel>(StaticFlag.LOGOUT, mParams,
                new MyVolleyListener() {
                    @Override
                    public void onSuccess(Object data) {
                        Message message = Message.obtain();
                        message.what = 1;
                        myHandler.sendMessage(message);
                    }

                    @Override
                    public void onFailure(int failureCode, String failureMessage) {
                        Message message = Message.obtain();
                        message.what = -1;
                        Bundle b = new Bundle();
                        b.putString("failure_message", failureMessage);
                        message.setData(b);
                        myHandler.sendMessage(message);
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        Message message = Message.obtain();
                        message.what = 0;
                        Bundle b = new Bundle();
                        b.putSerializable("message", errorMessage);
                        message.setData(b);
                        myHandler.sendMessage(message);
                    }
                }
        );

    }
}
