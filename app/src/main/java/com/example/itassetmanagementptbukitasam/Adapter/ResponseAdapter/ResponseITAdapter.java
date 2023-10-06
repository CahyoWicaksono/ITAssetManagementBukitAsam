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

public class ResponseITAdapter extends RecyclerView.Adapter<ResponseITAdapter.ViewHolder> {

    int lastpos = -1;
    private ArrayList<ResponseModel> responseModelArrayList;
    private Context context;
    private ResponseITAdapter.ResponseITClickInterface responseITClickInterface;

    public ResponseITAdapter(ArrayList<ResponseModel> responseModelArrayList, Context context, ResponseITClickInterface responseITClickInterface) {
        this.responseModelArrayList = responseModelArrayList;
        this.context = context;
        this.responseITClickInterface = responseITClickInterface;
    }



    @NonNull
    @Override
    public ResponseITAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_response_it,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResponseITAdapter.ViewHolder responseHolder, int position) {
        ResponseModel responseModel = responseModelArrayList.get(position);

        responseHolder.responseName.setText(responseModel.getNamaResponse());
        responseHolder.date.setText(responseModel.getTanggalResponse());
        responseHolder.division.setText(responseModel.getDivisiResponse());

        setAnimation(responseHolder.itemView,position);
        responseHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {responseITClickInterface.onResponseITClick(position);
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
    public interface ResponseITClickInterface {
        void onResponseITClick(int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView responseName, date, division;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            responseName = itemView.findViewById(R.id.tv_name_response_IT);
            date = itemView.findViewById(R.id.tv_date_responseresponse_IT);
            division = itemView.findViewById(R.id.tv_division_response_IT);


        }
    }
}
