package com.example.enlight21.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.enlight21.Models.Post;
import com.example.enlight21.databinding.MypostrvdesignBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyPostRvAdapter extends RecyclerView.Adapter<MyPostRvAdapter.ViewHolder> {

    private ArrayList<Post> PostList;


    // Constructor to initialize the ArrayList
    public MyPostRvAdapter(Context context, ArrayList<Post> postList) {
        this.PostList = postList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MypostrvdesignBinding binding =MypostrvdesignBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );

        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Picasso.get().load(PostList.get(position).getPostUrl()).into(holder.binding.reImgView);

    }

    @Override
    public int getItemCount() {
        return PostList.size();
    }

    // ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder {
        MypostrvdesignBinding binding;

        public ViewHolder(MypostrvdesignBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }



}
