package com.example.itassetmanagementptbukitasam.Adapter.ResponseAdapter;


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
import com.example.itassetmanagementptbukitasam.model.ResponseModel;

import java.util.ArrayList;

public class ResponseSDMAdapter extends RecyclerView.Adapter<ResponseSDMAdapter.ViewHolder> {

    int lastpos = -1;
    private ArrayList<ResponseModel> responseModelArrayList;
    private Context context;
    private ResponseSDMAdapter.ResponseSDMClickInterface responseSDMClickInterface;


    public ResponseSDMAdapter(ArrayList<ResponseModel> responseModelArrayList, Context context, ResponseSDMClickInterface responseSDMClickInterface) {
        this.responseModelArrayList = responseModelArrayList;
        this.context = context;
        this.responseSDMClickInterface = responseSDMClickInterface;
    }

    @NonNull
    @Override
    public ResponseSDMAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_response_sdm,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResponseSDMAdapter.ViewHolder responseHolder, int position) {
        ResponseModel responseModel = responseModelArrayList.get(position);

        responseHolder.responseName.setText(responseModel.getNamaResponse());
        responseHolder.date.setText(responseModel.getTanggalResponse());
        responseHolder.division.setText(responseModel.getDivisiResponse());

        setAnimation(responseHolder.itemView,position);
        responseHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {responseSDMClickInterface.onResponseSDMClick(position);
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
    public int getItemCount() {return responseModelArrayList.size();}
    public interface ResponseSDMClickInterface {
        void onResponseSDMClick(int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView responseName, date, division;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            responseName = itemView.findViewById(R.id.tv_name_response_sdm);
            date = itemView.findViewById(R.id.tv_date_response_sdm);
            division = itemView.findViewById(R.id.tv_division_response_sdm);


        }
    }
}
