package com.jeff.shareapp.ui.task;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeff.shareapp.R;
import com.jeff.shareapp.model.QuestionModel;

import static android.text.TextUtils.isEmpty;

/**
 * Created by 张武 on 2016/5/20.
 */
public class TextQuestionLayout extends LinearLayout{

    private LinearLayout mLayout;
    private TextView questionDescribe;
    private TextView questionNum;
    private EditText answer;

    private QuestionModel mQuestion;

    public QuestionModel getmQuestion() {
        return mQuestion;
    }

    public void setmQuestion(QuestionModel mQuestion) {
        this.mQuestion = mQuestion;
    }


    public TextQuestionLayout(Context context, QuestionModel q) {
        super(context);
        if (q.getQuestionType() != 1)
            throw new IllegalStateException("SelectQuestionLayout 传入的题型不是问答题！");

        mQuestion = q;
        mLayout = (LinearLayout) View.inflate(context, R.layout.text_question, null);
        questionNum = (TextView) mLayout.findViewById(R.id.question_id);
        questionDescribe = (TextView) mLayout.findViewById(R.id.question_describe);
        answer = (EditText) mLayout.findViewById(R.id.question_text_answer);

        answer.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(TextUtils.isEmpty(answer.getText().toString())){
                        mQuestion.setAnswerText(answer.getText().toString());
                    }
                }
            }
        });

        questionDescribe.setText(mQuestion.getQuestionDescription());


        LayoutParams mParams=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(mParams);
        this.setOrientation(VERTICAL);
        this.addView(mLayout);
    }

    public void setQuestionNum(int i) {
        questionNum.setText(i + "");
    }
}
