package com.example.itassetmanagementptbukitasam.AddData.AddAsset;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.itassetmanagementptbukitasam.ListData.AssetData.AssetDataSafetyActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.AssetModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;


public class AddAssetSafetyActivity extends AppCompatActivity {
    private EditText kode_aset, nama_aset, jenis, merk, spesifikasi, jumlah, supply, datePurchase,period, nextperiod, link;
    private Button btn_simpan,btn_purchase,btn_period, btn_EndPeriod;
    private boolean valid = true;
    private ImageView backasset;
    int mYear, mMonth, mDay;
    private FirebaseAuth firebaseAuth;
    String assetID;
    private static final String TAG = "AddAssetSafetyActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_asset_savety);


        //EditText
        kode_aset = findViewById(R.id.et_kode_aset_safety);
        nama_aset = findViewById(R.id.et_nama_aset_safety);
        supply = findViewById(R.id.et_Supply_safety);
        datePurchase = findViewById(R.id.et_datePurchase_safety);
        period = findViewById(R.id.et_datePeriod_safety);
        nextperiod = findViewById(R.id.et_dateEndPeriod_safety);
        jenis = findViewById(R.id.et_jenis_safety);
        merk = findViewById(R.id.et_merk_safety);
        spesifikasi = findViewById(R.id.et_spesifikasi_safety);
        jumlah = findViewById(R.id.et_jumlah_safety);
        link = findViewById(R.id.et_link_image_safety);

        //Button
        btn_purchase = findViewById(R.id.btn_purchase_date_safety);
        btn_period = findViewById(R.id.btn_period_date_safety);
        btn_EndPeriod = findViewById(R.id.btn_end_period_date_safety);
        backasset = findViewById(R.id.backinAsset_safety);
        btn_simpan = findViewById(R.id.btn_simpan_safety);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Asset_Safety");

        btn_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddAssetSafetyActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        datePurchase.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        btn_period.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddAssetSafetyActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        period.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        btn_EndPeriod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddAssetSafetyActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        nextperiod.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        backasset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AssetDataSafetyActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkField(kode_aset);
                checkField(nama_aset);
                checkField(jenis);
                checkField(merk);
                checkField(supply);
                checkField(spesifikasi);
                checkField(jumlah);

                String AssetCode = kode_aset.getText().toString();
                String AssetName = nama_aset.getText().toString();
                String Supplier = supply.getText().toString();
                String Purchase = datePurchase.getText().toString();
                String Begining_Period = period.getText().toString();
                String End_period = nextperiod.getText().toString();
                String Type = jenis.getText().toString();
                String Merk_Asset = merk.getText().toString();
                String Spesifikasi_Asset = spesifikasi.getText().toString();
                String Jumlah_total = jumlah.getText().toString();
                String Link_Image = link.getText().toString();

                assetID = AssetCode;

                AssetModel assetModel = new AssetModel(AssetCode, AssetName, Supplier, Purchase, Begining_Period, End_period,
                        Type, Merk_Asset, Spesifikasi_Asset, Jumlah_total,Link_Image, assetID);

                if (valid) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child(assetID).setValue(assetModel);
                            startActivity(new Intent(AddAssetSafetyActivity.this, AssetDataSafetyActivity.class));
                            AssetModel asset = snapshot.getValue(AssetModel.class);
                            Log.d(TAG, "Create asset data successfull " + asset);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.w(TAG, "Failed to read asset.", error.toException());
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
