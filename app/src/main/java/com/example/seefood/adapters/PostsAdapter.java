package com.example.seefood.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.seefood.R;
import com.example.seefood.models.Post;
import com.example.seefood.models.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class PostsAdapter extends FirebaseRecyclerAdapter<Post, PostsAdapter.ViewHolder> {

    private Context context;
    private FirebaseRecyclerOptions<Post> posts;

    public PostsAdapter(Context context, FirebaseRecyclerOptions<Post> posts) {
        super(posts);
        this.context = context;
        this.posts = posts;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Post post) {

        String imageURL = post.getImageURL();
        User user = post.getUser();

        if(user != null){
            holder.tvUsername.setText(user.getUsername());
            Glide.with(context).load(user.getProfilePicURL()).into(holder.ivProfilePic);
        }
        
        Glide.with(context).load(imageURL).into(holder.ivImage);
        holder.rbRatingReview.setRating(post.getRating());

    }


    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivProfilePic;
        private TextView tvUsername;
        private ImageView ivImage;
        private RatingBar rbRatingReview;
        private DatabaseReference databaseReference;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            ivProfilePic = itemView.findViewById(R.id.ivProfilePic);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivImage = itemView.findViewById(R.id.ivImage);
            rbRatingReview = itemView.findViewById(R.id.rbRatingReview);
        }
    }
}

