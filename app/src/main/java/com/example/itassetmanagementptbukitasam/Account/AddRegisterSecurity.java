package com.example.itassetmanagementptbukitasam.Account;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.itassetmanagementptbukitasam.OtherMenu.AccessActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.WriteAccountDetailsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AddRegisterSecurity extends AppCompatActivity {
    private EditText editTextRegisterFullnameAdmin,editTextRegisterEmailAdmin, editTextRegisterDateAdmin, editTextRegisterMobileAdmin,editTextRegisterAdress,
            editTextRegisterPwdAdmin,editTextRegisterConPwdAdmin;
    private ProgressBar progressBarAdmin;
    private RadioGroup radioGroupRegisterGenderAdmin;
    private RadioButton radioButtonRegisterGenderSelectedAdmin;
    private DatePickerDialog pickerDialogAdmin;
    private ImageView backRegisterAdmin;
    private SwipeRefreshLayout swipeRefreshLayoutAdmin;
    private static final String TAG = "AddRegisterAdmin";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_admin);

        Toast.makeText(AddRegisterSecurity.this, "You can register now", Toast.LENGTH_SHORT).show();

        swipeToRefresh();
        editTextRegisterFullnameAdmin = findViewById(R.id.editText_register_full_name_Admin);
        editTextRegisterEmailAdmin = findViewById(R.id.editText_register_email_Admin);
        editTextRegisterDateAdmin = findViewById(R.id.editText_register_dob_Admin);
        editTextRegisterMobileAdmin = findViewById(R.id.editText_register_mobile_Admin);
        editTextRegisterPwdAdmin = findViewById(R.id.editText_register_password_Admin);
        editTextRegisterConPwdAdmin = findViewById(R.id.editText_register_conpassword_Admin);
        backRegisterAdmin = findViewById(R.id.backRegister_Admin);

        progressBarAdmin = findViewById(R.id.progressBar_Admin);
        radioGroupRegisterGenderAdmin = findViewById(R.id.radio_group_register_gender_Admin);
        radioGroupRegisterGenderAdmin.clearCheck();

        backRegisterAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddRegisterSecurity.this, AccessActivity.class);
                startActivity(intent);
                finish();
            }
        });
        editTextRegisterDateAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                pickerDialogAdmin = new DatePickerDialog(AddRegisterSecurity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        editTextRegisterDateAdmin.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, day, month, year);
                pickerDialogAdmin.show();
            }
        });
        Button register = findViewById(R.id.button_register_Admin);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedGenderId = radioGroupRegisterGenderAdmin.getCheckedRadioButtonId();
                radioButtonRegisterGenderSelectedAdmin = findViewById(selectedGenderId);

                String textFullname = editTextRegisterFullnameAdmin.getText().toString();
                String textEmail = editTextRegisterEmailAdmin.getText().toString();
                String textDate = editTextRegisterDateAdmin.getText().toString();
                String textMobile = editTextRegisterMobileAdmin.getText().toString();
                String textPwd = editTextRegisterPwdAdmin.getText().toString();
                String textConPwd = editTextRegisterConPwdAdmin.getText().toString();
                String textGender;

//                String mobileRegex = "[6-9][0-9]{9}";
//                Matcher mobileMatcer;
//                Pattern mobilePattern = Pattern.compile(mobileRegex);
//                mobileMatcer = mobilePattern.matcher(textMobile);

                if (TextUtils.isEmpty(textFullname)) {
                    Toast.makeText(AddRegisterSecurity.this, "Please enter your fullname", Toast.LENGTH_SHORT).show();
                    editTextRegisterFullnameAdmin.setError("your fullname is required");
                    editTextRegisterFullnameAdmin.requestFocus();
                } else if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(AddRegisterSecurity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    editTextRegisterEmailAdmin.setError("email is required");
                    editTextRegisterEmailAdmin.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(AddRegisterSecurity.this, "Please re-enter your email", Toast.LENGTH_SHORT).show();
                    editTextRegisterEmailAdmin.setError("Email is required");
                    editTextRegisterEmailAdmin.requestFocus();
                } else if (TextUtils.isEmpty(textDate)) {
                    Toast.makeText(AddRegisterSecurity.this, "Please enter your Date of Birth", Toast.LENGTH_SHORT).show();
                    editTextRegisterDateAdmin.setError("Date of Birth is required");
                    editTextRegisterDateAdmin.requestFocus();
                } else if (radioGroupRegisterGenderAdmin.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(AddRegisterSecurity.this, "Please enter your gender", Toast.LENGTH_SHORT).show();
                    radioButtonRegisterGenderSelectedAdmin.setError("Gender is required");
                    radioButtonRegisterGenderSelectedAdmin.requestFocus();
                } else if (TextUtils.isEmpty(textMobile)) {
                    Toast.makeText(AddRegisterSecurity.this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
                    editTextRegisterMobileAdmin.setError("phone Number is required");
                    editTextRegisterMobileAdmin.requestFocus();
                } else if (TextUtils.isEmpty(textPwd)) {
                    Toast.makeText(AddRegisterSecurity.this, "Please enter your Password", Toast.LENGTH_SHORT).show();
                    editTextRegisterPwdAdmin.setError("Your Password is required");
                    editTextRegisterPwdAdmin.requestFocus();
                } else if (textPwd.length() < 6) {
                    Toast.makeText(AddRegisterSecurity.this, "Pasword should be at least 6 digit", Toast.LENGTH_SHORT).show();
                    editTextRegisterPwdAdmin.setError("Your password too weak");
                    editTextRegisterPwdAdmin.requestFocus();
                } else if (TextUtils.isEmpty(textConPwd)) {
                    Toast.makeText(AddRegisterSecurity.this, "Please confirm your Password", Toast.LENGTH_SHORT).show();
                    editTextRegisterConPwdAdmin.setError("Confirm Password is required");
                    editTextRegisterConPwdAdmin.requestFocus();
                } else if (!textPwd.equals(textConPwd)) {
                    Toast.makeText(AddRegisterSecurity.this, "Please same password", Toast.LENGTH_SHORT).show();
                    editTextRegisterConPwdAdmin.setError("Confirm Password is required");
                    editTextRegisterConPwdAdmin.requestFocus();
                    editTextRegisterPwdAdmin.clearComposingText();
                    editTextRegisterConPwdAdmin.clearComposingText();
                } else {
                    textGender = radioButtonRegisterGenderSelectedAdmin.getText().toString();
                    progressBarAdmin.setVisibility(View.VISIBLE);
                    registerAdmin(textFullname, textEmail, textDate, textPwd, textConPwd, textGender, textMobile);
                    ClearAll();
                }
            }
        });
    }
    private void registerAdmin(String textFullname, String textEmail, String textDate, String textPwd, String textConPwd, String textGender, String textMobile) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(textEmail, textPwd).addOnCompleteListener(AddRegisterSecurity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser = auth.getCurrentUser();

                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(textFullname).build();
                    firebaseUser.updateProfile(profileChangeRequest);

                    WriteAccountDetailsModel writeAccountDetailsModel = new WriteAccountDetailsModel(textGender,textDate,textMobile);

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Register Admin");

                    databaseReference.child(firebaseUser.getUid()).setValue(writeAccountDetailsModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                firebaseUser.sendEmailVerification();
//                                showAlertDialog();
                                Toast.makeText(AddRegisterSecurity.this, "Create Access is Succesfully", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(AddRegisterSecurity.this, "Register Failed !!!,Please try again", Toast.LENGTH_SHORT).show();
                            }
                            progressBarAdmin.setVisibility(View.GONE);
                        }
                    });


                }else {
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        editTextRegisterPwdAdmin.setError("Your password is too weak. Kindly use a mix of alphabets, number or special character");
                        editTextRegisterPwdAdmin.requestFocus();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        editTextRegisterPwdAdmin.setError("Your email is invalid or already in use. Kindly re-enter");
                        editTextRegisterPwdAdmin.requestFocus();
                    }catch (FirebaseAuthUserCollisionException e){
                        editTextRegisterPwdAdmin.setError("User is already registered whit this email.");
                        editTextRegisterPwdAdmin.requestFocus();
                    }catch (Exception e){
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(AddRegisterSecurity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                    progressBarAdmin.setVisibility(View.GONE);
                }
            }
        });
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
//    private void showAlertDialog() {
//        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AddRegisterAdmin.this);
//        builder.setView(R.layout.activity_order__generate);
//
//        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int which) {
//                Intent intent = new Intent(AddRegisterAdmin.this, AccessActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//    }
    private void swipeToRefresh() {
        swipeRefreshLayoutAdmin = findViewById(R.id.swipeContainer);

        swipeRefreshLayoutAdmin.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startActivity(getIntent());
                finish();
                overridePendingTransition(0,0);
                swipeRefreshLayoutAdmin.setRefreshing(false);
            }
        });
        swipeRefreshLayoutAdmin.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_blue_light
                ,android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }
    private void ClearAll()
    {
        editTextRegisterFullnameAdmin.setText("");
        editTextRegisterEmailAdmin.setText("");
        editTextRegisterDateAdmin.setText("");
        editTextRegisterMobileAdmin.setText("");
        editTextRegisterPwdAdmin.setText("");
        editTextRegisterConPwdAdmin.setText("");
    }



}

