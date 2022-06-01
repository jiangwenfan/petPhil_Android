package com.example.petphil.userHandle;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.petphil.Tools.ContextApplication;

public class UserHandle{
    /*
    * 这是一个用来处理用户信息的类
    * */

    public static String getLoginedUserId(){
        //等到当前登录用户的id.从loginfo2.xml中读取
        SharedPreferences spf = ContextApplication.getAppContext().getSharedPreferences("logininfo2",MODE_PRIVATE);
        String userId = spf.getString("userId","xxxx");
        Log.d("debug-tets","[....]从本地loginfo2.xml中读取用户id"+userId);
        return userId;
    }

    /*
    private String id=null;
    public UserHandle(String id){
        this.id = id;
    }

    public String getImageUrl(){

        return "lujing";
    }

    public String getUserName(){

        return "zhangsan";
    }

    public String getUserId(){
        return "hello";
    }
    */
}
