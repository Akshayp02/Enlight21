package com.example.enlight21.Adapters;

import static com.example.enlight21.Utils.Constant.POST;
import static com.example.enlight21.Utils.Constant.USER_NODE;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.example.enlight21.Models.Post;
import com.example.enlight21.Models.User;
import com.example.enlight21.R;
import com.example.enlight21.databinding.PostHomeFeedBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    public ArrayList<Post> postlist;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    int coutn = 0;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore dbs = FirebaseFirestore.getInstance();


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

        dbs.collection(USER_NODE).document(user.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                User user1 = documentSnapshot.toObject(User.class);

                if(user1.image != null){
                    // to load user image to the user profile in the post
                    Picasso.get().load(user1.image).into(holder.binding.postcuretnuser);
                }
            }
        });
        /// set all deta to the image view in home feed

        try { // USER_NODE
            db.collection(POST).document(postlist.get(position).getUsername()).get().addOnSuccessListener(documentSnapshot -> {
                // holder.binding.usernaMe.setText(documentSnapshot.getString("username"));
                holder.binding.usernaMe.setText(postlist.get(position).getUsername());
                holder.binding.technology.setText(postlist.get(position).getTechnology());

                if(postlist.get(position).getCaption().isEmpty()){
                    holder.binding.imgeCaption.setVisibility(View.INVISIBLE);
                }else{
                    holder.binding.imgeCaption.setText(postlist.get(position).getCaption());
                }

                Glide.with(holder.binding.getRoot().getContext()).load(postlist.get(position).getPostUrl()).placeholder(R.drawable.user);

            });
        } catch (Exception e) {
            e.printStackTrace();
        }


       try {

           holder.binding.lodingpostProgressbar.setVisibility(View.VISIBLE);
           if(!postlist.get(position).getPostUrl().isEmpty()){
               holder.binding.lodingpostProgressbar.setVisibility(View.INVISIBLE);
               Glide.with(holder.binding.getRoot().getContext()).load(postlist.get(position).getPostUrl()).into(holder.binding.postimg);
               holder.binding.postimg.setVisibility(View.VISIBLE);
           }else {
               holder.binding.lodingpostProgressbar.setVisibility(View.INVISIBLE);
               holder.binding.postimg.setVisibility(View.INVISIBLE);
           }


       }catch (Exception e){
           e.printStackTrace();
       }



        holder.binding.like.setOnClickListener(v -> {
            holder.binding.like.setImageResource(R.drawable.after_like_px);

           // TODO : add functinality  for Like

        });

       holder.binding.save.setOnClickListener( v -> {
           holder.binding.save.setImageResource(R.drawable.bookmark_iconaftersave);
           // TODO : add functinality  for save
       });


       holder.binding.comments.setOnClickListener(v ->{
           // TODO : add functinality  for comments
       });

       holder.binding.share.setOnClickListener(v->{
           // TODO : add functinality  for share in chat and explicitly as well
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
