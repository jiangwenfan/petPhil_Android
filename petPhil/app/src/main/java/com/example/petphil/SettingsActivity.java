package com.example.petphil;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_settings);

        //目前未实现功能，只显示一个演示界面
        ActionBar bar = getSupportActionBar();
        bar.setTitle("设置页面");

    }
}