package com.example.itassetmanagementptbukitasam.Account;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.User.ProfileUserActivity;
import com.example.itassetmanagementptbukitasam.model.WriteUserDetailsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateProfileUserActivity extends AppCompatActivity {

    private EditText editTextUpdateName, editTextUpdateDob,editTextUpdateMobile;
    private RadioGroup radioGroupUdateGender;
    private RadioButton radioButtonUpdateSelect;
    private String textFullname,textDob, textGender, textMobile;
    private ProgressBar progressBar;
    private DatePickerDialog pickerDialog;
    private FirebaseAuth auth;
    private BottomNavigationView bottomNavigationViewProfUs;
    private ImageView profileUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile_user);

        editTextUpdateName = findViewById(R.id.editText_update_profile_name_user);
        editTextUpdateDob = findViewById(R.id.editText_update_profile_dob_user);
        editTextUpdateMobile = findViewById(R.id.editText_update_profile_mobile_user);

        progressBar = findViewById(R.id.progressBar);

        radioGroupUdateGender = findViewById(R.id.radio_group_update_profile_gender);

        profileUser = findViewById(R.id.backprofile);



        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();

        showProfile(firebaseUser);

        profileUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateProfileUserActivity.this, ProfileUserActivity.class);
                startActivity(intent);
                finish();
            }
        });

        editTextUpdateDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String textSADoB[] = textDob.split("/");
                int day = Integer.parseInt(textSADoB[0]);
                int month = Integer.parseInt(textSADoB[1]) -1;
                int year = Integer.parseInt(textSADoB[2]);

                pickerDialog = new DatePickerDialog(UpdateProfileUserActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        editTextUpdateDob.setText(dayOfMonth + "/" + (month + 1) + "/" +year);
                    }
                }, day,month,year);
                pickerDialog.show();
            }
        });

        Button updateProfile = findViewById(R.id.button_update_profile_user);
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfileAdmin(firebaseUser);
            }
        });

    }

    private void updateProfileAdmin(FirebaseUser firebaseUser) {
        int selectedGenderId = radioGroupUdateGender.getCheckedRadioButtonId();
        radioButtonUpdateSelect = findViewById(selectedGenderId);
        String mobileRegex ="[6-9][0-9]{9}";
        Matcher mobileMatcer;
        Pattern mobilePattern = Pattern.compile(mobileRegex);
        mobileMatcer = mobilePattern.matcher(textMobile);

        if (TextUtils.isEmpty(textFullname)) {
            Toast.makeText(UpdateProfileUserActivity.this, "Please enter your fullname", Toast.LENGTH_SHORT).show();
            editTextUpdateName.setError("your fullname is required");
            editTextUpdateName.requestFocus();
        }else if (TextUtils.isEmpty(textDob)) {
            Toast.makeText(UpdateProfileUserActivity.this, "Please enter your Date of Birth", Toast.LENGTH_SHORT).show();
            editTextUpdateDob.setError("Date of Birth is required");
            editTextUpdateDob.requestFocus();
        }else if (TextUtils.isEmpty(radioButtonUpdateSelect.getText())) {
            Toast.makeText(UpdateProfileUserActivity.this, "Please enter your gender", Toast.LENGTH_SHORT).show();
            radioButtonUpdateSelect.setError("Gender is required");
            radioButtonUpdateSelect.requestFocus();
        }else if (TextUtils.isEmpty(textMobile)) {
            Toast.makeText(UpdateProfileUserActivity.this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
            editTextUpdateMobile.setError("phone Number is required");
            editTextUpdateMobile.requestFocus();
        }else if (textMobile.length() !=12) {
            Toast.makeText(UpdateProfileUserActivity.this, "Please re-enter your phone number", Toast.LENGTH_SHORT).show();
            editTextUpdateMobile.setError("phone Number should be 12 digit");
            editTextUpdateMobile.requestFocus();
        }else if (!mobileMatcer.find()){
            Toast.makeText(UpdateProfileUserActivity.this, "Please re-enter your phone number", Toast.LENGTH_SHORT).show();
            editTextUpdateMobile.setError("phone number is not valid");
            editTextUpdateMobile.requestFocus();
        }else {
            textFullname = editTextUpdateName.getText().toString();
            textDob = editTextUpdateDob.getText().toString();
            textMobile = editTextUpdateMobile.getText().toString();
            textGender = radioButtonUpdateSelect.getText().toString();


            WriteUserDetailsModel writeUserDetailsModel = new WriteUserDetailsModel(textMobile,textDob,textGender);
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Register User");

            String adminId = firebaseUser.getUid();

            progressBar.setVisibility(View.VISIBLE);

            databaseReference.child(adminId).setValue(writeUserDetailsModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(textFullname).build();
                        firebaseUser.updateProfile(profileUpdate);

                        Toast.makeText(UpdateProfileUserActivity.this, "Update Successfull", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateProfileUserActivity.this, ProfileUserActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                | Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);
                        finish();
                    }else {
                        try {
                            throw task.getException();
                        }catch (Exception e){
                            Toast.makeText(UpdateProfileUserActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    progressBar.setVisibility(View.GONE);
                }
            });

        }
    }

    private void showProfile(FirebaseUser firebaseUser) {
        String userIdOfRegistered = firebaseUser.getUid();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Register User");
        progressBar.setVisibility(View.VISIBLE);

        databaseReference.child(userIdOfRegistered).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                WriteUserDetailsModel writeUserDetailsModel = snapshot.getValue(WriteUserDetailsModel.class);
                if (writeUserDetailsModel != null) {
                    textFullname = firebaseUser.getDisplayName();
                    textDob      = writeUserDetailsModel.Dob;
                    textGender   = writeUserDetailsModel.gender;
                    textMobile   = writeUserDetailsModel.mobile;

                    editTextUpdateName.setText(textFullname);
                    editTextUpdateDob.setText(textDob);
                    editTextUpdateMobile.setText(textMobile);

                    if (textGender.equals("Male")){
                        radioButtonUpdateSelect = findViewById(R.id.radio_male);
                    }else {
                        radioButtonUpdateSelect = findViewById(R.id.radio_female);
                    }
                    radioButtonUpdateSelect.setChecked(true);
                }else {
                    Toast.makeText(UpdateProfileUserActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateProfileUserActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
