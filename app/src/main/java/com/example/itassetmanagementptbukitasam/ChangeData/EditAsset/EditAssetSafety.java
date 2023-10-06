package com.example.itassetmanagementptbukitasam.ChangeData.EditAsset;
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

import com.example.itassetmanagementptbukitasam.ListData.AssetData.AssetDataSafetyActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.AssetModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditAssetSafety extends AppCompatActivity {
    EditText kode_aset, nama_aset, jenis, merk, link, spesifikasi, jumlah, supply, datePurchase,period,nextPeriod,purchase;
    Button btn_update_aset,btn_purchase,btn_period, btn_EndPeriod;
    boolean valid = true;
    ImageView backasset, backas;
    AssetModel assetModel;
    FirebaseDatabase firebaseDatabase;
    int mYear, mMonth, mDay;
    DatabaseReference databaseReference;
    String assetID;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_asset_safety);

        nama_aset = findViewById(R.id.et_nama_aset_update_safety);
        kode_aset = findViewById(R.id.et_kode_aset_update_safety);
        merk = findViewById(R.id.et_merk_update_safety);
        jenis = findViewById(R.id.et_jenis_update_safety);
        spesifikasi = findViewById(R.id.et_spesifikasi_update_safety);
        jumlah = findViewById(R.id.et_jumlah_update_safety);
        supply = findViewById(R.id.et_supplier_update_safety);
        period = findViewById(R.id.et_datePeriod_update_safety);
        nextPeriod = findViewById(R.id.et_dateEndPeriod_update_safety);
        purchase = findViewById(R.id.et_datePurchase_update_safety);
        link = findViewById(R.id.et_link_asset_update_safety);


        backas = findViewById(R.id.backAs_update_safety);
        btn_update_aset = findViewById(R.id.btn_update_aset_update_safety);

        btn_purchase = findViewById(R.id.purchase_dateUp_update_safety);
        btn_EndPeriod = findViewById(R.id.end_period_date_update_safety);
        btn_period = findViewById(R.id.period_date_update_safety);


        firebaseDatabase =FirebaseDatabase.getInstance();
        assetModel = getIntent().getParcelableExtra("asset");

        if(assetModel!=null){
            nama_aset.setText(assetModel.getNama_aset());
            kode_aset.setText(assetModel.getKode_aset());
            supply.setText(assetModel.getSupplier());
            purchase.setText(assetModel.getPurchase());
            period.setText(assetModel.getPeriod_awa());
            nextPeriod.setText(assetModel.getPeriod_akhir());
            merk.setText(assetModel.getMerk());
            jenis.setText(assetModel.getJenis());
            spesifikasi.setText(assetModel.getSpesifikasi());
            jumlah.setText(assetModel.getJumlah());
            link.setText(assetModel.getAssetLink());

            assetID = assetModel.getAssetID();

        }
        databaseReference = firebaseDatabase.getReference("Asset_Safety").child(assetID);


        backas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AssetDataSafetyActivity.class));
                finish();
            }
        });
        btn_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditAssetSafety.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        purchase.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditAssetSafety.this, new DatePickerDialog.OnDateSetListener() {
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditAssetSafety.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        nextPeriod.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        btn_update_aset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(nama_aset);
                checkField(kode_aset);
                checkField(merk);
                checkField(jenis);
                checkField(spesifikasi);
                checkField(jumlah);

                String AssetCode = kode_aset.getText().toString();
                String AssetName = nama_aset.getText().toString();
                String Supplier = supply.getText().toString();
                String Purchase = datePurchase.getText().toString();
                String Begining_Period = period.getText().toString();
                String End_period = nextPeriod.getText().toString();
                String Type = jenis.getText().toString();
                String Merk_Asset = merk.getText().toString();
                String Spesifikasi_Asset = spesifikasi.getText().toString();
                String Jumlah_total = jumlah.getText().toString();
                String Link_Image = link.getText().toString();


                Map<String,Object> Asset = new HashMap<>();
                Asset.put("AssetCode",AssetCode);
                Asset.put("AssetName",AssetName);
                Asset.put("Supplier", Supplier);
                Asset.put("Purchase",Purchase);
                Asset.put("Begining_Period",Begining_Period);
                Asset.put("End_period",End_period);
                Asset.put("Type",Type);
                Asset.put("Merk_Asset",Merk_Asset);
                Asset.put("Spesifikasi_Asset", Spesifikasi_Asset);
                Asset.put("Jumlah_total", Jumlah_total);
                Asset.put("Link_Image", Link_Image);

                Asset.put("assetID", assetID);


                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.updateChildren(Asset);
                        Toast.makeText(EditAssetSafety.this, "Asset Data Updated...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditAssetSafety.this, AssetDataSafetyActivity.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditAssetSafety.this, "Update Data Failed", Toast.LENGTH_SHORT).show();
                    }
                });

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
