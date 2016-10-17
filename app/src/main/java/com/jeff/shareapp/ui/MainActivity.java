package com.jeff.shareapp.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/5/6.
 * <p/>
 * Main
 */
public class MainActivity extends BasicActivity implements View.OnClickListener {

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

    private ViewPager mViewPager;

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

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                onClickChangeView(101 + position,false,false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


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

        mViewPager = (ViewPager) findViewById(R.id.id_content);

        titleText = (TextView) findViewById(R.id.lable_title);

        // 绑定按钮
        index_bt = (ImageView) findViewById(R.id.index_bt);
        allpage_bt = (ImageView) findViewById(R.id.allpage_bt);
        task_bt = (ImageView) findViewById(R.id.task_bt);
        mypage_bt = (ImageView) findViewById(R.id.mypage_bt);

        // 设置监听
        index_bt.setOnClickListener(this);
        allpage_bt.setOnClickListener(this);
        task_bt.setOnClickListener(this);
        mypage_bt.setOnClickListener(this);


        List<Fragment> fragList = new ArrayList<>();
        Fragment f1, f2, f3, f4;
        f1 = new IndexFragment();
        f2 = new AllPageFragment();
        f3 = new TaskListFragment();
        f4 = new MyPageFragment();
        fragList.add(f1);
        fragList.add(f2);
        fragList.add(f3);
        fragList.add(f4);

        mViewPager.setAdapter(new MyPageViewAdapter(fragList, getSupportFragmentManager()));

        onClickChangeView(fragmentId,false, false);
    }


    // 动态更换fragment
    public void onClickChangeView(int fragmentId, boolean isChangeFragment, boolean isAnimotion) {

        // 设置默认fragment
        switch (fragmentId) {
            case 101:
                titleText.setText("慕课在线");
                if (isChangeFragment)
                    mViewPager.setCurrentItem(0, isAnimotion);
                index_bt.setImageResource(R.drawable.ic_index1);
                allpage_bt.setImageResource(R.drawable.ic_allpage0);
                task_bt.setImageResource(R.drawable.ic_task0);
                mypage_bt.setImageResource(R.drawable.ic_mypage0);
                break;
            case 102:
                titleText.setText("全部资源");
                if (isChangeFragment)
                    mViewPager.setCurrentItem(1, isAnimotion);
                index_bt.setImageResource(R.drawable.ic_index0);
                allpage_bt.setImageResource(R.drawable.ic_allpage1);
                task_bt.setImageResource(R.drawable.ic_task0);
                mypage_bt.setImageResource(R.drawable.ic_mypage0);
                break;
            case 103:
                titleText.setText("学习任务");
                if (isChangeFragment)
                    mViewPager.setCurrentItem(2, isAnimotion);
                index_bt.setImageResource(R.drawable.ic_index0);
                allpage_bt.setImageResource(R.drawable.ic_allpage0);
                task_bt.setImageResource(R.drawable.ic_task1);
                mypage_bt.setImageResource(R.drawable.ic_mypage0);
                break;
            case 104:
                titleText.setText("我的空间");
                if (isChangeFragment)
                    mViewPager.setCurrentItem(3, isAnimotion);
                index_bt.setImageResource(R.drawable.ic_index0);
                allpage_bt.setImageResource(R.drawable.ic_allpage0);
                task_bt.setImageResource(R.drawable.ic_task0);
                mypage_bt.setImageResource(R.drawable.ic_mypage1);
                break;
            default:
                mViewPager.setCurrentItem(0, false);

        }


    }


    // 设置click监听
    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.index_bt:
                onClickChangeView(StaticFlag.INDEXPAGE_FRAGMENT,true, false);
                break;

            case R.id.allpage_bt:
                onClickChangeView(StaticFlag.ALLPAGE_FRAGMENT,true, false);
                break;

            case R.id.task_bt:
                onClickChangeView(StaticFlag.TASKPAGE_FRAGMENT,true, false);
                break;

            case R.id.mypage_bt:
                onClickChangeView(StaticFlag.MYPAGE_FRAGMENT,true, false);
                break;

        }
    }


}

