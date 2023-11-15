package com.example.enlight21.Post;

import static androidx.core.content.ContentProviderCompat.requireContext;

import static com.example.enlight21.Utils.Constant.USER_NODE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.animation.LinearInterpolator;

import com.example.enlight21.Adapters.SearchAdapter;
import com.example.enlight21.Models.User;
import com.example.enlight21.R;
import com.example.enlight21.databinding.ActivitySearchBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private ActivitySearchBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    SearchAdapter adapter;
    ArrayList<User> userlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.searchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchAdapter(this, userlist);
        binding.searchRecyclerView.setAdapter(adapter);


        // initial get all data of user

        FirebaseFirestore.getInstance().collection(USER_NODE).get().addOnSuccessListener(queryDocumentSnapshots -> {
            ArrayList<User> tempList = new ArrayList<>();
            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                if (!documentSnapshot.getId().toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())) {
                    User user = documentSnapshot.toObject(User.class);
                    tempList.add(user);
                }

            }

            userlist.clear();
            userlist.addAll(tempList);

            adapter.notifyDataSetChanged();

        }).addOnFailureListener(e -> {
            // Handle failures here
            e.printStackTrace();
        });


        binding.searchbtn.setOnClickListener(view -> {
            String search = binding.searchfeld.getText().toString().trim();

            db.collection(USER_NODE).whereEqualTo("username", search).get().addOnSuccessListener(queryDocumentSnapshots -> {
                ArrayList<User> tempList = new ArrayList<>();
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                    // if input is not empty, get all data of user
                    if (!search.isEmpty()) {
                        User user = documentSnapshot.toObject(User.class);
                        tempList.add(user);
                    }
                }
                userlist.clear();
                userlist.addAll(tempList);

                adapter.notifyDataSetChanged();

            }).addOnFailureListener(e -> {
                        // Handle failures here
                        e.printStackTrace();
                    }
            );
        });




    }
}