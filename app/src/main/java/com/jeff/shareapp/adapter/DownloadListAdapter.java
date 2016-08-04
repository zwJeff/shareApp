package com.jeff.shareapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jeff.shareapp.R;
import com.jeff.shareapp.model.ResourceModel;
import com.jeff.shareapp.util.FormatUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 张武 on 2016/5/16.
 */
public class DownloadListAdapter extends BaseAdapter {

    private Context context;

    private List<ResourceModel> resourceList;


    public DownloadListAdapter(List<ResourceModel> resourceList, Context context) {
        this.resourceList = resourceList;
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
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_share_record_list, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        fillValue(position, viewHolder);
        return convertView;

    }

    private void fillValue(int position, ViewHolder viewHolder) {


            viewHolder.resource_name.setText(resourceList.get(position).getResourceName());
            viewHolder.resource_uploadtime.setText("下载于" + FormatUtil.DateFormat(resourceList.get(position).getResourceUploadTime()));

    }


    static class ViewHolder {

        @InjectView(R.id.resource_name)
        TextView resource_name;


        @InjectView(R.id.resource_uploadtime)
        TextView resource_uploadtime;



        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }


}
