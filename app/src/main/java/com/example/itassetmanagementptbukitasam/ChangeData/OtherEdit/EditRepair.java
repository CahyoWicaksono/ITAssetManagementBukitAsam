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

import com.example.itassetmanagementptbukitasam.ListData.OtherData.RepairActivity;
import com.example.itassetmanagementptbukitasam.ListDataUser.RepairUserActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.RepairModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditRepair extends AppCompatActivity {

    boolean valid = true;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RepairModel repairModel;
    EditText kode_perbaikan, kode_aset, petugas, lokasi, status, tanggal;
    ImageView btn_Galery, btn_camera, dokumentasi,backrep;
    Button damageDate,btn_update_damage;
    String repairID;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_repair);

        kode_perbaikan = findViewById(R.id.et_kode_repair_update);
        kode_aset = findViewById(R.id.et_kode_aset_update);
        petugas = findViewById(R.id.et_petugas_repair_update);
        lokasi = findViewById(R.id.et_lokasi_repair_update);
        status = findViewById(R.id.et_status_repair_update);
        tanggal = findViewById(R.id.et_datetime_update);
        backrep = findViewById(R.id.backrep_up_update);


        btn_update_damage = findViewById(R.id.btn_updaterep);
        damageDate = findViewById(R.id.damage_date_up);

        firebaseDatabase =FirebaseDatabase.getInstance();
        repairModel = getIntent().getParcelableExtra("repair");

        if(repairModel!=null){
            kode_perbaikan.setText(repairModel.getKode_perbaikan());
            kode_aset.setText(repairModel.getKode_aset());
            petugas.setText(repairModel.getPetugas());
            status.setText(repairModel.getStatus());
            lokasi.setText(repairModel.getLokasi());
            tanggal.setText(repairModel.getTanggal());
            repairID = repairModel.getRepairID();


        }
        databaseReference = firebaseDatabase.getReference("Repair").child(repairID);


        backrep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RepairUserActivity.class));
                finish();
            }
        });




        btn_update_damage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

                Map<String,Object> repair = new HashMap<>();
                repair.put("Repair_Code",Repair_Code);
                repair.put("Asset_code",Asset_code);
                repair.put("Officer",Officer);
                repair.put("Location", Location);
                repair.put("Status_repair", Status_repair);
                repair.put("Date",Date);
                repair.put("repairID",repairID);




                if (valid) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.updateChildren(repair);
                            Toast.makeText(EditRepair.this, "Repair Updated...", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(EditRepair.this, RepairActivity.class));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(EditRepair.this, "Repair Update Failed...", Toast.LENGTH_SHORT).show();
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
