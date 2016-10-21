package com.jeff.shareapp.ui.task;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jeff.shareapp.R;
import com.jeff.shareapp.adapter.ResourceListAdapter;
import com.jeff.shareapp.adapter.TaskListAdapter;
import com.jeff.shareapp.model.CourseTypeModel;
import com.jeff.shareapp.model.TaskModel;
import com.jeff.shareapp.model.TaskRespModel;
import com.jeff.shareapp.ui.MainActivity;
import com.jeff.shareapp.util.FormatUtil;
import com.jeff.shareapp.util.MyApplication;
import com.jeff.shareapp.util.MyVolley;
import com.jeff.shareapp.util.MyVolleyListener;
import com.jeff.shareapp.util.StaticFlag;
import com.markmao.pulltorefresh.widget.XListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class TaskListFragment extends Fragment implements View.OnClickListener, XListView.IXListViewListener {


    private List<TaskRespModel> taskList;
    private List<TaskRespModel> mainTaskList;
    private RelativeLayout errorView;
    private TextView errorText;
    private TextView currentBtn;
    private TextView historyBtn;
    private int labFlag = 0;
    private XListView mXistView;
    private TaskListAdapter mAdapter;
    private View view;
    private int page_num = 0;
    private int page_count = 10;

    public Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            ((MainActivity) getActivity()).endWait();
            switch (msg.what) {
                case 1:
                    if (page_num == 0) {
                        //下拉刷新
                        mainTaskList = taskList;
                        if (mXistView == null)
                            initXListView();
                        if (mainTaskList == null || (taskList != null && mainTaskList.size() == 0)) {
                            mXistView.setVisibility(View.GONE);
                            errorView.setVisibility(View.VISIBLE);
                            if (labFlag == 0)
                                errorText.setText("没找到任何当前任务记录，请点击重试！");
                            else
                                errorText.setText("没找到任何历史任务记录，请点击重试！");
                        } else {
                            mAdapter = new TaskListAdapter(labFlag, mainTaskList, (RelativeLayout) view.findViewById(R.id.index_list_item), getContext());
                            mAdapter.setFlag(labFlag);
                            mXistView.setAdapter(mAdapter);

                            mXistView.setVisibility(View.VISIBLE);
                            errorView.setVisibility(View.GONE);

                            if (mainTaskList.size() > 0)
                                mXistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        TaskDetialActivity.startActivity(getActivity(), mainTaskList.get(position - 1) + "");
                                    }
                                });
                            if (mainTaskList.size() < 10){
                                mXistView.setPullLoadEnable(false);
                                mXistView.setFooterText("暂无更多任务记录");
                            }

                        }
                        mXistView.stopRefresh();
                    } else {

                        //上滑加载

                        mXistView.stopLoadMore();
                        if (taskList == null || (taskList != null && taskList.size() == 0)) {
                            mXistView.setFooterText("暂无更多任务记录");
                            page_num--;
                        } else
                            mAdapter.addDataList(taskList);

                        mXistView.stopLoadMore();
                    }
                    break;


                case 0:
                    Toast.makeText(getContext(), msg.getData().getString("failure_message"), Toast.LENGTH_SHORT).show();
                    mXistView.setVisibility(View.GONE);
                    errorView.setVisibility(View.VISIBLE);
                    errorText.setText(msg.getData().getString("failure_message") + "，请点击重试！");
                    break;
                case StaticFlag.ERROR:

                    mXistView.setVisibility(View.GONE);
                    errorView.setVisibility(View.VISIBLE);
                    errorText.setText(msg.getData().getString("message") + "，请点击重试！");
                    Toast.makeText(getContext(), msg.getData().getString("message"), Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public TaskListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        this.view = view;
        initView();
        return view;
    }


    private void initView() {
        currentBtn = (TextView) view.findViewById(R.id.current_notice_button);
        historyBtn = (TextView) view.findViewById(R.id.history_notice_button);
        errorView = (RelativeLayout) view.findViewById(R.id.error_view);
        errorText = (TextView) view.findViewById(R.id.error_text);
        mXistView = (XListView) view.findViewById(R.id.task_list_view);
        currentBtn.setOnClickListener(this);
        historyBtn.setOnClickListener(this);
        errorView.setOnClickListener(this);
        errorView.setVisibility(View.GONE);
        changeLab();
    }

    private void initXListView() {


        mXistView.setPullRefreshEnable(true);
        mXistView.setPullLoadEnable(true);
        mXistView.setAutoLoadEnable(true);
        mXistView.setXListViewListener(this);
        mXistView.setRefreshTime(getTime());
        mXistView.setRefreshTime(new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date()));


    }

    private void changeLab() {
        if (labFlag == 0) {
            currentBtn.setTextColor(getResources().getColor(R.color.white));
            currentBtn.setBackground(getResources().getDrawable(R.drawable.current_task_down));
            historyBtn.setTextColor(getResources().getColor(R.color.main_blue));
            historyBtn.setBackground(getResources().getDrawable(R.drawable.history_task_up));
        } else {
            currentBtn.setTextColor(getResources().getColor(R.color.main_blue));
            currentBtn.setBackground(getResources().getDrawable(R.drawable.current_task_up));
            historyBtn.setTextColor(getResources().getColor(R.color.white));
            historyBtn.setBackground(getResources().getDrawable(R.drawable.history_task_down));
        }
        if (mXistView != null) {
            mXistView.stopRefresh();
            mXistView.stopLoadMore();
        }
        page_num = 0;
        getTaskList();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.current_notice_button:
                labFlag = 0;
                changeLab();
                break;
            case R.id.history_notice_button:
                labFlag = 1;
                changeLab();
                break;
            case R.id.error_view:
                getTaskList();
                break;
        }
    }


    /**
     * 获取当前时间
     *
     * @return
     */
    private String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new java.util.Date());
    }

    @Override
    public void onRefresh() {

        page_num = 0;
        changeLab();

    }

    @Override
    public void onLoadMore() {

        page_num++;
        getTaskList();

    }

    private void getTaskList() {

        String url;
        if (MyApplication.getMyApplication().getUserinfo().getUserType() == StaticFlag.TEACHER_USER_TYPE)
            url = StaticFlag.TEACHER_GET_TASK_LIST;
        else
            url = StaticFlag.STUDENT_GET_TASK_LIST;

        HashMap<String, String> mParams = new HashMap<String, String>();
        mParams.put("token", MyApplication.getMyApplication().getUserinfo().getToken());
        mParams.put("task_statu", labFlag + "");
        mParams.put("page_num", page_num + "");
        mParams.put("page_count", page_count + "");
        ((MainActivity) getActivity()).startWait();
        new MyVolley<CourseTypeModel>(url, mParams,
                new MyVolleyListener() {
                    @Override
                    public void onSuccess(Object data) {

                        Gson gson = FormatUtil.getFormatGson();
                        String jsonResult = gson.toJson(data);
                        taskList = gson.fromJson(jsonResult, new TypeToken<List<TaskRespModel>>() {
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
}
