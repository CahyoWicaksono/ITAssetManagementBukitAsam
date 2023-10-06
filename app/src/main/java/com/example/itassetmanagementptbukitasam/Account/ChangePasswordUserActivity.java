package com.example.itassetmanagementptbukitasam.Account;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.itassetmanagementptbukitasam.User.ProfileUserActivity;
import com.example.itassetmanagementptbukitasam.User.HomeUserActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordUserActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText editTextPwdCur, editTextPwdNew, editTextConfirmNew;
    private TextView textViewAuthenticated;
    private Button buttonChangePwd, buttonReAuthenticate;
    private ProgressBar progressBar;
    private String userPwdCur;
    private ImageView backEdit;
    private BottomNavigationView bottomNavigationViePwdUs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        editTextPwdCur  = findViewById(R.id.editText_change_pwd_current);
        editTextPwdNew  = findViewById(R.id.editText_change_pwd_new);
        editTextConfirmNew  = findViewById(R.id.editText_change_con_new);
        textViewAuthenticated = findViewById(R.id.textView_change_pwd_authenticated);
        progressBar = findViewById(R.id.progressBar);

        buttonChangePwd = findViewById(R.id.button_change_pwd);
        buttonReAuthenticate = findViewById(R.id.button_change_pwd_authenticate);

        backEdit = findViewById(R.id.back_password_user);

        editTextPwdNew.setEnabled(false);
        editTextConfirmNew.setEnabled(false);
        buttonChangePwd.setEnabled(false);

        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();


        backEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HomeUserActivity.class));
                finish();
            }
        });


        if (firebaseUser.equals("")){
            Toast.makeText(ChangePasswordUserActivity.this, "Something went wrong!! User's details available", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ChangePasswordUserActivity.this, ProfileUserActivity.class);
            startActivity(intent);
            finish();
        }else {
            buttonAuthenticateAdmin(firebaseUser);
        }


    }

    private void buttonAuthenticateAdmin(FirebaseUser firebaseUser) {
        buttonReAuthenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userPwdCur = editTextPwdCur.getText().toString();

                if (TextUtils.isEmpty(userPwdCur)){
                    Toast.makeText(ChangePasswordUserActivity.this, "Password is needed", Toast.LENGTH_SHORT).show();
                    editTextPwdCur.setError("Please enter your curent password to authenticate");
                    editTextPwdCur.requestFocus();
                }else {
                    progressBar.setVisibility(View.VISIBLE);

                    AuthCredential credential = EmailAuthProvider.getCredential(firebaseUser.getEmail(), userPwdCur);
                    firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                progressBar.setVisibility(View.GONE);
                                editTextPwdCur.setEnabled(false);
                                editTextPwdNew.setEnabled(true);
                                editTextConfirmNew.setEnabled(true);

                                buttonReAuthenticate.setEnabled(false);
                                buttonChangePwd.setEnabled(true);


                                textViewAuthenticated.setText("You are authenticated/verified." + "You can change password now!!");
                                Toast.makeText(ChangePasswordUserActivity.this, "You are authenticated/verified." + "You can change password now!!", Toast.LENGTH_SHORT).show();
                                buttonChangePwd.setBackgroundTintList(ContextCompat.getColorStateList(ChangePasswordUserActivity.this, R.color.green));

                                buttonChangePwd.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        chagePwd(firebaseUser);
                                    }
                                });
                            }else {
                                try {
                                    throw task.getException();
                                }catch (Exception e){
                                    Toast.makeText(ChangePasswordUserActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }

    private void chagePwd(FirebaseUser firebaseUser) {
        String userPwdNew = editTextPwdNew.getText().toString();
        String userPwdConfirm = editTextConfirmNew.getText().toString();


        if (TextUtils.isEmpty(userPwdNew)){
            Toast.makeText(ChangePasswordUserActivity.this, "New Password is needed", Toast.LENGTH_SHORT).show();
            editTextPwdNew.setError("Please enter your new password");
            editTextPwdNew.requestFocus();
        }else if (TextUtils.isEmpty(userPwdConfirm)){
            Toast.makeText(ChangePasswordUserActivity.this, "Please confirm your password", Toast.LENGTH_SHORT).show();
            editTextConfirmNew.setError("Please re-enter your new password");
            editTextConfirmNew.requestFocus();
        }else if (!userPwdConfirm.matches(userPwdConfirm)){
            Toast.makeText(ChangePasswordUserActivity.this, "Password did not match", Toast.LENGTH_SHORT).show();
            editTextConfirmNew.setError("Please re-enter your same password");
            editTextConfirmNew.requestFocus();
        }else if (userPwdNew.matches(userPwdNew)){
            Toast.makeText(ChangePasswordUserActivity.this, "New Password cannoe be same as old password", Toast.LENGTH_SHORT).show();
            editTextPwdNew.setError("Please enter a new password");
            editTextPwdNew.requestFocus();
        }else {
            progressBar.setVisibility(View.VISIBLE);

            firebaseUser.updatePassword(userPwdNew).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(ChangePasswordUserActivity.this, "Password has been changed", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ChangePasswordUserActivity.this, ProfileUserActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        try {
                            throw task.getException();
                        }catch (Exception e){
                            Toast.makeText(ChangePasswordUserActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    progressBar.setVisibility(View.GONE);
                }
            });
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
