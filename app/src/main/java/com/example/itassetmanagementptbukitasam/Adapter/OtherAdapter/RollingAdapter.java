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
import com.example.itassetmanagementptbukitasam.model.RollingModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class RollingAdapter extends RecyclerView.Adapter<RollingAdapter.ViewHolder> {
    int lastpos = -1;
    private ArrayList<RollingModel> rollingModelArrayList;
    private Context context;
    private RollingAdapter.RollingClickInterface rollingClickInterface;

    public RollingAdapter(ArrayList<RollingModel> rollingModelArrayList, Context context, RollingClickInterface rollingClickInterface) {
        this.rollingModelArrayList = rollingModelArrayList;
        this.context = context;
        this.rollingClickInterface = rollingClickInterface;
    }

    @NonNull
    @Override
    public RollingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_rolling,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder rolling, int position) {
        RollingModel rollingModel = rollingModelArrayList.get(position);
        rolling.assetName.setText(rollingModel.getNamaAssetRoll());
        rolling.assetCode.setText(rollingModel.getKodeAssetRoll());
        rolling.division.setText(rollingModel.getDivisiRoll());
        Picasso.get().load(rollingModel.getRollingLink()).into(rolling.image);

        setAnimation(rolling.itemView,position);
        rolling.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollingClickInterface.onRollingClick(position);
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
        return rollingModelArrayList.size();
    }
    public interface RollingClickInterface { void onRollingClick(int position);}

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView assetName, assetCode, division;
        private ImageView image;

        public ViewHolder(@NonNull View itemView) {super(itemView);
            assetName = itemView.findViewById(R.id.tv_asset_name_rolling);
            assetCode = itemView.findViewById(R.id.tv_asset_code_rolling);
            division = itemView.findViewById(R.id.tv_division_rolling);

            image = itemView.findViewById(R.id.IMG_asset_rolling);

        }
    }


}
