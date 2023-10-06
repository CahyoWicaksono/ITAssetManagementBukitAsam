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

import com.example.itassetmanagementptbukitasam.ListData.OtherData.MaintenanceActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.MaintananceModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditMaintenance extends AppCompatActivity {
    EditText kode_maintanance, kode_aset, petugas, kondisi_peralatan, lokasi, status, tanggal,first_up,finallu_up;
    Button btn_update_maintenance, date_main, btn_first_up, btn_finally_up;
    boolean valid = true;
    ImageView dokument, backmnt;
    int mYear, mMonth, mDay,t1Hour, t1Minute;
    String mainId;
    MaintananceModel maintananceModel;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_maintanance);

        kode_maintanance = findViewById(R.id.et_kode_main_up);
        kode_aset = findViewById(R.id.et_kode_aset_maintenance_up);
        petugas = findViewById(R.id.et_petugas_up);
        kondisi_peralatan = findViewById(R.id.et_kondisi_up);
        lokasi = findViewById(R.id.et_lokasi_up);
        status = findViewById(R.id.et_status_up);
        tanggal = findViewById(R.id.et_datetime_up);


        backmnt = findViewById(R.id.backmnt_up);

        btn_update_maintenance = findViewById(R.id.btn_updatemnt);
        date_main = findViewById(R.id.maintenance_date_up);

        firebaseDatabase =FirebaseDatabase.getInstance();
        maintananceModel = getIntent().getParcelableExtra("maintenance");

        if(maintananceModel!=null){
            kode_maintanance.setText(maintananceModel.getKode_mintanance());
            kode_aset.setText(maintananceModel.getKode_aset());
            petugas.setText(maintananceModel.getPetugas());
            kondisi_peralatan.setText(maintananceModel.getKondisi_peralatan());
            lokasi.setText(maintananceModel.getLokasi());
            status.setText(maintananceModel.getStatus());
            tanggal.setText(maintananceModel.getTanggal());

            mainId = maintananceModel.getMainID();

        }
        databaseReference = firebaseDatabase.getReference("Maintenance").child(mainId);

        backmnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MaintenanceActivity.class));
                finish();
            }
        });

        date_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditMaintenance.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        tanggal.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
//
//

        btn_update_maintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkField(kode_maintanance);
                checkField(kode_aset);
                checkField(tanggal);
                checkField(status);
                checkField(petugas);
                checkField(lokasi);
                checkField(kondisi_peralatan);


                String Maintenance_Code = kode_maintanance.getText().toString();
                String Asset_code = kode_aset.getText().toString();
                String Officer = petugas.getText().toString();
                String Condition = kondisi_peralatan.getText().toString();
                String Location = lokasi.getText().toString();
                String Status_maintenance = status.getText().toString();
                String Date_maintenance = tanggal.getText().toString();

                Map<String,Object> Maintenance = new HashMap<>();

                Maintenance.put("Maintenance_Code",Maintenance_Code);
                Maintenance.put("Asset_code",Asset_code);
                Maintenance.put("Officer", Officer);
                Maintenance.put("Condition", Condition);
                Maintenance.put("Location",Location);
                Maintenance.put("Status_maintenance",Status_maintenance);
                Maintenance.put("Date_maintenance",Date_maintenance);
                Maintenance.put("mainId",mainId);



                if (valid) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.updateChildren(Maintenance);
                            Toast.makeText(EditMaintenance.this, "Maintenance Updated...", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(EditMaintenance.this, MaintenanceActivity.class));

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(EditMaintenance.this, "Maintenance Updated Failed...", Toast.LENGTH_SHORT).show();

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
