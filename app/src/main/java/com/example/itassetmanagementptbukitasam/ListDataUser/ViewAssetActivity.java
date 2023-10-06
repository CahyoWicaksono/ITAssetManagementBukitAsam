package com.example.itassetmanagementptbukitasam.ListDataUser;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.itassetmanagementptbukitasam.Adapter.OtherAdapter.ViewAssetAdapter;
import com.example.itassetmanagementptbukitasam.Admin.GenerateCode;
import com.example.itassetmanagementptbukitasam.Admin.HomeAdminActivity;
import com.example.itassetmanagementptbukitasam.Admin.ProfileAdminActivity;
import com.example.itassetmanagementptbukitasam.OtherMenu.ScanQRCodeActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.User.HomeUserActivity;
import com.example.itassetmanagementptbukitasam.User.ProfileUserActivity;
import com.example.itassetmanagementptbukitasam.User.QRCodeActivity;
import com.example.itassetmanagementptbukitasam.model.AssetModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewAssetActivity extends AppCompatActivity implements ViewAssetAdapter.ViewAssetDataClickInterface {


    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<AssetModel> assetModelArrayList;
    private ViewAssetAdapter viewAssetAdapter;
    private NestedScrollView bottomViewasset;
    Toolbar backview;
    private SwipeRefreshLayout swipeRefreshLayout;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_aset);

        swipeToRefresh();

        recyclerView = findViewById(R.id.view_data);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Asset");
        assetModelArrayList = new ArrayList<>();
        bottomViewasset = findViewById(R.id.idRLAset_view);

        viewAssetAdapter = new ViewAssetAdapter(assetModelArrayList, this,  this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(viewAssetAdapter);


        bottomNavigationView = findViewById(R.id.botomNavigation_view_asset);

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
        getAllAsset();


    }
    public void  getAllAsset() {
        assetModelArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                assetModelArrayList.add(snapshot.getValue(AssetModel.class));
                viewAssetAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                viewAssetAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                viewAssetAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                viewAssetAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onViewAssetDataClick(int position) {
        displayBottomShet(assetModelArrayList.get(position));

    }
    private  void displayBottomShet(AssetModel assetModel) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_view_asset, bottomViewasset);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView AssetCode = layout.findViewById(R.id.idTVAssetCode_view);
        TextView AssetName = layout.findViewById(R.id.idTVAssetName_view);
        TextView Supplier = layout.findViewById(R.id.idTVSupplier_view);
        TextView Purchase = layout.findViewById(R.id.idTVPurchase_view);
        TextView Begining  = layout.findViewById(R.id.id_TVdateUse_view);
        TextView End = layout.findViewById(R.id.id_dateEnd_view);
        TextView Type = layout.findViewById(R.id.idTVType_view);
        TextView Merk = layout.findViewById(R.id.idTVMerk_view);
        TextView Specification = layout.findViewById(R.id.idTVSpecification_view);
        TextView Amount = layout.findViewById(R.id.idTVAmount_view);
        ImageView AssetIMG = layout.findViewById(R.id.image_view_asset);

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

