package com.example.itassetmanagementptbukitasam.Manager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.itassetmanagementptbukitasam.Admin.HomeAdminActivity;
import com.example.itassetmanagementptbukitasam.OtherMenu.ForgotActivity;
import com.example.itassetmanagementptbukitasam.OtherMenu.ScondStartActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class SignInManagerActivity extends AppCompatActivity {
    private EditText editTextEmailManager, editTextPasswordManager;
    private ProgressBar progressBar;
    private Button login;
    private FirebaseAuth auth;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final String TAG = "SignInManagerActivity";
    private ImageView viewPassword;
    private TextView forgot;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_manager);

        swipeToRefresh();

        editTextEmailManager = findViewById(R.id.editText_login_email_manager);
        editTextPasswordManager = findViewById(R.id.loginPassword_manager);
        login = findViewById(R.id.button_login_manager);
        progressBar = findViewById(R.id.progress_Bar_login_manager);
        viewPassword = findViewById(R.id.imageView_show_hide_pwd);
        forgot = findViewById(R.id.forgot_password_manager);
        viewPassword.setImageResource(R.drawable.ic_see_visibility);

        auth = FirebaseAuth.getInstance();


        viewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextPasswordManager.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    editTextPasswordManager.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    viewPassword.setImageResource(R.drawable.ic_see_visibility);
                    viewPassword.setColorFilter(R.color.blue_light);
                }else {
                    editTextPasswordManager.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    viewPassword.setImageResource(R.drawable.ic_see);
                    viewPassword.setColorFilter(R.color.blue_light);
                }
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInManagerActivity.this, ForgotActivity.class));
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textEmail = editTextEmailManager.getText().toString();
                String textPassword = editTextPasswordManager.getText().toString();
                if (TextUtils.isEmpty(textEmail)){
                    Toast.makeText(SignInManagerActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    editTextEmailManager.setError("email is required");
                    editTextEmailManager.requestFocus();
                }else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(SignInManagerActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    editTextEmailManager.setError("Email is required");
                    editTextEmailManager.requestFocus();
                }else if (TextUtils.isEmpty(textPassword)) {
                    Toast.makeText(SignInManagerActivity.this, "Please enter Password", Toast.LENGTH_SHORT).show();
                    editTextPasswordManager.setError("password is required");
                    editTextPasswordManager.requestFocus();
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    loginManager(textEmail, textPassword);
                }
            }
        });

    }
    private void loginManager(String Email, String Password) {
        auth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(SignInManagerActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                        Toast.makeText(SignInManagerActivity.this, "Already logged In", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignInManagerActivity.this, HomeManager.class));
                        finish();

                }else {
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthInvalidUserException e){
                        editTextEmailManager.setError("User does not exits or is no longer valid. Please try again");
                        editTextEmailManager.requestFocus();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        editTextEmailManager.setError("Invalid credential.Kindly check and re-enter");
                        editTextEmailManager.requestFocus();
                    }catch (Exception e){
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(SignInManagerActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }
                progressBar.setVisibility(View.GONE);

            }
        });
    }
//    private void showAlertDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
//        builder.setTitle("Email not verified").setIcon(R.drawable.ic_damage);
//        builder.setMessage("Please verift your email now.You can not login without email verification");
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


    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser() != null){
            Toast.makeText(SignInManagerActivity.this, "Already logged In", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SignInManagerActivity.this, HomeManager.class));
            finish();
        }else {
            Toast.makeText(SignInManagerActivity.this, "You Can Login Now !!", Toast.LENGTH_SHORT).show();
        }
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
    public void setback(View view)
    {
        Intent intent=new Intent(SignInManagerActivity.this, ScondStartActivity.class);
        startActivity(intent);
        finish();
    }
}
