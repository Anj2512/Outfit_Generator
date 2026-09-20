package com.example.finalproject;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class MyVPAdapter extends FragmentStateAdapter {

    private ArrayList<ArrayAttributes> outfit;
    private String swapCat = null;
    public MyVPAdapter(FragmentManager fm, Lifecycle lifecycle, ArrayList<ArrayAttributes> outfit) {
        super(fm, lifecycle);
        this.outfit = outfit;

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 0){
            return new OutfitFragment(outfit);
        }else{
            return SwapFragment.newInstance(swapCat);
        }
    }


    @Override
    public int getItemCount() {
        return 2;
    }

    public void setSwapCat(String category) {
        this.swapCat = category;
        notifyDataSetChanged();
    }
}
