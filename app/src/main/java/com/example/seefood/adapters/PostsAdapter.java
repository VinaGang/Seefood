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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder>{
    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts) {
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
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

        public void bind(Post post){
            String imageURL = post.getImageURL();
            String userID = post.getUserID();
            databaseReference = FirebaseDatabase.getInstance().getReference("user").child(userID);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);
                    String username = user.getUsername();
                    tvUsername.setText(username);
                    Glide.with(context).load(user.getProfilePicURL()).into(ivProfilePic);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            Glide.with(context).load(imageURL).into(ivImage);
            rbRatingReview.setRating(post.getRating());
        }
    }

    public void clear(){
        posts.clear();
        Log.d("POST ADAPTER: Clear",  posts.toString());
        notifyDataSetChanged();
    }

    public void addAll(List<Post> post_list){
        posts.addAll(post_list);
        Log.d("POST ADAPTER: ",  post_list.toString());
        notifyDataSetChanged();
    }
}

