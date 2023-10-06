package com.example.itassetmanagementptbukitasam.Laboratory;

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
import com.example.itassetmanagementptbukitasam.model.WriteAccountDetailsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateProfileLabActivity extends AppCompatActivity {

    private EditText editTextUpdateName, editTextUpdateDob,editTextUpdateMobile, editTextUpdateAddress;
    private RadioGroup radioGroupUdateGender;
    private RadioButton radioButtonUpdateSelect;
    private String textFullname,textDob, textGender, textMobile,textAddress;
    private ProgressBar progressBar;
    private DatePickerDialog pickerDialog;
    private FirebaseAuth auth;
    private ImageView date_picker,backup;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile_laboratory);

        editTextUpdateName = findViewById(R.id.editText_update_profile_name_lab);
        editTextUpdateDob = findViewById(R.id.editText_update_profile_dob_lab);
        editTextUpdateMobile = findViewById(R.id.editText_update_profile_mobile_lab);

        progressBar = findViewById(R.id.progressBar);

        radioGroupUdateGender = findViewById(R.id.radio_group_update_profile_gender_lab);
        date_picker = findViewById(R.id.imageView_date_picker_lab);

        backup = findViewById(R.id.backprofile_lab);

        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();

        showProfile_laboratory(firebaseUser);

        backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UpdateProfileLabActivity.this, ProfileLabActivity.class);
                startActivity(intent);
                finish();
            }
        });

        date_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String textSADoB[] = textDob.split("/");
                int day = Integer.parseInt(textSADoB[0]);
                int month = Integer.parseInt(textSADoB[1]) -1;
                int year = Integer.parseInt(textSADoB[2]);

                pickerDialog = new DatePickerDialog(UpdateProfileLabActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                updateProfileLab(firebaseUser);
            }
        });

    }

    private void updateProfileLab(FirebaseUser firebaseUser) {
        int selectedGenderId = radioGroupUdateGender.getCheckedRadioButtonId();
        radioButtonUpdateSelect = findViewById(selectedGenderId);

        if (TextUtils.isEmpty(textFullname)) {
            Toast.makeText(UpdateProfileLabActivity.this, "Please enter your fullname", Toast.LENGTH_SHORT).show();
            editTextUpdateName.setError("your fullname is required");
            editTextUpdateName.requestFocus();
        }else if (TextUtils.isEmpty(textDob)) {
            Toast.makeText(UpdateProfileLabActivity.this, "Please enter your Date of Birth", Toast.LENGTH_SHORT).show();
            editTextUpdateDob.setError("Date of Birth is required");
            editTextUpdateDob.requestFocus();
        }else if (TextUtils.isEmpty(radioButtonUpdateSelect.getText())) {
            Toast.makeText(UpdateProfileLabActivity.this, "Please enter your gender", Toast.LENGTH_SHORT).show();
            radioButtonUpdateSelect.setError("Gender is required");
            radioButtonUpdateSelect.requestFocus();
        }else if (TextUtils.isEmpty(textMobile)) {
            Toast.makeText(UpdateProfileLabActivity.this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
            editTextUpdateMobile.setError("phone Number is required");
            editTextUpdateMobile.requestFocus();
        }else {
            textFullname = editTextUpdateName.getText().toString();
            textDob = editTextUpdateDob.getText().toString();
            textGender = radioButtonUpdateSelect.getText().toString();
            textMobile = editTextUpdateMobile.getText().toString();

            WriteAccountDetailsModel writeAccountDetailsModel = new WriteAccountDetailsModel(textDob,textGender,textMobile);
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Register_Laboratorium");

            String LabId = firebaseUser.getUid();

            progressBar.setVisibility(View.VISIBLE);

            databaseReference.child(LabId).setValue(writeAccountDetailsModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(textFullname).build();
                        firebaseUser.updateProfile(profileUpdate);

                        Toast.makeText(UpdateProfileLabActivity.this, "Update Successfull", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateProfileLabActivity.this, ProfileLabActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                | Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);
                        finish();

                    }else {
                        try {
                            throw task.getException();
                        }catch (Exception e){
                            Toast.makeText(UpdateProfileLabActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    progressBar.setVisibility(View.GONE);
                }
            });

        }
    }

    private void showProfile_laboratory(FirebaseUser firebaseUser) {
        String userIdOfRegistered = firebaseUser.getUid();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Register_Laboratorium");
        progressBar.setVisibility(View.VISIBLE);

        databaseReference.child(userIdOfRegistered).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                WriteAccountDetailsModel writeAccountDetailsModel = snapshot.getValue(WriteAccountDetailsModel.class);
                if (writeAccountDetailsModel != null) {
                    textFullname = firebaseUser.getDisplayName();
                    textDob      = writeAccountDetailsModel.Dob;
                    textGender   = writeAccountDetailsModel.gender;
                    textMobile   = writeAccountDetailsModel.mobile;


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
                    Toast.makeText(UpdateProfileLabActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateProfileLabActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
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
}
