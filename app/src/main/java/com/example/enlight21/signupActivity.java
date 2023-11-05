package com.example.enlight21;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.enlight21.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

// ... (other import statements)

public class signupActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();

        // Check if the user is signed in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User is already signed in, navigate to the main activity
            Intent intent = new Intent(signupActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close the current activity to prevent the user from going back
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        binding.signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.Email.getText().toString();
                String password = validatePassword(binding.Password.getText().toString(), binding.ConfirmPassword.getText().toString());

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(binding.ConfirmPassword.getText()) || TextUtils.isEmpty(binding.Username.getText())) {
                    Toast.makeText(signupActivity.this, "Please fill the Information", Toast.LENGTH_SHORT).show();
                } else {
                    if (password != null) {
                        Task<AuthResult> authResultTask = mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(signupActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "Create User with email: Success");
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            Toast.makeText(signupActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(signupActivity.this, signinActivity.class);
                                            startActivity(intent);
                                            // Registration is successful, you can perform any further actions here
                                        } else {
                                            Log.w(TAG, "Create user failed", task.getException());
                                            Toast.makeText(signupActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                    }
                }


            }
        });

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signupActivity.this, signinActivity.class);
                startActivity(intent);
            }
        });
    }


    public String validatePassword(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            // Display an error message to the user
            Toast.makeText(signupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return null;
        }

        return password;
    }
}
