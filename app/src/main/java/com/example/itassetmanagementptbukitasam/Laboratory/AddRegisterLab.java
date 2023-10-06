package com.example.itassetmanagementptbukitasam.Laboratory;

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

public class AddRegisterLab extends AppCompatActivity {
    private EditText editTextRegisterFullnamelab,editTextRegisterEmaillab, editTextRegisterDatelab, editTextRegisterMobilelab,editTextRegisterAdress,
            editTextRegisterPwdlab,editTextRegisterConPwdlab;
    private ProgressBar progressBarlab;
    private RadioGroup radioGroupRegisterGenderlab;
    private RadioButton radioButtonRegisterGenderSelectedlab;
    private DatePickerDialog pickerDialoglab;
    private ImageView backRegisterlab;
    private SwipeRefreshLayout swipeRefreshLayoutlab;
    private static final String TAG = "AddRegisterLab";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_lab);

        Toast.makeText(AddRegisterLab.this, "You can register now", Toast.LENGTH_SHORT).show();

        swipeToRefresh();
        editTextRegisterFullnamelab = findViewById(R.id.editText_register_full_name_lab);
        editTextRegisterEmaillab = findViewById(R.id.editText_register_email_lab);
        editTextRegisterDatelab = findViewById(R.id.editText_register_dob_lab);
        editTextRegisterMobilelab = findViewById(R.id.editText_register_mobile_lab);
        editTextRegisterPwdlab = findViewById(R.id.editText_register_password_lab);
        editTextRegisterConPwdlab = findViewById(R.id.editText_register_conpassword_lab);
        backRegisterlab = findViewById(R.id.backRegister_lab);

        progressBarlab = findViewById(R.id.progressBar_lab);
        radioGroupRegisterGenderlab = findViewById(R.id.radio_group_register_gender_lab);
        radioGroupRegisterGenderlab.clearCheck();

        backRegisterlab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddRegisterLab.this, AccessActivity.class);
                startActivity(intent);
                finish();
            }
        });
        editTextRegisterDatelab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                pickerDialoglab = new DatePickerDialog(AddRegisterLab.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        editTextRegisterDatelab.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, day, month, year);
                pickerDialoglab.show();
            }
        });
        Button register = findViewById(R.id.button_register_lab);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedGenderId = radioGroupRegisterGenderlab.getCheckedRadioButtonId();
                radioButtonRegisterGenderSelectedlab = findViewById(selectedGenderId);

                String textFullname = editTextRegisterFullnamelab.getText().toString();
                String textEmail = editTextRegisterEmaillab.getText().toString();
                String textDate = editTextRegisterDatelab.getText().toString();
                String textMobile = editTextRegisterMobilelab.getText().toString();
                String textPwd = editTextRegisterPwdlab.getText().toString();
                String textConPwd = editTextRegisterConPwdlab.getText().toString();
                String textGender;

//                String mobileRegex = "[6-9][0-9]{9}";
//                Matcher mobileMatcer;
//                Pattern mobilePattern = Pattern.compile(mobileRegex);
//                mobileMatcer = mobilePattern.matcher(textMobile);

                if (TextUtils.isEmpty(textFullname)) {
                    Toast.makeText(AddRegisterLab.this, "Please enter your fullname", Toast.LENGTH_SHORT).show();
                    editTextRegisterFullnamelab.setError("your fullname is required");
                    editTextRegisterFullnamelab.requestFocus();
                } else if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(AddRegisterLab.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    editTextRegisterEmaillab.setError("email is required");
                    editTextRegisterEmaillab.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(AddRegisterLab.this, "Please re-enter your email", Toast.LENGTH_SHORT).show();
                    editTextRegisterEmaillab.setError("Email is required");
                    editTextRegisterEmaillab.requestFocus();
                } else if (TextUtils.isEmpty(textDate)) {
                    Toast.makeText(AddRegisterLab.this, "Please enter your Date of Birth", Toast.LENGTH_SHORT).show();
                    editTextRegisterDatelab.setError("Date of Birth is required");
                    editTextRegisterDatelab.requestFocus();
                } else if (radioGroupRegisterGenderlab.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(AddRegisterLab.this, "Please enter your gender", Toast.LENGTH_SHORT).show();
                    radioButtonRegisterGenderSelectedlab.setError("Gender is required");
                    radioButtonRegisterGenderSelectedlab.requestFocus();
                } else if (TextUtils.isEmpty(textMobile)) {
                    Toast.makeText(AddRegisterLab.this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
                    editTextRegisterMobilelab.setError("phone Number is required");
                    editTextRegisterMobilelab.requestFocus();
                } else if (TextUtils.isEmpty(textPwd)) {
                    Toast.makeText(AddRegisterLab.this, "Please enter your Password", Toast.LENGTH_SHORT).show();
                    editTextRegisterPwdlab.setError("Your Password is required");
                    editTextRegisterPwdlab.requestFocus();
                } else if (textPwd.length() < 6) {
                    Toast.makeText(AddRegisterLab.this, "Pasword should be at least 6 digit", Toast.LENGTH_SHORT).show();
                    editTextRegisterPwdlab.setError("Your password too weak");
                    editTextRegisterPwdlab.requestFocus();
                } else if (TextUtils.isEmpty(textConPwd)) {
                    Toast.makeText(AddRegisterLab.this, "Please confirm your Password", Toast.LENGTH_SHORT).show();
                    editTextRegisterConPwdlab.setError("Confirm Password is required");
                    editTextRegisterConPwdlab.requestFocus();
                } else if (!textPwd.equals(textConPwd)) {
                    Toast.makeText(AddRegisterLab.this, "Please same password", Toast.LENGTH_SHORT).show();
                    editTextRegisterConPwdlab.setError("Confirm Password is required");
                    editTextRegisterConPwdlab.requestFocus();
                    editTextRegisterPwdlab.clearComposingText();
                    editTextRegisterConPwdlab.clearComposingText();
                } else {
                    textGender = radioButtonRegisterGenderSelectedlab.getText().toString();
                    progressBarlab.setVisibility(View.VISIBLE);
                    registerLab(textFullname, textEmail, textDate, textPwd, textConPwd, textGender, textMobile);
                }
            }
        });
    }
    private void registerLab(String textFullname, String textEmail, String textDate, String textPwd, String textConPwd, String textGender, String textMobile) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(textEmail, textPwd).addOnCompleteListener(AddRegisterLab.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser = auth.getCurrentUser();

                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(textFullname).build();
                    firebaseUser.updateProfile(profileChangeRequest);

                    WriteAccountDetailsModel writeAccountDetailsModel = new WriteAccountDetailsModel(textGender,textDate,textMobile);

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Register_Laboratorium");

                    databaseReference.child(firebaseUser.getUid()).setValue(writeAccountDetailsModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                firebaseUser.sendEmailVerification();
//                                showAlertDialog();
                                Toast.makeText(AddRegisterLab.this, "Create Access is Succesfully", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(AddRegisterLab.this, "Register Failed !!!,Please try again", Toast.LENGTH_SHORT).show();
                            }
                            progressBarlab.setVisibility(View.GONE);
                        }
                    });


                }else {
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        editTextRegisterPwdlab.setError("Your password is too weak. Kindly use a mix of alphabets, number or special character");
                        editTextRegisterPwdlab.requestFocus();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        editTextRegisterPwdlab.setError("Your email is invalid or already in use. Kindly re-enter");
                        editTextRegisterPwdlab.requestFocus();
                    }catch (FirebaseAuthUserCollisionException e){
                        editTextRegisterPwdlab.setError("User is already registered whit this email.");
                        editTextRegisterPwdlab.requestFocus();
                    }catch (Exception e){
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(AddRegisterLab.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                    progressBarlab.setVisibility(View.GONE);
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
    private void swipeToRefresh() {
        swipeRefreshLayoutlab = findViewById(R.id.swipeContainer);

        swipeRefreshLayoutlab.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startActivity(getIntent());
                finish();
                overridePendingTransition(0,0);
                swipeRefreshLayoutlab.setRefreshing(false);
            }
        });
        swipeRefreshLayoutlab.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_blue_light
                ,android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }

}

