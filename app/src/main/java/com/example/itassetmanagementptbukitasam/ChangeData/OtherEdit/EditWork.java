package com.example.itassetmanagementptbukitasam.ChangeData.OtherEdit;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.itassetmanagementptbukitasam.ListData.OtherData.WorkOrderActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.WorkOrderModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditWork extends AppCompatActivity {

    private EditText officer, date,location,asset,nameAsset;
    private Button date_button,save,deploy_date;
    private TextView Time;
    String WorkID;
    private ImageView backUpdate;
    int t1Hour,t1Minute,mYear, mMonth, mDay;
    boolean valid = true;
    WorkOrderModel workOrderModel;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_work_order);


        officer = findViewById(R.id.et_officer_work_update);
        date = findViewById(R.id.et_date_work_update);
        Time = findViewById(R.id.et_time_work_update);
        location = findViewById(R.id.et_location_work_update);
        asset = findViewById(R.id.et_asset_code_work_update);
        nameAsset = findViewById(R.id.et_asset_name_work_update);
        backUpdate = findViewById(R.id.backWork_update);


        save = findViewById(R.id.btn_simpan_work_update);

        firebaseDatabase = FirebaseDatabase.getInstance();
        workOrderModel = getIntent().getParcelableExtra("work_order");

        if(workOrderModel!=null){
            officer.setText(workOrderModel.getNamaPetugas());
            date.setText(workOrderModel.getTanggalWork());
            Time.setText(workOrderModel.getWaktuWork());
            location.setText(workOrderModel.getLocation());
            asset.setText(workOrderModel.getKodeAssetWork());
            nameAsset.setText(workOrderModel.getNamaAssetWork());
            WorkID = workOrderModel.getWorkID();
        }
        databaseReference = firebaseDatabase.getReference("Work_Order").child(WorkID);


        backUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditWork.this, WorkOrderActivity.class));

            }
        });



        Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        EditWork.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        t1Hour = hourOfDay;
                        t1Minute = minute;
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(0,0,0,t1Hour,t1Minute);
                        Time.setText(DateFormat.format("hh:mm aa",calendar));


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
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditWork.this, new DatePickerDialog.OnDateSetListener() {
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
                String time__Work = Time.getText().toString();
                String location_Work  = location.getText().toString();
                String asset_code  = asset.getText().toString();
                String asset_name_Work = nameAsset.getText().toString();

                Map<String,Object> Work = new HashMap<>();
                Work.put("Officer_Work",Officer_Work);
                Work.put("date_Work",date_Work);
                Work.put("time__Work", time__Work);
                Work.put("location_Work",location_Work);
                Work.put("asset_code",asset_code);
                Work.put("asset_name_Work",asset_name_Work);
                Work.put("WorkID",WorkID);


                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.updateChildren(Work);
                        Toast.makeText(EditWork.this, "Data Updated...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditWork.this, WorkOrderActivity.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

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
