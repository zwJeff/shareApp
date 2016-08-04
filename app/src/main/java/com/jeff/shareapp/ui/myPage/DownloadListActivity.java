package com.jeff.shareapp.ui.myPage;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeff.shareapp.R;
import com.jeff.shareapp.adapter.DownloadListAdapter;
import com.jeff.shareapp.adapter.ShareRecordListAdapter;
import com.jeff.shareapp.model.ResourceModel;
import com.jeff.shareapp.ui.BasicActivity;
import com.jeff.shareapp.ui.allPage.ResourceDetialActivity;
import com.markmao.pulltorefresh.widget.XListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class DownloadListActivity extends BasicActivity implements XListView.IXListViewListener {

    @InjectView(R.id.download_list)
    XListView mXListView;
    @InjectView(R.id.lable_title)
    TextView title;
    @InjectView(R.id.img_left)
    ImageView backPic;

    private ArrayList<ResourceModel> resourceList;
    private DownloadListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_list);
        ButterKnife.inject(this);
        initData();
        initView();
    }

    private void initData() {
        resourceList=new ArrayList<ResourceModel>();
        for(int i=1;i<12;i++)
        {
            ResourceModel r=new ResourceModel();
            r.setResourceName("大学物理第"+i+"章学习讲义");
            r.setResourceUploadTime(new Date(115, 5, i));
            resourceList.add(r);
        }

    }

    private void initView() {
        title.setText("下载记录");
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

        mAdapter = new DownloadListAdapter(resourceList, this);
        mXListView.setAdapter(mAdapter);
        mXListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ResourceDetialActivity.startActivity(DownloadListActivity.this,resourceList.get(position).getResourceId()+"");
            }
        });

    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
       mXListView.stopRefresh();
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 上滑加载
     */
    @Override
    public void onLoadMore() {

        mAdapter.notifyDataSetChanged();
    }

}
