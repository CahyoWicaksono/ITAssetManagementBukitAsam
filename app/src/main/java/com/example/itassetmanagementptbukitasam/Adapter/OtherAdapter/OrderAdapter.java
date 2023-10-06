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
import com.example.itassetmanagementptbukitasam.model.ComplaintModel;
import com.example.itassetmanagementptbukitasam.model.OrderModel;

import java.util.ArrayList;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{
    int lastpos = -1;
    private ArrayList<OrderModel> orderModelArrayList;
    private Context context;
    private OrderAdapter.ViewDataOrderClickInterface viewDataOrderClickInterface;

    public OrderAdapter(ArrayList<OrderModel>orderModelArrayList, Context context, ViewDataOrderClickInterface viewDataOrderClickInterface) {
        this.orderModelArrayList = orderModelArrayList;
        this.context = context;
        this.viewDataOrderClickInterface = viewDataOrderClickInterface;
    }


    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_order,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder assetholder, int position) {
        OrderModel orderModel = orderModelArrayList.get(position);
        assetholder.nama_pengadu.setText(orderModel.getNamaAsetOrder());
        assetholder.date.setText(orderModel.getKodeAsetOrder());
        assetholder.divisi.setText(orderModel.getTanggalOrder());
        setAnimation(assetholder.itemView,position);
        assetholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewDataOrderClickInterface.onViewDataOrderClick(position);
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
        return orderModelArrayList.size();
    }
    public interface ViewDataOrderClickInterface {
        void onViewDataOrderClick(int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView nama_pengadu, date,divisi;




        public ViewHolder(@NonNull View itemView) {super(itemView);
            nama_pengadu =  itemView.findViewById(R.id.tv_name_order);
            date =itemView.findViewById(R.id.tv_code_order);
            divisi = itemView.findViewById(R.id.tv_date_order);
        }
    }

}
