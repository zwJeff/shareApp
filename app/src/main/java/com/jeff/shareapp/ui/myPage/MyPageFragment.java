package com.jeff.shareapp.ui.myPage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jeff.shareapp.R;
import com.jeff.shareapp.model.UserinfoModel;
import com.jeff.shareapp.core.MyApplication;
import com.jeff.shareapp.net.MyImageLoader;
import com.jeff.shareapp.ui.MainActivity;
import com.jeff.shareapp.ui.login.LoginActivity;
import com.jeff.shareapp.util.StaticFlag;
import com.jeff.shareapp.util.UIUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MyPageFragment extends Fragment implements View.OnClickListener {


    @InjectView(R.id.my_page_user_pic)
    RoundedImageView myImagePic;
    @InjectView(R.id.my_page_username)
    TextView userName;
    @InjectView(R.id.mapage_account_manage)
    RelativeLayout accountManageLayout;
    @InjectView(R.id.mypage_download_manage)
    RelativeLayout downloadManage;
    @InjectView(R.id.mapage_my_share_manage)
    RelativeLayout myShareManageLayout;
    @InjectView(R.id.mapage_setting)
    RelativeLayout settingLayout;

    private UserinfoModel user;

    public MyPageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_page, container, false);
        ButterKnife.inject(this, view);
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        if (MyApplication.getMyApplication().isLogin) {
            user = MyApplication.getMyApplication().getDataPref().getLocalData(UserinfoModel.class);
        }
        initView();
    }

    private void initView() {

        myImagePic.setOnClickListener(this);
        accountManageLayout.setOnClickListener(this);
        downloadManage.setOnClickListener(this);
        myShareManageLayout.setOnClickListener(this);
        settingLayout.setOnClickListener(this);
        if(MyApplication.getMyApplication().isLogin){
            userName.setText(user.getUsername() + (user.getUserType() == StaticFlag.TEACHER_USER_TYPE ? "老师" : ""));
            if (user.getUserHeadUrl() != null) {
                new MyImageLoader(getContext()).loadImage(StaticFlag.FILE_URL + user.getUserHeadUrl(), myImagePic);
            } else
                myImagePic.setImageResource(R.mipmap.not_login_head_img);
        }else{
            userName.setText("未登录");
            myImagePic.setImageResource(R.mipmap.not_login_head_img);
        }

    }

    @Override
    public void onClick(View v) {

        if (!MyApplication.getMyApplication().isLogin && v.getId() != R.id.mapage_setting) {
            Intent i2 = new Intent(getActivity(), LoginActivity.class);
            startActivity(i2);
            UIUtils.popToTop(getActivity());
            return;
        }

        switch (v.getId()) {
            case R.id.my_page_user_pic:
                Intent i = new Intent(getActivity(), UploadHeadPicActivity.class);
                getActivity().startActivity(i);
                UIUtils.popToLeft(getActivity());
                getActivity().finish();
                break;
            case R.id.mapage_account_manage:
                Intent i2 = new Intent(getActivity(), AccountActivity.class);
                startActivity(i2);
                UIUtils.popToLeft(getActivity());
                break;
            case R.id.mypage_download_manage:
                Intent i4 = new Intent(getActivity(), DownloadListActivity.class);
                startActivity(i4);
                UIUtils.popToLeft(getActivity());
                break;
            case R.id.mapage_my_share_manage:
                Intent i3 = new Intent(getActivity(), ShareRecordActivity.class);
                startActivity(i3);
                UIUtils.popToLeft(getActivity());
                break;
            case R.id.mapage_setting:
                Intent i5 = new Intent(getActivity(), SettingActivity.class);
                startActivity(i5);
                UIUtils.popToLeft(getActivity());
                break;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);   //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        } else Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
        return sdDir.toString();

    }


}
