package com.example.itassetmanagementptbukitasam.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.itassetmanagementptbukitasam.Account.ChangePasswordActivity;
import com.example.itassetmanagementptbukitasam.Account.DeleteAcountActivity;
import com.example.itassetmanagementptbukitasam.Account.UpdateEmailActivity;
import com.example.itassetmanagementptbukitasam.Clinic.DeleteAcountClinic;
import com.example.itassetmanagementptbukitasam.Clinic.HomeClinic;
import com.example.itassetmanagementptbukitasam.Clinic.SignInClinicActivity;
import com.example.itassetmanagementptbukitasam.Clinic.UpdateProfileClinicActivity;
import com.example.itassetmanagementptbukitasam.Humas.SignInHumasActivity;
import com.example.itassetmanagementptbukitasam.ListData.AssetData.AssetDataActivity;
import com.example.itassetmanagementptbukitasam.OtherMenu.SignInActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class SettingActivity extends AppCompatActivity {

    TextView updateprofile, updateemail, updatepassword, deleteAccount, logOut;
    private FirebaseAuth auth;
    ImageView back;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_app_admin);

        updateprofile = findViewById(R.id.updateprofile_admin);
        updateemail = findViewById(R.id.change_account_admin);
        updatepassword = findViewById(R.id.change_password_admin);
        deleteAccount = findViewById(R.id.delete_account_admin);
        logOut = findViewById(R.id.log_out_admin);

        back = findViewById(R.id.back_account_admin);


        auth = FirebaseAuth.getInstance();
        updateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UpdateProfileAdminActivity.class);
                startActivity(intent);
                finish();
            }
        });
        updateemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UpdateEmailActivity.class);
                startActivity(intent);
                finish();

            }
        });
        updatepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });
        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DeleteAcountClinic.class);
                startActivity(intent);
                finish();
            }
        });
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "User Logged Out...", Toast.LENGTH_SHORT).show();
                auth.signOut();
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeAdminActivity.class);
                startActivity(intent);
                finish();
            }
        });




    }
}
