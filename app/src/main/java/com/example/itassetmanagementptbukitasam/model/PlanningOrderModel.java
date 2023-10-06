package com.example.itassetmanagementptbukitasam.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PlanningOrderModel implements Parcelable {

    String namaAsetPlanning, kodeAssetPlanning, tanggalPlanning, jumlahPlanning,divisiPlanning, planningID, linkPlanning;

    PlanningOrderModel (){

    }

    public PlanningOrderModel(String namaAsetPlanning, String kodeAssetPlanning, String tanggalPlanning, String jumlahPlanning, String divisiPlanning, String planningID, String linkPlanning) {
        this.namaAsetPlanning = namaAsetPlanning;
        this.kodeAssetPlanning = kodeAssetPlanning;
        this.tanggalPlanning = tanggalPlanning;
        this.jumlahPlanning = jumlahPlanning;
        this.divisiPlanning = divisiPlanning;
        this.planningID = planningID;
        this.linkPlanning = linkPlanning;
    }
    public String getNamaAsetPlanning() {
        return namaAsetPlanning;
    }

    public void setNamaAsetPlanning(String namaAsetPlanning) {
        this.namaAsetPlanning = namaAsetPlanning;
    }

    public String getKodeAssetPlanning() {
        return kodeAssetPlanning;
    }

    public void setKodeAssetPlanning(String kodeAssetPlanning) {
        this.kodeAssetPlanning = kodeAssetPlanning;
    }

    public String getTanggalPlanning() {
        return tanggalPlanning;
    }

    public void setTanggalPlanning(String tanggalPlanning) {
        this.tanggalPlanning = tanggalPlanning;
    }

    public String getJumlahPlanning() {
        return jumlahPlanning;
    }

    public void setJumlahPlanning(String jumlahPlanning) {
        this.jumlahPlanning = jumlahPlanning;
    }

    public String getDivisiPlanning() {
        return divisiPlanning;
    }

    public void setDivisiPlanning(String divisiPlanning) {
        this.divisiPlanning = divisiPlanning;
    }

    public String getPlanningID() {
        return planningID;
    }

    public void setPlanningID(String planningID) {
        this.planningID = planningID;
    }

    public String getLinkPlanning() {
        return linkPlanning;
    }

    public void setLinkPlanning(String linkPlanning) {
        this.linkPlanning = linkPlanning;
    }


    protected PlanningOrderModel(Parcel in) {
        namaAsetPlanning = in.toString();
        kodeAssetPlanning = in.toString();
        tanggalPlanning = in.toString();
        jumlahPlanning = in.toString();
        divisiPlanning = in.toString();
        planningID = in.toString();
        linkPlanning = in.toString();

    }

    public static final Creator<PlanningOrderModel> CREATOR = new Creator<PlanningOrderModel>() {
        @Override
        public PlanningOrderModel createFromParcel(Parcel in) {
            return new PlanningOrderModel(in);
        }

        @Override
        public PlanningOrderModel[] newArray(int size) {
            return new PlanningOrderModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(namaAsetPlanning);
        parcel.writeString(kodeAssetPlanning);
        parcel.writeString(tanggalPlanning);
        parcel.writeString(jumlahPlanning);
        parcel.writeString(divisiPlanning);
        parcel.writeString(namaAsetPlanning);
        parcel.writeString(planningID);
        parcel.writeString(linkPlanning);
    }
}
