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
import com.example.itassetmanagementptbukitasam.Report.ReportAdapter.AssetReportAdapter;
import com.example.itassetmanagementptbukitasam.Report.ReportAdapter.AssetTransferReportAdapter;
import com.example.itassetmanagementptbukitasam.Report.penerimaan.ViewAssetRepairReport;
import com.example.itassetmanagementptbukitasam.Report.penyerahan.UploadAssetReportActivity;
import com.example.itassetmanagementptbukitasam.Report.penyerahan.UploadTransferReportActivity;
import com.example.itassetmanagementptbukitasam.model.FileinModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivityAssetTransferReport extends AppCompatActivity {

    FloatingActionButton tambahLaporan;
    RecyclerView recyclerView;
    AssetTransferReportAdapter assetTransferReportAdapter;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_report_transfer);

        tambahLaporan = findViewById(R.id.tambah_report_transfer);
        tambahLaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UploadTransferReportActivity.class));
            }
        });
        bottomNavigationView = findViewById(R.id.botomNavigation_transfer_report);

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

        recyclerView = findViewById(R.id.recyclerView_transfer);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<FileinModel> options =
                new FirebaseRecyclerOptions.Builder<FileinModel>().setQuery(FirebaseDatabase.getInstance().getReference("Transfer_Report"),FileinModel.class).build();

        assetTransferReportAdapter=new AssetTransferReportAdapter(options);
        recyclerView.setAdapter(assetTransferReportAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        assetTransferReportAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        assetTransferReportAdapter.stopListening();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
