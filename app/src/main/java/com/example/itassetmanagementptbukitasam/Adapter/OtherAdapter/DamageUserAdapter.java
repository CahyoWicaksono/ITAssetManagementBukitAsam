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
import com.example.itassetmanagementptbukitasam.model.KerusakanModel;

import java.util.ArrayList;

public class DamageUserAdapter extends RecyclerView.Adapter<DamageUserAdapter.ViewHolder> {
    public DamageUserAdapter(ArrayList<KerusakanModel> kerusakanModelArrayList, Context context, DamageUserClickInterface damageUserClickInterface) {
        this.kerusakanModelArrayList = kerusakanModelArrayList;
        this.context = context;
        this.damageUserClickInterface = damageUserClickInterface;
    }
    int lastpos = -1;
    private ArrayList<KerusakanModel> kerusakanModelArrayList;
    private Context context;
    private DamageUserAdapter.DamageUserClickInterface damageUserClickInterface;


    @NonNull
    @Override
    public DamageUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_kerusakan_user,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DamageUserAdapter.ViewHolder Damageholder, int position) {
        KerusakanModel kerusakanModel = kerusakanModelArrayList.get(position);

        Damageholder.damage_code.setText(kerusakanModel.getKode_kerusakan());
        Damageholder.asset_code.setText(kerusakanModel.getKode_aset());
        Damageholder.date.setText(kerusakanModel.getTanggal());



        setAnimation(Damageholder.itemView,position);
        Damageholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {damageUserClickInterface.onDamageUserClick(position);
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
        return kerusakanModelArrayList.size();
    }
    public interface DamageUserClickInterface {
        void onDamageUserClick(int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView damage_code, asset_code, date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            damage_code = itemView.findViewById(R.id.tv_damage_user);
            asset_code = itemView.findViewById(R.id.tv_asset_ker_user);
            date = itemView.findViewById(R.id.tv_tanggal_ker_user);


        }
    }
}
