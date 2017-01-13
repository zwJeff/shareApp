package com.jeff.shareapp.ui.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.jeff.shareapp.R;
import com.jeff.shareapp.model.UserinfoModel;
import com.jeff.shareapp.ui.MainActivity;
import com.jeff.shareapp.core.MyApplication;
import com.jeff.shareapp.net.MyVolley;
import com.jeff.shareapp.net.MyVolleyListener;
import com.jeff.shareapp.util.StaticFlag;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 张武 on 2016/7/11.
 */
public class MyGetNotificationService extends Service {

    private static int notificationNum = 0;

    public static boolean isStartLoop=false;


    @Override
    public void onCreate() {
        Log.e("jeff", "service is created!");
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.e("jeff", "service is started!");
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("jeff", "service is running!");
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                getTaskNewNums();
            }
        }, 0, 10*1000);

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void getTaskNewNums() {

        HashMap<String, String> mParams = new HashMap<String, String>();

        MyVolley.getMyVolley().addStringRequest(new TypeToken<Integer>() {
        }.getType(), StaticFlag.GET_STUDENT_TASK_NEW, mParams, new MyVolleyListener<Integer>() {
            @Override
            public void onSuccess(Integer data) {
                if (data > 0)
                    showNotification(data);
            }

            @Override
            public void onFailure(int failureCode, String failureMessage) {
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
            }
        });

    }

    /**
     * 通知栏里显示推送消息
     *
     * @param num
     */
    private void showNotification(int num) {
        //从系统服务中获得通知管理器
        NotificationManager nm = (NotificationManager) MyGetNotificationService.this.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("fragment_id", StaticFlag.TASKPAGE_FRAGMENT);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentTitle("您有新的学习任务！")//设置通知栏标题
                .setContentText("当前共有" + num + "条学习任务等待学习，点击查看详情。") //设置通知栏显示内容
                .setContentIntent(pendingIntent) //设置通知栏点击意图
//	.setNumber(number) //设置通知集合的数量
                .setTicker("您有新的学习任务！") //通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setPriority(Notification.PRIORITY_DEFAULT) //设置该通知优先级
//	.setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                .setSmallIcon(R.mipmap.ic_launcher);//设置通知小ICON
        Notification n = mBuilder.build();
        n.flags = Notification.FLAG_AUTO_CANCEL | Notification.FLAG_INSISTENT;
        nm.notify(++notificationNum, n);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("jeff", "service is stopped!");
    }
}
