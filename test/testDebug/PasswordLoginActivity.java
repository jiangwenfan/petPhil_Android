package com.example.petphil.testDebug;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.petphil.R;
import com.example.petphil.testDebug.AuthenticationCodeActivity;
import com.example.petphil.testDebug.BetaIndexActivity;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class PasswordLoginActivity extends AppCompatActivity {

    String phoneNumber;
    EditText et_password;
    String et_password_string;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_password);

        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("phoneNumber");

        et_password = (EditText)findViewById(R.id.password_text);
    }

    //跳转到authentication code
    public void authenticationCodeLogin(View v){
        Intent intent = new Intent(getApplicationContext(), AuthenticationCodeActivity.class);
        intent.putExtra("phoneNumber",phoneNumber);
        startActivity(intent);
    }

    //进行login判断
    public void login(View v) {
        et_password_string = et_password.getText().toString().trim();

        //将用户名和密码到远程Server上进行对比,接受返回值进行判断
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add("phoneNumber", phoneNumber)
                            .add("password", et_password_string).build();
                    Request request = new Request.Builder().url("https://mock.uutool.cn/test2")
                            .post(requestBody)
                            .build();
                    //Response response = client.newCall(request).execute();
                    //String responseData = response.body().string(); //server 返回的数据。 true or false
                    //Boolean loginStatus = Boolean.valueOf(responseData);

                    Boolean loginStatus = true; //test [debug]
                    //进行返回值判断
                    if (loginStatus) {
                        //不能在子线程中使用
                        //Toast.makeText(getApplicationContext(),"login success!",Toast.LENGTH_LONG).show();
                        //记住密码，存储到本地
                        SharedPreferences.Editor editor = getSharedPreferences("config",MODE_PRIVATE).edit();
                        editor.putString("phoneNumber",phoneNumber);
                        editor.putBoolean("loginStatus",loginStatus);
                        editor.apply();
                        //跳转到首页
                        Intent intent = new Intent(getApplicationContext(), BetaIndexActivity.class);
                        startActivity(intent);
                    } else
                    {
                        System.out.println("toast");
                        //Toast.makeText(getApplicationContext(),"密码错误! tips: 111---"+et_password_string,Toast.LENGTH_LONG).show();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();



    }
}