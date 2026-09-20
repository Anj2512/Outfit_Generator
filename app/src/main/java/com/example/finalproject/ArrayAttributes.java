package com.example.finalproject;

import java.io.Serializable;

public class ArrayAttributes implements Serializable {
    int id;
    String category;

    public ArrayAttributes(int id, String category){
        this.id = id;
        this.category = category;
    }

    public int getId(){
        return id;
    }

    public String getCategory(){
        return category;
    }
}
