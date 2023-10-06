package com.example.itassetmanagementptbukitasam.Adapter.OtherAdapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.ComplaintModel;

import java.util.ArrayList;


public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ViewHolder>{
    int lastpos = -1;
    private ArrayList<ComplaintModel> complaintModelArrayList;
    private Context context;
    private ComplaintAdapter.ViewDataComplaintClickInterface viewDataComplaintClickInterface;

    public ComplaintAdapter(ArrayList<ComplaintModel>complaintModelArrayList, Context context, ViewDataComplaintClickInterface viewDataComplaintClickInterface) {
        this.complaintModelArrayList = complaintModelArrayList;
        this.context = context;
        this.viewDataComplaintClickInterface = viewDataComplaintClickInterface;
    }


    @NonNull
    @Override
    public ComplaintAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_complain,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComplaintAdapter.ViewHolder assetholder, int position) {
        ComplaintModel complaintModel = complaintModelArrayList.get(position);
        assetholder.nama_pengadu.setText(complaintModel.getComplaintName());
        assetholder.date.setText(complaintModel.getDateComplaint());
        assetholder.divisi.setText(complaintModel.getComplaintDivision());
        setAnimation(assetholder.itemView,position);
        assetholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewDataComplaintClickInterface.onViewDataComplaintClick(position);
            }
        });

    }
    private void setAnimation(View itemView,int position){
        if (position > lastpos){
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastpos = position;
        }

    }

    @Override
    public int getItemCount() {
        return complaintModelArrayList.size();
    }
    public interface ViewDataComplaintClickInterface {
        void onViewDataComplaintClick(int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView nama_pengadu, date,divisi;


        public ViewHolder(@NonNull View itemView) {super(itemView);
            nama_pengadu =  itemView.findViewById(R.id.tv_Complain_Name);
            date =itemView.findViewById(R.id.tv_date_complaint);
            divisi = itemView.findViewById(R.id.tv_division_complain);
        }
    }

}
