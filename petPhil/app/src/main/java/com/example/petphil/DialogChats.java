package com.example.petphil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.petphil.rabbitMQHandle.RabbitMQHandle;

import java.util.HashMap;
import java.util.Map;

public class DialogChats extends AppCompatActivity {

    private TextView tv_userName;
    private EditText et_messageContent;
    private String userid;
    private String friendid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_chats);


        //从上一个Integer意图拿到用户昵称
        Intent intent = getIntent();
        String userName = intent.getStringExtra("friendName"); //拿到从上activity传过来的好友昵称
        //userid = intent.getStringExtra("userid"); //拿到从上Activity传过来的user id
        //friendid = intent.getStringExtra("friendid"); //拿到好友id

        //把用户名更新到对话框中
        tv_userName = findViewById(R.id.tv_userName);
        tv_userName.setText(userName);

        //开始监听是否有发过来的消息
        //1.拼接队列名
        String receiveQueueName = friendid+"-"+userid;
        //创建子线程,连接rabbitmq并持续监听队列，将队列中的数据返回到主线程，更新ui界面

//        //2.连接该队列
//        RabbitMQHandle mq = new RabbitMQHandle(receiveQueueName);
//        //3.进行监听数据监听，如果有数据自动更新到页面
//        mq.getMessg();

    }

    public void sendMessg(View view) {
        /*
        * 处理发送消息
        * */
        //获取要发送的消息
        et_messageContent = findViewById(R.id.et_messageContent);
        String messageContent = et_messageContent.getText().toString().trim();

        //将要发送的消息更新到界面
        //[1.]将消息发送给fragment,添加一个条目
        //
        //[2.]删除输入框中的消息
        et_messageContent.setText("");

        //将要发送的消息通过api进行发送
        Map<String,String> data = new HashMap<>(); //要发送的数据
        data.put("userid",userid);
        data.put("message", messageContent);
        //api http://project.pwall.ciu/api/send


    }
}