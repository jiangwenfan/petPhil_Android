package com.example.petphil.fragment;

import static com.example.petphil.R.*;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.petphil.R;
import com.example.petphil.SettingsActivity;
import com.example.petphil.Tools.JsonApiHandle;
import com.example.petphil.userHandle.UserHandle;

import org.json.JSONArray;

import java.util.HashMap;

public class MeFragment extends Fragment {
    /*
    * 这是一个用来显示个人信息界面的fragment
    * */

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(layout.fragment_me,container,false); //加载个人信息界面的布局文件
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("debug-test","[me]进入个人界面");
        //获取这个界面的元素
        TextView tv_set = view.findViewById(id.tv_set);
        ImageView me_img = view.findViewById(id.me_img);
        TextView me_userName = view.findViewById(id.me_userName);
        TextView me_userId =  view.findViewById(id.me_userId);

        Log.d("debug-test","[me]设置点击事件");
        //设置点击事件
        tv_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //开启设置页面的activity
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        Log.d("debug-test","[me]开始handle处理主线程数据");
        Log.d("debug-test","[me]------------");
        //Log.d("debug-test","[me]获取用户id[1]"+UserHandle.getLoginedUserId()+"----------");
        //设置数据
        Handler myHandle = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 5:
                        //获取用户信息
                        Log.d("debug-test","进入handle,5");
                        HashMap<String,Object> data = (HashMap<String, Object>) msg.obj;
                        Log.d("debug-test","[me]主线程接收到数据"+data);

                        //更新ui界面
                        me_img.setImageDrawable(getResources().getDrawable(drawable.linglaiyao1)); //设置背景图
                        //me_img.setBackgroundResource(drawable.linglaiyao1);
                        me_userName.setText((String)data.get("userName"));
                        me_userId.setText((String)data.get("userId"));
                        Log.d("debug-test","[me]UI界面更新完毕");
                        break;
                    default:
                        break;
                }
            }
        };

        //发送请求，获取数据
        //Log.d("debug-test","[me]获取用户id[2]"+UserHandle.getLoginedUserId()); //不知道啥原因，就报错。
        SharedPreferences spf = getActivity().getSharedPreferences("logininfo2", Context.MODE_PRIVATE);
        String userId = spf.getString("userId","");
        JsonApiHandle.getUserInfo(myHandle,userId);
        //JsonApiHandle.getUserInfo(myHandle,"2672136463");
        Log.d("debug-test","[me]执行完毕");
    }
}
