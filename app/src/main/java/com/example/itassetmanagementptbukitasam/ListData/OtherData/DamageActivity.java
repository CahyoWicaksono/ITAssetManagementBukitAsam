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

import com.example.itassetmanagementptbukitasam.AddData.OtherAddData.AddDamageActivity;
import com.example.itassetmanagementptbukitasam.Admin.GenerateCode;
import com.example.itassetmanagementptbukitasam.Admin.HomeAdminActivity;
import com.example.itassetmanagementptbukitasam.Admin.ProfileAdminActivity;
import com.example.itassetmanagementptbukitasam.ChangeData.OtherEdit.EditDamage;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.Adapter.OtherAdapter.DamageAdapter;
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

public class DamageActivity extends AppCompatActivity implements DamageAdapter.DamageClickInterface{

    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FloatingActionButton floatingActionButton;
    private ArrayList<KerusakanModel> kerusakanModelArrayList;
    private DamageAdapter damageAdapter;
    private NestedScrollView bottomdamage;
    private ProgressBar loadingPB;
    Toolbar backdamage;
    private Button update;
    private SwipeRefreshLayout swipeRefreshLayout;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_damage);

        swipeToRefresh();

        recyclerView            = findViewById(R.id.kerusakan_data);
        recyclerView            = findViewById(R.id.kerusakan_data);
        floatingActionButton    = findViewById(R.id.tambah_kerusakan);
        firebaseDatabase        = FirebaseDatabase.getInstance();
        databaseReference       = firebaseDatabase.getReference("Damage");
        kerusakanModelArrayList = new ArrayList<>();
        bottomdamage            = findViewById(R.id.idRLDamage);
        loadingPB               = findViewById(R.id.progress_bar_damage);


        damageAdapter = new DamageAdapter(kerusakanModelArrayList, this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(damageAdapter);


        bottomNavigationView = findViewById(R.id.botomNavigation_damage);

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
                startActivity(new Intent(DamageActivity.this, AddDamageActivity.class));

            }

        });
        getAllDamage();
    }
        public void  getAllDamage(){
            kerusakanModelArrayList.clear();
            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    loadingPB.setVisibility(View.GONE);
                    kerusakanModelArrayList.add(snapshot.getValue(KerusakanModel.class));
                    damageAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    loadingPB.setVisibility(View.GONE);
                    damageAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                    loadingPB.setVisibility(View.GONE);
                    damageAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    loadingPB.setVisibility(View.GONE);
                    damageAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }

    @Override
    public void onDamageClick(int position) {
        displayBottomShet(kerusakanModelArrayList.get(position));

    }
    private  void displayBottomShet(KerusakanModel kerusakanModel){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_damage_data,bottomdamage);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView Damage_Code        = layout.findViewById(R.id.idTVDamageCode);
        TextView Asset_code         = layout.findViewById(R.id.idTVAssetCodeDamage);
        TextView Date_damage               = layout.findViewById(R.id.idTVDate);
        TextView Damage_type        = layout.findViewById(R.id.idTVTypeDamage);
        TextView Officer       = layout.findViewById(R.id.idTVOfficer);
        TextView Location           = layout.findViewById(R.id.idTVLocation);
        TextView Date_deployment         = layout.findViewById(R.id.idTVDeploy);
        TextView Solution           = layout.findViewById(R.id.idTVSolution);

        Button editData             = layout.findViewById(R.id.editDataDamage);
        Button deleteData           = layout.findViewById(R.id.DeleteDataDamage);


        Damage_Code.setText(kerusakanModel.getKode_kerusakan());
        Asset_code.setText(kerusakanModel.getKode_aset());
        Date_damage.setText(kerusakanModel.getTanggal());
        Damage_type.setText(kerusakanModel.getJenis_kerusakan());
        Officer.setText(kerusakanModel.getPetugas());
        Location.setText(kerusakanModel.getLokasi());
        Date_deployment.setText(kerusakanModel.getPenerapan());
        Solution.setText(kerusakanModel.getSolusi());

        editData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DamageActivity.this, EditDamage.class);
                i.putExtra("damage", kerusakanModel);
                startActivity(i);
            }
        });

        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.removeValue();
                Toast.makeText(DamageActivity.this, "Damage Data deleted...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), DamageActivity.class));
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

