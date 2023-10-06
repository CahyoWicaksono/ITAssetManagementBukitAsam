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

import com.example.itassetmanagementptbukitasam.Adapter.OtherAdapter.RepairUserAdapter;
import com.example.itassetmanagementptbukitasam.AddData.AddDataUser.AddRepairUserActivity;
import com.example.itassetmanagementptbukitasam.ChangeData.OtherEdit.EditRepair;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.Report.ReportDivision.ReportUser;
import com.example.itassetmanagementptbukitasam.User.HomeUserActivity;
import com.example.itassetmanagementptbukitasam.User.ProfileUserActivity;
import com.example.itassetmanagementptbukitasam.User.QRCodeActivity;
import com.example.itassetmanagementptbukitasam.model.RepairModel;
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

public class RepairUserActivity extends AppCompatActivity implements RepairUserAdapter.RepairUserClickInterface{

    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FloatingActionButton floatingActionButton;
    private ArrayList<RepairModel> repairModelArrayList;
    private RepairUserAdapter repairAdapter ;
    private RelativeLayout bottomrepair;
    Toolbar backrepair;
    private SwipeRefreshLayout swipeRefreshLayout;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_repair_user);

        swipeToRefresh();

        recyclerView         = findViewById(R.id.repair_data_user);
        floatingActionButton = findViewById(R.id.tambah_perbaikan_user);
        firebaseDatabase     = FirebaseDatabase.getInstance();
        databaseReference    = FirebaseDatabase.getInstance().getReference("Repair");
        repairModelArrayList = new ArrayList<>();
        bottomrepair         = findViewById(R.id.idRLRepair_user);

        repairAdapter = new RepairUserAdapter(repairModelArrayList, this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(repairAdapter);



        bottomNavigationView = findViewById(R.id.botomNavigation_repair_user);

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
                startActivity(new Intent(RepairUserActivity.this, AddRepairUserActivity.class));

            }

        });
        getAllRepair();
    }
    public void  getAllRepair(){
        repairModelArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                repairModelArrayList.add(snapshot.getValue(RepairModel.class));
                repairAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                repairAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                repairAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                repairAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onRepairUserClick(int position) {
        displayBottomShet(repairModelArrayList.get(position));

    }
    private  void displayBottomShet(RepairModel repairModel){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_repair_user,bottomrepair);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();


        TextView repair_code        = layout.findViewById(R.id.idTVRepairCode_user);
        TextView asset_code         = layout.findViewById(R.id.idTVAssetCodeRepair_user);
        TextView Officer_name       = layout.findViewById(R.id.idTVOfficerRepair_user);
        TextView location           = layout.findViewById(R.id.idTVLocationRepair_user);
        TextView Status             = layout.findViewById(R.id.idTVStatusRepair_user);
        TextView date               = layout.findViewById(R.id.idTVDateRepair_user);



        Button editData             = layout.findViewById(R.id.editDataRepair_user);
        Button deleteData           = layout.findViewById(R.id.DeleteDataRepair_user);


        repair_code.setText(repairModel.getKode_perbaikan());
        asset_code.setText(repairModel.getKode_aset());
        Officer_name.setText(repairModel.getPetugas());
        location.setText(repairModel.getLokasi());
        Status.setText(repairModel.getStatus());
        date.setText(repairModel.getTanggal());



        editData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RepairUserActivity.this, EditRepair.class);
                i.putExtra("repair", repairModel);
                startActivity(i);
            }
        });

        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.removeValue();
                Toast.makeText(RepairUserActivity.this, "Repair Data deleted...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), RepairUserActivity.class));
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

