package com.example.petphil.Tools;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class JsonApiHandle {
    /*
     * 这个类用来进行api操作
     * */

    public static ArrayList<Map<String, Object>> getContactsInfo() throws IOException, JSONException {

//        private Handler handler = new Handler(){
//            public void handleMessage(android.os.Message msg){
//                //进行判断执行....
//                switch(msg.what){
//                    case ...:
//                        break;
//                    default:
//                        break;
//                }
//            };
//        };

        //开启子线程获取数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Map<String, Object>> mydata = new ArrayList<>();

                OkHttpClient client = new OkHttpClient();

                //创建一个request body对象，存放要提交的数据
                RequestBody requestBody = new FormBody.Builder()
                        .add("userid", "admin")
                        .build();

                //创建一个request对象，存放body
                Request request = new Request.Builder()
                        .url("http://www.baidu.com")
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();

                    //数据解析
                    // 解析json.[{},{}] 列表套字典
                    JSONArray jsonArray = new JSONArray(responseData); //将数据转为json数组对象
                    //循环遍历json数组
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i); //获取json数组中的每一个json对象
                        String friendName = jsonObject.getString("friendName"); //用户名
                        String friendRemarkName = jsonObject.getString("friendRemarkName"); //用户备注名
                        String friendId = jsonObject.getString("friendId"); //用户id

                        //存储到字典，哈希map中
                        HashMap<String, Object> friendInfo = new HashMap<>();
                        friendInfo.put("friendId", friendId);
                        friendInfo.put("friendName", friendName);
                        friendInfo.put("friendRemarkName", friendRemarkName);

                        //存储到列表中
                        mydata.add(friendInfo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //发送到主线程
                Message message = new Message();
                message.what = flag;
                message.obj = info;
                handler.sendMessage(message);
            }
        }).start();
    }
}
