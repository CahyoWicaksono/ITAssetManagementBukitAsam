package com.example.itassetmanagementptbukitasam.Report.ReportDivision;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.itassetmanagementptbukitasam.Manager.ProfileManagerActivity;
import com.example.itassetmanagementptbukitasam.OtherMenu.Capture;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.Report.penerimaan.ViewAssetDivisioReport;
import com.example.itassetmanagementptbukitasam.Report.penerimaan.ViewAssetReceiverReport;
import com.example.itassetmanagementptbukitasam.Report.penerimaan.ViewAssetRepairReport;
import com.example.itassetmanagementptbukitasam.Report.penerimaan.ViewAssetTransferReport;
import com.example.itassetmanagementptbukitasam.Report.penerimaan.ViewDamageAssetReport;
import com.example.itassetmanagementptbukitasam.Report.penerimaan.ViewDestructionAssetReport;
import com.example.itassetmanagementptbukitasam.Report.penerimaan.ViewMaintenanceReport;
import com.example.itassetmanagementptbukitasam.Report.penerimaan.ViewRequestAssetReport;
import com.example.itassetmanagementptbukitasam.Report.penerimaan.ViewWithAssetReport;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ReportManager extends AppCompatActivity {
    CardView asset, maintenance, damage, repair, transfer, withdrawal, receiver,destruction,request;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_receive_manager);

        asset = findViewById(R.id.report_asset_view);
        maintenance = findViewById(R.id.maintenance_report_view);
        damage = findViewById(R.id.report_damage_view);
        repair = findViewById(R.id.report_repair_view);
        transfer = findViewById(R.id.report_transfer_view);
        withdrawal = findViewById(R.id.report_withdrawal_view);
        receiver = findViewById(R.id.report_receiver_view);
        destruction = findViewById(R.id.report_destruction_view);
        request = findViewById(R.id.report_request_manager);


        asset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportManager.this, ViewAssetDivisioReport.class));
                finish();
            }
        });
        maintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportManager.this, ViewMaintenanceReport.class));
                finish();
            }
        });
        damage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportManager.this, ViewDamageAssetReport.class));
                finish();
            }
        });
        repair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportManager.this, ViewAssetRepairReport.class));
                finish();
            }
        });

        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportManager.this, ViewAssetTransferReport.class));
                finish();
            }
        });
        withdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportManager.this, ViewWithAssetReport.class));
                finish();
            }
        });
        receiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportManager.this, ViewAssetReceiverReport.class));
                finish();
            }
        });
        destruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportManager.this, ViewDestructionAssetReport.class));
                finish();
            }
        });
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportManager.this, ViewRequestAssetReport.class));
                finish();
            }
        });

        bottomNavigationView = findViewById(R.id.botomNavigation_report_view_manager);

        bottomNavigationView.setSelectedItemId(R.id.id_home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.id_home:
                        return true;
                    case R.id.id_profile:
                        startActivity(new Intent(getApplicationContext(), ProfileManagerActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.id_code:
                        IntentIntegrator intentIntegrator = new IntentIntegrator(ReportManager.this);
                        intentIntegrator.setPrompt("For flash use volume key");
                        intentIntegrator.setBeepEnabled(true);
                        intentIntegrator.setOrientationLocked(true);
                        intentIntegrator.setCaptureActivity(Capture.class);
                        intentIntegrator.initiateScan();
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if ((intentResult.getContents() != null)){
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(
                    ReportManager.this);
            builder.setTitle("Information");
            builder.setMessage(intentResult.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        }else {
            Toast.makeText(ReportManager.this, "Scan Failed", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
