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
import com.example.itassetmanagementptbukitasam.model.SoftwareModel;

import java.util.ArrayList;

public class SoftwareAdapter extends RecyclerView.Adapter<SoftwareAdapter.ViewHolder>{
    int lastpos = -1;
    private ArrayList<SoftwareModel> softwareModelArrayList;
    private Context context;
    private SoftwareClickInterface softwareClickInterface;

    public SoftwareAdapter(ArrayList<SoftwareModel> softwareModelArrayList, Context context, SoftwareClickInterface softwareClickInterface) {
        this.softwareModelArrayList = softwareModelArrayList;
        this.context = context;
        this.softwareClickInterface = softwareClickInterface;
    }

    @NonNull
    @Override
    public SoftwareAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_software,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SoftwareAdapter.ViewHolder softholder, int position) {
        SoftwareModel softwareModel = softwareModelArrayList.get(position);
        softholder.software_name.setText(softwareModel.getSoftware_name());
        softholder.brand_name.setText(softwareModel.getBrand_name());


        setAnimation(softholder.itemView,position);
        softholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                softwareClickInterface.onSosftwareClick(position);
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
        return softwareModelArrayList.size();
    }

    public interface SoftwareClickInterface{
        void onSosftwareClick(int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView software_name, brand_name;

        public ViewHolder(@NonNull View itemView) {super(itemView);
            software_name =  itemView.findViewById(R.id.tv_software);
            brand_name =itemView.findViewById(R.id.tv_brand);

        }
    }
}