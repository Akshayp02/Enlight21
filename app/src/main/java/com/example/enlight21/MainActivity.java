package com.example.enlight21;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.enlight21.databinding.ActivityMainBinding;
import com.example.enlight21.fragments.AddFragment;
import com.example.enlight21.fragments.BookFragment;
import com.example.enlight21.fragments.HomeFragment;
import com.example.enlight21.fragments.ProfileFragment;
import com.example.enlight21.fragments.chat_Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private static final String ROOT_FRAGMENT_TAG = "root_fragment";
    private ActivityMainBinding binding;
    BottomNavigationView bnview;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // load fragment
        binding.bnView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;
                if (item.getItemId() == R.id.homeFragment) {
                    fragment = new HomeFragment();
                } else if (item.getItemId() == R.id.book) {
                    fragment = new BookFragment();
                } else if (item.getItemId() == R.id.add) {
                    fragment = new AddFragment();


                } else if (item.getItemId() == R.id.chat) {
                    fragment = new chat_Fragment();
                } else if (item.getItemId() == R.id.profile) {
                    fragment = new ProfileFragment();
                }


                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                return true;
            }
        });

        // Set the selected item ID
        binding.bnView.setSelectedItemId(R.id.homeFragment);


    }


}