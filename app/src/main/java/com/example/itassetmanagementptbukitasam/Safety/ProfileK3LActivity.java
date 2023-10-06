package com.example.itassetmanagementptbukitasam.Safety;

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

import com.example.itassetmanagementptbukitasam.Admin.GenerateCode;
import com.example.itassetmanagementptbukitasam.Admin.HomeAdminActivity;
import com.example.itassetmanagementptbukitasam.Oprational.HomeOprational;
import com.example.itassetmanagementptbukitasam.Oprational.ProfileOprationalActivity;
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

public class ProfileK3LActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_k3l_profile);


        swipeToRefresh();

        textViewFullName    = findViewById(R.id.textView_show_full_name_safety);
        textViewEmail       = findViewById(R.id.textView_show_email_safety);
        textViewDob         = findViewById(R.id.textView_show_dob_safety);
        textViewMobile      = findViewById(R.id.textView_show_mobile_safety);
        textViewGender      = findViewById(R.id.textView_show_gender_safety);
        progressBar         = findViewById(R.id.progress_bar_safety);


        bottomNavigationViewProfile = findViewById(R.id.botomNavigationView_profile_safety);

        auth                = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();

        bottomNavigationViewProfile.setSelectedItemId(R.id.id_profile);

        bottomNavigationViewProfile.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.id_home:
                        startActivity(new Intent(getApplicationContext(), HomeSafety.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.id_profile:
                        return true;
                    case R.id.id_code:
                        IntentIntegrator intentIntegrator = new IntentIntegrator(ProfileK3LActivity.this);
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
            Toast.makeText(ProfileK3LActivity.this, "Something went wrong! User's details are not available at the moment",
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

//    private void showAlertDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileAdminActivity.this);
//        builder.setTitle("Email not verified");
//        builder.setMessage("Please verift your email now.You can not login without email verification next time");
//
//        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int which) {
//                Intent intent = new Intent(Intent.ACTION_MAIN);
//                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//            }
//        });
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//    }

    private void showUserProfile(FirebaseUser firebaseUser) {
        String UserId = firebaseUser.getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Register_Safety");
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
                Toast.makeText(ProfileK3LActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if ((intentResult.getContents() != null)){
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(
                    ProfileK3LActivity.this);
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
            Toast.makeText(ProfileK3LActivity.this, "Scan Failed", Toast.LENGTH_SHORT).show();
        }

    }

}
