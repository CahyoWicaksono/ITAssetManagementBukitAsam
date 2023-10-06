package com.example.itassetmanagementptbukitasam.ListData.Request.ListRequest;


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

import com.example.itassetmanagementptbukitasam.Oprational.HomeOprational;
import com.example.itassetmanagementptbukitasam.Oprational.ProfileOprationalActivity;
import com.example.itassetmanagementptbukitasam.OtherMenu.Capture;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.Adapter.RequestAdapter.RequestOprationalAdapter;
import com.example.itassetmanagementptbukitasam.AddData.AddRequest.AddRequestOprational;
import com.example.itassetmanagementptbukitasam.ChangeData.EditRequest.EditRequestOprational;
import com.example.itassetmanagementptbukitasam.model.RequestModel;
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

public class RequestOprationalActivity extends AppCompatActivity implements RequestOprationalAdapter.ViewDataOprationalRequestClickInterface {

    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FloatingActionButton floatingActionButton;
    private ArrayList<RequestModel> requestModelArrayList;
    private RequestOprationalAdapter requestOprationalAdapter;
    private NestedScrollView bottomRequest;
    private ProgressBar loadingPB;
    Toolbar backdamage;
    private SwipeRefreshLayout swipeRefreshLayout;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_permintaan_oprational);

        swipeToRefresh();

        recyclerView            = findViewById(R.id.permintaan_data_oprational);
        floatingActionButton    = findViewById(R.id.tambah_request_oprational);
        firebaseDatabase        = FirebaseDatabase.getInstance();
        databaseReference       = firebaseDatabase.getReference("Request_oprational");
        requestModelArrayList = new ArrayList<>();
        bottomRequest            = findViewById(R.id.idRLAset_request_oprational);
        loadingPB               = findViewById(R.id.progress_bar_request_oprational);

        requestOprationalAdapter = new RequestOprationalAdapter(requestModelArrayList, this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(requestOprationalAdapter);


        bottomNavigationView = findViewById(R.id.botomNavigation_request_oprational);

        bottomNavigationView.setSelectedItemId(R.id.id_home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.id_home:
                        startActivity(new Intent(getApplicationContext(), HomeOprational.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.id_profile:
                        startActivity(new Intent(getApplicationContext(), ProfileOprationalActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.id_code:
                        IntentIntegrator intentIntegrator = new IntentIntegrator(RequestOprationalActivity.this);
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
                startActivity(new Intent(RequestOprationalActivity.this, AddRequestOprational.class));

            }

        });
        getAllDamage();
    }
        public void  getAllDamage(){
            requestModelArrayList.clear();
            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    loadingPB.setVisibility(View.GONE);
                    requestModelArrayList.add(snapshot.getValue(RequestModel.class));
                    requestOprationalAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    loadingPB.setVisibility(View.GONE);
                    requestOprationalAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                    loadingPB.setVisibility(View.GONE);
                    requestOprationalAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    loadingPB.setVisibility(View.GONE);
                    requestOprationalAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }

    @Override
    public void onViewDataOprationalRequestClick(int position) {
        displayBottomShet(requestModelArrayList.get(position));

    }
    private  void displayBottomShet(RequestModel requestModel){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_data_permintaan_oprational,bottomRequest);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView Name_Asset_Request_oprational             = layout.findViewById(R.id.idTVAssetName_request_oprational);
        TextView Asset_Code_Request_oprational             = layout.findViewById(R.id.idTVAssetName_request_oprational);
        TextView Divisi_Asset_Request_oprational           = layout.findViewById(R.id.idTVType_request_oprational);
        TextView Amount_Request_oprational                 = layout.findViewById(R.id.idTVAmount_request_oprational);
        TextView Description_Request_oprational            = layout.findViewById(R.id.idTVDesc_request_oprational);
        TextView Date_Request_oprational                   = layout.findViewById(R.id.idTVDate_request_oprational);

        ImageView Image_Asset_oprational                   = layout.findViewById(R.id.image_asset_request_oprational);

        Button editData_oprational                         = layout.findViewById(R.id.editData_request_oprational);
        Button deleteData_oprational                       = layout.findViewById(R.id.DeleteData_request_oprational);

        Name_Asset_Request_oprational.setText(requestModel.getNameRequest());
        Asset_Code_Request_oprational.setText(requestModel.getKodeAsetRequest());
        Divisi_Asset_Request_oprational.setText(requestModel.getDivisionRequest());
        Amount_Request_oprational.setText(requestModel.getAmountRequest());
        Description_Request_oprational.setText(requestModel.getDescRequest());
        Date_Request_oprational.setText(requestModel.getDateRequest());

        Picasso.get().load(requestModel.getLinkrequest()).into(Image_Asset_oprational);

        editData_oprational.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RequestOprationalActivity.this, EditRequestOprational.class);
                i.putExtra("request_oprational", requestModel);
                startActivity(i);
            }
        });



        deleteData_oprational.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.removeValue();
                Toast.makeText(RequestOprationalActivity.this, "Data deleted...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), RequestOprationalActivity.class));
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
                    RequestOprationalActivity.this);
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
            Toast.makeText(RequestOprationalActivity.this, "Scan Failed", Toast.LENGTH_SHORT).show();
        }

    }
}

