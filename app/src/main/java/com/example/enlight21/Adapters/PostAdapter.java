package com.example.enlight21.Adapters;

import static com.example.enlight21.Utils.Constant.USER_NODE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.enlight21.Models.Post;
import com.example.enlight21.Models.User;
import com.example.enlight21.R;
import com.example.enlight21.databinding.PostHomeFeedBinding;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    public ArrayList<Post> postlist;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    int coutn = 0;


    public PostAdapter(Context context, ArrayList<Post> postlist) {
        this.postlist = postlist;
    }

    public PostAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PostHomeFeedBinding binding = PostHomeFeedBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );

        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        /// set all deta to the image view in home feed

        try {
            db.collection(USER_NODE).document(postlist.get(position).getUsername()).get().addOnSuccessListener(documentSnapshot -> {

                holder.binding.usernaMe.setText(documentSnapshot.getString("username"));
                holder.binding.technology.setText(postlist.get(position).getTechnology());
                holder.binding.imgeCaption.setText(postlist.get(position).getCaption());


                Glide.with(holder.binding.getRoot().getContext()).load(postlist.get(position).getPostUrl()).placeholder(R.drawable.user);

            });
        } catch (Exception e) {
            e.printStackTrace();
        }


        Glide.with(holder.binding.getRoot().getContext()).load(postlist.get(position).getPostUrl()).placeholder(R.drawable.loading);
       // Glide.with(holder.binding.getRoot().getContext()).load(postlist.get(position).getPostUrl()).placeholder(R.drawable.user).into(holder.binding.postcuretnuser);
        Glide.with(holder.binding.getRoot().getContext()).load(postlist.get(position).getPostUrl()).placeholder(R.drawable.loading).into(holder.binding.postimg);


        // Toast.makeText(holder.binding.getRoot().getContext(), "username "+coutn, Toast.LENGTH_SHORT).show();

        holder.binding.like.setOnClickListener(v -> {
            holder.binding.like.setImageResource(R.drawable.love);
           // coutn++;
        });

    }


    @Override
    public int getItemCount() {

        return postlist.size();
    }

    // ViewHolder class

    public class ViewHolder extends RecyclerView.ViewHolder {

        PostHomeFeedBinding binding;

        public ViewHolder(@NonNull PostHomeFeedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }


}
