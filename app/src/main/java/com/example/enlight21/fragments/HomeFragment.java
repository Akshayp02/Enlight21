package com.example.enlight21.fragments;

import static com.example.enlight21.databinding.FragmentHomeBinding.inflate;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.enlight21.R;
import com.example.enlight21.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {

   private FragmentHomeBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = inflate(inflater, container, false);
        // code here




        return binding.getRoot();
    }
}