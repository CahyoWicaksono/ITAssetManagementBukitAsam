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

import com.example.itassetmanagementptbukitasam.Logistic.HomeLogisticActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.OrderModel;
import com.example.itassetmanagementptbukitasam.model.PendingAssetModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;


public class AddPendingAsset extends AppCompatActivity {
    Toolbar toolbar;
    int mYear, mMonth, mDay;
    boolean valid = true;
    EditText namapending, tanggalpending,kategori,supplier,jumlah, kodeaset, deskripsi;
    ImageView backmaintenance;
    Button btn_simpan, maintenanceDate;
    String pendingID;
    private static final String TAG = "AddPendingAsset";


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_pending_aset);

        namapending = findViewById(R.id.et_name_asset_pending);
        kodeaset = findViewById(R.id.et_kode_aset_pending);
        tanggalpending = findViewById(R.id.et_date_pending);
        kategori = findViewById(R.id.et_categori_pending);
        supplier = findViewById(R.id.et_supplier_pending);
        jumlah = findViewById(R.id.et_amount_pending);
        deskripsi = findViewById(R.id.et_desc_pending);

        backmaintenance = findViewById(R.id.back_pending);

        btn_simpan = findViewById(R.id.btn_save_pending);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Pending");

        backmaintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeLogisticActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tanggalpending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddPendingAsset.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        tanggalpending.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkField(namapending);
                checkField(kodeaset);
                checkField(tanggalpending);
                checkField(kategori);
                checkField(supplier);
                checkField(jumlah);
                checkField(deskripsi);

                String Asset_Name_Pending = namapending.getText().toString();
                String Asset_Code_Pending = kodeaset.getText().toString();
                String Date = tanggalpending.getText().toString();
                String Categories = kategori.getText().toString();
                String Supplier = supplier.getText().toString();
                String Amount  = jumlah.getText().toString();
                String Description  = deskripsi.getText().toString();

                pendingID = Asset_Name_Pending;

                PendingAssetModel pendingAssetModel = new PendingAssetModel(
                   pendingID, Asset_Code_Pending,Asset_Code_Pending,Date,Categories,Supplier,Amount,Description);

                if (valid) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child(pendingID).setValue(pendingAssetModel);
                            startActivity(new Intent(AddPendingAsset.this, HomeLogisticActivity.class));
                            PendingAssetModel pendingAssetModel = snapshot.getValue(PendingAssetModel.class);
                            Log.d(TAG, "Pending asset successfull  " + pendingAssetModel);
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




