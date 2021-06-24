package com.example.vbreakdown;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class HomeActivity extends AppCompatActivity {
    ImageView roadEm;
    ImageView towing;
    ImageView fuel;
    ImageView onSpot;
    ImageView tyre;
    ImageView jump;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        roadEm=findViewById(R.id.roadEmergency);
        towing=findViewById(R.id.towing);
        fuel=findViewById(R.id.fuel);
        onSpot=findViewById(R.id.onSpot);
        tyre=findViewById(R.id.tyre);
        jump=findViewById(R.id.jumpstart);
        roadEm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,Road_Emergency.class));

            }
        });
        towing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, TowingAct.class));

            }
        });
        fuel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, FuelAct.class));
            }
        });
        onSpot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, OnSpotAct.class));
            }
        });
        tyre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, TyreAct.class));
            }
        });
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, JumpAct.class));
            }
        });
    }
}