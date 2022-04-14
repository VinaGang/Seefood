package com.example.seefood.models;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Post {
    private String description;
    private String imageURL;
    private float rating;
    private User user;

    public Post(){};


    public Post(String description, String imageURL, float rating, User user) {
        this.description = description;
        this.imageURL = imageURL;
        this.rating = rating;
        this.user = user;
    }

    public String getImageURL() {
        return imageURL;
    }

    public float getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }

    public User getUser() {return user;}
}


