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
import com.example.itassetmanagementptbukitasam.model.AssetModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ViewAssetAdapter extends RecyclerView.Adapter<ViewAssetAdapter.ViewHolder>{
    int lastpos = -1;
    private ArrayList<AssetModel> assetModelArrayList;
    private Context context;
    private ViewAssetAdapter.ViewAssetDataClickInterface viewAssetDataClickInterface;

    public ViewAssetAdapter(ArrayList<AssetModel> peralatanModelArrayList, Context context, ViewAssetDataClickInterface viewDataClickInterface) {
        this.assetModelArrayList = peralatanModelArrayList;
        this.context = context;
        this.viewAssetDataClickInterface = viewDataClickInterface;
    }


    @NonNull
    @Override
    public ViewAssetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_view_item,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAssetAdapter.ViewHolder assetholder, int position) {
        AssetModel assetModel = assetModelArrayList.get(position);
        assetholder.asset_code.setText(assetModel.getKode_aset());
        assetholder.asset_name.setText(assetModel.getNama_aset());


        setAnimation(assetholder.itemView,position);
        assetholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewAssetDataClickInterface.onViewAssetDataClick(position);
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
    public interface ViewAssetDataClickInterface {
        void onViewAssetDataClick(int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView asset_code,asset_name, supplier;
        private ImageView assetimage;


        public ViewHolder(@NonNull View itemView) {super(itemView);
            asset_code =  itemView.findViewById(R.id.kode_aset);
            asset_name =itemView.findViewById(R.id.nama_Aset);
        }
    }

}
