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

import com.example.itassetmanagementptbukitasam.ListData.OtherData.RollingActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.RollingModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditRolling extends AppCompatActivity {
    EditText nama_aset, kode_aset, tanggal_peminjaman, divisi,jumlah,keterangan,link;
    Button btn_update_rolling, date;
    boolean valid = true;
    ImageView backasset, backas;
    RollingModel rollingModel;
    FirebaseDatabase firebaseDatabase;
    int mYear, mMonth, mDay;
    DatabaseReference databaseReference;
    String RollingId;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_rolling);

        nama_aset = findViewById(R.id.et_name_rolling_update);
        kode_aset = findViewById(R.id.et_kode_aset_rolling_update);
        divisi = findViewById(R.id.et_devisi_rolling_update);
        tanggal_peminjaman = findViewById(R.id.et_datetime_rolling_update);
        jumlah = findViewById(R.id.et_amount_rolling_update);
        keterangan = findViewById(R.id.et_desc_rolling_update);
        link = findViewById(R.id.et_link_rolling_update);

        backas = findViewById(R.id.back_rolling_update);

        btn_update_rolling = findViewById(R.id.btn_update_rolling);
        date = findViewById(R.id.exter_rolling_update);


        firebaseDatabase = FirebaseDatabase.getInstance();
        rollingModel  = getIntent().getParcelableExtra("rolling");

        if(rollingModel!=null){
            nama_aset.setText(rollingModel.getNamaAssetRoll());
            kode_aset.setText(rollingModel.getKodeAssetRoll());
            divisi.setText(rollingModel.getDivisiRoll());
            tanggal_peminjaman.setText(rollingModel.getTanggalRoll());
            jumlah.setText(rollingModel.getJumlahRoll());
            keterangan.setText(rollingModel.getDescRoll());
            link.setText(rollingModel.getRollingLink());

            RollingId = rollingModel.getRollingID();

        }
        databaseReference = firebaseDatabase.getReference("Rolling").child(RollingId);


        backas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RollingActivity.class));
                finish();
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditRolling.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        tanggal_peminjaman.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        btn_update_rolling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(nama_aset);
                checkField(kode_aset);
                checkField(divisi);
                checkField(tanggal_peminjaman);
                checkField(jumlah);
                checkField(keterangan);
                checkField(link);

                String Nama_Asset = nama_aset.getText().toString();
                String Kode_Asset_rolling = kode_aset.getText().toString();
                String Divisi_Rolling = divisi.getText().toString();
                String Date_Rolling = tanggal_peminjaman.getText().toString();
                String Amount_Rolling = jumlah.getText().toString();
                String Description_Rolling = keterangan.getText().toString();
                String Link = link.getText().toString();


                Map<String,Object> Asset = new HashMap<>();
                Asset.put("Name_Asset",Nama_Asset);
                Asset.put("Kode_Asset_rolling",Kode_Asset_rolling);
                Asset.put("Divisi_Rolling",Divisi_Rolling);
                Asset.put("Date_Rolling",Date_Rolling);
                Asset.put("Amount_Rolling",Amount_Rolling);
                Asset.put("Description_Rolling",Description_Rolling);
                Asset.put("Link_Image", Link);
                Asset.put("RollingId", RollingId);


                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.updateChildren(Asset);
                        Toast.makeText(EditRolling.this, "Data Updated...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditRolling.this, RollingActivity.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditRolling.this, "Update Data Failed", Toast.LENGTH_SHORT).show();
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
