package com.example.seefood.models;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class RestoMenu implements Serializable {

    public String name;
    public List<Food> items;
    public String date;

    public RestoMenu() {
    }


    public RestoMenu(String name, List<Food> items, String date){
        this.name = name;
        this.items = items;
        this.date = date;
    }
}
