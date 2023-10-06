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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.itassetmanagementptbukitasam.ListData.OtherData.RepairActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.RepairModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;


public class AddRepairActivity extends AppCompatActivity {
    int mYear, mMonth, mDay;
    boolean valid = true;
    EditText kode_perbaikan, kode_aset, petugas, lokasi, status, tanggal;
    ImageView backrepair;
    Button btn_simpan, repairDate;
    String repairid;
    private static final String TAG = "AddRepairActivity";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_repair);

        kode_perbaikan = findViewById(R.id.et_kode_repair);
        kode_aset = findViewById(R.id.et_kode_aset_repair);
        petugas = findViewById(R.id.et_petugas_repair);
        lokasi = findViewById(R.id.et_lokasi_repair);
        status = findViewById(R.id.et_status_repair);
        tanggal = findViewById(R.id.et_datetime_repair);
        backrepair = findViewById(R.id.backrep);

        repairDate = findViewById(R.id.repair_date);
        btn_simpan = findViewById(R.id.btn_simpan);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Repair");


        backrepair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RepairActivity.class);
                startActivity(intent);
                finish();
            }
        });

        repairDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddRepairActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                checkField(kode_perbaikan);
                checkField(kode_aset);
                checkField(tanggal);
                checkField(status);
                checkField(petugas);
                checkField(lokasi);


                String Repair_Code = kode_perbaikan.getText().toString();
                String Asset_code = kode_aset.getText().toString();
                String Officer = petugas.getText().toString();
                String Location = lokasi.getText().toString();
                String Status_repair = status.getText().toString();
                String Date = tanggal.getText().toString();


                repairid = Repair_Code;

                RepairModel repairModel = new RepairModel(repairid, Repair_Code, Asset_code, Date, Status_repair, Officer,Location);

                if (valid) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child(repairid).setValue(repairModel);
                            Toast.makeText(AddRepairActivity.this, "Repair Added...", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddRepairActivity.this, RepairActivity.class));
                            RepairModel repairModel = snapshot.getValue(RepairModel.class);
                            Log.d(TAG, "Create repair asset successfull  " + repairModel);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.w(TAG, "Failed to read repair.", error.toException());
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




