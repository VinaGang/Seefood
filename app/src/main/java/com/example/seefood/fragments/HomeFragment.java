package com.example.seefood.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.seefood.R;
import com.example.seefood.adapters.PostsAdapter;
import com.example.seefood.models.Post;
import com.example.seefood.models.User;
import com.example.seefood.statics.ComposeActivity;
import com.example.seefood.statics.LoginActivity;
import com.example.seefood.statics.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {


    private FloatingActionButton fbtnCompose;
    protected PostsAdapter postsAdapter;
    private RecyclerView rvPosts;
    protected SwipeRefreshLayout swipeContainer;
    DatabaseReference postRef;
    FirebaseRecyclerOptions<Post> options;
    private TextView tvgreetHeading;
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    String userID = currentUser.getUid();
    Toolbar toolbar;
    //FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

    public HomeFragment(){
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.top_app_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){

            case R.id.item_logOut:
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
                break;

            case R.id.item_user:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.slide_out)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).replace(R.id.flContainer, new UserFragment())
                        .addToBackStack(null).addSharedElement(toolbar, "toolbar").commit();
                break;
        }
        return true;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onViewCreated(view, savedInstanceState);
        fbtnCompose = view.findViewById(R.id.fbtnCompose);
        rvPosts = view.findViewById(R.id.rvPosts);
        tvgreetHeading = view.findViewById(R.id.tvgreetHeading);
        toolbar = view.findViewById(R.id.tbTopBar);


        Toolbar toolbar = view.findViewById(R.id.tbTopBar);
        ((AppCompatActivity) getActivity() ).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity() ).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity() ).getSupportActionBar().setLogo(R.drawable.ic_fork_and_knife_inside_a_circular_border_svgrepo_com);
        ((AppCompatActivity) getActivity() ).getSupportActionBar().setDisplayUseLogoEnabled(true);
        ((AppCompatActivity) getActivity() ).getSupportActionBar().setDisplayShowTitleEnabled(false);

        postRef = FirebaseDatabase.getInstance().getReference("user").child(userID);
        postRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                tvgreetHeading.setText("Welcome, " + user.getUsername() + "!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        swipeContainer = view.findViewById(R.id.swipeContainer);

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        postRef = FirebaseDatabase.getInstance().getReference("post");

        options = new FirebaseRecyclerOptions.Builder<Post>()
                .setQuery(postRef, Post.class)
                .build();

        postsAdapter = new PostsAdapter(getContext(), options);

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

        fbtnCompose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ComposeActivity.class));
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        postsAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        postsAdapter.stopListening();
    }

    private void queryPosts() {

        FirebaseRecyclerOptions<Post> options = new FirebaseRecyclerOptions.Builder<Post>()
                .setQuery(postRef, Post.class)
                .build();

        postsAdapter = new PostsAdapter(getContext(), options);

        postsAdapter.notifyDataSetChanged();

        swipeContainer.setRefreshing(false);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}