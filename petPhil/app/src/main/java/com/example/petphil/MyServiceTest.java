package com.example.petphil;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class MyServiceTest extends Service {
    private static final String CHANNEL_DEFAULT_IMPORTANCE = "hello";
    private static final int ONGOING_NOTIFICATION_ID = 101;

    public MyServiceTest() {
    }
    private DownloadBinder mBinder = new DownloadBinder();
    //下载管理类
    class DownloadBinder extends Binder{
        public void startDownload(){
            Log.d("MyService","start a download ...");
        }
        //查看下载进度
        public int getProcess(){
            Log.d("MyService","getProcess executed");
            return 0;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return mBinder;
    }

    //处理逻辑，重写方法。服务需要在清单文件中注册。
    //在服务创建时调用
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyService","onCreate executed");
        //-----------------android 12 可能无法使用前台服务---------------------
        //add
        NotificationChannel channel = new NotificationChannel("xxx", "xxx", NotificationManager.IMPORTANCE_LOW);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(manager == null)
            return;
        manager.createNotificationChannel(channel);
        //添加如下变成前台服务。去掉则是后台服务
        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this,0,intent,0);
        //Notification notification = new NotificationCompat.Builder(this)
        Notification notification = new Notification.Builder(this)
                .setContentTitle("this is content title")
                .setContentText("this is content text")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setContentIntent(pi)
                .build();
        //startForeground(1,notification);
        startForeground(ONGOING_NOTIFICATION_ID,notification);
    }
    //会在每次服务启动时调用
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyService","onStartCommand executed");
        return super.onStartCommand(intent, flags, startId);
    }
    //会在服务销毁时调用
    @Override
    public void onDestroy() {
        Log.d("MyService","onDestory executed");
        super.onDestroy();
    }
}