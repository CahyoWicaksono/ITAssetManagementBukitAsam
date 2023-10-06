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
import com.example.itassetmanagementptbukitasam.model.TransferModel;

import java.util.ArrayList;


public class TransferAdapter extends RecyclerView.Adapter<TransferAdapter.ViewHolder> {

    int lastpos = -1;
    private ArrayList<TransferModel> transferModelArrayList;
    private Context context;
    private TransferAdapter.TransferClickInterface transferClickInterface;

    public TransferAdapter(ArrayList<TransferModel> transferModelArrayList, Context context, TransferClickInterface transferClickInterface) {
        this.transferModelArrayList = transferModelArrayList;
        this.context = context;
        this.transferClickInterface = transferClickInterface;
    }


    @NonNull
    @Override
    public TransferAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_transfer,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransferAdapter.ViewHolder transferholder, int position) {
        TransferModel transferModel = transferModelArrayList.get(position);
        transferholder.transfer.setText(transferModel.getKode_transfer());
        transferholder.asset.setText(transferModel.getKode_aset());
        transferholder.date.setText(transferModel.getTanggal());

        setAnimation(transferholder.itemView,position);
        transferholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transferClickInterface.onTransferClick(position);
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
        return transferModelArrayList.size();
    }
    public interface TransferClickInterface {
        void onTransferClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView transfer,asset,date;
        private ImageView TFimage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
                transfer =  itemView.findViewById(R.id.tv_kode_tf);
                asset = itemView.findViewById(R.id.tv_kode_aset_tf);
                date  = itemView.findViewById(R.id.tv_date_tf);


        }
    }
}
