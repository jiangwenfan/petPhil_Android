package com.example.petphil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText et_number; //phone number object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone_number);

        et_number = (EditText)findViewById(R.id.phoneNumber);

        //读取登录状态数据
        SharedPreferences pref = getSharedPreferences("config",MODE_PRIVATE);
        Boolean loginstatus = pref.getBoolean("loginStatus",false); //只读取登录状态
        if(loginstatus == true){
            Intent intent = new Intent(getApplicationContext(),BetaIndexActivity.class);
            startActivity(intent);
        }
    }

    //get phone number,go on second activity to send authentication code
    public void getPhoneNumber(View v){
        String et_number_string = et_number.getText().toString().trim(); //get phone number string

        //handle login 条件 judge
        if(et_number_string.length() == 0){
            Toast.makeText(getApplicationContext(),"输入不能为空",Toast.LENGTH_LONG).show();
        }else
        {
            //跳转到authentication code activity
            Toast.makeText(getApplicationContext(),et_number_string,Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(),PasswordLoginActivity.class);
            intent.putExtra("phoneNumber",et_number_string); //send phone number data
            startActivity(intent);
        }

    }

    public void mybt1(View v){
        Toast.makeText(getApplicationContext(),"test hello",Toast.LENGTH_LONG).show();
    }

}