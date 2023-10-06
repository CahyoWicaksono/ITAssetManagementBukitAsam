package com.example.itassetmanagementptbukitasam.AddData.OtherAddData;

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
import com.example.itassetmanagementptbukitasam.SDM.HomeSDM;
import com.example.itassetmanagementptbukitasam.model.ComplaintModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class AddComplaint extends AppCompatActivity {

    private EditText nameComplaint, dateComplaint, divisionComplain, assetComplaint, descriptionComplain;
    private Button btn_submitComplain;
    boolean valid = true;
    int mYear, mMonth, mDay;
    private String complainID;
    private ImageView backcom;
    private static final String TAG = "AddComplaint";




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_complain);

        nameComplaint = findViewById(R.id.et_name_complaint);
        dateComplaint = findViewById(R.id.et_date_complaint);
        divisionComplain = findViewById(R.id.et_division_complaint);
        assetComplaint = findViewById(R.id.et_name_asset_complaint);
        descriptionComplain = findViewById(R.id.et_problem_complaint);

        backcom = findViewById(R.id.back_complaint);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Complaint");



        backcom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        dateComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddComplaint.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        dateComplaint.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        btn_submitComplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkField(assetComplaint);
                checkField(dateComplaint);
                checkField(nameComplaint);
                checkField(divisionComplain);
                checkField(nameComplaint);
                checkField(descriptionComplain);

                String Name_Complaint = nameComplaint.getText().toString();
                String Date_Complaint = dateComplaint.getText().toString();
                String Division_Complaint = divisionComplain.getText().toString();
                String Asset_Complaint = assetComplaint.getText().toString();
                String Problem = descriptionComplain.getText().toString();

                complainID = Name_Complaint;

                ComplaintModel complaintModel = new ComplaintModel(
                        complainID, Asset_Complaint,Date_Complaint,Division_Complaint,Asset_Complaint,Problem);

                if (valid) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child(complainID).setValue(complaintModel);
                            startActivity(new Intent(AddComplaint.this, HomeSDM.class));
                            ComplaintModel complaint = snapshot.getValue(ComplaintModel.class);
                            Log.d(TAG, "Create complaint asset successfull  " + complaint);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.w(TAG, "Failed to read maintenance.", error.toException());

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
