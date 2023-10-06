package com.example.itassetmanagementptbukitasam.ListData.OtherData;


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

import com.example.itassetmanagementptbukitasam.Adapter.OtherAdapter.ComplaintAdapter;
import com.example.itassetmanagementptbukitasam.Adapter.OtherAdapter.OrderAdapter;
import com.example.itassetmanagementptbukitasam.AddData.OtherAddData.AddOrderAsset;
import com.example.itassetmanagementptbukitasam.Admin.GenerateCode;
import com.example.itassetmanagementptbukitasam.Admin.HomeAdminActivity;
import com.example.itassetmanagementptbukitasam.Admin.ProfileAdminActivity;
import com.example.itassetmanagementptbukitasam.ChangeData.OtherEdit.EditDamage;
import com.example.itassetmanagementptbukitasam.ChangeData.OtherEdit.EditOrder;
import com.example.itassetmanagementptbukitasam.Logistic.HomeLogisticActivity;
import com.example.itassetmanagementptbukitasam.Logistic.ProfileLogisticActivity;
import com.example.itassetmanagementptbukitasam.OtherMenu.ScanQRCodeActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.ComplaintModel;
import com.example.itassetmanagementptbukitasam.model.OrderModel;
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

public class OrderActivity extends AppCompatActivity implements OrderAdapter.ViewDataOrderClickInterface{

    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FloatingActionButton floatingActionButton;
    private ArrayList<OrderModel> orderModelArrayList;
    private OrderAdapter orderAdapter;
    private NestedScrollView bottomdamage;
    private ProgressBar loadingPB;
    Toolbar backdamage;
    private SwipeRefreshLayout swipeRefreshLayout;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_order);

        swipeToRefresh();

        recyclerView            = findViewById(R.id.order_data);
        firebaseDatabase        = FirebaseDatabase.getInstance();
        databaseReference       = firebaseDatabase.getReference("Order");
        orderModelArrayList     = new ArrayList<>();
        bottomdamage            = findViewById(R.id.idRLOrder);
        loadingPB               = findViewById(R.id.progress_bar_order);
        floatingActionButton    = findViewById(R.id.tambah_order);

        orderAdapter = new OrderAdapter(orderModelArrayList, this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(orderAdapter);


        bottomNavigationView = findViewById(R.id.botomNavigation_order);

        bottomNavigationView.setSelectedItemId(R.id.id_home);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddOrderAsset.class));
                finish();

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
                        startActivity(new Intent(getApplicationContext(), ScanQRCodeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });
        getAllDamage();
    }
        public void  getAllDamage(){
            orderModelArrayList.clear();
            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    loadingPB.setVisibility(View.GONE);
                    orderModelArrayList.add(snapshot.getValue(OrderModel.class));
                    orderAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    loadingPB.setVisibility(View.GONE);
                    orderAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                    loadingPB.setVisibility(View.GONE);
                    orderAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    loadingPB.setVisibility(View.GONE);
                    orderAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }

    @Override
    public void onViewDataOrderClick(int position) {
        displayBottomShet(orderModelArrayList.get(position));

    }
    private  void displayBottomShet(OrderModel orderModel){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_order_data,bottomdamage);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView Asset_Name_Order             = layout.findViewById(R.id.idTVNameOrder);
        TextView Asset_Code_Order             = layout.findViewById(R.id.idTVAssetCodeOrder);
        TextView Date_Order         = layout.findViewById(R.id.idTVDateOrder);
        TextView Divisi_Order        = layout.findViewById(R.id.idTVDivisiOrder);
        TextView Type_Order          = layout.findViewById(R.id.idTVTypeOrder);
        TextView Amount_Order          = layout.findViewById(R.id.idTVAmountOrder);

        Button EditData           = layout.findViewById(R.id.EditOrder);
        Button DeleteData          = layout.findViewById(R.id.DeleteOrder);


        Asset_Name_Order.setText(orderModel.getNamaAsetOrder());
        Asset_Code_Order.setText(orderModel.getKodeAsetOrder());
        Date_Order.setText(orderModel.getTanggalOrder());
        Divisi_Order.setText(orderModel.getDivisiOrder());
        Type_Order.setText(orderModel.getJenisAsetOrder());
        Amount_Order.setText(orderModel.getJumlahOrder());

        DeleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.removeValue();
                Toast.makeText(OrderActivity.this, "Order Data deleted...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), OrderActivity.class));
                finish();
            }
        });

        EditData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OrderActivity.this, EditOrder.class);
                i.putExtra("order", orderModel);
                startActivity(i);
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
    public void onBackPressed() {
        super.onBackPressed();
    }

}

