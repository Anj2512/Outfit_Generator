package com.example.finalproject;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class SwapFragment extends Fragment {

        private static final String THE_CATEGORY = "category";

        public static SwapFragment newInstance(String category){
            SwapFragment fragment = new SwapFragment();
            Bundle args = new Bundle();
            args.putString(THE_CATEGORY, category);
            fragment.setArguments(args);

            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_swap, container, false);
            ListView itemList = view.findViewById(R.id.listView);

            String category = getArguments().getString(THE_CATEGORY);
            ArrayList<Integer> drawables = getClothingForCat(category);

            CustomAdapter adapter = new CustomAdapter(getContext(), drawables, category);
            itemList.setAdapter(adapter);

            itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int chosenItem = drawables.get(position);
                }
            });
            return view;
        }

        public ArrayList<Integer> getClothingForCat(String cat){
            ArrayList<Integer> ints = new ArrayList<>();
            Log.d("category in swap fragment: ", cat);
            if (cat.equals("shirts")) {
                ints.add(R.drawable.frillyshirt);
                ints.add(R.drawable.blueshirt);
                ints.add(R.drawable.sleeveshirt);
                ints.add(R.drawable.grayshirt);
                ints.add(R.drawable.cuteshirt);
                ints.add(R.drawable.blackshirt);
                ints.add(R.drawable.tshirt);

            } else if (cat.equals("bottoms")) {
                ints.add(R.drawable.brownpant);
                ints.add(R.drawable.sweatpant);
                ints.add(R.drawable.jeanpant);
                ints.add(R.drawable.greenpant);
            }
            else if(cat.equals("shoes")){
                ints.add(R.drawable.whiteshoes);
                ints.add(R.drawable.greenshoes);
                ints.add(R.drawable.redshoes);
                ints.add(R.drawable.brownshoes);
                ints.add(R.drawable.blackshoes);
            }
            else if(cat.equals("bags")){
                ints.add(R.drawable.bluebag);
                ints.add(R.drawable.brownbag);
                ints.add(R.drawable.pinkbag);
            }
            else if(cat.equals("access")){
                ints.add(R.drawable.necklacetwo);
                ints.add(R.drawable.necklaceone);
                ints.add(R.drawable.sunglasstwo);
                ints.add(R.drawable.sunglassone);
            }
            else if(cat.equals("jackets")){
                ints.add(R.drawable.yellowjacket);
                ints.add(R.drawable.jeanjacket);
                ints.add(R.drawable.greayjacket);
                ints.add(R.drawable.brownjacket);
            }
            else{
                Log.d("CAT IN SWAP FRAGS", cat);
            }
            return ints;
        }

        private static class CustomAdapter extends ArrayAdapter<Integer> {
            Context context;
            ArrayList<Integer> images;
            String category;
            public CustomAdapter(@NonNull Context context, ArrayList<Integer> draws, String category) {
                super(context, R.layout.adapter, draws);
                this.context = context;
                images = draws;
                this.category = category;
            }

            @NonNull
            public View getView(int position, View convertView, ViewGroup parent){
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.adapter, parent, false);
                }

                ImageView image = convertView.findViewById(R.id.imageSwap);
                image.setImageResource(images.get(position));

                Button bSelect = convertView.findViewById(R.id.bSelect);
                bSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int newId = images.get(position);

                        ClothingDisplay activity = (ClothingDisplay) context;
                        activity.replaceFit(category, newId);
                        activity.getViewPager().setCurrentItem(0);
                    }
                });

                return convertView;
            }
        }
    }
