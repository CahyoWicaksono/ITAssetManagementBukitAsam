package com.example.itassetmanagementptbukitasam.ChangeData.OtherEdit;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import java.util.HashMap;
import java.util.Map;

public class EditChecking extends AppCompatActivity {
    private EditText assetName, assetCode,officer,location,date;
    Button btn_update_cheking;
    boolean valid = true;
    ImageView backasset, backas;
    CheckingModel checkingModel;
    FirebaseDatabase firebaseDatabase;
    int mYear, mMonth, mDay;
    DatabaseReference databaseReference;
    private RadioGroup radioGroupMaintained, radioGroupCondition, radioGrouptypeAsset;
    private RadioButton radioButtonMaintained, radioButtonCondition,radioButtontypeAsset;
    String CheckingID;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_checking);

        assetName = findViewById(R.id.et_name_check_Update);
        assetCode = findViewById(R.id.et_kode_aset_check_Update);
        officer = findViewById(R.id.et_officer_check_Update);
        location = findViewById(R.id.et_location_check_Update);
        date = findViewById(R.id.et_datetime_check_Update);


        radioGroupMaintained = findViewById(R.id.radio_group_maintenance_Update);
        radioGroupCondition = findViewById(R.id.radio_group_condition_Update);
        radioGrouptypeAsset = findViewById(R.id.radio_group_type_Update);
        btn_update_cheking = findViewById(R.id.btn_update_check);
        backas = findViewById(R.id.backCheckingUpdate);


        firebaseDatabase = FirebaseDatabase.getInstance();
        checkingModel  = getIntent().getParcelableExtra("checking_asset");

        if(checkingModel!=null){
            assetName.setText(checkingModel.getNamaAsetCheck());
            assetCode.setText(checkingModel.getKodeAsetCheck());
            officer.setText(checkingModel.getPetugasCheck());
            radioButtonMaintained.setText(checkingModel.getMaintenanceCheck());
            radioButtonCondition.setText(checkingModel.getStatusCheck());
            radioButtontypeAsset.setText(checkingModel.getTypeCheck());
            location.setText(checkingModel.getLokasiCheck());
            date.setText(checkingModel.getTanggalCheck());

            CheckingID = checkingModel.getCheckID();

        }
        databaseReference = firebaseDatabase.getReference("Checking_Asset").child(CheckingID);


        backas.setOnClickListener(new View.OnClickListener() {
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditChecking.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });



        btn_update_cheking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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


                Map<String,Object> Checking = new HashMap<>();
                Checking.put("Name_Asset_Check",Name_Asset_Check);
                Checking.put("Asset_Code_Check",Asset_Code_Check);
                Checking.put("Officer_Check",Officer_Check);
                Checking.put("Maintenance_Status",Maintenance_Status);
                Checking.put("Condition_Asset",Condition_Asset);
                Checking.put("Type_Asset",Type_Asset);
                Checking.put("Location_Check",Location_Check);
                Checking.put("Date_Check", Date_Check);
                Checking.put("CheckingID", CheckingID);

                //Maitenance
                if (radioButtonMaintained.equals("AlReady")){
                    radioButtonMaintained = findViewById(R.id.radio_sudah);
                }else if(radioButtonMaintained.equals("Process")){
                    radioButtonMaintained = findViewById(R.id.radio_pproses);
                } else {
                    radioButtonMaintained = findViewById(R.id.radio_belum);
                }
                radioButtonMaintained.setChecked(true);

                //Condition
                if (radioButtonCondition.equals("Worthy")){
                    radioButtonCondition = findViewById(R.id.radio_worthy);
                }else if(radioButtonCondition.equals("Not Feasible")){
                    radioButtonCondition = findViewById(R.id.radio_feasible);
                } else {
                    radioButtonCondition = findViewById(R.id.radio_sure);
                }
                radioButtonCondition.setChecked(true);

                //Type

                if (radioButtontypeAsset.equals("Fixed")){
                    radioButtontypeAsset = findViewById(R.id.radio_fix);
                }else if(radioButtontypeAsset.equals("Temporary")){
                    radioButtontypeAsset = findViewById(R.id.radio_tempo);
                } else {
                    radioButtontypeAsset = findViewById(R.id.radio_loan);
                }
                radioButtontypeAsset.setChecked(true);




                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.updateChildren(Checking);
                        Toast.makeText(EditChecking.this, "Data Updated...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditChecking.this, CheckingActivity.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditChecking.this, "Update Data Failed", Toast.LENGTH_SHORT).show();
                    }
                });

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
