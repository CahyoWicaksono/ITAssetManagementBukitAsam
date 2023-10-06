package com.example.itassetmanagementptbukitasam.SDM;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.itassetmanagementptbukitasam.Account.ChangePasswordActivity;
import com.example.itassetmanagementptbukitasam.Account.UpdateEmailActivity;
import com.example.itassetmanagementptbukitasam.Oprational.DeleteAcountOprational;
import com.example.itassetmanagementptbukitasam.Oprational.HomeOprational;
import com.example.itassetmanagementptbukitasam.Oprational.SignInOprationalActivity;
import com.example.itassetmanagementptbukitasam.Oprational.UpdateProfileOprationalActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.google.firebase.auth.FirebaseAuth;

public class SettingSDMActivity extends AppCompatActivity {

    TextView updateprofile, updateemail, updatepassword, deleteAccount, logOut;
    private FirebaseAuth auth;
    ImageView back;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_app_sdm);

        updateprofile = findViewById(R.id.updateprofile_sdm);
        updateemail = findViewById(R.id.change_email_sdm);
        updatepassword = findViewById(R.id.change_password_sdm);
        deleteAccount = findViewById(R.id.delete_account_sdm);
        logOut = findViewById(R.id.log_out_sdm);

        back = findViewById(R.id.back_account_sdm);


        auth = FirebaseAuth.getInstance();
        updateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UpdateProfileComplainActivity.class);
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
                Intent intent = new Intent(getApplicationContext(), DeleteAcountComplaint.class);
                startActivity(intent);
                finish();
            }
        });
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "User Logged Out...", Toast.LENGTH_SHORT).show();
                auth.signOut();
                Intent intent = new Intent(getApplicationContext(), SignInComplaintActivity.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeSDM.class);
                startActivity(intent);
                finish();
            }
        });




    }
}
