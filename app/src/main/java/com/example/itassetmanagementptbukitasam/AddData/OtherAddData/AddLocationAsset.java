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
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.itassetmanagementptbukitasam.Admin.HomeAdminActivity;
import com.example.itassetmanagementptbukitasam.ListData.OtherData.LoocationActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.LocationModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AddLocationAsset extends AppCompatActivity {
    boolean valid = true;
    EditText kodeAset, namaAset,jumlahHRD,  jumlahClinic,  jumlahOprasional,  jumlahSafety,
            jumlahLogistik,  jumlahHumas, jumlahLab, statusLocation;
    ImageView backlocation;
    Button btn_simpan;
    String LocationID;
    private static final String TAG = "AddLocationAsset";


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_location_asset);


        kodeAset = findViewById(R.id.kodeAsetLokasi);
        namaAset = findViewById(R.id.namaAsetLokasi);
        jumlahHRD = findViewById(R.id.amount_hrd);
        jumlahClinic = findViewById(R.id.amount_clinic);
        jumlahOprasional = findViewById(R.id.amount_oprational);
        jumlahSafety = findViewById(R.id.amount_safety);
        jumlahLogistik = findViewById(R.id.amount_logistic);
        jumlahHumas = findViewById(R.id.amount_humas);
        jumlahLab = findViewById(R.id.amount_lab);
        statusLocation = findViewById(R.id.status_lokasi);


        backlocation = findViewById(R.id.back_location);

        btn_simpan = findViewById(R.id.btn_simpan_lokasi);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Asset_Location");



        backlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoocationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                LocationID = Asset_Code;

                LocationModel assetLocationModel = new LocationModel(
                    LocationID, Asset_Code, Asset_Name, Amount_HRD, Amount_CLinic,Amount_Operational, Amount_Logistic,
                        Amount_Humas, Amount_Lab, Amount_Safety, Status_Asset);

                if (valid) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child(LocationID).setValue(assetLocationModel);
                            startActivity(new Intent(AddLocationAsset.this, HomeAdminActivity.class));
                            LocationModel assetLocationModel = snapshot.getValue(LocationModel.class);
                            Log.d(TAG, "Location asset successfull  " + assetLocationModel);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.w(TAG, "Failed to read .", error.toException());

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




