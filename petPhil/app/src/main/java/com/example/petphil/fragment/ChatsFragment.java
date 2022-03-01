package com.example.petphil.fragment;
import androidx.fragment.app.Fragment;
//import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;

import com.example.petphil.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatsFragment extends Fragment {
    /*
    * 这是一个聊天页面的fragment,用来显示聊天页面的数据
    * */

    ListView chats_lv; //listview
    SimpleAdapter simpleAdapter; //数组适配器
    List<Map<String,Object>> mydata; //数据源

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        /*
        * 引入布局文件
        * */
//        View view =  //引入布局文件
        return inflater.inflate(R.layout.fragment_chats,container,false); //返回view
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        /*
        * 这个是方法是当view创建完成之后
        * */
        super.onViewCreated(view, savedInstanceState);
        //tv_chats1 = view.findViewById(R.id.tv_chtas1); //找到fragement的布局文件中的元素进行使用
        chats_lv = view.findViewById(R.id.fr_chats_lv); //找到这个listview
        //模拟数据
        mydata = new ArrayList<>();
        for(int i=0;i<20;i++){
            Map<String,Object> map = new HashMap();
            map.put("img",R.drawable.linglaiyao1); //头像资源写死了
            map.put("title","用户"+i);
            map.put("content","消息,消息"+i);
            mydata.add(map);
        }
        //把数据放和适配器关联
        simpleAdapter = new SimpleAdapter(getActivity(),
                mydata,
                R.layout.listview_item, //把数据和item布局关联起来
                //键值对对应起来
                new String[]{"img","title","content"},
                new int[]{R.id.contacts_iv,R.id.tv_title,R.id.tv_content}
        );

        //把适配器设置给listview
        chats_lv.setAdapter(simpleAdapter);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
