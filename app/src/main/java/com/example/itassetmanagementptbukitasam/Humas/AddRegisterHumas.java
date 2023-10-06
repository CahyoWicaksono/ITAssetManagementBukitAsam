package com.example.itassetmanagementptbukitasam.Humas;

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

public class AddRegisterHumas extends AppCompatActivity {
    private EditText editTextRegisterFullnameHumas,editTextRegisterEmailHumas, editTextRegisterDateHumas, editTextRegisterMobileHumas,editTextRegisterAdress,
            editTextRegisterPwdHumas,editTextRegisterConPwdHumas;
    private ProgressBar progressBarHumas;
    private RadioGroup radioGroupRegisterGenderHumas;
    private RadioButton radioButtonRegisterGenderSelectedHumas;
    private DatePickerDialog pickerDialogHumas;
    private ImageView backRegisterHumas;
    private SwipeRefreshLayout swipeRefreshLayoutHumas;
    private static final String TAG = "AddRegisterHumas";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_humas);

        Toast.makeText(AddRegisterHumas.this, "You can register now", Toast.LENGTH_SHORT).show();

        swipeToRefresh();
        editTextRegisterFullnameHumas = findViewById(R.id.editText_register_full_name_humas);
        editTextRegisterEmailHumas = findViewById(R.id.editText_register_email_humas);
        editTextRegisterDateHumas = findViewById(R.id.editText_register_dob_humas);
        editTextRegisterMobileHumas = findViewById(R.id.editText_register_mobile_humas);
        editTextRegisterPwdHumas = findViewById(R.id.editText_register_password_humas);
        editTextRegisterConPwdHumas = findViewById(R.id.editText_register_conpassword_humas);
        backRegisterHumas = findViewById(R.id.backRegister_humas);

        progressBarHumas = findViewById(R.id.progressBar_humas);
        radioGroupRegisterGenderHumas = findViewById(R.id.radio_group_register_gender_humas);
        radioGroupRegisterGenderHumas.clearCheck();

        backRegisterHumas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddRegisterHumas.this, AccessActivity.class);
                startActivity(intent);
                finish();
            }
        });
        editTextRegisterDateHumas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                pickerDialogHumas = new DatePickerDialog(AddRegisterHumas.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        editTextRegisterDateHumas.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, day, month, year);
                pickerDialogHumas.show();
            }
        });
        Button register = findViewById(R.id.button_register_humas);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedGenderId = radioGroupRegisterGenderHumas.getCheckedRadioButtonId();
                radioButtonRegisterGenderSelectedHumas = findViewById(selectedGenderId);

                String textFullname = editTextRegisterFullnameHumas.getText().toString();
                String textEmail = editTextRegisterEmailHumas.getText().toString();
                String textDate = editTextRegisterDateHumas.getText().toString();
                String textMobile = editTextRegisterMobileHumas.getText().toString();
                String textPwd = editTextRegisterPwdHumas.getText().toString();
                String textConPwd = editTextRegisterConPwdHumas.getText().toString();
                String textGender;

//                String mobileRegex = "[6-9][0-9]{9}";
//                Matcher mobileMatcer;
//                Pattern mobilePattern = Pattern.compile(mobileRegex);
//                mobileMatcer = mobilePattern.matcher(textMobile);

                if (TextUtils.isEmpty(textFullname)) {
                    Toast.makeText(AddRegisterHumas.this, "Please enter your fullname", Toast.LENGTH_SHORT).show();
                    editTextRegisterFullnameHumas.setError("your fullname is required");
                    editTextRegisterFullnameHumas.requestFocus();
                } else if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(AddRegisterHumas.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    editTextRegisterEmailHumas.setError("email is required");
                    editTextRegisterEmailHumas.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(AddRegisterHumas.this, "Please re-enter your email", Toast.LENGTH_SHORT).show();
                    editTextRegisterEmailHumas.setError("Email is required");
                    editTextRegisterEmailHumas.requestFocus();
                } else if (TextUtils.isEmpty(textDate)) {
                    Toast.makeText(AddRegisterHumas.this, "Please enter your Date of Birth", Toast.LENGTH_SHORT).show();
                    editTextRegisterDateHumas.setError("Date of Birth is required");
                    editTextRegisterDateHumas.requestFocus();
                } else if (radioGroupRegisterGenderHumas.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(AddRegisterHumas.this, "Please enter your gender", Toast.LENGTH_SHORT).show();
                    radioButtonRegisterGenderSelectedHumas.setError("Gender is required");
                    radioButtonRegisterGenderSelectedHumas.requestFocus();
                } else if (TextUtils.isEmpty(textMobile)) {
                    Toast.makeText(AddRegisterHumas.this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
                    editTextRegisterMobileHumas.setError("phone Number is required");
                    editTextRegisterMobileHumas.requestFocus();
                } else if (TextUtils.isEmpty(textPwd)) {
                    Toast.makeText(AddRegisterHumas.this, "Please enter your Password", Toast.LENGTH_SHORT).show();
                    editTextRegisterPwdHumas.setError("Your Password is required");
                    editTextRegisterPwdHumas.requestFocus();
                } else if (textPwd.length() < 6) {
                    Toast.makeText(AddRegisterHumas.this, "Pasword should be at least 6 digit", Toast.LENGTH_SHORT).show();
                    editTextRegisterPwdHumas.setError("Your password too weak");
                    editTextRegisterPwdHumas.requestFocus();
                } else if (TextUtils.isEmpty(textConPwd)) {
                    Toast.makeText(AddRegisterHumas.this, "Please confirm your Password", Toast.LENGTH_SHORT).show();
                    editTextRegisterConPwdHumas.setError("Confirm Password is required");
                    editTextRegisterConPwdHumas.requestFocus();
                } else if (!textPwd.equals(textConPwd)) {
                    Toast.makeText(AddRegisterHumas.this, "Please same password", Toast.LENGTH_SHORT).show();
                    editTextRegisterConPwdHumas.setError("Confirm Password is required");
                    editTextRegisterConPwdHumas.requestFocus();
                    editTextRegisterPwdHumas.clearComposingText();
                    editTextRegisterConPwdHumas.clearComposingText();
                } else {
                    textGender = radioButtonRegisterGenderSelectedHumas.getText().toString();
                    progressBarHumas.setVisibility(View.VISIBLE);
                    registerHumas(textFullname, textEmail, textDate, textPwd, textConPwd, textGender, textMobile);
                }
            }
        });
    }
    private void registerHumas(String textFullname, String textEmail, String textDate, String textPwd, String textConPwd, String textGender, String textMobile) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(textEmail, textPwd).addOnCompleteListener(AddRegisterHumas.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser = auth.getCurrentUser();

                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(textFullname).build();
                    firebaseUser.updateProfile(profileChangeRequest);

                    WriteAccountDetailsModel writeAccountDetailsModel = new WriteAccountDetailsModel(textGender,textDate,textMobile);

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Register_Humas");

                    databaseReference.child(firebaseUser.getUid()).setValue(writeAccountDetailsModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                firebaseUser.sendEmailVerification();
//                                showAlertDialog();
                                Toast.makeText(AddRegisterHumas.this, "Create Access is Succesfully", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(AddRegisterHumas.this, "Register Failed !!!,Please try again", Toast.LENGTH_SHORT).show();
                            }
                            progressBarHumas.setVisibility(View.GONE);
                        }
                    });


                }else {
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        editTextRegisterPwdHumas.setError("Your password is too weak. Kindly use a mix of alphabets, number or special character");
                        editTextRegisterPwdHumas.requestFocus();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        editTextRegisterPwdHumas.setError("Your email is invalid or already in use. Kindly re-enter");
                        editTextRegisterPwdHumas.requestFocus();
                    }catch (FirebaseAuthUserCollisionException e){
                        editTextRegisterPwdHumas.setError("User is already registered whit this email.");
                        editTextRegisterPwdHumas.requestFocus();
                    }catch (Exception e){
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(AddRegisterHumas.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                    progressBarHumas.setVisibility(View.GONE);
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
//    }
    private void swipeToRefresh() {
        swipeRefreshLayoutHumas = findViewById(R.id.swipeContainer);

        swipeRefreshLayoutHumas.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startActivity(getIntent());
                finish();
                overridePendingTransition(0,0);
                swipeRefreshLayoutHumas.setRefreshing(false);
            }
        });
        swipeRefreshLayoutHumas.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_blue_light
                ,android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }



}

