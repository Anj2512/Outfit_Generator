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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;


public class OutfitFragment extends Fragment {

    private ArrayList<ArrayAttributes> outfit;
    ImageView shoeI, jacketI, shirtI, botI, bagI, accessI, star, fullstar;
    Switch favSwitch;
    FirebaseDatabase rootNode;
    DatabaseReference reference;


    public OutfitFragment(ArrayList<ArrayAttributes> outfit) {
        this.outfit = outfit;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.clothing, container, false);

        FirebaseApp.initializeApp(getContext());

        shoeI = view.findViewById(R.id.imageShoe);
        jacketI = view.findViewById(R.id.imageJack);
        shirtI = view.findViewById(R.id.imageShirt);
        botI = view.findViewById(R.id.imageBot);
        bagI = view.findViewById(R.id.imageBag);
        accessI = view.findViewById(R.id.imageAccess);
        star = view.findViewById(R.id.imageView2);
        fullstar = view.findViewById(R.id.imageView3);

        favSwitch = view.findViewById(R.id.switch1);

        if (outfit != null) {
            for (ArrayAttributes item : outfit) {
                String category = item.getCategory();
                int id = item.getId();

                switch (category) {
                    case "jackets":
                        jacketI.setImageResource(id);
                        jacketI.setTag(id);
                        break;
                    case "shoes":
                        shoeI.setImageResource(id);
                        shoeI.setTag(id);
                        break;
                    case "bottoms":
                        botI.setImageResource(id);
                        botI.setTag(id);
                        break;
                    case "shirts":
                        shirtI.setImageResource(id);
                        shirtI.setTag(id);
                        break;
                    case "access":
                        accessI.setImageResource(id);
                        accessI.setTag(id);
                        break;
                    case "bags":
                        bagI.setImageResource(id);
                        bagI.setTag(id);
                        break;
                    default:
                        Log.d("Graf", "unknown category: " + category);
                }
            }
        }

        try{
            FileOutputStream stream = requireContext().openFileOutput("latest_outfit_txt", Context.MODE_PRIVATE); // new file in the app's internal storage
            String data = shirtI.getTag()+","+botI.getTag()+","+shoeI.getTag()+","+jacketI.getTag()+","+bagI.getTag()+","+accessI.getTag();
            stream.write(data.getBytes());
            stream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        Button swapShoe = view.findViewById(R.id.swapShoe);  // must be  in clothing.xml
        swapShoe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ClothingDisplay) requireActivity()).goToSwap("shoes");
            }
        });
        Button swapBot = view.findViewById(R.id.swapBot);
        swapBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ClothingDisplay) requireActivity()).goToSwap("bottoms");
            }
        });
        Button swapShirt = view.findViewById(R.id.swapShirt);
        swapShirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ClothingDisplay) requireActivity()).goToSwap("shirts");
            }
        });
        Button swapJack = view.findViewById(R.id.swapJack);
        swapJack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ClothingDisplay) requireActivity()).goToSwap("jackets");
            }
        });
        Button swapAccess = view.findViewById(R.id.swapAccess);
        swapAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ClothingDisplay) requireActivity()).goToSwap("access");
            }
        });
        Button swapBag = view.findViewById(R.id.swapBag);
        swapBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ClothingDisplay) requireActivity()).goToSwap("bags");
            }
        });

        favSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    fullstar.setVisibility(View.VISIBLE);
                    star.setVisibility(View.INVISIBLE);

                    rootNode = FirebaseDatabase.getInstance();
                    reference = rootNode.getReference("outfit");

                    reference.child("access").setValue(accessI.getId());
                    reference.child("bag").setValue(bagI.getId());
                    reference.child("bottom").setValue(botI.getId());
                    reference.child("jacket").setValue(jacketI.getId());
                    reference.child("shirt").setValue(shirtI.getId());
                    reference.child("shoe").setValue(shoeI.getId());

                }else{
                    rootNode = FirebaseDatabase.getInstance();
                    reference = rootNode.getReference("outfit");

                    fullstar.setVisibility(View.INVISIBLE);
                    star.setVisibility(View.VISIBLE);

                    reference.child("access").setValue(0);
                    reference.child("bag").setValue(0);
                    reference.child("bottom").setValue(0);
                    reference.child("jacket").setValue(0);
                    reference.child("shirt").setValue(0);
                    reference.child("shoe").setValue(0);
                }
            }
        });

        return view;
    }


    public void updateItem(String category, int drawableId) {
        switch (category) {
            case "jackets":
                jacketI.setImageResource(drawableId);
                break;
            case "shoes":
                shoeI.setImageResource(drawableId);
                break;
            case "access":
                accessI.setImageResource(drawableId);
                break;
            case "shirts":
                shirtI.setImageResource(drawableId);
                break;
            case "bottoms":
                botI.setImageResource(drawableId);
                break;
            case "bags":
                bagI.setImageResource(drawableId);
                break;
            default:
                Log.d("OutfitFragment", "Unknown category: " + category);
        }
    }
}



