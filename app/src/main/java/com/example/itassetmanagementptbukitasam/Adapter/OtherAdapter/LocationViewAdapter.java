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
import com.example.itassetmanagementptbukitasam.model.LocationModel;

import java.util.ArrayList;


public class LocationViewAdapter extends RecyclerView.Adapter<LocationViewAdapter.ViewHolder>{
    int lastpos = -1;
    private ArrayList<LocationModel> locationModelArrayList;
    private Context context;
    private LocationViewAdapter.ViewDataViewLocationClickInterface viewDataViewLocationClickInterface;

    public LocationViewAdapter(ArrayList<LocationModel>locationModelArrayList, Context context, ViewDataViewLocationClickInterface viewDataViewLocationClickInterface) {
        this.locationModelArrayList = locationModelArrayList;
        this.context = context;
        this.viewDataViewLocationClickInterface = viewDataViewLocationClickInterface;
    }


    @NonNull
    @Override
    public LocationViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_location_view,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewAdapter.ViewHolder locationholder, int position) {
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
                viewDataViewLocationClickInterface.onViewDataViewLocationClick(position);
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
    public interface ViewDataViewLocationClickInterface {
        void onViewDataViewLocationClick(int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView kodeAset, namaAset,jumlahHRD,  jumlahClinic,  jumlahOprasional,  jumlahSafety,
                jumlahLogistik,  jumlahHumas, jumlahLab, statusLocation;

        public ViewHolder(@NonNull View itemView) {super(itemView);
            kodeAset =  itemView.findViewById(R.id.kode_aset_lokasi_view);
            namaAset =itemView.findViewById(R.id.nama_aset_lokasi_view);
            jumlahHRD = itemView.findViewById(R.id.lokasi_hrd_view);
            jumlahClinic = itemView.findViewById(R.id.lokasi_clinic_view);
            jumlahOprasional = itemView.findViewById(R.id.lokasi_oprasional_view);
            jumlahSafety = itemView.findViewById(R.id.lokasi_safety_view);
            jumlahLogistik = itemView.findViewById(R.id.lokasi_logistik_view);
            jumlahHumas = itemView.findViewById(R.id.lokasi_humas_view);
            jumlahLab = itemView.findViewById(R.id.lokasi_lab_view);
            statusLocation = itemView.findViewById(R.id.status_lokasi_view);
        }
    }

}
