package com.example.enlight21.fragments;

import static com.example.enlight21.databinding.FragmentChatBinding.inflate;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.enlight21.Post.SearchActivity;
import com.example.enlight21.databinding.FragmentChatBinding;


public class chat_Fragment extends Fragment {

    private FragmentChatBinding binding;

    public chat_Fragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding =inflate(inflater, container, false);

       binding.Search.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               Intent intent = new Intent(getActivity(), SearchActivity.class);
               startActivity(intent);

           }
       });




       return binding.getRoot();
    }
}