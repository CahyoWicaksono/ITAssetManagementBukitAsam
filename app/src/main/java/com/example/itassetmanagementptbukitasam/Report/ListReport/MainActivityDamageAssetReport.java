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
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.Report.ReportAdapter.AssetDamageReportAdapter;
import com.example.itassetmanagementptbukitasam.Report.ReportAdapter.AssetReportAdapter;
import com.example.itassetmanagementptbukitasam.Report.penyerahan.UploadAssetReportActivity;
import com.example.itassetmanagementptbukitasam.Report.penyerahan.UploadDamageReportActivity;
import com.example.itassetmanagementptbukitasam.model.FileinModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivityDamageAssetReport extends AppCompatActivity {

    FloatingActionButton tambahLaporan;
    RecyclerView recyclerView;
    AssetDamageReportAdapter assetDamageReportAdapter;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_report_damage);

        tambahLaporan = findViewById(R.id.tambah_report_damage);
        tambahLaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UploadDamageReportActivity.class));
            }
        });
        bottomNavigationView = findViewById(R.id.botomNavigation_report_damage);

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

        recyclerView = findViewById(R.id.recyclerView_damage);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<FileinModel> options =
                new FirebaseRecyclerOptions.Builder<FileinModel>().setQuery(FirebaseDatabase.getInstance().getReference("Damage_Report"),FileinModel.class).build();

        assetDamageReportAdapter =new AssetDamageReportAdapter(options);
        recyclerView.setAdapter(assetDamageReportAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        assetDamageReportAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        assetDamageReportAdapter.stopListening();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}


