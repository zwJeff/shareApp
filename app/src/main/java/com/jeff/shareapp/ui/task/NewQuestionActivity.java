package com.jeff.shareapp.ui.task;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jeff.shareapp.R;
import com.jeff.shareapp.model.UserinfoModel;
import com.jeff.shareapp.ui.BasicActivity;
import com.jeff.shareapp.ui.CustomVIew.MyDialog;
import com.jeff.shareapp.ui.CustomVIew.OnBtnClickListemer;
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

public class NewQuestionActivity extends BasicActivity implements View.OnClickListener {

    @InjectView(R.id.new_question_describe)
    EditText questionDescribe;
    @InjectView(R.id.new_question_select_layout)
    LinearLayout selectLayout;
    @InjectView(R.id.new_question_answer_a)
    EditText questionAnswerA;
    @InjectView(R.id.new_question_answer_b)
    EditText questionAnswerB;
    @InjectView(R.id.new_question_answer_c)
    EditText questionAnswerC;
    @InjectView(R.id.new_question_answer_d)
    EditText questionAnswerD;
    @InjectView(R.id.new_question_answer_true)
    EditText questionTrueAnswer;
    @InjectView(R.id.new_question_num)
    TextView questionNumText;
    @InjectView(R.id.new_question_type_spinner)
    Spinner questionTypeSelect;
    @InjectView(R.id.new_question_ok_button)
    Button okBtn;
    @InjectView(R.id.lable_title)
    TextView title;
    @InjectView(R.id.img_left)
    ImageView backPic;

    private ArrayList<Integer> questions;
    private ArrayAdapter mSpinnerAdapter;
    private int questionType = 0;
    private int resourceId;
    private int testPaperId;


    public Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            endWait();
            switch (msg.what) {
                case 1:
                    if (questions.size() >= 10) {
                        newTaskPaper();
                    } else {

                        NewQuestionActivity.startActivity(NewQuestionActivity.this, resourceId, questions);
                        finish();
                    }
                    break;
                case 2:
                    NewTaskActivity.startActivity(NewQuestionActivity.this, resourceId, testPaperId);
                    break;
                case 0:
                    Toast.makeText(NewQuestionActivity.this, msg.getData().getString("failure_message"), Toast.LENGTH_SHORT).show();
                    break;
                case StaticFlag.ERROR:
                    try {
                        Thread.currentThread().sleep(1000);//毫秒
                    } catch (Exception e) {
                    }
                    Toast.makeText(NewQuestionActivity.this, msg.getData().getString("message"), Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public static void startActivity(Activity activity, int resourceId, ArrayList<Integer> questions) {
        Intent i = new Intent(activity, NewQuestionActivity.class);
        i.putExtra("resource_id", resourceId);
        i.putIntegerArrayListExtra("questions", questions);
        activity.startActivity(i);
        UIUtils.popToLeft(activity);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UIUtils.openImmerseStatasBarMode(this);
        questions = getIntent().getIntegerArrayListExtra("questions");
        resourceId = getIntent().getIntExtra("resource_id", -1);
        setContentView(R.layout.activity_new_question);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {

        title.setText("新建习题");
        backPic.setVisibility(View.VISIBLE);
        backPic.setOnClickListener(this);
        int questionNum = questions.size() + 1;
        questionNumText.setText("第" + questionNum + "题/共十题");
        okBtn.setOnClickListener(this);
        String[] mItems = {"请选择题目类型", "选择题", "问答题"};
        mSpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, mItems);
        questionTypeSelect.setAdapter(mSpinnerAdapter);
        questionTypeSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                questionType = position - 1;
                if (questionType == 0) {
                    selectLayout.setVisibility(View.VISIBLE);

                    questionDescribe.setText("测试");
                    questionAnswerA.setText("这是A选项的答案");
                    questionAnswerB.setText("这是B选项的答案");
                    questionAnswerC.setText("这是C选项的答案");
                    questionAnswerD.setText("这是D选项的答案");

                    questionTrueAnswer.setText("测试");
                }
                if (questionType == 1) {
                    selectLayout.setVisibility(View.GONE);

                    /**
                     * 填充测试数据
                     */
                    questionDescribe.setText("测试");
                    questionTrueAnswer.setText("测试");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (questions.size() + 1 >= 10)
            okBtn.setText("完   成");
        else
            okBtn.setText("下一题");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_left:
                MyDialog m = new MyDialog(this);
                m.setContentText("您确定要停止新建习题吗？");
                m.setBtnClick(new OnBtnClickListemer() {
                    @Override
                    public void OnOKBtnClick() {
                        finish();
                    }

                    @Override
                    public void OnCancleBtnClick() {

                    }
                });
                m.showDiglog();
                break;
            case R.id.new_question_ok_button:

                attemptAddQuestion();
                break;
        }
    }

    private void attemptAddQuestion() {
        // Reset errors.
        questionAnswerA.setError(null);
        questionAnswerB.setError(null);
        questionAnswerC.setError(null);
        questionAnswerD.setError(null);
        questionDescribe.setError(null);
        questionTrueAnswer.setError(null);




        boolean cancel = false;
        View focusView = null;

        // Store values at the time of the login attempt.
        if (TextUtils.isEmpty(questionDescribe.getText().toString())) {
            questionDescribe.setError("题目描述不能为空！");
            focusView = questionDescribe;
            cancel = true;
        }

        if (questionType != 0 && questionType != 1) {
            cancel = true;
            Toast.makeText(this, "请选择题目类型", Toast.LENGTH_LONG).show();
        }

        if (questionType == StaticFlag.QUESTION_SELECT) {

            if (TextUtils.isEmpty(questionAnswerA.getText().toString())) {
                questionAnswerA.setError("选项不能为空！");
                focusView = questionAnswerA;
                cancel = true;
            }
            if (TextUtils.isEmpty(questionAnswerB.getText().toString())) {
                questionAnswerA.setError("选项不能为空！");
                focusView = questionAnswerA;
                cancel = true;
            }
            if (TextUtils.isEmpty(questionAnswerC.getText().toString())) {
                questionAnswerA.setError("选项不能为空！");
                focusView = questionAnswerA;
                cancel = true;
            }
            if (TextUtils.isEmpty(questionAnswerD.getText().toString())) {
                questionAnswerA.setError("选项不能为空！");
                focusView = questionAnswerA;
                cancel = true;
            }
        }
        if (TextUtils.isEmpty(questionTrueAnswer.getText().toString())) {
            questionTrueAnswer.setError("选项不能为空！");
            focusView = questionTrueAnswer;
            cancel = true;
        }


        if (cancel) {
            focusView.requestFocus();
        } else {
            //登陆操作
            addQuestion();
        }
    }

    private void addQuestion() {

        HashMap<String, String> mParams = new HashMap<String, String>();

        mParams.put("question_description", questionDescribe.getText().toString());
        mParams.put("description_type", questionType + "");
        mParams.put("answer_a", questionAnswerA.getText().toString());
        mParams.put("answer_b", questionAnswerB.getText().toString());
        mParams.put("answer_c", questionAnswerC.getText().toString());
        mParams.put("answer_d", questionAnswerD.getText().toString());
        mParams.put("true_answer", questionTrueAnswer.getText().toString());
        mParams.put("total_score", 100 + "");

        startWait();
        new MyVolley<UserinfoModel>(StaticFlag.ADD_QUESTION, mParams,
                new MyVolleyListener() {
                    @Override
                    public void onSuccess(Object data) {

                        Gson gson = FormatUtil.getFormatGson();
                        String jsonResult = gson.toJson(data);
                        int d = gson.fromJson(jsonResult, new TypeToken<Integer>() {
                        }.getType());
                        questions.add(d);
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

    private void newTaskPaper() {

        HashMap<String, String> mParams = new HashMap<String, String>();

        mParams.put("question0_id", questions.get(0) + "");
        mParams.put("question1_id", questions.get(1) + "");
        mParams.put("question2_id", questions.get(2) + "");
        mParams.put("question3_id", questions.get(3) + "");
        mParams.put("question4_id", questions.get(4) + "");
        mParams.put("question5_id", questions.get(5) + "");
        mParams.put("question6_id", questions.get(6) + "");
        mParams.put("question7_id", questions.get(7) + "");
        mParams.put("question8_id", questions.get(8) + "");
        mParams.put("question9_id", questions.get(9) + "");

        startWait();
        new MyVolley(StaticFlag.ARRANGEMENT_WORK, mParams,
                new MyVolleyListener() {
                    @Override
                    public void onSuccess(Object data) {

                        Gson gson = FormatUtil.getFormatGson();
                        String jsonResult = gson.toJson(data);
                        testPaperId = gson.fromJson(jsonResult, new TypeToken<Integer>() {
                        }.getType());
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            backPic.callOnClick();
            return true;
        } else
            return false;
    }
}
