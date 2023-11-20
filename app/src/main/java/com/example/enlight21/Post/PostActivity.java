package com.example.enlight21.Post;


import static com.example.enlight21.Utils.Constant.POST;
import static com.example.enlight21.Utils.Constant.USER_NODE;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.enlight21.Models.Post;
import com.example.enlight21.Models.User;
import com.example.enlight21.R;
import com.example.enlight21.Utils.Utils;
import com.example.enlight21.databinding.ActivityPostBinding;
import com.example.enlight21.fragments.HomeFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class PostActivity extends AppCompatActivity {
    private ActivityPostBinding binding;
    private String IMAGEURL;

    private String CurrentUsername;
    private String technology;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


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

        CurrentUsername = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

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

        db.collection(USER_NODE).document(user.getUid()).get().addOnSuccessListener(documentSnapshot -> {

            User user = documentSnapshot.toObject(User.class);
            assert user != null;

            CurrentUsername = user.username.toString();
        });

        binding.imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcher.launch("image/*");
            }
        });


        binding.shearePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (binding.TECNOLOGYip.getText().toString().isEmpty()) {
                    binding.TECNOLOGYip.setError("Please select  technology");
                } else {
                    technology = binding.TECNOLOGYip.getText().toString();
                }


                Post post = new Post(IMAGEURL, binding.inputCaption.getText().toString(), CurrentUsername, technology);

                // check whether given any field is empty or not it it is empty then show error message
                if (post.getCaption().isEmpty()) {
                    binding.inputCaption.setError("Caption is required");
                    return;
                } else {

                    db.collection(POST).document().set(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            db.collection(FirebaseAuth.getInstance().getCurrentUser().getUid()).document().set(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
//                                Intent intent = new Intent(getActivity(), HomeFragment.class);
//                                startActivity(intent);
//                                finish();


                                    FragmentManager fragmentManager = getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.container, new HomeFragment());
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();


                                }
                            });

                        }
                    });
                }


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
                        Toast.makeText(PostActivity.this, "Post Uploded", Toast.LENGTH_SHORT).show();

                        if (callback != null) {
                            callback.onImageUploaded(imageUrl);
                        }
                    }
                });
            }
        });


    }

}