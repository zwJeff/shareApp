package com.jeff.shareapp.ui.allPage;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jeff.shareapp.R;
import com.jeff.shareapp.adapter.CourseTypeAdapter;
import com.jeff.shareapp.adapter.ListDropDownAdapter;
import com.jeff.shareapp.adapter.ResourceListAdapter;
import com.jeff.shareapp.model.CourseTypeModel;
import com.jeff.shareapp.model.ResourceRespModel;
import com.jeff.shareapp.ui.MainActivity;
import com.jeff.shareapp.util.MyApplication;
import com.jeff.shareapp.util.MyVolley;
import com.jeff.shareapp.util.MyVolleyListener;
import com.jeff.shareapp.util.StaticFlag;
import com.markmao.pulltorefresh.widget.XListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;

public class AllPageFragment extends Fragment implements XListView.IXListViewListener {


    private DropDownMenu mDropDownMenu;
    private String headers[] = {"课程类型", "文件类型", "排序方式"};
    private List<View> popupViews = new ArrayList<>();

    //选择栏填充的adpter
    private CourseTypeAdapter courseTypeAdapter;
    private ListDropDownAdapter fileTypeAdapter;
    private ListDropDownAdapter sexAdapter;


    //选择栏填充内容
    private List<CourseTypeModel> courseList = new ArrayList<>();
    private String fileTypeName[] = {"不限", "图片", "文档", "演示文稿", "音频", "视频", "其它"};
    private String sortTypeName[] = {"默认排序", "按下载量", "按热度"};
    private XListView xListView;
    private ResourceListAdapter mAdapter;
    private List<ResourceRespModel> resourceList;
    private List<ResourceRespModel> mainResourceList;

    //列表条件
    private int courseType = -1;
    private int fileType = -1;
    private int sortType = -1;
    private int page_num = 0;
    private int page_count = 10;

    private boolean isCourseFinish = false;
    private boolean isResourceFinish = false;

    private LayoutInflater inflater;
    private View view;


    public AllPageFragment() {
        // Required empty public constructor
    }

    public Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    initCourseView();
                    if (isResourceFinish) {
                        ((MainActivity) getActivity()).endWait();
                    }
                    break;
                case 2:
                    if (isCourseFinish) {
                        ((MainActivity) getActivity()).endWait();
                    }
                    if (page_num == 0) {
                        mainResourceList=resourceList;
                        if(xListView!=null){
                            xListView.stopRefresh();
                        }
                        initXListView();
                        isResourceFinish = true;
                    } else {
                        mainResourceList.addAll(resourceList);
                        xListView.stopLoadMore();
                        if(resourceList.size()==0){
                            Toast.makeText(getContext(),"全部加载完毕！",Toast.LENGTH_LONG).show();
                        }
                        mAdapter.addDataLiat(resourceList);
                    }
                    break;

                case 0:
                    Toast.makeText(getContext(), msg.getData().getString("failure_message"), Toast.LENGTH_SHORT).show();
                    break;
                case StaticFlag.ERROR:
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_all_page, container, false);
        this.inflater = inflater;
        initView();
        initData();
        return view;
    }

    private void initView() {
        mDropDownMenu = (DropDownMenu) view.findViewById(R.id.dropDownMenu);
    }

    private void initData() {
        page_num = 0;
        getAllCourse();
        getResourceList();
    }

    private void getAllCourse() {
        HashMap<String, String> mParams = new HashMap<String, String>();
        mParams.put("token", MyApplication.getMyApplication().getUserinfo().getToken());

        ((MainActivity) getActivity()).startWait();
        new MyVolley<CourseTypeModel>(StaticFlag.GET_ALL_COURSE, mParams,
                new MyVolleyListener() {
                    @Override
                    public void onSuccess(Object data) {

                        Gson gson = new Gson();
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

    private void getResourceList() {
        HashMap<String, String> mParams = new HashMap<String, String>();
        mParams.put("token", MyApplication.getMyApplication().getUserinfo().getToken());
        mParams.put("resource_course_type", courseType + "");
        mParams.put("sort_type", sortType + "");
        mParams.put("resource_file_type", fileType + "");
        mParams.put("page_count", page_count + "");
        mParams.put("page_number", page_num + "");
        new MyVolley<CourseTypeModel>(StaticFlag.GET_RESOURCE_LIST, mParams,
                new MyVolleyListener() {
                    @Override
                    public void onSuccess(Object data) {

                        Gson gson = new Gson();
                        String jsonResult = gson.toJson(data);
                        resourceList = gson.fromJson(jsonResult, new TypeToken<List<ResourceRespModel>>() {
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

    private void initXListView() {

        xListView = (XListView) view.findViewById(R.id.list_view);
        xListView.setPullRefreshEnable(true);
        xListView.setPullLoadEnable(true);
        xListView.setAutoLoadEnable(true);
        xListView.setXListViewListener(this);
        xListView.setRefreshTime(getTime());

        mAdapter = new ResourceListAdapter(mainResourceList, (RelativeLayout) view.findViewById(R.id.allpage_list_item), getContext());
        xListView.setAdapter(mAdapter);

        if (resourceList.size() == 0) {
            xListView.setFooterText("无符合条件的资源");
            Toast.makeText(getContext(), "无符合条件的资源！", Toast.LENGTH_LONG).show();
        }else {

            xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ResourceDetialActivity.startActivity(getActivity(), mainResourceList.get(position-1).getResourceId() + "");
                }
            });
        }
    }

    private void initCourseView() {

        //资源课程类别选择栏
        final View courseTypeView = inflater.inflate(R.layout.custom_layout, null);
        GridView courseTypeGridView = ButterKnife.findById(courseTypeView, R.id.constellation);
        courseTypeAdapter = new CourseTypeAdapter(getActivity(), courseList);
        courseTypeGridView.setAdapter(courseTypeAdapter);
        TextView ok = ButterKnife.findById(courseTypeView, R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDropDownMenu.setTabText(courseType == -1 ? headers[0] : courseList.get(courseType).getCourseName());
                mDropDownMenu.closeMenu();
                ((MainActivity) getActivity()).startWait();
                getResourceList();
            }
        });

        courseTypeGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                courseTypeAdapter.setCheckItem(position);
                courseType = position - 1;
            }
        });

        popupViews.add(courseTypeView);
        isCourseFinish = true;

        initFileAndSortView();
    }

    private void initFileAndSortView() {
        //资源文件类型选择栏
        final ListView fileTypeView = new ListView(getActivity());
        fileTypeView.setDividerHeight(0);
        fileTypeAdapter = new ListDropDownAdapter(getActivity(), Arrays.asList(fileTypeName));
        fileTypeView.setAdapter(fileTypeAdapter);

        fileTypeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fileTypeAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : fileTypeName[position]);
                fileType = position - 1;
                mDropDownMenu.closeMenu();
                ((MainActivity) getActivity()).startWait();
                getResourceList();
            }
        });
        //资源结果排序方式选择栏
        final ListView sortTypeView = new ListView(getActivity());
        sortTypeView.setDividerHeight(0);
        sexAdapter = new ListDropDownAdapter(getActivity(), Arrays.asList(sortTypeName));
        sortTypeView.setAdapter(sexAdapter);

        sortTypeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sexAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[2] : sortTypeName[position]);
                sortType = position - 1;
                mDropDownMenu.closeMenu();
                ((MainActivity) getActivity()).startWait();
                getResourceList();
            }
        });

        //init popupViews

        popupViews.add(fileTypeView);
        popupViews.add(sortTypeView);


        View nullView = new View(getContext());

        //init dropdownview
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, nullView);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }


    @Override
    public void onDetach() {

        //退出activity前关闭菜单
        if (mDropDownMenu.isShowing()) {
            mDropDownMenu.closeMenu();
        }
        clearData();
        super.onDetach();

    }


    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        page_num=0;
        getResourceList();
    }

    /**
     * 上滑加载
     */
    @Override
    public void onLoadMore() {
        page_num++;
        getResourceList();
    }


    /**
     * 获取当前时间
     *
     * @return
     */
    private String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }

    /**
     * 离开当前fragment时清除数据
     */
    private void clearData() {
        popupViews.removeAll(popupViews);
        courseList.removeAll(courseList);
    }


}