package com.example.petphil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ContactsAdapter extends ArrayAdapter<Contacts> {
    private int resourceId;
    public ContactsAdapter(Context context, int textViewResourceId, List<Contacts> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Contacts contacts = getItem(position); //获取当前项的Contact实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);

//        ImageView contactsImage = (ImageView)view.findViewById(R.id.contacts_image);
//        TextView contactsName = (TextView)view.findViewById(R.id.contacts_name);
//        TextView contactsDesc = (TextView)view.findViewById(R.id.contacts_desc);
//
//        contactsImage.setImageResource(contacts.getImageId());
//        contactsName.setText(contacts.getName());
//        contactsDesc.setText(contacts.getDesc());

        return view;
    }
}
