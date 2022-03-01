package com.example.petphil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.petphil.fragment.ChatsFragment;
import com.example.petphil.fragment.ContactsFragment;
import com.example.petphil.fragment.MeFragment;
import com.example.petphil.fragment.Test;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

//    EditText et_number; //phone number object
    RelativeLayout rl_chats;
    RelativeLayout rl_contacts;
    RelativeLayout rl_me;
private  ChatsFragment chatsFragment;
ContactsFragment contactsFragment;
MeFragment meFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rl_chats = findViewById(R.id.rl_chats);
        rl_contacts = findViewById(R.id.rl_contacts);
        rl_me = findViewById(R.id.rl_me);

        rl_chats.setOnClickListener(this);
        rl_contacts.setOnClickListener(this);
        rl_me.setOnClickListener(this);

        /*
        * 进入首页默认把chats这个fragment设置到首页activity中
        * */
        chatsFragment = new ChatsFragment(); //实例化一个fragment

        meFragment = new MeFragment();
        //把chatsFragement添加到这个指定位置的activity中
       getSupportFragmentManager().beginTransaction().add(R.id.fl_container, chatsFragment).commitAllowingStateLoss();
      // getSupportFragmentManager().beginTransaction().add(R.id.fl_container, ChatsFragment.class,null,"tag").setReorderingAllowed(true).commitAllowingStateLoss();
        //getSupportFragmentManager().beginTransaction().add(R.id.fl_container,chatsFragment,null).commit();

//        et_number = (EditText)findViewById(R.id.phoneNumber);
//
//        //读取登录状态数据
//        SharedPreferences pref = getSharedPreferences("config",MODE_PRIVATE);
//        Boolean loginstatus = pref.getBoolean("loginStatus",false); //只读取登录状态
//        if(loginstatus == true){
//            Intent intent = new Intent(getApplicationContext(),BetaIndexActivity.class);
//            startActivity(intent);
//        }
    }

//    //get phone number,go on second activity to send authentication code
//    public void getPhoneNumber(View v){
//        String et_number_string = et_number.getText().toString().trim(); //get phone number string
//
//        //handle login 条件 judge
//        if(et_number_string.length() == 0){
//            Toast.makeText(getApplicationContext(),"输入不能为空",Toast.LENGTH_LONG).show();
//        }else
//        {
//            //跳转到authentication code activity
//            Toast.makeText(getApplicationContext(),et_number_string,Toast.LENGTH_LONG).show();
//            Intent intent = new Intent(getApplicationContext(),PasswordLoginActivity.class);
//            intent.putExtra("phoneNumber",et_number_string); //send phone number data
//            startActivity(intent);
//        }
//
//    }
//
//    public void mybt1(View v){
//        Toast.makeText(getApplicationContext(),"test hello",Toast.LENGTH_LONG).show();
//    }

    @Override
    public void onClick(View v) {
        /*
        * 判断按钮
        * */
        switch (v.getId()) {
            case R.id.rl_chats:
                Log.d("chats---","self");
                if(chatsFragment == null){
                    chatsFragment = new ChatsFragment();
                }
                //切换回聊天fragment
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,chatsFragment).commitAllowingStateLoss();

                break;
            case R.id.rl_contacts:
                Log.d("---","contacts");
                //切换到通讯录fragment
                if (contactsFragment == null){
                    contactsFragment = new ContactsFragment(); //实例化一个对象
                }
                //切换到通讯录fragment
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,contactsFragment).commitAllowingStateLoss();

                break;
            case R.id.rl_me:
                Log.d("----","me");
                //切换到个人主页frament
                if (meFragment == null){
                    meFragment = new MeFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,meFragment).commitAllowingStateLoss();
                break;
            default:
                Log.d("----","unknow");

        }
        }
}