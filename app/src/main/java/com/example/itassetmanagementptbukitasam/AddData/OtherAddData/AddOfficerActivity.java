package com.example.itassetmanagementptbukitasam.AddData.OtherAddData;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.itassetmanagementptbukitasam.ListData.OtherData.OfficerActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.OfficerModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddOfficerActivity extends AppCompatActivity {
    private EditText kode_petugas, email, no_telp, nama_petugas, Devisi,alamat;
    private Button btn_simpan;
    boolean valid = true;
    private String officerId;
    private ImageView backoff;
    private static final String TAG = "AddOfficerActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_officer);

        kode_petugas = findViewById(R.id.et_officercode);
        email = findViewById(R.id.et_email);
        no_telp = findViewById(R.id.et_notelp);
        nama_petugas = findViewById(R.id.et_name);
        Devisi = findViewById(R.id.et_Devisi);
        alamat = findViewById(R.id.et_alamat);
        btn_simpan = findViewById(R.id.btn_simpan);
        backoff =findViewById(R.id.backoff);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Officer");

        backoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OfficerActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkField(kode_petugas);
                checkField(email);
                checkField(no_telp);
                checkField(nama_petugas);
                checkField(Devisi);

                String Officer_code  = kode_petugas.getText().toString();
                String Emailadd  = email.getText().toString();
                String Phone_number = no_telp.getText().toString();
                String Officer_name = nama_petugas.getText().toString();
                String Division = Devisi.getText().toString();
                String Address = alamat.getText().toString();
                officerId = Officer_name;

                OfficerModel officerModel = new OfficerModel(Officer_code, Officer_name,Emailadd, Phone_number,Division,Address,officerId);

                if (valid) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child(officerId).setValue(officerModel);
                            startActivity(new Intent(AddOfficerActivity.this, OfficerActivity.class));
                            OfficerModel officerModel = snapshot.getValue(OfficerModel.class);
                            Log.d(TAG, "Create officer asset successfull  " + officerModel);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.w(TAG, "Failed to read officer.", error.toException());
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
