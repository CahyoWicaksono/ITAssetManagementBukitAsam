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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.itassetmanagementptbukitasam.ListData.OtherData.DamageLogistikActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.KerusakanLogistikModel;
import com.example.itassetmanagementptbukitasam.model.KerusakanModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditDamageLogistic extends AppCompatActivity {
    boolean valid = true;
    int mYear, mMonth, mDay;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    KerusakanLogistikModel kerusakanLogistikModel;
    EditText kode_kerusakan, kode_aset, tanggal, jenis_kerusakan,tanggal_kerusakan, petugas, lokasi, solusi, penerapan_Aset;
    ImageView backdmg;
    Button damageDate,purchase, btn_update_damage;
    String damageLogId;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_kerusakan_logistic);

        kode_kerusakan = findViewById(R.id.et_kode_ker_logistic_update);
        kode_aset = findViewById(R.id.et_kode_aset_ker_logistic_update);
        jenis_kerusakan = findViewById(R.id.et_jenisK_ker_logistic_update);
        solusi = findViewById(R.id.solusi_ker_logistic_update);
        tanggal_kerusakan = findViewById(R.id.tanggal_ker_logistic_update);
        penerapan_Aset = findViewById(R.id.et_penerapan_asset_logistic_update);


        backdmg = findViewById(R.id.backinDamage_logistic_update);

        btn_update_damage = findViewById(R.id.btn_simpan_ker_logistic_update);
        damageDate = findViewById(R.id.damage_date_logistic_update);
        purchase = findViewById(R.id.penerapan_date_logistic);


        firebaseDatabase =FirebaseDatabase.getInstance();
        kerusakanLogistikModel = getIntent().getParcelableExtra("damage_logistic");

        if(kerusakanLogistikModel!=null){
            kode_kerusakan.setText(kerusakanLogistikModel.getNamaKerusakan());
            kode_aset.setText(kerusakanLogistikModel.getKodeasetKerusakan());
            tanggal.setText(kerusakanLogistikModel.getTanggalKerusakan());
            jenis_kerusakan.setText(kerusakanLogistikModel.getTipeKerusakan());
            penerapan_Aset.setText(kerusakanLogistikModel.getTanggalPurchaseKerusakan());
            solusi.setText(kerusakanLogistikModel.getSolusiKerusakan());
            damageLogId = kerusakanLogistikModel.getDamageLogistikID();


        }
        databaseReference = firebaseDatabase.getReference("Damage_Logistik").child(damageLogId);

        backdmg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DamageLogistikActivity.class));
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditDamageLogistic.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        tanggal.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        btn_update_damage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(kode_kerusakan);
                checkField(kode_aset);
                checkField(tanggal);
                checkField(solusi);
                checkField(petugas);
                checkField(lokasi);
                checkField(jenis_kerusakan);


                String Asset_Name = kode_kerusakan.getText().toString();
                String Asset_Code = kode_aset.getText().toString();
                String Date_damage = tanggal_kerusakan.getText().toString();
                String Damage_Type = jenis_kerusakan.getText().toString();
                String Date_Purchase = penerapan_Aset.getText().toString();
                String Solution = solusi.getText().toString();

                Map<String,Object> Damage = new HashMap<>();
                Damage.put("Asset_Name",Asset_Name);
                Damage.put("Asset_Code",Asset_Code);
                Damage.put("Date_damage",Date_damage);
                Damage.put("Damage_Type",Damage_Type);
                Damage.put("Date_Purchase", Date_Purchase);
                Damage.put("Solution", Solution);
                Damage.put("damageLogId", damageLogId);

                if (valid) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.updateChildren(Damage);
                            Toast.makeText(EditDamageLogistic.this, "Damage Updated...", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(EditDamageLogistic.this, DamageLogistikActivity.class));

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(EditDamageLogistic.this, "Damage Updated Failed...", Toast.LENGTH_SHORT).show();
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
        super.onBackPressed();
    }
}
