package com.example.itassetmanagementptbukitasam.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.itassetmanagementptbukitasam.ListData.Receiver.ListReceiver.ReceiverActivity;
import com.example.itassetmanagementptbukitasam.ListData.Receiver.ListReceiver.ViewReceiverSDMActivity;
import com.example.itassetmanagementptbukitasam.R;

public class ReceiverAssetActivity extends AppCompatActivity {

    TextView receiver,receiver_view;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);

        receiver = findViewById(R.id.receiver_asset);
        receiver_view = findViewById(R.id.view_receiver);


        receiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ReceiverActivity.class));
                overridePendingTransition(0, 0);
            }
        });
        receiver_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ReceiverDivisionActivity.class));
                overridePendingTransition(0, 0);
            }
        });



    }
}
