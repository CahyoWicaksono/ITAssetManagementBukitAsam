package com.example.itassetmanagementptbukitasam.Manager;

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

public class AddRegisterManager extends AppCompatActivity {
    private EditText editTextRegisterFullnamemanager,editTextRegisterEmailmanager, editTextRegisterDatemanager, editTextRegisterMobilemanager,editTextRegisterAdress,
            editTextRegisterPwdmanager,editTextRegisterConPwdmanager;
    private ProgressBar progressBarmanager;
    private RadioGroup radioGroupRegisterGendermanager;
    private RadioButton radioButtonRegisterGenderSelectedmanager;
    private DatePickerDialog pickerDialogmanager;
    private ImageView backRegistermanager;
    private SwipeRefreshLayout swipeRefreshLayoutmanager;
    private static final String TAG = "AddRegisterManager";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_manager);

        Toast.makeText(AddRegisterManager.this, "You can register now", Toast.LENGTH_SHORT).show();

        swipeToRefresh();
        editTextRegisterFullnamemanager = findViewById(R.id.editText_register_full_name_manager);
        editTextRegisterEmailmanager = findViewById(R.id.editText_register_email_manager);
        editTextRegisterDatemanager = findViewById(R.id.editText_register_dob_manager);
        editTextRegisterMobilemanager = findViewById(R.id.editText_register_mobile_manager);
        editTextRegisterPwdmanager = findViewById(R.id.editText_register_password_manager);
        editTextRegisterConPwdmanager = findViewById(R.id.editText_register_conpassword_manager);
        backRegistermanager = findViewById(R.id.backRegister_manager);

        progressBarmanager = findViewById(R.id.progressBar_manager);
        radioGroupRegisterGendermanager = findViewById(R.id.radio_group_register_gender_manager);
        radioGroupRegisterGendermanager.clearCheck();

        backRegistermanager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddRegisterManager.this, AccessActivity.class);
                startActivity(intent);
                finish();
            }
        });
        editTextRegisterDatemanager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                pickerDialogmanager = new DatePickerDialog(AddRegisterManager.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        editTextRegisterDatemanager.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, day, month, year);
                pickerDialogmanager.show();
            }
        });
        Button register = findViewById(R.id.button_register_manager);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedGenderId = radioGroupRegisterGendermanager.getCheckedRadioButtonId();
                radioButtonRegisterGenderSelectedmanager = findViewById(selectedGenderId);

                String textFullname = editTextRegisterFullnamemanager.getText().toString();
                String textEmail = editTextRegisterEmailmanager.getText().toString();
                String textDate = editTextRegisterDatemanager.getText().toString();
                String textMobile = editTextRegisterMobilemanager.getText().toString();
                String textPwd = editTextRegisterPwdmanager.getText().toString();
                String textConPwd = editTextRegisterConPwdmanager.getText().toString();
                String textGender;

//                String mobileRegex = "[6-9][0-9]{9}";
//                Matcher mobileMatcer;
//                Pattern mobilePattern = Pattern.compile(mobileRegex);
//                mobileMatcer = mobilePattern.matcher(textMobile);

                if (TextUtils.isEmpty(textFullname)) {
                    Toast.makeText(AddRegisterManager.this, "Please enter your fullname", Toast.LENGTH_SHORT).show();
                    editTextRegisterFullnamemanager.setError("your fullname is required");
                    editTextRegisterFullnamemanager.requestFocus();
                } else if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(AddRegisterManager.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    editTextRegisterEmailmanager.setError("email is required");
                    editTextRegisterEmailmanager.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(AddRegisterManager.this, "Please re-enter your email", Toast.LENGTH_SHORT).show();
                    editTextRegisterEmailmanager.setError("Email is required");
                    editTextRegisterEmailmanager.requestFocus();
                } else if (TextUtils.isEmpty(textDate)) {
                    Toast.makeText(AddRegisterManager.this, "Please enter your Date of Birth", Toast.LENGTH_SHORT).show();
                    editTextRegisterDatemanager.setError("Date of Birth is required");
                    editTextRegisterDatemanager.requestFocus();
                } else if (radioGroupRegisterGendermanager.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(AddRegisterManager.this, "Please enter your gender", Toast.LENGTH_SHORT).show();
                    radioButtonRegisterGenderSelectedmanager.setError("Gender is required");
                    radioButtonRegisterGenderSelectedmanager.requestFocus();
                } else if (TextUtils.isEmpty(textMobile)) {
                    Toast.makeText(AddRegisterManager.this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
                    editTextRegisterMobilemanager.setError("phone Number is required");
                    editTextRegisterMobilemanager.requestFocus();
                } else if (TextUtils.isEmpty(textPwd)) {
                    Toast.makeText(AddRegisterManager.this, "Please enter your Password", Toast.LENGTH_SHORT).show();
                    editTextRegisterPwdmanager.setError("Your Password is required");
                    editTextRegisterPwdmanager.requestFocus();
                } else if (textPwd.length() < 6) {
                    Toast.makeText(AddRegisterManager.this, "Pasword should be at least 6 digit", Toast.LENGTH_SHORT).show();
                    editTextRegisterPwdmanager.setError("Your password too weak");
                    editTextRegisterPwdmanager.requestFocus();
                } else if (TextUtils.isEmpty(textConPwd)) {
                    Toast.makeText(AddRegisterManager.this, "Please confirm your Password", Toast.LENGTH_SHORT).show();
                    editTextRegisterConPwdmanager.setError("Confirm Password is required");
                    editTextRegisterConPwdmanager.requestFocus();
                } else if (!textPwd.equals(textConPwd)) {
                    Toast.makeText(AddRegisterManager.this, "Please same password", Toast.LENGTH_SHORT).show();
                    editTextRegisterConPwdmanager.setError("Confirm Password is required");
                    editTextRegisterConPwdmanager.requestFocus();
                    editTextRegisterPwdmanager.clearComposingText();
                    editTextRegisterConPwdmanager.clearComposingText();
                } else {
                    textGender = radioButtonRegisterGenderSelectedmanager.getText().toString();
                    progressBarmanager.setVisibility(View.VISIBLE);
                    registermanager(textFullname, textEmail, textDate, textPwd, textConPwd, textGender, textMobile);
                }
            }
        });
    }
    private void registermanager(String textFullname, String textEmail, String textDate, String textPwd, String textConPwd, String textGender, String textMobile) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(textEmail, textPwd).addOnCompleteListener(AddRegisterManager.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser = auth.getCurrentUser();

                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(textFullname).build();
                    firebaseUser.updateProfile(profileChangeRequest);

                    WriteAccountDetailsModel writeAccountDetailsModel = new WriteAccountDetailsModel(textGender,textDate,textMobile);

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Register_Manager");

                    databaseReference.child(firebaseUser.getUid()).setValue(writeAccountDetailsModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                firebaseUser.sendEmailVerification();
//                                showAlertDialog();
                                Toast.makeText(AddRegisterManager.this, "Create Access is Succesfully", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(AddRegisterManager.this, "Register Failed !!!,Please try again", Toast.LENGTH_SHORT).show();
                            }
                            progressBarmanager.setVisibility(View.GONE);
                        }
                    });


                }else {
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        editTextRegisterPwdmanager.setError("Your password is too weak. Kindly use a mix of alphabets, number or special character");
                        editTextRegisterPwdmanager.requestFocus();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        editTextRegisterPwdmanager.setError("Your email is invalid or already in use. Kindly re-enter");
                        editTextRegisterPwdmanager.requestFocus();
                    }catch (FirebaseAuthUserCollisionException e){
                        editTextRegisterPwdmanager.setError("User is already registered whit this email.");
                        editTextRegisterPwdmanager.requestFocus();
                    }catch (Exception e){
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(AddRegisterManager.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                    progressBarmanager.setVisibility(View.GONE);
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
        swipeRefreshLayoutmanager = findViewById(R.id.swipeContainer);

        swipeRefreshLayoutmanager.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startActivity(getIntent());
                finish();
                overridePendingTransition(0,0);
                swipeRefreshLayoutmanager.setRefreshing(false);
            }
        });
        swipeRefreshLayoutmanager.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_blue_light
                ,android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }

}

