package com.example.itassetmanagementptbukitasam.Account;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
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

import com.example.itassetmanagementptbukitasam.Admin.HomeAdminActivity;
import com.example.itassetmanagementptbukitasam.Admin.ProfileAdminActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdateEmailActivity extends AppCompatActivity {


    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private ProgressBar progressBar;
    private TextView textViewAutenticated;
    private String userOldEmail, userNewEmail, userPwd;
    private Button buttonUpdateEmail;
    private EditText editTextNewEmail,editTextPwd;
    private ImageView backUpEmail;
    private BottomNavigationView bottomNavigationView_update_email;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_email);

         progressBar = findViewById(R.id.progressBar);
         editTextPwd = findViewById(R.id.editText_update_email_verify_password);
         editTextNewEmail = findViewById(R.id.editText_update_email_new);
         textViewAutenticated = findViewById(R.id.textView_update_email_authenticated);
         backUpEmail = findViewById(R.id.backupEmail);

         buttonUpdateEmail = findViewById(R.id.button_update_email);

         buttonUpdateEmail.setEnabled(false);
         editTextNewEmail.setEnabled(false);

         auth = FirebaseAuth.getInstance();
         firebaseUser = auth.getCurrentUser();

        backUpEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UpdateEmailActivity.this, ProfileAdminActivity.class);
                startActivity(intent);
                finish();
            }
        });

         if (firebaseUser.equals("")){
             Toast.makeText(UpdateEmailActivity.this, "Something went wrong! User's details not available", Toast.LENGTH_SHORT).show();
         }else {
             userAutenticated(firebaseUser);
         }
        backUpEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeAdminActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void userAutenticated(FirebaseUser firebaseUser) {
        Button verifyUser = findViewById(R.id.button_authenticate_user);
        verifyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userPwd = editTextPwd.getText().toString();

                if (TextUtils.isEmpty(userPwd)){
                    Toast.makeText(UpdateEmailActivity.this, "Password is needed to continue", Toast.LENGTH_SHORT).show();
                    editTextPwd.setError("Please enter yout password for authentication");
                    editTextPwd.requestFocus();
                }else {
                   progressBar.setVisibility(View.VISIBLE);

                    AuthCredential authCredential = EmailAuthProvider.getCredential(userOldEmail, userPwd);
                    firebaseUser.reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(UpdateEmailActivity.this, "Password has been verified" +
                                        "You can update email now.", Toast.LENGTH_SHORT).show();

                                textViewAutenticated.setText("You are authenticated. You can update your email now");

                                editTextNewEmail.setEnabled(true);
                                editTextPwd.setEnabled(false);
                                verifyUser.setEnabled(false);
                                buttonUpdateEmail.setEnabled(true);

                                buttonUpdateEmail.setBackgroundTintList(ContextCompat.getColorStateList(UpdateEmailActivity.this,
                                        R.color.green));

                                buttonUpdateEmail.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (TextUtils.isEmpty(userNewEmail)){
                                            Toast.makeText(UpdateEmailActivity.this, "Update email is required", Toast.LENGTH_SHORT).show();
                                            editTextNewEmail.setError("Please enter new email");
                                            editTextNewEmail.requestFocus();
                                        }else if (!Patterns.EMAIL_ADDRESS.matcher(userNewEmail).matches()){
                                            Toast.makeText(UpdateEmailActivity.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                                            editTextNewEmail.setError("Please provide valid email");
                                            editTextNewEmail.requestFocus();
                                        }else if (userOldEmail.matches(userNewEmail)){
                                            Toast.makeText(UpdateEmailActivity.this, "New password cannot be same old email", Toast.LENGTH_SHORT).show();
                                            editTextNewEmail.setError("Please enter new email");
                                            editTextNewEmail.requestFocus();
                                        }else {
                                            progressBar.setVisibility(View.VISIBLE);
                                            UpdateEmail(firebaseUser);
                                        }
                                    }
                                });

                            }else {
                                try {
                                    throw task.getException();
                                }catch (Exception e){
                                    Toast.makeText(UpdateEmailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                    });
                }

            }
        });
    }

    private void UpdateEmail(FirebaseUser firebaseUser) {
        firebaseUser.updateEmail(userNewEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isComplete()){
                    firebaseUser.sendEmailVerification();

                    Toast.makeText(UpdateEmailActivity.this, "Email has been updated. Please verify your new email", Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(UpdateEmailActivity.this, ProfileAdminActivity.class);
                    startActivity(intent);
                    finish();

                }else {
                    try {
                        throw task.getException();
                    }catch (Exception e){
                        Toast.makeText(UpdateEmailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
