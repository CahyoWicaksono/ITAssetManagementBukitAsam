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
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.itassetmanagementptbukitasam.AddData.OtherAddData.AddSoftwareActivity;
import com.example.itassetmanagementptbukitasam.Admin.GenerateCode;
import com.example.itassetmanagementptbukitasam.Admin.HomeAdminActivity;
import com.example.itassetmanagementptbukitasam.Admin.ProfileAdminActivity;
import com.example.itassetmanagementptbukitasam.ChangeData.OtherEdit.EditSoftware;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.Adapter.OtherAdapter.SoftwareAdapter;
import com.example.itassetmanagementptbukitasam.Report.ReportDivision.ReportAdmin;
import com.example.itassetmanagementptbukitasam.model.SoftwareModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

;import java.util.ArrayList;

public class SoftwareActivity extends AppCompatActivity implements SoftwareAdapter.SoftwareClickInterface {

    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FloatingActionButton floatingActionButton;
    private ArrayList<SoftwareModel>softwareModelArrayList;
    private SoftwareAdapter softAdapter;
    private NestedScrollView bottomsheet;
    private ProgressBar loadingPB;
    private Toolbar backsoftware;
    private SwipeRefreshLayout swipeRefreshLayout;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_soft);

        swipeToRefresh();

        recyclerView = findViewById(R.id.sof_data);
        floatingActionButton = findViewById(R.id.tambah_soft);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Software");
        softwareModelArrayList = new ArrayList<>();
        bottomsheet = findViewById(R.id.idRLsoft);
        swipeRefreshLayout = findViewById(R.id.swipeContainer);
        loadingPB = findViewById(R.id.progress_bar_software);

        softAdapter = new SoftwareAdapter(softwareModelArrayList, this,   this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(softAdapter);

        bottomNavigationView = findViewById(R.id.botomNavigation_software);

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
                startActivity(new Intent(SoftwareActivity.this, AddSoftwareActivity.class));

            }
        });
        getAllSoftware();
    }

    public void  getAllSoftware(){
        softwareModelArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                softwareModelArrayList.add(snapshot.getValue(SoftwareModel.class));
                softAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                softAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                loadingPB.setVisibility(View.GONE);
                softAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                softAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onSosftwareClick(int position) {
        displayBottomShet(softwareModelArrayList.get(position));

    }
    private  void displayBottomShet(SoftwareModel softwareModel){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_software_data,bottomsheet);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView softwareName = layout.findViewById(R.id.idTVsoftwareName);
        TextView brandName = layout.findViewById(R.id.idTVbrand);
        TextView ProductKey = layout.findViewById(R.id.idTVproduct);
        TextView purchasedate = layout.findViewById(R.id.idTVdatepur);
        TextView expiredate = layout.findViewById(R.id.idTVdateexpi);

        Button editData = layout.findViewById(R.id.editsoftware);
        Button deleteData = layout.findViewById(R.id.DeleteSoft);

        softwareName.setText(softwareModel.getSoftware_name());
        brandName.setText(softwareModel.getBrand_name());
        ProductKey.setText(softwareModel.getProduct_key());
        purchasedate.setText(softwareModel.getDate_purchase());
        expiredate.setText(softwareModel.getExparation());

        editData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SoftwareActivity.this, EditSoftware.class);
                i.putExtra("software", softwareModel);
                startActivity(i);
            }
        });

        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.removeValue();
                Toast.makeText(SoftwareActivity.this, "Software deleted...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), SoftwareActivity.class));
                finish();
            }
        });
    }
    private void swipeToRefresh() {
        swipeRefreshLayout = findViewById(R.id.swipeContainer);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startActivity(getIntent());
                finish();
                overridePendingTransition(0,0);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_blue_light
                ,android.R.color.holo_orange_light, android.R.color.holo_red_light);

    }
    public void onBackPressed() {
        super.onBackPressed();
    }


}

