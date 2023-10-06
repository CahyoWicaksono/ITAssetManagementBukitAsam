package com.example.itassetmanagementptbukitasam.Manager;

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
import com.example.itassetmanagementptbukitasam.Logistic.DeleteAcountLogistic;
import com.example.itassetmanagementptbukitasam.Logistic.HomeLogisticActivity;
import com.example.itassetmanagementptbukitasam.Logistic.SignInLogisticActivity;
import com.example.itassetmanagementptbukitasam.Logistic.UpdateProfileLogisticActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.google.firebase.auth.FirebaseAuth;

public class SettingManagerActivity extends AppCompatActivity {

    TextView updateprofile, updateemail, updatepassword, deleteAccount, logOut;
    private FirebaseAuth auth;
    ImageView back;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_app_manager);

        updateprofile = findViewById(R.id.updateprofile_manager);
        updateemail = findViewById(R.id.change_email_manager);
        updatepassword = findViewById(R.id.change_password_manager);
        deleteAccount = findViewById(R.id.delete_account_manager);
        logOut = findViewById(R.id.log_out_manager);

        back = findViewById(R.id.back_account_manager);


        auth = FirebaseAuth.getInstance();
        updateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UpdateProfileManagerActivity.class);
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
                Intent intent = new Intent(getApplicationContext(), DeleteAcountManager.class);
                startActivity(intent);
                finish();
            }
        });
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "User Logged Out...", Toast.LENGTH_SHORT).show();
                auth.signOut();
                Intent intent = new Intent(getApplicationContext(), SignInManagerActivity.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeManager.class);
                startActivity(intent);
                finish();
            }
        });




    }
}
