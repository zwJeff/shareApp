package com.jeff.shareapp.ui.task;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jeff.shareapp.R;
import com.jeff.shareapp.ui.BasicActivity;
import com.jeff.shareapp.ui.allPage.ResourceDetialActivity;
import com.jeff.shareapp.util.UIUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TaskDetialActivity extends BasicActivity implements View.OnClickListener {

    @InjectView(R.id.lable_title)
    TextView title;
    @InjectView(R.id.img_left)
    ImageView backPic;
    @InjectView(R.id.task_detial_xuexiziliao_btn)
    TextView resourceBtn;
    @InjectView(R.id.task_detial_xuexiziliao_form)
    LinearLayout resourceform;
    @InjectView(R.id.task_detial_kehouxiti_btn)
    TextView testBtn;
    @InjectView(R.id.task_detial_kehouxiti_form)
    LinearLayout testform;
    @InjectView(R.id.task_detial_download)
    Button downloadBtn;
    @InjectView(R.id.task_detial_go_detial)
    Button checkDetialBtn;
    @InjectView(R.id.task_detial_collect)
    Button collectBtn;
    @InjectView(R.id.task_detial_go_answer)
    Button answerBtn;

    public static void startActivity(Activity activity, String taskId) {
        Intent intent = new Intent(activity, TaskDetialActivity.class);
        intent.putExtra("taskId", taskId);
        activity.startActivity(intent);
        UIUtils.popToLeft(activity);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detial);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        title.setText("学习任务");
        backPic.setVisibility(View.VISIBLE);
        backPic.setOnClickListener(this);
        resourceBtn.setOnClickListener(this);
        testBtn.setOnClickListener(this);
        downloadBtn.setOnClickListener(this);
        checkDetialBtn.setOnClickListener(this);
        collectBtn.setOnClickListener(this);
        answerBtn.setOnClickListener(this);
        changeLabel(0);
    }

    private void changeLabel(int flag){
        if(flag==0){
            resourceBtn.setSelected(true);
            resourceBtn.setTextColor(getResources().getColor(R.color.white));
            resourceform.setVisibility(View.VISIBLE);
            testBtn.setSelected(false);
            testBtn.setTextColor(getResources().getColor(R.color.main_blue));
            testform.setVisibility(View.GONE);
        }else{
            testBtn.setSelected(true);
            testBtn.setTextColor(getResources().getColor(R.color.white));
            testform.setVisibility(View.VISIBLE);
            resourceBtn.setSelected(false);
            resourceBtn.setTextColor(getResources().getColor(R.color.main_blue));
            resourceform.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_left:
                finish();
                break;
            case R.id.task_detial_xuexiziliao_btn:
                changeLabel(0);
                break;
            case R.id.task_detial_kehouxiti_btn:
                changeLabel(1);
                break;
            case R.id.task_detial_download:
                Toast.makeText(this,"开始下载...",Toast.LENGTH_SHORT).show();
                break;
            case R.id.task_detial_go_detial:
                ResourceDetialActivity.startActivity(this,"");
                break;
            case R.id.task_detial_collect:
                Toast.makeText(this,"已收藏！",Toast.LENGTH_SHORT).show();
                break;
            case R.id.task_detial_go_answer:
                TestPaperActivity.startActivity(this,"");
                break;
        }
    }
}
