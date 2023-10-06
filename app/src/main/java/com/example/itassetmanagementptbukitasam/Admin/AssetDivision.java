package com.example.itassetmanagementptbukitasam.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.itassetmanagementptbukitasam.ListData.AssetData.AssetDataClinicActivity;
import com.example.itassetmanagementptbukitasam.ListData.AssetData.AssetDataHumasActivity;
import com.example.itassetmanagementptbukitasam.ListData.AssetData.AssetDataLabActivity;
import com.example.itassetmanagementptbukitasam.ListData.AssetData.AssetDataLogisticActivity;
import com.example.itassetmanagementptbukitasam.ListData.AssetData.AssetDataSDMActivity;
import com.example.itassetmanagementptbukitasam.ListData.AssetData.AssetDataSafetyActivity;
import com.example.itassetmanagementptbukitasam.ListData.AssetData.AssetOprationalActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class AssetDivision extends AppCompatActivity {

    CardView clinic, humas, lab, logistic, safety, sdm, oprational;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_division);

        clinic = findViewById(R.id.clinic);
        humas = findViewById(R.id.humas);
        lab = findViewById(R.id.labolatorium);
        safety = findViewById(R.id.safety);
        sdm = findViewById(R.id.hrd);
        oprational = findViewById(R.id.oprational);
        logistic = findViewById(R.id.logistic);

        bottomNavigationView = findViewById(R.id.botomNavigation_asset_division);


        bottomNavigationView.setSelectedItemId(R.id.id_home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.id_home:
                        startActivity(new Intent(getApplicationContext(), HomeAdminActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.id_profile:
                        startActivity(new Intent(getApplicationContext(), ProfileAdminActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.id_code:
                        startActivity(new Intent(getApplicationContext(), GenerateCode.class));
                        overridePendingTransition(0, 0);
                        return true;
                }

                return false;
            }
        });

        clinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AssetDataClinicActivity.class));
                overridePendingTransition(0, 0);
            }
        });

        humas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AssetDataHumasActivity.class));
                overridePendingTransition(0, 0);
            }
        });

        lab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AssetDataLabActivity.class));
                overridePendingTransition(0, 0);
            }
        });

        logistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AssetDataLogisticActivity.class));
                overridePendingTransition(0, 0);
            }
        });

        safety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AssetDataSafetyActivity.class));
                overridePendingTransition(0, 0);
            }
        });

        sdm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AssetDataSDMActivity.class));
                overridePendingTransition(0, 0);
            }
        });
        oprational.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AssetOprationalActivity.class));
                overridePendingTransition(0, 0);
            }
        });


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
