package com.example.seefood.classes;

import com.google.firebase.database.DatabaseReference;

public class CartItem {
    String foodImagePath;
    String foodName;
    String price;
    String amount;

    public CartItem(DatabaseReference databaseObject){
        foodImagePath = databaseObject.getS
    }
}
