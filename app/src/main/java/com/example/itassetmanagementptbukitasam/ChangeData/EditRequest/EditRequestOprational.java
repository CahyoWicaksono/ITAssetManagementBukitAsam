package com.example.itassetmanagementptbukitasam.ChangeData.EditRequest;
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

import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.ListData.Request.ListRequest.RequestOprationalActivity;
import com.example.itassetmanagementptbukitasam.model.RequestModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditRequestOprational extends AppCompatActivity {
    private EditText nameRequest, dateRequest, divisiRequest, jumlahRequest,link, descRequest, kodeassetRequest;
    Button btn_update_request;
    boolean valid = true;
    ImageView  backas;
    RequestModel requestModel;
    FirebaseDatabase firebaseDatabase;
    int mYear, mMonth, mDay;
    DatabaseReference databaseReference;
    String RequestID_oprational;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_permintaan_oprational);

        nameRequest = findViewById(R.id.et_name_update_request_oprational);
        dateRequest = findViewById(R.id.et_kode_aset_update_request_oprational);
        divisiRequest = findViewById(R.id.et_divisi_update_request_oprational);
        jumlahRequest = findViewById(R.id.et_amount_update_request_oprational);
        descRequest = findViewById(R.id.et_desc_update_request_oprational);
        kodeassetRequest = findViewById(R.id.et_kode_aset_update_request_oprational);
        link = findViewById(R.id.et_link_image_update_request_oprational);


        backas = findViewById(R.id.back_update_request_oprational);
        btn_update_request = findViewById(R.id.btn_update_request_oprational);


        firebaseDatabase =FirebaseDatabase.getInstance();
        requestModel = getIntent().getParcelableExtra("request_oprational");

        if(requestModel!=null){
            nameRequest.setText(requestModel.getNameRequest());
            kodeassetRequest.setText(requestModel.getKodeAsetRequest());
            divisiRequest.setText(requestModel.getDivisionRequest());
            jumlahRequest.setText(requestModel.getAmountRequest());
            descRequest.setText(requestModel.getDescRequest());
            dateRequest.setText(requestModel.getDateRequest());
            link.setText(requestModel.getLinkrequest());

            RequestID_oprational = requestModel.getRequestid();

        }
        databaseReference = firebaseDatabase.getReference("Request_oprational").child(RequestID_oprational);


        backas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RequestOprationalActivity.class));
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditRequestOprational.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        dateRequest.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        btn_update_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkField(nameRequest);
                checkField(kodeassetRequest);
                checkField(divisiRequest);
                checkField(jumlahRequest);
                checkField(descRequest);
                checkField(dateRequest);
                checkField(link);

                String Name_Asset_Request_oprational = nameRequest.getText().toString();
                String Asset_Code_Request_oprational = kodeassetRequest.getText().toString();
                String Divisi_Asset_Request_oprational = divisiRequest.getText().toString();
                String Amount_Request_oprational = jumlahRequest.getText().toString();
                String Description_Request_oprational = descRequest.getText().toString();
                String Date_Request_oprational = dateRequest.getText().toString();
                String Link_Image_oprational = link.getText().toString();

                Map<String,Object> Request = new HashMap<>();
                Request.put("Name_Asset_Request_oprational",Name_Asset_Request_oprational);
                Request.put("Asset_Code_Request_oprational",Asset_Code_Request_oprational);
                Request.put("Divisi_Asset_Request_oprational", Divisi_Asset_Request_oprational);
                Request.put("Amount_Request_oprational",Amount_Request_oprational);
                Request.put("Description_Request_oprational",Description_Request_oprational);
                Request.put("Date_Request_oprational",Date_Request_oprational);
                Request.put("Link_Image_oprational", Link_Image_oprational);
                Request.put("RequestID_oprational", RequestID_oprational);


                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.updateChildren(Request);
                        Toast.makeText(EditRequestOprational.this, " Data Updated...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditRequestOprational.this, RequestOprationalActivity.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditRequestOprational.this, "Update Data Failed", Toast.LENGTH_SHORT).show();
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
