package com.jeff.shareapp.ui.task;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jeff.shareapp.R;
import com.jeff.shareapp.model.UserinfoModel;
import com.jeff.shareapp.ui.BasicActivity;
import com.jeff.shareapp.ui.CustomVIew.MyDialog;
import com.jeff.shareapp.ui.CustomVIew.MyInputDialog;
import com.jeff.shareapp.ui.CustomVIew.OnBtnClickListemer;
import com.jeff.shareapp.ui.MainActivity;
import com.jeff.shareapp.ui.index.ResourceUploadActivity;
import com.jeff.shareapp.util.FormatUtil;
import com.jeff.shareapp.core.MyApplication;
import com.jeff.shareapp.net.MyVolley;
import com.jeff.shareapp.net.MyVolleyListener;
import com.jeff.shareapp.util.StaticFlag;
import com.jeff.shareapp.util.UIUtils;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NewTaskActivity extends BasicActivity implements View.OnClickListener {

    @InjectView(R.id.new_task_upload_resource)
    RelativeLayout resourceUpload;
    @InjectView(R.id.new_task_upload_question)
    RelativeLayout questionUpload;
    @InjectView(R.id.new_task_to_student)
    RelativeLayout toStudent;
    @InjectView(R.id.lable_title)
    TextView title;
    @InjectView(R.id.img_left)
    ImageView backPic;
    @InjectView(R.id.new_task_button)
    Button newTaskBtn;
    @InjectView(R.id.new_task_upload_resource_pic)
    ImageView resourceUploadPic;
    @InjectView(R.id.new_task_upload_question_pic)
    ImageView questionUploadPic;

    int taskId = -1;
    String taskName;
    //data
    private int resourceId = -1;
    private int testPaperId = -1;

    public Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            endWait();
            switch (msg.what) {
                case 1:
                    GiveTaskToStudentActivity.startActivity(NewTaskActivity.this, taskId);

                case 0:
                    Toast.makeText(NewTaskActivity.this, msg.getData().getString("failure_message"), Toast.LENGTH_SHORT).show();
                    break;
                case StaticFlag.ERROR:
                    try {
                    } catch (Exception e) {
                    }
                    Toast.makeText(NewTaskActivity.this, msg.getData().getString("message"), Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public static void startActivity(Activity activity, int resourceId, int testPaperId) {
        Intent i = new Intent(activity, NewTaskActivity.class);
        i.putExtra("resource_id", resourceId);
        i.putExtra("test_paper_id", testPaperId);
        activity.startActivity(i);
        UIUtils.pushToRight(activity);
    }


    public static void startActivity(Activity activity, int resourceId) {
        Intent i = new Intent(activity, NewTaskActivity.class);
        i.putExtra("resource_id", resourceId);
        activity.startActivity(i);
        UIUtils.popToLeft(activity);
    }

    public static void startActivity(Activity activity) {
        Intent i = new Intent(activity, NewTaskActivity.class);
        activity.startActivity(i);
        UIUtils.popToLeft(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UIUtils.openImmerseStatasBarMode(this);
        setContentView(R.layout.activity_new_task);
        ButterKnife.inject(this);
        Intent i = getIntent();
        this.resourceId = i.getIntExtra("resource_id", -1);
        this.testPaperId = i.getIntExtra("test_paper_id", -1);
        initView();
    }

    private void initView() {

        title.setText("新建任务");
        backPic.setVisibility(View.VISIBLE);
        backPic.setOnClickListener(this);
        resourceUpload.setOnClickListener(this);
        questionUpload.setOnClickListener(this);
        toStudent.setOnClickListener(this);
        newTaskBtn.setOnClickListener(this);
        if (resourceId != -1) {
            resourceUploadPic.setImageResource(R.mipmap.ic_duigou);
            resourceUpload.setBackgroundColor(getResources().getColor(R.color.finish_background));
            resourceUpload.setClickable(false);

        }
        if (testPaperId != -1) {
            questionUploadPic.setImageResource(R.mipmap.ic_duigou);
            questionUpload.setBackgroundColor(getResources().getColor(R.color.finish_background));
            questionUpload.setClickable(false);
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_left:
                MyDialog m = new MyDialog(this);
                m.setContentText("您确定要放弃此次新建的任务？");
                m.setBtnClick(new OnBtnClickListemer() {
                    @Override
                    public void OnOKBtnClick() {
                        MainActivity.startActivity(NewTaskActivity.this, StaticFlag.INDEXPAGE_FRAGMENT);
                        finish();
                    }

                    @Override
                    public void OnCancleBtnClick() {

                    }
                });
                m.showDiglog();
                break;
            case R.id.new_task_upload_resource:
                ResourceUploadActivity.startActivity(NewTaskActivity.this, 1);
                finish();
                break;
            case R.id.new_task_upload_question:
                ArrayList<Integer> questions = new ArrayList<Integer>();
                NewQuestionActivity.startActivity(NewTaskActivity.this, resourceId, questions);
                break;
            case R.id.new_task_to_student:
                if (resourceId == -1)
                    Toast.makeText(NewTaskActivity.this, "请先完成学习资料的上传！", Toast.LENGTH_LONG).show();
                else if (testPaperId == -1)
                    Toast.makeText(NewTaskActivity.this, "请先布置课后习题！", Toast.LENGTH_LONG).show();
                else {
                    if (taskId != -1) {
                        Message message = Message.obtain();
                        message.what = 1;
                        myHandler.sendMessage(message);
                    } else
                        inpuTaskName();
                }
                break;
            case R.id.new_task_button:
                final MyDialog m2 = new MyDialog(this);
                m2.setContentText("完成后无法再更改或者增加学生，确认完成学习任务的新建吗？");
                m2.setBtnClick(new OnBtnClickListemer() {
                    @Override
                    public void OnOKBtnClick() {
                        finish();
                    }

                    @Override
                    public void OnCancleBtnClick() {

                    }
                });
                m2.showDiglog();
                break;
        }

    }

    private void inpuTaskName() {
        final MyInputDialog m = new MyInputDialog(this);
        m.setBtnClick(new OnBtnClickListemer() {
            @Override
            public void OnOKBtnClick() {
                taskName = m.getInputText();
                saveTask();
            }

            @Override
            public void OnCancleBtnClick() {

            }
        });
        m.showDiglog();
    }

    private void saveTask() {

        HashMap<String, String> mParams = new HashMap<String, String>();

        mParams.put("resource_id", resourceId + "");
        mParams.put("paper_id", testPaperId + "");
        mParams.put("task_name", taskName);

        startWait();
        new MyVolley<UserinfoModel>(StaticFlag.ADD_TASK, mParams,
                new MyVolleyListener() {
                    @Override
                    public void onSuccess(Object data) {

                        Gson gson = FormatUtil.getFormatGson();
                        String jsonResult = gson.toJson(data);
                        taskId = gson.fromJson(jsonResult, new TypeToken<Integer>() {
                        }.getType());
                        Message message = Message.obtain();
                        message.what = 1;
                        myHandler.sendMessage(message);
                    }

                    @Override
                    public void onFailure(int failureCode, String failureMessage) {
                        Message message = Message.obtain();
                        message.what = 0;
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == event.KEYCODE_BACK) {
            backPic.callOnClick();
            return true;
        }
        return false;

    }
}
