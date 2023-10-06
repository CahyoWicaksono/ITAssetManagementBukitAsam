package com.example.itassetmanagementptbukitasam.OtherMenu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.itassetmanagementptbukitasam.Clinic.SignInClinicActivity;
import com.example.itassetmanagementptbukitasam.Humas.SignInHumasActivity;
import com.example.itassetmanagementptbukitasam.Laboratory.SignInLabActivity;
import com.example.itassetmanagementptbukitasam.Logistic.SignInLogisticActivity;
import com.example.itassetmanagementptbukitasam.Manager.SignInManagerActivity;
import com.example.itassetmanagementptbukitasam.Oprational.SignInOprationalActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.Account.LoginUserActivity;
import com.example.itassetmanagementptbukitasam.SDM.SignInComplaintActivity;
import com.example.itassetmanagementptbukitasam.Safety.SignInSafetyActivity;

import java.util.ArrayList;
import java.util.List;

public class ScondStartActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView idAdminlog;
    private TextView idUserlog;
    Spinner divisi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_log);
        divisi= findViewById(R.id.spinner_login);


        divisi.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add(0,"Select Type Access");
        categories.add("IT Support");
        categories.add("Officer");
        categories.add("Work Unit");
        categories.add("Manager");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        divisi.setAdapter(dataAdapter);


    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getItemAtPosition(position).equals("Select Type Access")){

        }else {
            String item = parent.getItemAtPosition(position).toString();
            Toast.makeText(parent.getContext(), "Selected" + item, Toast.LENGTH_SHORT).show();

            if (parent.getItemAtPosition(position).equals("IT Support")) {
                Intent intent = new Intent(ScondStartActivity.this, SignInActivity.class);
                startActivity(intent);

            } else if (parent.getItemAtPosition(position).equals("Officer")) {
                Intent intent = new Intent(ScondStartActivity.this, LoginUserActivity.class);
                startActivity(intent);

            } else if (parent.getItemAtPosition(position).equals("Work Unit")) {
                Intent intent = new Intent(ScondStartActivity.this, SignInComplaintActivity.class);
                startActivity(intent);

            }else  {
                Intent intent = new Intent(ScondStartActivity.this, SignInManagerActivity.class);
                startActivity(intent);
            }
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
