package com.example.enlight21;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.enlight21.databinding.ActivitySigninBinding;
import com.example.enlight21.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class signinActivity extends AppCompatActivity {
    private ActivitySigninBinding binding;
    private FirebaseAuth mAuth;



    @Override
    public void onStart() {
        super.onStart();

        // Check if the user is signed in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User is already signed in, navigate to the main activity
            Intent intent = new Intent(signinActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close the current activity to prevent the user from going back
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySigninBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        mAuth = FirebaseAuth.getInstance();
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = binding.Email.getText().toString();
                String Password = binding.Password.getText().toString();

                if (TextUtils.isEmpty(Email) || TextUtils.isEmpty(Password)) {
                    Toast.makeText(signinActivity.this, "Email and Password cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.signInWithEmailAndPassword(Email, Password)
                            .addOnCompleteListener(signinActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "Login Success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(signinActivity.this, "Log in Success", Toast.LENGTH_SHORT).show();

                                       Intent intent = new Intent(signinActivity.this,MainActivity.class);
                                       startActivity(intent);


                                    } else {
                                        Toast.makeText(signinActivity.this, "Login Fail: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });


        binding.Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signinActivity.this, signupActivity.class);
                startActivity(intent);
            }
        });
    }

    // functions

}