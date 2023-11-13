package com.example.enlight21.Utils;

import android.net.Uri;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.util.UUID;

public class Utils {


    public interface UploadImageCallback {
        void onImageUploaded(String imageUrl);
    }

    public void UploadImage(Uri imageUri, String FolderName, UploadImageCallback callback) {
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
                        callback.onImageUploaded(imageUrl);
                    }
                });
            }
        });
    }


}