package com.example.petphil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class ContactInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_info);

        //获取从通讯录activity传过来的 用户头像资源路径
        Intent intent1 = getIntent();
        String img = intent1.getStringExtra("img");

        //获取从通讯录activity传过来的 获取用户昵称
        String userName = intent1.getStringExtra("userName");
        //String userName = "jwf";

        //获取从通讯录activity传过来的 获取用户id
        String userid = intent1.getStringExtra("userid");

        //开启一个聊天意图,并把当前详情页面的用户昵称传过去
        Intent intent = new Intent(getApplicationContext(),DialogChats.class);
        intent.putExtra("userName",userName); //把用户昵称传到聊天对话框
        startActivity(intent);

    }
}