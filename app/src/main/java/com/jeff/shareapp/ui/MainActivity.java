package com.jeff.shareapp.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeff.shareapp.R;
import com.jeff.shareapp.ui.allPage.AllPageFragment;
import com.jeff.shareapp.ui.index.IndexFragment;
import com.jeff.shareapp.ui.myPage.MyPageFragment;
import com.jeff.shareapp.ui.service.MyGetNotificationService;
import com.jeff.shareapp.ui.task.TaskListFragment;
import com.jeff.shareapp.util.StaticFlag;
import com.jeff.shareapp.util.UIUtils;

/**
 * Created by Administrator on 2015/5/6.
 * <p/>
 * Main
 */
public class MainActivity extends BasicActivity implements View.OnClickListener {

    // Fragment管理工具
    public FragmentManager fm;

    //导航栏文字
    private TextView titleText;

    // 底栏菜单组-首页
    private ImageView index_bt;


    // 底栏菜单组-全部资源页面
    private ImageView allpage_bt;

    // 底栏菜单组-学习任务
    private ImageView task_bt;

    // 底栏菜单组-我的页面
    private ImageView mypage_bt;

    private static MainActivity instant;

    public static MainActivity getMainActivity() {
        if (instant == null) {
            instant = new MainActivity();
        }
        return instant;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        UIUtils.openImmerseStatasBarMode(this);
        Intent i = getIntent();
        int fragmentId = i.getIntExtra("fragment_id", StaticFlag.INDEXPAGE_FRAGMENT);
        setContentView(R.layout.activity_main);
        initView(fragmentId);
        instant = this;

        //开启后台服务检测是否有新通知，每10s轮询一次
        startService(new Intent(MainActivity.this, MyGetNotificationService.class));
    }
    protected void onDestroy() {
        super.onDestroy();


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int fragmentId = intent.getIntExtra("fragment_id", StaticFlag.INDEXPAGE_FRAGMENT);
        setContentView(R.layout.activity_main);
        initView(fragmentId);
        setIntent(intent);
    }

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);

        activity.startActivity(intent);
        UIUtils.popToLeft(activity);
    }

    public static void startActivity(Activity activity, int fragmentId) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra("fragment_id", fragmentId);
        activity.startActivity(intent);
        UIUtils.popToLeft(activity);
    }

    // 初始化界面
    public void initView(int fragmentId) {

        // 获取fragmentManager
        fm = getSupportFragmentManager();

        titleText = (TextView) findViewById(R.id.lable_title);

        // 绑定按钮
        index_bt = (ImageView) findViewById(R.id.index_bt);
        allpage_bt = (ImageView) findViewById(R.id.allpage_bt);
        task_bt = (ImageView) findViewById(R.id.task_bt);
        ;
        mypage_bt = (ImageView) findViewById(R.id.mypage_bt);

        // 设置监听
        index_bt.setOnClickListener(this);
        allpage_bt.setOnClickListener(this);
        task_bt.setOnClickListener(this);
        mypage_bt.setOnClickListener(this);


        onClickChangeView(fragmentId);
    }


    // 动态更换fragment
    public void onClickChangeView(int fragmentId) {

        Fragment currentFragment;

        // 设置默认fragment
        switch (fragmentId) {
            case 101:
                titleText.setText("慕课在线");
                currentFragment = new IndexFragment();
                index_bt.setImageResource(R.drawable.ic_index1);
                allpage_bt.setImageResource(R.drawable.ic_allpage0);
                task_bt.setImageResource(R.drawable.ic_task0);
                mypage_bt.setImageResource(R.drawable.ic_mypage0);
                break;
            case 102:
                titleText.setText("全部资源");
                currentFragment = new AllPageFragment();
                index_bt.setImageResource(R.drawable.ic_index0);
                allpage_bt.setImageResource(R.drawable.ic_allpage1);
                task_bt.setImageResource(R.drawable.ic_task0);
                mypage_bt.setImageResource(R.drawable.ic_mypage0);
                break;
            case 103:
                titleText.setText("学习任务");
                currentFragment = new TaskListFragment();
                index_bt.setImageResource(R.drawable.ic_index0);
                allpage_bt.setImageResource(R.drawable.ic_allpage0);
                task_bt.setImageResource(R.drawable.ic_task1);
                mypage_bt.setImageResource(R.drawable.ic_mypage0);
                break;
            case 104:
                titleText.setText("我的空间");
                currentFragment = new MyPageFragment();
                index_bt.setImageResource(R.drawable.ic_index0);
                allpage_bt.setImageResource(R.drawable.ic_allpage0);
                task_bt.setImageResource(R.drawable.ic_task0);
                mypage_bt.setImageResource(R.drawable.ic_mypage1);
                break;
            default:
                currentFragment = new IndexFragment();

        }

        // 开启Fragment事务
        FragmentTransaction transaction = fm.beginTransaction();

        transaction.replace(R.id.id_content, currentFragment);

        transaction.commit();
    }


    // 设置click监听
    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.index_bt:
                onClickChangeView(StaticFlag.INDEXPAGE_FRAGMENT);
                break;

            case R.id.allpage_bt:
                onClickChangeView(StaticFlag.ALLPAGE_FRAGMENT);
                break;

            case R.id.task_bt:
                onClickChangeView(StaticFlag.TASKPAGE_FRAGMENT);
                break;

            case R.id.mypage_bt:
                onClickChangeView(StaticFlag.MYPAGE_FRAGMENT);
                break;

        }
    }


}

