package com.example.itassetmanagementptbukitasam.AddData.AddResponse;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.itassetmanagementptbukitasam.Admin.HomeAdminActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.ListData.Response.List.ResponseHumasActivity;
import com.example.itassetmanagementptbukitasam.model.ResponseModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class AddResponseHumas extends AppCompatActivity {

    private EditText nameResponse, dateResponse, divisionResponse, assetResponse, descriptionResponse;
    private Button btn_save;
    boolean valid = true;
    int mYear, mMonth, mDay;
    private String complainID;
    private ImageView backcom;
    private static final String TAG = "AddResponseHumas";
    private RadioGroup radioGroupStatusResponse;
    private RadioButton radioButtonStatusResponse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_response_humas);

        nameResponse = findViewById(R.id.et_name_response_humas);
        dateResponse = findViewById(R.id.et_date_response_humas);
        divisionResponse = findViewById(R.id.et_division_response_humas);
        assetResponse = findViewById(R.id.et_name_asset_response_humas);
        radioGroupStatusResponse = findViewById(R.id.radio_group_response_humas);

        btn_save = findViewById(R.id.btn_submit_response_humas);
        backcom = findViewById(R.id.back_response_humas);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Response_Humas");


        backcom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ResponseHumasActivity.class));
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddResponseHumas.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        dateResponse.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkField(assetResponse);
                checkField(dateResponse);
                checkField(nameResponse);
                checkField(divisionResponse);

                int selectedStatusID = radioGroupStatusResponse.getCheckedRadioButtonId();
                radioButtonStatusResponse = findViewById(selectedStatusID);

                String Name_Response = nameResponse.getText().toString();
                String Date_Response = dateResponse.getText().toString();
                String Division_Response = divisionResponse.getText().toString();
                String Asset_Response = assetResponse.getText().toString();
                String Status_Response = radioButtonStatusResponse.getText().toString();

                complainID = Name_Response;

                ResponseModel responseModel = new ResponseModel(
                        complainID, Name_Response,Date_Response,Division_Response,Asset_Response,Status_Response);

                if (valid) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child(complainID).setValue(responseModel);
                            startActivity(new Intent(AddResponseHumas.this, ResponseHumasActivity.class));
                            ResponseModel responseModel = snapshot.getValue(ResponseModel.class);
                            Log.d(TAG, "Create data response successfull  " + responseModel);
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
