package com.jeff.shareapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jeff.shareapp.R;
import com.jeff.shareapp.model.TaskRespModel;
import com.jeff.shareapp.model.UserinfoModel;
import com.jeff.shareapp.util.FormatUtil;
import com.jeff.shareapp.core.MyApplication;
import com.jeff.shareapp.util.StaticFlag;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 张武 on 2016/5/16.
 */
public class TaskListAdapter extends BaseAdapter {

    private Context context;

    private List<TaskRespModel> taskList;

    private RelativeLayout listItemLayout;

    private int flag = 0;


    public TaskListAdapter(int flag, List<TaskRespModel> taskList, RelativeLayout listItemLayout, Context context) {
        this.taskList = taskList;
        this.listItemLayout = listItemLayout;
        this.context = context;
        this.flag = flag;
    }

    @Override
    public int getCount() {
        return taskList.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_task_list, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        TaskRespModel t = taskList.get(position);

        if (MyApplication.getMyApplication().getDataPref().getLocalData(UserinfoModel.class).getUserType() == StaticFlag.TEACHER_USER_TYPE)
            teacherFillData(t, viewHolder);
        else
            studentFillData(t, viewHolder);

        return convertView;

    }

    private void teacherFillData(TaskRespModel t, ViewHolder viewHolder) {

        viewHolder.newPic.setVisibility(View.INVISIBLE);
        if (flag == 0) {
            viewHolder.taskName.setText(t.getTaskName());
            viewHolder.taskAuthor.setText("共"+t.getTotalNumber()+"人,"+t.getFinishNumber()+"人已完成");
            viewHolder.taskTime.setText("布置于" + FormatUtil.DateFormat(t.getNewTime()));
        } else {
            viewHolder.newPic.setVisibility(View.INVISIBLE);
            viewHolder.taskName.setText(t.getTaskName());
            viewHolder.taskAuthor.setText(t.getTeacherName() + "老师");
            viewHolder.taskAuthor.setText("共"+t.getTotalNumber()+"人,全部已完成");
            viewHolder.taskTime.setText("布置于" + FormatUtil.DateFormat(t.getNewTime()));
        }
    }


    private void studentFillData(TaskRespModel t, ViewHolder viewHolder) {
        if (flag == 0) {
            if (t.getTaskStatu() == 0)
                viewHolder.newPic.setVisibility(View.VISIBLE);
            else
                viewHolder.newPic.setVisibility(View.INVISIBLE);
            viewHolder.taskName.setText(t.getTaskName());
            viewHolder.taskAuthor.setText(t.getTeacherName() + "老师");
            viewHolder.taskTime.setText("布置于" + FormatUtil.DateFormat(t.getNewTime()));
        } else {
            viewHolder.newPic.setVisibility(View.INVISIBLE);
            viewHolder.taskName.setText(t.getTaskName());
            viewHolder.taskAuthor.setText(t.getTeacherName() + "老师");
            viewHolder.taskTime.setText("于" + FormatUtil.DateFormat(t.getNewTime()) + "已完成");
        }
    }

    static class ViewHolder {

        @InjectView(R.id.item_task_name)
        TextView taskName;

        @InjectView(R.id.item_task_author)
        TextView taskAuthor;

        @InjectView(R.id.item_task_time)
        TextView taskTime;

        @InjectView(R.id.item_task_new)
        ImageView newPic;


        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void addDataList(List<TaskRespModel> dataList) {

        this.taskList.addAll(dataList);

        notifyDataSetChanged();

    }

}
