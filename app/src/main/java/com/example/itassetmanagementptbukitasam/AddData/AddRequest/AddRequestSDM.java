package com.example.itassetmanagementptbukitasam.AddData.AddRequest;

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
import com.example.itassetmanagementptbukitasam.ListData.Request.ListRequest.RequestSDMActivity;
import com.example.itassetmanagementptbukitasam.model.RequestModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class AddRequestSDM extends AppCompatActivity {

    private EditText nameRequest, dateRequest, divisiRequest, jumlahRequest,link, descRequest, kodeassetRequest;
    private Button btn_save;
    boolean valid = true;
    int mYear, mMonth, mDay;
    private String RequestID_sdm;
    private ImageView backcom;
    private static final String TAG = "AddRequestSDM";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_permintaan_sdm);

        nameRequest = findViewById(R.id.et_name_Request_sdm);
        dateRequest = findViewById(R.id.et_kode_aset_Request_sdm);
        divisiRequest = findViewById(R.id.et_divisi_Request_sdm);
        jumlahRequest = findViewById(R.id.et_amount_Request_sdm);
        descRequest = findViewById(R.id.et_desc_Request_sdm);
        kodeassetRequest = findViewById(R.id.et_kode_aset_Request_sdm);
        link = findViewById(R.id.et_link_image_Request_sdm);


        btn_save = findViewById(R.id.btn_simpan_Request_sdm);
        backcom = findViewById(R.id.back_Request_sdm);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Request_sdm");


        backcom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RequestSDMActivity.class));
                finish();
            }
        });

        dateRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddRequestSDM.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        dateRequest.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkField(nameRequest);
                checkField(kodeassetRequest);
                checkField(divisiRequest);
                checkField(jumlahRequest);
                checkField(descRequest);
                checkField(dateRequest);
                checkField(link);

                String Name_Asset_Request_sdm = nameRequest.getText().toString();
                String Asset_Code_Request_sdm = kodeassetRequest.getText().toString();
                String Divisi_Asset_Request_sdm = divisiRequest.getText().toString();
                String Amount_Request_sdm = jumlahRequest.getText().toString();
                String Description_Request_sdm = descRequest.getText().toString();
                String Date_Request_sdm = dateRequest.getText().toString();
                String Link_Image_sdm = link.getText().toString();

                RequestID_sdm = Name_Asset_Request_sdm;

                RequestModel requestModel = new RequestModel(
                        RequestID_sdm,
                        Name_Asset_Request_sdm,
                        Asset_Code_Request_sdm,
                        Divisi_Asset_Request_sdm,
                        Amount_Request_sdm,
                        Description_Request_sdm,
                        Date_Request_sdm,
                        Link_Image_sdm);

                if (valid) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child(RequestID_sdm).setValue(requestModel);
                            startActivity(new Intent(AddRequestSDM.this, RequestSDMActivity.class));
                            RequestModel requestModel = snapshot.getValue(RequestModel.class);
                            Log.d(TAG, "Create data successfull  " + requestModel);
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
