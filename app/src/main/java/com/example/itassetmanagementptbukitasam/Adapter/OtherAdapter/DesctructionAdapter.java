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
import com.example.itassetmanagementptbukitasam.model.PemusnahanModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class DesctructionAdapter extends RecyclerView.Adapter<DesctructionAdapter.ViewHolder>{
    int lastpos = -1;
    private ArrayList<PemusnahanModel> pemusnahanModelArrayList;
    private Context context;
    private DesctructionAdapter.ViewDataDesctructionClickInterface viewDataDesctructionClickInterface;

    public DesctructionAdapter(ArrayList<PemusnahanModel>pemusnahanModelArrayList, Context context, ViewDataDesctructionClickInterface viewDataDesctructionClickInterface) {
        this.pemusnahanModelArrayList = pemusnahanModelArrayList;
        this.context = context;
        this.viewDataDesctructionClickInterface = viewDataDesctructionClickInterface;

    }

    @NonNull
    @Override
    public DesctructionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_item_pemusnahan,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DesctructionAdapter.ViewHolder pemusnahanholder, int position) {
        PemusnahanModel pemusnahanModel = pemusnahanModelArrayList.get(position);
        pemusnahanholder.asset_name.setText(pemusnahanModel.getNamaAsetPem());
        pemusnahanholder.asset_code.setText(pemusnahanModel.getKodeAssetPem());
        pemusnahanholder.type.setText(pemusnahanModel.getTypeAsetPem());
        Picasso.get().load(pemusnahanModel.getLinkImageDestruction()).into(pemusnahanholder.assetimage);
        setAnimation(pemusnahanholder.itemView,position);

        pemusnahanholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewDataDesctructionClickInterface.onViewDataDesctructionClick(position);
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
        return pemusnahanModelArrayList.size();
    }
    public interface ViewDataDesctructionClickInterface {
        void onViewDataDesctructionClick(int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView asset_name, asset_code,type;
        private ImageView assetimage;

        public ViewHolder(@NonNull View itemView) {super(itemView);
            asset_name =itemView.findViewById(R.id.tv_name_asset_pemusnahan);
            asset_code =  itemView.findViewById(R.id.tv_kode_Aset_pemusnahan);
            type = itemView.findViewById(R.id.tv_type_pemusnahan);
            assetimage = itemView.findViewById(R.id.image_asset_pemusnahan);
        }
    }

}
