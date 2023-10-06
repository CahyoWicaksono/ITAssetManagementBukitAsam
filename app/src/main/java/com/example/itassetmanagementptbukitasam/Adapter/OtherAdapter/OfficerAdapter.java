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
import com.example.itassetmanagementptbukitasam.model.OfficerModel;
import java.util.ArrayList;

public class OfficerAdapter extends RecyclerView.Adapter<OfficerAdapter.ViewHolder> {

    public OfficerAdapter(ArrayList<OfficerModel> officerModelArrayList, Context context, ViewOfficerClickInterface viewOfficerClickInterface) {
        this.officerModelArrayList = officerModelArrayList;
        this.context = context;
        this.viewOfficerClickInterface = viewOfficerClickInterface;
    }
    int lastpos = -1;
    private ArrayList<OfficerModel> officerModelArrayList;
    private Context context;
    private OfficerAdapter.ViewOfficerClickInterface viewOfficerClickInterface;


    @NonNull
    @Override
    public OfficerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_officer,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfficerAdapter.ViewHolder officerholder, int position) {
        OfficerModel officerModel = officerModelArrayList.get(position);
        officerholder.officer_code.setText(officerModel.getKode_petugas());
        officerholder.email.setText(officerModel.getEmail());
        officerholder.notelp.setText(officerModel.getNo_telp());

        setAnimation(officerholder.itemView,position);
        officerholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewOfficerClickInterface.onViewOfficerClick(position);
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
        return officerModelArrayList.size();
    }
    public interface ViewOfficerClickInterface {
        void onViewOfficerClick(int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView officer_code, email, notelp;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            officer_code =  itemView.findViewById(R.id.tv_OffICER_CODE);
            email  =itemView.findViewById(R.id.tv_email_officer);
            notelp =itemView.findViewById(R.id.tv_no_telp_officer);
        }
    }
}
