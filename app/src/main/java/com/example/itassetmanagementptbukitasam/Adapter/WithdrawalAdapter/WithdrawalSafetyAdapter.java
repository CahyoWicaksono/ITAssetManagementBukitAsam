package com.example.itassetmanagementptbukitasam.Adapter.WithdrawalAdapter;


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
import com.example.itassetmanagementptbukitasam.model.WithdrawalModel;

import java.util.ArrayList;

public class WithdrawalSafetyAdapter extends RecyclerView.Adapter<WithdrawalSafetyAdapter.ViewHolder> {
    int lastpos = -1;
    private ArrayList<WithdrawalModel> withdrawalModelArrayList;
    private Context context;
    private WithdrawalSafetyAdapter.WithdrawalSafetyClickInterface withdrawalSafetyClickInterface;

    public WithdrawalSafetyAdapter(ArrayList<WithdrawalModel> withdrawalModelArrayList, Context context, WithdrawalSafetyClickInterface withdrawalSafetyClickInterface) {
        this.withdrawalModelArrayList = withdrawalModelArrayList;
        this.context = context;
        this.withdrawalSafetyClickInterface = withdrawalSafetyClickInterface;
    }

    @NonNull
    @Override
    public WithdrawalSafetyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_withdrawal_safety,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WithdrawalSafetyAdapter.ViewHolder withdrawalholder, int position) {
        WithdrawalModel withdrawalModel = withdrawalModelArrayList.get(position);
        withdrawalholder.withdrawal.setText(withdrawalModel.getKode_penarikan());
        withdrawalholder.asset.setText(withdrawalModel.getKode_aset());
        withdrawalholder.date.setText(withdrawalModel.getTanggal());



        setAnimation(withdrawalholder.itemView,position);
        withdrawalholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                withdrawalSafetyClickInterface.onWithdrawalSafetyClick(position);
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
        return withdrawalModelArrayList.size();
    }
    public interface WithdrawalSafetyClickInterface {
        void onWithdrawalSafetyClick(int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView withdrawal,asset,date;


        public ViewHolder(@NonNull View itemView) {super(itemView);
            withdrawal =  itemView.findViewById(R.id.tv_kode_with_safety);
            asset =itemView.findViewById(R.id.tv_kode_aset_with_safety);
            date  = itemView.findViewById(R.id.tv_date_with_safety);


        }
    }
}
