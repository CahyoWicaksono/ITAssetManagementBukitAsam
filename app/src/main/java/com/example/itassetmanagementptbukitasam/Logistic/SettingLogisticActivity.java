package com.example.itassetmanagementptbukitasam.Logistic;

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
import com.example.itassetmanagementptbukitasam.Laboratory.DeleteAcountLaboratory;
import com.example.itassetmanagementptbukitasam.Laboratory.LabHome;
import com.example.itassetmanagementptbukitasam.Laboratory.SignInLabActivity;
import com.example.itassetmanagementptbukitasam.Laboratory.UpdateProfileLabActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.google.firebase.auth.FirebaseAuth;

public class SettingLogisticActivity extends AppCompatActivity {

    TextView updateprofile, updateemail, updatepassword, deleteAccount, logOut;
    private FirebaseAuth auth;
    ImageView back;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_app_logistic);

        updateprofile = findViewById(R.id.updateprofile_logistic);
        updateemail = findViewById(R.id.change_account_logistic);
        updatepassword = findViewById(R.id.change_password_logistic);
        deleteAccount = findViewById(R.id.delete_account_logistic);
        logOut = findViewById(R.id.log_out_logistic);

        back = findViewById(R.id.back_account_logistic);


        auth = FirebaseAuth.getInstance();
        updateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UpdateProfileLogisticActivity.class);
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
                Intent intent = new Intent(getApplicationContext(), DeleteAcountLogistic.class);
                startActivity(intent);
                finish();
            }
        });
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "User Logged Out...", Toast.LENGTH_SHORT).show();
                auth.signOut();
                Intent intent = new Intent(getApplicationContext(), SignInLogisticActivity.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeLogisticActivity.class);
                startActivity(intent);
                finish();
            }
        });




    }
}
