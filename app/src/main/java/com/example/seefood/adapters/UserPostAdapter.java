package com.example.seefood.adapters;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.seefood.R;
import com.example.seefood.models.Post;
import com.example.seefood.models.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserPostAdapter extends FirebaseRecyclerAdapter<Post, UserPostAdapter.ViewHolder>{
    private Context context;
    private FirebaseRecyclerOptions<Post> posts;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("post");
    private User user;

    public UserPostAdapter(Context context, FirebaseRecyclerOptions<Post> posts) {
        super(posts);
        this.context = context;
        this.posts = posts;
    }

    public UserPostAdapter(Context context, FirebaseRecyclerOptions<Post> posts, User user) {
        super(posts);
        this.context = context;
        this.posts = posts;
        this.user = user;
    }

    @NonNull
    @Override
    public UserPostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_post_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull UserPostAdapter.ViewHolder holder, int position, @NonNull Post post) {

        String imageURL = post.getImageURL();
        Glide.with(context).load(imageURL).into(holder.ivimagePost);

    }
    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivimagePost;
        private RelativeLayout rluserItem;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            ivimagePost = itemView.findViewById(R.id.ivimagePost);
            rluserItem = itemView.findViewById(R.id.rluserItem);
        }
    }
}

