package com.example.itassetmanagementptbukitasam.OtherMenu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.itassetmanagementptbukitasam.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class ForgotActivity extends AppCompatActivity {
    private Button send;
    private EditText editTextResetPwd;
    private ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    private ImageView back;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final String TAG = "ForgotActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        swipeToRefresh();
        send = findViewById(R.id.button_password_reset);
        progressBar = findViewById(R.id.progressBar);
        editTextResetPwd = findViewById(R.id.editText_password_reset_email);
        back = findViewById(R.id.backForgot);
        firebaseAuth = FirebaseAuth.getInstance();



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotActivity.this, ScondStartActivity.class);
                startActivity(intent);
                finish();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextResetPwd.getText().toString();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(ForgotActivity.this, "Please enter you registered email !!", Toast.LENGTH_SHORT).show();
                    editTextResetPwd.setError("Email is required");
                    editTextResetPwd.requestFocus();
                }else  if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(ForgotActivity.this, "Please enter valid email !!", Toast.LENGTH_SHORT).show();
                    editTextResetPwd.setError("Valid email is required");
                    editTextResetPwd.requestFocus();
                }else {
                    progressBar.setVisibility(view.VISIBLE);
                    resetpassword(email);
                }
            }
        });
    }
    private void resetpassword(String email) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ForgotActivity.this, "Please check your email for password reset link"
                            , Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), ScondStartActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                            | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }else {
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthInvalidUserException e){
                        editTextResetPwd.setText("User does not exits is no longer valid. Please register again !!");
                    }catch (Exception e){
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(ForgotActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                }
                progressBar.setVisibility(View.GONE);
            }
        });
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
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
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
}
