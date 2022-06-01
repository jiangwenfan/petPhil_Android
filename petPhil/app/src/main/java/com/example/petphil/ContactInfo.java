package com.example.petphil;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ContactInfo extends AppCompatActivity{
    private TextView tv_friendName;
    private TextView tv_userRemarks;
    private TextView tv_userId;
    private ImageView iv_img;


    String userName;
    String userRemarks;
    String userId;
    int img;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_info);

        ActionBar bar = getSupportActionBar();
        bar.setTitle("用户详情");

        tv_friendName = findViewById(R.id.tv_friendName);
        tv_userRemarks =findViewById(R.id.tv_userRemarks);
        tv_userId = findViewById(R.id.tv_userId);
        iv_img = findViewById(R.id.iv_img);
        //tv_sendMessage = findViewById(R.id.tv_sendStatusMessage);

        //获取从通讯录activity传过来的 用户头像资源路径         //获取从通讯录activity传过来的 获取用户昵称
        Intent intent = getIntent();
       userName = intent.getStringExtra("friendName");
       userRemarks = intent.getStringExtra("userRemarks");
       userId = intent.getStringExtra("userId");
         img = intent.getIntExtra("img",2131165312);
         Log.d("debug-test","从上个activity获取到的头像id"+img);


        //把用户信息设置上去
        tv_friendName.setText(userName);
        tv_userRemarks.setText(userRemarks);
        tv_userId.setText(userId);
        iv_img.setImageDrawable(getResources().getDrawable(img));


        //tv_sendMessage.setClickable(true); //给textvew设置点击事件
//        tv_sendMessage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //开启一个聊天意图,并把当前详情页面的用户昵称传过去
////                Intent intent = new Intent(getApplicationContext(),DialogChats.class);
////                intent.putExtra("friendName",userName); //把用户昵称传到聊天对话框
////                startActivity(intent);
//                Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_LONG).show();
//            }
//        });

        //设置返回按钮,显示出来
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);


    }



    //给textvew设置点击事件
    public void send2(View view) {
//        开启一个聊天意图,并把当前详情页面的用户昵称传过去
        Log.d("debug-test","[]开启一个聊天对话框");
        Intent intent = new Intent(getApplicationContext(), DialogChats.class);
        intent.putExtra("userRemarks", userRemarks); //把用户昵称传到聊天对话框
        intent.putExtra("userId",userId); //当前好友id
        Log.d("debug-test","[]开启一个聊天对话框，发送数据完毕"+userRemarks+userId);
        startActivity(intent);
        Log.d("debug-test","[]开启一个聊天对话框,开启意图完毕");



    }


}