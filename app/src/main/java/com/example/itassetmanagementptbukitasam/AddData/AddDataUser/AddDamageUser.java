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
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.itassetmanagementptbukitasam.ListData.OtherData.DamageActivity;
import com.example.itassetmanagementptbukitasam.ListDataUser.DamageUserActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.KerusakanModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;


public class AddDamageUser extends AppCompatActivity {
    Toolbar toolbar;
    boolean valid = true;
    int mYear, mMonth, mDay;
    EditText kode_kerusakan, kode_aset, tanggal_kerusakan, jenis_kerusakan, petugas, lokasi, solusi,penerapan_Aset;
    ImageView  dokumentasi, backdamage;
    Button btn_simpan, damageDate, deployDate;
    String damageId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_kerusakan);

        kode_kerusakan = findViewById(R.id.et_kode_ker_user);
        kode_aset = findViewById(R.id.et_kode_aset_ker_user);
        petugas = findViewById(R.id.et_petugas_ker_user);
        jenis_kerusakan = findViewById(R.id.et_jenisK_ker_user);
        lokasi = findViewById(R.id.et_lokasi_ker_user);
        solusi = findViewById(R.id.solusi_ker_user);
        tanggal_kerusakan = findViewById(R.id.tanggal_ker_user);
        deployDate = findViewById(R.id.penerapan_date_user);
        penerapan_Aset = findViewById(R.id.et_penerapan_asset_user);
        backdamage = findViewById(R.id.backinDamage_user);

        btn_simpan = findViewById(R.id.btn_simpan_ker_user);
        damageDate = findViewById(R.id.damage_date_user);




        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Damage");


        backdamage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DamageUserActivity.class);
                startActivity(intent);
                finish();
            }
        });


        damageDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddDamageUser.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        tanggal_kerusakan.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        deployDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddDamageUser.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        penerapan_Aset.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });



        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkField(kode_kerusakan);
                checkField(kode_aset);
                checkField(tanggal_kerusakan);
                checkField(jenis_kerusakan);
                checkField(petugas);
                checkField(lokasi);
                checkField(solusi);

                String Damage_Code = kode_kerusakan.getText().toString();
                String Asset_code = kode_aset.getText().toString();
                String Date_damage = tanggal_kerusakan.getText().toString();
                String Damage_type = jenis_kerusakan.getText().toString();
                String Officer = petugas.getText().toString();
                String Location = lokasi.getText().toString();
                String Date_deployment = penerapan_Aset.getText().toString();
                String Solution  = solusi.getText().toString();

                damageId = Damage_Code;

                KerusakanModel kerusakanModel = new KerusakanModel(damageId,Damage_Code,Asset_code,Date_damage,Damage_type,Officer,
                        Location,Date_deployment,Solution);


                if (valid) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child(damageId).setValue(kerusakanModel);
                            Toast.makeText(AddDamageUser.this, "Damage Added...", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddDamageUser.this, DamageUserActivity.class));

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(AddDamageUser.this, "Create Data Failed", Toast.LENGTH_SHORT).show();
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




