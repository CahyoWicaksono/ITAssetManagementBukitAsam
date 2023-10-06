package com.example.itassetmanagementptbukitasam.OtherMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.itassetmanagementptbukitasam.Admin.AddRegisterAdmin;
import com.example.itassetmanagementptbukitasam.Account.AddRegisterUserActivity;
import com.example.itassetmanagementptbukitasam.Admin.GenerateCode;
import com.example.itassetmanagementptbukitasam.Admin.HomeAdminActivity;
import com.example.itassetmanagementptbukitasam.Admin.ProfileAdminActivity;
import com.example.itassetmanagementptbukitasam.Clinic.AddRegisterClinic;
import com.example.itassetmanagementptbukitasam.Humas.AddRegisterHumas;
import com.example.itassetmanagementptbukitasam.Laboratory.AddRegisterLab;
import com.example.itassetmanagementptbukitasam.Logistic.AddRegisterLogistic;
import com.example.itassetmanagementptbukitasam.Manager.AddRegisterManager;
import com.example.itassetmanagementptbukitasam.Oprational.AddRegisterOprational;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.SDM.AddRegisterSDM;
import com.example.itassetmanagementptbukitasam.Safety.AddRegisterK3L;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccessActivity extends AppCompatActivity {
    private ImageView createAdmin, createUser,createsdm,createhumas,createlogistic,createsafety,createmanager
            ,createoprational,createla,createclinic;
    private FirebaseAuth auth;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_create_access);

        createAdmin = findViewById(R.id.id_admin_create);
        createUser = findViewById(R.id.id_user_create);
        createsdm = findViewById(R.id.id_hr_create);
        createhumas= findViewById(R.id.id_humas_create);
        createlogistic= findViewById(R.id.id_logistic_create);
        createsafety= findViewById(R.id.id_safety_create);
        createmanager= findViewById(R.id.id_manager_create);
        createoprational= findViewById(R.id.id_oprational_create);
        createla= findViewById(R.id.id_lab_create);
        createclinic= findViewById(R.id.id_clinic_create);

        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();


        bottomNavigationView = findViewById(R.id.botomNavigation_access);

        bottomNavigationView.setSelectedItemId(R.id.id_home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.id_home:
                        startActivity(new Intent(getApplicationContext(), HomeAdminActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.id_profile:
                        startActivity(new Intent(getApplicationContext(), ProfileAdminActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.id_code:
                        startActivity(new Intent(getApplicationContext(), GenerateCode.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });

        createAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AccessActivity.this, "You are create admin access now !!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AccessActivity.this, AddRegisterAdmin.class);
                startActivity(intent);
                finish();
            }
        });
        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AccessActivity.this, "You are create user access now !!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AccessActivity.this, AddRegisterUserActivity.class);
                startActivity(intent);
                finish();
            }
        });
        createsdm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AccessActivity.this, "You are create user access now !!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AccessActivity.this, AddRegisterSDM.class);
                startActivity(intent);
                finish();
            }
        });

        createhumas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AccessActivity.this, "You are create user access now !!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AccessActivity.this, AddRegisterHumas.class);
                startActivity(intent);
                finish();
            }
        });
        createlogistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AccessActivity.this, "You are create user access now !!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AccessActivity.this, AddRegisterLogistic.class);
                startActivity(intent);
                finish();
            }
        });
        createsafety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AccessActivity.this, "You are create user access now !!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AccessActivity.this, AddRegisterK3L.class);
                startActivity(intent);
                finish();
            }
        });
        createmanager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AccessActivity.this, "You are create user access now !!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AccessActivity.this, AddRegisterManager.class);
                startActivity(intent);
                finish();
            }
        });
        createoprational.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AccessActivity.this, "You are create user access now !!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AccessActivity.this, AddRegisterOprational.class);
                startActivity(intent);
                finish();
            }
        });
        createla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AccessActivity.this, "You are create user access now !!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AccessActivity.this, AddRegisterLab.class);
                startActivity(intent);
                finish();
            }
        });
        createclinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AccessActivity.this, "You are create user access now !!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AccessActivity.this, AddRegisterClinic.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
