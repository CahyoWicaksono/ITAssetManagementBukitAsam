package com.example.itassetmanagementptbukitasam.Admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.itassetmanagementptbukitasam.Account.ChangePasswordActivity;
import com.example.itassetmanagementptbukitasam.Account.DeleteAcountActivity;
import com.example.itassetmanagementptbukitasam.Account.UpdateEmailActivity;
import com.example.itassetmanagementptbukitasam.ListData.Receiver.ListReceiver.ViewReceiverClnicActivity;
import com.example.itassetmanagementptbukitasam.ListData.Request.ListRequest.RequestActivity;
import com.example.itassetmanagementptbukitasam.ListData.Request.ListRequest.ViewClinicRequestActivity;
import com.example.itassetmanagementptbukitasam.ListData.Request.ListRequest.ViewHumasRequestActivity;
import com.example.itassetmanagementptbukitasam.ListData.Request.ListRequest.ViewLabRequestActivity;
import com.example.itassetmanagementptbukitasam.ListData.Request.ListRequest.ViewOprationalRequestActivity;
import com.example.itassetmanagementptbukitasam.ListData.Request.ListRequest.ViewRequestActivity;
import com.example.itassetmanagementptbukitasam.ListData.Request.ListRequest.ViewSDMRequestActivity;
import com.example.itassetmanagementptbukitasam.ListData.Request.ListRequest.ViewSafetyRequestActivity;
import com.example.itassetmanagementptbukitasam.Logistic.DeleteAcountLogistic;
import com.example.itassetmanagementptbukitasam.Logistic.HomeLogisticActivity;
import com.example.itassetmanagementptbukitasam.Logistic.ProfileLogisticActivity;
import com.example.itassetmanagementptbukitasam.Logistic.SignInLogisticActivity;
import com.example.itassetmanagementptbukitasam.Logistic.UpdateProfileLogisticActivity;
import com.example.itassetmanagementptbukitasam.OtherMenu.Capture;
import com.example.itassetmanagementptbukitasam.OtherMenu.SignInActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class RequestDivisionActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private FirebaseAuth auth;
    private CardView cardViewIT, cardViewHR, cardViewHumas, cardViewSafety, cardViewOprational, cardViewClinic, cardViewLab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_division_request);

        cardViewHR = findViewById(R.id.aset_request_hrd);
        cardViewHumas = findViewById(R.id.aset_request_oprational);
        cardViewSafety = findViewById(R.id.aset_request_safety);
        cardViewOprational = findViewById(R.id.aset_request_clinic);
        cardViewClinic = findViewById(R.id.aset_request_labolatorium);
        cardViewLab = findViewById(R.id.aset_request_humas);

        auth = FirebaseAuth.getInstance();

        cardViewHR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RequestDivisionActivity.this, ViewSDMRequestActivity.class));
                finish();
            }
        });
        cardViewHumas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RequestDivisionActivity.this, ViewHumasRequestActivity.class));
                finish();
            }
        });
        cardViewSafety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RequestDivisionActivity.this, ViewSafetyRequestActivity.class));
                finish();
            }
        });
        cardViewOprational.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RequestDivisionActivity.this, ViewOprationalRequestActivity.class));
                finish();
            }
        });
        cardViewClinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RequestDivisionActivity.this, ViewClinicRequestActivity.class));
                finish();
            }
        });
        cardViewLab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RequestDivisionActivity.this, ViewLabRequestActivity.class));
                finish();
            }
        });


        bottomNavigationView = findViewById(R.id.botomNavigation_aset_request);

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