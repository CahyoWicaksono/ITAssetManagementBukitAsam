package com.example.itassetmanagementptbukitasam.ListData.Receiver.ListReceiver;


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
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.itassetmanagementptbukitasam.Adapter.ReceiverAdapter.ReceiverSafetyAdapter;
import com.example.itassetmanagementptbukitasam.AddData.AddReceiver.AddReceiverSafety;
import com.example.itassetmanagementptbukitasam.Admin.GenerateCode;
import com.example.itassetmanagementptbukitasam.Admin.HomeAdminActivity;
import com.example.itassetmanagementptbukitasam.Admin.ProfileAdminActivity;
import com.example.itassetmanagementptbukitasam.ChangeData.EditAsset.EditAssetSafety;
import com.example.itassetmanagementptbukitasam.Logistic.HomeLogisticActivity;
import com.example.itassetmanagementptbukitasam.Logistic.ProfileLogisticActivity;
import com.example.itassetmanagementptbukitasam.OtherMenu.Capture;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.Safety.HomeSafety;
import com.example.itassetmanagementptbukitasam.Safety.ProfileK3LActivity;
import com.example.itassetmanagementptbukitasam.model.ReceiverModel;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewReceiverSafetyActivity extends AppCompatActivity implements ReceiverSafetyAdapter.ViewDataSafetyReceiverClickInterface {

    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FloatingActionButton floatingActionButton;
    private ArrayList<ReceiverModel> receiverModelArrayList;
    private ReceiverSafetyAdapter receiverSafetyAdapter;
    private NestedScrollView bottomreceiver;
    private ProgressBar loadingPB;
    Toolbar backdamage;
    private SwipeRefreshLayout swipeRefreshLayout;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_penerimaan_safety_view);

        swipeToRefresh();

        recyclerView            = findViewById(R.id.penerimaan_data_safety_view);
        firebaseDatabase        = FirebaseDatabase.getInstance();
        databaseReference       = firebaseDatabase.getReference("Receiver_safety");
        receiverModelArrayList   = new ArrayList<>();
        bottomreceiver            = findViewById(R.id.idRLAset_receiver_safety_view);
        loadingPB               = findViewById(R.id.progress_bar_receiver_safety_view);

        receiverSafetyAdapter = new ReceiverSafetyAdapter(receiverModelArrayList, this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(receiverSafetyAdapter);


        bottomNavigationView = findViewById(R.id.botomNavigation_receiver_safety_view);

        bottomNavigationView.setSelectedItemId(R.id.id_home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.id_home:
                        startActivity(new Intent(getApplicationContext(), HomeAdminActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.id_profile:
                        startActivity(new Intent(getApplicationContext(), ProfileAdminActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.id_code:
                        startActivity(new Intent(getApplicationContext(), GenerateCode.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        getAllDamage();
    }
        public void  getAllDamage(){
            receiverModelArrayList.clear();
            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    loadingPB.setVisibility(View.GONE);
                    receiverModelArrayList.add(snapshot.getValue(ReceiverModel.class));
                    receiverSafetyAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    loadingPB.setVisibility(View.GONE);
                    receiverSafetyAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                    loadingPB.setVisibility(View.GONE);
                    receiverSafetyAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    loadingPB.setVisibility(View.GONE);
                    receiverSafetyAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }

    @Override
    public void onViewDataSafetyReceiverClick(int position) {
        displayBottomShet(receiverModelArrayList.get(position));

    }
    private  void displayBottomShet(ReceiverModel receiverModel){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_data_penerimaan_safetyview,bottomreceiver);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView Name_Asset_Receiver             = layout.findViewById(R.id.idTVAssetName_receiver_safety_view);
        TextView Asset_Code_Receiver             = layout.findViewById(R.id.idTVAssetName_receiver_safety_view);
        TextView Divisi_Asset_Receiver           = layout.findViewById(R.id.idTVType_receiver_safety_view);
        TextView Amount_Receiver                 = layout.findViewById(R.id.idTVAmount_receiver_safety_view);
        TextView Condition_Receiver            = layout.findViewById(R.id.idTVDesc_receiver_safety_view);
        TextView Date_Receiver                   = layout.findViewById(R.id.idTVDate_receiver_safety_view);

        Name_Asset_Receiver.setText(receiverModel.getNamaAsetPen());
        Asset_Code_Receiver.setText(receiverModel.getKodeAsetPen());
        Divisi_Asset_Receiver.setText(receiverModel.getDivisiPen());
        Amount_Receiver.setText(receiverModel.getJumlahPen());
        Condition_Receiver.setText(receiverModel.getKondisiPen());
        Date_Receiver.setText(receiverModel.getTanggalPen());

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

