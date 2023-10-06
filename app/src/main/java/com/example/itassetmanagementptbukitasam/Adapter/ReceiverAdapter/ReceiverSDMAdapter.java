package com.example.itassetmanagementptbukitasam.Adapter.ReceiverAdapter;


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
import com.example.itassetmanagementptbukitasam.model.ReceiverModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ReceiverSDMAdapter extends RecyclerView.Adapter<ReceiverSDMAdapter.ViewHolder>{
    int lastpos = -1;
    private ArrayList<ReceiverModel> receiverModelArrayList;
    private Context context;
    private ReceiverSDMAdapter.ViewDataSDMReceiverClickInterface viewDataSDMReceiverClickInterface;

    public ReceiverSDMAdapter(ArrayList<ReceiverModel>receiverModellArrayList, Context context, ViewDataSDMReceiverClickInterface viewDataSDMReceiverClickInterface) {
        this.receiverModelArrayList = receiverModellArrayList;
        this.context = context;
        this.viewDataSDMReceiverClickInterface = viewDataSDMReceiverClickInterface;
    }

    @NonNull
    @Override
    public ReceiverSDMAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_item_penerimaan_sdm,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiverSDMAdapter.ViewHolder assetholder, int position) {
        ReceiverModel receiverModel = receiverModelArrayList.get(position);
        assetholder.nama_aset.setText(receiverModel.getNamaAsetPen());
        assetholder.kode_aset.setText(receiverModel.getKodeAsetPen());
        assetholder.divisi.setText(receiverModel.getDivisiPen());

        setAnimation(assetholder.itemView,position);
        assetholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewDataSDMReceiverClickInterface.onViewDataSDMReceiverClick(position);
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
        return receiverModelArrayList.size();
    }
    public interface ViewDataSDMReceiverClickInterface {
        void onViewDataSDMReceiverClick(int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView nama_aset, kode_aset,divisi;
        private ImageView assetimage;



        public ViewHolder(@NonNull View itemView) {super(itemView);
            nama_aset =  itemView.findViewById(R.id.tv_name_asset_receiver_sdm);
            kode_aset =itemView.findViewById(R.id.tv_kode_Aset_receiver_sdm);
            divisi = itemView.findViewById(R.id.tv_division_receiver_sdm);

        }
    }

}
