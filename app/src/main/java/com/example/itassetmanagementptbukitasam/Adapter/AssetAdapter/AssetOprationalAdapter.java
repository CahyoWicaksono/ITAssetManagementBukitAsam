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


public class AssetOprationalAdapter extends RecyclerView.Adapter<AssetOprationalAdapter.ViewHolder>{
    int lastpos = -1;
    private ArrayList<AssetModel> assetModelArrayList;
    private Context context;
    private AssetOprationalAdapter.ViewDataOpratialClickInterface viewDataOpratialClickInterface;

    public AssetOprationalAdapter(ArrayList<AssetModel>assetModelArrayList, Context context, ViewDataOpratialClickInterface viewDataClickInterface) {
        this.assetModelArrayList = assetModelArrayList;
        this.context = context;
        this.viewDataOpratialClickInterface = viewDataClickInterface;
    }


    @NonNull
    @Override
    public AssetOprationalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_item_oprational,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssetOprationalAdapter.ViewHolder assetholder, int position) {
        AssetModel assetModel = assetModelArrayList.get(position);
        assetholder.asset_name.setText(assetModel.getNama_aset());
        assetholder.asset_code.setText(assetModel.getKode_aset());
        assetholder.supplier.setText(assetModel.getSupplier());
        Picasso.get().load(assetModel.getAssetLink()).into(assetholder.assetimage);

        setAnimation(assetholder.itemView,position);
        assetholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewDataOpratialClickInterface.onViewDataOpratialClick(position);
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
    public interface ViewDataOpratialClickInterface {
        void onViewDataOpratialClick(int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView asset_name, asset_code,supplier;
        private ImageView assetimage;

        public ViewHolder(@NonNull View itemView) {super(itemView);
            asset_code =  itemView.findViewById(R.id.tv_kode_aset_oprational);
            asset_name =itemView.findViewById(R.id.tv_nama_aset_oprational);

        }
    }

}
