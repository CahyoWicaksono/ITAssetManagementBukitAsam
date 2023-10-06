package com.example.itassetmanagementptbukitasam.Manager;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.itassetmanagementptbukitasam.Account.ChangePasswordActivity;
import com.example.itassetmanagementptbukitasam.Account.UpdateEmailActivity;
import com.example.itassetmanagementptbukitasam.Clinic.AddComplaintClinic;
import com.example.itassetmanagementptbukitasam.Clinic.DeleteAcountClinic;
import com.example.itassetmanagementptbukitasam.Clinic.HomeClinic;
import com.example.itassetmanagementptbukitasam.Clinic.ProfileClinicActivity;
import com.example.itassetmanagementptbukitasam.Clinic.UpdateProfileClinicActivity;
import com.example.itassetmanagementptbukitasam.Logistic.SettingLogisticActivity;
import com.example.itassetmanagementptbukitasam.OtherMenu.Capture;
import com.example.itassetmanagementptbukitasam.OtherMenu.SignInActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.Report.ReportDivision.ReportManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class HomeManager extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar toolbar;
    private FirebaseAuth auth;
    private TextView location_admin;
    private BottomNavigationView bottomNavigationView;
    private CardView information, database, qrprogress, schedule, damageProgress, report;
    private ImageView setting;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_manager);

        swipeToRefresh();
        onBackPressed();

        database = findViewById(R.id.database_progress_manager);
        qrprogress = findViewById(R.id.qr_progress_manager);
        schedule = findViewById(R.id.schedule_progress_manager);
        damageProgress = findViewById(R.id.damage_progress_manager);
        information = findViewById(R.id.information_asset_manager);

        auth = FirebaseAuth.getInstance();

        location_admin = findViewById(R.id.location_manager);

        report = findViewById(R.id.report_manager);

        setting=findViewById(R.id.setting_manager);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingManagerActivity.class);
                startActivity(intent);
                finish();
            }
        });

        bottomNavigationView = findViewById(R.id.botomNavigationView_manager);

        bottomNavigationView.setSelectedItemId(R.id.id_home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.id_home:
                        return true;
                    case R.id.id_profile:
                        startActivity(new Intent(getApplicationContext(), ProfileManagerActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.id_code:
                        IntentIntegrator intentIntegrator = new IntentIntegrator(HomeManager.this);
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


        location_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoURL("https://maps.app.goo.gl/N97RnsPwfAHLojuA8");
            }
        });

        database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoURL("https://itassetmanagementptba.blogspot.com/2022/09/firebase-realtime-database.html");
            }
        });

        qrprogress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoURL("https://itassetmanagementptba.blogspot.com/2022/09/scan-and-generate-qr-code.html");
            }
        });
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoURL("https://itassetmanagementptba.blogspot.com/2022/09/schedule-maintenance.html");
            }
        });
        damageProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoURL("https://itassetmanagementptba.blogspot.com/2022/09/repair-replace-or-leave-it-be.html");
            }
        });


        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReportManager.class);
                startActivity(intent);
                finish();
            }
        });


        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(HomeManager.this)
                        .setTitle("Features")
                        .setMessage("The IT assets in question include the maintenance of hardware (hardware) and software (software) assets. The purpose of this study is to analyze and design an IT asset management information system for recording history maintenance.  Why is IT Asset Management Important? An Organization Delivers Value By Leveraging Its Assets.So it becomes important to keep those assets up and running;" +
                                "\n\n" + "ITAM Help In This But For IT Assets.\n" +
                                " Some of the Main Goals of IT Asset Management Are:\n" +
                                "•\tHelping Organizations To Create Asset Inventory.\n" +
                                "•\tProvides Visibility And Control Over IT Assets. \t\n" +
                                "•\tDrive Business Value By Capturing Asset Data.\n" +
                                "•\tAvoid Buying Unnecessary Assets.\n" +
                                "•\tAssistance In Tracking Licensed Software.\n" +
                                "•\tITAM Improves Organizational Understanding About Its Business Values\n" +
                                "•\tData Security\n")
                        .setNegativeButton("Close", null)
                        .show();
            }
        });

    }
    private void gotoURL(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    private void swipeToRefresh() {
        swipeRefreshLayout = findViewById(R.id.swipeContainer);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startActivity(getIntent());
                finish();
                overridePendingTransition(0, 0);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_blue_light
                , android.R.color.holo_orange_light, android.R.color.holo_red_light);

    }

    @Override
    public void onBackPressed() {
        new android.app.AlertDialog.Builder(this)
                .setIcon(R.drawable.logo_start)
                .setTitle("Exit App")
                .setMessage("Are you sure you want to exit the app?")
                .setPositiveButton("AGREE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if ((intentResult.getContents() != null)){
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(
                    HomeManager.this);
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
            Toast.makeText(HomeManager.this, "Scan Failed", Toast.LENGTH_SHORT).show();
        }

    }

}
