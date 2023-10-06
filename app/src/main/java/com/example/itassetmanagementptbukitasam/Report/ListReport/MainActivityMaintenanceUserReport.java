package com.example.itassetmanagementptbukitasam.Report.ListReport;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itassetmanagementptbukitasam.Admin.GenerateCode;
import com.example.itassetmanagementptbukitasam.Admin.HomeAdminActivity;
import com.example.itassetmanagementptbukitasam.Admin.ProfileAdminActivity;
import com.example.itassetmanagementptbukitasam.OtherMenu.ScanQRCodeActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.Report.ReportAdapter.AssetMaintenanceReportAdapter;
import com.example.itassetmanagementptbukitasam.Report.ReportAdapter.AssetMaintenanceUserReportAdapter;
import com.example.itassetmanagementptbukitasam.Report.penyerahan.UploadMantenanceReportActivity;
import com.example.itassetmanagementptbukitasam.Report.penyerahan.UploadMantenanceUserReportActivity;
import com.example.itassetmanagementptbukitasam.User.HomeUserActivity;
import com.example.itassetmanagementptbukitasam.User.ProfileUserActivity;
import com.example.itassetmanagementptbukitasam.model.FileinModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivityMaintenanceUserReport extends AppCompatActivity {

    FloatingActionButton tambahLaporan;
    RecyclerView recyclerView;
    AssetMaintenanceUserReportAdapter assetMaintenanceUserReportAdapter;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_report_maintenance_user);

        tambahLaporan = findViewById(R.id.tambah_report_maintenance_user);
        tambahLaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UploadMantenanceUserReportActivity.class));
            }
        });
        bottomNavigationView = findViewById(R.id.botomNavigation_report_maintenance_user);

        bottomNavigationView.setSelectedItemId(R.id.id_home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.id_home:
                        startActivity(new Intent(getApplicationContext(), HomeUserActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.id_profile:
                        startActivity(new Intent(getApplicationContext(), ProfileUserActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.id_code:
                        startActivity(new Intent(getApplicationContext(), ScanQRCodeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });

        recyclerView = findViewById(R.id.recyclerView_maintenance_user);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<FileinModel> options =
                new FirebaseRecyclerOptions.Builder<FileinModel>().setQuery(FirebaseDatabase.getInstance().getReference("Maintenance_Report_user"),FileinModel.class).build();

        assetMaintenanceUserReportAdapter=new AssetMaintenanceUserReportAdapter(options);
        recyclerView.setAdapter(assetMaintenanceUserReportAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        assetMaintenanceUserReportAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        assetMaintenanceUserReportAdapter.stopListening();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
