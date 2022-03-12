package com.example.petphil.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.example.petphil.R;


public class Test extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //SharedPreferences spf = getActivity().getSharedPreferences();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test, container, false);
    }
}