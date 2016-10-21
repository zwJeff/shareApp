package com.jeff.shareapp.ui.task;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.jeff.shareapp.util.FormatUtil;
import com.jeff.shareapp.util.MyApplication;
import com.jeff.shareapp.util.MyVolley;
import com.jeff.shareapp.util.MyVolleyListener;
import com.jeff.shareapp.util.StaticFlag;
import com.jeff.shareapp.util.StringUtil;
import com.jeff.shareapp.util.UIUtils;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class GiveTaskToStudentActivity extends BasicActivity implements View.OnClickListener {

    @InjectView(R.id.find_student_key_text)
    EditText findStudentKeyText;
    @InjectView(R.id.find_student_btn)
    ImageView findBtn;
    @InjectView(R.id.find_student_result_list)
    LinearLayout findResultLayout;
    @InjectView(R.id.add_student_list)
    LinearLayout addStudentLayout;
    @InjectView(R.id.add_student_finish_btn)
    Button addStudentFinishBtn;
    @InjectView(R.id.lable_title)
    TextView title;
    @InjectView(R.id.img_left)
    ImageView backPic;

    private String key;
    private LayoutInflater inflater;

    private static int FIND_RESULT_MAX_SHOW_NUM = 3;

    private ArrayList<UserinfoModel> findResultList;
    private ArrayList<UserinfoModel> addStudentList;
    private int taskId;


    public Handler myHandler = new Handler() {
        public void handleMessage(final Message msg) {
            endWait();
            switch (msg.what) {
                case 1:
                    fillList();

                    break;
                case 2:
                    addToView();
                    break;
                case 0:
                    Toast.makeText(GiveTaskToStudentActivity.this, msg.getData().getString("failure_message"), Toast.LENGTH_SHORT).show();
                    break;
                case StaticFlag.ERROR:
                    Toast.makeText(GiveTaskToStudentActivity.this, msg.getData().getString("message"), Toast.LENGTH_SHORT).show();
                    break;


            }


            super.handleMessage(msg);
        }
    };


    public static void startActivity(Activity activity, int taskId) {

        Intent i = new Intent(activity, GiveTaskToStudentActivity.class);
        i.putExtra("taskId", taskId);
        activity.startActivity(i);
        UIUtils.popToLeft(activity);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_task_to_student);
        ButterKnife.inject(this);
        taskId=getIntent().getIntExtra("taskId",-1);
        UIUtils.openImmerseStatasBarMode(this);
        // initData();
        initView();
    }

    private void initView() {

        findBtn.setOnClickListener(this);
        backPic.setOnClickListener(this);
        addStudentFinishBtn.setOnClickListener(this);
        inflater = LayoutInflater.from(this);
        title.setText("布置作业");
        backPic.setVisibility(View.VISIBLE);
        addStudentList = new ArrayList<UserinfoModel>();
    }


    private void fillList() {

        findResultLayout.removeAllViews();

        if (findResultList.size() > 1) {
            int size = findResultList.size() < FIND_RESULT_MAX_SHOW_NUM ? findResultList.size() : FIND_RESULT_MAX_SHOW_NUM;
            for (int i = 0; i < size; i++) {
                final UserinfoModel u = findResultList.get(i);
                // 获取需要添加的布局
                final LinearLayout layout = (LinearLayout) inflater.inflate(
                        R.layout.item_student_list, null).findViewById(R.id.item_student_item);
                // 将布局加入到当前布局中
                ((TextView) layout.getChildAt(0)).setText(u.getUsername());
                ((TextView) layout.getChildAt(1)).setText(u.getTelephone());
                LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.dip2px(this, 50));
                mParams.setMargins(0, 2, 0, 2);
                layout.setLayoutParams(mParams);
                ((TextView) layout.getChildAt(2)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!addStudentList.contains(u)) {
                            addStudentList.add(u);
                            addToNet();
                            ((TextView) layout.getChildAt(2)).setClickable(false);
                            ((TextView) layout.getChildAt(2)).setText("已添加");
                        } else {
                            Toast.makeText(GiveTaskToStudentActivity.this, "该用户已添加!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                findResultLayout.addView(layout);
            }
        } else {
            TextView layout = new TextView(this);
            layout.setText("未搜索到相匹配的用户");
            layout.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.dip2px(this, 50));
            mParams.setMargins(0, 2, 0, 2);
            layout.setLayoutParams(mParams);
            findResultLayout.addView(layout);
        }


    }

    private void addToNet() {

        UserinfoModel u = addStudentList.get(addStudentList.size() - 1);

        if (u != null) {
            startWait();
            HashMap<String, String> mParams = new HashMap<String, String>();
            mParams.put("token", MyApplication.getMyApplication().getUserinfo().getToken());
            mParams.put("student_id", u.getUserId() + "");
            mParams.put("task_id", taskId + "");

            new MyVolley(StaticFlag.ADD_STUDENT, mParams,
                    new MyVolleyListener() {
                        @Override
                        public void onSuccess(Object data) {

                            Message message = Message.obtain();
                            message.what = 2;
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

    }

    private void addToView() {

        UserinfoModel u = addStudentList.get(addStudentList.size() - 1);
        if (u != null) {
            // 获取需要添加的布局
            LinearLayout layout = (LinearLayout) inflater.inflate(
                    R.layout.item_student_list, null).findViewById(R.id.item_student_item);
            // 将布局加入到当前布局中
            ((TextView) layout.getChildAt(0)).setText(u.getUsername());
            ((TextView) layout.getChildAt(1)).setText(u.getTelephone());
            ((TextView) layout.getChildAt(2)).setVisibility(View.GONE);
            LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.dip2px(this, 50));
            mParams.setMargins(0, 2, 0, 2);
            layout.setLayoutParams(mParams);
            addStudentLayout.addView(layout);
        }
        addStudentFinishBtn.bringToFront();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_left:
                finish();
                break;
            case R.id.find_student_btn:
                key = findStudentKeyText.getText().toString();
                if (TextUtils.isEmpty(key)) {
                    Toast.makeText(this, "请输入搜索词！", Toast.LENGTH_LONG).show();
                } else {
                    findStudent();
                }
                break;
            case R.id.add_student_finish_btn:
                finish();
                break;

        }
    }

    private void findStudent() {

        startWait();
        HashMap<String, String> mParams = new HashMap<String, String>();
        mParams.put("token", MyApplication.getMyApplication().getUserinfo().getToken());
        mParams.put("key", key);
        new MyVolley(StaticFlag.FIND_STUDENT, mParams,
                new MyVolleyListener() {
                    @Override
                    public void onSuccess(Object data) {

                        Gson gson = FormatUtil.getFormatGson();
                        String jsonResult = gson.toJson(data);
                        findResultList = gson.fromJson(jsonResult, new TypeToken<ArrayList<UserinfoModel>>() {
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
}
