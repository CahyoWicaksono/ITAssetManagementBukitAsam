package com.example.itassetmanagementptbukitasam.Adapter.OtherAdapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.LocationModel;

import java.util.ArrayList;


public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder>{
    int lastpos = -1;
    private ArrayList<LocationModel> locationModelArrayList;
    private Context context;
    private LocationAdapter.ViewDataLocationClickInterface viewDataLocationClickInterface;

    public LocationAdapter(ArrayList<LocationModel>locationModelArrayList, Context context, ViewDataLocationClickInterface viewDataLocationClickInterface) {
        this.locationModelArrayList = locationModelArrayList;
        this.context = context;
        this.viewDataLocationClickInterface = viewDataLocationClickInterface;
    }


    @NonNull
    @Override
    public LocationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_location,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.ViewHolder locationholder, int position) {
        LocationModel locationModel = locationModelArrayList.get(position);
        locationholder.kodeAset.setText(locationModel.getKodeAsetLocation());
        locationholder.namaAset.setText(locationModel.getNamaAsetLocation());
        locationholder.jumlahHRD.setText(locationModel.getJumlahHRD());
        locationholder.jumlahClinic.setText(locationModel.getJumlahClinic());
        locationholder.jumlahOprasional.setText(locationModel.getJumlahOprasional());
        locationholder.jumlahLogistik.setText(locationModel.getJumlahLogistik());
        locationholder.jumlahHumas.setText(locationModel.getJumlahHumas());
        locationholder.jumlahLab.setText(locationModel.getJumlahLab());
        locationholder.jumlahSafety.setText(locationModel.getJumlahSafety());
        locationholder.statusLocation.setText(locationModel.getStatusAsset());

        setAnimation(locationholder.itemView,position);
        locationholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewDataLocationClickInterface.onViewDataLocationClick(position);
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
        return locationModelArrayList.size();
    }
    public interface ViewDataLocationClickInterface {
        void onViewDataLocationClick(int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView kodeAset, namaAset,jumlahHRD,  jumlahClinic,  jumlahOprasional,  jumlahSafety,
                jumlahLogistik,  jumlahHumas, jumlahLab, statusLocation;

        public ViewHolder(@NonNull View itemView) {super(itemView);
            kodeAset =  itemView.findViewById(R.id.kode_aset_lokasi);
            namaAset =itemView.findViewById(R.id.nama_aset_lokasi);
            jumlahHRD = itemView.findViewById(R.id.lokasi_hrd);
            jumlahClinic = itemView.findViewById(R.id.lokasi_clinic);
            jumlahOprasional = itemView.findViewById(R.id.lokasi_oprasional);
            jumlahSafety = itemView.findViewById(R.id.lokasi_safety);
            jumlahLogistik = itemView.findViewById(R.id.lokasi_logistik);
            jumlahHumas = itemView.findViewById(R.id.lokasi_humas);
            jumlahLab = itemView.findViewById(R.id.lokasi_lab);
            statusLocation = itemView.findViewById(R.id.status_lokasi);
        }
    }

}
