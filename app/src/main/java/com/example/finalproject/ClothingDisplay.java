package com.example.finalproject;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.Objects;

public class ClothingDisplay extends AppCompatActivity {

    ImageView shirtI, bottomI, bagI, accessI, shoeI, jacketI;
    ViewPager2 vp2;
    ArrayList<ArrayAttributes> outfit;
    MyVPAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.containerlayout);

        outfit = (ArrayList<ArrayAttributes>) getIntent().getSerializableExtra("ids");
        vp2 = findViewById(R.id.viewPager2);
        adapter = new MyVPAdapter(getSupportFragmentManager(), getLifecycle(), outfit);
        vp2.setAdapter(adapter);

    }

    public void goToSwap(String category) {
        vp2.setAdapter(null);

        MyVPAdapter freshAdapter = new MyVPAdapter(getSupportFragmentManager(), getLifecycle(), outfit);
        freshAdapter.setSwapCat(category);
        vp2.setAdapter(freshAdapter);
        vp2.setCurrentItem(1);
    }

    public void replaceFit(String category, int newId) {
        String cat = category;
        int drawId = newId;

        OutfitFragment fragment = (OutfitFragment) getSupportFragmentManager().findFragmentByTag("f0");
        if(fragment != null){
            fragment.updateItem(cat, drawId);
        }
    }

    public ViewPager2 getViewPager() {

        return vp2;
    }
}

