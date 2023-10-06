package com.example.itassetmanagementptbukitasam.Admin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.itassetmanagementptbukitasam.ListData.AssetData.AssetDataActivity;
import com.example.itassetmanagementptbukitasam.ListData.OtherData.ComplaintActivity;
import com.example.itassetmanagementptbukitasam.ListData.OtherData.DamageActivity;
import com.example.itassetmanagementptbukitasam.ListData.OtherData.LoocationActivity;
import com.example.itassetmanagementptbukitasam.ListData.OtherData.MaintenanceActivity;
import com.example.itassetmanagementptbukitasam.ListData.OtherData.OfficerActivity;
import com.example.itassetmanagementptbukitasam.ListData.OtherData.RepairActivity;
import com.example.itassetmanagementptbukitasam.ListData.OtherData.RollingActivity;
import com.example.itassetmanagementptbukitasam.ListData.OtherData.ScheduleActivity;
import com.example.itassetmanagementptbukitasam.ListData.OtherData.SoftwareActivity;
import com.example.itassetmanagementptbukitasam.ListData.OtherData.TransferActivity;
import com.example.itassetmanagementptbukitasam.ListData.OtherData.ViewCheckingActivity;
import com.example.itassetmanagementptbukitasam.ListData.OtherData.WorkOrderActivity;
import com.example.itassetmanagementptbukitasam.ListData.Receiver.ListReceiver.ReceiverActivity;
import com.example.itassetmanagementptbukitasam.ListData.Request.ListRequest.RequestActivity;
import com.example.itassetmanagementptbukitasam.ListData.Withdrawal.List.WithdrawalActivity;
import com.example.itassetmanagementptbukitasam.Notification.NotificationActivity;
import com.example.itassetmanagementptbukitasam.OtherMenu.AccessActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.Report.ReportDivision.OverallReport;
import com.example.itassetmanagementptbukitasam.Report.ReportDivision.ReportAdmin;
import com.example.itassetmanagementptbukitasam.Report.ReportDivision.ReportManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class AllMenuActivity extends AppCompatActivity {

    private ImageView menuAset, menuMaintenance, menuRepair, menuTransfer, menuWith, menuDamage,
            menuOfficer, menuAccess, menuSchedule, menuSosftware,division, complain, work, request, response,
            notifikasi, pengecekan, rolling, penerimaan, pelaporan;
    private BottomNavigationView bottomNavigationView;
    private TextView location_admin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_menu_admin);

        menuAset = findViewById(R.id.menu_asset);
        menuMaintenance = findViewById(R.id.menu_maintenance);
        menuRepair = findViewById(R.id.menu_repair);
        menuDamage = findViewById(R.id.menu_damage);
        menuTransfer = findViewById(R.id.menu_transfer);
        menuOfficer = findViewById(R.id.menu_officer);
        menuAccess = findViewById(R.id.menu_access);
        menuWith = findViewById(R.id.menu_withdrawal);
        menuSosftware = findViewById(R.id.menu_software);
        menuSchedule = findViewById(R.id.menu_schedule);

        complain  = findViewById(R.id.menu_complaint_admin);
        division = findViewById(R.id.menu_division_admin);
        work = findViewById(R.id.menu_work_admin);
        request = findViewById(R.id.menu_request_admin);
        response = findViewById(R.id.menu_response_admin);
        notifikasi = findViewById(R.id.menu_notif_admin);
        pengecekan = findViewById(R.id.menu_checking_Admin);
        rolling = findViewById(R.id.menu_rolling_admin);
        penerimaan = findViewById(R.id.menu_receiver_admin);
        pelaporan = findViewById(R.id.menu_report_admin);



        location_admin = findViewById(R.id.menu_location_admin);
        location_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoURL("https://maps.app.goo.gl/N97RnsPwfAHLojuA8");
            }
        });
        //menu
        menuAset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AssetDataActivity.class);
                startActivity(intent);
                finish();
            }
        });
        menuMaintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MaintenanceActivity.class);
                startActivity(intent);
                finish();
            }
        });
        menuRepair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RepairActivity.class);
                startActivity(intent);
                finish();
            }
        });
        menuDamage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DamageActivity.class);
                startActivity(intent);
                finish();
            }
        });
        menuTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TransferActivity.class);
                startActivity(intent);
                finish();
            }
        });

        menuOfficer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OfficerActivity.class);
                startActivity(intent);
                finish();

            }
        });
        menuAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AccessActivity.class);
                startActivity(intent);
                finish();
            }
        });
        menuSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ScheduleActivity.class);
                startActivity(intent);
                finish();
            }
        });
        menuWith.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WithdrawalActivity.class);
                startActivity(intent);
                finish();
            }
        });
        menuSosftware.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SoftwareActivity.class);
                startActivity(intent);
                finish();
            }
        });

        complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ComplaintActivity.class);
                startActivity(intent);
                finish();
            }
        });
        division.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoocationActivity.class);
                startActivity(intent);
                finish();
            }
        });
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RequestTakingActivity.class);
                startActivity(intent);
                finish();
            }
        });
        work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WorkOrderActivity.class);
                startActivity(intent);
                finish();
            }
        });
        response.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ResponseDivisionActivity.class);
                startActivity(intent);
                finish();
            }
        });
        notifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
                startActivity(intent);
                finish();
            }
        });
        rolling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RollingActivity.class);
                startActivity(intent);
                finish();
            }
        });
        penerimaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReceiverAssetActivity.class);
                startActivity(intent);
                finish();
            }
        });
        pelaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OverallReport.class);
                startActivity(intent);
                finish();
            }
        });

        pengecekan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ViewCheckingActivity.class);
                startActivity(intent);
                finish();

            }
        });
        bottomNavigationView = findViewById(R.id.botomNavigationView_menu);

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
    private void gotoURL(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
