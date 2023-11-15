package com.example.enlight21;

import static com.example.enlight21.Utils.Constant.USER_NODE;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.enlight21.Models.User;
import com.example.enlight21.Post.PostActivity;
import com.example.enlight21.Utils.Utils;
import com.example.enlight21.databinding.ActivityEditProfileBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class editProfileActivity extends AppCompatActivity {
    private ActivityEditProfileBinding binding;


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ImageView profileImage;
    EditText username, Discription;
    User currentUser;
    String IMAGEURL;
    public Utils utils = new Utils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        profileImage = binding.profilePic;
        username = binding.username;
        Discription = binding.discription;

        getUserdata();

        final ActivityResultLauncher<String> launcher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        if (uri != null) {
                            UploadImage(uri, "USER PROFILE FOLDER", new Utils.UploadImageCallback() {
                                @Override
                                public void onImageUploaded(String imageUrl) {
                                    IMAGEURL = imageUrl;
                                }
                            });


                            binding.profilePic.setImageURI(uri);

                        }

                    }
                }
        );

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set the user profile image
                launcher.launch("image/*");

            }
        });

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
            currentUser.setEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
            currentUser.setImage(IMAGEURL);
            UpdateToFirestore();
        }

    }


    void UpdateToFirestore() {
        db.collection(USER_NODE).document(user.getUid()).set(currentUser).addOnSuccessListener(aVoid -> {
            Toast.makeText(editProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
            // Instead of finish(), start the profile activity again
            finish();


        }).addOnFailureListener(e -> Toast.makeText(editProfileActivity.this, "Error Updating Profile", Toast.LENGTH_SHORT).show());

    }

    void getUserdata() {
        db.collection(USER_NODE).document(user.getUid()).get().addOnSuccessListener(documentSnapshot -> {

            User users = documentSnapshot.toObject(User.class);
            assert users != null;
            binding.username.setText(users.getName());
            binding.discription.setText(users.getDescription());

        });
    }
    public void UploadImage(Uri imageUri, String FolderName, Utils.UploadImageCallback callback) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child(FolderName).child(UUID.randomUUID().toString());

        UploadTask uploadTask = storageRef.putFile(imageUri);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri downloadUri) {
                        String imageUrl = downloadUri.toString();
                       // Toast.makeText(editProfileActivity.this, "Profile pic Uploded", Toast.LENGTH_SHORT).show();
                        if (callback != null) {
                            callback.onImageUploaded(imageUrl);
                        }
                    }
                });
            }
        });



    }

}



