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

import com.example.itassetmanagementptbukitasam.Admin.GenerateCode;
import com.example.itassetmanagementptbukitasam.Admin.HomeAdminActivity;
import com.example.itassetmanagementptbukitasam.Admin.ProfileAdminActivity;
import com.example.itassetmanagementptbukitasam.ChangeData.OtherEdit.EditMaintenance;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.Adapter.OtherAdapter.MainAdapter;
import com.example.itassetmanagementptbukitasam.AddData.OtherAddData.AddMaintainActivity;
import com.example.itassetmanagementptbukitasam.model.MaintananceModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MaintenanceActivity extends AppCompatActivity implements MainAdapter.MaintenanceClickInterface {

    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FloatingActionButton floatingActionButton;
    private ArrayList<MaintananceModel> maintananceModelArrayList;
    private MainAdapter mainAdapter;
    private NestedScrollView bottomMain;
    private ProgressBar loadingPB;
    private SwipeRefreshLayout swipeRefreshLayout;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_maintenance);

        swipeToRefresh();

        recyclerView = findViewById(R.id.maintain_data);
        floatingActionButton = findViewById(R.id.tambah_pemeliharaan);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Maintenance");
        maintananceModelArrayList = new ArrayList<>();
        bottomMain = findViewById(R.id.idRLMain);
        loadingPB = findViewById(R.id.progress_bar_maintenance);

        mainAdapter = new MainAdapter(maintananceModelArrayList, this,   this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mainAdapter);



        bottomNavigationView = findViewById(R.id.botomNavigation_maintenance);

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
                startActivity(new Intent(MaintenanceActivity.this, AddMaintainActivity.class));

            }
        });
        getAllMaintenance();
    }

    public void  getAllMaintenance(){
        maintananceModelArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                maintananceModelArrayList.add(snapshot.getValue(MaintananceModel.class));
                mainAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                mainAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                loadingPB.setVisibility(View.GONE);
                mainAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                mainAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private  void displayBottomShet(MaintananceModel maintananceModel){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_maintenance_data,bottomMain);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();


        TextView Maintenance_Code= layout.findViewById(R.id.idTVMaintenanceCode);
        TextView AssetCode       = layout.findViewById(R.id.idTVAssetCodeMaintenance);
        TextView Officer         = layout.findViewById(R.id.idTVOfficerMaintenance);
        TextView kondisi         = layout.findViewById(R.id.idTVConditionMaintenance);
        TextView Location          = layout.findViewById(R.id.idTVLocationMaintenance);
        TextView StatusMain      = layout.findViewById(R.id.idTVStatusMaintenance);
        TextView DateMaintenance = layout.findViewById(R.id.idTVDateMaintenance);


        Button editData          = layout.findViewById(R.id.editDataMaintenance);
        Button deleteData        = layout.findViewById(R.id.DeleteMaintenance);

        Maintenance_Code.setText(maintananceModel.getKode_mintanance());
        AssetCode.setText(maintananceModel.getKode_aset());
        Officer.setText(maintananceModel.getPetugas());
        kondisi.setText(maintananceModel.getKondisi_peralatan());
        Location.setText(maintananceModel.getLokasi());
        StatusMain.setText(maintananceModel.getStatus());
        DateMaintenance.setText(maintananceModel.getTanggal());


        editData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MaintenanceActivity.this, EditMaintenance.class);
                i.putExtra("maintenance", maintananceModel);
                startActivity(i);
            }
        });

        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.removeValue();
                Toast.makeText(MaintenanceActivity.this, "Maintenance deleted...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MaintenanceActivity.class));
                finish();
            }
        });

    }

    @Override
    public void onMaintenanceClick(int position) {
        displayBottomShet(maintananceModelArrayList.get(position));
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

