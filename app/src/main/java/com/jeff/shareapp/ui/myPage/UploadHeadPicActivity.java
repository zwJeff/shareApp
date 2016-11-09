package com.jeff.shareapp.ui.myPage;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jeff.shareapp.R;
import com.jeff.shareapp.model.PictureListModel;
import com.jeff.shareapp.model.UserinfoModel;
import com.jeff.shareapp.ui.BasicActivity;
import com.jeff.shareapp.ui.CustomVIew.MyDialog;
import com.jeff.shareapp.ui.CustomVIew.OnBtnClickListemer;
import com.jeff.shareapp.ui.MainActivity;
import com.jeff.shareapp.ui.index.FileUploadListener;
import com.jeff.shareapp.ui.task.NewTaskActivity;
import com.jeff.shareapp.util.MyApplication;
import com.jeff.shareapp.util.MyImageLoader;
import com.jeff.shareapp.util.MyVolley;
import com.jeff.shareapp.util.MyVolleyListener;
import com.jeff.shareapp.util.StaticFlag;
import com.jeff.shareapp.util.UIUtils;
import com.jeff.shareapp.util.UploadUtil;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class UploadHeadPicActivity extends BasicActivity implements View.OnClickListener {

    @InjectView(R.id.lable_title)
    TextView title;
    @InjectView(R.id.img_left)
    ImageView backPic;
    @InjectView(R.id.upload_head_pic_image)
    RoundedImageView uploadImageView;
    @InjectView(R.id.upload_head_pic_btn)
    Button okBtn;
    private UserinfoModel user;
    private String imageUrl;
    private boolean isChooseImage = false;


    public Handler myHandler = new Handler() {
        public void handleMessage(final Message msg) {

            switch (msg.what) {
                case StaticFlag.UPLOAD_SUCCESS:
                    okBtn.setText("上传成功！");
                    okBtn.setClickable(false);
                    backPic.callOnClick();
                    break;
                case StaticFlag.UPLOAD_START:
                    okBtn.setBackground(getResources().getDrawable(R.drawable.round_conner_line_btn_down));
                    okBtn.setTextColor(getResources().getColor(R.color.orange));
                    okBtn.setText("正在上传 0%");
                    okBtn.setClickable(false);
                    break;
                case StaticFlag.UPLOAD_UPLOADING:
                    int t = msg.getData().getInt("percent", -1);
                    if (t != -1)
                        okBtn.setText("已上传 " + t + "%");
                    okBtn.setClickable(false);
                    break;

                case StaticFlag.UPLOAD_FAIL:
                    Toast.makeText(UploadHeadPicActivity.this, "上传失败！", Toast.LENGTH_SHORT).show();
                    okBtn.setText("重新上传");
                    okBtn.setClickable(true);
                    break;


            }


            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_head_pic);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        user = MyApplication.getMyApplication().getUserinfo();
        if (user.getUserHeadUrl() != null) {
            new MyImageLoader(this).loadImage(StaticFlag.FILE_URL + user.getUserHeadUrl(), uploadImageView);
        }
        title.setText("头像上传");
        backPic.setVisibility(View.VISIBLE);
        backPic.setOnClickListener(this);
        uploadImageView.setOnClickListener(this);
        okBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_left:
                MainActivity.startActivity(this, StaticFlag.MYPAGE_FRAGMENT);
                UIUtils.pushToRight(this);
                finish();
                break;
            case R.id.upload_head_pic_image:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 1);
                break;
            case R.id.upload_head_pic_btn:
                if (isChooseImage)
                    uploadImage();
                else Toast.makeText(this,"请先选择作为头像的图片！",Toast.LENGTH_SHORT).show();
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {//是否选择，没选择就不会继续
            Uri uri = data.getData();//得到uri，后面就是将uri转化成file的过程。
            uploadImageView.setImageURI(uri);
            imageUrl = uri.getPath();
            isChooseImage = true;
        }
    }

    private void uploadImage() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                UploadUtil.uploadFile(new File(imageUrl), StaticFlag.UPLOAD_IMAGE, new FileUploadListener() {
                    @Override
                    public void onStart() {

                        Message message = Message.obtain();
                        message.what = StaticFlag.UPLOAD_START;
                        myHandler.sendMessage(message);
                    }

                    @Override
                    public void onUploading(int percent) {
                        Message message = Message.obtain();
                        message.what = StaticFlag.UPLOAD_UPLOADING;
                        Bundle b = new Bundle();
                        b.putInt("percent", percent);
                        message.setData(b);
                        myHandler.sendMessage(message);
                    }

                    @Override
                    public void onSuccess(String result) {

                        Message message = Message.obtain();
                        message.what = StaticFlag.UPLOAD_SUCCESS;
                        user.setUserHeadUrl("headPic" + result);
                        upLoadImageUrl();
                        MyApplication.getMyApplication().setUserinfo(user);
                        MyApplication.getMyApplication().saveUserInfoToPreference();
                        myHandler.sendMessage(message);
                    }

                    @Override
                    public void onFile() {

                        Message message = Message.obtain();
                        message.what = StaticFlag.UPLOAD_FAIL;
                        myHandler.sendMessage(message);
                    }
                });
            }
        }).start();
    }


    private void upLoadImageUrl() {

        HashMap<String, String> mParams = new HashMap<String, String>();
        mParams.put("token", MyApplication.getMyApplication().getUserinfo().getToken());
        mParams.put("user_picture_url", user.getUserHeadUrl());

        new MyVolley<UserinfoModel>(StaticFlag.UPLOAD_IMAGE_URL, mParams,
                new MyVolleyListener() {
                    @Override
                    public void onSuccess(Object data) {

                    }

                    @Override
                    public void onFailure(int failureCode, String failureMessage) {

                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {

                    }
                }
        );

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == event.KEYCODE_BACK) {
            backPic.callOnClick();
            return true;
        }
        return false;

    }
}
