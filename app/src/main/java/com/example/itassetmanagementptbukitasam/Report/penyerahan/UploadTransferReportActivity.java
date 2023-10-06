package com.example.itassetmanagementptbukitasam.Report.penyerahan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.Report.AddReport.TransferReportActivity;
import com.example.itassetmanagementptbukitasam.model.FileinModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class UploadTransferReportActivity extends AppCompatActivity {
    TextView edit;
    TextView uploadBTn;

    StorageReference storageReference;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_report_transfer);

        edit = findViewById(R.id.editText_Transfer);
        uploadBTn = findViewById(R.id.btn_upload_transfer);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("Transfer_Report");

        //set the upload btn disable first without selecting the pdf file
        uploadBTn.setEnabled(false);

        //select the pdf file
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPDF();
            }
        });

    }

    private void selectPDF() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Pdf files"),101);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri uri =data.getData();

            //we need the file name of the pdf file, so extract the name of the pdf file
            String uriString  = uri.toString();
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();
            String displayName = null;

            if (uriString.startsWith("content://")){
                Cursor cursor = null;
                try {
                    cursor = this.getContentResolver().query(uri,null,null,null,null);
                    if (cursor != null && cursor.moveToFirst()){
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } finally {
                    cursor.close();
                }
            } else  if (uriString.startsWith("file://")){
                displayName = myFile.getName();
            }

            uploadBTn.setEnabled(true);
            edit.setText(displayName);

            uploadBTn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    uploadPDF(data.getData());
                }
            });


        }

    }

    private void uploadPDF(Uri data) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Report Submission..");
        pd.show();

        final StorageReference reference = storageReference.child("Transfer Report/"+ System.currentTimeMillis() + ".pdf");
        // store in upload folder of the Firebase storage
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete());
                        Uri uri = uriTask.getResult();

                        FileinModel fileinModel = new FileinModel(edit.getText().toString(), uri.toString()); //get the views from the model class
                        databaseReference.child(databaseReference.push().getKey()).setValue(fileinModel);// push the value into the realtime database
                        Toast.makeText(UploadTransferReportActivity.this, "Report Submission Successfully!!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                        pd.setMessage("Report Submission is in progress");
                    }
                });

    }

    //lets try and upload pdf file
    //before

    public void CreateTransferReport(View view) {
        // now here we will extract those pdf files
        startActivity(new Intent(UploadTransferReportActivity.this, TransferReportActivity.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}