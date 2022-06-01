package com.example.petphil.fragment;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
//import android.app.Fragment;
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

import androidx.annotation.Nullable;

import com.example.petphil.DialogChats;
import com.example.petphil.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChatsFragment extends Fragment {
    /*
    * 这是一个聊天页面的fragment,用来显示聊天页面的数据
    * */

    ListView chats_lv; //listview
    SimpleAdapter simpleAdapter; //数组适配器
    ArrayList<HashMap<String,Object>> data; //数据

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        /*
        * 引入布局文件
        * */
        return inflater.inflate(R.layout.fragment_chats,container,false); //返回view


    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        /*
        * 这个是方法是当view创建完成之后
        * */
        super.onViewCreated(view, savedInstanceState);


        chats_lv = view.findViewById(R.id.fr_chats_lv); //找到这个listview


        //实际数据
        //从访问api获取数据
        //[1]从sp中读取userId
        SharedPreferences spf = getActivity().getSharedPreferences("logininfo2", Context.MODE_PRIVATE);
        String userId = spf.getString("userId","");
        Log.d("debug-test","[聊天activity]从loginfo2.xml读取userId"+userId);

        //在主线程获取数据
        Handler handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                if(msg.what == 0){
                    data = (ArrayList<HashMap<String, Object>>) msg.obj; //取出消息
                    Log.d("debug-test","主线程中接收到最新last聊天数据"+data);

                    //更新ui
                    //把数据放和适配器关联
                    simpleAdapter = new SimpleAdapter(getActivity(),
                            data,
                            R.layout.listview_item, //把数据和item布局关联起来
                            //键值对对应起来
                            new String[]{"img","title","content"},
                            new int[]{R.id.contacts_iv,R.id.tv_title,R.id.tv_content}
                    );

                    //把适配器设置给listview
                    chats_lv.setAdapter(simpleAdapter);

                    //设置点击动作
                    chats_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Log.d("debug-test","[聊天]响应item点击事件");
                            //设置点击事件
                            HashMap<String,Object> map = data.get(i);
                            String title = (String)map.get("title"); //好友昵称
                            String userId = (String) map.get("userId");
                            //开启聊天对话框
                            Log.d("debug-test","[聊天]开启对话框意图");
                            Intent intent = new Intent(getActivity(), DialogChats.class);
                            Log.d("debug-test","[聊天]开启对话框意图,传入数据"+title);
                            intent.putExtra("userRemarks",title);
                            intent.putExtra("userId",userId);
                            startActivity(intent);
                            Log.d("debug-test","[聊天]开启对话框意图,开启成功");
                        }
                    });
                }
            }
        };

        //[2]开启子线程获取最新聊天数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                //http://119.29.194.108:8085/api/lastChatRecords/?userId=3821914126
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://119.29.194.108:8085/api/lastChatRecords/?userId="+userId)
                        .get()
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d("debug-test","从api中获取最新last的聊天记录数据，响应字符串"+responseData);

                    //解析数据
                    JSONArray jsonArray = new JSONArray(responseData);
                    ArrayList<HashMap<String,Object>> data = new ArrayList<HashMap<String,Object>>();
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String friendId = jsonObject.getString("friendId");
                        String friendName = jsonObject.getString("userName");
                        String text = jsonObject.getString("data");
                        JSONObject jsonObject1 = new JSONObject(text);
                        String messg = jsonObject1.getString("chatsMessage");

                        HashMap<String,Object> map = new HashMap<>();
                        map.put("friendId",friendId);
                        map.put("title",friendName);
                        map.put("content",messg);
                        map.put("img",R.drawable.linglaiyao1);
                        map.put("userId",jsonObject1.getString("userId")); //加个用户id
                        data.add(map);
                    }
                    Log.d("debug-test","解析之后的last聊天记录数据"+data);

                    //发送到主线程
                    Message message = new Message();
                    message.what = 0;
                    message.obj = data;
                    handler.sendMessage(message);

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();





    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}
