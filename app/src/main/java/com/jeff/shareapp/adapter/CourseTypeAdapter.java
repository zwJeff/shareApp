package com.jeff.shareapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jeff.shareapp.R;
import com.jeff.shareapp.model.CourseTypeModel;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class CourseTypeAdapter extends BaseAdapter {

    private Context context;
    private List<CourseTypeModel> list;
    private int checkItemPosition = 0;

    public void setCheckItem(int position) {
        checkItemPosition = position;
        notifyDataSetChanged();
    }

    public CourseTypeAdapter(Context context, List<CourseTypeModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size()+1;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_constellation_layout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        fillValue(position, viewHolder);
        return convertView;
    }

    private void fillValue(int position, ViewHolder viewHolder) {
        if (position == 0)
            viewHolder.mText.setText("不限");
        else
            viewHolder.mText.setText(list.get(position-1).getCourseName());
        if (checkItemPosition != -1) {
            if (checkItemPosition == position) {
                viewHolder.mText.setTextColor(context.getResources().getColor(R.color.main_blue));
                viewHolder.mText.setBackgroundResource(R.drawable.check_bg);
            } else {
                viewHolder.mText.setTextColor(context.getResources().getColor(R.color.drop_down_unselected));
                viewHolder.mText.setBackgroundResource(R.drawable.uncheck_bg);
            }
        }
    }


    static class ViewHolder {
        @InjectView(R.id.text)
        TextView mText;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
