package com.example.petphil.Tools;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.petphil.R;

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

//    public static ArrayList<Map<String, Object>> getContactsInfo() throws IOException, JSONException {
//
//
//    }

    public static Void TestNetwork(Handler myHandler){
        /*
        * 测试网络请求
        * */

        new Thread(new Runnable() {
            @Override
            public void run() {
                //发送请求
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://www.baidu.com")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    //Log.d("test","handle slave:"+responseData);
                    //把消息传给主线程
                    Message message =new Message();
                    message.what = 0;
                    //message.obj = "hellohellohello子线程消息";
                    message.obj = responseData;
                    myHandler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //Log.d("test","method: rentun ----> "+data[0]);
        return null;
    }
    public static void TestApi(Handler myHandle){
        /*
        * 用来测试解析json数据
        * */
        new Thread(new Runnable() {
            @Override
            public void run() {
                //发送请求
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("http://119.29.194.108/data.json")
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d("test-------------->","12121####\n"+responseData);
                    //数据解析
                    JSONArray jsonArray = new JSONArray(responseData); //数据变json数组对象

                    Log.d("test","xunhappy--测试jsonc长度-->222"+jsonArray.length());
                    ArrayList<HashMap<String,String>> data = new ArrayList<>();

                    for(int i=0;i<jsonArray.length();i++){
                        Log.d("test","测试json"+i);
                        HashMap<String,String> item = new HashMap<>();

                        JSONObject jsonObject = jsonArray.getJSONObject(i); //每个内容哦都是json对象
                        String name = jsonObject.getString("name");
                        String age = jsonObject.getString("age");

                        item.put("name",name);
                        item.put("age",age);
                        data.add(item);
                        Log.d("test","--->测试json---->12121解析成功"+item);
                    }
                    Log.d("test","12121解析结束"+data);
                    //发送到主线程
                    Message message =new Message();
                    message.what = 1;
                    message.obj = data;
                    myHandle.sendMessage(message);

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }



            }
        }).start();

    }

    public static void getAllContacts(Handler myHandle,String userId){
        /*
        * 根据用户id，获取联系人
        * 这个id可以写到本地
        * */
        new Thread(new Runnable() {
            @Override
            public void run() {
                //http://119.29.194.108:8085/api/getAllContacts/?userId=6771749816
                OkHttpClient client = new OkHttpClient();
                String urlw="http://119.29.194.108:8085/api/getAllContacts/?userId="+userId;
                //String urlw="http://119.29.194.108/con.json";
                Log.d("debug-test",urlw);
                Request request = new Request.Builder()
                        .get()
                        .url(urlw)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d("debug-test","联系人通讯录数据"+responseData);
                    //解析数据
                    JSONArray jsonArray = new JSONArray(responseData);
                    ArrayList<HashMap<String,Object>> data = new ArrayList<>();
                    Log.d("debug-test","xunha----联系人长度---->222"+jsonArray.length());

                    for(int jindex=0;jindex<jsonArray.length();jindex++){
                        Log.d("test","xunha---联系人-循环次数---->"+jindex);
                        JSONObject record = jsonArray.getJSONObject(jindex);

                        HashMap<String,Object> item = new HashMap<>();

                        String userName = record.getString("userName");
                        Log.d("test","联系人:"+userName);
                        String userId =record.getString("userId");
                        Log.d("test","联系人:"+userId);
                        String userRemarks = record.getString("userRemarks");
                        Log.d("test","联系人:"+userRemarks);
                        String userSentence = record.getString("userSentence");
                        Log.d("test","联系人:"+userSentence);

                        item.put("userName",userName); //好友昵称
                        item.put("userId",userId); //好友id
                        item.put("userRemarks",userRemarks); //好友备注 useRemarks
                        item.put("userSentence",userSentence); //好友个签
                        item.put("img",R.drawable.linglaiyao1); //头像资源写死了

                        data.add(item);
                        Log.d("test","---联系人--->解析成功"+item);
                    }
                    Log.d("test","12121解析-结束"+data);
                    //发送到主线程
                    Message message =new Message();
                    message.what = 3;
                    message.obj = data;
                    myHandle.sendMessage(message);
                    Log.d("test","12121发送成功"+data);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public static void getUserInfo(Handler myHandle,String userId){
        /*
        * 根据用户自己的id，获取用户自己的信息
        * */
        new Thread(new Runnable() {
            @Override
            public void run() {
                /*
                *http://119.29.194.108:8085/api/getUserInfo/?userId=6771749816
                * */
                Log.d("debug-test","进入api获取个人信息");
                OkHttpClient client = new OkHttpClient();
                String url ="http://119.29.194.108:8085/api/getUserInfo/?userId="+userId;
                Request request = new Request.Builder()
                        .get()
                        .url(url)
                        .build();
                try {
                    Log.d("debug-test","要发送url:"+url);
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d("debug-test","获取自己的信息"+responseData);

                    //解析数据
                   JSONArray jsonArray = new JSONArray(responseData);
                   JSONObject record = jsonArray.getJSONObject(0);

                   HashMap<String,Object> data = new HashMap<>();
                   data.put("userName",record.getString("userName"));
                   data.put("userId",record.getString("userId"));
                   data.put("img",R.drawable.linglaiyao1);

                    //发送数据
                    Message message = new Message();
                    message.what = 5;
                    message.obj = data;
                    myHandle.sendMessage(message);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
