package com.example.enlight21.fragments;

import static com.example.enlight21.Utils.Constant.FOLLOW;
import static com.example.enlight21.Utils.Constant.POST;
import static com.example.enlight21.databinding.FragmentHomeBinding.inflate;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.enlight21.Adapters.FollowrvAdapter;
import com.example.enlight21.Adapters.PostAdapter;
import com.example.enlight21.Models.Post;
import com.example.enlight21.Models.User;

import com.example.enlight21.databinding.FragmentHomeBinding;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

   private FragmentHomeBinding binding;
   private ArrayList<Post> postlist = new ArrayList<>();
   private ArrayList<User> followlist = new ArrayList<>();
   private PostAdapter adapter ;
   private FollowrvAdapter folloeadapter;

   FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = inflate(inflater, container, false);
        adapter = new PostAdapter(requireContext(),postlist);
        binding.postRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.postRecycler.setAdapter(adapter);

        folloeadapter = new FollowrvAdapter(requireContext(),followlist);
        binding.followRecycler.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false));
       // binding.followRecycler.setPadding(0, 0, 0, 0);

        binding.followRecycler.setAdapter(folloeadapter);
        // code here

        db.collection(FirebaseAuth.getInstance().getCurrentUser().getUid()+FOLLOW).get().addOnSuccessListener(queryDocumentSnapshots -> {
            followlist.clear();
            for (int i = 0; i < queryDocumentSnapshots.size(); i++) {
                User user = queryDocumentSnapshots.getDocuments().get(i).toObject(User.class);
                followlist.add(user);
            }
            folloeadapter.notifyDataSetChanged();
        }
        );

        db.collection(POST).get().addOnSuccessListener(queryDocumentSnapshots -> {
            postlist.clear();
            for (int i = 0; i < queryDocumentSnapshots.size(); i++) {
                Post post = queryDocumentSnapshots.getDocuments().get(i).toObject(Post.class);
                postlist.add(post);
            }
            adapter.notifyDataSetChanged();
        });




        return binding.getRoot();
    }
}