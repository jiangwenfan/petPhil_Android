package com.example.petphil.userHandle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petphil.MainActivity;
import com.example.petphil.R;

public class LoginCode extends AppCompatActivity {

    String password = ""; //以后从网络获取的验证码
    String phoneNumber; //传过来的phoneNumber

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_code); //加载验证码布局

        EditText ed_loginCode = findViewById(R.id.et_LoginCode);
        TextView tv_sendStatusMessage = findViewById(R.id.tv_sendStatusMessage);

        //获取上个activity中的数据，电话号码
        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("phoneNumber");
        Toast.makeText(getApplicationContext(),phoneNumber,Toast.LENGTH_LONG).show();


        //1.发送验证码,自己输入   ------> 当前用随机数模拟，并自动输入
        //生产4位验证码
        String code = "";
        for(int i=0;i<4;i++){
            final double d = Math.random();
            final int i1 = (int) (d*10);
            String i1String = String.valueOf(i1);
            code += i1String;
        }
        //江验证码
        password = code;
        //自动输入
        ed_loginCode.setText(code);

        //2.更新页面发送成功的消息
        tv_sendStatusMessage.setText("已经发送至 "+phoneNumber+" 成功!");

        //3.监听输入框 ----> 当前通过按钮触发

    }
    public void ok(View view){
        //3.监听输入框的低配版

        //获取验证码
        EditText inputCode = findViewById(R.id.et_LoginCode);
        String inputCodeNum = inputCode.getText().toString().trim();

        //对比验证码
        if (inputCodeNum.equals(password)){
            //登陆成功，写入本地文件
            SharedPreferences sharedPreferences = getSharedPreferences("logininfo",MODE_PRIVATE);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString("userName",phoneNumber);
            edit.putString("password",password);
            edit.putBoolean("status",true); //登陆状态
            edit.apply();

            //跳转到main activity
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }else{
            //弹出吐司，输入报错，继续输入
            Toast.makeText(getApplicationContext(),"输入的验证码有误!",Toast.LENGTH_LONG).show();
        }

    }
}