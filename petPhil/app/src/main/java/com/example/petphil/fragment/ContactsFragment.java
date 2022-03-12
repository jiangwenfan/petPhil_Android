package com.example.petphil.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.petphil.ContactInfo;
import com.example.petphil.R;

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
    List<Map<String,Object>> mydata; //数据源
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

        //模拟数据
        mydata = new ArrayList<>();
        for(int i=0;i<20;i++){
            Map<String,Object> map = new HashMap();
            map.put("img",R.drawable.linglaiyao1); //头像资源写死了
            map.put("title","用户名"+i);
            map.put("content","个性签名"+i);
            mydata.add(map);
        }
        //获取真实数据
        JsonApi.get

        //把数据放和适配器关联
        simpleAdapter = new SimpleAdapter(getActivity(),
                mydata,
                R.layout.listview_item, //把数据和item布局关联起来
                //键值对对应起来
                new String[]{"img","title","content"},
                new int[]{R.id.contacts_iv,R.id.tv_title,R.id.tv_content}
        );

        //把适配器设置给listview
        fr_contacts_lv.setAdapter(simpleAdapter);

        //设置item点击事件
        fr_contacts_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String,Object> map = mydata.get(i);
                String title = (String)map.get("title");
                //开启一个用户详细信息的actiity
                Intent intent = new Intent(getActivity(), ContactInfo.class);
                intent.putExtra("friendName",title);
                startActivity(intent);
            }
        });
    }
}
