package com.example.itassetmanagementptbukitasam.Report.ReportDivision;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.Report.penerimaan.ViewAssetRepairUserReport;
import com.example.itassetmanagementptbukitasam.Report.penerimaan.ViewMaintenanceUserReport;

public class ViewReportUser extends AppCompatActivity {

    CardView maintenance, repair;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_view_user);

        maintenance = findViewById(R.id.maintenance_report_view);
        repair = findViewById(R.id.report_repair_view);

        maintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewReportUser.this, ViewMaintenanceUserReport.class));
                finish();
            }
        });
        repair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewReportUser.this, ViewAssetRepairUserReport.class));
                finish();
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
