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
import com.example.itassetmanagementptbukitasam.ListData.Request.ListRequest.RequestSafetyActivity;
import com.example.itassetmanagementptbukitasam.model.RequestModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditRequestSafety extends AppCompatActivity {
    private EditText nameRequest, dateRequest, divisiRequest, jumlahRequest,link, descRequest, kodeassetRequest;
    Button btn_update_request;
    boolean valid = true;
    ImageView  backas;
    RequestModel requestModel;
    FirebaseDatabase firebaseDatabase;
    int mYear, mMonth, mDay;
    DatabaseReference databaseReference;
    String RequestID_safety;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_permintaan_safety);

        nameRequest = findViewById(R.id.et_name_update_request_safety);
        dateRequest = findViewById(R.id.et_kode_aset_update_request_safety);
        divisiRequest = findViewById(R.id.et_divisi_update_request_safety);
        jumlahRequest = findViewById(R.id.et_amount_update_request_safety);
        descRequest = findViewById(R.id.et_desc_update_request_safety);
        kodeassetRequest = findViewById(R.id.et_kode_aset_update_request_safety);
        link = findViewById(R.id.et_link_image_update_request_safety);


        backas = findViewById(R.id.back_update_request);
        btn_update_request = findViewById(R.id.btn_update_request);


        firebaseDatabase =FirebaseDatabase.getInstance();
        requestModel = getIntent().getParcelableExtra("request_safety");

        if(requestModel!=null){
            nameRequest.setText(requestModel.getNameRequest());
            kodeassetRequest.setText(requestModel.getKodeAsetRequest());
            divisiRequest.setText(requestModel.getDivisionRequest());
            jumlahRequest.setText(requestModel.getAmountRequest());
            descRequest.setText(requestModel.getDescRequest());
            dateRequest.setText(requestModel.getDateRequest());
            link.setText(requestModel.getLinkrequest());

            RequestID_safety = requestModel.getRequestid();

        }
        databaseReference = firebaseDatabase.getReference("Request_safety").child(RequestID_safety);


        backas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RequestSafetyActivity.class));
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditRequestSafety.this, new DatePickerDialog.OnDateSetListener() {
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

                String Name_Asset_Request_safety = nameRequest.getText().toString();
                String Asset_Code_Request_safety = kodeassetRequest.getText().toString();
                String Divisi_Asset_Request_safety = divisiRequest.getText().toString();
                String Amount_Request_safety = jumlahRequest.getText().toString();
                String Description_Request_safety = descRequest.getText().toString();
                String Date_Request_safety = dateRequest.getText().toString();
                String Link_Image_safety = link.getText().toString();

                Map<String,Object> Request = new HashMap<>();
                Request.put("Name_Asset_Request_safety",Name_Asset_Request_safety);
                Request.put("Asset_Code_Request_safety",Asset_Code_Request_safety);
                Request.put("Divisi_Asset_Request_safety", Divisi_Asset_Request_safety);
                Request.put("Amount_Request_safety",Amount_Request_safety);
                Request.put("Description_Request_safety",Description_Request_safety);
                Request.put("Date_Request_safety",Date_Request_safety);
                Request.put("Link_Image_safety", Link_Image_safety);
                Request.put("RequestID_safety", RequestID_safety);


                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.updateChildren(Request);
                        Toast.makeText(EditRequestSafety.this, " Data Updated...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditRequestSafety.this, RequestSafetyActivity.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditRequestSafety.this, "Update Data Failed", Toast.LENGTH_SHORT).show();
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
