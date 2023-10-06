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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.itassetmanagementptbukitasam.ListData.OtherData.DesctructionActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.PemusnahanModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class AddDestruction extends AppCompatActivity {

    private EditText nameDestruction, dateDestruction, typeDestruction, jumlahDestruction,link, descDestruction, kodeassetDestruction;
    private Button btn_save;
    boolean valid = true;
    int mYear, mMonth, mDay;
    private String DestructionID;
    private ImageView backcom;
    private static final String TAG = "AddDestruction";
    private RadioGroup radioGroupStatusDestruction;
    private RadioButton radioButtonCategoryDestruction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_pemusnahan);

        nameDestruction = findViewById(R.id.et_name_Destruction);
        kodeassetDestruction = findViewById(R.id.et_kode_aset_Destruction);
        typeDestruction = findViewById(R.id.et_type_Destruction);
        radioGroupStatusDestruction = findViewById(R.id.radio_group_Destruction);
        jumlahDestruction = findViewById(R.id.et_amount_Destruction);
        descDestruction = findViewById(R.id.et_desc_Destruction);
        dateDestruction = findViewById(R.id.et_datetime_Destruction);
        link = findViewById(R.id.et_link_image_Destruction);


        btn_save = findViewById(R.id.btn_simpan_Destruction);
        backcom = findViewById(R.id.back_Destruction);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Destruction");


        backcom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DesctructionActivity.class));
                finish();
            }
        });

        dateDestruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddDestruction.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        dateDestruction.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkField(nameDestruction);
                checkField(kodeassetDestruction);
                checkField(typeDestruction);
                checkField(jumlahDestruction);
                checkField(descDestruction);
                checkField(dateDestruction);
                checkField(link);

                int selectedStatusID = radioGroupStatusDestruction.getCheckedRadioButtonId();
                radioButtonCategoryDestruction = findViewById(selectedStatusID);

                String Name_Asset_Destruction = nameDestruction.getText().toString();
                String Asset_Code_Destruction = kodeassetDestruction.getText().toString();
                String Type_Asset_Destruction = typeDestruction.getText().toString();
                String Categories_Destruction = radioButtonCategoryDestruction.getText().toString();
                String Amount_Destruction = jumlahDestruction.getText().toString();
                String Description_Destruction = descDestruction.getText().toString();
                String Date_Destruction = dateDestruction.getText().toString();
                String Link_Image = link.getText().toString();

                DestructionID = Name_Asset_Destruction;

                PemusnahanModel pemusnahanModel = new PemusnahanModel(
                        DestructionID, Name_Asset_Destruction,Asset_Code_Destruction,Type_Asset_Destruction,Amount_Destruction,Description_Destruction,
                        Date_Destruction, Link_Image, Categories_Destruction);

                if (valid) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child(DestructionID).setValue(pemusnahanModel);
                            startActivity(new Intent(AddDestruction.this, DesctructionActivity.class));
                            PemusnahanModel pemusnahanModel = snapshot.getValue(PemusnahanModel.class);
                            Log.d(TAG, "Create data successfull  " + pemusnahanModel);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.w(TAG, "Failed to read data.", error.toException());

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
