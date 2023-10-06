package com.example.itassetmanagementptbukitasam.User;

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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.itassetmanagementptbukitasam.ListData.OtherData.CheckingActivity;
import com.example.itassetmanagementptbukitasam.ListData.OtherData.ViewWorkOrderActivity;
import com.example.itassetmanagementptbukitasam.ListData.OtherData.WorkOrderActivity;
import com.example.itassetmanagementptbukitasam.ListDataUser.DamageUserActivity;
import com.example.itassetmanagementptbukitasam.ListDataUser.MaintenanceUserActivity;
import com.example.itassetmanagementptbukitasam.ListDataUser.RepairUserActivity;
import com.example.itassetmanagementptbukitasam.ListDataUser.ScheduleUserActvity;
import com.example.itassetmanagementptbukitasam.ListDataUser.ViewAssetActivity;
import com.example.itassetmanagementptbukitasam.Logistic.SettingLogisticActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.Report.ReportDivision.ReportUser;
import com.example.itassetmanagementptbukitasam.model.WriteUserDetailsModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalTime;
import java.util.ArrayList;


public class HomeUserActivity extends AppCompatActivity {

    private CardView information,databaseUser,qrprogressUser,scheduleUser,damageProgressUser,menuCode;
    private LocalTime time_user;
    private TextView location_user,viewAll, textViewFullName,
            textViewEmail, textViewMobile;
    private String FullName,Email, Dob, Mobile, Gender, Address;
    private FirebaseAuth auth;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageSlider slider;
    private BottomNavigationView bottomNavigationViewUser;
    private ImageView menuAset,menuMaintenance, menuRepair, menuDamage, menuSchedule,setting, workorder,checking,report;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_officer);

        swipeToRefresh();


        slider = findViewById(R.id.Slidder_user);

        bottomNavigationViewUser = findViewById(R.id.botomNavigationView_user);

        location_user = findViewById(R.id.location);

        databaseUser = findViewById(R.id.database_progress_user);
        qrprogressUser = findViewById(R.id.qr_progress_user);
        scheduleUser =findViewById(R.id.schedule_progress_user);
        damageProgressUser = findViewById(R.id.damage_progress_user);

        information = findViewById(R.id.information_asset_user);

        bottomNavigationViewUser.setSelectedItemId(R.id.id_home);

        menuMaintenance = findViewById(R.id.maintenance_user);
        menuRepair = findViewById(R.id.repair_user);
        menuDamage = findViewById(R.id.damage_user);
        menuSchedule = findViewById(R.id.schedule_user);
        workorder = findViewById(R.id.work_officer);
        checking = findViewById(R.id.checking_officer);
        report = findViewById(R.id.report_officer);

        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();

        setting=findViewById(R.id.setting_officer);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingOfficerActivity.class);
                startActivity(intent);
                finish();
            }
        });

        bottomNavigationViewUser.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.id_home:
                        return true;
                    case R.id.id_profile:
                        startActivity(new Intent(getApplicationContext(), ProfileUserActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.id_code:
                        startActivity(new Intent(getApplicationContext(), QRCodeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });

        menuMaintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), MaintenanceUserActivity.class);
                startActivity(intent);
                finish();
            }
        });
        menuRepair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), RepairUserActivity.class);
                startActivity(intent);
                finish();
            }
        });
        menuDamage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), DamageUserActivity.class);
                startActivity(intent);
                finish();
            }
        });
        menuSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), ScheduleUserActvity.class);
                startActivity(intent);
                finish();
            }
        });
        workorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), ViewWorkOrderActivity.class);
                startActivity(intent);
                finish();
            }
        });
        checking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), CheckingActivity.class);
                startActivity(intent);
                finish();
            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), ReportUser.class);
                startActivity(intent);
                finish();
            }
        });



        ArrayList <SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://th.bing.com/th/id/R.6b62eb6b7a679a6d7ffc074995c19571?rik=Ydn2NqwQC%2b4A%2fw&riu=http%3a%2f%2fbestpower1.co.id%2fwp-content%2fuploads%2f2018%2f03%2fPower-Plant-Site-Pelabuhan-Tarahan-2-1024x576.png&ehk=yxRMTFgqI8rk71SUpgjrv8OeBI7yphMr0deHaRldUeI%3d&risl=&pid=ImgRaw&r=0", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://th.bing.com/th/id/R.ce9d55d6875c54dd5ef8cd96258a7820?rik=5uvMblrwYaUObw&riu=http%3a%2f%2f4.bp.blogspot.com%2f-WO20qd6Er2Q%2fVVNcwrEmqGI%2fAAAAAAAAAlI%2fGjviK1Sqyh4%2fs1600%2fFoto1252.jpg&ehk=k%2bsPcVU%2bUpP8sMhfHDhDNYn3%2bFbYrKXja6KcHTyAZiA%3d&risl=&pid=ImgRaw&r=0", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://th.bing.com/th/id/R.da48daee99873c59ee002ac24305f3e5?rik=Y4pHhFZmyEwT8A&riu=http%3a%2f%2ftransportasi.co%2fimages%2fmodules%2fcontent%2fimages%2fp_dermaga_batubara_terbesar.jpg&ehk=4X9n0bxOwGSTS%2fYgAchUKZiwZmtrYfbwt92NFBBxxGs%3d&risl=&pid=ImgRaw&r=0", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://th.bing.com/th/id/OIP.hyZs1MqL8x7Shvn380P-eAHaE6?pid=ImgDet&rs=1", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://th.bing.com/th/id/R.569c840171407a99002bfe0db44eb8bc?rik=7AcZav9H6LkGcQ&riu=http%3a%2f%2fannualreport.id%2fassets%2fptbacoid2-1461047205.jpg&ehk=WCj8q8nB9t10LbB48%2fWT7njvEqVCNwnMcqJsiv6uAas%3d&risl=&pid=ImgRaw&r=0", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://www.itera.ac.id/wp-content/uploads/2016/11/3-2.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://th.bing.com/th/id/OIP.av5AO4dvDvpDoyegM69B2QHaEK?pid=ImgDet&rs=1", ScaleTypes.FIT));


        slider.setImageList(slideModels, ScaleTypes.FIT);

        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(HomeUserActivity.this)
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


        location_user.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                    gotoURL("https://maps.app.goo.gl/N97RnsPwfAHLojuA8");
                }
        });

        databaseUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoURL ("https://itassetmanagementptba.blogspot.com/2022/09/firebase-realtime-database.html");
            }
        });

        qrprogressUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoURL("https://itassetmanagementptba.blogspot.com/2022/09/scan-and-generate-qr-code.html");
            }
        });
        scheduleUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoURL("https://itassetmanagementptba.blogspot.com/2022/09/schedule-maintenance.html");
            }
        });
        damageProgressUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoURL("https://itassetmanagementptba.blogspot.com/2022/09/repair-replace-or-leave-it-be.html");
            }
        });
        if (firebaseUser == null){
            Toast.makeText(HomeUserActivity.this, "Something went wrong! User's details are not available at the moment",
                    Toast.LENGTH_SHORT).show();
        }else {
            showUserProfile(firebaseUser);
        }

    }
    private void gotoURL(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }


    private void swipeToRefresh() {
        swipeRefreshLayout = findViewById(R.id.swipeContainer);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startActivity(getIntent());
                finish();
                overridePendingTransition(0,0);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_blue_light
                ,android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }



    private void showUserProfile(FirebaseUser firebaseUser) {
        String UserId = firebaseUser.getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Register User");

        databaseReference.child(UserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                WriteUserDetailsModel writeUserDetailsModel = snapshot.getValue(WriteUserDetailsModel.class);
                if (writeUserDetailsModel != null){
                    FullName = firebaseUser.getDisplayName();
                    textViewFullName.setText("Welcome, " + FullName + "!");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeUserActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
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

}



