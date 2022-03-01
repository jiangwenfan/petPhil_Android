package com.example.petphil.testDebug;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.petphil.R;

public class BetaIndexActivity extends AppCompatActivity {

    EditText et_bcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_one);

        et_bcode = (EditText)findViewById(R.id.bCode);
    }

    //进入不同的测试activity
    public void betaCode(View v){
        String code = et_bcode.getText().toString().trim();
        if(code.equals("1111")){
            //1111 进入交友测试页面
            System.out.println("111");
            //listView test
            Intent intent = new Intent(getApplicationContext(), TestPageActivity.class);
            startActivity(intent);
        }else if(code.equals("2222")){
            //2222 进入其他ui界面
            System.out.println("2222");
            //download test page
            Intent intent = new Intent(getApplicationContext(), DownloadTest.class);
            startActivity(intent);
        }else if(code.equals("3333")){
            //定位测试
            System.out.println("location test");
            Intent intent = new Intent(getApplicationContext(),CameraTest.class);
            startActivity(intent);
        }else if(code.equals("media")){
            //媒体测试
        }else if(code.equals("ui")){
            //ui 测试
        }else if(code.equals("service")){
            // service
            Log.d("code test","serivce test .. start..");
//            Intent intent = new Intent(getApplicationContext(),ServiceTest.class);
//            startActivity(intent);
        }
        else{
            //
            System.out.println("3333");
            System.out.println(code);
            Toast.makeText(getApplicationContext(),"tips:1111--"+code,Toast.LENGTH_LONG).show();
        }
    }
}