package com.example.itassetmanagementptbukitasam.ListData.OtherData;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.itassetmanagementptbukitasam.Admin.ProfileAdminActivity;
import com.example.itassetmanagementptbukitasam.Adapter.OtherAdapter.CheckingAdapter;
import com.example.itassetmanagementptbukitasam.Admin.GenerateCode;
import com.example.itassetmanagementptbukitasam.Admin.HomeAdminActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.CheckingModel;
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

public class ViewCheckingActivity extends AppCompatActivity implements CheckingAdapter.ViewDataCheckingClickInterface{

    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FloatingActionButton floatingActionButton;
    private ArrayList<CheckingModel> checkingModelArrayList;
    private CheckingAdapter checkingAdapter;
    private ProgressBar loadingPB;
    private NestedScrollView bottomChecking;
    private SwipeRefreshLayout swipeRefreshLayout;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_check_view);

        swipeToRefresh();

        recyclerView = findViewById(R.id.check_data_view);
        firebaseDatabase = FirebaseDatabase.getInstance();
        loadingPB = findViewById(R.id.progress_bar_check_view);
        databaseReference = firebaseDatabase.getReference("Checking_Asset");
        checkingModelArrayList = new ArrayList<>();
        bottomChecking = findViewById(R.id.idRLCheck_view);
        bottomNavigationView = findViewById(R.id.botomNavigation_check_view);

        checkingAdapter = new CheckingAdapter(checkingModelArrayList, this,  this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(checkingAdapter);


        bottomNavigationView = findViewById(R.id.botomNavigation_check_view);

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
                        overridePendingTransition(0,0);
                        startActivity(new Intent(getApplicationContext(), GenerateCode.class));
                        return true;
                }

                return false;
            }
        });

        getAllCheck();
    }
    public void  getAllCheck(){
        checkingModelArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                checkingModelArrayList.add(snapshot.getValue(CheckingModel.class));
                checkingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                checkingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                checkingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                checkingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
}

    @Override
    public void onViewDataCheckingClick(int position) {
        displayBottomShet(checkingModelArrayList.get(position));
    }
    private  void displayBottomShet(CheckingModel checkingModel) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_cheking_data_view, bottomChecking);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView Name_Asset_Check = layout.findViewById(R.id.idTVAssetNameChecking_view);
        TextView Asset_Code_Check = layout.findViewById(R.id.idTVAssetCodeChecking_view);
        TextView Officer_Check = layout.findViewById(R.id.idTVOfficerChecking_view);
        TextView Maintenance_Status = layout.findViewById(R.id.idTVnMaintenanceChecking_view);
        TextView Condition_Asset  = layout.findViewById(R.id.idTVConditionChecking_view);
        TextView Type_Asset = layout.findViewById(R.id.idTVTypeCheking_view);
        TextView Location_Check = layout.findViewById(R.id.idTVLocationCheck_view);
        TextView Date_Check = layout.findViewById(R.id.idTVDateCheck_view);

        Name_Asset_Check.setText(checkingModel.getNamaAsetCheck());
        Asset_Code_Check.setText(checkingModel.getKodeAsetCheck());
        Officer_Check.setText(checkingModel.getPetugasCheck());
        Maintenance_Status.setText(checkingModel.getMaintenanceCheck());
        Condition_Asset.setText(checkingModel.getStatusCheck());
        Type_Asset.setText(checkingModel.getTypeCheck());
        Location_Check.setText(checkingModel.getLokasiCheck());
        Date_Check.setText(checkingModel.getTanggalCheck());
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
