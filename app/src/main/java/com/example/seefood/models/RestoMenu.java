package com.example.seefood.models;

import java.util.List;

public class RestoMenu {

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
