package com.example.enlight21.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.enlight21.Adapters.MyPostRvAdapter;
import com.example.enlight21.Models.Post;
import com.example.enlight21.R;
import com.example.enlight21.databinding.FragmentMyPostBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class MyPostFragment extends Fragment {

   private FragmentMyPostBinding binding;
    ArrayList<Post> postlist = new ArrayList<>();

    public MyPostFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       binding = FragmentMyPostBinding.inflate(inflater, container, false);


        MyPostRvAdapter adapter = new MyPostRvAdapter(requireContext(),postlist);
        binding.mypostRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        binding.mypostRecyclerview.setAdapter(adapter);

        FirebaseFirestore.getInstance()
                .collection(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<Post> tempList = new ArrayList<>();

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Post post = documentSnapshot.toObject(Post.class);
                            tempList.add(post);
                        }

                        postlist.addAll(tempList);
                        adapter.notifyDataSetChanged();
                    }
                });


        return binding.getRoot();
    }
}