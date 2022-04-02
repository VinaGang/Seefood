package com.example.seefood.classes;

public class Post {
    private String description;
    private String imageURL;
    private float rating;
    private String profileURL;

    public Post(){};

    public Post(String description, String imageURL, float rating) {
        this.description = description;
        this.imageURL = imageURL;
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public float getRating() {
        return rating;
    }
}


