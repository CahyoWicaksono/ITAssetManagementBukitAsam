package com.example.itassetmanagementptbukitasam.ListData.Withdrawal.List;


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

import com.example.itassetmanagementptbukitasam.Adapter.WithdrawalAdapter.WithdrawalHumasAdapter;
import com.example.itassetmanagementptbukitasam.AddData.AddWithdrawal.AddWithdrawalActivity;
import com.example.itassetmanagementptbukitasam.AddData.AddWithdrawal.AddWithdrawalHumasActivity;
import com.example.itassetmanagementptbukitasam.Admin.GenerateCode;
import com.example.itassetmanagementptbukitasam.Admin.HomeAdminActivity;
import com.example.itassetmanagementptbukitasam.Admin.ProfileAdminActivity;
import com.example.itassetmanagementptbukitasam.ChangeData.EditWithdrawal.EditWithdrawal;
import com.example.itassetmanagementptbukitasam.ChangeData.EditWithdrawal.EditWithdrawalHumas;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.WithdrawalModel;
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

public class WithdrawalHumasActivity extends AppCompatActivity implements WithdrawalHumasAdapter.WithdrawalHumasClickInterface {

    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FloatingActionButton floatingActionButton;
    private ArrayList<WithdrawalModel> withdrawalModelArrayList;
    private WithdrawalHumasAdapter withdrawalHumasAdapter;
    private NestedScrollView bottomWith;
    private ProgressBar loadingPB;
    Toolbar backwith;
    private SwipeRefreshLayout swipeRefreshLayout;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_withdrawal_humas);


        swipeToRefresh();

        recyclerView = findViewById(R.id.with_data_humas);
        floatingActionButton = findViewById(R.id.tambah_penarikan_humas);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Withdrawal_humas");
        withdrawalModelArrayList = new ArrayList<>();
        bottomWith = findViewById(R.id.idRLWithdrawal_humas);
        loadingPB = findViewById(R.id.progress_bar_withdrawal_humas);

        withdrawalHumasAdapter = new WithdrawalHumasAdapter(withdrawalModelArrayList, this,  this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(withdrawalHumasAdapter);

        bottomNavigationView = findViewById(R.id.botomNavigation_withrawal_humas);

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
                startActivity(new Intent(WithdrawalHumasActivity.this, AddWithdrawalHumasActivity.class));

            }
        });
        getAllWith();

    }
    public void  getAllWith(){
        withdrawalModelArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                withdrawalModelArrayList.add(snapshot.getValue(WithdrawalModel.class));
                withdrawalHumasAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                withdrawalHumasAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                loadingPB.setVisibility(View.GONE);
                withdrawalHumasAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                withdrawalHumasAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onWithdrawalHumasClick(int position) {
        displayBottomShet(withdrawalModelArrayList.get(position));

    }
    private  void displayBottomShet(WithdrawalModel withdrawalModel){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_withdrawal_data_humas,bottomWith);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();


        TextView WithCode           = layout.findViewById(R.id.idTVWithdrawalCode_humas);
        TextView AssetCode      = layout.findViewById(R.id.idTVAssetCode_Withdrawal_humas);
        TextView Date      = layout.findViewById(R.id.idTVDateWithdrawal_humas);
        TextView Amount         = layout.findViewById(R.id.idTVAmountWithdrawal_humas);
        TextView Division           = layout.findViewById(R.id.idTVDivisonWithdrawal_humas);
        TextView Reason  = layout.findViewById(R.id.idTVReasonWithdrawal_humas);
        TextView Officer         = layout.findViewById(R.id.idTVOfficerWithdrawal_humas);
        TextView Description  = layout.findViewById(R.id.idTVDescriptionWithdrawal_humas);

        Button editData = layout.findViewById(R.id.editDataWithdrawal_humas);
        Button deleteData = layout.findViewById(R.id.DeleteDataWithdrawal_humas);


        WithCode.setText(withdrawalModel.getKode_penarikan());
        AssetCode.setText(withdrawalModel.getKode_aset());
        Date.setText(withdrawalModel.getTanggal());
        Amount.setText(withdrawalModel.getJumlah_penarikan());
        Division.setText(withdrawalModel.getDevisi());
        Reason.setText(withdrawalModel.getAlasan());
        Officer.setText(withdrawalModel.getPetugas());
        Description.setText(withdrawalModel.getKeterangan_penarikan());


        editData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(WithdrawalHumasActivity.this, EditWithdrawalHumas.class);
                i.putExtra("withdrawal_lab", withdrawalModel);
                startActivity(i);
            }
        });

        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.removeValue();
                Toast.makeText(WithdrawalHumasActivity.this, "Withdrawal deleted...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), WithdrawalHumasActivity.class));
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

