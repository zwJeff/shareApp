package com.jeff.shareapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jeff.shareapp.R;
import com.jeff.shareapp.model.ResourceRespModel;
import com.jeff.shareapp.model.UserinfoModel;
import com.jeff.shareapp.util.FormatUtil;
import com.jeff.shareapp.core.MyApplication;
import com.jeff.shareapp.net.MyVolley;
import com.jeff.shareapp.net.MyVolleyListener;
import com.jeff.shareapp.util.StaticFlag;
import com.jeff.shareapp.util.UIUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 张武 on 2016/5/16.
 */
public class ResourceListAdapter extends BaseAdapter {

    private Context context;

    private List<ResourceRespModel> resourceList;

    private RelativeLayout listItemLayout;


    public ResourceListAdapter(List<ResourceRespModel> resourceList, RelativeLayout listItemLayout, Context context) {
        this.resourceList = resourceList;
        this.listItemLayout = listItemLayout;
        this.context = context;
    }

    @Override
    public int getCount() {
        return resourceList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_allpage_list, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        viewHolder.resourceName.setText(resourceList.get(position).getResourceName());
        viewHolder.resourceAuthorAndTime.setText(resourceList.get(position).getAuthorName() + "上传于" + FormatUtil.DateFormat(resourceList.get(position).getResourceUploadTime()));
        viewHolder.resourcePic.setImageResource(UIUtils.getPicByType(resourceList.get(position).getResourceFileType()));
        if (resourceList.get(position).getIsCollected() == 1) {
            viewHolder.collectPic.setImageResource(R.mipmap.ic_favourite_press_25dp);
            viewHolder.collectPic.setClickable(false);
        } else
            viewHolder.collectPic.setImageResource(R.mipmap.ic_favourite_normal_25dp);
        viewHolder.collectPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //收藏操作
                if(resourceList.get(position).getIsCollected()==1){
                    viewHolder.collectPic.setImageResource(R.mipmap.ic_favourite_normal_25dp);
                    resourceList.get(position).setIsCollected(0);
                    collectResource( resourceList.get(position).getResourceId()+"");
                    Toast.makeText(context,"取消收藏成功！",Toast.LENGTH_SHORT).show();
                }else{
                    resourceList.get(position).setIsCollected(1);
                    viewHolder.collectPic.setImageResource(R.mipmap.ic_favourite_press_25dp);
                    collectResource( resourceList.get(position).getResourceId()+"");
                    Toast.makeText(context,"收藏成功！",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return convertView;

    }


    public void addDataList(List<ResourceRespModel> dataList) {
        resourceList.addAll(dataList);
        notifyDataSetChanged();
    }


    static class ViewHolder {

        @InjectView(R.id.item_allpage_resource_name)
        TextView resourceName;

        @InjectView(R.id.item_allpage_resource_author)
        TextView resourceAuthorAndTime;

        @InjectView(R.id.item_allpage_resource_pic)
        ImageView resourcePic;

        @InjectView(R.id.item_allpage_collect_pic)
        ImageView collectPic;


        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    private void collectResource(String resource_id) {
        final String[] msg = new String[1];
        HashMap<String, String> mParams = new HashMap<String, String>();

        mParams.put("resource_id", resource_id);

        new MyVolley<String>(StaticFlag.COLLECT, mParams,
                new MyVolleyListener() {
                    @Override
                    public void onSuccess(Object data) {

                    }

                    @Override
                    public void onFailure(int failureCode, String failureMessage) {
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                    }
                }
        );

    }


}
