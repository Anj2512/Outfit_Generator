package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class LatestFit extends AppCompatActivity {

    ImageView lShirt, lJack, lBot, lAccess, lShoe, lBag;
    Button goBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.latestfitxml);

        lAccess = findViewById(R.id.latestAccess);
        lBag = findViewById(R.id.latestBag);
        lBot = findViewById(R.id.latestBottom);
        lJack = findViewById(R.id.latestJacket);
        lShirt = findViewById(R.id.latestShirt);
        lShoe = findViewById(R.id.latestShoe);

        goBack = findViewById(R.id.bBack);

        try {
            FileInputStream inputStream = openFileInput("latest_outfit_txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = reader.readLine();
            if (line != null) {
                String[] fits = line.split(",");

                int shirtID = Integer.parseInt(fits[0]);
                int botID = Integer.parseInt(fits[1]);
                int shoeID = Integer.parseInt(fits[2]);
                int jackID = Integer.parseInt(fits[3]);
                int bagID = Integer.parseInt(fits[4]);
                int accessID = Integer.parseInt(fits[5]);

                lShirt.setImageResource(shirtID);
                lBot.setImageResource(botID);
                lShoe.setImageResource(shoeID);
                lJack.setImageResource(jackID);
                lBag.setImageResource(bagID);
                lAccess.setImageResource(accessID);

            }
            reader.close();
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LatestFit.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}

