package com.example.itassetmanagementptbukitasam.ListData.Response.List;


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

import com.example.itassetmanagementptbukitasam.Admin.ProfileAdminActivity;
import com.example.itassetmanagementptbukitasam.Admin.GenerateCode;
import com.example.itassetmanagementptbukitasam.Admin.HomeAdminActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.Adapter.ResponseAdapter.ResponseOprationalAdapter;
import com.example.itassetmanagementptbukitasam.AddData.AddResponse.AddResponseOprational;
import com.example.itassetmanagementptbukitasam.ChangeData.Edit.EditResponseOprational;
import com.example.itassetmanagementptbukitasam.model.ResponseModel;
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

public class ResponseOprationalActivity extends AppCompatActivity implements ResponseOprationalAdapter.ResponseOprarionalClickInterface{

    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FloatingActionButton floatingActionButton;
    private ArrayList<ResponseModel> responseModelArrayList;
    private ResponseOprationalAdapter responseOprationalAdapter;
    private NestedScrollView bottomresponse;
    private ProgressBar loadingPB;
    Toolbar backdamage;
    private SwipeRefreshLayout swipeRefreshLayout;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_response_oprational);


        recyclerView            = findViewById(R.id.am_response_oprational);
        floatingActionButton    = findViewById(R.id.tambah_response_oprational);
        firebaseDatabase        = FirebaseDatabase.getInstance();
        databaseReference       = firebaseDatabase.getReference("Response_Oprational");
        responseModelArrayList = new ArrayList<>();
        bottomresponse            = findViewById(R.id.idRLResponseOprational);
        loadingPB               = findViewById(R.id.progress_bar_response_oprational);

        responseOprationalAdapter = new ResponseOprationalAdapter(responseModelArrayList, this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(responseOprationalAdapter);


        bottomNavigationView = findViewById(R.id.botomNavigation_response_oprational);

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
                startActivity(new Intent(ResponseOprationalActivity.this, AddResponseOprational.class));

            }

        });
        getAllResponse();
    }
        public void  getAllResponse(){
            responseModelArrayList.clear();
            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    loadingPB.setVisibility(View.GONE);
                    responseModelArrayList.add(snapshot.getValue(ResponseModel.class));
                    responseOprationalAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    loadingPB.setVisibility(View.GONE);
                    responseOprationalAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                    loadingPB.setVisibility(View.GONE);
                    responseOprationalAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    loadingPB.setVisibility(View.GONE);
                    responseOprationalAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }

    @Override
    public void onResponseOprationalClick(int position) {
        displayBottomShet(responseModelArrayList.get(position));

    }
    private  void displayBottomShet(ResponseModel responseModel){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_response_oprational,bottomresponse);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView Name             = layout.findViewById(R.id.idTVNameResponseOprational);
        TextView Date             = layout.findViewById(R.id.idTVDateResponseOprational);
        TextView Division         = layout.findViewById(R.id.idTVDivisionResponseOprational);
        TextView AssetName        = layout.findViewById(R.id.idTVAssetNameResponseOprational);
        TextView Status           = layout.findViewById(R.id.idTVStatusResponseOprational);

        Button edit                 = layout.findViewById(R.id.editDataResponseOprational);
        Button deleteData           = layout.findViewById(R.id.DeleteResponseOprational);

        Name.setText(responseModel.getNamaResponse());
        Date.setText(responseModel.getTanggalResponse());
        Division.setText(responseModel.getDivisiResponse());
        AssetName.setText(responseModel.getNamaAssetResponse());
        Status.setText(responseModel.getStatusResponse());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ResponseOprationalActivity.this, EditResponseOprational.class);
                i.putExtra("response_oprational", responseModel);
                startActivity(i);
            }
        });


        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.removeValue();
                Toast.makeText(ResponseOprationalActivity.this, "Data deleted...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), ResponseOprationalActivity.class));
                finish();
            }
        });

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

