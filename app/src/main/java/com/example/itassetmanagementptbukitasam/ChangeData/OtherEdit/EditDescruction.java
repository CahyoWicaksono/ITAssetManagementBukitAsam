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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.itassetmanagementptbukitasam.ListData.AssetData.AssetDataActivity;
import com.example.itassetmanagementptbukitasam.ListData.OtherData.DesctructionActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.PemusnahanModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditDescruction extends AppCompatActivity {
    private EditText nameDestruction, dateDestruction, typeDestruction, jumlahDestruction,link, descDestruction, kodeassetDestruction;
    Button btn_update_Destruction;
    boolean valid = true;
    ImageView backasset, backas;
    PemusnahanModel pemusnahanModel;
    FirebaseDatabase firebaseDatabase;
    int mYear, mMonth, mDay;
    DatabaseReference databaseReference;
    private RadioGroup radioGroupCategoryDestruction;
    private RadioButton radioButtonCategoryDestruction;
    String DestructionID;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_pemusnahan);

        nameDestruction = findViewById(R.id.et_name_Destruction_update);
        kodeassetDestruction = findViewById(R.id.et_kode_aset_Destruction_update);
        typeDestruction = findViewById(R.id.et_type_Destruction_update);
        radioGroupCategoryDestruction = findViewById(R.id.radio_group_Destruction_update);
        jumlahDestruction = findViewById(R.id.et_amount_Destruction_update);
        descDestruction = findViewById(R.id.et_desc_Destruction_update);
        dateDestruction = findViewById(R.id.et_datetime_Destruction_update);
        link = findViewById(R.id.et_link_image_Destruction_update);


        backas = findViewById(R.id.back_Destruction_update);
        btn_update_Destruction = findViewById(R.id.btn_update_Destruction);



        firebaseDatabase = FirebaseDatabase.getInstance();
        pemusnahanModel  = getIntent().getParcelableExtra("destruction");

        if(pemusnahanModel!=null){
            nameDestruction.setText(pemusnahanModel.getNamaAsetPem());
            kodeassetDestruction.setText(pemusnahanModel.getKodeAssetPem());
            typeDestruction.setText(pemusnahanModel.getTypeAsetPem());
            radioButtonCategoryDestruction.setText(pemusnahanModel.getkategoriPem());
            jumlahDestruction.setText(pemusnahanModel.getJumlahPem());
            descDestruction.setText(pemusnahanModel.getKeteranganPem());
            dateDestruction.setText(pemusnahanModel.getTanggalPem());
            link.setText(pemusnahanModel.getLinkImageDestruction());

            DestructionID = pemusnahanModel.getPemusnahanID();

        }
        databaseReference = firebaseDatabase.getReference("Destruction").child(DestructionID);


        backas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AssetDataActivity.class));
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditDescruction.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        dateDestruction.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });



        btn_update_Destruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(nameDestruction);
                checkField(kodeassetDestruction);
                checkField(typeDestruction);
                checkField(jumlahDestruction);
                checkField(descDestruction);
                checkField(dateDestruction);
                checkField(link);

                int selectedStatusID = radioGroupCategoryDestruction.getCheckedRadioButtonId();
                radioButtonCategoryDestruction = findViewById(selectedStatusID);

                String Name_Asset_Destruction = nameDestruction.getText().toString();
                String Asset_Code_Destruction = kodeassetDestruction.getText().toString();
                String Type_Asset_Destruction = typeDestruction.getText().toString();
                String Categories_Destruction = radioButtonCategoryDestruction.getText().toString();
                String Amount_Destruction = jumlahDestruction.getText().toString();
                String Description_Destruction = descDestruction.getText().toString();
                String Date_Destruction = dateDestruction.getText().toString();
                String Link_Image = link.getText().toString();



                Map<String,Object> Asset = new HashMap<>();
                Asset.put("Name_Asset_Destruction",Name_Asset_Destruction);
                Asset.put("Asset_Code_Destruction",Asset_Code_Destruction);
                Asset.put("Type_Asset_Destruction",Type_Asset_Destruction);
                Asset.put("Categories_Destruction",Categories_Destruction);
                Asset.put("Amount_Destruction",Amount_Destruction);
                Asset.put("Description_Destruction",Description_Destruction);
                Asset.put("Date_Destruction",Date_Destruction);
                Asset.put("Link_Image", Link_Image);
                Asset.put("DestructionID", DestructionID);

                if(radioButtonCategoryDestruction.equals("Fixed")) {
                    radioButtonCategoryDestruction = findViewById(R.id.radio_proses);
                    radioButtonCategoryDestruction.setChecked(true);
                } else {
                    radioButtonCategoryDestruction = findViewById(R.id.radio_noyet);
                }
                radioButtonCategoryDestruction.setChecked(true);




                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.updateChildren(Asset);
                        Toast.makeText(EditDescruction.this, "Data Updated...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditDescruction.this, DesctructionActivity.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditDescruction.this, "Update Data Failed", Toast.LENGTH_SHORT).show();
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
