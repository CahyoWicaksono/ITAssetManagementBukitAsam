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

import com.example.itassetmanagementptbukitasam.ListData.OtherData.DamageActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.KerusakanModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditDamage extends AppCompatActivity {
    boolean valid = true;
    int mYear, mMonth, mDay;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    KerusakanModel kerusakanModel;
    EditText kode_kerusakan, kode_aset, tanggal, jenis_kerusakan,tanggal_kerusakan, petugas, lokasi, solusi, penerapan_Aset;
    ImageView backdmg;
    Button damageDate,btn_update_damage;
    String damageId;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_kerusakan);

        kode_kerusakan = findViewById(R.id.et_kode_ker_up);
        kode_aset = findViewById(R.id.et_kode_aset_ker_up);
        petugas = findViewById(R.id.et_petugas_ker_up);
        jenis_kerusakan = findViewById(R.id.et_jenis_ker_up);
        lokasi = findViewById(R.id.et_lokasi_ker_up);
        solusi = findViewById(R.id.solusi_ker_up);
        tanggal_kerusakan = findViewById(R.id.tanggal_ker_up);
        penerapan_Aset = findViewById(R.id.et_penerapan_asset_up);
        backdmg = findViewById(R.id.backDmg_up);

        btn_update_damage = findViewById(R.id.btn_updatedmg);
        damageDate = findViewById(R.id.damage_date_up);

        firebaseDatabase =FirebaseDatabase.getInstance();
        kerusakanModel = getIntent().getParcelableExtra("damage");

        if(kerusakanModel!=null){
            kode_kerusakan.setText(kerusakanModel.getKode_kerusakan());
            kode_aset.setText(kerusakanModel.getKode_aset());
            tanggal.setText(kerusakanModel.getTanggal());
            jenis_kerusakan.setText(kerusakanModel.getJenis_kerusakan());
            petugas.setText(kerusakanModel.getPetugas());
            lokasi.setText(kerusakanModel.getLokasi());
            penerapan_Aset.setText(kerusakanModel.getPenerapan());
            solusi.setText(kerusakanModel.getSolusi());
            damageId = kerusakanModel.getDamageid();


        }
        databaseReference = firebaseDatabase.getReference("Damage").child(damageId);

        backdmg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DamageActivity.class));
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditDamage.this, new DatePickerDialog.OnDateSetListener() {
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


                String Damage_Code = kode_kerusakan.getText().toString();
                String Asset_code = kode_aset.getText().toString();
                String Date_damage = tanggal_kerusakan.getText().toString();
                String Damage_type = jenis_kerusakan.getText().toString();
                String Officer = petugas.getText().toString();
                String Location = lokasi.getText().toString();
                String Date_deployment = penerapan_Aset.getText().toString();
                String Solution  = solusi.getText().toString();

                Map<String,Object> Damage = new HashMap<>();
                Damage.put("Damage_Code",Damage_Code);
                Damage.put("Asset_code",Asset_code);
                Damage.put("Date_damage",Date_damage);
                Damage.put("Damage_type",Damage_type);
                Damage.put("Officer", Officer);
                Damage.put("Location",Location);
                Damage.put("Date_deployment", Date_deployment);
                Damage.put("Solution", Solution);
                Damage.put("damageId", damageId);

                if (valid) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.updateChildren(Damage);
                            Toast.makeText(EditDamage.this, "Damage Updated...", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(EditDamage.this, DamageActivity.class));

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(EditDamage.this, "Damage Updated Failed...", Toast.LENGTH_SHORT).show();
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
        petugas.setText("");
        jenis_kerusakan.setText("");
        lokasi.setText("");
        solusi.setText("");
        tanggal.setText("");
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
