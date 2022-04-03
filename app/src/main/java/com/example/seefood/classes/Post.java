package com.example.seefood.classes;

public class Post {
    private String description;
    private String imageURL;
    private float rating;
    private String userID;

    public Post(){};


    public Post(String description, String imageURL, float rating, String userID) {
        this.description = description;
        this.imageURL = imageURL;
        this.rating = rating;
        this.userID = userID;
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

    public String getUserID() {return userID;}
}


