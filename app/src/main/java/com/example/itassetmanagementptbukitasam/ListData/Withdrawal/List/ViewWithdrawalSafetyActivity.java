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

import com.example.itassetmanagementptbukitasam.Adapter.WithdrawalAdapter.WithdrawalSafetyAdapter;
import com.example.itassetmanagementptbukitasam.AddData.AddWithdrawal.AddWithdrawalSafetyActivity;
import com.example.itassetmanagementptbukitasam.Admin.GenerateCode;
import com.example.itassetmanagementptbukitasam.Admin.HomeAdminActivity;
import com.example.itassetmanagementptbukitasam.Admin.ProfileAdminActivity;
import com.example.itassetmanagementptbukitasam.ChangeData.EditWithdrawal.EditWithdrawalSafety;
import com.example.itassetmanagementptbukitasam.OtherMenu.Capture;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.Safety.HomeSafety;
import com.example.itassetmanagementptbukitasam.Safety.ProfileK3LActivity;
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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class ViewWithdrawalSafetyActivity extends AppCompatActivity implements WithdrawalSafetyAdapter.WithdrawalSafetyClickInterface {

    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FloatingActionButton floatingActionButton;
    private ArrayList<WithdrawalModel> withdrawalModelArrayList;
    private WithdrawalSafetyAdapter withdrawalSafetyAdapter;
    private NestedScrollView bottomWith;
    private ProgressBar loadingPB;
    Toolbar backwith;
    private SwipeRefreshLayout swipeRefreshLayout;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_withdrawal_view_safety);


        swipeToRefresh();

        recyclerView = findViewById(R.id.with_data_safety_view);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Withdrawal_safety");
        withdrawalModelArrayList = new ArrayList<>();
        bottomWith = findViewById(R.id.idRLWithdrawal_safety_view);
        loadingPB = findViewById(R.id.progress_bar_withdrawal_safety_view);

        withdrawalSafetyAdapter = new WithdrawalSafetyAdapter(withdrawalModelArrayList, this,  this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(withdrawalSafetyAdapter);

        bottomNavigationView = findViewById(R.id.botomNavigation_withrawal_safety_view);

        bottomNavigationView.setSelectedItemId(R.id.id_home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.id_home:
                        startActivity(new Intent(getApplicationContext(), HomeSafety.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.id_profile:
                        startActivity(new Intent(getApplicationContext(), ProfileK3LActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.id_code:
                        IntentIntegrator intentIntegrator = new IntentIntegrator(ViewWithdrawalSafetyActivity.this);
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

        getAllWithSafety();

    }
    public void  getAllWithSafety(){
        withdrawalModelArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                withdrawalModelArrayList.add(snapshot.getValue(WithdrawalModel.class));
                withdrawalSafetyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                withdrawalSafetyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                loadingPB.setVisibility(View.GONE);
                withdrawalSafetyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                withdrawalSafetyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onWithdrawalSafetyClick(int position) {
        displayBottomShet(withdrawalModelArrayList.get(position));

    }
    private  void displayBottomShet(WithdrawalModel withdrawalModel){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_withdrawal_data_safety_view,bottomWith);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();


        TextView WithCode           = layout.findViewById(R.id.idTVWithdrawalCode_safety_view);
        TextView AssetCode      = layout.findViewById(R.id.idTVAssetCode_Withdrawal_safety_view);
        TextView Date      = layout.findViewById(R.id.idTVDateWithdrawal_safety_view);
        TextView Amount         = layout.findViewById(R.id.idTVAmountWithdrawal_safety_view);
        TextView Division           = layout.findViewById(R.id.idTVDivisonWithdrawal_safety_view);
        TextView Reason  = layout.findViewById(R.id.idTVReasonWithdrawal_safety_view);
        TextView Officer         = layout.findViewById(R.id.idTVOfficerWithdrawal_safety_view);
        TextView Description  = layout.findViewById(R.id.idTVDescriptionWithdrawal_safety_view);


        WithCode.setText(withdrawalModel.getKode_penarikan());
        AssetCode.setText(withdrawalModel.getKode_aset());
        Date.setText(withdrawalModel.getTanggal());
        Amount.setText(withdrawalModel.getJumlah_penarikan());
        Division.setText(withdrawalModel.getDevisi());
        Reason.setText(withdrawalModel.getAlasan());
        Officer.setText(withdrawalModel.getPetugas());
        Description.setText(withdrawalModel.getKeterangan_penarikan());


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
                    ViewWithdrawalSafetyActivity.this);
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
            Toast.makeText(ViewWithdrawalSafetyActivity.this, "Scan Failed", Toast.LENGTH_SHORT).show();
        }

    }
}

