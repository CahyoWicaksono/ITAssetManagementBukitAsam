package com.example.itassetmanagementptbukitasam.Report.ListReport;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itassetmanagementptbukitasam.Admin.GenerateCode;
import com.example.itassetmanagementptbukitasam.Admin.HomeAdminActivity;
import com.example.itassetmanagementptbukitasam.Admin.ProfileAdminActivity;
import com.example.itassetmanagementptbukitasam.Logistic.HomeLogisticActivity;
import com.example.itassetmanagementptbukitasam.Logistic.ProfileLogisticActivity;
import com.example.itassetmanagementptbukitasam.OtherMenu.Capture;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.Report.ReportAdapter.AssetReceiverReportAdapter;
import com.example.itassetmanagementptbukitasam.Report.ReportAdapter.AssetReportAdapter;
import com.example.itassetmanagementptbukitasam.Report.penyerahan.UploadAssetReportActivity;
import com.example.itassetmanagementptbukitasam.Report.penyerahan.UploadReceiverReportActivity;
import com.example.itassetmanagementptbukitasam.model.FileinModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivityAssetReceiverReport extends AppCompatActivity {

    FloatingActionButton tambahLaporan;
    RecyclerView recyclerView;
    AssetReceiverReportAdapter assetReceiverReportAdapter;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_report_receiver);

        tambahLaporan = findViewById(R.id.tambah_receiver_report);
        tambahLaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UploadReceiverReportActivity.class));
            }
        });
        bottomNavigationView = findViewById(R.id.botomNavigation_receiver_report);

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

        recyclerView = findViewById(R.id.recyclerView_receiver);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<FileinModel> options =
                new FirebaseRecyclerOptions.Builder<FileinModel>().setQuery(FirebaseDatabase.getInstance().getReference("Receiver_Report"), FileinModel.class).build();

        assetReceiverReportAdapter = new AssetReceiverReportAdapter(options);
        recyclerView.setAdapter(assetReceiverReportAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        assetReceiverReportAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        assetReceiverReportAdapter.stopListening();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}