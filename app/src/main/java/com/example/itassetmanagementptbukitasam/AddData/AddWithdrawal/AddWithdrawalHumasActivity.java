package com.example.itassetmanagementptbukitasam.AddData.AddWithdrawal;

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

import com.example.itassetmanagementptbukitasam.ListData.Withdrawal.List.WithdrawalHumasActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.ListData.Withdrawal.List.WithdrawalActivity;
import com.example.itassetmanagementptbukitasam.model.WithdrawalModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class AddWithdrawalHumasActivity extends AppCompatActivity {
    private int mYear, mMonth, mDay;
    private String withhumasId;
    private boolean valid = true;
    private EditText kode_penarikan,withlink, kode_aset, tanggal, devisi, jumlah_penarikan, alasan ,kondisi, keterangan_penarikan, petugas;
    private Button btn_simpan, withdrawalDate;
    private ImageView backwith;
    private static final String TAG = "AddWithdrawalHumasActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_withdrawal_humas);

        kode_penarikan = findViewById(R.id.et_with_code_humas);
        kode_aset = findViewById(R.id.et_kode_aset_humas);
        tanggal = findViewById(R.id.et_tanggal_humas);
        jumlah_penarikan = findViewById(R.id.et_amount_with_humas);
        alasan = findViewById(R.id.et_reason_humas);
        devisi = findViewById(R.id.et_devisi_with_humas);
        keterangan_penarikan = findViewById(R.id.et_desc_with_humas);
        petugas = findViewById(R.id.et_officer_with_humas);
        backwith = findViewById(R.id.backwith_humas);

        btn_simpan = findViewById(R.id.btn_simpan_humas);
        withdrawalDate = findViewById(R.id.with_date_humas);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Withdrawal_humas");

        backwith.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WithdrawalHumasActivity.class);
                startActivity(intent);
                finish();
            }
        });

        withdrawalDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddWithdrawalHumasActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        tanggal.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                checkField(kode_penarikan);
                checkField(kode_aset);
                checkField(tanggal);
                checkField(jumlah_penarikan);
                checkField(devisi);
                checkField(keterangan_penarikan);
                checkField(petugas);
                checkField(alasan);

                String nama_aset = kode_penarikan.getText().toString();
                String asset_code = kode_aset.getText().toString();
                String date = tanggal.getText().toString();
                String amount = jumlah_penarikan.getText().toString();
                String division = devisi.getText().toString();
                String reason = alasan.getText().toString();
                String officer = petugas.getText().toString();
                String description = keterangan_penarikan.getText().toString();



                withhumasId = nama_aset;


                WithdrawalModel withdrawalModel = new WithdrawalModel(nama_aset,asset_code,date,
                        amount,division,reason,officer,description, withhumasId);

                if (valid) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child(withhumasId).setValue(withdrawalModel);
                            startActivity(new Intent(AddWithdrawalHumasActivity.this, WithdrawalHumasActivity.class));
                            WithdrawalModel withdrawal = snapshot.getValue(WithdrawalModel.class);
                            Log.d(TAG, "Create withdrawal asset successfull  " + withdrawal);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.w(TAG, "Failed to read withdrawal.", error.toException());
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
