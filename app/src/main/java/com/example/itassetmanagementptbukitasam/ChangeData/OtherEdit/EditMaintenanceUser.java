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

import com.example.itassetmanagementptbukitasam.ListDataUser.MaintenanceUserActivity;
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

public class EditMaintenanceUser extends AppCompatActivity {
    EditText kode_maintanance_user, kode_aset_user, petugas_user, kondisi_peralatan_user, lokasi_user, status_user, tanggal_user,first_up,finallu_up;
    Button btn_update_maintenance_user, date_main_user, btn_first_up, btn_finally_up;
    boolean valid = true;
    ImageView dokument, backmnt_user;
    int mYear, mMonth, mDay,t1Hour, t1Minute;
    String mainId;
    MaintananceModel maintananceModel;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_maintanance_user);

        kode_maintanance_user = findViewById(R.id.et_kode_main_up_user);
        kode_aset_user = findViewById(R.id.et_kode_aset_up_user);
        petugas_user = findViewById(R.id.et_petugas_up_user);
        kondisi_peralatan_user = findViewById(R.id.et_kondisi_up_user);
        lokasi_user = findViewById(R.id.et_lokasi_up_user);
        status_user = findViewById(R.id.et_status_up_user);
        tanggal_user = findViewById(R.id.et_datetime_up_user);


        backmnt_user = findViewById(R.id.backmnt_up_user);

        btn_update_maintenance_user = findViewById(R.id.btn_updatemnt_user);
        date_main_user = findViewById(R.id.maintenance_date_up_user);

        firebaseDatabase =FirebaseDatabase.getInstance();
        maintananceModel = getIntent().getParcelableExtra("maintenance");

        if(maintananceModel!=null){
            kode_maintanance_user.setText(maintananceModel.getKode_mintanance());
            kode_aset_user.setText(maintananceModel.getKode_aset());
            petugas_user.setText(maintananceModel.getPetugas());
            kondisi_peralatan_user.setText(maintananceModel.getKondisi_peralatan());
            lokasi_user.setText(maintananceModel.getLokasi());
            status_user.setText(maintananceModel.getStatus());
            tanggal_user.setText(maintananceModel.getTanggal());

            mainId = maintananceModel.getMainID();


        }
        databaseReference = firebaseDatabase.getReference("Maintenance").child(mainId);

        backmnt_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MaintenanceUserActivity.class));
                finish();
            }
        });

        date_main_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditMaintenanceUser.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        tanggal_user.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
//
//

        btn_update_maintenance_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkField(kode_maintanance_user);
                checkField(kode_aset_user);
                checkField(tanggal_user);
                checkField(status_user);
                checkField(petugas_user);
                checkField(lokasi_user);
                checkField(kondisi_peralatan_user);


                String Maintenance_Code = kode_maintanance_user.getText().toString();
                String Asset_code = kode_aset_user.getText().toString();
                String Officer = petugas_user.getText().toString();
                String Condition = kondisi_peralatan_user.getText().toString();
                String Location = lokasi_user.getText().toString();
                String Status_maintenance = status_user.getText().toString();
                String Date_maintenance = tanggal_user.getText().toString();

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
                            Toast.makeText(EditMaintenanceUser.this, "Maintenance Updated...", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(EditMaintenanceUser.this, MaintenanceUserActivity.class));

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(EditMaintenanceUser.this, "Maintenance Updated Failed...", Toast.LENGTH_SHORT).show();

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
