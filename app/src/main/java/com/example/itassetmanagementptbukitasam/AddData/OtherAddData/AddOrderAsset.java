package com.example.itassetmanagementptbukitasam.AddData.OtherAddData;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.itassetmanagementptbukitasam.Logistic.HomeLogisticActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.ComplaintModel;
import com.example.itassetmanagementptbukitasam.model.OrderModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;


public class AddOrderAsset extends AppCompatActivity {
    Toolbar toolbar;
    int mYear, mMonth, mDay;
    boolean valid = true;
    EditText namaPengadu, tanggalPengadu,Divisi, NamaAset,Problem, kode, type;
    ImageView backmaintenance;
    Button btn_simpan, maintenanceDate;
    String orderID;
    private static final String TAG = "AddComAddOrderAsset";


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_order_aset);

        tanggalPengadu = findViewById(R.id.et_date_order);
        Divisi = findViewById(R.id.et_division_order);
        NamaAset = findViewById(R.id.et_name_asset_order);
        Problem = findViewById(R.id.et_amount_order);
        kode = findViewById(R.id.et_kode_aset_order);
        type = findViewById(R.id.et_type_asset_order);


        backmaintenance = findViewById(R.id.back_order);

        btn_simpan = findViewById(R.id.btn_save_order);



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Order");



        backmaintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeLogisticActivity.class);
                startActivity(intent);
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddOrderAsset.this, new DatePickerDialog.OnDateSetListener() {
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
            public void onClick(View v) {

                checkField(type);
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

                orderID = Asset_Name_Order;

                OrderModel orderModel = new OrderModel(
                   orderID, Asset_Name_Order,Asset_Code_Order,Date_Order,Divisi_Order,Type_Order,Amount_Order);

                if (valid) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child(orderID).setValue(orderModel);
                            startActivity(new Intent(AddOrderAsset.this, HomeLogisticActivity.class));
                            OrderModel orderModel = snapshot.getValue(OrderModel.class);
                            Log.d(TAG, "Order asset successfull  " + orderModel);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.w(TAG, "Failed to read .", error.toException());

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




