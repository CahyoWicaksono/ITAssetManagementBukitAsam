package com.example.itassetmanagementptbukitasam.ListData.OtherData;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.itassetmanagementptbukitasam.Adapter.OtherAdapter.ScheduleAdapter;
import com.example.itassetmanagementptbukitasam.AddData.OtherAddData.ScheduleMaintenanceActivity;
import com.example.itassetmanagementptbukitasam.Admin.GenerateCode;
import com.example.itassetmanagementptbukitasam.Admin.HomeAdminActivity;
import com.example.itassetmanagementptbukitasam.Admin.ProfileAdminActivity;
import com.example.itassetmanagementptbukitasam.ChangeData.OtherEdit.UpdateSchedule;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.ScheduleModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.OnDateSelectedListener;
import org.naishadhparmar.zcustomcalendar.Property;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class ScheduleActivity extends AppCompatActivity implements ScheduleAdapter.ViewScheduleClickInterface{
    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FloatingActionButton floatingActionButton;
    private ArrayList<ScheduleModel> scheduleModelArrayList;
    private ScheduleAdapter scheduleAdapter;
    private ProgressBar loadingPB;
    private NestedScrollView bottomSchedule;
    private SwipeRefreshLayout swipeRefreshLayout;
    private BottomNavigationView bottomNavigationView;
    private CustomCalendar customCalendar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_schedule);

        recyclerView = findViewById(R.id.schedule_data);
        floatingActionButton = findViewById(R.id.tambah_schedule);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Maintenance_Schedule");
        scheduleModelArrayList = new ArrayList<>();
        bottomSchedule = findViewById(R.id.idRLSchedule);
        loadingPB = findViewById(R.id.progress_bar_schedule);
        customCalendar = findViewById(R.id.custom_calendar);

        //Last Date
        HashMap<Object, Property> descMap =new HashMap<>();
        Property defaultProperty = new Property();
        defaultProperty.layoutResource = R.layout.default_view;
        defaultProperty.dateTextViewResource = R.id.text_view;
        descMap.put("default",defaultProperty);

        //Current Date
        Property currentProperty = new Property();
        currentProperty.layoutResource = R.layout.current_view;
        currentProperty.dateTextViewResource = R.id.text_view;
        descMap.put("current",currentProperty);

        //Present Date
        Property presentProperty = new Property();
        presentProperty.layoutResource = R.layout.present_view;
        presentProperty.dateTextViewResource = R.id.text_view;
        descMap.put("present",presentProperty);

        //Absent Date
        Property absentProperty = new Property();
        absentProperty.layoutResource = R.layout.absent_date;
        absentProperty.dateTextViewResource = R.id.text_view;
        descMap.put("absent",absentProperty);

        customCalendar.setMapDescToProp(descMap);

        HashMap<Integer,Object> dateHashMap = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        dateHashMap.put(calendar.get(Calendar.DAY_OF_MONTH),"current");
        dateHashMap.put(1,"present");
        dateHashMap.put(2,"absent");
        dateHashMap.put(3,"present");
        dateHashMap.put(4,"absent");
        dateHashMap.put(20,"present");
        dateHashMap.put(30,"absent");

        customCalendar.setDate(calendar,dateHashMap);

        customCalendar.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(View view, Calendar selectedDate, Object desc) {
                String sDate = selectedDate.get(Calendar.DAY_OF_MONTH)
                        +"/" + (selectedDate.get(Calendar.MONTH) + 1)
                        +"/" + selectedDate.get(Calendar.YEAR);

                Toast.makeText(getApplicationContext(), sDate, Toast.LENGTH_SHORT).show();
            }
        });

        scheduleAdapter = new ScheduleAdapter(scheduleModelArrayList, this,   this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(scheduleAdapter);


        bottomNavigationView = findViewById(R.id.botomNavigation_schedule);

        bottomNavigationView.setSelectedItemId(R.id.id_home);


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.id_home:
                        startActivity(new Intent(getApplicationContext(), HomeAdminActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.id_profile:
                        startActivity(new Intent(getApplicationContext(), ProfileAdminActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.id_code:
                        startActivity(new Intent(getApplicationContext(), GenerateCode.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ScheduleActivity.this, ScheduleMaintenanceActivity.class));

            }
        });
        getAllAsset();
    }

    private void getAllAsset() {
        scheduleModelArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                scheduleModelArrayList.add(snapshot.getValue(ScheduleModel.class));
                scheduleAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                scheduleAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                loadingPB.setVisibility(View.GONE);
                scheduleAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                scheduleAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    @Override
    public void onScheduleClick(int position) {
        displayBottomShet(scheduleModelArrayList.get(position));


    }
    private  void displayBottomShet(ScheduleModel scheduleModel) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_view_schedule, bottomSchedule);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView Task = layout.findViewById(R.id.idTVTask);
        TextView Date = layout.findViewById(R.id.idTVDateSchedule);
        TextView Time = layout.findViewById(R.id.idTVTimeSchedule);
        TextView Location = layout.findViewById(R.id.idTVLocationSchedule);
        TextView Asset_Code  = layout.findViewById(R.id.idTVAssetCode_Schedule);
        TextView Deployment_Date = layout.findViewById(R.id.idTVDeployment_schedule);

        Button editData = layout.findViewById(R.id.editSchedule);
        Button deleteData = layout.findViewById(R.id.DeleteSchedule);

        Task.setText(scheduleModel.getTaskSchedule());
        Date.setText(scheduleModel.getDateSchedule());
        Time.setText(scheduleModel.getTimeSchedule());
        Location.setText(scheduleModel.getLocationSchedule());
        Asset_Code.setText(scheduleModel.getAssetSchedule());
        Deployment_Date.setText(scheduleModel.getDeploySchedule());

        editData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ScheduleActivity.this, UpdateSchedule.class);
                i.putExtra("schedule", scheduleModel);
                startActivity(i);
            }
        });

        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.removeValue();
                Toast.makeText(ScheduleActivity.this, "Asset deleted...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), ScheduleActivity.class));
                finish();
            }
        });

    }

    public void onBackPressed() {
        super.onBackPressed();
    }
}
