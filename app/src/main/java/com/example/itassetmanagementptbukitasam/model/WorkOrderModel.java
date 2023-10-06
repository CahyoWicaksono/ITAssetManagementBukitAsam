package com.example.itassetmanagementptbukitasam.model;

import android.os.Parcel;
import android.os.Parcelable;

public class WorkOrderModel implements Parcelable {

    String namaPetugas, namaAssetWork, kodeAssetWork, location, tanggalWork, waktuWork, workID;

    WorkOrderModel(){

    }

    public WorkOrderModel(String namaPetugas, String namaAssetWork, String kodeAssetWork, String location, String tanggalWork, String waktuWork, String workID) {
        this.namaPetugas = namaPetugas;
        this.namaAssetWork = namaAssetWork;
        this.kodeAssetWork = kodeAssetWork;
        this.location = location;
        this.tanggalWork = tanggalWork;
        this.waktuWork = waktuWork;
        this.workID = workID;
    }

    public String getNamaPetugas() {
        return namaPetugas;
    }

    public void setNamaPetugas(String namaPetugas) {
        this.namaPetugas = namaPetugas;
    }

    public String getNamaAssetWork() {
        return namaAssetWork;
    }

    public void setNamaAssetWork(String namaAssetWork) {
        this.namaAssetWork = namaAssetWork;
    }

    public String getKodeAssetWork() {
        return kodeAssetWork;
    }

    public void setKodeAssetWork(String kodeAssetWork) {
        this.kodeAssetWork = kodeAssetWork;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTanggalWork() {
        return tanggalWork;
    }

    public void setTanggalWork(String tanggalWork) {
        this.tanggalWork = tanggalWork;
    }

    public String getWaktuWork() {
        return waktuWork;
    }

    public void setWaktuWork(String waktuWork) {
        this.waktuWork = waktuWork;
    }

    public String getWorkID() {
        return workID;
    }

    public void setWorkID(String workID) {
        this.workID = workID;
    }

    protected WorkOrderModel(Parcel in) {
        namaAssetWork = in.readString();
        namaPetugas = in.readString();
        kodeAssetWork = in.readString();
        location = in.readString();
        tanggalWork = in.readString();
        waktuWork  = in.readString();
        workID = in.readString();
    }

    public static final Creator<WorkOrderModel> CREATOR = new Creator<WorkOrderModel>() {
        @Override
        public WorkOrderModel createFromParcel(Parcel in) {
            return new WorkOrderModel(in);
        }

        @Override
        public WorkOrderModel[] newArray(int size) {
            return new WorkOrderModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(namaAssetWork);
        parcel.writeString(kodeAssetWork);
        parcel.writeString(namaPetugas);
        parcel.writeString(location);
        parcel.writeString(tanggalWork);
        parcel.writeString(waktuWork);
        parcel.writeString(workID);
    }
}
