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

import com.example.itassetmanagementptbukitasam.ListData.OtherData.OrderActivity;
import com.example.itassetmanagementptbukitasam.ListData.OtherData.PendingActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.OrderModel;
import com.example.itassetmanagementptbukitasam.model.PendingAssetModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditPending extends AppCompatActivity {
    boolean valid = true;
    int mYear, mMonth, mDay;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    PendingAssetModel pendingAssetModel;
    EditText namapending, tanggalpending,kategori,supplier,jumlah, kodeaset, deskripsi;
    ImageView backmaintenance;
    Button btn_simpan;
    String pendingID;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_pending_aset);

        namapending = findViewById(R.id.et_name_asset_pending_update);
        kodeaset = findViewById(R.id.et_kode_aset_pending_update);
        tanggalpending = findViewById(R.id.et_date_pending_update);
        kategori = findViewById(R.id.et_categori_pending_update);
        supplier = findViewById(R.id.et_supplier_pending_update);
        jumlah = findViewById(R.id.et_amount_pending_update);
        deskripsi = findViewById(R.id.et_desc_pending_update);

        backmaintenance = findViewById(R.id.back_pending_update);

        btn_simpan = findViewById(R.id.btn_update_pending);

        firebaseDatabase =FirebaseDatabase.getInstance();
        pendingAssetModel = getIntent().getParcelableExtra("pending");

        if(pendingAssetModel!=null){
            namapending.setText(pendingAssetModel.getNamaAsetPending());
            kodeaset.setText(pendingAssetModel.getKodeAsetPending());
            tanggalpending.setText(pendingAssetModel.getTanggalAsetPending());
            kategori.setText(pendingAssetModel.getKategoriPending());
            supplier.setText(pendingAssetModel.getSupplierPending());
            jumlah.setText(pendingAssetModel.getJumlahAsetPending());
            deskripsi.setText(pendingAssetModel.getKeteranganPending());

        }
        databaseReference = firebaseDatabase.getReference("Pending").child(pendingID);

        backmaintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PendingActivity.class));
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditPending.this, new DatePickerDialog.OnDateSetListener() {
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
            public void onClick(View view) {
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

                Map<String,Object> Damage = new HashMap<>();
                Damage.put("Asset_Name_Pending",Asset_Name_Pending);
                Damage.put("Asset_Code_Pending",Asset_Code_Pending);
                Damage.put("Date",Date);
                Damage.put("Categories",Categories);
                Damage.put("Supplier", Supplier);
                Damage.put("Amount", Amount);
                Damage.put("Description", Description);
                Damage.put("pendingID", pendingID);

                if (valid) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.updateChildren(Damage);
                            Toast.makeText(EditPending.this, "Pending Updated...", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(EditPending.this, PendingActivity.class));

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(EditPending.this, "Pending Updated Failed...", Toast.LENGTH_SHORT).show();
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
