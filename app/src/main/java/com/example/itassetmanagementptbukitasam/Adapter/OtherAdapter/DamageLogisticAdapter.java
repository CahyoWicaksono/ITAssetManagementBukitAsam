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
import com.example.itassetmanagementptbukitasam.model.KerusakanLogistikModel;

import java.util.ArrayList;


public class DamageLogisticAdapter extends RecyclerView.Adapter<DamageLogisticAdapter.ViewHolder>{
    int lastpos = -1;
    private ArrayList<KerusakanLogistikModel> kerusakanLogistikModelArrayList;
    private Context context;
    private DamageLogisticAdapter.ViewDataKerusakanLogisticClickInterface viewDataKerusakanLogisticClickInterface;

    public DamageLogisticAdapter(ArrayList<KerusakanLogistikModel>kerusakanLogistikModelArrayList, Context context, ViewDataKerusakanLogisticClickInterface viewDataKerusakanLogisticClickInterface) {
        this.kerusakanLogistikModelArrayList = kerusakanLogistikModelArrayList;
        this.context = context;
        this.viewDataKerusakanLogisticClickInterface = viewDataKerusakanLogisticClickInterface;
    }


    @NonNull
    @Override
    public DamageLogisticAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_kerusakan_logistic,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DamageLogisticAdapter.ViewHolder assetholder, int position) {
        KerusakanLogistikModel kerusakanLogistikModel = kerusakanLogistikModelArrayList.get(position);
        assetholder.nama_aset.setText(kerusakanLogistikModel.getNamaKerusakan());
        assetholder.kode_aset.setText(kerusakanLogistikModel.getKodeasetKerusakan());
        assetholder.date.setText(kerusakanLogistikModel.getTanggalKerusakan());
        setAnimation(assetholder.itemView,position);
        assetholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewDataKerusakanLogisticClickInterface.onViewDataKerusakanClick(position);
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
        return kerusakanLogistikModelArrayList.size();
    }
    public interface ViewDataKerusakanLogisticClickInterface {
        void onViewDataKerusakanClick(int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView nama_aset, kode_aset,date;

        public ViewHolder(@NonNull View itemView) {super(itemView);
            nama_aset =  itemView.findViewById(R.id.tv_damage_logistic);
            kode_aset =itemView.findViewById(R.id.tv_asset_ker_logistic);
            date = itemView.findViewById(R.id.tv_tanggal_ker_logistic);
        }
    }

}
