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
import com.example.itassetmanagementptbukitasam.model.MaintananceModel;

import java.util.ArrayList;


public class MainAdapterUser extends RecyclerView.Adapter<MainAdapterUser.ViewHolder> {
    int lastpos = -1;
    private ArrayList<MaintananceModel> maintananceModelArrayList;
    private Context context;
    private MaintenanceUserClickInterface maintenanceUserClickInterface;

    public MainAdapterUser(ArrayList<MaintananceModel> maintananceModelArrayList, Context context, MaintenanceUserClickInterface maintenanceUserClickInterface) {
        this.maintananceModelArrayList = maintananceModelArrayList;
        this.context = context;
        this.maintenanceUserClickInterface = maintenanceUserClickInterface;
    }




    @NonNull
    @Override
    public MainAdapterUser.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_maintenance_user,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapterUser.ViewHolder Maintenanceholder, int position) {
        MaintananceModel maintananceModel = maintananceModelArrayList.get(position);
        Maintenanceholder.maintenance.setText(maintananceModel.getKode_mintanance());
        Maintenanceholder.asset.setText(maintananceModel.getKode_aset());
        Maintenanceholder.officer.setText(maintananceModel.getPetugas());


        setAnimation(Maintenanceholder.itemView,position);
        Maintenanceholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maintenanceUserClickInterface.onMaintenanceClick(position);
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
        return maintananceModelArrayList.size();
    }
    public interface MaintenanceUserClickInterface{
        void onMaintenanceClick(int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView  maintenance, asset, officer;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            maintenance = itemView.findViewById(R.id.tv_maintenance_code_user);
            asset = itemView.findViewById(R.id.tv_asset_main_user);
            officer = itemView.findViewById(R.id.tv_officer_user);

        }
    }
}
