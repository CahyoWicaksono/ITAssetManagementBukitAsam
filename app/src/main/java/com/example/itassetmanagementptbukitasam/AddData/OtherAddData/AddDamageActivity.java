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

import com.example.itassetmanagementptbukitasam.ListData.OtherData.DamageActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.KerusakanModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Calendar;


public class AddDamageActivity extends AppCompatActivity {
    Toolbar toolbar;
    boolean valid = true;
    int mYear, mMonth, mDay;
    EditText kode_kerusakan, kode_aset, tanggal_kerusakan, jenis_kerusakan, petugas, lokasi, solusi,penerapan_Aset;
    ImageView  dokumentasi, backdamage;
    Button btn_simpan, damageDate, deployDate;
    String damageId;
    private static final String TAG = "AddDamageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_kerusakan);

        kode_kerusakan = findViewById(R.id.et_kode_ker);
        kode_aset = findViewById(R.id.et_kode_aset_ker);
        petugas = findViewById(R.id.et_petugas_ker);
        jenis_kerusakan = findViewById(R.id.et_jenisK_ker);
        lokasi = findViewById(R.id.et_lokasi_ker);
        solusi = findViewById(R.id.solusi_ker);
        tanggal_kerusakan = findViewById(R.id.tanggal_ker);
        deployDate = findViewById(R.id.penerapan_date);
        penerapan_Aset = findViewById(R.id.et_penerapan_asset);
        backdamage = findViewById(R.id.backinDamage);

        btn_simpan = findViewById(R.id.btn_simpan_ker);
        damageDate = findViewById(R.id.damage_date);



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Damage");


        backdamage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DamageActivity.class);
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddDamageActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddDamageActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                            startActivity(new Intent(AddDamageActivity.this, DamageActivity.class));
                            KerusakanModel kerusakan = snapshot.getValue(KerusakanModel.class);
                            Log.d(TAG, "Create damage asset successfull  " + kerusakan);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.w(TAG, "Failed to read damage asset.", error.toException());
                        }

                    });
                }

            }
        });


    }

    private void ClearAll()
    {
        kode_kerusakan.setText("");
        kode_aset.setText("");
        tanggal_kerusakan.setText("");
        jenis_kerusakan.setText("");
        petugas.setText("");
        lokasi.setText("");
        solusi.setText("");

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




