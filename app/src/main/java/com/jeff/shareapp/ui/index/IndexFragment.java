package com.jeff.shareapp.ui.index;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jeff.shareapp.R;
import com.jeff.shareapp.model.CourseTypeModel;
import com.jeff.shareapp.model.PictureListModel;
import com.jeff.shareapp.model.ResourceRespModel;
import com.jeff.shareapp.model.UserinfoModel;
import com.jeff.shareapp.ui.CustomVIew.BannerCirlePlayer;
import com.jeff.shareapp.ui.CustomVIew.MyUploadMenuDialog;
import com.jeff.shareapp.ui.CustomVIew.OnBtnClickListemer;
import com.jeff.shareapp.ui.MainActivity;
import com.jeff.shareapp.ui.allPage.ResourceDetialActivity;
import com.jeff.shareapp.ui.task.NewTaskActivity;
import com.jeff.shareapp.util.FormatUtil;
import com.jeff.shareapp.util.MyApplication;
import com.jeff.shareapp.util.MyImageLoader;
import com.jeff.shareapp.util.MyVolley;
import com.jeff.shareapp.util.MyVolleyListener;
import com.jeff.shareapp.util.StaticFlag;
import com.jeff.shareapp.util.UIUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;


public class IndexFragment extends Fragment implements View.OnClickListener {

    //轮播图数据
    private List<PictureListModel> newsList;
    private int currentItem = 0; // 当前图片的索引号

    private LinearLayout courseGroup1;
    private LinearLayout courseGroup2;
    private LinearLayout hotResourceForm;
    private BannerCirlePlayer picPlayer;
    private List<ResourceRespModel> hotResourceList;
    private boolean isHotResourceFinish = false;
    private ImageView upload;

    private List<CourseTypeModel> courses;
    private boolean isCourseFinish = false;
    private MyImageLoader mImageLoader;


    public IndexFragment() {
        // Required empty public constructor
    }

    public Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    initHotResurce();
                    break;
                case 2:
                    initNewsView();
                    break;
                case 3:
                    initCourse();
                    break;
                case -1:
                    Toast.makeText(getContext(), msg.getData().getString("failure_message"), Toast.LENGTH_SHORT).show();
                    break;
                case 0:
                    try {
                    } catch (Exception e) {
                    }
                    Toast.makeText(getContext(), msg.getData().getString("message"), Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_index, container, false);
        ButterKnife.inject(getActivity());
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {

        courseGroup1 = (LinearLayout) view.findViewById(R.id.index_coursegroup1);
        courseGroup2 = (LinearLayout) view.findViewById(R.id.index_coursegroup2);
        upload = (ImageView) view.findViewById(R.id.index_upload);
        upload.setOnClickListener(this);
        hotResourceForm = (LinearLayout) view.findViewById(R.id.hot_resource);
        picPlayer = (BannerCirlePlayer) view.findViewById(R.id.banner_circle);
        mImageLoader = new MyImageLoader(getContext());

    }

    /**
     * 初始化轮播图
     */
    private void initNewsView()
    {
        picPlayer.setmImageLoader(mImageLoader);
        List<String> list = new ArrayList<>();
        List<View.OnClickListener> listeners = new ArrayList<>();
        for (final PictureListModel pic : newsList) {
            list.add(pic.getPictureUrl().startsWith("newsPic")?StaticFlag.FILE_URL+pic.getPictureUrl():pic.getPictureUrl());
            listeners.add(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 处理跳转逻辑
                    if (pic.getPictureNewsType() == 0)
                        ResourceDetialActivity.startActivity(getActivity(), "" + pic.getResourceId());
                    else {
                        Uri uri = Uri.parse(pic.getPictureContext());   //指定网址
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);           //指定Action
                        intent.setData(uri);                            //设置Uri
                        getActivity().startActivity(intent);        //启动Activity
                        UIUtils.popToLeft(getActivity());
                    }
                }
            });
        }
        picPlayer.setData(list,listeners,true,3);

    }

    private void initCourse() {

        if (courses.size() >= 4)
            for (int i = 0; i < 4; i++) {
                if (courseGroup1.getChildAt(i) != null) {
                    // 异步加载图片
                    mImageLoader.loadImage(StaticFlag.FILE_URL + courses.get(i).getCourseIconUrl(), (ImageView) courseGroup1.getChildAt(i));
                    courseGroup1.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MainActivity.getMainActivity().onClickChangeView(StaticFlag.ALLPAGE_FRAGMENT);
                        }
                    });
                }
            }
        if (courses.size() >= 7)
            for (int i = 0; i < 3; i++) {
                if (courseGroup2.getChildAt(i) != null) {
                    // 异步加载图片
                    mImageLoader.loadImage(StaticFlag.FILE_URL + courses.get(i + 4).getCourseIconUrl(), (ImageView) courseGroup2.getChildAt(i));
                    courseGroup2.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MainActivity.getMainActivity().onClickChangeView(StaticFlag.ALLPAGE_FRAGMENT);
                        }
                    });
                }
            }
        courseGroup2.getChildAt(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getMainActivity().onClickChangeView(StaticFlag.ALLPAGE_FRAGMENT);
            }
        });
        isCourseFinish = true;
        if (isHotResourceFinish)
            ((MainActivity)

                    getActivity()

            ).

                    endWait();
    }

    private void initHotResurce() {
        final LayoutInflater inflater = LayoutInflater.from(getContext());
        for (int i = 0; i < hotResourceList.size(); i++) {
            ResourceRespModel r = hotResourceList.get(i);
            // 获取需要添加的布局
            RelativeLayout layout = (RelativeLayout) inflater.inflate(
                    R.layout.item_index_list, null).findViewById(R.id.index_list_item);
            // 将布局加入到当前布局中
            ((ImageView) layout.getChildAt(0)).setImageResource(UIUtils.getPicByType(r.getResourceFileType()));
            ((TextView) layout.getChildAt(1)).setText(r.getResourceName());
            ((TextView) layout.getChildAt(2)).setText(r.getAuthorName() + "上传于" + FormatUtil.DateFormat(r.getResourceUploadTime()));
            LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.dip2px(getContext(), 100));
            mParams.setMargins(0, 2, 0, 2);
            layout.setLayoutParams(mParams);
            hotResourceForm.addView(layout);
        }

        for (int i = 0; i < hotResourceList.size(); i++) {
            if (hotResourceForm.getChildAt(i) != null) {
                hotResourceForm.getChildAt(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.index_item_bg));
                hotResourceForm.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ResourceDetialActivity.startActivity(getActivity(), hotResourceList.get(0).getResourceId() + "");
                    }
                });
            }
        }

        isHotResourceFinish = true;

        if (isCourseFinish)
            ((MainActivity) getActivity()).endWait();

    }

    private void initData() {
        getNewsPicList();
        getCourseList();
        getHotResourceList();

    }

    private void getHotResourceList() {
        HashMap<String, String> mParams = new HashMap<String, String>();
        mParams.put("token", MyApplication.getMyApplication().getUserinfo().getToken());

        new MyVolley<UserinfoModel>(StaticFlag.GET_HOT_RESOUSE, mParams,
                new MyVolleyListener() {
                    @Override
                    public void onSuccess(Object data) {

                        Gson gson = new Gson();
                        String jsonResult = gson.toJson(data);
                        hotResourceList = gson.fromJson(jsonResult, new TypeToken<List<ResourceRespModel>>() {
                        }.getType());

                        Message message = Message.obtain();
                        message.what = 1;
                        myHandler.sendMessage(message);
                    }

                    @Override
                    public void onFailure(int failureCode, String failureMessage) {
                        Message message = Message.obtain();
                        message.what = -1;
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

    private void getCourseList() {
        HashMap<String, String> mParams = new HashMap<String, String>();
        mParams.put("token", MyApplication.getMyApplication().getUserinfo().getToken());

        ((MainActivity) getActivity()).startWait();
        new MyVolley<CourseTypeModel>(StaticFlag.GET_INDEX_COURSE, mParams,
                new MyVolleyListener() {
                    @Override
                    public void onSuccess(Object data) {

                        Gson gson = new Gson();
                        String jsonResult = gson.toJson(data);
                        courses = gson.fromJson(jsonResult, new TypeToken<List<CourseTypeModel>>() {
                        }.getType());

                        Message message = Message.obtain();
                        message.what = 3;
                        myHandler.sendMessage(message);
                    }

                    @Override
                    public void onFailure(int failureCode, String failureMessage) {
                        Message message = Message.obtain();
                        message.what = -1;
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

    private void getNewsPicList() {

        HashMap<String, String> mParams = new HashMap<String, String>();
        mParams.put("token", MyApplication.getMyApplication().getUserinfo().getToken());

        new MyVolley<UserinfoModel>(StaticFlag.GET_NEWS_PICTURES_LIST, mParams,
                new MyVolleyListener() {
                    @Override
                    public void onSuccess(Object data) {

                        Gson gson = new Gson();
                        String jsonResult = gson.toJson(data);
                        newsList = gson.fromJson(jsonResult, new TypeToken<List<PictureListModel>>() {
                        }.getType());

                        Message message = Message.obtain();
                        message.what = 2;
                        myHandler.sendMessage(message);
                    }

                    @Override
                    public void onFailure(int failureCode, String failureMessage) {
                        Message message = Message.obtain();
                        message.what = -1;
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
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.index_upload:
                final MyUploadMenuDialog myDialog = new MyUploadMenuDialog(getContext());
                myDialog.setBtnClick(new OnBtnClickListemer() {
                    @Override
                    public void OnOKBtnClick() {
                        if (myDialog.getUploadType() == 0) {
                            //
                            ResourceUploadActivity.startActivity(getActivity(), 0);
                            getActivity().finish();
                        } else if (myDialog.getUploadType() == 1) {
                            //
                            NewTaskActivity.startActivity(getActivity());
                            getActivity().finish();
                        }
                    }

                    @Override
                    public void OnCancleBtnClick() {
                    }
                });
                myDialog.show();
                break;
        }
    }



}
