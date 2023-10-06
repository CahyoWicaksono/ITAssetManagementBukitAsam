package com.example.itassetmanagementptbukitasam.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.itassetmanagementptbukitasam.ListData.Request.ListRequest.RequestActivity;
import com.example.itassetmanagementptbukitasam.ListData.Response.List.ResponseSDMActivity;
import com.example.itassetmanagementptbukitasam.R;

public class RequestTakingActivity extends AppCompatActivity {

    TextView take,view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        take = findViewById(R.id.take_asset);
        view = findViewById(R.id.view_request);


        take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RequestTakingActivity.this, RequestActivity.class));
                finish();
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RequestTakingActivity.this, RequestDivisionActivity.class));
                finish();

            }
        });

    }
}
