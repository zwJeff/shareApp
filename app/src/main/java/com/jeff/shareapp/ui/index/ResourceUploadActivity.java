package com.jeff.shareapp.ui.index;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jeff.shareapp.R;
import com.jeff.shareapp.model.CourseTypeModel;
import com.jeff.shareapp.model.UserinfoModel;
import com.jeff.shareapp.ui.BasicActivity;
import com.jeff.shareapp.ui.CustomVIew.MyDialog;
import com.jeff.shareapp.ui.CustomVIew.OnBtnClickListemer;
import com.jeff.shareapp.ui.MainActivity;
import com.jeff.shareapp.ui.task.NewTaskActivity;
import com.jeff.shareapp.util.FormatUtil;
import com.jeff.shareapp.core.MyApplication;
import com.jeff.shareapp.net.MyVolley;
import com.jeff.shareapp.net.MyVolleyListener;
import com.jeff.shareapp.util.StaticFlag;
import com.jeff.shareapp.util.StringUtil;
import com.jeff.shareapp.util.UIUtils;
import com.jeff.shareapp.net.UploadUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class ResourceUploadActivity extends BasicActivity implements View.OnClickListener {

    private TextView title;
    private ImageView backPic;
    private TextView uploadBtn;
    private EditText upload_resource_name;
    private EditText upload_resource_describe;
    private TextView uploadFinishBtn;
    private Spinner mCourseTypeSpinner;
    private ArrayAdapter mSpinnerAdapter;
    private List<CourseTypeModel> course;
    private String filePath;
    private String fileUrl;

    //来源标记，0-首页资源上传，1-新建任务是资源上传
    private int fromFlag = 0;

    public Handler myHandler = new Handler() {
        public void handleMessage(final Message msg) {

            switch (msg.what) {
                case StaticFlag.SUCCESS:
                    endWait();
                    final int resourceId = msg.getData().getInt("resourceId");
                    MyDialog m2 = new MyDialog(ResourceUploadActivity.this, 1);

                    if (fromFlag == 0)
                        m2.setContentText("恭喜你，上传成功！点击\"好的\"，返回首页。");
                    else
                        m2.setContentText("恭喜你，上传成功！点击\"好的\"，继续新建任务。");
                    m2.setBtnText("好的");
                    m2.setBtnClick(new OnBtnClickListemer() {
                        @Override
                        public void OnOKBtnClick() {
                            if (fromFlag == 0)
                                MainActivity.startActivity(ResourceUploadActivity.this);
                            else
                                NewTaskActivity.startActivity(ResourceUploadActivity.this, resourceId, -1);
                            finish();
                        }

                        @Override
                        public void OnCancleBtnClick() {

                        }
                    });
                    m2.showDiglog();

                    break;

                case 3:
                    endWait();
                    initView();
                    break;

                case StaticFlag.FAILURE:
                    endWait();
                    Toast.makeText(ResourceUploadActivity.this, msg.getData().getString("failure_message"), Toast.LENGTH_SHORT).show();
                    break;
                case StaticFlag.ERROR:
                    endWait();
                    Toast.makeText(ResourceUploadActivity.this, msg.getData().getString("message"), Toast.LENGTH_SHORT).show();
                    break;
                case StaticFlag.UPLOAD_SUCCESS:
                    uploadBtn.setText("上传成功！");
                    uploadBtn.setClickable(false);
                    fileUrl = msg.getData().getString("fileUrl");
                    break;
                case StaticFlag.UPLOAD_START:
                    uploadBtn.setBackground(getResources().getDrawable(R.drawable.round_conner_line_btn_down));
                    uploadBtn.setTextColor(getResources().getColor(R.color.orange));
                    uploadBtn.setText("正在上传 0%");
                    uploadBtn.setClickable(false);
                    break;
                case StaticFlag.UPLOAD_UPLOADING:
                    int t = msg.getData().getInt("percent", -1);
                    if (t != -1)
                        uploadBtn.setText("已上传 " + t + "%");
                    uploadBtn.setClickable(false);
                    break;

                case StaticFlag.UPLOAD_FAIL:
                    Toast.makeText(ResourceUploadActivity.this, "上传失败！", Toast.LENGTH_SHORT).show();
                    uploadBtn.setText("重新上传");
                    uploadBtn.setClickable(true);
                    break;


            }


            super.handleMessage(msg);
        }
    };

    public static void startActivity(Activity activity, int fromFlag) {
        Intent intent = new Intent(activity, ResourceUploadActivity.class);
        intent.putExtra("from_flag", fromFlag);
        activity.startActivity(intent);
        UIUtils.popToLeft(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UIUtils.openImmerseStatasBarMode(this);
        setContentView(R.layout.activity_resource_upload);
        this.fromFlag = getIntent().getIntExtra("from_flag", 0);
        initCourzeTypeData();
    }

    private void initView() {
        title = (TextView) findViewById(R.id.lable_title);
        title.setText("资源上传");
        backPic = (ImageView) findViewById(R.id.img_left);
        backPic.setVisibility(View.VISIBLE);
        backPic.setOnClickListener(this);
        uploadFinishBtn = (TextView) findViewById(R.id.upload_finish_btn);
        uploadFinishBtn.setOnClickListener(this);
        uploadBtn = (TextView) findViewById(R.id.upload_btn);
        uploadBtn.setOnClickListener(this);
        upload_resource_name = (EditText) findViewById(R.id.upload_resource_name);
        upload_resource_describe = (EditText) findViewById(R.id.upload_resource_describe);
        mCourseTypeSpinner = (Spinner) findViewById(R.id.upload_resource_course);

        String[] mItems = new String[course.size() + 1];
        mItems[0] = "请选择资源所属课程";
        for (int i = 1; i <= course.size(); i++) {
            mItems[i] = course.get(i - 1).getCourseName();
        }
        mSpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, mItems);

        mCourseTypeSpinner.setAdapter(mSpinnerAdapter);

    }

    private void initCourzeTypeData() {

        HashMap<String, String> mParams = new HashMap<String, String>();


        startWait();
        new MyVolley<CourseTypeModel>(StaticFlag.GET_ALL_COURSE, mParams,
                new MyVolleyListener() {
                    @Override
                    public void onSuccess(Object data) {

                        Gson gson = FormatUtil.getFormatGson();
                        String jsonResult = gson.toJson(data);
                        course = gson.fromJson(jsonResult, new TypeToken<List<CourseTypeModel>>() {
                        }.getType());

                        Message message = Message.obtain();
                        message.what = 3;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_left:
                final MyDialog myDialog1 = new MyDialog(this);
                myDialog1.setBtnClick(new OnBtnClickListemer() {
                    @Override
                    public void OnOKBtnClick() {
                        MainActivity.startActivity(ResourceUploadActivity.this);
                        finish();
                    }

                    @Override
                    public void OnCancleBtnClick() {
                    }
                });
                myDialog1.setContentText("返回后，当前输入信息将会丢失，您确认要退出吗？");
                myDialog1.showDiglog();
                break;
            case R.id.upload_finish_btn:
                uploadResourceInfo();


                break;

            case R.id.upload_btn:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                try {
                    startActivityForResult(Intent.createChooser(intent, "请选择学习资源文件"), 1);
                } catch (android.content.ActivityNotFoundException ex) {
                    // Potentially direct the user to the Market with a Dialog
                    Toast.makeText(this, "请安装文件管理器", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    private void uploadResourceInfo() {

        String resourceName = upload_resource_name.getText().toString();
        String resourceDescribe = upload_resource_describe.getText().toString();
        int courseId = mCourseTypeSpinner.getSelectedItemPosition();

        if (TextUtils.isEmpty(resourceName)) {

            Toast.makeText(this, "请输入资源名称！", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(resourceDescribe)) {

            Toast.makeText(this, "请输入资源描述！", Toast.LENGTH_LONG).show();
        } else if (courseId < 1) {
            Toast.makeText(this, "请选择资源所属课程！", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(fileUrl)) {

            Toast.makeText(this, "请上传文件！", Toast.LENGTH_LONG).show();
        } else {
            startWait();
            HashMap<String, String> mParams = new HashMap<String, String>();
            mParams.put("resource_name", resourceName);
            mParams.put("resource_url", fileUrl);
            mParams.put("resource_describe", resourceDescribe);
            mParams.put("resource_course_type", courseId + "");
            mParams.put("resource_file_type", StringUtil.fileType(fileUrl) + "");


            new MyVolley(StaticFlag.UPLOAD_RESOURCE, mParams,
                    new MyVolleyListener() {
                        @Override
                        public void onSuccess(Object data) {
                            Gson gson = FormatUtil.getFormatGson();
                            String jsonResult = gson.toJson(data);
                            String r = gson.fromJson(jsonResult, new TypeToken<String>() {
                            }.getType());

                            Message message = Message.obtain();
                            message.what = StaticFlag.SUCCESS;
                            Bundle b = new Bundle();
                            b.putInt("resourceId", Integer.parseInt(r));
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
    }

    /**
     * 根据返回选择的文件，来进行操作
     **/
    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        // TODO Auto-generated method stub
        if (resultCode == Activity.RESULT_OK) {
            // Get the Uri of the selected file
            Toast.makeText(this, filePath, Toast.LENGTH_LONG).show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    UploadUtil.uploadFile(new File(UploadUtil.getFileAbsolutePath(ResourceUploadActivity.this,data.getData())), StaticFlag.UPLOAD, new FileUploadListener() {
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
                            Bundle b = new Bundle();

                            Log.i("jeff", result);
                            b.putString("fileUrl","/upload"+ result);
                            message.setData(b);
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
        super.onActivityResult(requestCode, resultCode, data);
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
