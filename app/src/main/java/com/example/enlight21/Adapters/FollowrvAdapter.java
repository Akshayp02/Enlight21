package com.example.enlight21.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.enlight21.Models.User;
import com.example.enlight21.R;
import com.example.enlight21.databinding.FollowRvBinding;

import java.util.ArrayList;

public class FollowrvAdapter extends RecyclerView.Adapter<FollowrvAdapter.ViewHolder> {

    ArrayList<User> followlist = new ArrayList<>();

    FollowRvBinding binding;

    public FollowrvAdapter(Context context, ArrayList<User> followlist) {
        this.followlist = followlist;

    }

    @NonNull
    @Override
    public FollowrvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = FollowRvBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);


        return new ViewHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull FollowrvAdapter.ViewHolder holder, int position) {
        if (followlist != null && position < followlist.size()-1) {
            // Your existing code here
            Glide.with(holder.itemView.getContext()).load(followlist.get(position).image)
                    .placeholder(R.drawable.user).into(holder.binding.storyicon);
            holder.binding.followername.setText(followlist.get(position).username);
        }
    }


    @Override
    public int getItemCount() {
        return followlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        FollowRvBinding binding;

        public ViewHolder(@NonNull FollowRvBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
