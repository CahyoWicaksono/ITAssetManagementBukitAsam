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
import com.example.itassetmanagementptbukitasam.model.WorkOrderModel;

import java.util.ArrayList;

public class WorkOrderAdapter extends RecyclerView.Adapter<WorkOrderAdapter.ViewHolder> {
    int lastpos = -1;
    private ArrayList<WorkOrderModel> workOrderModelArrayList;
    private Context context;
    private WorkOrderAdapter.WorkClickInterface workClickInterface;

    public WorkOrderAdapter(ArrayList<WorkOrderModel> workOrderModelArrayList, Context context, WorkClickInterface workClickInterface) {
        this.workOrderModelArrayList = workOrderModelArrayList;
        this.context = context;
        this.workClickInterface = workClickInterface;
    }



    @NonNull
    @Override
    public WorkOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_work,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkOrderAdapter.ViewHolder WorkHolder, int position) {
        WorkOrderModel workOrderModel = workOrderModelArrayList.get(position);

        WorkHolder.officer.setText(workOrderModel.getNamaPetugas());
        WorkHolder.date.setText(workOrderModel.getTanggalWork());
        WorkHolder.time.setText(workOrderModel.getWaktuWork());



        setAnimation(WorkHolder.itemView,position);
        WorkHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workClickInterface.onWorkClick(position);
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
    public int getItemCount() {return workOrderModelArrayList.size();}
    public interface WorkClickInterface {
        void onWorkClick(int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView officer, date, time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            officer = itemView.findViewById(R.id.tv_officer_work);
            date = itemView.findViewById(R.id.tv_date_work);
            time = itemView.findViewById(R.id.tv_time_work);


        }
    }
}
