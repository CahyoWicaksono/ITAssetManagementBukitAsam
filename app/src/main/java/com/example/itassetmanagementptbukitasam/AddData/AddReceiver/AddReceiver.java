package com.example.itassetmanagementptbukitasam.AddData.AddReceiver;

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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.ListData.Receiver.ListReceiver.ReceiverActivity;
import com.example.itassetmanagementptbukitasam.model.ReceiverModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class AddReceiver extends AppCompatActivity {

    private EditText nameReceiver, dateReceiver, divisiReceiver, jumlahReceiver,link, ConditionReceiver, kodeassetReceiver;
    private Button btn_save;
    boolean valid = true;
    int mYear, mMonth, mDay;
    private String ReceiverID;
    private ImageView backcom;
    private static final String TAG = "AddReceiver";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_penerimaan_it);

        nameReceiver = findViewById(R.id.et_name_penerimaan);
        dateReceiver = findViewById(R.id.et_kode_aset_penerimaan);
        divisiReceiver = findViewById(R.id.et_divisi_penerimaan);
        jumlahReceiver = findViewById(R.id.et_amount_penerimaan);
        ConditionReceiver = findViewById(R.id.et_desc_penerimaan);
        kodeassetReceiver = findViewById(R.id.et_kode_aset_penerimaan);

        btn_save = findViewById(R.id.btn_simpan_penerimaan);
        backcom = findViewById(R.id.back_penerimaan);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Receiver");


        backcom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ReceiverActivity.class));
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddReceiver.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        dateReceiver.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkField(nameReceiver);
                checkField(kodeassetReceiver);
                checkField(divisiReceiver);
                checkField(jumlahReceiver);
                checkField(ConditionReceiver);
                checkField(dateReceiver);

                String Name_Asset_Receiver = nameReceiver.getText().toString();
                String Asset_Code_Receiver = kodeassetReceiver.getText().toString();
                String Divisi_Asset_Receiver = divisiReceiver.getText().toString();
                String Amount_Receiver = jumlahReceiver.getText().toString();
                String Condition_Receiver = ConditionReceiver.getText().toString();
                String Date_Receiver = dateReceiver.getText().toString();


                ReceiverID = Name_Asset_Receiver;

                ReceiverModel receiverModel = new ReceiverModel(
                        ReceiverID, Name_Asset_Receiver,Asset_Code_Receiver,Divisi_Asset_Receiver,Amount_Receiver,Condition_Receiver,
                        Date_Receiver);

                if (valid) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child(ReceiverID).setValue(receiverModel);
                            startActivity(new Intent(AddReceiver.this, ReceiverActivity.class));
                            ReceiverModel receiverModel = snapshot.getValue(ReceiverModel.class);
                            Log.d(TAG, "Create data successfull  " + receiverModel);
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
