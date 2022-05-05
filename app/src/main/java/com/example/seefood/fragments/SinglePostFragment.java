package com.example.seefood.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.seefood.R;
import com.example.seefood.models.Post;

public class SinglePostFragment extends Fragment {

    Post post;

    public SinglePostFragment() {
        // Required empty public constructor
    }

    public SinglePostFragment(Post post){
        this.post = post;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_single_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       Toolbar tbTopBar;
       ImageView ivpostImage;
       ImageView ivAvatar;
       TextView tvuser;
       TextView tvDetails;
       TextView tvRating;

       tbTopBar = view.findViewById(R.id.tbTopBar);
       ivpostImage = view.findViewById(R.id.ivpostImage);
       ivAvatar = view.findViewById(R.id.ivAvatar);
       tvuser = view.findViewById(R.id.tvuser);
       tvRating = view.findViewById(R.id.tvRating);
       tvDetails = view.findViewById(R.id.tvDetails);

       tbTopBar.setNavigationOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               getActivity().getSupportFragmentManager().popBackStack();
           }
       });

       Glide.with(getContext()).load(post.getImageURL()).transform(new RoundedCorners(50)).into(ivpostImage);
       Glide.with(getContext()).load(post.getUser().getProfilePicURL()).into(ivAvatar);
       tvuser.setText(post.getUser().getUsername());
       tvRating.setText("Rating: " + Float.toString(post.getRating()) + "/5.0");
       tvDetails.setText(post.getDescription());
    }
}