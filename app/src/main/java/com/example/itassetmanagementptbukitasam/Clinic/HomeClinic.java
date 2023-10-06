package com.example.itassetmanagementptbukitasam.Clinic;

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

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.itassetmanagementptbukitasam.Account.ChangePasswordActivity;
import com.example.itassetmanagementptbukitasam.Account.DeleteAcountActivity;
import com.example.itassetmanagementptbukitasam.Account.UpdateEmailActivity;
import com.example.itassetmanagementptbukitasam.Admin.GenerateCode;
import com.example.itassetmanagementptbukitasam.Admin.HomeAdminActivity;
import com.example.itassetmanagementptbukitasam.Admin.ProfileAdminActivity;
import com.example.itassetmanagementptbukitasam.Admin.SettingActivity;
import com.example.itassetmanagementptbukitasam.Admin.UpdateProfileAdminActivity;
import com.example.itassetmanagementptbukitasam.ListData.OtherData.MaintenanceActivity;
import com.example.itassetmanagementptbukitasam.ListData.Receiver.ListReceiver.ReceiverClnicActivity;
import com.example.itassetmanagementptbukitasam.ListData.Request.ListRequest.RequestClinicActivity;
import com.example.itassetmanagementptbukitasam.ListData.Response.List.ResponseViewClinicActivity;
import com.example.itassetmanagementptbukitasam.ListData.Withdrawal.List.ViewWithdrawalActivity;
import com.example.itassetmanagementptbukitasam.OtherMenu.Capture;
import com.example.itassetmanagementptbukitasam.OtherMenu.SignInActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class HomeClinic extends AppCompatActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar toolbar;
    private FirebaseAuth auth;
    private ImageSlider slider_admin;
    private BottomNavigationView bottomNavigationView;
    private CardView information, database, qrprogress, schedule, damageProgress;
    private TextView location_admin, view;
    private ImageView menuComplain, menuWith, menuRequest, menuReceiver, menuResposnse,setting;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_clinic);

        swipeToRefresh();
        onBackPressed();

        menuComplain = findViewById(R.id.complaint_clinic);
        menuWith = findViewById(R.id.withdrawal_clinic);
        menuRequest = findViewById(R.id.request_clinic);
        menuReceiver = findViewById(R.id.receiver_clinic);
        menuResposnse = findViewById(R.id.response_clinic);

        database = findViewById(R.id.database_progress_clinic);
        qrprogress = findViewById(R.id.qr_progress_clinic);
        schedule = findViewById(R.id.schedule_progress_clinic);
        damageProgress = findViewById(R.id.damage_progress_clinic);
        information = findViewById(R.id.information_asset_clinic);

        location_admin = findViewById(R.id.location_clinic);
        slider_admin = findViewById(R.id.Slidder_clinic);

        auth = FirebaseAuth.getInstance();


        bottomNavigationView = findViewById(R.id.botomNavigationView_home_clinic);

        bottomNavigationView.setSelectedItemId(R.id.id_home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.id_home:
                        return true;
                    case R.id.id_profile:
                        startActivity(new Intent(getApplicationContext(), ProfileClinicActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.id_code:
                        IntentIntegrator intentIntegrator = new IntentIntegrator(HomeClinic.this);
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
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://th.bing.com/th/id/R.6b62eb6b7a679a6d7ffc074995c19571?rik=Ydn2NqwQC%2b4A%2fw&riu=http%3a%2f%2fbestpower1.co.id%2fwp-content%2fuploads%2f2018%2f03%2fPower-Plant-Site-Pelabuhan-Tarahan-2-1024x576.png&ehk=yxRMTFgqI8rk71SUpgjrv8OeBI7yphMr0deHaRldUeI%3d&risl=&pid=ImgRaw&r=0", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://th.bing.com/th/id/R.ce9d55d6875c54dd5ef8cd96258a7820?rik=5uvMblrwYaUObw&riu=http%3a%2f%2f4.bp.blogspot.com%2f-WO20qd6Er2Q%2fVVNcwrEmqGI%2fAAAAAAAAAlI%2fGjviK1Sqyh4%2fs1600%2fFoto1252.jpg&ehk=k%2bsPcVU%2bUpP8sMhfHDhDNYn3%2bFbYrKXja6KcHTyAZiA%3d&risl=&pid=ImgRaw&r=0", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://th.bing.com/th/id/R.da48daee99873c59ee002ac24305f3e5?rik=Y4pHhFZmyEwT8A&riu=http%3a%2f%2ftransportasi.co%2fimages%2fmodules%2fcontent%2fimages%2fp_dermaga_batubara_terbesar.jpg&ehk=4X9n0bxOwGSTS%2fYgAchUKZiwZmtrYfbwt92NFBBxxGs%3d&risl=&pid=ImgRaw&r=0", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://th.bing.com/th/id/OIP.hyZs1MqL8x7Shvn380P-eAHaE6?pid=ImgDet&rs=1", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://th.bing.com/th/id/R.569c840171407a99002bfe0db44eb8bc?rik=7AcZav9H6LkGcQ&riu=http%3a%2f%2fannualreport.id%2fassets%2fptbacoid2-1461047205.jpg&ehk=WCj8q8nB9t10LbB48%2fWT7njvEqVCNwnMcqJsiv6uAas%3d&risl=&pid=ImgRaw&r=0", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://www.itera.ac.id/wp-content/uploads/2016/11/3-2.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://th.bing.com/th/id/OIP.av5AO4dvDvpDoyegM69B2QHaEK?pid=ImgDet&rs=1", ScaleTypes.FIT));

        slider_admin.setImageList(slideModels, ScaleTypes.FIT);


        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(HomeClinic.this)
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

        setting=findViewById(R.id.setting_clinic);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingClinicActivity.class);
                startActivity(intent);
                finish();
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


        menuComplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddComplaintClinic.class);
                startActivity(intent);
                finish();
            }
        });
        menuWith.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ViewWithdrawalActivity.class);
                startActivity(intent);
                finish();
            }
        });
        menuResposnse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ResponseViewClinicActivity.class);
                startActivity(intent);
                finish();
            }
        });
        menuRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RequestClinicActivity.class);
                startActivity(intent);
                finish();
            }
        });
        menuReceiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReceiverClnicActivity.class);
                startActivity(intent);
                finish();
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
                    HomeClinic.this);
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
            Toast.makeText(HomeClinic.this, "Scan Failed", Toast.LENGTH_SHORT).show();
        }

    }

}
