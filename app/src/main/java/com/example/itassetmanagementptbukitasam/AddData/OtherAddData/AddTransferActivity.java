package com.example.itassetmanagementptbukitasam.AddData.OtherAddData;

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

import com.example.itassetmanagementptbukitasam.ListData.OtherData.TransferActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.TransferModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Calendar;

public class AddTransferActivity extends AppCompatActivity {
    EditText kode_transfer, kode_aset, tanggal, giver, receiver, devision, link,
            description_aset, condition, status;
    int mYear, mMonth, mDay;
    String transIF;
    Button btn_simpan, transferDate;
    boolean valid = true;
    ImageView backtf;
    private static final String TAG = "AddTransferActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_transfer);


        kode_transfer = findViewById(R.id.et_transfer_code_tf);
        kode_aset = findViewById(R.id.et_kode_aset_tf);
        tanggal = findViewById(R.id.et_tanggal_tf);
        giver = findViewById(R.id.et_Giver_tf);
        receiver = findViewById(R.id.et_receiver_tf);
        devision = findViewById(R.id.et_devisi_tf);
        description_aset = findViewById(R.id.et_desc_tf);
        condition = findViewById(R.id.et_con_tf);
        status = findViewById(R.id.et_sts_tf);
        backtf = findViewById(R.id.backtf);

        btn_simpan = findViewById(R.id.btn_simpan_tf);
        transferDate = findViewById(R.id.transfer_date_tf);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Transfer");



        backtf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TransferActivity.class));
                finish();
            }
        });
        transferDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTransferActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                checkField(kode_aset);
                checkField(kode_transfer);
                checkField(tanggal);
                checkField(giver);
                checkField(receiver);
                checkField(devision);
                checkField(condition);
                checkField(status);

                String Asset_Name = kode_transfer.getText().toString();
                String Asset_Code = kode_aset.getText().toString();
                String Date = tanggal.getText().toString();
                String Giver_Asset = giver.getText().toString();
                String Receiver_Asset = receiver.getText().toString();
                String Division = devision.getText().toString();
                String Condition_Asset = condition.getText().toString();
                String Status_Asset = status.getText().toString();
                String Description = description_aset.getText().toString();

                transIF = Asset_Name;

                TransferModel transferModel = new TransferModel(transIF,Asset_Name,Asset_Code,Date,
                        Giver_Asset,Receiver_Asset,Division,Condition_Asset,Status_Asset,Description);

                if (valid) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child(transIF).setValue(transferModel);
                            startActivity(new Intent(AddTransferActivity.this, TransferActivity.class));

           

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(AddTransferActivity.this, "", Toast.LENGTH_SHORT).show();
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
