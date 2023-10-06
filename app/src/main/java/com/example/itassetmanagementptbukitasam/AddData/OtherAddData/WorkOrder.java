package com.example.itassetmanagementptbukitasam.AddData.OtherAddData;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.itassetmanagementptbukitasam.ListData.OtherData.ScheduleActivity;
import com.example.itassetmanagementptbukitasam.ListData.OtherData.WorkOrderActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.WorkOrderModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class WorkOrder extends AppCompatActivity {
    private EditText officer, date,location,asset,nameAsset;
    private Button date_button,save,deploy_date;
    private TextView select_time;
    String WorkID;
    int t1Hour,t1Minute,mYear, mMonth, mDay;
    boolean valid = true;
    private ImageView back;
    private static final String TAG = "WorkOrder";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_work_order);

        officer = findViewById(R.id.et_officer_work);
        date = findViewById(R.id.et_date_work);
        select_time = findViewById(R.id.et_time_work);
        location = findViewById(R.id.et_location_work);
        asset = findViewById(R.id.et_asset_code_work);
        nameAsset = findViewById(R.id.et_asset_name_work);
        back = findViewById(R.id.backWork);


        save = findViewById(R.id.btn_simpan_work);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Work_Order");


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WorkOrder.this, WorkOrderActivity.class));
            }
        });


        select_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        WorkOrder.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        t1Hour = hourOfDay;
                        t1Minute = minute;
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(0,0,0,t1Hour,t1Minute);
                        select_time.setText(DateFormat.format("hh:mm aa",calendar));


                    }
                },12,0,false);
                timePickerDialog.updateTime(t1Hour,t1Minute);
                timePickerDialog.show();
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(WorkOrder.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkField(officer);
                checkField(date);
                checkField(location);
                checkField(asset);
                checkField(nameAsset);

                String Officer_Work = officer.getText().toString();
                String date_Work = date.getText().toString();
                String time__Work = select_time.getText().toString();
                String location_Work  = location.getText().toString();
                String asset_code  = asset.getText().toString();
                String asset_name_Work = nameAsset.getText().toString();

                WorkID = Officer_Work;

                WorkOrderModel workOrderModel = new WorkOrderModel(
                        WorkID,
                        Officer_Work,date_Work,
                        time__Work,
                        location_Work,
                        asset_code,
                        asset_name_Work);

                if (valid) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child(WorkID).setValue(workOrderModel);

                            WorkOrderModel workOrderModel = snapshot.getValue(WorkOrderModel.class);
                            startActivity(new Intent(WorkOrder.this, WorkOrderActivity.class));
                            Log.d(TAG, "Create successfull " + workOrderModel);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.w(TAG, "Failed to read.", error.toException());

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

