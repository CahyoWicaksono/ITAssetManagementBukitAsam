package com.example.itassetmanagementptbukitasam.ChangeData.Edit;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.ListData.Response.List.ResponseLogisticActivity;
import com.example.itassetmanagementptbukitasam.model.ResponseModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditResponseLogistic extends AppCompatActivity {
    boolean valid = true;
    int mYear, mMonth, mDay;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ResponseModel responseModel;
    private RadioGroup radioGroupUdateStatus;
    private RadioButton radioButtonUpdateSelect;
    private EditText nameResponse, dateResponse, divisionResponse, assetResponse;
    Button update_response;
    ImageView backdmg;
    String ResponseID, txtStatus;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_response_logistic);

        nameResponse = findViewById(R.id.et_name_response_logistic_update);
        dateResponse = findViewById(R.id.et_date_response_logistic_update);
        divisionResponse = findViewById(R.id.et_division_response_logistic_update);
        assetResponse = findViewById(R.id.et_name_asset_response_logistic_update);
        backdmg = findViewById(R.id.back_response_logistic_update);

        update_response = findViewById(R.id.btn_submit_logistic_update);
        radioGroupUdateStatus = findViewById(R.id.radio_group_response_logistic_update);

        firebaseDatabase =FirebaseDatabase.getInstance();
        responseModel = getIntent().getParcelableExtra("response_logistic");

        if(responseModel!=null){
            nameResponse.setText(responseModel.getNamaResponse());
            dateResponse.setText(responseModel.getTanggalResponse());
            divisionResponse.setText(responseModel.getDivisiResponse());
            assetResponse.setText(responseModel.getNamaAssetResponse());
            radioButtonUpdateSelect.setText(responseModel.getStatusResponse());
            ResponseID = responseModel.getResponseID();


        }
        databaseReference = firebaseDatabase.getReference("Response_Logistic").child(ResponseID);

        backdmg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ResponseLogisticActivity.class));
                finish();
            }
        });

        dateResponse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditResponseLogistic.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        dateResponse.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        update_response.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(assetResponse);
                checkField(dateResponse);
                checkField(nameResponse);
                checkField(divisionResponse);

                int selectedStatusID = radioGroupUdateStatus.getCheckedRadioButtonId();
                radioButtonUpdateSelect = findViewById(selectedStatusID);

                String Name_Response = nameResponse.getText().toString();
                String Date_Response = dateResponse.getText().toString();
                String Division_Response = divisionResponse.getText().toString();
                String Asset_Response = assetResponse.getText().toString();
                String Status_Response = radioButtonUpdateSelect.getText().toString();

                Map<String,Object> Response = new HashMap<>();
                Response.put("Name_Response",Name_Response);
                Response.put("Date_Response",Date_Response);
                Response.put("Division_Response",Division_Response);
                Response.put("Asset_Response",Asset_Response);
                Response.put("Status_Response",Status_Response);
                Response.put("ResponseID", ResponseID);


                if (radioButtonUpdateSelect.equals("Success")){
                    radioButtonUpdateSelect = findViewById(R.id.radio_sukses);
                }else if(radioButtonUpdateSelect.equals("Process")){
                    radioButtonUpdateSelect = findViewById(R.id.radio_proses);
                }else if(radioButtonUpdateSelect.equals("Pending")) {
                    radioButtonUpdateSelect = findViewById(R.id.radio_proses);
                    radioButtonUpdateSelect.setChecked(true);
                } else {
                    radioButtonUpdateSelect = findViewById(R.id.radio_noyet);
                }
                    radioButtonUpdateSelect.setChecked(true);


                if (valid) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.updateChildren(Response);
                            Toast.makeText(EditResponseLogistic.this, "Data Updated...", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(EditResponseLogistic.this, ResponseLogisticActivity.class));

                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(EditResponseLogistic.this, "Data Updated Failed...", Toast.LENGTH_SHORT).show();
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
