package com.example.itassetmanagementptbukitasam.ListData.OtherData;


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

import com.example.itassetmanagementptbukitasam.Adapter.OtherAdapter.OrderAdapter;
import com.example.itassetmanagementptbukitasam.Adapter.OtherAdapter.PendingAdapter;
import com.example.itassetmanagementptbukitasam.ChangeData.OtherEdit.EditDamage;
import com.example.itassetmanagementptbukitasam.ChangeData.OtherEdit.EditPending;
import com.example.itassetmanagementptbukitasam.Logistic.HomeLogisticActivity;
import com.example.itassetmanagementptbukitasam.Logistic.ProfileLogisticActivity;
import com.example.itassetmanagementptbukitasam.OtherMenu.ScanQRCodeActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.OrderModel;
import com.example.itassetmanagementptbukitasam.model.PendingAssetModel;
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

public class PendingActivity extends AppCompatActivity implements PendingAdapter.ViewDataPendingClickInterface{

    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FloatingActionButton floatingActionButton;
    private ArrayList<PendingAssetModel> pendingAssetModelArrayList;
    private PendingAdapter pendingAdapter;
    private NestedScrollView bottomdamage;
    private ProgressBar loadingPB;
    Toolbar backdamage;
    private SwipeRefreshLayout swipeRefreshLayout;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_pending);

        swipeToRefresh();

        recyclerView            = findViewById(R.id.pending_asset_data);
        firebaseDatabase        = FirebaseDatabase.getInstance();
        databaseReference       = firebaseDatabase.getReference("Pending");
        pendingAssetModelArrayList     = new ArrayList<>();
        bottomdamage            = findViewById(R.id.idRLPending);
        loadingPB               = findViewById(R.id.progress_bar_pending);

        pendingAdapter = new PendingAdapter(pendingAssetModelArrayList, this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(pendingAdapter);


        bottomNavigationView = findViewById(R.id.botomNavigation_pending);

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
                        startActivity(new Intent(getApplicationContext(), ScanQRCodeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });
        getAllDamage();
    }
        public void  getAllDamage(){
            pendingAssetModelArrayList.clear();
            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    loadingPB.setVisibility(View.GONE);
                    pendingAssetModelArrayList.add(snapshot.getValue(PendingAssetModel.class));
                    pendingAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    loadingPB.setVisibility(View.GONE);
                    pendingAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                    loadingPB.setVisibility(View.GONE);
                    pendingAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    loadingPB.setVisibility(View.GONE);
                    pendingAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }

    @Override
    public void onViewDataPendingClick(int position) {
        displayBottomShet(pendingAssetModelArrayList.get(position));

    }
    private  void displayBottomShet(PendingAssetModel pendingAssetModel){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_pending_data,bottomdamage);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView Asset_Name_Pending             = layout.findViewById(R.id.idTVNamePending);
        TextView Asset_Code_Pending             = layout.findViewById(R.id.idTVAssetCodePending);
        TextView Date                           = layout.findViewById(R.id.idTVDatePending);
        TextView Categories                     = layout.findViewById(R.id.idTVCategoriesPending);
        TextView Supplier                       = layout.findViewById(R.id.idTVSupplierPending);
        TextView Amount                         = layout.findViewById(R.id.idTVAmountPending);
        TextView Description                    = layout.findViewById(R.id.idTVDescriptionPending);

        Button EditData           = layout.findViewById(R.id.EditPending);
        Button DeleteData          = layout.findViewById(R.id.DeletePending);

        Asset_Name_Pending.setText(pendingAssetModel.getNamaAsetPending());
        Asset_Code_Pending.setText(pendingAssetModel.getKodeAsetPending());
        Date.setText(pendingAssetModel.getTanggalAsetPending());
        Categories.setText(pendingAssetModel.getKategoriPending());
        Supplier.setText(pendingAssetModel.getSupplierPending());
        Amount.setText(pendingAssetModel.getJumlahAsetPending());
        Description.setText(pendingAssetModel.getKeteranganPending());

        DeleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.removeValue();
                Toast.makeText(PendingActivity.this, "Pending Data deleted...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), PendingActivity.class));
                finish();
            }
        });

        EditData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PendingActivity.this, EditPending.class);
                i.putExtra("pending", pendingAssetModel);
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
    public void onBackPressed() {
        super.onBackPressed();
    }

}

