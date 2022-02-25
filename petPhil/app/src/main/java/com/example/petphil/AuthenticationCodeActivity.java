package com.example.petphil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class AuthenticationCodeActivity extends AppCompatActivity {
    String phoneNumber;
    EditText et_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_verification_code);

        et_code = (EditText)findViewById(R.id.code);

        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("phoneNumber");

        TextView tv_message = (TextView) findViewById(R.id.sendMessage);
        String tv_message_string = tv_message.getText().toString().trim();
        String result = tv_message_string + phoneNumber;
        tv_message.setText(result);

        //生产4位验证码
        String num1 = "";
        for(int i=0;i<4;i++){
            final double d = Math.random();
            final int i1 = (int) (d*10);
            String i1String = String.valueOf(i1);
            num1 += i1String;
        }
        et_code.setText(num1);
    }


}