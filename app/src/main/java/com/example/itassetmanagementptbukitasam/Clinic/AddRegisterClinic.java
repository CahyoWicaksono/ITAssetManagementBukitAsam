package com.example.itassetmanagementptbukitasam.Clinic;

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

public class AddRegisterClinic extends AppCompatActivity {
    private EditText editTextRegisterFullnameclinic, editTextRegisterEmailclinic, editTextRegisterDateclinic, editTextRegisterMobileclinic, editTextRegisterAdress,
            editTextRegisterPwdclinic, editTextRegisterConPwdclinic;
    private ProgressBar progressBarclinic;
    private RadioGroup radioGroupRegisterGenderclinic;
    private RadioButton radioButtonRegisterGenderSelectedclinic;
    private DatePickerDialog pickerDialogclinic;
    private ImageView backRegisterclinic;
    private SwipeRefreshLayout swipeRefreshLayoutclinic;
    private static final String TAG = "AddRegisterClinic";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_clinic);

        Toast.makeText(AddRegisterClinic.this, "You can register now", Toast.LENGTH_SHORT).show();

        swipeToRefresh();
        editTextRegisterFullnameclinic = findViewById(R.id.editText_register_full_name_clinic);
        editTextRegisterEmailclinic = findViewById(R.id.editText_register_email_clinic);
        editTextRegisterDateclinic = findViewById(R.id.editText_register_dob_clinic);
        editTextRegisterMobileclinic = findViewById(R.id.editText_register_mobile_clinic);
        editTextRegisterPwdclinic = findViewById(R.id.editText_register_password_clinic);
        editTextRegisterConPwdclinic = findViewById(R.id.editText_register_conpassword_clinic);
        backRegisterclinic = findViewById(R.id.backRegister_clinic);

        progressBarclinic = findViewById(R.id.progressBar_clinic);
        radioGroupRegisterGenderclinic = findViewById(R.id.radio_group_register_gender_clinic);
        radioGroupRegisterGenderclinic.clearCheck();

        backRegisterclinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddRegisterClinic.this, AccessActivity.class);
                startActivity(intent);
                finish();
            }
        });
        editTextRegisterDateclinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                pickerDialogclinic = new DatePickerDialog(AddRegisterClinic.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        editTextRegisterDateclinic.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, day, month, year);
                pickerDialogclinic.show();
            }
        });
        Button register = findViewById(R.id.button_register_clinic);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedGenderId = radioGroupRegisterGenderclinic.getCheckedRadioButtonId();
                radioButtonRegisterGenderSelectedclinic = findViewById(selectedGenderId);

                String textFullname = editTextRegisterFullnameclinic.getText().toString();
                String textEmail = editTextRegisterEmailclinic.getText().toString();
                String textDate = editTextRegisterDateclinic.getText().toString();
                String textMobile = editTextRegisterMobileclinic.getText().toString();
                String textPwd = editTextRegisterPwdclinic.getText().toString();
                String textConPwd = editTextRegisterConPwdclinic.getText().toString();
                String textGender;

//                String mobileRegex = "[6-9][0-9]{9}";
//                Matcher mobileMatcer;
//                Pattern mobilePattern = Pattern.compile(mobileRegex);
//                mobileMatcer = mobilePattern.matcher(textMobile);

                if (TextUtils.isEmpty(textFullname)) {
                    Toast.makeText(AddRegisterClinic.this, "Please enter your fullname", Toast.LENGTH_SHORT).show();
                    editTextRegisterFullnameclinic.setError("your fullname is required");
                    editTextRegisterFullnameclinic.requestFocus();
                } else if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(AddRegisterClinic.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    editTextRegisterEmailclinic.setError("email is required");
                    editTextRegisterEmailclinic.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(AddRegisterClinic.this, "Please re-enter your email", Toast.LENGTH_SHORT).show();
                    editTextRegisterEmailclinic.setError("Email is required");
                    editTextRegisterEmailclinic.requestFocus();
                } else if (TextUtils.isEmpty(textDate)) {
                    Toast.makeText(AddRegisterClinic.this, "Please enter your Date of Birth", Toast.LENGTH_SHORT).show();
                    editTextRegisterDateclinic.setError("Date of Birth is required");
                    editTextRegisterDateclinic.requestFocus();
                } else if (radioGroupRegisterGenderclinic.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(AddRegisterClinic.this, "Please enter your gender", Toast.LENGTH_SHORT).show();
                    radioButtonRegisterGenderSelectedclinic.setError("Gender is required");
                    radioButtonRegisterGenderSelectedclinic.requestFocus();
                } else if (TextUtils.isEmpty(textMobile)) {
                    Toast.makeText(AddRegisterClinic.this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
                    editTextRegisterMobileclinic.setError("phone Number is required");
                    editTextRegisterMobileclinic.requestFocus();
                } else if (TextUtils.isEmpty(textPwd)) {
                    Toast.makeText(AddRegisterClinic.this, "Please enter your Password", Toast.LENGTH_SHORT).show();
                    editTextRegisterPwdclinic.setError("Your Password is required");
                    editTextRegisterPwdclinic.requestFocus();
                } else if (textPwd.length() < 6) {
                    Toast.makeText(AddRegisterClinic.this, "Pasword should be at least 6 digit", Toast.LENGTH_SHORT).show();
                    editTextRegisterPwdclinic.setError("Your password too weak");
                    editTextRegisterPwdclinic.requestFocus();
                } else if (TextUtils.isEmpty(textConPwd)) {
                    Toast.makeText(AddRegisterClinic.this, "Please confirm your Password", Toast.LENGTH_SHORT).show();
                    editTextRegisterConPwdclinic.setError("Confirm Password is required");
                    editTextRegisterConPwdclinic.requestFocus();
                } else if (!textPwd.equals(textConPwd)) {
                    Toast.makeText(AddRegisterClinic.this, "Please same password", Toast.LENGTH_SHORT).show();
                    editTextRegisterConPwdclinic.setError("Confirm Password is required");
                    editTextRegisterConPwdclinic.requestFocus();
                    editTextRegisterPwdclinic.clearComposingText();
                    editTextRegisterConPwdclinic.clearComposingText();
                } else {
                    textGender = radioButtonRegisterGenderSelectedclinic.getText().toString();
                    progressBarclinic.setVisibility(View.VISIBLE);
                    registerClinic(textFullname, textEmail, textDate, textPwd, textConPwd, textGender, textMobile);
                }
            }
        });
    }

    private void registerClinic(String textFullname, String textEmail, String textDate, String textPwd, String textConPwd, String textGender, String textMobile) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(textEmail, textPwd).addOnCompleteListener(AddRegisterClinic.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = auth.getCurrentUser();

                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(textFullname).build();
                    firebaseUser.updateProfile(profileChangeRequest);

                    WriteAccountDetailsModel writeAccountDetailsModel = new WriteAccountDetailsModel(textGender, textDate, textMobile);

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Register_Clinic");

                    databaseReference.child(firebaseUser.getUid()).setValue(writeAccountDetailsModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                firebaseUser.sendEmailVerification();
//                                showAlertDialog();
                                Toast.makeText(AddRegisterClinic.this, "Create Access is Succesfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AddRegisterClinic.this, "Register Failed !!!,Please try again", Toast.LENGTH_SHORT).show();
                            }
                            progressBarclinic.setVisibility(View.GONE);
                        }
                    });


                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        editTextRegisterPwdclinic.setError("Your password is too weak. Kindly use a mix of alphabets, number or special character");
                        editTextRegisterPwdclinic.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        editTextRegisterPwdclinic.setError("Your email is invalid or already in use. Kindly re-enter");
                        editTextRegisterPwdclinic.requestFocus();
                    } catch (FirebaseAuthUserCollisionException e) {
                        editTextRegisterPwdclinic.setError("User is already registered whit this email.");
                        editTextRegisterPwdclinic.requestFocus();
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(AddRegisterClinic.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                    progressBarclinic.setVisibility(View.GONE);
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
        swipeRefreshLayoutclinic = findViewById(R.id.swipeContainer);

        swipeRefreshLayoutclinic.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startActivity(getIntent());
                finish();
                overridePendingTransition(0, 0);
                swipeRefreshLayoutclinic.setRefreshing(false);
            }
        });
        swipeRefreshLayoutclinic.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_blue_light
                , android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }


}

