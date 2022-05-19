package com.example.seefood.models;

import java.io.Serializable;

public class MenuItem {
    private String menuImagePath;
    private String menuName;
    private float menuPrice;

    public MenuItem(){}

    public MenuItem(String menuImagePath, String menuName, float menuPrice){
        this.menuImagePath = menuImagePath;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
    }

    public String getMenuImagePath(){
        return menuImagePath;
    }

    public String getMenuName(){
        return menuName;
    }

    public float getMenuPrice(){
        return menuPrice;
    }


}
