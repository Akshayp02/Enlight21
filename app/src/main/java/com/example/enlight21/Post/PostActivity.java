package com.example.enlight21.Post;

import static com.example.enlight21.Utils.Constant.POST;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.enlight21.Models.Post;
import com.example.enlight21.Utils.Utils;
import com.example.enlight21.databinding.ActivityPostBinding;
import com.example.enlight21.fragments.HomeFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class PostActivity extends AppCompatActivity {
    private ActivityPostBinding binding;
    private String IMAGEURL;


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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


                            binding.imageView1.setImageURI(uri);

                        }

                    }
                }
        );

        binding.imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcher.launch("image/*");
            }
        });

    binding.shearePost.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {


                
                Post post = new Post(IMAGEURL,binding.inputCaption.getText().toString());

                db.collection(POST).document().set(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        db.collection(FirebaseAuth.getInstance().getCurrentUser().getUid()).document().set(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Intent intent = new Intent(PostActivity.this, HomeFragment.class);
                                startActivity(intent);
                                finish();

                            }
                        });

                    }
                });
                

            
                   
        }
    });

    }

    public interface UploadImageCallback {
        void onImageUploaded(String imageUrl);
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

                        if (callback != null) {
                            callback.onImageUploaded(imageUrl);
                        }
                    }
                });
            }
        });
    }

}