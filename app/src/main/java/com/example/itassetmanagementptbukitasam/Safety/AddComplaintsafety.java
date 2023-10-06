package com.example.itassetmanagementptbukitasam.Safety;

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

import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.SDM.HomeSDM;
import com.example.itassetmanagementptbukitasam.model.ComplaintModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;


public class AddComplaintsafety extends AppCompatActivity {
    Toolbar toolbar;
    int mYear, mMonth, mDay;
    boolean valid = true;
    EditText namaPengadu, tanggalPengadu,Divisi, NamaAset,Problem;
    ImageView backmaintenance;
    Button btn_simpan, maintenanceDate;
    String ComplaintID;
    private static final String TAG = "AddComplaintsafety";


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_complain_safety);


        namaPengadu = findViewById(R.id.et_name_complaint_safety);
        tanggalPengadu = findViewById(R.id.et_date_complaint_safety);
        Divisi = findViewById(R.id.et_division_complaint_safety);
        NamaAset = findViewById(R.id.et_name_asset_complaint_safety);
        Problem = findViewById(R.id.et_problem_complaint_safety);


        backmaintenance = findViewById(R.id.back_complaint_safety);

        btn_simpan = findViewById(R.id.btn_submit_safety);



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Complaint");



        backmaintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeSafety.class);
                startActivity(intent);
                finish();
            }
        });

        tanggalPengadu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddComplaintsafety.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        tanggalPengadu.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkField(namaPengadu);
                checkField(tanggalPengadu);
                checkField(Divisi);
                checkField(NamaAset);
                checkField(Problem);

                String Nama_Pengadu = namaPengadu.getText().toString();
                String Tanggal_Pengaduan = tanggalPengadu.getText().toString();
                String Divisi_Pengaduan = Divisi.getText().toString();
                String Nama_Asset_Pengaduan = NamaAset.getText().toString();
                String problem_Pengaduan = Problem.getText().toString();
                ComplaintID = Nama_Pengadu;

                ComplaintModel complaintModel = new ComplaintModel(
                    ComplaintID, Nama_Pengadu,Tanggal_Pengaduan,Divisi_Pengaduan,Nama_Asset_Pengaduan,problem_Pengaduan);

                if (valid) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child(ComplaintID).setValue(complaintModel);
                            startActivity(new Intent(AddComplaintsafety.this, HomeSafety.class));
                            ComplaintModel complaintModel = snapshot.getValue(ComplaintModel.class);
                            Log.d(TAG, "Complaint asset successfull  " + complaintModel);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.w(TAG, "Failed to read .", error.toException());

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




