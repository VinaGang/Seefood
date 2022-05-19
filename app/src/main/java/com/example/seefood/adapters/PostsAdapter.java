package com.example.seefood.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.seefood.R;
import com.example.seefood.fragments.SinglePostFragment;
import com.example.seefood.models.Post;
import com.example.seefood.models.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;

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
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Post posts) {

        String imageURL = posts.getImageURL();
        User user = posts.getUser();

        if(user != null){
            holder.tvUsername.setText(user.getUsername());
            Glide.with(context).load(user.getProfilePicURL()).into(holder.ivProfilePic);
        }
        
        Glide.with(context).load(imageURL).into(holder.ivImage);
        holder.rbRatingReview.setRating(posts.getRating());
        holder.cvuserPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.flContainer, new SinglePostFragment(posts))
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null).addSharedElement(holder.ivImage, "toSingleView").commit();
            }
        });
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivProfilePic;
        private TextView tvUsername;
        private ImageView ivImage;
        private RatingBar rbRatingReview;
        private DatabaseReference databaseReference;
        private CardView cvuserPost;


        public ViewHolder(@NonNull View itemView){
            super(itemView);
            ivProfilePic = itemView.findViewById(R.id.ivProfilePic);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivImage = itemView.findViewById(R.id.ivimagePost);
            rbRatingReview = itemView.findViewById(R.id.rbRatingReview);
            cvuserPost = itemView.findViewById(R.id.cvcartItem);
        }
    }
}

