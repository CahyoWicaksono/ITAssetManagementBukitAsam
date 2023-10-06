package com.example.itassetmanagementptbukitasam.ChangeData.OtherEdit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

import java.util.HashMap;
import java.util.Map;

public class EditOfficer extends AppCompatActivity {
    EditText kode_petugas,nama,devisi,alamat,email,notelp;
    Button btn_update_off;
    boolean valid = true;
    String OfficerID;
    ImageView back, foto_petugas, backoff;
    OfficerModel officerModel;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_officer);

        kode_petugas = findViewById(R.id.et_officercode_up);
        nama = findViewById(R.id.et_name_up);
        devisi = findViewById(R.id.et_Devisi_up);
        alamat = findViewById(R.id.et_alamat_up);
        email = findViewById(R.id.et_email_up);
        notelp = findViewById(R.id.et_notelp_up);
        backoff = findViewById(R.id.backoff_up);

        btn_update_off = findViewById(R.id.btn_update_off);
        back = findViewById(R.id.backoff_up);

        firebaseDatabase = FirebaseDatabase.getInstance();
        officerModel = getIntent().getParcelableExtra("officer");

        if (officerModel != null) {
            kode_petugas.setText(officerModel.getKode_petugas());
            email.setText(officerModel.getEmail());
            notelp.setText(officerModel.getNo_telp());
            nama.setText(officerModel.getNama_petugas());
            devisi.setText(officerModel.getDevisi());
            alamat.setText(officerModel.getAlamat_petugas());

            OfficerID = officerModel.getOfficerId();

        }
        databaseReference = firebaseDatabase.getReference("Officer").child(OfficerID);


        backoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), OfficerActivity.class));
                finish();
            }
        });

        btn_update_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(kode_petugas);
                checkField(nama);
                checkField(devisi);
                checkField(email);
                checkField(notelp);
                checkField(alamat);

                String Officer_code  = kode_petugas.getText().toString();
                String Emailadd  = email.getText().toString();
                String Phone_number = notelp.getText().toString();
                String Officer_name = nama.getText().toString();
                String Division = devisi.getText().toString();
                String Address = alamat.getText().toString();

                Map<String, Object> officer = new HashMap<>();
                officer.put("Officer_code", Officer_code);
                officer.put("email", Emailadd);
                officer.put("Phone_number", Phone_number);
                officer.put("Officer_name", Officer_name);
                officer.put("Division", Division);
                officer.put("Address", Address);
                officer.put("OfficerID", OfficerID);


                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.updateChildren(officer);
                        Toast.makeText(EditOfficer.this, "Software Updated...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditOfficer.this, OfficerActivity.class));


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditOfficer.this, "Officer Updated Failed...", Toast.LENGTH_SHORT).show();

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



