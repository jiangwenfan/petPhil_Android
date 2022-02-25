package com.example.petphil;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class TestPageActivity extends AppCompatActivity {
    private List<Contacts> contactsList = new ArrayList<>();
    private String[] data = {"aa","bb","cc","dd","ee","ff","aa","bb","cc","dd","ee","ff","aa","bb","cc","dd","ee","ff"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(TestPageActivity.this, android.R.layout.simple_list_item_1,data);
//        ListView listView = (ListView)findViewById(R.id.list_view);
//        listView.setAdapter(adapter);

        initContacts();
        ContactsAdapter adapter = new ContactsAdapter(TestPageActivity.this,R.layout.contacts_item,contactsList);

        ListView listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);


    }
    private void initContacts(){
        for(int i=0;i<20;i++){
            Contacts linglaiyao1 = new Contacts("lingLaiYao1","cate",R.drawable.linglaiyao1);
            contactsList.add(linglaiyao1);

            Contacts linglaiyao2 = new Contacts("lintaiyao2","love",R.drawable.linglaiyao2);
            contactsList.add(linglaiyao2);

            Contacts linglaiyao3 = new Contacts("linglaiyao3","gold",R.drawable.linglaiyao3);
            contactsList.add(linglaiyao3);
        }
    }
}
