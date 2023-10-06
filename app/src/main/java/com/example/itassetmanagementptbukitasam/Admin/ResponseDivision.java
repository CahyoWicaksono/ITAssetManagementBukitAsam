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
import com.example.itassetmanagementptbukitasam.ListData.Response.List.ResponseLogisticActivity;
import com.example.itassetmanagementptbukitasam.ListData.Response.List.ResponseOprationalActivity;
import com.example.itassetmanagementptbukitasam.ListData.Response.List.ResponseSDMActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class ResponseDivision extends AppCompatActivity {

    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private FirebaseAuth auth;
    private CardView cardViewIT, cardViewLogistic, cardViewHR, cardViewHumas, cardViewSafety, cardViewOprational, cardViewClinic, cardViewLab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_division_response);


        cardViewHR = findViewById(R.id.asset_response_hr);
        cardViewHumas = findViewById(R.id.asset_response_humas);
        cardViewSafety = findViewById(R.id.asset_response_safety);
        cardViewOprational = findViewById(R.id.asset_response_oprational);
        cardViewClinic = findViewById(R.id.asset_response_clinic);
        cardViewLab = findViewById(R.id.asset_response_labolatorium);

        cardViewHR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ResponseSDMActivity.class);
                startActivity(intent);
                finish();
            }
        });
        cardViewOprational.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ResponseOprationalActivity.class);
                startActivity(intent);
                finish();
            }
        });
        cardViewLogistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ResponseLogisticActivity.class);
                startActivity(intent);
                finish();
            }
        });
        cardViewClinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ResponseClinicActivity.class);
                startActivity(intent);
                finish();
            }
        });
        cardViewLab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ResponseLabActivity.class);
                startActivity(intent);
                finish();
            }
        });
        cardViewHumas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ResponseHumasActivity.class);
                startActivity(intent);
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
