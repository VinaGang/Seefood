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
    String price;
    String amount;


    public CartItem(){};

    public CartItem(String foodImagePath, String foodName, String price, String amount){
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

    public String getPrice(){
        return price;
    }

    public String getAmount(){
        return amount;
    }
}