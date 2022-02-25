package com.example.petphil;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ServiceTest extends AppCompatActivity implements View.OnClickListener{
    private MyServiceTest.DownloadBinder downloadBinder;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_test);

        Button startService = (Button)findViewById(R.id.start_service);
        Button stopService = (Button)findViewById(R.id.stop_service);
        Button bindService = (Button)findViewById(R.id.bind_service);
        Button unbindService = (Button)findViewById(R.id.unbind_service);
        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);
        bindService.setOnClickListener(this);
        unbindService.setOnClickListener(this);
    }

    //实现接口，重写onClick
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_service:
                //创建意图对象，启动服务
                Intent startIntent = new Intent(this,MyServiceTest.class);
                startService(startIntent);
                break;
            case R.id.stop_service:
                Intent stopIntent = new Intent(this,MyServiceTest.class);
                stopService(stopIntent);
                break;
            case R.id.bind_service:
                Intent intent = new Intent(this,MyServiceTest.class);
                bindService(intent,connection,BIND_AUTO_CREATE); //绑定服务
                break;
            case R.id.unbind_service:
                unbindService(connection); //解绑服务
                break;
            default:
                break;
        }
    }

    //绑定服务
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (MyServiceTest.DownloadBinder) service;
            downloadBinder.startDownload();
            downloadBinder.getProcess();
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

}
