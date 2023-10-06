package com.example.itassetmanagementptbukitasam.Safety;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.itassetmanagementptbukitasam.Admin.HomeAdminActivity;
import com.example.itassetmanagementptbukitasam.Clinic.SignInClinicActivity;
import com.example.itassetmanagementptbukitasam.Humas.SignInHumasActivity;
import com.example.itassetmanagementptbukitasam.Laboratory.SignInLabActivity;
import com.example.itassetmanagementptbukitasam.Logistic.SignInLogisticActivity;
import com.example.itassetmanagementptbukitasam.Oprational.SignInOprationalActivity;
import com.example.itassetmanagementptbukitasam.OtherMenu.ForgotActivity;
import com.example.itassetmanagementptbukitasam.OtherMenu.ScondStartActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.SDM.SignInComplaintActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class SignInSafetyActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private EditText editTextEmailSafety, editTextPasswordSafety;
    private ProgressBar progressBar;
    private Button login;
    private FirebaseAuth auth;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final String TAG = "SignInSafetyActivity";
    private ImageView viewPassword;
    private TextView forgot;
    private Spinner divisi;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_k3l);

        swipeToRefresh();

        editTextEmailSafety = findViewById(R.id.editText_login_email_safety);
        editTextPasswordSafety = findViewById(R.id.loginPassword_safety);
        login = findViewById(R.id.button_login_safety);
        progressBar = findViewById(R.id.progress_Bar_login_safety);
        viewPassword = findViewById(R.id.imageView_show_hide_pwd);
        forgot = findViewById(R.id.forgot_password_safety);
        viewPassword.setImageResource(R.drawable.ic_see_visibility);

        divisi = findViewById(R.id.spinner_login2_safety);

        divisi.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add(0,"Select Your Division");
        categories.add("HRD");
        categories.add("Logistic");
        categories.add("Publick Relation");
        categories.add("Oprational");
        categories.add("Clinic");
        categories.add("Laboratory");
        categories.add("Safety");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        divisi.setAdapter(dataAdapter);

        auth = FirebaseAuth.getInstance();


        viewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextPasswordSafety.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    editTextPasswordSafety.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    viewPassword.setImageResource(R.drawable.ic_see_visibility);
                    viewPassword.setColorFilter(R.color.blue_light);
                }else {
                    editTextPasswordSafety.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    viewPassword.setImageResource(R.drawable.ic_see);
                    viewPassword.setColorFilter(R.color.blue_light);
                }
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInSafetyActivity.this, ForgotActivity.class));
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textEmail = editTextEmailSafety.getText().toString();
                String textPassword = editTextPasswordSafety.getText().toString();
                if (TextUtils.isEmpty(textEmail)){
                    Toast.makeText(SignInSafetyActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    editTextEmailSafety.setError("email is required");
                    editTextEmailSafety.requestFocus();
                }else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(SignInSafetyActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    editTextEmailSafety.setError("Email is required");
                    editTextEmailSafety.requestFocus();
                }else if (TextUtils.isEmpty(textPassword)) {
                    Toast.makeText(SignInSafetyActivity.this, "Please enter Password", Toast.LENGTH_SHORT).show();
                    editTextPasswordSafety.setError("password is required");
                    editTextPasswordSafety.requestFocus();
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    loginSafety(textEmail, textPassword);
                }
            }
        });

    }
    private void loginSafety(String Email, String Password) {
        auth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(SignInSafetyActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                        Toast.makeText(SignInSafetyActivity.this, "Already logged In", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignInSafetyActivity.this, HomeSafety.class));
                        finish();

                }else {
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthInvalidUserException e){
                        editTextEmailSafety.setError("User does not exits or is no longer valid. Please try again");
                        editTextEmailSafety.requestFocus();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        editTextEmailSafety.setError("Invalid credential.Kindly check and re-enter");
                        editTextEmailSafety.requestFocus();
                    }catch (Exception e){
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(SignInSafetyActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

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
            Toast.makeText(SignInSafetyActivity.this, "Already logged In", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SignInSafetyActivity.this, HomeSafety.class));
            finish();
        }else {
            Toast.makeText(SignInSafetyActivity.this, "You Can Login Now !!", Toast.LENGTH_SHORT).show();
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
        Intent intent=new Intent(SignInSafetyActivity.this, ScondStartActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getItemAtPosition(position).equals("Select Your Division")) {

        } else {
            String item = parent.getItemAtPosition(position).toString();
            Toast.makeText(parent.getContext(), "Selected" + item, Toast.LENGTH_SHORT).show();

            if (parent.getItemAtPosition(position).equals("HRD")) {
                Intent intent = new Intent(SignInSafetyActivity.this, SignInComplaintActivity.class);
                startActivity(intent);

            } else if (parent.getItemAtPosition(position).equals("Logistic")) {
                Intent intent = new Intent(SignInSafetyActivity.this, SignInLogisticActivity.class);
                startActivity(intent);

            } else if (parent.getItemAtPosition(position).equals("Publick Relation")) {
                Intent intent = new Intent(SignInSafetyActivity.this, SignInHumasActivity.class);
                startActivity(intent);

            } else if (parent.getItemAtPosition(position).equals("Oprational")) {
                Intent intent = new Intent(SignInSafetyActivity.this, SignInOprationalActivity.class);
                startActivity(intent);


            } else if (parent.getItemAtPosition(position).equals("Clinic")) {
                Intent intent = new Intent(SignInSafetyActivity.this, SignInClinicActivity.class);
                startActivity(intent);

            } else if (parent.getItemAtPosition(position).equals("Laboratory")) {
                Intent intent = new Intent(SignInSafetyActivity.this, SignInLabActivity.class);
                startActivity(intent);

            } else {
                Intent intent = new Intent(SignInSafetyActivity.this, SignInSafetyActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
