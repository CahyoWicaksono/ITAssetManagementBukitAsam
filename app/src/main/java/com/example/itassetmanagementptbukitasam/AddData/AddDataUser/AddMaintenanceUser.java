package com.example.itassetmanagementptbukitasam.AddData.AddDataUser;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.itassetmanagementptbukitasam.ListData.OtherData.MaintenanceActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.MaintananceModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class AddMaintenanceUser extends AppCompatActivity {
    int mYear, mMonth, mDay;
    boolean valid = true;
    EditText kode_maintanance_user, kode_aset_user, petugas_user, kondisi_peralatan_user, lokasi_user, status_user, tanggal_user;
    ImageView dokumentasi, backmaintenance;
    Button btn_simpan, maintenanceDate;
    String mainId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_maintanance_user);

        kode_maintanance_user = findViewById(R.id.et_kode_main_user);
        kode_aset_user = findViewById(R.id.et_kode_aset_user);
        petugas_user = findViewById(R.id.et_petugas_user);
        kondisi_peralatan_user = findViewById(R.id.et_kondisi_user);
        lokasi_user= findViewById(R.id.et_lokasi_user);
        status_user = findViewById(R.id.et_status_user);
        tanggal_user = findViewById(R.id.et_datetime_user);


        maintenanceDate = findViewById(R.id.maintenance_date_user);
        btn_simpan = findViewById(R.id.btn_simpan_user);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Maintenance");


        maintenanceDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddMaintenanceUser.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        tanggal_user.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkField(kode_maintanance_user);
                checkField(kode_aset_user);
                checkField(tanggal_user);
                checkField(status_user);
                checkField(petugas_user);
                checkField(lokasi_user);
                checkField(kondisi_peralatan_user);

                String Maintenance_Code = kode_maintanance_user.getText().toString();
                String Asset_code = kode_aset_user.getText().toString();
                String Officer = petugas_user.getText().toString();
                String Condition = kondisi_peralatan_user.getText().toString();
                String Location = lokasi_user.getText().toString();
                String Status_maintenance = status_user.getText().toString();
                String Date_maintenance = tanggal_user.getText().toString();
                mainId = Maintenance_Code;

                MaintananceModel maintananceModel = new MaintananceModel(
                        mainId, Maintenance_Code,Asset_code,Officer,Condition,Location, Status_maintenance,Date_maintenance);

                if (valid) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child(mainId).setValue(maintananceModel);
                            Toast.makeText(AddMaintenanceUser.this, "Maintenance Added...", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddMaintenanceUser.this, MaintenanceActivity.class));

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(AddMaintenanceUser.this, "Create Data Failed", Toast.LENGTH_SHORT).show();

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




