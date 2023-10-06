package com.example.itassetmanagementptbukitasam.SDM;

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

public class AddRegisterSDM extends AppCompatActivity {
    private EditText editTextRegisterFullnamecomplaint,editTextRegisterEmailcomplaint, editTextRegisterDatecomplaint, editTextRegisterMobilecomplaint,editTextRegisterAdress,
            editTextRegisterPwdcomplaint,editTextRegisterConPwdcomplaint;
    private ProgressBar progressBarcomplaint;
    private RadioGroup radioGroupRegisterGendercomplaint;
    private RadioButton radioButtonRegisterGenderSelectedcomplaint;
    private DatePickerDialog pickerDialogcomplaint;
    private ImageView backRegistercomplaint;
    private SwipeRefreshLayout swipeRefreshLayoutcomplaint;
    private static final String TAG = "AddRegisterSDM";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_complaint);

        Toast.makeText(AddRegisterSDM.this, "You can register now", Toast.LENGTH_SHORT).show();

        swipeToRefresh();
        editTextRegisterFullnamecomplaint = findViewById(R.id.editText_register_full_name_complaint);
        editTextRegisterEmailcomplaint = findViewById(R.id.editText_register_email_complaint);
        editTextRegisterDatecomplaint = findViewById(R.id.editText_register_dob_complaint);
        editTextRegisterMobilecomplaint = findViewById(R.id.editText_register_mobile_complaint);
        editTextRegisterPwdcomplaint = findViewById(R.id.editText_register_password_complaint);
        editTextRegisterConPwdcomplaint = findViewById(R.id.editText_register_conpassword_complaint);
        backRegistercomplaint = findViewById(R.id.backRegister_complaint);

        progressBarcomplaint = findViewById(R.id.progressBar_complaint);
        radioGroupRegisterGendercomplaint = findViewById(R.id.radio_group_register_gender_complaint);
        radioGroupRegisterGendercomplaint.clearCheck();

        backRegistercomplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddRegisterSDM.this, AccessActivity.class);
                startActivity(intent);
                finish();
            }
        });
        editTextRegisterDatecomplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                pickerDialogcomplaint = new DatePickerDialog(AddRegisterSDM.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        editTextRegisterDatecomplaint.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, day, month, year);
                pickerDialogcomplaint.show();
            }
        });
        Button register = findViewById(R.id.button_register_complaint);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedGenderId = radioGroupRegisterGendercomplaint.getCheckedRadioButtonId();
                radioButtonRegisterGenderSelectedcomplaint = findViewById(selectedGenderId);

                String textFullname = editTextRegisterFullnamecomplaint.getText().toString();
                String textEmail = editTextRegisterEmailcomplaint.getText().toString();
                String textDate = editTextRegisterDatecomplaint.getText().toString();
                String textMobile = editTextRegisterMobilecomplaint.getText().toString();
                String textPwd = editTextRegisterPwdcomplaint.getText().toString();
                String textConPwd = editTextRegisterConPwdcomplaint.getText().toString();
                String textGender;

//                String mobileRegex = "[6-9][0-9]{9}";
//                Matcher mobileMatcer;
//                Pattern mobilePattern = Pattern.compile(mobileRegex);
//                mobileMatcer = mobilePattern.matcher(textMobile);

                if (TextUtils.isEmpty(textFullname)) {
                    Toast.makeText(AddRegisterSDM.this, "Please enter your fullname", Toast.LENGTH_SHORT).show();
                    editTextRegisterFullnamecomplaint.setError("your fullname is required");
                    editTextRegisterFullnamecomplaint.requestFocus();
                } else if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(AddRegisterSDM.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    editTextRegisterEmailcomplaint.setError("email is required");
                    editTextRegisterEmailcomplaint.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(AddRegisterSDM.this, "Please re-enter your email", Toast.LENGTH_SHORT).show();
                    editTextRegisterEmailcomplaint.setError("Email is required");
                    editTextRegisterEmailcomplaint.requestFocus();
                } else if (TextUtils.isEmpty(textDate)) {
                    Toast.makeText(AddRegisterSDM.this, "Please enter your Date of Birth", Toast.LENGTH_SHORT).show();
                    editTextRegisterDatecomplaint.setError("Date of Birth is required");
                    editTextRegisterDatecomplaint.requestFocus();
                } else if (radioGroupRegisterGendercomplaint.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(AddRegisterSDM.this, "Please enter your gender", Toast.LENGTH_SHORT).show();
                    radioButtonRegisterGenderSelectedcomplaint.setError("Gender is required");
                    radioButtonRegisterGenderSelectedcomplaint.requestFocus();
                } else if (TextUtils.isEmpty(textMobile)) {
                    Toast.makeText(AddRegisterSDM.this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
                    editTextRegisterMobilecomplaint.setError("phone Number is required");
                    editTextRegisterMobilecomplaint.requestFocus();
                } else if (TextUtils.isEmpty(textPwd)) {
                    Toast.makeText(AddRegisterSDM.this, "Please enter your Password", Toast.LENGTH_SHORT).show();
                    editTextRegisterPwdcomplaint.setError("Your Password is required");
                    editTextRegisterPwdcomplaint.requestFocus();
                } else if (textPwd.length() < 6) {
                    Toast.makeText(AddRegisterSDM.this, "Pasword should be at least 6 digit", Toast.LENGTH_SHORT).show();
                    editTextRegisterPwdcomplaint.setError("Your password too weak");
                    editTextRegisterPwdcomplaint.requestFocus();
                } else if (TextUtils.isEmpty(textConPwd)) {
                    Toast.makeText(AddRegisterSDM.this, "Please confirm your Password", Toast.LENGTH_SHORT).show();
                    editTextRegisterConPwdcomplaint.setError("Confirm Password is required");
                    editTextRegisterConPwdcomplaint.requestFocus();
                } else if (!textPwd.equals(textConPwd)) {
                    Toast.makeText(AddRegisterSDM.this, "Please same password", Toast.LENGTH_SHORT).show();
                    editTextRegisterConPwdcomplaint.setError("Confirm Password is required");
                    editTextRegisterConPwdcomplaint.requestFocus();
                    editTextRegisterPwdcomplaint.clearComposingText();
                    editTextRegisterConPwdcomplaint.clearComposingText();
                } else {
                    textGender = radioButtonRegisterGenderSelectedcomplaint.getText().toString();
                    progressBarcomplaint.setVisibility(View.VISIBLE);
                    registercomplaint(textFullname, textEmail, textDate, textPwd, textConPwd, textGender, textMobile);
                }
            }
        });
    }
    private void registercomplaint(String textFullname, String textEmail, String textDate, String textPwd, String textConPwd, String textGender, String textMobile) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(textEmail, textPwd).addOnCompleteListener(AddRegisterSDM.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser = auth.getCurrentUser();

                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(textFullname).build();
                    firebaseUser.updateProfile(profileChangeRequest);

                    WriteAccountDetailsModel writeAccountDetailsModel = new WriteAccountDetailsModel(textGender,textDate,textMobile);

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Register_Complaint");

                    databaseReference.child(firebaseUser.getUid()).setValue(writeAccountDetailsModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                firebaseUser.sendEmailVerification();
//                                showAlertDialog();
                                Toast.makeText(AddRegisterSDM.this, "Create Access is Succesfully", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(AddRegisterSDM.this, "Register Failed !!!,Please try again", Toast.LENGTH_SHORT).show();
                            }
                            progressBarcomplaint.setVisibility(View.GONE);
                        }
                    });


                }else {
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        editTextRegisterPwdcomplaint.setError("Your password is too weak. Kindly use a mix of alphabets, number or special character");
                        editTextRegisterPwdcomplaint.requestFocus();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        editTextRegisterPwdcomplaint.setError("Your email is invalid or already in use. Kindly re-enter");
                        editTextRegisterPwdcomplaint.requestFocus();
                    }catch (FirebaseAuthUserCollisionException e){
                        editTextRegisterPwdcomplaint.setError("User is already registered whit this email.");
                        editTextRegisterPwdcomplaint.requestFocus();
                    }catch (Exception e){
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(AddRegisterSDM.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                    progressBarcomplaint.setVisibility(View.GONE);
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
        swipeRefreshLayoutcomplaint = findViewById(R.id.swipeContainer);

        swipeRefreshLayoutcomplaint.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startActivity(getIntent());
                finish();
                overridePendingTransition(0,0);
                swipeRefreshLayoutcomplaint.setRefreshing(false);
            }
        });
        swipeRefreshLayoutcomplaint.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_blue_light
                ,android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }


}

