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

import com.example.itassetmanagementptbukitasam.Adapter.OtherAdapter.LocationAdapter;
import com.example.itassetmanagementptbukitasam.ListData.OtherData.DamageActivity;
import com.example.itassetmanagementptbukitasam.ListData.OtherData.LoocationActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.KerusakanModel;
import com.example.itassetmanagementptbukitasam.model.LocationModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditLocation extends AppCompatActivity {
    boolean valid = true;
    int mYear, mMonth, mDay;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    LocationModel locationModel;
    EditText kodeAset, namaAset,jumlahHRD,  jumlahClinic,  jumlahOprasional,  jumlahSafety,
            jumlahLogistik,  jumlahHumas, jumlahLab, statusLocation;
    ImageView backlocation;
    Button btn_update_damage;
    String LocationID;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_location_asset);

        kodeAset = findViewById(R.id.kodeAsetLokasi_update);
        namaAset = findViewById(R.id.namaAsetLokasi_update);
        jumlahHRD = findViewById(R.id.amount_hrd_update);
        jumlahClinic = findViewById(R.id.amount_clinic_update);
        jumlahOprasional = findViewById(R.id.amount_oprational_update);
        jumlahSafety = findViewById(R.id.amount_safety_update);
        jumlahLogistik = findViewById(R.id.amount_logistic_update);
        jumlahHumas = findViewById(R.id.amount_humas_update);
        jumlahLab = findViewById(R.id.amount_lab_update);
        statusLocation = findViewById(R.id.status_lokasi_update);



        btn_update_damage = findViewById(R.id.btn_Update_lokasi);

        firebaseDatabase =FirebaseDatabase.getInstance();
        locationModel = getIntent().getParcelableExtra("lokasi");

        if(locationModel!=null){
            kodeAset.setText(locationModel.getKodeAsetLocation());
            namaAset.setText(locationModel.getNamaAsetLocation());
            jumlahHRD.setText(locationModel.getJumlahHRD());
            jumlahClinic.setText(locationModel.getJumlahClinic());
            jumlahOprasional.setText(locationModel.getJumlahOprasional());
            jumlahLogistik.setText(locationModel.getJumlahLogistik());
            jumlahHumas.setText(locationModel.getJumlahHumas());
            jumlahLab.setText(locationModel.getJumlahLab());
            jumlahSafety.setText(locationModel.getJumlahSafety());
            statusLocation.setText(locationModel.getStatusAsset());
            LocationID = locationModel.getLocationID();


        }
        databaseReference = firebaseDatabase.getReference("Asset_Location").child(LocationID);

        backlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoocationActivity.class));
                finish();
            }
        });


        btn_update_damage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkField(kodeAset);
                checkField(namaAset);
                checkField(jumlahHRD);
                checkField(jumlahClinic);
                checkField(jumlahOprasional);
                checkField(jumlahLogistik);
                checkField(jumlahHumas);
                checkField(jumlahLab);
                checkField(jumlahSafety);
                checkField(statusLocation);



                String Asset_Code = kodeAset.getText().toString();
                String Asset_Name = namaAset.getText().toString();
                String Amount_HRD = jumlahHRD.getText().toString();
                String Amount_CLinic = jumlahClinic.getText().toString();
                String Amount_Operational = jumlahOprasional.getText().toString();
                String Amount_Logistic = jumlahLogistik.getText().toString();
                String Amount_Humas = jumlahHumas.getText().toString();
                String Amount_Lab = jumlahLab.getText().toString();
                String Amount_Safety = jumlahSafety.getText().toString();
                String Status_Asset = statusLocation.getText().toString();


                Map<String,Object> Damage = new HashMap<>();
                Damage.put("Asset_Code",Asset_Code);
                Damage.put("Asset_Name",Asset_Name);
                Damage.put("Amount_HRD",Amount_HRD);
                Damage.put("Amount_CLinic",Amount_CLinic);
                Damage.put("Amount_Operational", Amount_Operational);
                Damage.put("Amount_Logistic",Amount_Logistic);
                Damage.put("Amount_Humas", Amount_Humas);
                Damage.put("Amount_Lab", Amount_Lab);
                Damage.put("Amount_Safety", Amount_Safety);
                Damage.put("Status_Asset", Status_Asset);
                Damage.put("LocationID", LocationID);

                if (valid) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.updateChildren(Damage);
                            Toast.makeText(EditLocation.this, "Damage Updated...", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(EditLocation.this, DamageActivity.class));

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(EditLocation.this, "Damage Updated Failed...", Toast.LENGTH_SHORT).show();
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
