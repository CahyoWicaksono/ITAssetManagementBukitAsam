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

import com.example.itassetmanagementptbukitasam.OtherMenu.Capture;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.Adapter.ReceiverAdapter.ReceiverSDMAdapter;
import com.example.itassetmanagementptbukitasam.AddData.AddReceiver.AddReceiverSDM;
import com.example.itassetmanagementptbukitasam.ChangeData.Editreceiver.EditReceiverSDM;
import com.example.itassetmanagementptbukitasam.SDM.HomeSDM;
import com.example.itassetmanagementptbukitasam.SDM.ProfileSDMActivity;
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

public class ReceiverSDMActivity extends AppCompatActivity implements ReceiverSDMAdapter.ViewDataSDMReceiverClickInterface {

    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FloatingActionButton floatingActionButton;
    private ArrayList<ReceiverModel> receiverModelArrayList;
    private ReceiverSDMAdapter receiverSDMAdapter;
    private NestedScrollView bottomreceiver;
    private ProgressBar loadingPB;
    Toolbar backdamage;
    private SwipeRefreshLayout swipeRefreshLayout;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_penerimaan_sdm);

        swipeToRefresh();

        recyclerView            = findViewById(R.id.penerimaan_data_sdm);
        floatingActionButton    = findViewById(R.id.tambah_receiver_sdm);
        firebaseDatabase        = FirebaseDatabase.getInstance();
        databaseReference       = firebaseDatabase.getReference("Receiver_sdm");
        receiverModelArrayList   = new ArrayList<>();
        bottomreceiver            = findViewById(R.id.idRLAset_receiver_sdm);
        loadingPB               = findViewById(R.id.progress_bar_receiver_sdm);

        receiverSDMAdapter = new ReceiverSDMAdapter(receiverModelArrayList, this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(receiverSDMAdapter);


        bottomNavigationView = findViewById(R.id.botomNavigation_receiver_sdm);

        bottomNavigationView.setSelectedItemId(R.id.id_home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.id_home:
                        startActivity(new Intent(getApplicationContext(), HomeSDM.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.id_profile:
                        startActivity(new Intent(getApplicationContext(), ProfileSDMActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.id_code:
                        IntentIntegrator intentIntegrator = new IntentIntegrator(ReceiverSDMActivity.this);
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
                startActivity(new Intent(ReceiverSDMActivity.this, AddReceiverSDM.class));

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
                    receiverSDMAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    loadingPB.setVisibility(View.GONE);
                    receiverSDMAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                    loadingPB.setVisibility(View.GONE);
                    receiverSDMAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    loadingPB.setVisibility(View.GONE);
                    receiverSDMAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }

    @Override
    public void onViewDataSDMReceiverClick(int position) {
        displayBottomShet(receiverModelArrayList.get(position));

    }
    private  void displayBottomShet(ReceiverModel receiverModel){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_data_penerimaan_sdm,bottomreceiver);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView Name_Asset_Receiver             = layout.findViewById(R.id.idTVAssetName_receiver_sdm);
        TextView Asset_Code_Receiver             = layout.findViewById(R.id.idTVAssetName_receiver_sdm);
        TextView Divisi_Asset_Receiver           = layout.findViewById(R.id.idTVType_receiver_sdm);
        TextView Amount_Receiver                 = layout.findViewById(R.id.idTVAmount_receiver_sdm);
        TextView Condition_Receiver            = layout.findViewById(R.id.idTVDesc_receiver_sdm);
        TextView Date_Receiver                   = layout.findViewById(R.id.idTVDate_receiver_sdm);


        Button editData                         = layout.findViewById(R.id.editData_receiver_sdm);
        Button deleteData                       = layout.findViewById(R.id.DeleteData_receiver_sdm);

        Name_Asset_Receiver.setText(receiverModel.getNamaAsetPen());
        Asset_Code_Receiver.setText(receiverModel.getKodeAsetPen());
        Divisi_Asset_Receiver.setText(receiverModel.getDivisiPen());
        Amount_Receiver.setText(receiverModel.getJumlahPen());
        Condition_Receiver.setText(receiverModel.getKondisiPen());
        Date_Receiver.setText(receiverModel.getTanggalPen());


        editData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ReceiverSDMActivity.this, EditReceiverSDM.class);
                i.putExtra("receiver_sdm", receiverModel);
                startActivity(i);
            }
        });

        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.removeValue();
                Toast.makeText(ReceiverSDMActivity.this, "Data deleted...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), ReceiverSDMActivity.class));
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
                    ReceiverSDMActivity.this);
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
            Toast.makeText(ReceiverSDMActivity.this, "Scan Failed", Toast.LENGTH_SHORT).show();
        }

    }

}

