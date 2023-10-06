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


import com.example.itassetmanagementptbukitasam.Admin.GenerateCode;
import com.example.itassetmanagementptbukitasam.Admin.HomeAdminActivity;
import com.example.itassetmanagementptbukitasam.Admin.ProfileAdminActivity;
import com.example.itassetmanagementptbukitasam.ChangeData.OtherEdit.EditTransfer;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.Adapter.OtherAdapter.TransferAdapter;
import com.example.itassetmanagementptbukitasam.AddData.OtherAddData.AddTransferActivity;
import com.example.itassetmanagementptbukitasam.Report.ReportDivision.ReportAdmin;
import com.example.itassetmanagementptbukitasam.model.TransferModel;
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

public class TransferActivity extends AppCompatActivity implements TransferAdapter.TransferClickInterface {

    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FloatingActionButton floatingActionButton;
    private ArrayList<TransferModel> transferModelArrayList;
    private TransferAdapter transferAdapter;
    private NestedScrollView bottomTrans;
    private ProgressBar loadingPB;
    private Toolbar backtransfer;
    private SwipeRefreshLayout swipeRefreshLayout;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_transfer);


        swipeToRefresh();


        recyclerView = findViewById(R.id.tf_data);
        floatingActionButton = findViewById(R.id.tambah_transfer);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Transfer");
        transferModelArrayList = new ArrayList<>();
        bottomTrans = findViewById(R.id.idRLTransfer);
        loadingPB = findViewById(R.id.progress_bar_transfer);

        transferAdapter = new TransferAdapter(transferModelArrayList, this,  this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(transferAdapter);


        bottomNavigationView = findViewById(R.id.botomNavigation_transfer);

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
                startActivity(new Intent(TransferActivity.this, AddTransferActivity.class));

            }
        });
        getAllTrans();
    }

    public void  getAllTrans(){
        transferModelArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                transferModelArrayList.add(snapshot.getValue(TransferModel.class));
                transferAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                transferAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                loadingPB.setVisibility(View.GONE);
                transferAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                transferAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onTransferClick(int position) {
        displayBottomShet(transferModelArrayList.get(position));

    }
    private  void displayBottomShet(TransferModel transferModel){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_transfer_data,bottomTrans);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView transfer_code        = layout.findViewById(R.id.idTVTFCode);
        TextView asset_code     = layout.findViewById(R.id.idTVAssetCode_TF);
        TextView date          = layout.findViewById(R.id.idTVDateTF);
        TextView giver_asset        = layout.findViewById(R.id.idTVGiverTF);
        TextView receiver_asset      = layout.findViewById(R.id.idTVReceiverTF);
        TextView division      = layout.findViewById(R.id.idTVDivisonTF);
        TextView condition_asset     = layout.findViewById(R.id.idTVConditionTF);
        TextView status_asset        = layout.findViewById(R.id.idTVStatusTF);
        TextView description   = layout.findViewById(R.id.idTVDescriptionTF);

        Button editData        = layout.findViewById(R.id.editDataTF);
        Button deleteData      = layout.findViewById(R.id.DeleteDataTF);

        transfer_code.setText(transferModel.getKode_transfer());
        asset_code.setText(transferModel.getKode_aset());
        date.setText(transferModel.getTanggal());
        giver_asset.setText(transferModel.getGiver());
        receiver_asset.setText(transferModel.getReceiver());
        division.setText(transferModel.getDevision());
        condition_asset.setText(transferModel.getCondition());
        status_asset.setText(transferModel.getStatus());
        description.setText(transferModel.getDescription());


        editData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TransferActivity.this, EditTransfer.class);
                i.putExtra("transfer", transferModel);
                startActivity(i);
            }
        });

        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.removeValue();
                Toast.makeText(TransferActivity.this, "Asset deleted...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), TransferActivity.class));
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

