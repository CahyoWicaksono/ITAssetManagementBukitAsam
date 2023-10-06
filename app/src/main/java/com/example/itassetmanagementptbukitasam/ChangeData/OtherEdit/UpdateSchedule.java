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

import com.example.itassetmanagementptbukitasam.ListData.OtherData.ScheduleActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.ScheduleModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UpdateSchedule extends AppCompatActivity {

    private EditText Task, Date,Location,Asset_Code,Deployment_Date;
    private Button date_button,save,deploy_date;
    private TextView Time;
    String scheduleID;
    private ImageView backShcedule;
    int t1Hour,t1Minute,mYear, mMonth, mDay;
    boolean valid = true;
    ScheduleModel scheduleModel;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_edit_schedule);


        Task = findViewById(R.id.edit_task_edit);
        Date = findViewById(R.id.edit_datetime_schedule_edit);
        Location = findViewById(R.id.edit_location_schedule_edit);
        Asset_Code = findViewById(R.id.edit_asset_code_edit);
        Deployment_Date = findViewById(R.id.edit_deploy_edit);

        date_button = findViewById(R.id.schedule_Date_edit);
        deploy_date = findViewById(R.id.deploy_Date_edit);
        save = findViewById(R.id.btn_update_schedule);

        backShcedule = findViewById(R.id.backschedule_edit);

        Time = findViewById(R.id.textview_selectTime_edit);

        firebaseDatabase = FirebaseDatabase.getInstance();
        scheduleModel = getIntent().getParcelableExtra("schedule");

        if(scheduleModel!=null){
            Task.setText(scheduleModel.getTaskSchedule());
            Date.setText(scheduleModel.getDateSchedule());
            Time.setText(scheduleModel.getTimeSchedule());
            Location.setText(scheduleModel.getLocationSchedule());
            Asset_Code.setText(scheduleModel.getAssetSchedule());
            Deployment_Date.setText(scheduleModel.getDeploySchedule());
            scheduleID = scheduleModel.getScheduleID();
        }
        databaseReference = firebaseDatabase.getReference("Maintenance_Schedule").child(scheduleID);


        backShcedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UpdateSchedule.this, ScheduleActivity.class));

            }
        });



        Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        UpdateSchedule.this, new TimePickerDialog.OnTimeSetListener() {
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

        date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateSchedule.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        Date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        deploy_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateSchedule.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        Deployment_Date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(Task);
                checkField(Date);
                checkField(Location);
                checkField(Asset_Code);
                checkField(Deployment_Date);

                String task_schedule = Task.getText().toString();
                String date_schedule = Date.getText().toString();
                String time_schedule = Time.getText().toString();
                String location_schedule  = Location.getText().toString();
                String asset_code  = Asset_Code.getText().toString();
                String deployment_date = Deployment_Date.getText().toString();



                Map<String,Object> Schedule = new HashMap<>();
                Schedule.put("task_schedule",task_schedule);
                Schedule.put("date_schedule",date_schedule);
                Schedule.put("time_schedule", time_schedule);
                Schedule.put("location_schedule",location_schedule);
                Schedule.put("asset_code",asset_code);
                Schedule.put("deployment_date",deployment_date);
                Schedule.put("scheduleID",scheduleID);


                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.updateChildren(Schedule);
                        Toast.makeText(UpdateSchedule.this, "Schedule Updated...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UpdateSchedule.this, ScheduleActivity.class));

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
