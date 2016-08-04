package com.jeff.shareapp.ui.task;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jeff.shareapp.R;
import com.jeff.shareapp.model.QuestionModel;
import com.jeff.shareapp.ui.BasicActivity;
import com.jeff.shareapp.ui.CustomVIew.MyDialog;
import com.jeff.shareapp.ui.CustomVIew.OnBtnClickListemer;
import com.jeff.shareapp.ui.MainActivity;
import com.jeff.shareapp.util.StaticFlag;
import com.jeff.shareapp.util.UIUtils;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TestPaperActivity extends BasicActivity implements View.OnClickListener {

    @InjectView(R.id.lable_title)
    TextView title;
    @InjectView(R.id.img_left)
    ImageView backPic;
    @InjectView(R.id.test_paper_layout)
    LinearLayout testFormLayout;
    @InjectView(R.id.commit_test_paper)
    Button commitTestAnswer;

    private List<QuestionModel> questions;

    public static void startActivity(Activity activity, String taskId) {
        Intent intent = new Intent(activity, TestPaperActivity.class);
        intent.putExtra("taskId", taskId);
        activity.startActivity(intent);
        UIUtils.popToLeft(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_paper);
        ButterKnife.inject(this);
        initData();
        initView();
    }

    private void initData() {

        questions = new ArrayList<QuestionModel>();
        for (int i = 0; i < 9; i++) {
            QuestionModel q = new QuestionModel();
            q.setQuestionDescription("这是一个选择题，请根据直觉学则正确的答案：");
            q.setQuestionType(StaticFlag.QUESTION_SELECT);
            q.setAnswerA("昨天是周五，明天是周三");
            q.setAnswerB("昨天是周四，明天是周日");
            q.setAnswerC("昨天是周五，明天是周一");
            q.setAnswerD("昨天是周五，明天是周末");
            questions.add(q);
        }

        QuestionModel q = new QuestionModel();
        q.setQuestionDescription("这是一个问答题，请输入一段最美丽的文字：");
        q.setQuestionType(StaticFlag.QUESTION_TEXT);
        questions.add(q);

    }

    private void initView() {

        title.setText("课后习题");

        backPic.setVisibility(View.VISIBLE);

        backPic.setOnClickListener(this);

        commitTestAnswer.setOnClickListener(this);

        for (int i = 0; i < questions.size(); i++) {
            QuestionModel q = questions.get(i);
            if (q.getQuestionType() == 0) {
                SelectQuestionLayout mSQuestion = new SelectQuestionLayout(this, q);
                mSQuestion.setQuestionNum(i + 1);
                testFormLayout.addView(mSQuestion, i);
            } else {
                TextQuestionLayout mTQuestion = new TextQuestionLayout(this, q);
                mTQuestion.setQuestionNum(i + 1);
                testFormLayout.addView(mTQuestion, i);
            }
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.img_left:
                final MyDialog myDialog1 = new MyDialog(this);
                myDialog1.setBtnClick(new OnBtnClickListemer() {
                    @Override
                    public void OnOKBtnClick() {
                        finish();
                    }

                    @Override
                    public void OnCancleBtnClick() {
                    }
                });
                myDialog1.setContentText("返回后，当前答题内容将会丢失，您确认要退出吗？");
                myDialog1.show();
                break;
            case R.id.commit_test_paper:
                final MyDialog myDialog2 = new MyDialog(this);
                myDialog2.setBtnClick(new OnBtnClickListemer() {
                    @Override
                    public void OnOKBtnClick() {
                        commitTestAnswer();
                    }

                    @Override
                    public void OnCancleBtnClick() {
                    }
                });
                myDialog2.setContentText("提交课后习题后，学习任务也将提交完场，提交之后不可更改，您确认要提交吗？");
                myDialog2.show();
                break;

        }
    }

    private void commitTestAnswer() {

        Toast.makeText(this, "试卷已提交！", Toast.LENGTH_SHORT).show();

        MainActivity.startActivity(this, StaticFlag.TASKPAGE_FRAGMENT);

        finish();

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
