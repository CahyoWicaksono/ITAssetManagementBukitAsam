package com.example.itassetmanagementptbukitasam.Report.ReportAdapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.Report.ViewReport.ViewReportAsset;
import com.example.itassetmanagementptbukitasam.model.FileinModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AssetReceiverReportAdapter extends FirebaseRecyclerAdapter<FileinModel, AssetReceiverReportAdapter.myviewholder> {

    public AssetReceiverReportAdapter(@NonNull FirebaseRecyclerOptions<FileinModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull FileinModel model) {
        holder.text1.setText(model.getFilename());
        holder.text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.text1.getContext(), ViewReportAsset.class);
                intent.putExtra("filename",model.getFilename());
                intent.putExtra("fileurl",model.getFileurl());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.text1.getContext().startActivity(intent);

            }
        });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_item_receiver,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder{

        TextView text1;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.pdf_name_receiver);
        }
    }
}
