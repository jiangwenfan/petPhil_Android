package com.example.petphil.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.petphil.ContactInfo;
import com.example.petphil.R;
import com.example.petphil.Tools.JsonApiHandle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactsFragment  extends Fragment {
    /*
    * 这是一个联系人fragment,用来显示联系人界面信息
    * */
    ListView fr_contacts_lv;
    SimpleAdapter simpleAdapter; //数据适配器
    ArrayList<HashMap<String,Object>> conData; //数据

    String Tag = "通讯录页面-";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts,container,false); //加载通讯录布局文件
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //获取通信录布局文件中的元素
        fr_contacts_lv = view.findViewById(R.id.fr_contacts_lv);

        //创建handle接收消息
        Handler myHandler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                //处理接收到的消息
                switch (msg.what){
                    case 0:
                        //0表示网络测试
                        String mydata = (String) msg.obj;
                        Toast.makeText(getActivity(),mydata,Toast.LENGTH_SHORT).show();
                        Log.d("debug-test",Tag+"网络测试的handle "+mydata);
                        break;
                    case 1:
                        //进行api测试
                        ArrayList<HashMap<String ,String>> data = (ArrayList<HashMap<String, String>>) msg.obj;
                        Log.d("debug-test",Tag+"api测试的页面handle "+data.toString());
                        break;
                    case 3:
                        //进行联系人获取
                        conData = (ArrayList<HashMap<String, Object>>) msg.obj;
                        Log.d("debug-test",Tag+"获取联系人主线程:"+conData);
                        //更新到界面
                        list3(conData);
                         break;
                    default:
                        break;
                }
            }
        };

        //获取真实数据
        //[1]从sp中读取userId
        SharedPreferences spf = getActivity().getSharedPreferences("logininfo2", Context.MODE_PRIVATE);
        String userId = spf.getString("userId","");
        Log.d("debug-test","[聊天activity]从loginfo2.xml读取userId"+userId);
        //联系人信息获取,传入当前用户id
        //JsonApiHandle.getAllContacts(myHandler,"2672136463"); //3
        JsonApiHandle.getAllContacts(myHandler,userId); //3


        //显示从网络拿到的数据
        //JsonApiHandle.TestNetwork(myHandler);

        //测试json解析
        //JsonApiHandle.TestApi(myHandler);

    }

    public  void list3(ArrayList<HashMap<String,Object>> conData){
        /*
        * fragement后半部分代码,真实数据
        * */

        //把数据放和适配器关联
        simpleAdapter = new SimpleAdapter(getActivity(),
                conData,
                R.layout.listview_item, //把数据和item布局关联起来
                //键值对对应起来
                new String[]{"img","userName","userSentence"},
                new int[]{R.id.contacts_iv,R.id.tv_title,R.id.tv_content}
        );

        //把适配器设置给listview
        fr_contacts_lv.setAdapter(simpleAdapter);

        //设置item点击事件
        fr_contacts_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String,Object> map = conData.get(i);
                Log.d("debug-test",Tag+"通讯录数据"+conData);

                String userName = (String)map.get("userName");
                String userRemarks = (String)map.get("userRemarks");
                String userId = (String)map.get("userId");



                //开启一个用户详细信息的actiity
                Intent intent = new Intent(getActivity(), ContactInfo.class);
                intent.putExtra("friendName",userName);
                intent.putExtra("userRemarks",userRemarks);
                intent.putExtra("userId",userId);
                Log.d("debug-test",Tag+"传入用户详情页的用户备注:"+userName);

                int img = (int) map.get("img");
                intent.putExtra("img",img);
                Log.d("debug-test",Tag+"传入用户头像:"+img);
                startActivity(intent);
            }
        });
    }
}
