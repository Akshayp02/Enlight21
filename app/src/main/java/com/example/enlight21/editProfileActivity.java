package com.example.enlight21;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.enlight21.Models.User;
import com.example.enlight21.Utils.Utils;
import com.example.enlight21.databinding.ActivityEditProfileBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

public class editProfileActivity extends AppCompatActivity {
    private ActivityEditProfileBinding binding;


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ImageView profileImage;
    EditText username, Discription;
    User currentUser;
    public  Utils utils = new Utils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        profileImage = binding.profilePic;
        username = binding.username;
        Discription = binding.discription;

        getUserdata();



        binding.btnUpdate.setOnClickListener(view -> {
            // button click
            updateButtonClick();
        });


    }
    // functions

    void updateButtonClick() {
        String newname = username.getText().toString();
        String description = Discription.getText().toString();

        if (newname.isEmpty() || newname.length() < 3) {
            username.setError("Please enter a valid name");
            username.requestFocus();
        } else {
            // Check if currentUser is null and instantiate it if necessary
            if (currentUser == null) {
                currentUser = new User();
            }

            currentUser.setName(newname);
            currentUser.setDescription(description);
            UpdateToFirestore();
        }

    }


    void UpdateToFirestore() {
        db.collection("users").document(user.getUid()).set(currentUser).addOnSuccessListener(aVoid -> {
            Toast.makeText(editProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
            // Instead of finish(), start the profile activity again
            finish();


        }).addOnFailureListener(e -> Toast.makeText(editProfileActivity.this, "Error Updating Profile", Toast.LENGTH_SHORT).show());

    }

    void getUserdata() {
        db.collection("users").document(user.getUid()).get().addOnSuccessListener(documentSnapshot -> {

            User users = documentSnapshot.toObject(User.class);
            assert users != null;
            binding.username.setText(users.getName());
            binding.discription.setText(users.getDescription());

        });
    }





}



