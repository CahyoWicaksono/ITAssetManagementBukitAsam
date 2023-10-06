package com.example.itassetmanagementptbukitasam.ListDataUser;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.itassetmanagementptbukitasam.Adapter.OtherAdapter.MainAdapterUser;
import com.example.itassetmanagementptbukitasam.AddData.AddDataUser.AddMaintenanceUser;
import com.example.itassetmanagementptbukitasam.ChangeData.OtherEdit.EditMaintenanceUser;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.Report.ReportDivision.ReportAdmin;
import com.example.itassetmanagementptbukitasam.User.HomeUserActivity;
import com.example.itassetmanagementptbukitasam.User.ProfileUserActivity;
import com.example.itassetmanagementptbukitasam.User.QRCodeActivity;

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

public class MaintenanceUserActivity extends AppCompatActivity implements MainAdapterUser.MaintenanceUserClickInterface {

    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FloatingActionButton floatingActionButton;
    private ArrayList<MaintananceModel> maintananceModelArrayList;
    private MainAdapterUser mainAdapter;
    private RelativeLayout bottomMain;
    Toolbar backmainten;
    private SwipeRefreshLayout swipeRefreshLayout;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_maintenance_user);

        swipeToRefresh();

        recyclerView = findViewById(R.id.maintain_data_user);
        floatingActionButton = findViewById(R.id.tambah_pemeliharaan_user);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Maintenance");
        maintananceModelArrayList = new ArrayList<>();
        bottomMain = findViewById(R.id.idRLMain_user);

        mainAdapter = new MainAdapterUser(maintananceModelArrayList, this,   this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mainAdapter);




        bottomNavigationView = findViewById(R.id.botomNavigation_maintenance_user);

        bottomNavigationView.setSelectedItemId(R.id.id_home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.id_home:
                        startActivity(new Intent(getApplicationContext(), HomeUserActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.id_profile:
                        startActivity(new Intent(getApplicationContext(), ProfileUserActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.id_code:
                        startActivity(new Intent(getApplicationContext(), QRCodeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MaintenanceUserActivity.this, AddMaintenanceUser.class));

            }
        });
        getAllMaintenance();
    }

    public void  getAllMaintenance(){
        maintananceModelArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                maintananceModelArrayList.add(snapshot.getValue(MaintananceModel.class));
                mainAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                mainAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                mainAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                mainAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private  void displayBottomShet(MaintananceModel maintananceModel){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_maintenance_user,bottomMain);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView Maintenance_Code = layout.findViewById(R.id.idTVMaintenanceCode_user);
        TextView AssetCode       = layout.findViewById(R.id.idTVAssetCodeMaintenance_user);
        TextView Officer         = layout.findViewById(R.id.idTVOfficerMaintenance_user);
        TextView Kondisi         = layout.findViewById(R.id.idTVConditionMaintenance_user);
        TextView Location          = layout.findViewById(R.id.idTVLocationMaintenance_user);
        TextView StatusMain      = layout.findViewById(R.id.idTVStatusMaintenance_user);
        TextView DateMaintenance = layout.findViewById(R.id.idTVDateMaintenance_user);


        Button editData          = layout.findViewById(R.id.editDataMaintenance_user);
        Button deleteData        = layout.findViewById(R.id.DeleteMaintenance_user);

        Maintenance_Code.setText(maintananceModel.getKode_mintanance());
        AssetCode.setText(maintananceModel.getKode_aset());
        Officer.setText(maintananceModel.getPetugas());
        Kondisi.setText(maintananceModel.getKondisi_peralatan());
        Location.setText(maintananceModel.getLokasi());
        StatusMain.setText(maintananceModel.getStatus());
        DateMaintenance.setText(maintananceModel.getTanggal());

        editData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MaintenanceUserActivity.this, EditMaintenanceUser.class);
                i.putExtra("maintenance", maintananceModel);
                startActivity(i);
            }
        });

        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.removeValue();
                Toast.makeText(MaintenanceUserActivity.this, "Maintenance deleted...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MaintenanceUserActivity.class));
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

