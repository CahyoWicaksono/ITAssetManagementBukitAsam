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
import com.example.itassetmanagementptbukitasam.model.CheckingModel;

import java.util.ArrayList;


public class CheckingAdapter extends RecyclerView.Adapter<CheckingAdapter.ViewHolder>{
    int lastpos = -1;
    private ArrayList<CheckingModel> checkingModelArrayList;
    private Context context;
    private CheckingAdapter.ViewDataCheckingClickInterface viewDataCheckingClickInterface;

    public CheckingAdapter(ArrayList<CheckingModel>checkingAdapterArrayList, Context context, ViewDataCheckingClickInterface viewDataCheckingClickInterface) {
        this.checkingModelArrayList = checkingAdapterArrayList;
        this.context = context;
        this.viewDataCheckingClickInterface = viewDataCheckingClickInterface;
    }


    @NonNull
    @Override
    public CheckingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_checking,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckingAdapter.ViewHolder checkholder, int position) {
        CheckingModel checkingModel = checkingModelArrayList.get(position);
        checkholder.nama_Asset.setText(checkingModel.getNamaAsetCheck());
        checkholder.kode_asset.setText(checkingModel.getKodeAsetCheck());
        checkholder.officer.setText(checkingModel.getPetugasCheck());
        setAnimation(checkholder.itemView,position);
        checkholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewDataCheckingClickInterface.onViewDataCheckingClick(position);
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
        return checkingModelArrayList.size();
    }
    public interface ViewDataCheckingClickInterface {
        void onViewDataCheckingClick(int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView nama_Asset, kode_asset,officer;




        public ViewHolder(@NonNull View itemView) {super(itemView);
            nama_Asset =  itemView.findViewById(R.id.TVAssetNameChecking);
            kode_asset =itemView.findViewById(R.id.TVAssetCodeChecking);
            officer = itemView.findViewById(R.id.TVOfficerChecking);
        }
    }

}
