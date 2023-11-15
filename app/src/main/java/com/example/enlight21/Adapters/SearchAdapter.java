package com.example.enlight21.Adapters;

import static com.example.enlight21.Utils.Constant.FOLLOW;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.enlight21.Models.Post;
import com.example.enlight21.Models.User;
import com.example.enlight21.databinding.SearchrvBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private ArrayList<User> userlist = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private boolean isFollowing = false;

    public SearchAdapter(Context context, ArrayList<User> users) {
        this.userlist = users;
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        SearchrvBinding binding = SearchrvBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Glide.with(holder.itemView.getContext()).load(userlist.get(position).image).placeholder(com.example.enlight21.R.drawable.user).into(holder.binding.postcuretnuser);
        // check it for user name and etc stuff
        holder.binding.usrnamess.setText(userlist.get(position).username);

        // for follow see in the section of search if you are following or not if you are following then it will show unfollow
        db.collection(FirebaseAuth.getInstance().getCurrentUser().getUid() + FOLLOW)
                .whereEqualTo("email", userlist.get(position).email).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (queryDocumentSnapshots.isEmpty()) {
                            isFollowing = false;
                        } else {
                            holder.binding.followBtn.setText("Unfollow");
                            isFollowing = true;
                        }
                    }
                });

        holder.binding.followBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFollowing) {
                    db.collection(FirebaseAuth.getInstance().getCurrentUser().getUid() + FOLLOW)
                            .whereEqualTo("email", userlist.get(position).email).get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                    db.collection(FirebaseAuth.getInstance().getCurrentUser().getUid() + FOLLOW)
                                            .document(queryDocumentSnapshots.getDocuments().get(0).getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            holder.binding.followBtn.setText("Follow");
                                            isFollowing = false;
                                        }
                                    });

                                }
                            });

                } else {
                    db.collection(FirebaseAuth.getInstance().getCurrentUser().getUid() + FOLLOW)
                            .document().set(userlist.get(position)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    holder.binding.followBtn.setText("Unfollow");
                                    isFollowing = true;

                                }
                            });


                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SearchrvBinding binding;

        public ViewHolder(@NonNull SearchrvBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
