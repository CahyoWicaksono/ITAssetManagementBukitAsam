package com.example.itassetmanagementptbukitasam.Report.ReportDivision;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.itassetmanagementptbukitasam.Admin.GenerateCode;
import com.example.itassetmanagementptbukitasam.Admin.HomeAdminActivity;
import com.example.itassetmanagementptbukitasam.Admin.ProfileAdminActivity;

import com.example.itassetmanagementptbukitasam.R;
//import com.example.itassetmanagementptbukitasam.Report.ListReport.ListAssetReportActivity;
import com.example.itassetmanagementptbukitasam.Report.ListReport.MainActivityAssetDivisioReport;
import com.example.itassetmanagementptbukitasam.Report.ListReport.MainActivityAssetReceiverReport;
import com.example.itassetmanagementptbukitasam.Report.ListReport.MainActivityAssetRepairReport;
import com.example.itassetmanagementptbukitasam.Report.ListReport.MainActivityAssetRepairUserReport;
import com.example.itassetmanagementptbukitasam.Report.ListReport.MainActivityAssetReport;
import com.example.itassetmanagementptbukitasam.Report.ListReport.MainActivityAssetTransferReport;
import com.example.itassetmanagementptbukitasam.Report.ListReport.MainActivityDamageAssetReport;
import com.example.itassetmanagementptbukitasam.Report.ListReport.MainActivityMaintenanceReport;
import com.example.itassetmanagementptbukitasam.Report.ListReport.MainActivityRequestAssetReport;
import com.example.itassetmanagementptbukitasam.Report.ListReport.MainActivityWithAssetReport;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ReportAdmin extends AppCompatActivity {

    CardView asset, maintenance, damage, repair, transfer, request, receiver,withdrawal;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_create_admin);

        asset = findViewById(R.id.report_asset);
        maintenance = findViewById(R.id.maintenance_report);
        damage = findViewById(R.id.report_damage);
        repair = findViewById(R.id.report_repair);
        withdrawal = findViewById(R.id.report_withdrawal);
        transfer = findViewById(R.id.report_transfer_it);
        receiver = findViewById(R.id.report_receiver_it);
        request = findViewById(R.id.report_request);

        bottomNavigationView = findViewById(R.id.botomNavigation_report);


        asset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportAdmin.this, MainActivityAssetDivisioReport.class));
                finish();
            }
        });

        maintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportAdmin.this, MainActivityMaintenanceReport.class));
                finish();
            }
        });

        repair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportAdmin.this, MainActivityAssetRepairReport.class));
                finish();
            }
        });

        damage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportAdmin.this, MainActivityDamageAssetReport.class));
                finish();
            }
        });
        withdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportAdmin.this, MainActivityWithAssetReport.class));
                finish();
            }
        });
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportAdmin.this, MainActivityRequestAssetReport.class));
                finish();
            }
        });
        receiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportAdmin.this, MainActivityAssetReceiverReport.class));
                finish();
            }
        });
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportAdmin.this, MainActivityAssetTransferReport.class));
                finish();
            }
        });

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
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
