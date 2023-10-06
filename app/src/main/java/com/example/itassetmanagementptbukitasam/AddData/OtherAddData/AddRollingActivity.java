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

import com.example.itassetmanagementptbukitasam.ListData.OtherData.RollingActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.RollingModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;


public class AddRollingActivity extends AppCompatActivity {
    Toolbar toolbar;
    boolean valid = true;
    DatabaseReference databaseReference;
    int mYear, mMonth, mDay;
    EditText nama_aset, kode_aset, tanggal_peminjaman, divisi,jumlah,keterangan,link;
    ImageView  dokumentasi, backrolling;
    Button btn_simpan, rollingDate, deployDate;
    String RollingId;
    private static final String TAG = "AddRollingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_rolling);

        nama_aset = findViewById(R.id.et_name_rolling);
        kode_aset = findViewById(R.id.et_kode_aset_rolling);
        divisi = findViewById(R.id.et_devisi_rolling);
        tanggal_peminjaman = findViewById(R.id.et_datetime_rolling);
        jumlah = findViewById(R.id.et_amount_rolling);
        keterangan = findViewById(R.id.et_desc_rolling);
        link = findViewById(R.id.et_link_rolling);

        backrolling = findViewById(R.id.back_rolling);

        btn_simpan = findViewById(R.id.btn_simpan_rolling);
        rollingDate = findViewById(R.id.exter_rolling);



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Rolling");


        backrolling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RollingActivity.class);
                startActivity(intent);
                finish();
            }
        });


        rollingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddRollingActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        tanggal_peminjaman.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                RollingId = Nama_Asset;

                RollingModel rollingModel = new RollingModel( RollingId,Nama_Asset,Kode_Asset_rolling,Divisi_Rolling,Date_Rolling,Amount_Rolling,Description_Rolling,
                        Link);

                if (valid) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child(RollingId).setValue(rollingModel);
                            startActivity(new Intent(AddRollingActivity.this, RollingActivity.class));
                            RollingModel rollingModel = snapshot.getValue(RollingModel.class);
                            Log.d(TAG, "Create data successfull  " + rollingModel);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.w(TAG, "Failed to read data.", error.toException());
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




