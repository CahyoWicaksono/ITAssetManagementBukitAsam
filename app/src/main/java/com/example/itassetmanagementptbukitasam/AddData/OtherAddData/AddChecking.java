package com.example.itassetmanagementptbukitasam.AddData.OtherAddData;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.itassetmanagementptbukitasam.ListData.OtherData.CheckingActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.CheckingModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class AddChecking extends AppCompatActivity {

    private EditText assetName, assetCode,officer,location,date;
    private Button btn_save;
    boolean valid = true;
    int mYear, mMonth, mDay;
    private String CheckingID;
    private ImageView backcom;
    private static final String TAG = "AddChecking";
    private RadioGroup radioGroupMaintained, radioGroupCondition, radioGrouptypeAsset;
    private RadioButton radioButtonMaintained, radioButtonCondition,radioButtontypeAsset;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_checking);

        assetName = findViewById(R.id.et_name_check);
        assetCode = findViewById(R.id.et_kode_aset_check);
        officer = findViewById(R.id.et_officer_check);
        location = findViewById(R.id.et_location_check);
        date = findViewById(R.id.et_datetime_check);


        radioGroupMaintained = findViewById(R.id.radio_group_maintenance);
        radioGroupCondition = findViewById(R.id.radio_group_condition);
        radioGrouptypeAsset = findViewById(R.id.radio_group_type);
        btn_save = findViewById(R.id.btn_simpan_check);
        backcom = findViewById(R.id.backChecking);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Checking_Asset");


        backcom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CheckingActivity.class));
                finish();
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddChecking.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkField(assetName);
                checkField(assetCode);
                checkField(officer);
                checkField(location);
                checkField(date);

                int selectedMaintenanceID = radioGroupMaintained.getCheckedRadioButtonId();
                radioButtonMaintained = findViewById(selectedMaintenanceID);

                int selectedConditionID = radioGroupCondition.getCheckedRadioButtonId();
                radioButtonCondition = findViewById(selectedConditionID);

                int selectedTypeID = radioGrouptypeAsset.getCheckedRadioButtonId();
                radioButtontypeAsset = findViewById(selectedTypeID);

                String Name_Asset_Check = assetName.getText().toString();
                String Asset_Code_Check = assetCode.getText().toString();
                String Officer_Check = officer.getText().toString();
                String Maintenance_Status = radioButtonMaintained.getText().toString();
                String Condition_Asset = radioButtonCondition.getText().toString();
                String Type_Asset = radioButtontypeAsset.getText().toString();
                String Location_Check = location.getText().toString();
                String Date_Check = date.getText().toString();


                CheckingID = Name_Asset_Check;

                CheckingModel checkingModel = new CheckingModel(
                        CheckingID,
                        Name_Asset_Check,
                        Asset_Code_Check,
                        Officer_Check,
                        Maintenance_Status,
                        Condition_Asset,
                        Type_Asset,
                        Location_Check,
                        Date_Check);

                if (valid) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child(CheckingID).setValue(checkingModel);
                            startActivity(new Intent(AddChecking.this, CheckingActivity.class));
                            CheckingModel checkingModel = snapshot.getValue(CheckingModel.class);
                            Log.d(TAG, "Create data successfull  " + checkingModel);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.w(TAG, "Failed to read data.", error.toException());

                        }
                    });
                }
            }
        });


    }

    public boolean checkField(EditText textField){
        if(textField.getText().toString().isEmpty()){
            textField.setError("Column cannot be empity");
            valid = false;
        }else {
            valid = true;
        }

        return valid;
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
