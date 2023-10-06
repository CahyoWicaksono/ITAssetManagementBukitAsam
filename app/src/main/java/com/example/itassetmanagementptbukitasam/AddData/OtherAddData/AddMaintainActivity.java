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
import android.widget.Toolbar;

import androidx.annotation.NonNull;
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


public class AddMaintainActivity extends AppCompatActivity {
    Toolbar toolbar;
    int mYear, mMonth, mDay;
    boolean valid = true;
    EditText kode_maintanance, kode_aset, petugas, kondisi_peralatan, lokasi, status, tanggal;
    ImageView dokumentasi, backmaintenance;
    Button btn_simpan, maintenanceDate;
    String mainId;
    private static final String TAG = "AddMaintainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_maintanance);


        kode_maintanance = findViewById(R.id.et_kode_main);
        kode_aset = findViewById(R.id.et_kode_aset_maintenance);
        petugas = findViewById(R.id.et_petugas);
        kondisi_peralatan = findViewById(R.id.et_kondisi);
        lokasi = findViewById(R.id.et_lokasi);
        status = findViewById(R.id.et_status);
        tanggal = findViewById(R.id.et_datetime);

        backmaintenance = findViewById(R.id.backmain);

        //Button
        maintenanceDate = findViewById(R.id.maintenance_date);
        btn_simpan = findViewById(R.id.btn_simpan);



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Maintenance");



        backmaintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MaintenanceActivity.class);
                startActivity(intent);
                finish();
            }
        });

        maintenanceDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddMaintainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        tanggal.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkField(kode_maintanance);
                checkField(kode_aset);
                checkField(tanggal);
                checkField(status);
                checkField(petugas);
                checkField(lokasi);
                checkField(kondisi_peralatan);


                String Maintenance_Code = kode_maintanance.getText().toString();
                String Asset_code = kode_aset.getText().toString();
                String Officer = petugas.getText().toString();
                String Condition = kondisi_peralatan.getText().toString();
                String Location = lokasi.getText().toString();
                String Status_maintenance = status.getText().toString();
                String Date_maintenance = tanggal.getText().toString();
                mainId = Maintenance_Code;

                MaintananceModel maintananceModel = new MaintananceModel(
                    mainId, Maintenance_Code,Asset_code,Officer,Condition,Location, Status_maintenance,Date_maintenance);

                if (valid) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child(mainId).setValue(maintananceModel);
                            startActivity(new Intent(AddMaintainActivity.this, MaintenanceActivity.class));
                            MaintananceModel maintenance = snapshot.getValue(MaintananceModel.class);
                            Log.d(TAG, "Create maintenance asset successfull  " + maintenance);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.w(TAG, "Failed to read maintenance.", error.toException());

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




