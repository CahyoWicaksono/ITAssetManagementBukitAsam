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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.itassetmanagementptbukitasam.Adapter.OtherAdapter.DamageUserAdapter;
import com.example.itassetmanagementptbukitasam.AddData.AddDataUser.AddDamageUser;
import com.example.itassetmanagementptbukitasam.ChangeData.OtherEdit.EditDamageUser;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.Report.ReportDivision.ReportAdmin;
import com.example.itassetmanagementptbukitasam.User.HomeUserActivity;
import com.example.itassetmanagementptbukitasam.User.ProfileUserActivity;
import com.example.itassetmanagementptbukitasam.User.QRCodeActivity;
import com.example.itassetmanagementptbukitasam.model.KerusakanModel;
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

public class DamageUserActivity extends AppCompatActivity implements DamageUserAdapter.DamageUserClickInterface{

    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FloatingActionButton floatingActionButton;
    private ArrayList<KerusakanModel> kerusakanModelArrayList;
    private DamageUserAdapter damageAdapter;
    private RelativeLayout bottomdamage;
    private SwipeRefreshLayout swipeRefreshLayout;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_damage_user);

        swipeToRefresh();

        recyclerView            = findViewById(R.id.kerusakan_data_user);
        floatingActionButton    = findViewById(R.id.tambah_kerusakan_user);
        firebaseDatabase        = FirebaseDatabase.getInstance();
        databaseReference       = firebaseDatabase.getReference("Damage");
        kerusakanModelArrayList = new ArrayList<>();
        bottomdamage            = findViewById(R.id.idRLDamage_user);

        damageAdapter = new DamageUserAdapter(kerusakanModelArrayList, this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(damageAdapter);


        bottomNavigationView = findViewById(R.id.botomNavigation_damage_user);

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
                startActivity(new Intent(DamageUserActivity.this, AddDamageUser.class));

            }

        });
        getAllDamage();
    }
        public void  getAllDamage(){
            kerusakanModelArrayList.clear();
            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    kerusakanModelArrayList.add(snapshot.getValue(KerusakanModel.class));
                    damageAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    damageAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                    damageAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    damageAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }

    @Override
    public void onDamageUserClick(int position) {
        displayBottomShet(kerusakanModelArrayList.get(position));

    }
    private  void displayBottomShet(KerusakanModel kerusakanModel){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_damage_user,bottomdamage);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView damage_code           = layout.findViewById(R.id.idTVDamageCode_user);
        TextView asset_code         = layout.findViewById(R.id.idTVAssetCodeDamage_user);
        TextView date               = layout.findViewById(R.id.idTVDate_user);
        TextView type_damage        = layout.findViewById(R.id.idTVTypeDamage_user);
        TextView Officer_name       = layout.findViewById(R.id.idTVOfficer_user);
        TextView location            = layout.findViewById(R.id.idTVLocation_user);
        TextView Deployment         = layout.findViewById(R.id.idTVDeploy_user);
        TextView Solution           = layout.findViewById(R.id.idTVSolution_user);

        Button editData             = layout.findViewById(R.id.editDataDamage_user);
        Button deleteData           = layout.findViewById(R.id.DeleteDataDamage_user);


        damage_code.setText(kerusakanModel.getKode_kerusakan());
        asset_code.setText(kerusakanModel.getKode_aset());
        date.setText(kerusakanModel.getTanggal());
        type_damage.setText(kerusakanModel.getJenis_kerusakan());
        Officer_name.setText(kerusakanModel.getPetugas());
        location.setText(kerusakanModel.getLokasi());
        Deployment.setText(kerusakanModel.getPenerapan());
        Solution.setText(kerusakanModel.getSolusi());

        editData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DamageUserActivity.this, EditDamageUser.class);
                i.putExtra("damage", kerusakanModel);
                startActivity(i);
            }
        });

        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.removeValue();
                Toast.makeText(DamageUserActivity.this, "Damage Data deleted...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), DamageUserActivity.class));
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

