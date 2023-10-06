package com.example.itassetmanagementptbukitasam.ListData.AssetData;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.example.itassetmanagementptbukitasam.Adapter.AssetAdapter.AssetAdapter;
import com.example.itassetmanagementptbukitasam.AddData.AddAsset.AddAssetActivity;
import com.example.itassetmanagementptbukitasam.Admin.GenerateCode;
import com.example.itassetmanagementptbukitasam.Admin.HomeAdminActivity;
import com.example.itassetmanagementptbukitasam.Admin.ProfileAdminActivity;
import com.example.itassetmanagementptbukitasam.ChangeData.EditAsset.EditAsset;
import com.example.itassetmanagementptbukitasam.ListData.OtherData.LoocationViewActivity;
import com.example.itassetmanagementptbukitasam.ListData.OtherData.ViewLoocationActivity;
import com.example.itassetmanagementptbukitasam.Logistic.HomeLogisticActivity;
import com.example.itassetmanagementptbukitasam.Logistic.ProfileLogisticActivity;
import com.example.itassetmanagementptbukitasam.OtherMenu.Capture;
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
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;

public class AssetDataActivity extends AppCompatActivity implements AssetAdapter.ViewDataClickInterface{

    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FloatingActionButton floatingActionButton;
    private ArrayList<AssetModel> assetModelArrayList;
    private AssetAdapter viewAdapterAssetData;
    private ProgressBar loadingPB;
    private NestedScrollView bottomasset;
    private SwipeRefreshLayout swipeRefreshLayout;
    private BottomNavigationView bottomNavigationView;
    private TextView total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_aset);

        swipeToRefresh();

        recyclerView = findViewById(R.id.am_data);
        floatingActionButton = findViewById(R.id.tambah_Aset);
        firebaseDatabase = FirebaseDatabase.getInstance();
        loadingPB = findViewById(R.id.progress_bar_asset);
        databaseReference = firebaseDatabase.getReference("Asset");
        assetModelArrayList = new ArrayList<>();
        bottomasset = findViewById(R.id.idRLAset);
        swipeRefreshLayout = findViewById(R.id.swipeContainer);
        bottomNavigationView = findViewById(R.id.botomNavigation_asset);
        total = findViewById(R.id.location_asset);

        viewAdapterAssetData = new AssetAdapter(assetModelArrayList, this,  this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(viewAdapterAssetData);


        bottomNavigationView = findViewById(R.id.botomNavigation_asset);

        bottomNavigationView.setSelectedItemId(R.id.id_home);

        total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoocationViewActivity.class));
                overridePendingTransition(0,0);
            }
        });

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
                        IntentIntegrator intentIntegrator = new IntentIntegrator(AssetDataActivity.this);
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

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AssetDataActivity.this, AddAssetActivity.class));
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
                viewAdapterAssetData.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                viewAdapterAssetData.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                viewAdapterAssetData.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                viewAdapterAssetData.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
}

    @Override
    public void onViewDataClick(int position) {
        displayBottomShet(assetModelArrayList.get(position));

    }
    private  void displayBottomShet(AssetModel assetModel) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_data_asset, bottomasset);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView AssetCode = layout.findViewById(R.id.idTVAssetCode);
        TextView AssetName = layout.findViewById(R.id.idTVAssetName);
        TextView Supplier = layout.findViewById(R.id.idTVSupplier);
        TextView Purchase = layout.findViewById(R.id.idTVPurchase);
        TextView Begining  = layout.findViewById(R.id.id_TVdateUse);
        TextView End = layout.findViewById(R.id.id_dateEnd);
        TextView Type = layout.findViewById(R.id.idTVType);
        TextView Merk = layout.findViewById(R.id.idTVMerk);
        TextView Specification = layout.findViewById(R.id.idTVSpecification);
        TextView Amount = layout.findViewById(R.id.idTVAmount);
        ImageView AssetIMG = layout.findViewById(R.id.image_asset);

        Button editData = layout.findViewById(R.id.editDataAsset);
        Button deleteData = layout.findViewById(R.id.DeleteData);

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
                Intent i = new Intent(AssetDataActivity.this, EditAsset.class);
                i.putExtra("asset", assetModel);
                startActivity(i);
            }
        });

        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.removeValue();
                Toast.makeText(AssetDataActivity.this, "Asset deleted...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), AssetDataActivity.class));
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if ((intentResult.getContents() != null)){
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(
                    AssetDataActivity.this);
            builder.setTitle("Information");
            builder.setMessage(intentResult.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        }else {
            Toast.makeText(AssetDataActivity.this, "Scan Failed", Toast.LENGTH_SHORT).show();
        }

    }


}
