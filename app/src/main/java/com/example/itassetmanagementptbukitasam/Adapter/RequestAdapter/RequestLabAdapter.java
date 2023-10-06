package com.example.itassetmanagementptbukitasam.Adapter.RequestAdapter;


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
import com.example.itassetmanagementptbukitasam.model.RequestModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class RequestLabAdapter extends RecyclerView.Adapter<RequestLabAdapter.ViewHolder>{
    int lastpos = -1;
    private ArrayList<RequestModel> requestModelArrayList;
    private Context context;
    private RequestLabAdapter.ViewDataLabRequestClickInterface viewDataLabRequestClickInterface;

    public RequestLabAdapter(ArrayList<RequestModel>requestModellArrayList, Context context, ViewDataLabRequestClickInterface viewDataLabRequestClickInterface) {
        this.requestModelArrayList = requestModellArrayList;
        this.context = context;
        this.viewDataLabRequestClickInterface = viewDataLabRequestClickInterface;
    }

    @NonNull
    @Override
    public RequestLabAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_item_permintaan_lab,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestLabAdapter.ViewHolder assetholder, int position) {
        RequestModel requestModel = requestModelArrayList.get(position);
        assetholder.nama_aset.setText(requestModel.getNameRequest());
        assetholder.kode_aset.setText(requestModel.getKodeAsetRequest());
        assetholder.divisi.setText(requestModel.getDivisionRequest());


        Picasso.get().load(requestModel.getLinkrequest()).into(assetholder.assetimage);
        setAnimation(assetholder.itemView,position);
        assetholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewDataLabRequestClickInterface.onViewDataLabRequestClick(position);
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
        return requestModelArrayList.size();
    }
    public interface ViewDataLabRequestClickInterface {
        void onViewDataLabRequestClick(int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView nama_aset, kode_aset,divisi;
        private ImageView assetimage;



        public ViewHolder(@NonNull View itemView) {super(itemView);
            nama_aset =  itemView.findViewById(R.id.tv_name_asset_request_lab);
            kode_aset =itemView.findViewById(R.id.tv_kode_Aset_request_lab);
            divisi = itemView.findViewById(R.id.tv_division_request_lab);

            assetimage = itemView.findViewById(R.id.image_asset_request_lab);
        }
    }

}
