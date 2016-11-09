package com.jeff.shareapp.ui.allPage;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jeff.shareapp.R;
import com.jeff.shareapp.adapter.ShareRecordListAdapter;
import com.jeff.shareapp.model.CourseTypeModel;
import com.jeff.shareapp.model.ResourceModel;
import com.jeff.shareapp.model.ResourceRespModel;
import com.jeff.shareapp.ui.BasicActivity;
import com.jeff.shareapp.util.FormatUtil;
import com.jeff.shareapp.util.MyApplication;
import com.jeff.shareapp.util.MyVolley;
import com.jeff.shareapp.util.MyVolleyListener;
import com.jeff.shareapp.util.StaticFlag;
import com.jeff.shareapp.util.UIUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ResourceDetialActivity extends BasicActivity implements View.OnClickListener {

    @InjectView(R.id.img_left)
    ImageView titleBack;
    @InjectView(R.id.lable_title)
    TextView titleText;
    @InjectView(R.id.resource_detial_name)
    TextView resourceName;
    @InjectView(R.id.resource_detial_aurhor)
    TextView resourceAuthorAndTime;
    @InjectView(R.id.resource_detial_describe)
    TextView resourceDescribe;
    @InjectView(R.id.resource_detial_pic)
    ImageView resourcePic;
    @InjectView(R.id.resource_detial_download)
    Button downloadBtn;
    @InjectView(R.id.resource_detial_preview)
    Button previewBtn;
    @InjectView(R.id.resource_detial_collect)
    Button collectBtn;

    private boolean isCollect = false;

    private ResourceRespModel resource;
    private String resourceId;


    public Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            endWait();
            switch (msg.what) {
                case 1:
                    initView();
                    break;

                case 0:
                    Toast.makeText(ResourceDetialActivity.this, msg.getData().getString("failure_message"), Toast.LENGTH_SHORT).show();
                    break;
                case StaticFlag.ERROR:
                    try {
                    } catch (Exception e) {
                    }
                    Toast.makeText(ResourceDetialActivity.this, msg.getData().getString("message"), Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public static void startActivity(Activity activity, String resourceId) {
        Intent intent = new Intent(activity, ResourceDetialActivity.class);
        intent.putExtra("resourceId", resourceId);
        activity.startActivity(intent);
        UIUtils.popToLeft(activity);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UIUtils.openImmerseStatasBarMode(this);
        setContentView(R.layout.activity_resource_detial);
        ButterKnife.inject(this);
        resourceId = getIntent().getStringExtra("resourceId");
        getData();
    }



    private void getData() {
        HashMap<String, String> mParams = new HashMap<String, String>();
        mParams.put("token", MyApplication.getMyApplication().getUserinfo().getToken());
        mParams.put("resource_id", resourceId);
        startWait();
        MyVolley.getMyVolley().addStringRequest(new TypeToken<ResourceRespModel>(){}.getType(),StaticFlag.GET_RESOURCE_DETIAL, mParams,
                new MyVolleyListener<ResourceRespModel>() {
                    @Override
                    public void onSuccess(ResourceRespModel data) {

                        resource = data;
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
                        message.what = StaticFlag.ERROR;
                        Bundle b = new Bundle();
                        b.putSerializable("message", errorMessage);
                        message.setData(b);
                        myHandler.sendMessage(message);
                    }
                }
        );
    }

    private void initView() {
        titleBack.setVisibility(View.VISIBLE);
        titleText.setText("资源详情");
        titleBack.setOnClickListener(this);
        collectBtn.setOnClickListener(this);
        downloadBtn.setOnClickListener(this);

        resourcePic.setImageResource(UIUtils.getPicByType(resource.getResourceFileType()));
        resourceName.setText(resource.getResourceName());
        resourceAuthorAndTime.setText(resource.getAuthorName() + "上传于 " + FormatUtil.DateFormat(resource.getResourceUploadTime()));
        resourceDescribe.setText(resource.getResourceDescribe());
        downloadBtn.setText("下载(" + resource.getResourceDownloadTime() + "次)");
        collectBtn.setText("收藏(" + resource.getResourceCollectTime() + "次)");
        if (resource.getIsCollected() == 1) {
            isCollect = true;
        } else {
            isCollect = false;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_left:
                finish();
                break;
            case R.id.resource_detial_download:
                /**
                 * 下载方法一 调用浏览器
                 */
//                Uri uri = null;
//                try {
//                    uri = Uri.parse(StaticFlag.FILE_URL+ URLEncoder.encode(resource.getResourceUrl(),"UTF-8"));
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//                Intent downloadIntent = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(downloadIntent);

                /**
                 * 下载方法二 调用DownloadManager
                 */
                DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

                Uri uri = null;
                try {
                    uri = Uri.parse(StaticFlag.FILE_URL+ URLEncoder.encode(resource.getResourceUrl(),"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                DownloadManager.Request request = new DownloadManager.Request(uri);

                //设置允许使用的网络类型，这里是移动网络和wifi都可以
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);

                //禁止发出通知，既后台下载，如果要使用这一句必须声明一个权限：android.permission.DOWNLOAD_WITHOUT_NOTIFICATION
                //request.setShowRunningNotification(false);

                //显示下载界面
                request.setVisibleInDownloadsUi(true);
        /*设置下载后文件存放的位置,如果sdcard不可用，那么设置这个将报错，因此最好不设置如果sdcard可用，下载后的文件        在/mnt/sdcard/Android/data/packageName/files目录下面，如果sdcard不可用,设置了下面这个将报错，不设置，下载后的文件在/cache这个  目录下面*/
              //  request.setDestinationInExternalFilesDir(this, null, "tar.apk");
                long id = downloadManager.enqueue(request);
//TODO 把id保存好，在接收者里面要用，最好保存在Preferences里面

                break;

            case R.id.resource_detial_collect:

                if (isCollect) {
                    collectBtn.setText("收藏(" + (resource.getResourceCollectTime() + 1) + "次)");
                    Toast.makeText(ResourceDetialActivity.this, "取消收藏成功！", Toast.LENGTH_SHORT).show();
                    collectResource();
                    isCollect = false;
                } else {
                    collectBtn.setText("已收藏(" + (resource.getResourceCollectTime()) + "次)");
                    Toast.makeText(ResourceDetialActivity.this, "收藏成功！", Toast.LENGTH_SHORT).show();
                    collectResource();
                    isCollect = true;
                }
                break;
            case R.id.resource_detial_preview:
                break;
        }
    }


    private void collectResource() {
        final String[] msg = new String[1];
        HashMap<String, String> mParams = new HashMap<String, String>();
        mParams.put("token", MyApplication.getMyApplication().getUserinfo().getToken());
        mParams.put("resource_id", resourceId + "");

        new MyVolley<String>(StaticFlag.COLLECT, mParams,
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

}
