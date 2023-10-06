package com.example.itassetmanagementptbukitasam.ChangeData.Editreceiver;

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

import com.example.itassetmanagementptbukitasam.ListData.Receiver.ListReceiver.ReceiverClnicActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.ListData.Receiver.ListReceiver.ReceiverActivity;
import com.example.itassetmanagementptbukitasam.model.ReceiverModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditReceiverClinic extends AppCompatActivity {
    private EditText nameReceiver, dateReceiver, divisiReceiver, jumlahReceiver,link, ConditionReceiver, kodeassetReceiver;
    Button btn_update_request;
    boolean valid = true;
    ImageView  backas;
    ReceiverModel receiverModel;
    FirebaseDatabase firebaseDatabase;
    int mYear, mMonth, mDay;
    DatabaseReference databaseReference;
    String ReceiverID;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_penerimaan_clinic);



        nameReceiver = findViewById(R.id.et_name_penerimaan_Update_clinic);
        dateReceiver = findViewById(R.id.et_kode_aset_penerimaan_Update_clinic);
        divisiReceiver = findViewById(R.id.et_divisi_penerimaan_Update_clinic);
        jumlahReceiver = findViewById(R.id.et_amount_penerimaan_Update_clinic);
        ConditionReceiver = findViewById(R.id.et_desc_penerimaan_Update_clinic);
        kodeassetReceiver = findViewById(R.id.et_kode_aset_penerimaan_Update_clinic);


        btn_update_request = findViewById(R.id.btn_Update_penerimaan_clinic);
        backas = findViewById(R.id.back_penerimaan_clinic);

        firebaseDatabase =FirebaseDatabase.getInstance();
        receiverModel = getIntent().getParcelableExtra("receiver_clinic");

        if(receiverModel!=null){
            nameReceiver.setText(receiverModel.getNamaAsetPen());
            kodeassetReceiver.setText(receiverModel.getKodeAsetPen());
            divisiReceiver.setText(receiverModel.getDivisiPen());
            jumlahReceiver.setText(receiverModel.getJumlahPen());
            ConditionReceiver.setText(receiverModel.getKondisiPen());
            dateReceiver.setText(receiverModel.getTanggalPen());
            ReceiverID = receiverModel.getPenerimaanID();

        }
        databaseReference = firebaseDatabase.getReference("Receiver_clinic").child(ReceiverID);


        backas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ReceiverClnicActivity.class));
                finish();
            }
        });
        dateReceiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditReceiverClinic.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        dateReceiver.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        btn_update_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkField(nameReceiver);
                checkField(kodeassetReceiver);
                checkField(divisiReceiver);
                checkField(jumlahReceiver);
                checkField(ConditionReceiver);
                checkField(dateReceiver);
                checkField(link);

                String Name_Asset_Receiver = nameReceiver.getText().toString();
                String Asset_Code_Receiver = kodeassetReceiver.getText().toString();
                String Divisi_Asset_Receiver = divisiReceiver.getText().toString();
                String Amount_Receiver = jumlahReceiver.getText().toString();
                String Condition_Receiver = ConditionReceiver.getText().toString();
                String Date_Receiver = dateReceiver.getText().toString();


                Map<String,Object> Receiver = new HashMap<>();
                Receiver.put("Name_Asset_Receiver",Name_Asset_Receiver);
                Receiver.put("Asset_Code_Receiver",Asset_Code_Receiver);
                Receiver.put("Divisi_Asset_Receiver", Divisi_Asset_Receiver);
                Receiver.put("Amount_Receiver",Amount_Receiver);
                Receiver.put("Condition_Receiver",Condition_Receiver);
                Receiver.put("Date_Receiver",Date_Receiver);
                Receiver.put("ReceiverID",Receiver);


                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.updateChildren(Receiver);
                        Toast.makeText(EditReceiverClinic.this, " Data Updated...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditReceiverClinic.this, ReceiverClnicActivity.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditReceiverClinic.this, "Update Data Failed", Toast.LENGTH_SHORT).show();
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
