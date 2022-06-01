package com.example.petphil;

import static com.example.petphil.Tools.ContextApplication.getAppContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.R.layout;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petphil.Tools.ContextApplication;
import com.example.petphil.rabbitMQHandle.RabbitMQHandle;
import com.example.petphil.userHandle.UserHandle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DialogChats extends AppCompatActivity {

    private TextView tv_userName;
    private EditText et_messageContent;
    private ListView lv_dialog_Listview;
    ArrayAdapter<String> myArrayAdapter;

    ArrayList<HashMap<String,String>> data;
    ArrayList<String> resData;

    private String userId;
    private String friendid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_chats);

        Log.d("debug-test","[聊天对话框Activity]");

        ActionBar bar = getSupportActionBar();
        bar.setTitle("聊天对话框");

        tv_userName = findViewById(R.id.tv_userName);
        lv_dialog_Listview = findViewById(R.id.dialog_listview); //显示消息的listview


        //从上一个Integer意图拿到用户昵称
        Intent intent = getIntent();
        String userRemarks = intent.getStringExtra("userRemarks"); //拿到从上activity传过来的好友昵称
        friendid = intent.getStringExtra("userId"); //拿到好友id

        //读取用户id
        //userId = UserHandle.getLoginedUserId();
        SharedPreferences spf = getSharedPreferences("logininfo2", Context.MODE_PRIVATE);
        userId = spf.getString("userId","");
        Log.d("debug-test","[聊天activity]从loginfo2.xml读取userId"+userId);

        Log.d("debug-test","获取信息用户id:"+userId+" 好友id:"+friendid);

        //把用户名更新到对话框中
        tv_userName.setText(userRemarks);

        //获取历史聊天记录，并加载
        initMessage();



        //开始监听是否有发过来的消息
        //1.拼接队列名
       // String receiveQueueName = friendid+"-"+userid;
        //创建子线程,连接rabbitmq并持续监听队列，将队列中的数据返回到主线程，更新ui界面

//        //2.连接该队列
//        RabbitMQHandle mq = new RabbitMQHandle(receiveQueueName);
//        //3.进行监听数据监听，如果有数据自动更新到页面
//        mq.getMessg();



    }

    public void sendMessg(View view) {
        /*
        * 处理发送消息
        * */
        //获取要发送的消息
        et_messageContent = findViewById(R.id.et_messageContent);
        String messageContent = et_messageContent.getText().toString().trim();
        Log.d("debug-test","输入的内容是:"+messageContent);

        //将要发送的消息更新到界面
        String defaultBlank = "\u3000\u3000\u3000\u3000";
        if(messageContent.length() >= 24){
            //5个空白，加字符
            String message = "\n"+"自己:"+"\n"+defaultBlank+messageContent+"\n";
            resData.add(message);
        }else{
            //不足一行，多加空白字符
            //int num = 24 - messageContent.length();
            int num = 12 - messageContent.length();
            Log.d("debug-test","输入字符个数:"+num);
            if (num == 1){
                Log.d("debug-test","小于一个字符");
                String numBlank = "\u3000";
                String message = "\n"+"自己:"+"\n"+defaultBlank+numBlank+messageContent+"\n";
                resData.add(message);
            }else{
                Log.d("debug-test","大于一个字符");
                String numBlank2 = "\u3000";
                for(int k=1;k<num;k++){
                    numBlank2 = numBlank2 +"\u3000";
                }
                String message = "\n"+"自己:"+"\n"+defaultBlank+numBlank2+"[新]"+messageContent+"\n";
                //String message = "\n"+"自己:"+"\n"+"[新]"+defaultBlank+"test"+"test2"+messageContent+"\n";
                Log.d("debug-test"," "+message);
                resData.add(message);
            }
        }
        Log.d("debug-test","新数据:"+resData);
        myArrayAdapter.notifyDataSetChanged();
        lv_dialog_Listview.setAdapter(myArrayAdapter);


        //[2.]删除输入框中的消息
        et_messageContent.setText("");

        Toast.makeText(this,"消息发送成功!",Toast.LENGTH_SHORT).show();

        /*
        //将要发送的消息通过api进行发送
        Map<String,String> data = new HashMap<>(); //要发送的数据
        data.put("userid",userId);
        data.put("message", messageContent);
        //api http://project.pwall.ciu/api/send
         */

    }

    public void initMessage(){
        /*
        * 初始化聊天记录，从云端拉取聊天记录，显示到当前ui界面
        * */
        //从本地读取suerId
        Log.d("debug-test","[聊天对话框]从云端获取聊天记录，并显示在当前对话框页面上");


        //主线程更新出来数据
        Handler handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if(msg.what == 0){
                    //更新ui界面
                    data = (ArrayList<HashMap<String, String>>) msg.obj;
                    Log.d("debug-test","[聊天对话框]主线程接收到的数据"+data);

                    //生成可供显示的字符串数据
                    resData = generateStringData(data);
                    Log.d("debug-test","主线程:接收到的真实数据:"+resData);


                    //创建我的数组适配器对象
                    myArrayAdapter = new ArrayAdapter<>(
                            getApplicationContext(),
                            layout.simple_list_item_1,
                            resData
                    );
                   // Log.d("debug-test","测试数据:"+myTestData);
                    Log.d("debug-test","真实聊天对话框数据:"+resData);
                    Log.d("debug-test","设置我的数值适配器");
                    //设置数组适配器
                    lv_dialog_Listview.setAdapter(myArrayAdapter);
                    Log.d("debug-test","设置我的数值适配器--ok");

                }
            }
        };


        //1.拉取数据
        //http://119.29.194.108:8085/api/getChatRecords/?userId=3821914126&friendId=222333
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://119.29.194.108:8085/api/getChatRecords/?userId="+userId+"&friendId="+friendid;
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .get()
                        .build();
                try {
                    Log.d("debug-test","[聊天对话框] 开始从api中获取聊天记录");
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d("debug-test","[聊天对话框]url:"+url);
                    Log.d("debug-test","[聊天对话框]api返回的响应数据:"+responseData);

                    //解析数据
                    ArrayList<HashMap<String,String>> data = new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(responseData);
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        HashMap<String,String> map= new HashMap<>();
                        String userId = jsonObject.getString("userId");
                        String chatsTime = jsonObject.getString("chatsTime");
                        String chatsMessage = jsonObject.getString("chatsMessage");
                        map.put("userId",userId);
                        map.put("chatsTime",chatsTime);
                        map.put("chatsMessage",chatsMessage);
                        data.add(map);
                    }
                    Log.d("debug-test","[聊天对话框]解析后的数据"+data);

                    //向主线程发送数据
                    Message message = new Message();
                    message.what = 0;
                    message.obj = data;
                    handler.sendMessage(message);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();;

        /*
        //2.解析数据
        Date date = new Date();
        date.setTime(10000000); //从云端拿到的时间戳
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss", Locale.CHINA);
        String messageTime = sdf.format(date); //格式化待显示的字符串
         */



    }

    //3.将json数据，生成可供显示的字符串数据
    public ArrayList<String> generateStringData(ArrayList<HashMap<String,String>> data){
        ArrayList<String> resData = new ArrayList<>();
        Log.d("debug-test","[-]进行格式化字符串生成");
        for(int i=0;i<data.size();i++){
            HashMap<String,String> map = data.get(i);
            String userId = map.get("userId");
            Log.d("debug-test","[-]进行格式化字符串生成:获取用户id:"+userId);

//            SharedPreferences spf = getAppContext().getSharedPreferences("logininfo2",MODE_PRIVATE);
//            String currentUserId =  spf.getString("userId","xxxx");
//            Log.d("debug-test","[-]从本地loginfo2.xml中读取用户id"+currentUserId);
            //[1]从sp中读取userId
            SharedPreferences spf = getSharedPreferences("logininfo2", Context.MODE_PRIVATE);
            String currentUserId = spf.getString("userId","");
            Log.d("debug-test","[--]从loginfo2.xml读取userId"+currentUserId);

            if(userId.equals(currentUserId)){
                Log.d("debug-test","[-]进行格式化字符串生成:if--");
                //和当前登录用户id一样，说明是用户自己的发的消息
                String chats = map.get("chatsMessage");
                //空白 \u3000
                //最多29个字符，有5个事默认低头，那么实际存放24个字符
                Log.d("debug-test","[-]进行格式化字符串生成:获取用户自己发送的消息:"+chats);
                String defaultBlank = "\u3000\u3000\u3000";
                if(chats.length() >= 24){
                    //5个空白，加字符
                    String message = "\n"+"自己:"+"\n"+defaultBlank+chats+"\n";
                    Log.d("debug-test","api:"+message);
                    resData.add(message);
                }else{
                    //不足一行，多加空白字符
                    //空白字符个数
                    int num = 24 - chats.length();
                    if (num == 1){
                        String numBlank = "\u3000";
                        String message = "\n"+"自己:"+"\n"+defaultBlank+numBlank+chats+"\n";
                        resData.add(message);
                    }else{
                        String numBlank2 = "\u3000";
                        for(int k=1;k<num;k++){
                            numBlank2 = numBlank2 +"\u3000";
                        }
                        String message = "\n"+"自己:"+"\n"+defaultBlank+numBlank2+chats+"\n";
                        resData.add(message);
                    }
                }
            }else{
                //和当前用户id不一样，说明是好友消息
                Log.d("debug-test","[-]进行格式化字符串生成:else--");
                String chats = "\n"+"好友:"+"\n"+map.get("chatsMessage")+"\n";
                Log.d("debug-test","[-]进行格式化字符串生成:获取好友发送的消息:"+chats);
                resData.add(chats);
            }
        }

        return resData;
    }

}