package com.jeff.shareapp.ui.myPage;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jeff.shareapp.R;
import com.jeff.shareapp.adapter.ResourceListAdapter;
import com.jeff.shareapp.adapter.ShareRecordListAdapter;
import com.jeff.shareapp.model.CourseTypeModel;
import com.jeff.shareapp.model.ResourceModel;
import com.jeff.shareapp.model.ResourceRespModel;
import com.jeff.shareapp.model.TaskRespModel;
import com.jeff.shareapp.ui.BasicActivity;
import com.jeff.shareapp.ui.MainActivity;
import com.jeff.shareapp.ui.allPage.ResourceDetialActivity;
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

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ShareRecordActivity extends BasicActivity implements XListView.IXListViewListener {

    @InjectView(R.id.share_record_list)
    XListView mXListView;
    @InjectView(R.id.lable_title)
    TextView title;
    @InjectView(R.id.img_left)
    ImageView backPic;

    private int page_num = 0;
    private int page_count = 10;
    private ArrayList<ResourceRespModel> resourceList;
    private ShareRecordListAdapter mAdapter;

    public Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            endWait();
            switch (msg.what) {
                case 1:
                    if (page_num == 0) {
                        mAdapter = new ShareRecordListAdapter(resourceList, ShareRecordActivity.this);
                        mXListView.setAdapter(mAdapter);
                        mXListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                ResourceDetialActivity.startActivity(ShareRecordActivity.this, resourceList.get(position-1).getResourceId() + "");
                            }
                        });
                        if (resourceList.size() == 0)
                            mXListView.setFooterText("暂无分享记录");
                        mXListView.stopRefresh();
                        mXListView.stopLoadMore();
                    } else {
                        mXListView.stopLoadMore();
                        if (resourceList.size() == 0) {
                            mXListView.setFooterText("暂无更多任务记录");
                            page_num--;
                        } else
                            mAdapter.addDataList(resourceList);
                    }
                    break;


                case 0:
                    Toast.makeText(ShareRecordActivity.this, msg.getData().getString("failure_message"), Toast.LENGTH_SHORT).show();
                    break;
                case StaticFlag.ERROR:
                    try {
                    } catch (Exception e) {
                    }
                    Toast.makeText(ShareRecordActivity.this, msg.getData().getString("message"), Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_record);
        ButterKnife.inject(this);
        geRecordList();
        initView();
    }


    private void geRecordList() {

        HashMap<String, String> mParams = new HashMap<String, String>();
        mParams.put("token", MyApplication.getMyApplication().getUserinfo().getToken());
        mParams.put("page_num", page_num + "");
        mParams.put("page_count", page_count + "");
        startWait();
        new MyVolley<CourseTypeModel>(StaticFlag.GET_UPLOAD_LIST, mParams,
                new MyVolleyListener() {
                    @Override
                    public void onSuccess(Object data) {

                        Gson gson = new Gson();
                        String jsonResult = gson.toJson(data);
                        resourceList = gson.fromJson(jsonResult, new TypeToken<List<ResourceRespModel>>() {
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

    private void initView() {
        title.setText("分享记录");
        backPic.setVisibility(View.VISIBLE);
        backPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //初始化XListView
        mXListView.setPullRefreshEnable(true);
        mXListView.setPullLoadEnable(true);
        mXListView.setAutoLoadEnable(true);
        mXListView.setXListViewListener(this);
        mXListView.setRefreshTime(new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date()));


    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        page_num = 0;
        geRecordList();
    }

    /**
     * 上滑加载
     */
    @Override
    public void onLoadMore() {
        page_num++;
        geRecordList();
    }

}
