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
import com.example.itassetmanagementptbukitasam.model.OrderModel;
import com.example.itassetmanagementptbukitasam.model.PendingAssetModel;

import java.util.ArrayList;


public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.ViewHolder>{
    int lastpos = -1;
    private ArrayList<PendingAssetModel> pendingAssetModelArrayList;
    private Context context;
    private PendingAdapter.ViewDataPendingClickInterface viewDataPendingClickInterface;

    public PendingAdapter(ArrayList<PendingAssetModel>pendingAssetModelArrayList, Context context, ViewDataPendingClickInterface viewDataPendingClickInterface) {
        this.pendingAssetModelArrayList = pendingAssetModelArrayList;
        this.context = context;
        this.viewDataPendingClickInterface = viewDataPendingClickInterface;
    }


    @NonNull
    @Override
    public PendingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_pending,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingAdapter.ViewHolder assetholder, int position) {
        PendingAssetModel pendingAssetModel = pendingAssetModelArrayList.get(position);
        assetholder.nama_pengadu.setText(pendingAssetModel.getNamaAsetPending());
        assetholder.date.setText(pendingAssetModel.getKodeAsetPending());
        assetholder.divisi.setText(pendingAssetModel.getTanggalAsetPending());
        setAnimation(assetholder.itemView,position);
        assetholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewDataPendingClickInterface.onViewDataPendingClick(position);
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
        return pendingAssetModelArrayList.size();
    }
    public interface ViewDataPendingClickInterface {
        void onViewDataPendingClick(int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView nama_pengadu, date,divisi;




        public ViewHolder(@NonNull View itemView) {super(itemView);
            nama_pengadu =  itemView.findViewById(R.id.tv_name_pending);
            date =itemView.findViewById(R.id.tv_code_pending);
            divisi = itemView.findViewById(R.id.tv_date_pending);
        }
    }

}
