package com.example.seefood.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.seefood.R;
import com.example.seefood.adapters.PostsAdapter;
import com.example.seefood.models.Post;
import com.example.seefood.statics.ComposeActivity;
import com.example.seefood.statics.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private ImageView ivlogOut;
    private FloatingActionButton fbtnCompose;
    protected PostsAdapter postsAdapter;
    private RecyclerView rvPosts;
    protected List<Post> allPosts;
    protected SwipeRefreshLayout swipeContainer;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ivlogOut = view.findViewById(R.id.ivlogOut);
        fbtnCompose = view.findViewById(R.id.fbtnCompose);
        rvPosts = view.findViewById(R.id.rvPosts);

        allPosts = new ArrayList<>();
        postsAdapter = new PostsAdapter(getContext(), allPosts);

        swipeContainer = view.findViewById(R.id.swipeContainer);

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        rvPosts.setAdapter(postsAdapter);

        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        gridLayoutManager.scrollToPosition(0);
        rvPosts.setLayoutManager(gridLayoutManager);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryPosts();
            }
        });
        Log.d("home: ", "call query");

        queryPosts();

        fbtnCompose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ComposeActivity.class));
            }
        });

        ivlogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
            }
        });
    }

    private void queryPosts() {
        ProgressDialog progressDialog = ProgressDialog.show(getContext(), "Loading", "Loading posts", true);
        Log.d("home: ", "queryPost");
        DatabaseReference postRef = FirebaseDatabase.getInstance().getReference("post");
        postRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Post post = dataSnapshot.getValue(Post.class);
                    Log.d("home: item ", post.toString());
                    allPosts.add(post);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        postsAdapter.clear();
        postsAdapter.addAll(allPosts);
        progressDialog.dismiss();

        Log.d("home: ", allPosts.toString());
        swipeContainer.setRefreshing(false);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}