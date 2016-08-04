package com.jeff.shareapp.ui.CustomVIew;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.jeff.shareapp.R;
import com.jeff.shareapp.util.MyApplication;
import com.jeff.shareapp.util.StaticFlag;

/**
 * Created by 张武 on 2016/2/24.
 */
public class MyUploadMenuDialog extends Dialog {

    private Button okBtn;
    private Button cancleBtn;
    private TextView menu1;
    private TextView menu2;

    private int uploadType = 0;

    //接口对象
    private OnBtnClickListemer mOnOKBtnClickListemer;

    //构造函数
    public MyUploadMenuDialog(Context context) {
        super(context, R.style.dialog);

        Window window = this.getWindow();

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setContentView(R.layout.my_dailog_upload_menu_layout);

        okBtn = (Button) window.findViewById(R.id.ok_btn);
        cancleBtn = (Button) window.findViewById(R.id.cancle_btn);
        menu1 = (TextView) window.findViewById(R.id.index_upload_menu1);
        menu2 = (TextView) window.findViewById(R.id.index_upload_menu2);
        selectMenu(0);
        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectMenu(0);
            }
        });

        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectMenu(1);
            }
        });

        if(MyApplication.getMyApplication().getUserinfo().getUserType()== StaticFlag.STUDENT_USER_TYPE)
            menu2.setVisibility(View.GONE);

    }


    public void setBtnClick(final OnBtnClickListemer onBtnClickListemer) {


        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    private void selectMenu(int flag) {
        uploadType = flag;
        menu1.setSelected(false);
        menu2.setSelected(false);
        if (flag == 0)
            menu1.setSelected(true);
        else
            menu2.setSelected(true);
    }

    public void showDiglog() {
        this.show();
    }

    public int getUploadType() {
        return uploadType;
    }

}
