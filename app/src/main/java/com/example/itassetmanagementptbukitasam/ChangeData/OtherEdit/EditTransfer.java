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
import com.example.itassetmanagementptbukitasam.ListData.OtherData.TransferActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.TransferModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditTransfer extends AppCompatActivity {
    private EditText kode_transfer, kode_aset, tanggal, giver, receiver, jumlah_ass, devision, link, description_aset, condition, status;
    private Button btn_update_tf, transferDate;
    int mYear, mMonth, mDay;
    boolean valid = true;
    private ImageView backtf;
    private String transIF;
    private TransferModel transferModel;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_transfer);

        kode_transfer = findViewById(R.id.et_transfer_code_up);
        kode_aset = findViewById(R.id.et_kode_aset_tf_up);
        tanggal = findViewById(R.id.et_tanggal_trans_up);
        giver = findViewById(R.id.et_Giver_up);
        receiver = findViewById(R.id.et_receiver_up);
        devision = findViewById(R.id.et_devisi_tf_up);

        description_aset = findViewById(R.id.et_desc_tf_up);
        condition = findViewById(R.id.et_con_tf_up);
        status = findViewById(R.id.et_sts_tf_up);
        backtf = findViewById(R.id.backtf_up);
        transferDate = findViewById(R.id.transfer_date_up);

        btn_update_tf = findViewById(R.id.btn_updatetf);
        firebaseDatabase = FirebaseDatabase.getInstance();
        transferModel = getIntent().getParcelableExtra("transfer");

        if(transferModel!=null){
            kode_transfer.setText(transferModel.getKode_transfer());
            kode_aset.setText(transferModel.getKode_aset());
            tanggal.setText(transferModel.getTanggal());
            giver.setText(transferModel.getGiver());
            receiver.setText(transferModel.getReceiver());
            devision.setText(transferModel.getDevision());
            condition.setText(transferModel.getCondition());
            description_aset.setText(transferModel.getDescription());
            status.setText(transferModel.getStatus());

            transIF = transferModel.getTansIf();

        }
        databaseReference = firebaseDatabase.getReference("Transfer").child(transIF);

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
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditTransfer.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        tanggal.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        btn_update_tf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(kode_transfer);
                checkField(kode_aset);
                checkField(tanggal);
                checkField(giver);
                checkField(receiver);
                checkField(jumlah_ass);
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



                Map<String,Object> Transfer = new HashMap<>();
                Transfer.put("Asset_Name",Asset_Name);
                Transfer.put("Asset_Code",Asset_Code);
                Transfer.put("Date",Date);
                Transfer.put("Giver_Asset",Giver_Asset);
                Transfer.put("Receiver_Asset", Receiver_Asset);
                Transfer.put("Division",Division);
                Transfer.put("Condition_Asset",Condition_Asset);
                Transfer.put("Status_Asset",Status_Asset);
                Transfer.put("Description",Description);
                Transfer.put("transIF",transIF);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.updateChildren(Transfer);
                        Toast.makeText(EditTransfer.this, "Transfer Data Updated...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditTransfer.this, TransferActivity.class));


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditTransfer.this, "Fail To Transfer Data Updated...", Toast.LENGTH_SHORT).show();
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
