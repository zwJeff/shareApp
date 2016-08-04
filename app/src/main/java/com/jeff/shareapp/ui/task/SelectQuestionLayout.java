package com.jeff.shareapp.ui.task;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jeff.shareapp.R;
import com.jeff.shareapp.model.QuestionModel;

/**
 * Created by 张武 on 2016/5/20.
 */
public class SelectQuestionLayout extends LinearLayout implements View.OnClickListener {

    private LinearLayout mLayout;
    private TextView questionDescribe;
    private LinearLayout answerALayout;
    private TextView answerA;
    private LinearLayout answerBLayout;
    private TextView answerB;
    private LinearLayout answerCLayout;
    private TextView answerC;
    private LinearLayout answerDLayout;
    private TextView answerD;
    private TextView questionNum;

    private TextView answerTextView;

    private QuestionModel mQuestion;

    public QuestionModel getmQuestion() {
        return mQuestion;
    }

    public void setmQuestion(QuestionModel mQuestion) {
        this.mQuestion = mQuestion;
    }


    public SelectQuestionLayout(Context context, QuestionModel q) {
        super(context);
        if (q.getQuestionType() != 0)
            throw new IllegalStateException("SelectQuestionLayout 传入的题型不是选择题！");

        mQuestion = q;
        mLayout = (LinearLayout) View.inflate(context, R.layout.select_question, null);
        questionDescribe = (TextView) mLayout.findViewById(R.id.question_describe);
        answerA = (TextView) mLayout.findViewById(R.id.question_a_answer);
        answerB = (TextView) mLayout.findViewById(R.id.question_b_answer);
        answerC = (TextView) mLayout.findViewById(R.id.question_c_answer);
        answerD = (TextView) mLayout.findViewById(R.id.question_d_answer);
        answerALayout= (LinearLayout) mLayout.findViewById(R.id.question_a_layout);
        answerBLayout= (LinearLayout) mLayout.findViewById(R.id.question_b_layout);
        answerCLayout= (LinearLayout) mLayout.findViewById(R.id.question_c_layout);
        answerDLayout= (LinearLayout) mLayout.findViewById(R.id.question_d_layout);
        questionNum = (TextView) mLayout.findViewById(R.id.question_id);
        answerTextView=(TextView) mLayout.findViewById(R.id.question_your_answer);

        questionDescribe.setText(mQuestion.getQuestionDescription());
        answerA.setText(mQuestion.getAnswerA());
        answerB.setText(mQuestion.getAnswerB());
        answerC.setText(mQuestion.getAnswerC());
        answerD.setText(mQuestion.getAnswerD());

        LinearLayout.LayoutParams mParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(mParams);
        this.setOrientation(VERTICAL);
        answerALayout.setOnClickListener(this);
        answerBLayout.setOnClickListener(this);
        answerCLayout.setOnClickListener(this);
        answerDLayout.setOnClickListener(this);

        this.addView(mLayout);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.question_a_layout:
                selectAnswer(answerA,answerALayout);
                break;

            case R.id.question_b_layout:
                selectAnswer(answerB,answerBLayout);
                break;

            case R.id.question_c_layout:
                selectAnswer(answerC,answerCLayout);
                break;

            case R.id.question_d_layout:
                selectAnswer(answerD,answerDLayout);
                break;
        }
    }

    private void selectAnswer(TextView your_answer,LinearLayout your_layout) {


        answerALayout.setSelected(false);
        answerBLayout.setSelected(false);
        answerCLayout.setSelected(false);
        answerDLayout.setSelected(false);
        your_layout.setSelected(true);

        String ans = "";
        switch (your_answer.getId()) {
            case R.id.question_a_answer:
                ans = "A";
                break;

            case R.id.question_b_answer:
                ans = "B";
                break;

            case R.id.question_c_answer:
                ans = "C";
                break;

            case R.id.question_d_answer:
                ans = "D";
                break;
        }
        mQuestion.setAnswerText(ans);

        answerTextView.setText(mQuestion.getAnswerText());
    }

    public void setQuestionNum(int i) {
        questionNum.setText(i + "");
    }

}
