package com.example.itassetmanagementptbukitasam.Adapter.OtherAdapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.RepairModel;

import java.util.ArrayList;


public class RepairUserAdapter extends RecyclerView.Adapter<RepairUserAdapter.ViewHolder> {
    int lastpos = -1;
    private ArrayList<RepairModel> repairModelArrayList;
    private Context context;
    private RepairUserAdapter.RepairUserClickInterface repairUserClickInterface;

    public RepairUserAdapter(ArrayList<RepairModel> repairModelArrayList, Context context, RepairUserClickInterface repairUserClickInterface) {
        this.repairModelArrayList = repairModelArrayList;
        this.context = context;
        this.repairUserClickInterface = repairUserClickInterface;
    }

    @NonNull
    @Override
    public RepairUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_repair_user,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder repairholder, int position) {
        RepairModel repairModel = repairModelArrayList.get(position);
        repairholder.repaircode.setText(repairModel.getKode_perbaikan());
        repairholder.assetcode.setText(repairModel.getKode_aset());
        repairholder.officer.setText(repairModel.getPetugas());



        setAnimation(repairholder.itemView,position);
        repairholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {repairUserClickInterface.onRepairUserClick(position);
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
        return repairModelArrayList.size();
    }
    public interface RepairUserClickInterface { void onRepairUserClick(int position);}

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView repaircode, assetcode, officer,location;
        private ImageView repairimage;

        public ViewHolder(@NonNull View itemView) {super(itemView);
            repaircode = itemView.findViewById(R.id.tv_kode_Repair_user);
            assetcode = itemView.findViewById(R.id.tv_kode_aset_rep_user);
            officer = itemView.findViewById(R.id.tv_petugas_Repair_user);

        }
    }


}
