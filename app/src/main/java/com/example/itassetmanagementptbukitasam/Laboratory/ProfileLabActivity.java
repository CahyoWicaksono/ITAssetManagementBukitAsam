package com.example.itassetmanagementptbukitasam.Laboratory;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.itassetmanagementptbukitasam.Clinic.HomeClinic;
import com.example.itassetmanagementptbukitasam.Clinic.ProfileClinicActivity;
import com.example.itassetmanagementptbukitasam.Humas.ProfileHumasActivity;
import com.example.itassetmanagementptbukitasam.OtherMenu.Capture;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.WriteAccountDetailsModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ProfileLabActivity extends AppCompatActivity {
    private TextView textViewWelcome, textViewFullName, textViewEmail, textViewDob, textViewMobile, textViewGender,textAddress;
    private ProgressBar progressBar;
    private String FullName,Email, Dob, Mobile,Gender,Address;
    private FirebaseAuth auth;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageView backProfile;
    private BottomNavigationView bottomNavigationViewProfile;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_profile);


        swipeToRefresh();

        textViewFullName    = findViewById(R.id.textView_show_full_name_lab);
        textViewEmail       = findViewById(R.id.textView_show_email_lab);
        textViewDob         = findViewById(R.id.textView_show_dob_lab);
        textViewMobile      = findViewById(R.id.textView_show_mobile_lab);
        textViewGender      = findViewById(R.id.textView_show_gender_lab);
        progressBar         = findViewById(R.id.progress_bar_lab);


        bottomNavigationViewProfile = findViewById(R.id.botomNavigationView_profile_lab);

        auth                = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();

        bottomNavigationViewProfile.setSelectedItemId(R.id.id_profile);

        bottomNavigationViewProfile.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.id_home:
                        startActivity(new Intent(getApplicationContext(), LabHome.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.id_profile:
                        return true;
                    case R.id.id_code:
                        IntentIntegrator intentIntegrator = new IntentIntegrator(ProfileLabActivity.this);
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

        if (firebaseUser == null){
            Toast.makeText(ProfileLabActivity.this, "Something went wrong! User's details are not available at the moment",
                    Toast.LENGTH_SHORT).show();
        }else {
            checkIfEmailVerified(firebaseUser);
            progressBar.setVisibility(View.VISIBLE);
            showUserProfile(firebaseUser);
        }

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

    private void checkIfEmailVerified(FirebaseUser firebaseUser) {
        if (!firebaseUser.isEmailVerified()){
//            showAlertDialog();
        }
    }

    private void showUserProfile(FirebaseUser firebaseUser) {
        String UserId = firebaseUser.getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Register_Laboratorium");
        databaseReference.child(UserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                WriteAccountDetailsModel writeAccountDetailsModel = snapshot.getValue(WriteAccountDetailsModel.class);
                if (writeAccountDetailsModel != null){
                    FullName = firebaseUser.getDisplayName();
                    Email    = firebaseUser.getEmail();
                    Dob      = writeAccountDetailsModel.Dob;
                    Gender   = writeAccountDetailsModel.gender;
                    Mobile   = writeAccountDetailsModel.mobile;


                    textViewWelcome.setText("Welcome, "+FullName+ "!");
                    textViewFullName.setText(FullName);
                    textViewEmail.setText(Email);
                    textViewDob.setText(Dob);
                    textViewGender.setText(Gender);
                    textViewMobile.setText(Mobile);
                }
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileLabActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if ((intentResult.getContents() != null)){
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(
                    ProfileLabActivity.this);
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
            Toast.makeText(ProfileLabActivity.this, "Scan Failed", Toast.LENGTH_SHORT).show();
        }

    }

}
