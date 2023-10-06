package com.example.itassetmanagementptbukitasam.ListData.OtherData;

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
import com.example.itassetmanagementptbukitasam.Adapter.OtherAdapter.DesctructionAdapter;
import com.example.itassetmanagementptbukitasam.AddData.OtherAddData.AddDestruction;
import com.example.itassetmanagementptbukitasam.Admin.GenerateCode;
import com.example.itassetmanagementptbukitasam.Admin.HomeAdminActivity;
import com.example.itassetmanagementptbukitasam.ChangeData.OtherEdit.EditDescruction;
import com.example.itassetmanagementptbukitasam.Logistic.HomeLogisticActivity;
import com.example.itassetmanagementptbukitasam.Logistic.ProfileLogisticActivity;
import com.example.itassetmanagementptbukitasam.OtherMenu.Capture;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.PemusnahanModel;
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

public class DesctructionActivity extends AppCompatActivity implements DesctructionAdapter.ViewDataDesctructionClickInterface{

    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FloatingActionButton floatingActionButton;
    private ArrayList<PemusnahanModel> pemusnahanModelArrayList;
    private DesctructionAdapter desctructionAdapter;
    private ProgressBar loadingPB;
    private NestedScrollView bottomDesctruction;
    private SwipeRefreshLayout swipeRefreshLayout;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_pemusnahan);

        swipeToRefresh();

        recyclerView = findViewById(R.id.pemusnahan_data);
        floatingActionButton = findViewById(R.id.tambah_Desctruction);
        firebaseDatabase = FirebaseDatabase.getInstance();
        loadingPB = findViewById(R.id.progress_bar_Desctruction);
        databaseReference = firebaseDatabase.getReference("Destruction");
        pemusnahanModelArrayList = new ArrayList<>();
        bottomDesctruction = findViewById(R.id.idRLAset_Desctruction);
        bottomNavigationView = findViewById(R.id.botomNavigation_Desctruction);

        desctructionAdapter = new DesctructionAdapter(pemusnahanModelArrayList, this,  this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(desctructionAdapter);


        bottomNavigationView = findViewById(R.id.botomNavigation_Desctruction);


        floatingActionButton =findViewById(R.id.tambah_Desctruction);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddDestruction.class));
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.id_home);

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
                        IntentIntegrator intentIntegrator = new IntentIntegrator(DesctructionActivity.this);
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
                startActivity(new Intent(DesctructionActivity.this, AddDestruction.class));
                finish();

            }
        });
        getAllAsset();
    }
    public void  getAllAsset(){
        pemusnahanModelArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                pemusnahanModelArrayList.add(snapshot.getValue(PemusnahanModel.class));
                desctructionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                desctructionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                desctructionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                desctructionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
}

    @Override
    public void onViewDataDesctructionClick(int position) {
        displayBottomShet(pemusnahanModelArrayList.get(position));
    }
    private  void displayBottomShet(PemusnahanModel pemusnahanModel) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_data_pemusnahan, bottomDesctruction);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView Name_Asset_Destruction = layout.findViewById(R.id.idTVAssetName_Desctruction);
        TextView Asset_Code_Destruction = layout.findViewById(R.id.idTVAssetName_Desctruction);
        TextView Type_Asset_Destruction = layout.findViewById(R.id.idTVType_Desctruction);
        TextView Categories_Destruction = layout.findViewById(R.id.idTVCategories_Desctruction);
        TextView Amount_Destruction  = layout.findViewById(R.id.idTVAmount_Desctruction);
        TextView Description_Destruction = layout.findViewById(R.id.idTVDesc_Desctruction);
        TextView Date_Destruction = layout.findViewById(R.id.idTVDate_Desctruction);
        ImageView ImagePemusnahan = layout.findViewById(R.id.image_asset_pemusnahan);


        Button editData = layout.findViewById(R.id.editData_Desctruction);
        Button deleteData = layout.findViewById(R.id.DeleteData_Desctruction);

        Name_Asset_Destruction.setText(pemusnahanModel.getNamaAsetPem());
        Asset_Code_Destruction.setText(pemusnahanModel.getKodeAssetPem());
        Type_Asset_Destruction.setText(pemusnahanModel.getTypeAsetPem());
        Categories_Destruction.setText(pemusnahanModel.getkategoriPem());
        Amount_Destruction.setText(pemusnahanModel.getJumlahPem());
        Description_Destruction.setText(pemusnahanModel.getKeteranganPem());
        Date_Destruction.setText(pemusnahanModel.getTanggalPem());


        Picasso.get().load(pemusnahanModel.getLinkImageDestruction()).into(ImagePemusnahan);

        editData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DesctructionActivity.this, EditDescruction.class);
                i.putExtra("desctruction", pemusnahanModel);
                startActivity(i);
            }
        });

        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.removeValue();
                Toast.makeText(DesctructionActivity.this, "Data deleted...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), DesctructionActivity.class));
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if ((intentResult.getContents() != null)){
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(
                    DesctructionActivity.this);
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
            Toast.makeText(DesctructionActivity.this, "Scan Failed", Toast.LENGTH_SHORT).show();
        }

    }

}
