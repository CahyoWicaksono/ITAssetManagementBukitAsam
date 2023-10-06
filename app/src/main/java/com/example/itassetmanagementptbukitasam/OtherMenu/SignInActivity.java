package com.example.itassetmanagementptbukitasam.OtherMenu;

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
import com.example.itassetmanagementptbukitasam.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {
    private EditText editTextEmailAdmin, editTextPasswordAdmin;
    private ProgressBar progressBar;
    private Button login;
    private FirebaseAuth auth;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final String TAG = "SignInActivity";
    private ImageView viewPassword;
    private TextView forgot;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        swipeToRefresh();

        editTextEmailAdmin = findViewById(R.id.editText_login_email);
        editTextPasswordAdmin = findViewById(R.id.loginPasswordAdmin);
        login = findViewById(R.id.button_loginAdmin);
        progressBar = findViewById(R.id.progress_Bar_login_admin);
        viewPassword = findViewById(R.id.imageView_show_hide_pwd);
        forgot = findViewById(R.id.forgot_password);
        viewPassword.setImageResource(R.drawable.ic_see_visibility);

        auth = FirebaseAuth.getInstance();


        viewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextPasswordAdmin.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    editTextPasswordAdmin.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    viewPassword.setImageResource(R.drawable.ic_see_visibility);
                    viewPassword.setColorFilter(R.color.blue_light);
                }else {
                    editTextPasswordAdmin.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    viewPassword.setImageResource(R.drawable.ic_see);
                    viewPassword.setColorFilter(R.color.blue_light);
                }
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, ForgotActivity.class));
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textEmail = editTextEmailAdmin.getText().toString();
                String textPassword = editTextPasswordAdmin.getText().toString();
                if (TextUtils.isEmpty(textEmail)){
                    Toast.makeText(SignInActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    editTextEmailAdmin.setError("email is required");
                    editTextEmailAdmin.requestFocus();
                }else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(SignInActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    editTextEmailAdmin.setError("Email is required");
                    editTextEmailAdmin.requestFocus();
                }else if (TextUtils.isEmpty(textPassword)) {
                    Toast.makeText(SignInActivity.this, "Please enter Password", Toast.LENGTH_SHORT).show();
                    editTextPasswordAdmin.setError("password is required");
                    editTextPasswordAdmin.requestFocus();
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    loginAdmin(textEmail, textPassword);
                }
            }
        });

    }
    private void loginAdmin(String Email, String Password) {
        auth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                        Toast.makeText(SignInActivity.this, "Already logged In", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignInActivity.this, HomeAdminActivity.class));
                        finish();

                }else {
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthInvalidUserException e){
                        editTextEmailAdmin.setError("User does not exits or is no longer valid. Please try again");
                        editTextEmailAdmin.requestFocus();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        editTextEmailAdmin.setError("Invalid credential.Kindly check and re-enter");
                        editTextEmailAdmin.requestFocus();
                    }catch (Exception e){
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(SignInActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }
                progressBar.setVisibility(View.GONE);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser() != null){
            Toast.makeText(SignInActivity.this, "Already logged In", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SignInActivity.this, HomeAdminActivity.class));
            finish();
        }else {
            Toast.makeText(SignInActivity.this, "You Can Login Now !!", Toast.LENGTH_SHORT).show();
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
        Intent intent=new Intent(SignInActivity.this, ScondStartActivity.class);
        startActivity(intent);
        finish();
    }
}
