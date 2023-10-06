package com.example.itassetmanagementptbukitasam.ListData.OtherData;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import com.example.itassetmanagementptbukitasam.Admin.ProfileAdminActivity;
import com.example.itassetmanagementptbukitasam.Adapter.OtherAdapter.RollingAdapter;
import com.example.itassetmanagementptbukitasam.AddData.OtherAddData.AddRollingActivity;
import com.example.itassetmanagementptbukitasam.Admin.GenerateCode;
import com.example.itassetmanagementptbukitasam.Admin.HomeAdminActivity;
import com.example.itassetmanagementptbukitasam.ChangeData.OtherEdit.EditDescruction;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.RollingModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RollingActivity extends AppCompatActivity implements RollingAdapter.RollingClickInterface{

    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FloatingActionButton floatingActionButton;
    private ArrayList<RollingModel> rollingModelArrayList;
    private RollingAdapter rollingAdapter;
    private NestedScrollView bottomRolling;
    private ProgressBar loadingPB;
    Toolbar backdamage;
    private SwipeRefreshLayout swipeRefreshLayout;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_roling);

        swipeToRefresh();

        recyclerView            = findViewById(R.id.rolling_data);
        floatingActionButton    = findViewById(R.id.tambah_rolling);
        firebaseDatabase        = FirebaseDatabase.getInstance();
        databaseReference       = firebaseDatabase.getReference("Rolling");
        rollingModelArrayList   = new ArrayList<>();
        bottomRolling            = findViewById(R.id.idRLRolling);
        loadingPB               = findViewById(R.id.progress_bar_rolling);

        rollingAdapter = new RollingAdapter(rollingModelArrayList, this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(rollingAdapter);


        bottomNavigationView = findViewById(R.id.botomNavigation_rolling);

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
                startActivity(new Intent(RollingActivity.this, AddRollingActivity.class));

            }

        });
        getAllRolling();
    }
        public void  getAllRolling(){
            rollingModelArrayList.clear();
            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    loadingPB.setVisibility(View.GONE);
                    rollingModelArrayList.add(snapshot.getValue(RollingModel.class));
                    rollingAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    loadingPB.setVisibility(View.GONE);
                    rollingAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                    loadingPB.setVisibility(View.GONE);
                    rollingAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    loadingPB.setVisibility(View.GONE);
                    rollingAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }

    @Override
    public void onRollingClick(int position) {
        displayBottomShet(rollingModelArrayList.get(position));

    }
    private  void displayBottomShet(RollingModel rollingModel){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_rolling_data,bottomRolling);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView Nama_Asset            = layout.findViewById(R.id.idTVAssetName_rolling);
        TextView Kode_Asset_rolling             = layout.findViewById(R.id.idTVAssetCode_rolling);
        TextView Divisi_Rolling         = layout.findViewById(R.id.idTVDivisio_rolling);
        TextView Date_Rolling        = layout.findViewById(R.id.idTVDate_rolling);
        TextView Amount_Rolling          = layout.findViewById(R.id.idTVAmount_rolling);
        TextView Description_Rolling          = layout.findViewById(R.id.idTVDesc_Rolling);

        ImageView imageViewRolling = layout.findViewById(R.id.image_asset_rolling);

        Button editData             = layout.findViewById(R.id.editData_rolling);
        Button deleteData           = layout.findViewById(R.id.Delete_rolling);

        Nama_Asset.setText(rollingModel.getNamaAssetRoll());
        Kode_Asset_rolling.setText(rollingModel.getKodeAssetRoll());
        Divisi_Rolling.setText(rollingModel.getDivisiRoll());
        Date_Rolling.setText(rollingModel.getTanggalRoll());
        Amount_Rolling.setText(rollingModel.getJumlahRoll());
        Description_Rolling.setText(rollingModel.getDescRoll());

        Picasso.get().load(rollingModel.getRollingLink()).into(imageViewRolling);

        editData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RollingActivity.this, EditDescruction.class);
                i.putExtra("rolling", rollingModel);
                startActivity(i);
            }
        });

        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.removeValue();
                Toast.makeText(RollingActivity.this, "Damage Data deleted...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), RollingActivity.class));
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

