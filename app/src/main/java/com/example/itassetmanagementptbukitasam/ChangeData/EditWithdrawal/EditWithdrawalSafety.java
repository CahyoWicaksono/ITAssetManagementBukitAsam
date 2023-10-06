package com.example.itassetmanagementptbukitasam.ChangeData.EditWithdrawal;

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

import com.example.itassetmanagementptbukitasam.ListData.Withdrawal.List.WithdrawalActivity;
import com.example.itassetmanagementptbukitasam.ListData.Withdrawal.List.WithdrawalSafetyActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.WithdrawalModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditWithdrawalSafety extends AppCompatActivity {
    private EditText kode_penarikan,kode_aset, tanggal, devisi,
            jumlah_penarikan, alasan ,kondisi, keterangan_penarikan, petugas;
    private Button btn_update_with, withdrawalDate;
    int mYear, mMonth, mDay;
    boolean valid = true;
    private ImageView backwith;
    private String withSafetyId;
    private WithdrawalModel withdrawalModel;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_withdrawal_safety);

        kode_penarikan = findViewById(R.id.et_with_code_safety_update);
        kode_aset = findViewById(R.id.et_kode_aset_safety_update);
        tanggal = findViewById(R.id.et_tanggal_safety_update);
        devisi = findViewById(R.id.et_devisi_with_safety_update);
        alasan = findViewById(R.id.et_reason_safety_update);
        keterangan_penarikan = findViewById(R.id.et_desc_with_safety_update);
        jumlah_penarikan= findViewById(R.id.et_amount_with_safety_update);
        petugas = findViewById(R.id.et_officer_with_safety_update);

        backwith = findViewById(R.id.backwith_safety_update);
        withdrawalDate = findViewById(R.id.with_date_safety_update);

        btn_update_with = findViewById(R.id.btn_simpan_with_safety_update);
        firebaseDatabase =FirebaseDatabase.getInstance();
        withdrawalModel = getIntent().getParcelableExtra("withdrawal_safety");

        if(withdrawalModel!=null){
            kode_penarikan.setText(withdrawalModel.getKode_penarikan());
            kode_aset.setText(withdrawalModel.getKode_aset());
            tanggal.setText(withdrawalModel.getTanggal());
            devisi.setText(withdrawalModel.getDevisi());
            alasan.setText(withdrawalModel.getAlasan());
            keterangan_penarikan.setText(withdrawalModel.getKeterangan_penarikan());
            jumlah_penarikan.setText(withdrawalModel.getJumlah_penarikan());
            petugas.setText(withdrawalModel.getPetugas());

            withSafetyId = withdrawalModel.getDevisi();

        }
        databaseReference = firebaseDatabase.getReference("Withdrawal_safety").child(withSafetyId);

        backwith.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), WithdrawalSafetyActivity.class));
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditWithdrawalSafety.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        tanggal.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        btn_update_with.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(kode_penarikan);
                checkField(kode_aset);
                checkField(tanggal);
                checkField(devisi);
                checkField(alasan);
                checkField(keterangan_penarikan);
                checkField(jumlah_penarikan);
                checkField(petugas);

                String asset_name = kode_penarikan.getText().toString();
                String asset_code = kode_aset.getText().toString();
                String date = tanggal.getText().toString();
                String amount = jumlah_penarikan.getText().toString();
                String division = devisi.getText().toString();
                String reason = alasan.getText().toString();
                String officer = petugas.getText().toString();
                String description = keterangan_penarikan.getText().toString();

                Map<String,Object> Withdrawal = new HashMap<>();
                Withdrawal.put("asset_name",asset_name);
                Withdrawal.put("asset_code",asset_code);
                Withdrawal.put("date",date);
                Withdrawal.put("amount",amount);
                Withdrawal.put("division",division);
                Withdrawal.put("reason", reason);
                Withdrawal.put("officer",officer);
                Withdrawal.put("description",description);
                Withdrawal.put("withId",withSafetyId);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.updateChildren(Withdrawal);
                        Toast.makeText(EditWithdrawalSafety.this, "Withdrawal Data Updated...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditWithdrawalSafety.this, WithdrawalSafetyActivity.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditWithdrawalSafety.this, "Fail To Withdrawal Data Updated...", Toast.LENGTH_SHORT).show();
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
