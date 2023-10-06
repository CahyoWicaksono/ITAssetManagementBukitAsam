package com.example.itassetmanagementptbukitasam.ListData.AssetData;

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
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.itassetmanagementptbukitasam.Admin.ProfileAdminActivity;
import com.example.itassetmanagementptbukitasam.AddData.AddAsset.AddAssetOprationalActivity;
import com.example.itassetmanagementptbukitasam.Admin.GenerateCode;
import com.example.itassetmanagementptbukitasam.Admin.HomeAdminActivity;
import com.example.itassetmanagementptbukitasam.Adapter.AssetAdapter.AssetOprationalAdapter;
import com.example.itassetmanagementptbukitasam.ChangeData.EditAsset.EditAssetOprational;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.AssetModel;
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

public class AssetOprationalActivity extends AppCompatActivity implements AssetOprationalAdapter.ViewDataOpratialClickInterface{

    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FloatingActionButton floatingActionButton;
    private ArrayList<AssetModel> assetModelArrayList;
    private AssetOprationalAdapter assetOprationalAdapter;
    private ProgressBar loadingPB;
    private NestedScrollView bottomasset;
    private SwipeRefreshLayout swipeRefreshLayout;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_aset_oprational);

        swipeToRefresh();

        recyclerView = findViewById(R.id.am_data_oprational);
        floatingActionButton = findViewById(R.id.tambah_Aset_oprational);
        firebaseDatabase = FirebaseDatabase.getInstance();
        loadingPB = findViewById(R.id.progress_bar_asset_oprational);
        databaseReference = firebaseDatabase.getReference("Asset_Oprational");
        assetModelArrayList = new ArrayList<>();
        bottomasset = findViewById(R.id.idRLAsetOprational);
        swipeRefreshLayout = findViewById(R.id.swipeContainer);
        bottomNavigationView = findViewById(R.id.botomNavigation_asset_oprational);

        assetOprationalAdapter = new AssetOprationalAdapter(assetModelArrayList, this,  this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(assetOprationalAdapter);


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
                startActivity(new Intent(AssetOprationalActivity.this, AddAssetOprationalActivity.class));
                finish();

            }
        });
        getAllAsset();
    }
    public void  getAllAsset(){
        assetModelArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                assetModelArrayList.add(snapshot.getValue(AssetModel.class));
                assetOprationalAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                assetOprationalAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                assetOprationalAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                assetOprationalAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
}

    @Override
    public void onViewDataOpratialClick(int position) {
        displayBottomShet(assetModelArrayList.get(position));

    }
    private  void displayBottomShet(AssetModel assetModel) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_data_asset_oprational, bottomasset);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView AssetCode = layout.findViewById(R.id.idTVAssetCodeOprational);
        TextView AssetName = layout.findViewById(R.id.idTVAssetNameOprational);
        TextView Supplier = layout.findViewById(R.id.idTVSupplierOprational);
        TextView Purchase = layout.findViewById(R.id.idTVPurchaseOprational);
        TextView Begining  = layout.findViewById(R.id.id_TVdateUseOprational);
        TextView End = layout.findViewById(R.id.id_dateEndOprational);
        TextView Type = layout.findViewById(R.id.idTVTypeOprational);
        TextView Merk = layout.findViewById(R.id.idTVMerkOprational);
        TextView Specification = layout.findViewById(R.id.idTVSpecificationOprational);
        TextView Amount = layout.findViewById(R.id.idTVAmountOprational);
        ImageView AssetIMG = layout.findViewById(R.id.image_assetOprational);

        Button editData = layout.findViewById(R.id.editDataAssetOprational);
        Button deleteData = layout.findViewById(R.id.DeleteDataOprational);

        AssetCode.setText(assetModel.getKode_aset());
        AssetName.setText(assetModel.getNama_aset());
        Supplier.setText(assetModel.getSupplier());
        Purchase.setText(assetModel.getPurchase());
        Begining.setText(assetModel.getPeriod_awa());
        End.setText(assetModel.getPeriod_akhir());
        Type.setText(assetModel.getJenis());
        Merk.setText(assetModel.getMerk());
        Specification.setText(assetModel.getSpesifikasi());
        Amount.setText(assetModel.getJumlah());

        Picasso.get().load(assetModel.getAssetLink()).into(AssetIMG);

        editData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AssetOprationalActivity.this, EditAssetOprational.class);
                i.putExtra("asset", assetModel);
                startActivity(i);
            }
        });

        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.removeValue();
                Toast.makeText(AssetOprationalActivity.this, "Asset deleted...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), AssetOprationalActivity.class));
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
