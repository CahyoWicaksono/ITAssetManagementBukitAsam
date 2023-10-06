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
import com.example.itassetmanagementptbukitasam.model.WriteUserDetailsModel;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddRegisterUserActivity extends AppCompatActivity {
    private EditText editTextRegisterFullname,editTextRegisterEmail, editTextRegisterDate, editTextRegisterMobile,
            editTextRegisterPwd,editTextRegisterConPwd;
    private ProgressBar progressBar;
    private RadioGroup radioGroupRegisterGender;
    private RadioButton radioButtonRegisterGenderSelected;
    private DatePickerDialog pickerDialog;
    private ImageView backRegister;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final String TAG = "AddRegisterUserActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register_user);

        swipeToRefresh();

        Toast.makeText(AddRegisterUserActivity.this, "You can register now", Toast.LENGTH_SHORT).show();
        editTextRegisterFullname = findViewById(R.id.editText_register_full_name_user);
        editTextRegisterEmail = findViewById(R.id.editText_register_email_user);
        editTextRegisterDate = findViewById(R.id.editText_register_dob_user);
        editTextRegisterMobile = findViewById(R.id.editText_register_mobile_user);
        editTextRegisterPwd = findViewById(R.id.editText_register_password_user);
        editTextRegisterConPwd = findViewById(R.id.editText_register_conpassword_user);
        backRegister = findViewById(R.id.backRegister_user);

        progressBar = findViewById(R.id.progressBar);
        radioGroupRegisterGender = findViewById(R.id.radio_group_register_gender_user);
        radioGroupRegisterGender.clearCheck();

        backRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddRegisterUserActivity.this, AccessActivity.class);
                startActivity(intent);
                finish();
            }
        });

        editTextRegisterDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                pickerDialog = new DatePickerDialog(AddRegisterUserActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        editTextRegisterDate.setText(dayOfMonth + "/" + (month + 1) + "/" +year);
                    }
                }, day,month,year);
                pickerDialog.show();
            }
        });
        Button register = findViewById(R.id.button_register_user);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedGenderId = radioGroupRegisterGender.getCheckedRadioButtonId();
                radioButtonRegisterGenderSelected = findViewById(selectedGenderId);

                String textFullname = editTextRegisterFullname.getText().toString();
                String textEmail = editTextRegisterEmail.getText().toString();
                String textMobile = editTextRegisterMobile.getText().toString();
                String textDate = editTextRegisterDate.getText().toString();
                String textPwd = editTextRegisterPwd.getText().toString();
                String textConPwd = editTextRegisterConPwd.getText().toString();
                String textGender;

                String mobileRegex ="[6-9][0-9]{9}";
                Matcher mobileMatcer;
                Pattern mobilePattern = Pattern.compile(mobileRegex);
                mobileMatcer = mobilePattern.matcher(textMobile);

                if (TextUtils.isEmpty(textFullname)) {
                    Toast.makeText(AddRegisterUserActivity.this, "Please enter your fullname", Toast.LENGTH_SHORT).show();
                    editTextRegisterFullname.setError("your fullname is required");
                    editTextRegisterFullname.requestFocus();
                }else if (TextUtils.isEmpty(textEmail)){
                    Toast.makeText(AddRegisterUserActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    editTextRegisterEmail.setError("email is required");
                    editTextRegisterEmail.requestFocus();
                }else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(AddRegisterUserActivity.this, "Please re-enter your email", Toast.LENGTH_SHORT).show();
                    editTextRegisterEmail.setError("Email is required");
                    editTextRegisterEmail.requestFocus();
                }else if (TextUtils.isEmpty(textDate)) {
                    Toast.makeText(AddRegisterUserActivity.this, "Please enter your Date of Birth", Toast.LENGTH_SHORT).show();
                    editTextRegisterDate.setError("Date of Birth is required");
                    editTextRegisterDate.requestFocus();
                }else if (radioGroupRegisterGender.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(AddRegisterUserActivity.this, "Please enter your gender", Toast.LENGTH_SHORT).show();
                    radioButtonRegisterGenderSelected.setError("Gender is required");
                    radioButtonRegisterGenderSelected.requestFocus();
                }else if (TextUtils.isEmpty(textMobile)) {
                    Toast.makeText(AddRegisterUserActivity.this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
                    editTextRegisterMobile.setError("phone Number is required");
                    editTextRegisterMobile.requestFocus();
                }else if (textMobile.length() !=14) {
                    Toast.makeText(AddRegisterUserActivity.this, "Please re-enter your phone number", Toast.LENGTH_SHORT).show();
                    editTextRegisterMobile.setError("phone Number should be 14 digit");
                    editTextRegisterMobile.requestFocus();
                }else if (!mobileMatcer.find()){
                    Toast.makeText(AddRegisterUserActivity.this, "Please re-enter your phone number", Toast.LENGTH_SHORT).show();
                    editTextRegisterMobile.setError("phone number is not valid");
                    editTextRegisterMobile.requestFocus();
                }else if (TextUtils.isEmpty(textPwd)) {
                    Toast.makeText(AddRegisterUserActivity.this, "Please enter your Password", Toast.LENGTH_SHORT).show();
                    editTextRegisterPwd.setError("Your Password is required");
                    editTextRegisterPwd.requestFocus();
                }else if (textPwd.length() < 6 ) {
                    Toast.makeText(AddRegisterUserActivity.this, "Pasword should be at least 6 digit", Toast.LENGTH_SHORT).show();
                    editTextRegisterPwd.setError("Your password too weak");
                    editTextRegisterPwd.requestFocus();
                }else if (TextUtils.isEmpty(textConPwd)) {
                    Toast.makeText(AddRegisterUserActivity.this, "Please confirm your Password", Toast.LENGTH_SHORT).show();
                    editTextRegisterConPwd.setError("Confirm Password is required");
                    editTextRegisterConPwd.requestFocus();
                }else if (!textPwd.equals(textConPwd)) {
                    Toast.makeText(AddRegisterUserActivity.this, "Please same password", Toast.LENGTH_SHORT).show();
                    editTextRegisterConPwd.setError("Confirm Password is required");
                    editTextRegisterConPwd.requestFocus();
                    editTextRegisterPwd.clearComposingText();
                    editTextRegisterConPwd.clearComposingText();
                } else {
                    textGender = radioButtonRegisterGenderSelected.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);
                    registerAdmin(textFullname, textEmail,textDate,textPwd,textConPwd,textGender,textMobile);
                    ClearAll();
                }
            }
        });
    }

    private void registerAdmin(String textFullname, String textEmail, String textDate, String textPwd, String textConPwd, String textGender, String textMobile) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(textEmail, textPwd).addOnCompleteListener(AddRegisterUserActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser = auth.getCurrentUser();

                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(textFullname).build();
                    firebaseUser.updateProfile(profileChangeRequest);

                    WriteUserDetailsModel writeUserDetailsModel = new WriteUserDetailsModel(textGender,textDate,textMobile);

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Register User");

                    databaseReference.child(firebaseUser.getUid()).setValue(writeUserDetailsModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                firebaseUser.sendEmailVerification();
//                                showAlertDialog();
                                Toast.makeText(AddRegisterUserActivity.this, "Create Acces is Succesfully", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(AddRegisterUserActivity.this, "Register Failed !!!,Please try again", Toast.LENGTH_SHORT).show();
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    });


                }else {
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        editTextRegisterPwd.setError("Your password is too weak. Kindly use a mix of alphabets, number or special character");
                        editTextRegisterPwd.requestFocus();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        editTextRegisterPwd.setError("Your email is invalid or already in use. Kindly re-enter");
                        editTextRegisterPwd.requestFocus();
                    }catch (FirebaseAuthUserCollisionException e){
                        editTextRegisterPwd.setError("User is already registered whit this email.");
                        editTextRegisterPwd.requestFocus();
                    }catch (Exception e){
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(AddRegisterUserActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                    progressBar.setVisibility(View.GONE);
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
//        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AddRegisterUserActivity.this);
//        builder.setView(R.layout.activity_order__generate);
//
//        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int which) {
//                Intent intent = new Intent(AddRegisterUserActivity.this, AccessActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//    }
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
    private void ClearAll()
    {
        editTextRegisterFullname.setText("");
        editTextRegisterEmail.setText("");
        editTextRegisterDate.setText("");
        editTextRegisterMobile.setText("");
        editTextRegisterPwd.setText("");
        editTextRegisterConPwd.setText("");
    }
}