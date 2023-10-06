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


public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    int lastpos = -1;
    private ArrayList<MaintananceModel> maintananceModelArrayList;
    private Context context;
    private MaintenanceClickInterface maintenanceClickInterface;

    public MainAdapter(ArrayList<MaintananceModel> maintananceModelArrayList, Context context, MaintenanceClickInterface maintenanceClickInterface) {
        this.maintananceModelArrayList = maintananceModelArrayList;
        this.context = context;
        this.maintenanceClickInterface = maintenanceClickInterface;
    }


    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_maintenance,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder Maintenanceholder, int position) {
        MaintananceModel maintananceModel = maintananceModelArrayList.get(position);
        Maintenanceholder.maintenance.setText(maintananceModel.getKode_mintanance());
        Maintenanceholder.asset.setText(maintananceModel.getKode_aset());
        Maintenanceholder.officer.setText(maintananceModel.getPetugas());


        setAnimation(Maintenanceholder.itemView,position);
        Maintenanceholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maintenanceClickInterface.onMaintenanceClick(position);
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
    public interface MaintenanceClickInterface{
        void onMaintenanceClick(int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView  maintenance, asset, officer;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            maintenance = itemView.findViewById(R.id.tv_maintenance_code);
            asset = itemView.findViewById(R.id.tv_asset_main);
            officer = itemView.findViewById(R.id.tv_officer);

        }
    }
}
