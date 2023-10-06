package com.example.itassetmanagementptbukitasam.Logistic;

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

public class AddRegisterLogistic extends AppCompatActivity {
    private EditText editTextRegisterFullnamelogistic,editTextRegisterEmaillogistic, editTextRegisterDatelogistic, editTextRegisterMobilelogistic,editTextRegisterAdress,
            editTextRegisterPwdlogistic,editTextRegisterConPwdlogistic;
    private ProgressBar progressBarlogistic;
    private RadioGroup radioGroupRegisterGenderlogistic;
    private RadioButton radioButtonRegisterGenderSelectedlogistic;
    private DatePickerDialog pickerDialoglogistic;
    private ImageView backRegisterlogistic;
    private SwipeRefreshLayout swipeRefreshLayoutlogistic;
    private static final String TAG = "AddRegisterLogistic";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_logistic);

        Toast.makeText(AddRegisterLogistic.this, "You can register now", Toast.LENGTH_SHORT).show();

        swipeToRefresh();
        editTextRegisterFullnamelogistic = findViewById(R.id.editText_register_full_name_logistic);
        editTextRegisterEmaillogistic = findViewById(R.id.editText_register_email_logistic);
        editTextRegisterDatelogistic = findViewById(R.id.editText_register_dob_logistic);
        editTextRegisterMobilelogistic = findViewById(R.id.editText_register_mobile_logistic);
        editTextRegisterPwdlogistic = findViewById(R.id.editText_register_password_logistic);
        editTextRegisterConPwdlogistic = findViewById(R.id.editText_register_conpassword_logistic);
        backRegisterlogistic = findViewById(R.id.backRegister_logistic);

        progressBarlogistic = findViewById(R.id.progressBar_logistic);
        radioGroupRegisterGenderlogistic = findViewById(R.id.radio_group_register_gender_logistic);
        radioGroupRegisterGenderlogistic.clearCheck();

        backRegisterlogistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddRegisterLogistic.this, AccessActivity.class);
                startActivity(intent);
                finish();
            }
        });
        editTextRegisterDatelogistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                pickerDialoglogistic = new DatePickerDialog(AddRegisterLogistic.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        editTextRegisterDatelogistic.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, day, month, year);
                pickerDialoglogistic.show();
            }
        });
        Button register = findViewById(R.id.button_register_logistic);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedGenderId = radioGroupRegisterGenderlogistic.getCheckedRadioButtonId();
                radioButtonRegisterGenderSelectedlogistic = findViewById(selectedGenderId);

                String textFullname = editTextRegisterFullnamelogistic.getText().toString();
                String textEmail = editTextRegisterEmaillogistic.getText().toString();
                String textDate = editTextRegisterDatelogistic.getText().toString();
                String textMobile = editTextRegisterMobilelogistic.getText().toString();
                String textPwd = editTextRegisterPwdlogistic.getText().toString();
                String textConPwd = editTextRegisterConPwdlogistic.getText().toString();
                String textGender;

//                String mobileRegex = "[6-9][0-9]{9}";
//                Matcher mobileMatcer;
//                Pattern mobilePattern = Pattern.compile(mobileRegex);
//                mobileMatcer = mobilePattern.matcher(textMobile);

                if (TextUtils.isEmpty(textFullname)) {
                    Toast.makeText(AddRegisterLogistic.this, "Please enter your fullname", Toast.LENGTH_SHORT).show();
                    editTextRegisterFullnamelogistic.setError("your fullname is required");
                    editTextRegisterFullnamelogistic.requestFocus();
                } else if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(AddRegisterLogistic.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    editTextRegisterEmaillogistic.setError("email is required");
                    editTextRegisterEmaillogistic.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(AddRegisterLogistic.this, "Please re-enter your email", Toast.LENGTH_SHORT).show();
                    editTextRegisterEmaillogistic.setError("Email is required");
                    editTextRegisterEmaillogistic.requestFocus();
                } else if (TextUtils.isEmpty(textDate)) {
                    Toast.makeText(AddRegisterLogistic.this, "Please enter your Date of Birth", Toast.LENGTH_SHORT).show();
                    editTextRegisterDatelogistic.setError("Date of Birth is required");
                    editTextRegisterDatelogistic.requestFocus();
                } else if (radioGroupRegisterGenderlogistic.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(AddRegisterLogistic.this, "Please enter your gender", Toast.LENGTH_SHORT).show();
                    radioButtonRegisterGenderSelectedlogistic.setError("Gender is required");
                    radioButtonRegisterGenderSelectedlogistic.requestFocus();
                } else if (TextUtils.isEmpty(textMobile)) {
                    Toast.makeText(AddRegisterLogistic.this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
                    editTextRegisterMobilelogistic.setError("phone Number is required");
                    editTextRegisterMobilelogistic.requestFocus();
                } else if (TextUtils.isEmpty(textPwd)) {
                    Toast.makeText(AddRegisterLogistic.this, "Please enter your Password", Toast.LENGTH_SHORT).show();
                    editTextRegisterPwdlogistic.setError("Your Password is required");
                    editTextRegisterPwdlogistic.requestFocus();
                } else if (textPwd.length() < 6) {
                    Toast.makeText(AddRegisterLogistic.this, "Pasword should be at least 6 digit", Toast.LENGTH_SHORT).show();
                    editTextRegisterPwdlogistic.setError("Your password too weak");
                    editTextRegisterPwdlogistic.requestFocus();
                } else if (TextUtils.isEmpty(textConPwd)) {
                    Toast.makeText(AddRegisterLogistic.this, "Please confirm your Password", Toast.LENGTH_SHORT).show();
                    editTextRegisterConPwdlogistic.setError("Confirm Password is required");
                    editTextRegisterConPwdlogistic.requestFocus();
                } else if (!textPwd.equals(textConPwd)) {
                    Toast.makeText(AddRegisterLogistic.this, "Please same password", Toast.LENGTH_SHORT).show();
                    editTextRegisterConPwdlogistic.setError("Confirm Password is required");
                    editTextRegisterConPwdlogistic.requestFocus();
                    editTextRegisterPwdlogistic.clearComposingText();
                    editTextRegisterConPwdlogistic.clearComposingText();
                } else {
                    textGender = radioButtonRegisterGenderSelectedlogistic.getText().toString();
                    progressBarlogistic.setVisibility(View.VISIBLE);
                    registerlogistic(textFullname, textEmail, textDate, textPwd, textConPwd, textGender, textMobile);
                }
            }
        });
    }
    private void registerlogistic(String textFullname, String textEmail, String textDate, String textPwd, String textConPwd, String textGender, String textMobile) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(textEmail, textPwd).addOnCompleteListener(AddRegisterLogistic.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser = auth.getCurrentUser();

                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(textFullname).build();
                    firebaseUser.updateProfile(profileChangeRequest);

                    WriteAccountDetailsModel writeAccountDetailsModel = new WriteAccountDetailsModel(textGender,textDate,textMobile);

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Register_Logistic");

                    databaseReference.child(firebaseUser.getUid()).setValue(writeAccountDetailsModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                firebaseUser.sendEmailVerification();
//                                showAlertDialog();
                                Toast.makeText(AddRegisterLogistic.this, "Create Access is Succesfully", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(AddRegisterLogistic.this, "Register Failed !!!,Please try again", Toast.LENGTH_SHORT).show();
                            }
                            progressBarlogistic.setVisibility(View.GONE);
                        }
                    });


                }else {
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        editTextRegisterPwdlogistic.setError("Your password is too weak. Kindly use a mix of alphabets, number or special character");
                        editTextRegisterPwdlogistic.requestFocus();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        editTextRegisterPwdlogistic.setError("Your email is invalid or already in use. Kindly re-enter");
                        editTextRegisterPwdlogistic.requestFocus();
                    }catch (FirebaseAuthUserCollisionException e){
                        editTextRegisterPwdlogistic.setError("User is already registered whit this email.");
                        editTextRegisterPwdlogistic.requestFocus();
                    }catch (Exception e){
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(AddRegisterLogistic.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                    progressBarlogistic.setVisibility(View.GONE);
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
        swipeRefreshLayoutlogistic = findViewById(R.id.swipeContainer);

        swipeRefreshLayoutlogistic.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startActivity(getIntent());
                finish();
                overridePendingTransition(0,0);
                swipeRefreshLayoutlogistic.setRefreshing(false);
            }
        });
        swipeRefreshLayoutlogistic.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_blue_light
                ,android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }
}

