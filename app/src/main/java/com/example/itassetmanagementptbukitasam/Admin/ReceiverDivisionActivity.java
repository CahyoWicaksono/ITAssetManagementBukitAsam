package com.example.itassetmanagementptbukitasam.Admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.itassetmanagementptbukitasam.Account.ChangePasswordActivity;
import com.example.itassetmanagementptbukitasam.Account.UpdateEmailActivity;
import com.example.itassetmanagementptbukitasam.ListData.Receiver.ListReceiver.ViewReceiverClnicActivity;
import com.example.itassetmanagementptbukitasam.ListData.Receiver.ListReceiver.ViewReceiverHumasActivity;
import com.example.itassetmanagementptbukitasam.ListData.Receiver.ListReceiver.ViewReceiverOprationalActivity;
import com.example.itassetmanagementptbukitasam.ListData.Receiver.ListReceiver.ViewReceiverSDMActivity;
import com.example.itassetmanagementptbukitasam.Logistic.DeleteAcountLogistic;
import com.example.itassetmanagementptbukitasam.Logistic.HomeLogisticActivity;
import com.example.itassetmanagementptbukitasam.Logistic.ProfileLogisticActivity;
import com.example.itassetmanagementptbukitasam.Logistic.SignInLogisticActivity;
import com.example.itassetmanagementptbukitasam.Logistic.UpdateProfileLogisticActivity;
import com.example.itassetmanagementptbukitasam.OtherMenu.Capture;
import com.example.itassetmanagementptbukitasam.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ReceiverDivisionActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private FirebaseAuth auth;
    private CardView cardViewIT, cardViewHR, cardViewHumas,cardViewSafety,cardViewOprational,cardViewClinic, cardViewLab;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_division_receiver);


        cardViewHR = findViewById(R.id.asset_receiver_hrd);
        cardViewHumas = findViewById(R.id.asset_receiver_humas);
        cardViewSafety = findViewById(R.id.asset_receiver_safety);
        cardViewOprational = findViewById(R.id.asset_receiver_oprational);
        cardViewClinic = findViewById(R.id.asset_receiver_clinic);
        cardViewLab = findViewById(R.id.asset_receiver_labolatorium);


        auth = FirebaseAuth.getInstance();



        cardViewHR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ViewReceiverSDMActivity.class));
                overridePendingTransition(0, 0);
            }
        });
        cardViewHumas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ViewReceiverHumasActivity.class));
                overridePendingTransition(0, 0);
            }
        });
        cardViewSafety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ViewReceiverHumasActivity.class));
                overridePendingTransition(0, 0);
            }
        });
        cardViewOprational.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ViewReceiverOprationalActivity.class));
                overridePendingTransition(0, 0);
            }
        });
        cardViewClinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ViewReceiverClnicActivity.class));
                overridePendingTransition(0, 0);
            }
        });
        cardViewLab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ViewReceiverClnicActivity.class));
                overridePendingTransition(0, 0);
            }
        });



        bottomNavigationView = findViewById(R.id.botomNavigation_asset_receiver);

        bottomNavigationView.setSelectedItemId(R.id.id_home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.id_home:
                        startActivity(new Intent(getApplicationContext(), HomeLogisticActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.id_profile:
                        startActivity(new Intent(getApplicationContext(), ProfileLogisticActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.id_code:
                        IntentIntegrator intentIntegrator = new IntentIntegrator(ReceiverDivisionActivity.this);
                        intentIntegrator.setPrompt("For flash use volume key");
                        intentIntegrator.setBeepEnabled(true);
                        intentIntegrator.setOrientationLocked(true);
                        intentIntegrator.setCaptureActivity(Capture.class);
                        intentIntegrator.initiateScan();
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if ((intentResult.getContents() != null)){
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(
                    ReceiverDivisionActivity.this);
            builder.setTitle("Information");
            builder.setMessage(intentResult.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        }else {
            Toast.makeText(ReceiverDivisionActivity.this, "Scan Failed", Toast.LENGTH_SHORT).show();
        }

    }
}
