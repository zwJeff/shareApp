package com.jeff.shareapp.ui.CustomVIew;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.jeff.shareapp.R;

/**
 * Created by 张武 on 2016/2/24.
 */
public class MyDialog extends Dialog {

    private Button okBtn;
    private Button cancleBtn;
    private TextView contentTextView;
    private int buttonNum=2;

    //接口对象
    private OnBtnClickListemer mOnOKBtnClickListemer;

    public MyDialog(Context context, int buttonNum) {
        super(context, R.style.dialog);
        this.buttonNum=buttonNum;

        Window window = this.getWindow();

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setContentView(R.layout.my_dailog_layout);
        initView(window);

    }

    //构造函数
    public MyDialog(Context context) {
        super(context, R.style.dialog);

        Window window = this.getWindow();

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setContentView(R.layout.my_dailog_layout);
        initView(window);

    }

    private void initView(Window window){
        okBtn = (Button) window.findViewById(R.id.ok_btn);
        cancleBtn = (Button) window.findViewById(R.id.cancle_btn);
        contentTextView = (TextView) window.findViewById(R.id.context_text);
        if(buttonNum==1){
            cancleBtn.setVisibility(View.GONE);
        }
    }

    public void showDiglog() {
        this.show();
    }

    public void setContentText(String contentText) {
        contentTextView.setText(contentText);
    }

    public void setBtnText(String leftText,String rightText){
        okBtn.setText(leftText);
        cancleBtn.setText(rightText);
    }

    public void setBtnText(String leftText){
        okBtn.setText(leftText);
    }
    public void setBtnClick(final OnBtnClickListemer onBtnClickListemer){


        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onBtnClickListemer.OnOKBtnClick();//接口对象的方法，需要在实例化MyDialog是实现该回调方法
            }
        });
        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onBtnClickListemer.OnCancleBtnClick();
            }
        });
    }
}
