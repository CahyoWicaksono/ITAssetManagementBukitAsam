package com.example.itassetmanagementptbukitasam.Safety;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.itassetmanagementptbukitasam.Admin.ProfileAdminActivity;
import com.example.itassetmanagementptbukitasam.OtherMenu.SignInActivity;
import com.example.itassetmanagementptbukitasam.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteAcountSafety extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private EditText editTextUserPwd;
    private TextView textViewAutenticate;
    private ProgressBar progressBar;
    private String userPwd;
    private Button buttonReAutenticate, buttonDeleteUser;
    private ImageView backDel;
    private static final String TAG = "DeleteAcountActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_k3l_account);


        progressBar = findViewById(R.id.progressBar);
        editTextUserPwd = findViewById(R.id.editText_delete_user_pwd_safety);
        textViewAutenticate = findViewById(R.id.textView_delete_user_authenticated);
        buttonDeleteUser = findViewById(R.id.button_delete_user_safety);
        buttonReAutenticate = findViewById(R.id.button_delete_admin_authenticate_safety);

        backDel = findViewById(R.id.back_delete_account_admin_safety);

        buttonDeleteUser.setEnabled(false);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        if (firebaseUser.equals("")){
            Toast.makeText(DeleteAcountSafety.this, "Something went wrong !" +
                    "User details are not available at the moment", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DeleteAcountSafety.this, ProfileK3LActivity.class);
            startActivity(intent);
            finish();
        }else {
           reAutenticated(firebaseUser);

        }

        backDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeleteAcountSafety.this, HomeSafety.class);
                startActivity(intent);
                finish();

            }
        });

    }

    private void reAutenticated(FirebaseUser firebaseUser) {
        Button verifyUser = findViewById(R.id.button_delete_admin_authenticate_safety);
        verifyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userPwd = editTextUserPwd.getText().toString();

                if (TextUtils.isEmpty(userPwd)){
                    Toast.makeText(DeleteAcountSafety.this, "Password is needed to continue", Toast.LENGTH_SHORT).show();
                    editTextUserPwd.setError("Please enter yout password for authentication");
                    editTextUserPwd.requestFocus();
                }else {
                    progressBar.setVisibility(View.VISIBLE);

                    AuthCredential authCredential = EmailAuthProvider.getCredential(firebaseUser.getEmail(), userPwd);
                    firebaseUser.reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(DeleteAcountSafety.this, "Password has been verified" +
                                        "You can delete your account now. be careful, this action is irrevisible.", Toast.LENGTH_SHORT).show();

                                textViewAutenticate.setText("Password has been verified" +
                                        "You can delete account now.");


                                editTextUserPwd.setEnabled(false);
                                buttonReAutenticate.setEnabled(false);
                                buttonDeleteUser.setEnabled(true);


                                buttonDeleteUser.setBackgroundTintList(ContextCompat.getColorStateList(DeleteAcountSafety.this,
                                        R.color.red));

                                buttonDeleteUser.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        showAlertDialog();

                                    }
                                });

                            }else {
                                try {
                                    throw task.getException();
                                }catch (Exception e){
                                    Toast.makeText(DeleteAcountSafety.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                    });
                }

            }
        });
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DeleteAcountSafety.this);
        builder.setTitle("Delete account and related data?");
        builder.setMessage("Do you realy want to delete your account and related data? This action is irreversible!");

        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                deleteAccount(firebaseUser);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(DeleteAcountSafety.this, HomeSafety.class);
                startActivity(intent);
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.red));
            }
        });

        alertDialog.show();
    }

    private void deleteAccount(FirebaseUser firebaseUser) {
        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    deleteUserData();
                    auth.signOut();
                    Toast.makeText(DeleteAcountSafety.this, "User has been deleted!!",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DeleteAcountSafety.this, SignInSafetyActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    try {
                        throw task.getException();
                    }catch (Exception e){
                        Toast.makeText(DeleteAcountSafety.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    private void deleteUserData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Register_Safety");
        databaseReference.child(firebaseUser.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "OnSuccess : User Data Deleted");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, e.getMessage());
                Toast.makeText(DeleteAcountSafety.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    @Override
    public void onBackPressed() {
        new android.app.AlertDialog.Builder(this)
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
