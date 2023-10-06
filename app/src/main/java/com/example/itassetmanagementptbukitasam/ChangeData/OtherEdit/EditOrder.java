package com.example.itassetmanagementptbukitasam.ChangeData.OtherEdit;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.itassetmanagementptbukitasam.ListData.OtherData.DamageActivity;
import com.example.itassetmanagementptbukitasam.ListData.OtherData.OrderActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.KerusakanModel;
import com.example.itassetmanagementptbukitasam.model.OrderModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditOrder extends AppCompatActivity {
    boolean valid = true;
    int mYear, mMonth, mDay;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    OrderModel orderModel;
    EditText namaPengadu, tanggalPengadu,Divisi, NamaAset,Problem, kode, type;
    ImageView backmaintenance;
    Button btn_simpan;
    String orderID;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_order_aset);

        tanggalPengadu = findViewById(R.id.et_date_order_update);
        Divisi = findViewById(R.id.et_division_order_update);
        NamaAset = findViewById(R.id.et_name_asset_order_update);
        Problem = findViewById(R.id.et_amount_order_update);
        kode = findViewById(R.id.et_kode_aset_order_update);
        type = findViewById(R.id.et_type_asset_order_update);


        backmaintenance = findViewById(R.id.back_order_update);

        btn_simpan = findViewById(R.id.btn_update_order);

        firebaseDatabase =FirebaseDatabase.getInstance();
        orderModel = getIntent().getParcelableExtra("order");

        if(orderModel!=null){
            NamaAset.setText(orderModel.getNamaAsetOrder());
            kode.setText(orderModel.getKodeAsetOrder());
            tanggalPengadu.setText(orderModel.getTanggalOrder());
            Divisi.setText(orderModel.getDivisiOrder());
            type.setText(orderModel.getJenisAsetOrder());
            Problem.setText(orderModel.getJumlahOrder());


        }
        databaseReference = firebaseDatabase.getReference("Order").child(orderID);

        backmaintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), OrderActivity.class));
                finish();
            }
        });

        tanggalPengadu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditOrder.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        tanggalPengadu.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(namaPengadu);
                checkField(tanggalPengadu);
                checkField(Divisi);
                checkField(NamaAset);
                checkField(Problem);
                checkField(kode);

                String Asset_Name_Order = NamaAset.getText().toString();
                String Asset_Code_Order = kode.getText().toString();
                String Date_Order = tanggalPengadu.getText().toString();
                String Divisi_Order = Divisi.getText().toString();
                String Type_Order = type.getText().toString();
                String Amount_Order  = Problem.getText().toString();

                Map<String,Object> Damage = new HashMap<>();
                Damage.put("Asset_Name_Order",Asset_Name_Order);
                Damage.put("Asset_Code_Order",Asset_Code_Order);
                Damage.put("Date_Order",Date_Order);
                Damage.put("Divisi_Order",Divisi_Order);
                Damage.put("Type_Order", Type_Order);
                Damage.put("Amount_Order", Amount_Order);
                Damage.put("orderID", orderID);

                if (valid) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.updateChildren(Damage);
                            Toast.makeText(EditOrder.this, "Order Updated...", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(EditOrder.this, OrderActivity.class));

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(EditOrder.this, "Order Updated Failed...", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

        }
    public boolean checkField(EditText textField){
        if(textField.getText().toString().isEmpty()){
            textField.setError("Column cannot be empity");
            valid = false;
        }else {
            valid = true;
        }

        return valid;
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
