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

import com.example.itassetmanagementptbukitasam.ListData.OtherData.SoftwareActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.SoftwareModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditSoftware extends AppCompatActivity {
    private EditText software_name,brand_name,datepur,expare,software_img,product_key;
    private Button btn_update_soft, btn_puschase,btn_expi;
    private boolean valid = true;
    private String softid;
    private ImageView backsoft;
    private int mYear, mMonth, mDay;
    private SoftwareModel softwareModel;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_software);

        software_name = findViewById(R.id.et_software_up);
        brand_name = findViewById(R.id.et_brand_up);
        datepur = findViewById(R.id.et_purchase_up);
        expare = findViewById(R.id.et_expri_up);
        product_key = findViewById(R.id.et_produck_up);

        btn_update_soft = findViewById(R.id.btn_updatesoft);
        btn_puschase = findViewById(R.id.select_date_up);
        btn_expi = findViewById(R.id.expire_date_up);

        firebaseDatabase =FirebaseDatabase.getInstance();
        softwareModel = getIntent().getParcelableExtra("Software model");

        if(softwareModel!=null){
            software_name.setText(softwareModel.getSoftware_name());
            brand_name.setText(softwareModel.getBrand_name());
            product_key.setText(softwareModel.getProduct_key());
            datepur.setText(softwareModel.getDate_purchase());
            expare.setText(softwareModel.getExparation());
            softid = softwareModel.getSoftwareid();
        }
        databaseReference = firebaseDatabase.getReference("Software").child(softid);

        backsoft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SoftwareActivity.class));
                finish();
            }
        });


        btn_puschase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditSoftware.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        datepur.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        btn_expi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditSoftware.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        expare.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        btn_update_soft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(software_name);
                checkField(brand_name);
                checkField(datepur);
                checkField(expare);
                checkField(product_key);
                checkField(software_name);

                String soft_name = software_name.getText().toString();
                String name  = brand_name.getText().toString();
                String product = product_key.getText().toString();
                String purchase = datepur.getText().toString();
                String expire = expare.getText().toString();

                Map<String,Object> software = new HashMap<>();
                software.put("software_name",soft_name);
                software.put("brand_name",name);
                software.put("product_key", product);
                software.put("date_purchase",purchase);
                software.put("exparation",expire);
                software.put("softid", softid);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.updateChildren(software);
                        Toast.makeText(EditSoftware.this, "Software Updated...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditSoftware.this, SoftwareActivity.class));


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditSoftware.this, "Software Updated Failed...", Toast.LENGTH_SHORT).show();

                    }
                });

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
