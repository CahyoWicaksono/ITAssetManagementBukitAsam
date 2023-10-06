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

import com.example.itassetmanagementptbukitasam.Adapter.OtherAdapter.ComplaintAdapter;
import com.example.itassetmanagementptbukitasam.Adapter.OtherAdapter.DamageLogisticAdapter;
import com.example.itassetmanagementptbukitasam.AddData.OtherAddData.AddDamageLogistic;
import com.example.itassetmanagementptbukitasam.Admin.GenerateCode;
import com.example.itassetmanagementptbukitasam.Admin.HomeAdminActivity;
import com.example.itassetmanagementptbukitasam.Admin.ProfileAdminActivity;
import com.example.itassetmanagementptbukitasam.ChangeData.OtherEdit.EditDamage;
import com.example.itassetmanagementptbukitasam.ChangeData.OtherEdit.EditDamageLogistic;
import com.example.itassetmanagementptbukitasam.Logistic.HomeLogisticActivity;
import com.example.itassetmanagementptbukitasam.Logistic.ProfileLogisticActivity;
import com.example.itassetmanagementptbukitasam.OtherMenu.Capture;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.ComplaintModel;
import com.example.itassetmanagementptbukitasam.model.KerusakanLogistikModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class DamageLogistikActivity extends AppCompatActivity implements DamageLogisticAdapter.ViewDataKerusakanLogisticClickInterface{

    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FloatingActionButton floatingActionButton;
    private ArrayList<KerusakanLogistikModel> kerusakanLogistikModelArrayList;
    private DamageLogisticAdapter damageLogisticAdapter;
    private NestedScrollView bottomdamage;
    private ProgressBar loadingPB;
    Toolbar backdamage;
    private SwipeRefreshLayout swipeRefreshLayout;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_damage_logistic);

        swipeToRefresh();

        recyclerView            = findViewById(R.id.kerusakan_data_logistic);
        firebaseDatabase        = FirebaseDatabase.getInstance();
        databaseReference       = firebaseDatabase.getReference("Damage_Logistic");
        kerusakanLogistikModelArrayList = new ArrayList<>();
        bottomdamage            = findViewById(R.id.idRLDamageLogistic);
        loadingPB               = findViewById(R.id.progress_bar_damage_logistic);

        damageLogisticAdapter = new DamageLogisticAdapter(kerusakanLogistikModelArrayList, this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(damageLogisticAdapter);


        bottomNavigationView = findViewById(R.id.botomNavigation_damage_logistic);
         floatingActionButton = findViewById(R.id.tambah_kerusakan_logistic);
         floatingActionButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startActivity(new Intent(getApplicationContext(), AddDamageLogistic.class));
                 finish();
             }
         });

        bottomNavigationView.setSelectedItemId(R.id.id_home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.id_home:
                        startActivity(new Intent(getApplicationContext(), HomeLogisticActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.id_profile:
                        startActivity(new Intent(getApplicationContext(), ProfileLogisticActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.id_code:
                        IntentIntegrator intentIntegrator = new IntentIntegrator(DamageLogistikActivity.this);
                        intentIntegrator.setPrompt("For flash use volume key");
                        intentIntegrator.setBeepEnabled(true);
                        intentIntegrator.setOrientationLocked(true);
                        intentIntegrator.setCaptureActivity(Capture.class);
                        intentIntegrator.initiateScan();
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });
        getAllDamage();
    }
        public void  getAllDamage(){
            kerusakanLogistikModelArrayList.clear();
            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    loadingPB.setVisibility(View.GONE);
                    kerusakanLogistikModelArrayList.add(snapshot.getValue(KerusakanLogistikModel.class));
                    damageLogisticAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    loadingPB.setVisibility(View.GONE);
                    damageLogisticAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                    loadingPB.setVisibility(View.GONE);
                    damageLogisticAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    loadingPB.setVisibility(View.GONE);
                    damageLogisticAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }

    @Override
    public void onViewDataKerusakanClick(int position) {
        displayBottomShet(kerusakanLogistikModelArrayList.get(position));

    }
    private  void displayBottomShet(KerusakanLogistikModel kerusakanLogistikModel){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_damage_data_logistic,bottomdamage);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView Asset_Name             = layout.findViewById(R.id.idTVDamageCodeLogistic);
        TextView Asset_Code             = layout.findViewById(R.id.idTVAssetCodeDamageLogistic);
        TextView Date_damage            = layout.findViewById(R.id.idTVDateLogistic);
        TextView Damage_Type            = layout.findViewById(R.id.idTVTypeDamageLogistic);
        TextView Date_Purchase          = layout.findViewById(R.id.idTVPurchaseLogistic);
        TextView Solution               = layout.findViewById(R.id.idTVSolutionLogistic);

        Button deleteData           = layout.findViewById(R.id.DeleteDataDamageLogistic);
        Button editData             = layout.findViewById(R.id.editDataDamageLogistic);

        Asset_Name.setText(kerusakanLogistikModel.getNamaKerusakan());
        Asset_Code.setText(kerusakanLogistikModel.getKodeasetKerusakan());
        Date_damage.setText(kerusakanLogistikModel.getTanggalKerusakan());
        Damage_Type.setText(kerusakanLogistikModel.getTipeKerusakan());
        Date_Purchase.setText(kerusakanLogistikModel.getTanggalPurchaseKerusakan());
        Solution.setText(kerusakanLogistikModel.getSolusiKerusakan());


        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.removeValue();
                Toast.makeText(DamageLogistikActivity.this, "Damage Data deleted...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), DamageLogistikActivity.class));
                finish();
            }
        });
        editData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DamageLogistikActivity.this, EditDamageLogistic.class);
                i.putExtra("damage_logistic", kerusakanLogistikModel);
                startActivity(i);
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if ((intentResult.getContents() != null)) {
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(
                    DamageLogistikActivity.this);
            builder.setTitle("Information");
            builder.setMessage(intentResult.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        } else {
            Toast.makeText(DamageLogistikActivity.this, "Scan Failed", Toast.LENGTH_SHORT).show();
        }
    }


}

