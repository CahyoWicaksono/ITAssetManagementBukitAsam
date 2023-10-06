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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.itassetmanagementptbukitasam.ListData.OtherData.DamageLogistikActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.SDM.HomeSDM;
import com.example.itassetmanagementptbukitasam.model.ComplaintModel;
import com.example.itassetmanagementptbukitasam.model.KerusakanLogistikModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class AddDamageLogistic extends AppCompatActivity {

    Toolbar toolbar;
    boolean valid = true;
    int mYear, mMonth, mDay;
    EditText namaAset, kode_aset, tanggal_kerusakan, jenis_kerusakan,
            solusi,tanggal_penerimaan;
    ImageView  backdamage;
    Button btn_simpan, damageDate, purchaseDate;
    String damageLogId;
    private static final String TAG = "AddDamageLogistic";




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_kerusakan_logistic);

        namaAset = findViewById(R.id.et_kode_ker_logistic);
        kode_aset = findViewById(R.id.et_kode_aset_ker_logistic);
        jenis_kerusakan = findViewById(R.id.et_jenisK_ker_logistic);
        solusi = findViewById(R.id.solusi_ker_logistic);
        tanggal_kerusakan = findViewById(R.id.tanggal_ker_logistic);
        tanggal_penerimaan = findViewById(R.id.et_penerapan_asset_logistic);
        backdamage = findViewById(R.id.backinDamage_logistic);

        btn_simpan = findViewById(R.id.btn_simpan_ker_logistic);
        damageDate = findViewById(R.id.damage_date_logistic);
        purchaseDate = findViewById(R.id.penerimaan_date_logistic);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Damage_Logistic");



        backdamage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        damageDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddDamageLogistic.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        tanggal_kerusakan.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        purchaseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddDamageLogistic.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        tanggal_penerimaan.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkField(namaAset);
                checkField(kode_aset);
                checkField(tanggal_kerusakan);
                checkField(jenis_kerusakan);
                checkField(tanggal_penerimaan);
                checkField(solusi);

                String Asset_Name = namaAset.getText().toString();
                String Asset_Code = kode_aset.getText().toString();
                String Date_damage = tanggal_kerusakan.getText().toString();
                String Damage_Type = jenis_kerusakan.getText().toString();
                String Date_Purchase = tanggal_penerimaan.getText().toString();
                String Solution = solusi.getText().toString();


                damageLogId = Asset_Name;

                KerusakanLogistikModel kerusakanLogistikModel = new KerusakanLogistikModel(
                        damageLogId, Asset_Name,Asset_Code,Date_damage,Damage_Type,Date_Purchase,Solution);

                if (valid) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child(damageLogId).setValue(kerusakanLogistikModel);
                            startActivity(new Intent(AddDamageLogistic.this, DamageLogistikActivity.class));
                            ComplaintModel complaint = snapshot.getValue(ComplaintModel.class);
                            Log.d(TAG, "Create maintenance asset successfull  " + complaint);
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
