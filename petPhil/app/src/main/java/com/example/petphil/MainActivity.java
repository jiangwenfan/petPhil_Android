package com.example.petphil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.petphil.fragment.ChatsFragment;
import com.example.petphil.fragment.ContactsFragment;
import com.example.petphil.fragment.MeFragment;
import com.example.petphil.fragment.Test;
import com.example.petphil.userHandle.UserLogin;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    RelativeLayout rl_chats;
    RelativeLayout rl_contacts;
    RelativeLayout rl_me;
    private  ChatsFragment chatsFragment;
    ContactsFragment contactsFragment;
    MeFragment meFragment;
    ActionBar supportActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView(); //获取view id


        /*
        * 设置actionbar标题
        * */
        supportActionBar = getSupportActionBar();
        supportActionBar.setTitle("聊天");
        //设置按钮颜色
        rl_chats.setBackgroundColor(Color.parseColor("#2E8B57"));
        rl_contacts.setBackgroundColor(Color.parseColor("#E6E6FA"));
        rl_me.setBackgroundColor(Color.parseColor("#E6E6FA"));

        /*
        * 进入首页默认把chats这个fragment设置到首页activity中
        * */
        chatsFragment = new ChatsFragment(); //实例化一个fragment
        //把chatsFragement添加到这个指定位置的activity中
        getSupportFragmentManager().beginTransaction().add(R.id.fl_container, chatsFragment).commitAllowingStateLoss();

        meFragment = new MeFragment();



    }

    @Override
    public void onClick(View v) {
        /*
        * 判断fragment的点击按钮
        * */
        switch (v.getId()) {
            case R.id.rl_chats:
                Log.d("chats---","self");
                if(chatsFragment == null){
                    chatsFragment = new ChatsFragment();
                }
                //设置action bar标题
                supportActionBar.setTitle("聊天");
                //shez

                rl_chats.setBackgroundColor(Color.parseColor("#2E8B57"));
                rl_contacts.setBackgroundColor(Color.parseColor("#E6E6FA"));
                rl_me.setBackgroundColor(Color.parseColor("#E6E6FA"));
                //切换回聊天fragment
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,chatsFragment).commitAllowingStateLoss();

                break;
            case R.id.rl_contacts:
                Log.d("---","contacts");
                //切换到通讯录fragment
                if (contactsFragment == null){
                    contactsFragment = new ContactsFragment(); //实例化一个对象
                }
                supportActionBar.setTitle("通讯录");
                rl_chats.setBackgroundColor(Color.parseColor("#E6E6FA"));
                rl_contacts.setBackgroundColor(Color.parseColor("#2E8B57"));
                rl_me.setBackgroundColor(Color.parseColor("#E6E6FA"));
                //切换到通讯录fragment
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,contactsFragment).commitAllowingStateLoss();

                break;
            case R.id.rl_me:
                Log.d("----","me");
                //切换到个人主页frament
                if (meFragment == null){
                    meFragment = new MeFragment();
                }
                supportActionBar.setTitle("个人主页");
                rl_chats.setBackgroundColor(Color.parseColor("#E6E6FA"));
                rl_contacts.setBackgroundColor(Color.parseColor("#E6E6FA"));
                rl_me.setBackgroundColor(Color.parseColor("#2E8B57"));

                getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,meFragment).commitAllowingStateLoss();
                break;
            default:
                Log.d("----","unknow");

        }
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //显示右上角的选项菜单
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_options,menu); //绑定菜单样式
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //设置菜单的绑定事件
        switch (item.getItemId()){
            case R.id.newChats:
                //创建一个群聊
                Intent newChats = new Intent(getApplicationContext(),NewChats.class);
                startActivity(newChats);
                return true;
            case R.id.addContacts:
                //添加联系人界面
                Intent addContacts = new Intent(getApplicationContext(),AddContacts.class);
                startActivity(addContacts);
                return true;
            case R.id.scan:
                //这里将会打开一个拍照的
                Toast.makeText(getApplicationContext(),"开启一个扫描界面",Toast.LENGTH_LONG).show();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initView(){
        rl_chats = findViewById(R.id.rl_chats);
        rl_contacts = findViewById(R.id.rl_contacts);
        rl_me = findViewById(R.id.rl_me);

        rl_chats.setOnClickListener(this);
        rl_contacts.setOnClickListener(this);
        rl_me.setOnClickListener(this);
    }
}