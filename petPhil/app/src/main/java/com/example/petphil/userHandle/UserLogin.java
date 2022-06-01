package com.example.petphil.userHandle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.petphil.MainActivity;
import com.example.petphil.R;

public class UserLogin extends AppCompatActivity {
    /*
     * 用来处理登陆操作  登陆注册逻辑处理，进行基本用户认证
     *
     */
    private EditText et_phoneNumber;
    private CheckBox cb_yes;
    private Button bt_login;

    private String phoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_index); //加载登陆首页布局

        ActionBar bar = getSupportActionBar();
        bar.setTitle("[1]认证登陆");

        et_phoneNumber = findViewById(R.id.et_phoneNumber);
        cb_yes = findViewById(R.id.cb_loginStatus);
        bt_login = findViewById(R.id.bt_login);



        //判断是否已经登陆过了
        SharedPreferences sharedPreferences = getSharedPreferences("logininfo", MODE_PRIVATE);
        String userName = sharedPreferences.getString("userName", "");
        String password = sharedPreferences.getString("password", "");
        if (!userName.equals("") && !password.equals("")) {
            //如果存在登陆数据，直接跳转到首页
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }


    public void loginCheck(View view) {
        phoneNumber = et_phoneNumber.getText().toString().trim();

        //进行登陆操作。 获取输入输入的电话号码，传到下个activity
        Intent intent = new Intent(getApplicationContext(), LoginCode.class);
        intent.putExtra("phoneNumber", phoneNumber);
        startActivity(intent);
    }

}