package com.example.seefood.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.UserHandle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.seefood.R;
import com.example.seefood.adapters.UserPostAdapter;
import com.example.seefood.models.Post;
import com.example.seefood.models.User;
import com.example.seefood.statics.LoginActivity;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.common.base.FinalizableWeakReference;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.Query;


import java.util.List;

public class UserFragment extends Fragment {

    ImageView ivuserPic;
    ImageView ivPostIcon;
    TextView tvuserName;
    TextView tvnumPost;
    Toolbar tbTopBar;
    User curUser;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user");
    DatabaseReference postRef = FirebaseDatabase.getInstance().getReference("post");
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    String username;
    Post queryPosts;
    int numPost = 0;
    UserPostAdapter userPostAdapter;
    FirebaseRecyclerOptions<Post> options;
    private RecyclerView rvuserPost;
    Query query;

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    //Inflate toolbar elements
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.user_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){

            case R.id.item_edit:
                return true;
        }
        return true;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onViewCreated(view, savedInstanceState);
        tvuserName = view.findViewById(R.id.tvuserName);
        tvnumPost = view.findViewById(R.id.tvnumPost);
        ivuserPic = view.findViewById(R.id.ivuserPic);
        ivPostIcon = view.findViewById(R.id.ivPostIcon);
        tbTopBar = view.findViewById(R.id.tbTopBar);
        rvuserPost = view.findViewById(R.id.rvuserPost);

        reference.child(currentFirebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                username = snapshot.child("username").getValue(String.class);
                String profileURL = snapshot.child("profilePicURL").getValue(String.class);
                tvuserName.setText(username);
                Glide.with(getContext()).load(profileURL).into(ivuserPic);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        postRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    queryPosts = dataSnapshot.getValue(Post.class);
                    User localUser = queryPosts.getUser();
                    if (localUser.getUsername().equals(username)) {
                        numPost += 1;
                        curUser = localUser;
                    }
                }
                //Change number of post on user profile
                tvnumPost.setText(Integer.toString(numPost) + " " + "reviews");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        query = FirebaseDatabase.getInstance().getReference("post").orderByChild("userID").equalTo(currentFirebaseUser.getUid());
        options = new FirebaseRecyclerOptions.Builder<Post>()
                .setQuery(query, Post.class)
                .build();

        userPostAdapter = new UserPostAdapter(getContext(), options, curUser);
        rvuserPost.setAdapter(userPostAdapter);
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        rvuserPost.setLayoutManager(gridLayoutManager);

        tbTopBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        userPostAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        userPostAdapter.stopListening();
    }
}
