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
import com.example.itassetmanagementptbukitasam.ListDataUser.DamageUserActivity;
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

public class EditDamageUser extends AppCompatActivity {
    boolean valid = true;
    int mYear, mMonth, mDay;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    KerusakanModel kerusakanModel;
    EditText kode_kerusakan_updateUser, kode_aset_updateUser, tanggal_updateUser,
            jenis_kerusakan_updateUser,tanggal_kerusakan_updateUser, petugas_updateUser, lokasi_updateUser, solusi_updateUser, penerapan_Aset_updateUser;
    ImageView backdmg;
    Button damageDate,btn_update_damage;
    String damageId;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_kerusakan);

        kode_kerusakan_updateUser = findViewById(R.id.et_kode_ker_up_user);
        kode_aset_updateUser = findViewById(R.id.et_kode_aset_ker_up_user);
        petugas_updateUser = findViewById(R.id.et_petugas_ker_up_user);
        jenis_kerusakan_updateUser = findViewById(R.id.et_jenis_ker_up_user);
        lokasi_updateUser = findViewById(R.id.et_lokasi_ker_up_user);
        solusi_updateUser = findViewById(R.id.solusi_ker_up_user);
        tanggal_kerusakan_updateUser = findViewById(R.id.tanggal_ker_up_user);
        penerapan_Aset_updateUser = findViewById(R.id.et_penerapan_asset_up_user);
        backdmg = findViewById(R.id.backDmg_up_user);

        btn_update_damage = findViewById(R.id.btn_updatedmg_user);
        damageDate = findViewById(R.id.damage_date_up_user);

        firebaseDatabase =FirebaseDatabase.getInstance();
        kerusakanModel = getIntent().getParcelableExtra("damage");

        if(kerusakanModel!=null){
            kode_kerusakan_updateUser.setText(kerusakanModel.getKode_kerusakan());
            kode_aset_updateUser.setText(kerusakanModel.getKode_aset());
            tanggal_updateUser.setText(kerusakanModel.getTanggal());
            jenis_kerusakan_updateUser.setText(kerusakanModel.getJenis_kerusakan());
            petugas_updateUser.setText(kerusakanModel.getPetugas());
            lokasi_updateUser.setText(kerusakanModel.getLokasi());
            penerapan_Aset_updateUser.setText(kerusakanModel.getPenerapan());
            solusi_updateUser.setText(kerusakanModel.getSolusi());

            damageId = kerusakanModel.getDamageid();


        }
        databaseReference = firebaseDatabase.getReference("Damage").child(damageId);

        backdmg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DamageUserActivity.class));
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditDamageUser.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        tanggal_updateUser.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        btn_update_damage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(kode_kerusakan_updateUser);
                checkField(kode_aset_updateUser);
                checkField(tanggal_updateUser);
                checkField(solusi_updateUser);
                checkField(petugas_updateUser);
                checkField(lokasi_updateUser);
                checkField(jenis_kerusakan_updateUser);


                String damage_user = kode_kerusakan_updateUser.getText().toString();
                String aset_code = kode_aset_updateUser.getText().toString();
                String date_damage = tanggal_kerusakan_updateUser.getText().toString();
                String type = jenis_kerusakan_updateUser.getText().toString();
                String officer = petugas_updateUser.getText().toString();
                String location = lokasi_updateUser.getText().toString();
                String date_deployment = penerapan_Aset_updateUser.getText().toString();
                String solution  = solusi_updateUser.getText().toString();

                Map<String,Object> Damage = new HashMap<>();
                Damage.put("Damage_Code",damage_user);
                Damage.put("Asset_code",aset_code);
                Damage.put("Date_damage",date_damage);
                Damage.put("Damage_type",type);
                Damage.put("Officer", officer);
                Damage.put("Location",location);
                Damage.put("Date_deployment", date_deployment);
                Damage.put("Solution", solution);
                Damage.put("damageId", damageId);

                if (valid) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.updateChildren(Damage);
                            Toast.makeText(EditDamageUser.this, "Damage Updated...", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(EditDamageUser.this, DamageActivity.class));

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(EditDamageUser.this, "Damage Updated Failed...", Toast.LENGTH_SHORT).show();

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
