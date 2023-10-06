package com.example.itassetmanagementptbukitasam.Safety;

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

public class AddRegisterK3L extends AppCompatActivity {
    private EditText editTextRegisterFullnameSafety,editTextRegisterEmailSafety, editTextRegisterDateSafety, editTextRegisterMobileSafety,editTextRegisterAdress,
            editTextRegisterPwdSafety,editTextRegisterConPwdSafety;
    private ProgressBar progressBarSafety;
    private RadioGroup radioGroupRegisterGenderSafety;
    private RadioButton radioButtonRegisterGenderSelectedSafety;
    private DatePickerDialog pickerDialogSafety;
    private ImageView backRegisterSafety;
    private SwipeRefreshLayout swipeRefreshLayoutSafety;
    private static final String TAG = "AddRegisterK3L";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_k3l);

        Toast.makeText(AddRegisterK3L.this, "You can register now", Toast.LENGTH_SHORT).show();

        swipeToRefresh();
        editTextRegisterFullnameSafety = findViewById(R.id.editText_register_full_name_safety);
        editTextRegisterEmailSafety = findViewById(R.id.editText_register_email_safety);
        editTextRegisterDateSafety = findViewById(R.id.editText_register_dob_safety);
        editTextRegisterMobileSafety = findViewById(R.id.editText_register_mobile_safety);
        editTextRegisterPwdSafety = findViewById(R.id.editText_register_password_safety);
        editTextRegisterConPwdSafety = findViewById(R.id.editText_register_conpassword_safety);
        backRegisterSafety = findViewById(R.id.backRegister_safety);

        progressBarSafety = findViewById(R.id.progressBar_safety);
        radioGroupRegisterGenderSafety = findViewById(R.id.radio_group_register_gender_safety);
        radioGroupRegisterGenderSafety.clearCheck();

        backRegisterSafety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddRegisterK3L.this, AccessActivity.class);
                startActivity(intent);
                finish();
            }
        });
        editTextRegisterDateSafety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                pickerDialogSafety = new DatePickerDialog(AddRegisterK3L.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        editTextRegisterDateSafety.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, day, month, year);
                pickerDialogSafety.show();
            }
        });
        Button register = findViewById(R.id.button_register_safety);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedGenderId = radioGroupRegisterGenderSafety.getCheckedRadioButtonId();
                radioButtonRegisterGenderSelectedSafety = findViewById(selectedGenderId);

                String textFullname = editTextRegisterFullnameSafety.getText().toString();
                String textEmail = editTextRegisterEmailSafety.getText().toString();
                String textDate = editTextRegisterDateSafety.getText().toString();
                String textMobile = editTextRegisterMobileSafety.getText().toString();
                String textPwd = editTextRegisterPwdSafety.getText().toString();
                String textConPwd = editTextRegisterConPwdSafety.getText().toString();
                String textGender;

//                String mobileRegex = "[6-9][0-9]{9}";
//                Matcher mobileMatcer;
//                Pattern mobilePattern = Pattern.compile(mobileRegex);
//                mobileMatcer = mobilePattern.matcher(textMobile);

                if (TextUtils.isEmpty(textFullname)) {
                    Toast.makeText(AddRegisterK3L.this, "Please enter your fullname", Toast.LENGTH_SHORT).show();
                    editTextRegisterFullnameSafety.setError("your fullname is required");
                    editTextRegisterFullnameSafety.requestFocus();
                } else if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(AddRegisterK3L.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    editTextRegisterEmailSafety.setError("email is required");
                    editTextRegisterEmailSafety.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(AddRegisterK3L.this, "Please re-enter your email", Toast.LENGTH_SHORT).show();
                    editTextRegisterEmailSafety.setError("Email is required");
                    editTextRegisterEmailSafety.requestFocus();
                } else if (TextUtils.isEmpty(textDate)) {
                    Toast.makeText(AddRegisterK3L.this, "Please enter your Date of Birth", Toast.LENGTH_SHORT).show();
                    editTextRegisterDateSafety.setError("Date of Birth is required");
                    editTextRegisterDateSafety.requestFocus();
                } else if (radioGroupRegisterGenderSafety.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(AddRegisterK3L.this, "Please enter your gender", Toast.LENGTH_SHORT).show();
                    radioButtonRegisterGenderSelectedSafety.setError("Gender is required");
                    radioButtonRegisterGenderSelectedSafety.requestFocus();
                } else if (TextUtils.isEmpty(textMobile)) {
                    Toast.makeText(AddRegisterK3L.this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
                    editTextRegisterMobileSafety.setError("phone Number is required");
                    editTextRegisterMobileSafety.requestFocus();
                } else if (TextUtils.isEmpty(textPwd)) {
                    Toast.makeText(AddRegisterK3L.this, "Please enter your Password", Toast.LENGTH_SHORT).show();
                    editTextRegisterPwdSafety.setError("Your Password is required");
                    editTextRegisterPwdSafety.requestFocus();
                } else if (textPwd.length() < 6) {
                    Toast.makeText(AddRegisterK3L.this, "Pasword should be at least 6 digit", Toast.LENGTH_SHORT).show();
                    editTextRegisterPwdSafety.setError("Your password too weak");
                    editTextRegisterPwdSafety.requestFocus();
                } else if (TextUtils.isEmpty(textConPwd)) {
                    Toast.makeText(AddRegisterK3L.this, "Please confirm your Password", Toast.LENGTH_SHORT).show();
                    editTextRegisterConPwdSafety.setError("Confirm Password is required");
                    editTextRegisterConPwdSafety.requestFocus();
                } else if (!textPwd.equals(textConPwd)) {
                    Toast.makeText(AddRegisterK3L.this, "Please same password", Toast.LENGTH_SHORT).show();
                    editTextRegisterConPwdSafety.setError("Confirm Password is required");
                    editTextRegisterConPwdSafety.requestFocus();
                    editTextRegisterPwdSafety.clearComposingText();
                    editTextRegisterConPwdSafety.clearComposingText();
                } else {
                    textGender = radioButtonRegisterGenderSelectedSafety.getText().toString();
                    progressBarSafety.setVisibility(View.VISIBLE);
                    registerSafety(textFullname, textEmail, textDate, textPwd, textConPwd, textGender, textMobile);
                }
            }
        });
    }
    private void registerSafety(String textFullname, String textEmail, String textDate, String textPwd, String textConPwd, String textGender, String textMobile) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(textEmail, textPwd).addOnCompleteListener(AddRegisterK3L.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser = auth.getCurrentUser();

                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(textFullname).build();
                    firebaseUser.updateProfile(profileChangeRequest);

                    WriteAccountDetailsModel writeAccountDetailsModel = new WriteAccountDetailsModel(textGender,textDate,textMobile);

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Register_Safety");

                    databaseReference.child(firebaseUser.getUid()).setValue(writeAccountDetailsModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                firebaseUser.sendEmailVerification();
//                                showAlertDialog();
                                Toast.makeText(AddRegisterK3L.this, "Create Access is Succesfully", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(AddRegisterK3L.this, "Register Failed !!!,Please try again", Toast.LENGTH_SHORT).show();
                            }
                            progressBarSafety.setVisibility(View.GONE);
                        }
                    });


                }else {
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        editTextRegisterPwdSafety.setError("Your password is too weak. Kindly use a mix of alphabets, number or special character");
                        editTextRegisterPwdSafety.requestFocus();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        editTextRegisterPwdSafety.setError("Your email is invalid or already in use. Kindly re-enter");
                        editTextRegisterPwdSafety.requestFocus();
                    }catch (FirebaseAuthUserCollisionException e){
                        editTextRegisterPwdSafety.setError("User is already registered whit this email.");
                        editTextRegisterPwdSafety.requestFocus();
                    }catch (Exception e){
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(AddRegisterK3L.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                    progressBarSafety.setVisibility(View.GONE);
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
        swipeRefreshLayoutSafety = findViewById(R.id.swipeContainer);

        swipeRefreshLayoutSafety.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startActivity(getIntent());
                finish();
                overridePendingTransition(0,0);
                swipeRefreshLayoutSafety.setRefreshing(false);
            }
        });
        swipeRefreshLayoutSafety.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_blue_light
                ,android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }

}

