package com.jeff.shareapp.ui.index;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
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
import com.jeff.shareapp.ui.WelcomeActivity;
import com.jeff.shareapp.ui.allPage.ResourceDetialActivity;
import com.jeff.shareapp.ui.login.LoginActivity;
import com.jeff.shareapp.ui.task.NewTaskActivity;
import com.jeff.shareapp.util.FormatUtil;
import com.jeff.shareapp.core.MyApplication;
import com.jeff.shareapp.net.MyImageLoader;
import com.jeff.shareapp.net.MyVolley;
import com.jeff.shareapp.net.MyVolleyListener;
import com.jeff.shareapp.util.StaticFlag;
import com.jeff.shareapp.util.UIUtils;
import com.markmao.pulltorefresh.widget.XScrollView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class IndexFragment extends Fragment implements View.OnClickListener, XScrollView.IXScrollViewListener {

    private View mContentView;

    @InjectView(R.id.index_mainview)
    protected XScrollView mMainView;
    @InjectView(R.id.index_upload)
    protected ImageView upload;
    //课程类型
    private List<CourseTypeModel> courseList;
    private LinearLayout courseGroup1;
    private LinearLayout courseGroup2;
    //新闻图片
    private List<PictureListModel> newsList;
    //热门资源
    private LinearLayout hotResourceLayout;
    private List<ResourceRespModel> hotResourceList;
    //轮播图组件
    private BannerCirlePlayer bannerPlayer;

    private MyImageLoader mImageLoader;

    private int flag = 0;


    public IndexFragment() {
        // Required empty public constructor
    }

    public Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    synchronized (this) {
                        flag++;
                        if (flag == 3) {
                            ((MainActivity) getActivity()).endWait();
                            mMainView.stopRefresh();
                            initView(mContentView);
                        }
                    }
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


    protected void initView(View view) {

        mMainView = (XScrollView) view.findViewById(R.id.index_mainview);
        mMainView.setPullRefreshEnable(true);
        mMainView.setPullLoadEnable(true);
        mMainView.setAutoLoadEnable(false);
        mMainView.setIXScrollViewListener(this);

        mMainView.setRefreshTime(getTime());

        View content = LayoutInflater.from(getActivity()).inflate(R.layout.index_xscrollview_content, null);

        if (null != content) {
            bannerPlayer = (BannerCirlePlayer) content.findViewById(R.id.banner_circle);
            courseGroup1 = (LinearLayout) content.findViewById(R.id.index_coursegroup1);
            courseGroup2 = (LinearLayout) content.findViewById(R.id.index_coursegroup2);
            hotResourceLayout = (LinearLayout) content.findViewById(R.id.hot_resource);
        }
        initNewsView();
        initCourse();
        initHotResurce();
        mMainView.setView(content);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_index, container, false);
        ButterKnife.inject(this, view);
        mContentView = view;
        initData();
        mMainView.setPullLoadEnable(false);
        mImageLoader = new MyImageLoader(getContext());
        return view;
    }


    private void initData() {
        flag = 0;
        upload.setOnClickListener(this);
        ((MainActivity) getActivity()).startWait();
        getNewsPicList();
        getCourseList();
        getHotResourceList();
    }

    private String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }

    private void getHotResourceList() {
        HashMap<String, String> mParams = new HashMap<String, String>();


        new MyVolley<UserinfoModel>(StaticFlag.GET_HOT_RESOUSE, mParams,
                new MyVolleyListener() {
                    @Override
                    public void onSuccess(Object data) {

                        Gson gson = FormatUtil.getFormatGson();
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


        new MyVolley<CourseTypeModel>(StaticFlag.GET_INDEX_COURSE, mParams,
                new MyVolleyListener() {
                    @Override
                    public void onSuccess(Object data) {

                        Gson gson = FormatUtil.getFormatGson();
                        String jsonResult = gson.toJson(data);
                        courseList = gson.fromJson(jsonResult, new TypeToken<List<CourseTypeModel>>() {
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

    private void getNewsPicList() {

        HashMap<String, String> mParams = new HashMap<String, String>();


        new MyVolley<UserinfoModel>(StaticFlag.GET_NEWS_PICTURES_LIST, mParams,
                new MyVolleyListener() {
                    @Override
                    public void onSuccess(Object data) {

                        Gson gson = FormatUtil.getFormatGson();
                        String jsonResult = gson.toJson(data);
                        newsList = gson.fromJson(jsonResult, new TypeToken<List<PictureListModel>>() {
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


    /**
     * 初始化轮播图
     */
    private void initNewsView() {

        List<String> list = new ArrayList<>();
        List<View.OnClickListener> listeners = new ArrayList<>();
        for (final PictureListModel pic : newsList) {
            list.add(pic.getPictureUrl().startsWith("newsPic") ? StaticFlag.FILE_URL + pic.getPictureUrl() : pic.getPictureUrl());
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
                        startActivity(intent);        //启动Activity
                        UIUtils.popToLeft(getActivity());
                    }
                }
            });
        }
        bannerPlayer.setmImageLoader(mImageLoader);
        bannerPlayer.setData(list, listeners, true, 3);

    }

    private void initCourse() {

        if (courseList.size() >= 4)
            for (int i = 0; i < 4; i++) {
                if (courseGroup1.getChildAt(i) != null) {
                    // 异步加载图片
                    mImageLoader.loadImage(StaticFlag.FILE_URL + courseList.get(i).getCourseIconUrl(), (ImageView) courseGroup1.getChildAt(i));
                    courseGroup1.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MainActivity.getMainActivity().onClickChangeView(StaticFlag.ALLPAGE_FRAGMENT, true, true);
                        }
                    });
                }
            }
        if (courseList.size() >= 7)
            for (int i = 0; i < 3; i++) {
                if (courseGroup2.getChildAt(i) != null) {
                    // 异步加载图片
                    mImageLoader.loadImage(StaticFlag.FILE_URL + courseList.get(i + 4).getCourseIconUrl(), (ImageView) courseGroup2.getChildAt(i));
                    courseGroup2.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MainActivity.getMainActivity().onClickChangeView(StaticFlag.ALLPAGE_FRAGMENT, true, true);
                        }
                    });
                }
            }
        courseGroup2.getChildAt(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getMainActivity().onClickChangeView(StaticFlag.ALLPAGE_FRAGMENT, true, true);
            }
        });

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
            hotResourceLayout.addView(layout);
        }

        for (int i = 0; i < hotResourceList.size(); i++) {
            if (hotResourceLayout.getChildAt(i) != null) {
                hotResourceLayout.getChildAt(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.index_item_bg));
                hotResourceLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int j = hotResourceLayout.indexOfChild(v);
                        ResourceDetialActivity.startActivity(getActivity(), hotResourceList.get(j).getResourceId() + "");
                    }
                });
            }
        }


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.index_upload:
                if (MyApplication.getMyApplication().isLogin == false) {
                    Intent i2 = new Intent(getActivity(), LoginActivity.class);
                    startActivity(i2);
                    UIUtils.popToTop(getActivity());
                } else {
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
                }
                break;
        }
    }


    @Override
    public void onRefresh() {
        initData();
    }

    @Override
    public void onLoadMore() {
        MainActivity.getMainActivity().onClickChangeView(StaticFlag.ALLPAGE_FRAGMENT, true, true);
        mMainView.stopLoadMore();
    }


}
