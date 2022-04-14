package com.example.seefood.models;

public class User {

    private String email;
    private String password;
    private String username;
    private String profilePicURL;

    public User() {
    }

    public User(String email, String password, String username, String profilePicURL){
        this.email = email;
        this.password = password;
        this.username = username;
        this.profilePicURL = profilePicURL;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {return username;}

    public String getProfilePicURL() {return profilePicURL;}


}

