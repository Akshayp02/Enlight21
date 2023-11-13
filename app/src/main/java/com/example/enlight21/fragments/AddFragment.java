package com.example.enlight21.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.enlight21.Post.PostActivity;
import com.example.enlight21.R;
import com.example.enlight21.databinding.FragmentAddBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddFragment extends BottomSheetDialogFragment {

    private FragmentAddBinding binding;


    public AddFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAddBinding.inflate(inflater, container, false);

        binding.addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PostActivity.class);
                 startActivity(intent);


            }
        });




        return binding.getRoot();
    }
}