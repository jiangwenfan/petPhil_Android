package com.example.petphil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ContactInfo extends AppCompatActivity {
    private TextView friendName;
    private TextView tv_sendMessage;
    String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_info);

        friendName = findViewById(R.id.friendName);
        tv_sendMessage = findViewById(R.id.tv_sendStatusMessage);

        //获取从通讯录activity传过来的 用户头像资源路径
        Intent intent = getIntent();
//        String img = intent1.getStringExtra("img");

        //获取从通讯录activity传过来的 获取用户昵称
       userName = intent.getStringExtra("friendName");
        //String userName = "jwf";

        //获取从通讯录activity传过来的 获取用户id
        //String userid = intent1.getStringExtra("userid");

        //把用户信息设置上去
        friendName.setText(userName);


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


    }
    //给textvew设置点击事件
    public void send2(View view) {
        Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_LONG).show();
//        开启一个聊天意图,并把当前详情页面的用户昵称传过去
        Intent intent = new Intent(getApplicationContext(), DialogChats.class);
        intent.putExtra("friendName", userName); //把用户昵称传到聊天对话框
        startActivity(intent);

    }
}