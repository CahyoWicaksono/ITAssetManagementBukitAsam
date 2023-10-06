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
import com.example.itassetmanagementptbukitasam.AddData.AddAsset.AddAssetSDMActivity;
import com.example.itassetmanagementptbukitasam.Admin.GenerateCode;
import com.example.itassetmanagementptbukitasam.Admin.HomeAdminActivity;
import com.example.itassetmanagementptbukitasam.Adapter.AssetAdapter.AssetSDMAdapter;
import com.example.itassetmanagementptbukitasam.ChangeData.EditAsset.EditAssetSDM;
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

public class AssetDataSDMActivity extends AppCompatActivity implements AssetSDMAdapter.ViewDataSDMClickInterface{

    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FloatingActionButton floatingActionButton;
    private ArrayList<AssetModel> assetModelArrayList;
    private AssetSDMAdapter assetSDMAdapter;
    private ProgressBar loadingPB;
    private NestedScrollView bottomasset;
    private SwipeRefreshLayout swipeRefreshLayout;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_aset);

        swipeToRefresh();

        recyclerView = findViewById(R.id.am_data);
        floatingActionButton = findViewById(R.id.tambah_Aset);
        firebaseDatabase = FirebaseDatabase.getInstance();
        loadingPB = findViewById(R.id.progress_bar_asset);
        databaseReference = firebaseDatabase.getReference("Asset_SDM");
        assetModelArrayList = new ArrayList<>();
        bottomasset = findViewById(R.id.idRLAset);
        swipeRefreshLayout = findViewById(R.id.swipeContainer);
        bottomNavigationView = findViewById(R.id.botomNavigation_asset);

        assetSDMAdapter = new AssetSDMAdapter(assetModelArrayList, this,  this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(assetSDMAdapter);




        bottomNavigationView = findViewById(R.id.botomNavigation_asset);

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
                startActivity(new Intent(AssetDataSDMActivity.this, AddAssetSDMActivity.class));
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
                assetSDMAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                assetSDMAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                assetSDMAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                assetSDMAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
}

    @Override
    public void onViewDataSDMClick(int position) {
        displayBottomShet(assetModelArrayList.get(position));

    }
    private  void displayBottomShet(AssetModel assetModel) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_data_asset_sdm, bottomasset);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView AssetCode = layout.findViewById(R.id.idTVAssetCodeComplaint);
        TextView AssetName = layout.findViewById(R.id.idTVAssetNameComplaint);
        TextView Supplier = layout.findViewById(R.id.idTVSupplierComplaint);
        TextView Purchase = layout.findViewById(R.id.idTVPurchaseComplaint);
        TextView Begining  = layout.findViewById(R.id.id_TVdateUseComplaint);
        TextView End = layout.findViewById(R.id.id_dateEndComplaint);
        TextView Type = layout.findViewById(R.id.idTVTypeComplaint);
        TextView Merk = layout.findViewById(R.id.idTVMerkComplaint);
        TextView Specification = layout.findViewById(R.id.idTVSpecificationComplaint);
        TextView Amount = layout.findViewById(R.id.idTVAmountComplaint);
        ImageView AssetIMG = layout.findViewById(R.id.image_assetComplaint);

        Button editData = layout.findViewById(R.id.editDataAssetComplaint);
        Button deleteData = layout.findViewById(R.id.DeleteDataComplaint);

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
                Intent i = new Intent(AssetDataSDMActivity.this, EditAssetSDM.class);
                i.putExtra("asset", assetModel);
                startActivity(i);
            }
        });

        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.removeValue();
                Toast.makeText(AssetDataSDMActivity.this, "Asset deleted...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), AssetDataSDMActivity.class));
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
