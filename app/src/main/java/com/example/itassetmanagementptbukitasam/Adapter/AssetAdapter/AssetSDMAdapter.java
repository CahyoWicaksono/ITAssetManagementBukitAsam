package com.example.itassetmanagementptbukitasam.Adapter.AssetAdapter;


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
import com.example.itassetmanagementptbukitasam.model.AssetModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class AssetSDMAdapter extends RecyclerView.Adapter<AssetSDMAdapter.ViewHolder>{
    int lastpos = -1;
    private ArrayList<AssetModel> assetModelArrayList;
    private Context context;
    private AssetSDMAdapter.ViewDataSDMClickInterface viewDataSDMClickInterface;

    public AssetSDMAdapter(ArrayList<AssetModel>assetModelArrayList, Context context, ViewDataSDMClickInterface viewDataSDMClickInterface) {
        this.assetModelArrayList = assetModelArrayList;
        this.context = context;
        this.viewDataSDMClickInterface = viewDataSDMClickInterface;
    }

    @NonNull
    @Override
    public AssetSDMAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_item_sdm,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssetSDMAdapter.ViewHolder assetholder, int position) {
        AssetModel assetModel = assetModelArrayList.get(position);
        assetholder.asset_name.setText(assetModel.getNama_aset());
        assetholder.asset_code.setText(assetModel.getKode_aset());


        setAnimation(assetholder.itemView,position);
        assetholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewDataSDMClickInterface.onViewDataSDMClick(position);
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
        return assetModelArrayList.size();
    }
    public interface ViewDataSDMClickInterface {
        void onViewDataSDMClick(int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView asset_name, asset_code,supplier;
        private ImageView assetimage;

        public ViewHolder(@NonNull View itemView) {super(itemView);
            asset_code =  itemView.findViewById(R.id.tv_kode_aset_complaint);
            asset_name =itemView.findViewById(R.id.tv_nama_aset_complaint);
        }
    }

}
