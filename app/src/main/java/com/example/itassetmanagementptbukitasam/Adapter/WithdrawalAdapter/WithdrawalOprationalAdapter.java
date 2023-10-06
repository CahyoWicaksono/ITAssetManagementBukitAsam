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

public class WithdrawalOprationalAdapter extends RecyclerView.Adapter<WithdrawalOprationalAdapter.ViewHolder> {
    int lastpos = -1;
    private ArrayList<WithdrawalModel> withdrawalModelArrayList;
    private Context context;
    private WithdrawalOprationalAdapter.WithdrawalOprationalClickInterface withdrawalOprationalClickInterface;

    public WithdrawalOprationalAdapter(ArrayList<WithdrawalModel> withdrawalModelArrayList, Context context, WithdrawalOprationalClickInterface withdrawalOprationalClickInterface) {
        this.withdrawalModelArrayList = withdrawalModelArrayList;
        this.context = context;
        this.withdrawalOprationalClickInterface = withdrawalOprationalClickInterface;
    }

    @NonNull
    @Override
    public WithdrawalOprationalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_withdrawal_oprational,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WithdrawalOprationalAdapter.ViewHolder withdrawalholder, int position) {
        WithdrawalModel withdrawalModel = withdrawalModelArrayList.get(position);
        withdrawalholder.withdrawal.setText(withdrawalModel.getKode_penarikan());
        withdrawalholder.asset.setText(withdrawalModel.getKode_aset());
        withdrawalholder.date.setText(withdrawalModel.getTanggal());



        setAnimation(withdrawalholder.itemView,position);
        withdrawalholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                withdrawalOprationalClickInterface.onWithdrawalOprationalClick(position);
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
    public interface WithdrawalOprationalClickInterface {
        void onWithdrawalOprationalClick(int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView withdrawal,asset,date;


        public ViewHolder(@NonNull View itemView) {super(itemView);
            withdrawal =  itemView.findViewById(R.id.tv_kode_with_oprational);
            asset =itemView.findViewById(R.id.tv_kode_aset_with_oprational);
            date  = itemView.findViewById(R.id.tv_date_with_oprational);


        }
    }
}
