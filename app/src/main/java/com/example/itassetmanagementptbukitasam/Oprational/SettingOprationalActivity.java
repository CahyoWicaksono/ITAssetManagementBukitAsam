package com.example.itassetmanagementptbukitasam.Oprational;

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

public class SettingOprationalActivity extends AppCompatActivity {

    TextView updateprofile, updateemail, updatepassword, deleteAccount, logOut;
    private FirebaseAuth auth;
    ImageView back;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_app_oprational);

        updateprofile = findViewById(R.id.updateprofile_oprational);
        updateemail = findViewById(R.id.change_email_oprational);
        updatepassword = findViewById(R.id.change_password_oprational);
        deleteAccount = findViewById(R.id.delete_account_oprational);
        logOut = findViewById(R.id.log_out_oprational);

        back = findViewById(R.id.back_account_oprational);


        auth = FirebaseAuth.getInstance();
        updateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UpdateProfileOprationalActivity.class);
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
                Intent intent = new Intent(getApplicationContext(), DeleteAcountOprational.class);
                startActivity(intent);
                finish();
            }
        });
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "User Logged Out...", Toast.LENGTH_SHORT).show();
                auth.signOut();
                Intent intent = new Intent(getApplicationContext(), SignInOprationalActivity.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeOprational.class);
                startActivity(intent);
                finish();
            }
        });




    }
}
