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

import com.example.itassetmanagementptbukitasam.ListData.Receiver.ListReceiver.ViewReceiverClnicActivity;
import com.example.itassetmanagementptbukitasam.ListData.Request.ListRequest.ViewHumasRequestActivity;
import com.example.itassetmanagementptbukitasam.ListData.Request.ListRequest.ViewLabRequestActivity;
import com.example.itassetmanagementptbukitasam.ListData.Request.ListRequest.ViewOprationalRequestActivity;
import com.example.itassetmanagementptbukitasam.ListData.Request.ListRequest.ViewRequestActivity;
import com.example.itassetmanagementptbukitasam.ListData.Request.ListRequest.ViewSDMRequestActivity;
import com.example.itassetmanagementptbukitasam.ListData.Request.ListRequest.ViewSafetyRequestActivity;
import com.example.itassetmanagementptbukitasam.ListData.Withdrawal.List.WithdrawalActivity;
import com.example.itassetmanagementptbukitasam.ListData.Withdrawal.List.WithdrawalHumasActivity;
import com.example.itassetmanagementptbukitasam.ListData.Withdrawal.List.WithdrawalLabActivity;
import com.example.itassetmanagementptbukitasam.ListData.Withdrawal.List.WithdrawalLogisticActivity;
import com.example.itassetmanagementptbukitasam.ListData.Withdrawal.List.WithdrawalOprationalActivity;
import com.example.itassetmanagementptbukitasam.ListData.Withdrawal.List.WithdrawalSDMActivity;
import com.example.itassetmanagementptbukitasam.ListData.Withdrawal.List.WithdrawalSafetyActivity;
import com.example.itassetmanagementptbukitasam.Logistic.HomeLogisticActivity;
import com.example.itassetmanagementptbukitasam.Logistic.ProfileLogisticActivity;
import com.example.itassetmanagementptbukitasam.OtherMenu.Capture;
import com.example.itassetmanagementptbukitasam.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class WithdrawalDivisionActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private FirebaseAuth auth;
    private CardView cardViewLogistic, cardViewHR, cardViewHumas, cardViewSafety, cardViewOprational, cardViewClinic, cardViewLab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_division_withdrawal);

        cardViewHR = findViewById(R.id.asset_withdrawal_hrd);
        cardViewHumas = findViewById(R.id.asset_withdrawal_humas);
        cardViewSafety = findViewById(R.id.asset_withdrawal_safety);
        cardViewOprational = findViewById(R.id.asset_withdrawal_oprational);
        cardViewClinic = findViewById(R.id.asset_withdrawal_clinic);
        cardViewLab = findViewById(R.id.asset_withdrawal_labolatorium);
        cardViewLogistic = findViewById(R.id.asset_withdrawal_logistic);


        cardViewHR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), WithdrawalSDMActivity.class));
                overridePendingTransition(0, 0);
            }
        });
        cardViewHumas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), WithdrawalHumasActivity.class));
                overridePendingTransition(0, 0);
            }
        });
        cardViewSafety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), WithdrawalSafetyActivity.class));
                overridePendingTransition(0, 0);
            }
        });
        cardViewOprational.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), WithdrawalOprationalActivity.class));
                overridePendingTransition(0, 0);
            }
        });
        cardViewClinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), WithdrawalActivity.class));
                overridePendingTransition(0, 0);
            }
        });
        cardViewLab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), WithdrawalLabActivity.class));
                overridePendingTransition(0, 0);
            }
        });

        cardViewLogistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), WithdrawalLogisticActivity.class));
                overridePendingTransition(0, 0);
            }
        });


        bottomNavigationView = findViewById(R.id.botomNavigation_asset_withdrawal);

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