package com.example.itassetmanagementptbukitasam.Account;

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
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.itassetmanagementptbukitasam.OtherMenu.ForgotActivity;
import com.example.itassetmanagementptbukitasam.OtherMenu.ScondStartActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.User.HomeUserActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginUserActivity extends AppCompatActivity {
    private EditText editTextEmail, editTextPassword;
    private ProgressBar progressBar;
    private Button login;
    private FirebaseAuth auth;
    private TextView register_user;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final String TAG = "LoginUserActivity";
    private ImageView viewPassword;
    private TextView forget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);


        swipeToRefresh();

        editTextEmail = findViewById(R.id.loginEmailUser);
        editTextPassword = findViewById(R.id.loginPasswordUser);
        login = findViewById(R.id.button_login_user);
        progressBar = findViewById(R.id.progressBar);
        viewPassword = findViewById(R.id.ViewPasswordLogin);
        forget = findViewById(R.id.forgot_user);
        viewPassword.setImageResource(R.drawable.ic_see_visibility);

        auth = FirebaseAuth.getInstance();



        viewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextPassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    viewPassword.setImageResource(R.drawable.ic_see_visibility);
                    viewPassword.setColorFilter(R.color.blue_light);
                }else {
                    editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    viewPassword.setImageResource(R.drawable.ic_see);
                    viewPassword.setColorFilter(R.color.blue_light);
                }
            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginUserActivity.this, ForgotActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textEmail = editTextEmail.getText().toString();
                String textPassword = editTextPassword.getText().toString();


                if (TextUtils.isEmpty(textEmail)){
                    Toast.makeText(LoginUserActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    editTextEmail.setError("email is required");
                    editTextEmail.requestFocus();
                }else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(LoginUserActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    editTextEmail.setError("Email is required");
                    editTextEmail.requestFocus();
                }else if (TextUtils.isEmpty(textPassword)) {
                    Toast.makeText(LoginUserActivity.this, "Please enter Password", Toast.LENGTH_SHORT).show();
                    editTextPassword.setError("password is required");
                    editTextPassword.requestFocus();
                }else {
                    progressBar.setVisibility(View.GONE);
                    loginUser(textEmail, textPassword);
                }
            }
        });



    }

    private void loginUser(String Email, String Password) {
        auth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(LoginUserActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                        Toast.makeText(LoginUserActivity.this, "You are logged in now !!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginUserActivity.this, HomeUserActivity.class);
                        startActivity(intent);

                }else {
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthInvalidUserException e){
                        editTextEmail.setError("User does not exits or is no longer valid. Please try again");
                        editTextEmail.requestFocus();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        editTextEmail.setError("Invalid credential.Kindly check and re-enter");
                        editTextEmail.requestFocus();
                    }catch (Exception e){
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(LoginUserActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }
                progressBar.setVisibility(View.GONE);

            }
        });
    }

//    private void showAlertDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(LoginUserActivity.this);
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
            Toast.makeText(LoginUserActivity.this, "Already logged In", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginUserActivity.this, HomeUserActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(LoginUserActivity.this, "You Can Login Now !!", Toast.LENGTH_SHORT).show();
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
    public void setback_user(View view)
    {
        Intent intent=new Intent(LoginUserActivity.this, ScondStartActivity.class);
        startActivity(intent);
        finish();
    }

}

