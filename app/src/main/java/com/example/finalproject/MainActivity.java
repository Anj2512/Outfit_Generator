package com.example.finalproject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button bTop, bBot, bAcc, bJack, bShoe, bBag, bGenerate, bSee;
    ArrayList<Button> buttonsList;

    ArrayList<Integer> shirts, bottoms, accessories, jackets, shoes, bags;

    ArrayList<ArrayList> chosenLists;
    ArrayList<ArrayAttributes> chosenIds;

    String message = ""; String cat = "";
    int wwa = -1;
    static String INTENT_VAL;
    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bTop = findViewById(R.id.bShirt);
        bBot = findViewById(R.id.bBottom);
        bAcc = findViewById(R.id.bAccessories);
        bJack = findViewById(R.id.bJackets);
        bShoe = findViewById(R.id.bShoes);
        bBag = findViewById(R.id.bBags);
        bGenerate = findViewById(R.id.button7);
        bSee = findViewById(R.id.bSee);

        buttonsList = new ArrayList<>();
        chosenLists = new ArrayList<>();

        chosenIds = new ArrayList<>();

        shirts = new ArrayList<>();
        bottoms = new ArrayList<>();
        accessories = new ArrayList<>();
        shoes = new ArrayList<>();
        jackets = new ArrayList<>();
        bags = new ArrayList<>();

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                if(o.getResultCode() == RESULT_OK){
                    Intent data = o.getData();
                    Log.d("data from 2nd activity", data.getStringExtra(INTENT_VAL));
                }
            }
        });

        shirts.add(R.drawable.blackshirt);
        shirts.add(R.drawable.sleeveshirt);
        shirts.add(R.drawable.blueshirt);
        shirts.add(R.drawable.cuteshirt);
        shirts.add(R.drawable.frillyshirt);
        shirts.add(R.drawable.grayshirt);
        shirts.add(R.drawable.tshirt);
        shirts.add(R.drawable.tanktop);

        bottoms.add(R.drawable.brownpant);
        bottoms.add(R.drawable.greenpant);
        bottoms.add(R.drawable.jeanpant);
        bottoms.add(R.drawable.sweatpant);

        accessories.add(R.drawable.necklaceone);
        accessories.add(R.drawable.necklacetwo);
        accessories.add(R.drawable.sunglassone);
        accessories.add(R.drawable.sunglasstwo);

        shoes.add(R.drawable.blackshoes);
        shoes.add(R.drawable.brownshoes);
        shoes.add(R.drawable.greenshoes);
        shoes.add(R.drawable.redshoes);
        shoes.add(R.drawable.whiteshoes);

        jackets.add(R.drawable.brownjacket);
        jackets.add(R.drawable.jeanjacket);
        jackets.add(R.drawable.greayjacket);
        jackets.add(R.drawable.yellowjacket);

        bags.add(R.drawable.brownbag);
        bags.add(R.drawable.bluebag);
        bags.add(R.drawable.pinkbag);
        bGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Button b : buttonsList)
                {
                    Log.d("the chosen id", b.getResources().getResourceName(b.getId()));

                    message = b.getResources().getResourceName(b.getId());
                    String m = message.substring(message.indexOf("/")+1);

                    if(m.equals("bJackets")){
                        chosenLists.add(jackets);
                        cat = "jackets";
                        wwa++;
                    }
                    else if (m.equals("bAccessories")){
                        chosenLists.add(accessories);
                        cat = "access";
                        wwa++;
                    }
                    else if (m.equals("bShirt")){
                        chosenLists.add(shirts);
                        cat = "shirts";
                        wwa++;
                    }
                    else if (m.equals("bBottom")){
                        chosenLists.add(bottoms);
                        cat = "bottoms";
                        wwa++;
                    }
                    else if (m.equals("bShoes")){
                        chosenLists.add(shoes);
                        cat = "shoes";
                        wwa++;
                    }
                    else if (m.equals("bBags")){
                        chosenLists.add(bags);
                        cat = "bags";
                        wwa++;
                    }
                    else{
                        Log.d("problem", "probelm");
                    }
                    chosenIds.add(new ArrayAttributes(randomlySelectClothingItem(chosenLists.get(wwa)), cat));  // gets random item from list at most recent index and stores it in chosenIds list

                }
                callSecondActivity(chosenIds);
            }
        });

        bSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(getFilesDir(), "latest_outfit_txt");

                if(file.exists()){
                    ArrayList<ArrayAttributes> latestFits = new ArrayList<>();
                    try{
                        Log.d("view latest", "working!");
                        FileInputStream inputStream = openFileInput("latest_outfit_txt");
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        String line = reader.readLine();
                        reader.close();

                        String[] fits = line.split(",");
                        if(fits.length == 6) {
                            int shirtID = Integer.parseInt(fits[0]);
                            int botID = Integer.parseInt(fits[1]);
                            int shoeID = Integer.parseInt(fits[2]);
                            int jackID = Integer.parseInt(fits[3]);
                            int accessID = Integer.parseInt(fits[4]);
                            int bagID = Integer.parseInt(fits[5]);

                            latestFits.add(new ArrayAttributes(shirtID, "shirts"));
                            latestFits.add(new ArrayAttributes(botID, "bottoms"));
                            latestFits.add(new ArrayAttributes(shoeID, "shoes"));
                            latestFits.add(new ArrayAttributes(jackID, "jackets"));
                            latestFits.add(new ArrayAttributes(accessID, "access"));
                            latestFits.add(new ArrayAttributes(bagID, "bags"));

                            Intent intent = new Intent(MainActivity.this, LatestFit.class);
                            intent.putExtra("ids", latestFits);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Your latest outfit has to be FULL/COMPLETE to see it here.", Toast.LENGTH_SHORT).show();
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    public void onClick(View view){
        view.setBackgroundColor(Color.rgb(139,195,74));
        view.setTag("selected!");
        buttonsList.add((Button) view);

        Log.d("Added to list: ",  buttonsList.get(buttonsList.size()-1)+"");
    }

    public Integer randomlySelectClothingItem(ArrayList<Integer> list){ // this method gonna generate a clothing item of the list given
        int rand = (int) (Math.random() * list.size());

        return list.get(rand);
    }

    public void callSecondActivity(ArrayList<ArrayAttributes> ids){
        Intent intent = new Intent(MainActivity.this, ClothingDisplay.class);
        intent.putExtra("ids", ids);
        activityResultLauncher.launch(intent);
    }


}