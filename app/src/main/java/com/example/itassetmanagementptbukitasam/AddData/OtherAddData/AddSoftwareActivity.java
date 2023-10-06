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


public class AddSoftwareActivity extends AppCompatActivity {
    EditText software, brand,purchase, exparations, product, linkImg;
    Button btn_simpan, purschaseDate, expiredDate;
    int mYear, mMonth, mDay;
    boolean valid = true;
    String softid;
    ImageView backsoft;
    private static final String TAG = "AddSoftwareActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_software);

        software = findViewById(R.id.et_software);
        brand = findViewById(R.id.et_brand);
        purchase= findViewById(R.id.et_purchase);
        exparations = findViewById(R.id.et_expri);
        product = findViewById(R.id.et_produck);
        btn_simpan = findViewById(R.id.btn_simpan);
        purschaseDate = findViewById(R.id.select_date);
        expiredDate = findViewById(R.id.expire_date);
        backsoft = findViewById(R.id.backsoft);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Software");


        backsoft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SoftwareActivity.class);
                startActivity(intent);
                finish();
            }
        });
        purschaseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddSoftwareActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        purchase.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        expiredDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddSoftwareActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        exparations.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });



        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkField(software);
                checkField(brand);
                checkField(purchase);
                checkField(exparations);
                checkField(product);

                String software_name = software.getText().toString();
                String brand_name  = brand.getText().toString();
                String product_key = product.getText().toString();
                String datepur = purchase.getText().toString();
                String expare = exparations.getText().toString();

                softid = software_name;

                SoftwareModel softwareModel = new SoftwareModel(product_key,expare, datepur,brand_name,software_name,softid);

                if (valid) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child(softid).setValue(softwareModel);
                            Toast.makeText(AddSoftwareActivity.this, "Software Added...", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddSoftwareActivity.this, SoftwareActivity.class));
                            SoftwareModel software = snapshot.getValue(SoftwareModel.class);
                            Log.d(TAG, "Create software asset successfull  " + software);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.w(TAG, "Failed to read software.", error.toException());
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
