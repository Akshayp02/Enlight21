package com.example.enlight21.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.enlight21.Adapters.ViewpagerAdapter;
import com.example.enlight21.Models.User;
import com.example.enlight21.databinding.FragmentProfileBinding;

import com.example.enlight21.editProfileActivity;

import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.Objects;


public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ViewpagerAdapter viewpagerAdapter ;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        binding.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), editProfileActivity.class);
                intent.putExtra("Mode", 1);
                startActivity(intent);
            }
        });

        viewpagerAdapter = new ViewpagerAdapter(requireActivity().getSupportFragmentManager(), getLifecycle());

        // Add fragments to the adapter
        viewpagerAdapter.addFragment(new MyPostFragment(), "My Post");
        viewpagerAdapter.addFragment(new MySrtaredFragment(), "My Stared");

        // Set the adapter to the ViewPager2
        binding.Viewpager.setAdapter(viewpagerAdapter);

        // Use TabLayoutMediator to connect TabLayout with ViewPager2
        new TabLayoutMediator(binding.tabLayout, binding.Viewpager,
                (tab, position) -> tab.setText(viewpagerAdapter.getTitle(position))
        ).attach();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onStart() {
        super.onStart();
        // code here
        db.collection("users").document(user.getUid()).get().addOnSuccessListener(documentSnapshot -> {

            User user = documentSnapshot.toObject(User.class);
            assert user != null;
            binding.user.setText(user.getName());
            binding.Disciption.setText(user.getDescription());


            if(user.image != null){
                Picasso.get().load(user.image).into(binding.profilePic);
            }
//            else{
//              //  binding.image.setImageResource(android.R.drawable.ic_menu_camera);
//            }
        });



    }
}
