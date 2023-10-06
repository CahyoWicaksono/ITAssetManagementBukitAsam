package com.example.itassetmanagementptbukitasam.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.itassetmanagementptbukitasam.ListData.Response.List.ResponseClinicActivity;
import com.example.itassetmanagementptbukitasam.ListData.Response.List.ResponseHumasActivity;
import com.example.itassetmanagementptbukitasam.ListData.Response.List.ResponseLabActivity;
import com.example.itassetmanagementptbukitasam.ListData.Response.List.ResponseOprationalActivity;
import com.example.itassetmanagementptbukitasam.ListData.Response.List.ResponseSDMActivity;
import com.example.itassetmanagementptbukitasam.ListData.Response.List.ResponsesSafetyActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class ResponseDivisionActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private FirebaseAuth auth;
    private CardView cardViewResponseHRD, cardViewResponseHumas, cardViewResponseSafety, cardViewResponseOprational,
            cardViewResponseClinic, cardViewResponseLab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_division_response);

        cardViewResponseHRD = findViewById(R.id.asset_response_hr);
        cardViewResponseHumas = findViewById(R.id.asset_response_humas);
        cardViewResponseSafety = findViewById(R.id.asset_response_safety);
        cardViewResponseOprational = findViewById(R.id.asset_response_oprational);
        cardViewResponseClinic = findViewById(R.id.asset_response_clinic);
        cardViewResponseLab = findViewById(R.id.asset_response_labolatorium);

        cardViewResponseHRD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResponseDivisionActivity.this, ResponseSDMActivity.class));
                finish();
            }
        });
        cardViewResponseHumas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResponseDivisionActivity.this, ResponseHumasActivity.class));
                finish();
            }
        });
        cardViewResponseSafety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResponseDivisionActivity.this, ResponsesSafetyActivity.class));
                finish();
            }
        });
        cardViewResponseOprational.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResponseDivisionActivity.this, ResponseOprationalActivity.class));
                finish();
            }
        });
        cardViewResponseClinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResponseDivisionActivity.this, ResponseClinicActivity.class));
                finish();
            }
        });
        cardViewResponseLab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResponseDivisionActivity.this, ResponseLabActivity.class));
                finish();
            }
        });


        bottomNavigationView = findViewById(R.id.botomNavigation_asset_response);

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

    }
}