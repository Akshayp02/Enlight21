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
import com.example.enlight21.databinding.ActivityPostBinding;
import com.example.enlight21.fragments.HomeFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

        binding.toolbar.setNavigationOnClickListener(view -> finish());

        CurrentUsername = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        // Initially disable the post button
        binding.shearePost.setEnabled(false);

        final ActivityResultLauncher<String> launcher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        if (uri != null) {
                            binding.progressBar.setVisibility(View.VISIBLE); // Show progress bar

                            UploadImage(uri, "USER_PROFILE_FOLDER", new UploadImageCallback() {
                                @Override
                                public void onImageUploaded(String imageUrl) {
                                    IMAGEURL = imageUrl;
                                    binding.progressBar.setVisibility(View.GONE); // Hide progress bar
                                    binding.shearePost.setEnabled(true); // Enable post button
                                }
                            });
                            binding.imageView1.setImageURI(uri);
                        }
                    }
                }
        );

        db.collection(USER_NODE).document(user.getUid()).get().addOnSuccessListener(documentSnapshot -> {
            User user = documentSnapshot.toObject(User.class);
            if (user != null) {
                CurrentUsername = user.username;
            }
        });

        binding.imageView1.setOnClickListener(view -> launcher.launch("image/*"));

        binding.shearePost.setOnClickListener(view -> {
            if (binding.TECNOLOGYip.getText().toString().isEmpty()) {
                binding.TECNOLOGYip.setError("Please select technology");
                return;
            } else {
                technology = binding.TECNOLOGYip.getText().toString();
            }

            if (IMAGEURL == null) {
                Toast.makeText(PostActivity.this, "Image not uploaded yet", Toast.LENGTH_SHORT).show();
                return;
            }

            Post post = new Post(IMAGEURL, binding.inputCaption.getText().toString(), CurrentUsername, technology);

            if (post.getCaption().isEmpty()) {
                binding.inputCaption.setError("Caption is required");
                return;
            }

            db.collection(POST).document().set(post).addOnSuccessListener(unused ->
                    db.collection(user.getUid()).document().set(post).addOnSuccessListener(unused1 -> {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, new HomeFragment());
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    })
            );
        });
    }

    public interface UploadImageCallback {
        void onImageUploaded(String imageUrl);
    }

    public void UploadImage(Uri imageUri, String folderName, UploadImageCallback callback) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child(folderName).child(UUID.randomUUID().toString());

        UploadTask uploadTask = storageRef.putFile(imageUri);
        uploadTask.addOnSuccessListener(taskSnapshot ->
                storageRef.getDownloadUrl().addOnSuccessListener(downloadUri -> {
                    String imageUrl = downloadUri.toString();
                    Toast.makeText(PostActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                    if (callback != null) {
                        callback.onImageUploaded(imageUrl);
                    }
                })
        );
    }
}
