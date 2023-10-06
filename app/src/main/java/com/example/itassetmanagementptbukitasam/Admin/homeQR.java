package com.example.itassetmanagementptbukitasam.Admin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.itassetmanagementptbukitasam.OtherMenu.ScanQRCodeActivity;
import com.example.itassetmanagementptbukitasam.R;

public class homeQR extends AppCompatActivity {
    //vars
    public static final int CAMERA_PERMISSION_CODE = 100;

    //widgets
    private Button camera;
    private Button generate;
    private Button scanpage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_qr_code);
        camera = findViewById(R.id.camera);
        generate = findViewById(R.id.generate);
        scanpage = findViewById(R.id.scan_qr_code);


        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);
            }
        });
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homeQR.this, GenerateCode.class);
                startActivity(intent);
            }
        });
        scanpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homeQR.this, ScanQRCodeActivity.class);
                startActivity(intent);
            }
        });
    }
    public void checkPermission(String permission, int requestCode){
        if(ContextCompat.checkSelfPermission(homeQR.this, permission)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(homeQR.this, new String[] {permission},
                    requestCode);
        }
        else{
            Toast.makeText(this, "Permission Already Granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CAMERA_PERMISSION_CODE){
            if(grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}







