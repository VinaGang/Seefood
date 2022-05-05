package com.example.seefood.models;

import com.google.firebase.database.DatabaseReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CartItem {
    String foodImagePath;
    String foodName;
    float price;
    int amount;
    String userId;


    public CartItem(){};

    public CartItem(String userId, String foodImagePath, String foodName, float price, int amount){
        this.userId = userId;
        this.foodImagePath = foodImagePath;
        this.foodName = foodName;
        this.price = price;
        this.amount = amount;
    }

    public String getFoodImagePath(){
        return foodImagePath;
    }

    public String getFoodName(){
        return foodName;
    }

    public float getPrice(){
        return price;
    }

    public int getAmount(){
        return amount;
    }

    public String getUserId(){return userId;}
}