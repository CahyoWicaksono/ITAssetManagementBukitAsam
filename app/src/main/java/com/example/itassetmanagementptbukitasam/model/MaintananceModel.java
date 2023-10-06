package com.example.itassetmanagementptbukitasam.model;


import android.os.Parcel;
import android.os.Parcelable;

public class MaintananceModel implements Parcelable {
    String kode_mintanance, kode_aset, petugas, kondisi_peralatan, lokasi, status, tanggal, mainID;

    MaintananceModel()
    {

    }

    public MaintananceModel(String kode_mintanance, String kode_aset,String mainID, String petugas, String kondisi_peralatan, String lokasi, String status, String tanggal) {
        this.kode_mintanance = kode_mintanance;
        this.kode_aset = kode_aset;
        this.petugas = petugas;
        this.kondisi_peralatan = kondisi_peralatan;
        this.lokasi = lokasi;
        this.status = status;
        this.mainID = mainID;
        this.tanggal = tanggal;
    }

    protected MaintananceModel(Parcel in) {
        kode_mintanance = in.readString();
        kode_aset = in.readString();
        petugas = in.readString();
        kondisi_peralatan = in.readString();
        mainID  = in.readString();
        lokasi = in.readString();
        status = in.readString();
        tanggal = in.readString();
    }

    public static final Creator<MaintananceModel> CREATOR = new Creator<MaintananceModel>() {
        @Override
        public MaintananceModel createFromParcel(Parcel in) {
            return new MaintananceModel(in);
        }

        @Override
        public MaintananceModel[] newArray(int size) {
            return new MaintananceModel[size];
        }
    };

    public String getKode_mintanance() {
        return kode_mintanance;
    }

    public void setKode_mintanance(String kode_mintanance) {
        this.kode_mintanance = kode_mintanance;
    }

    public String getKode_aset() {
        return kode_aset;
    }

    public void setKode_aset(String kode_aset) {
        this.kode_aset = kode_aset;
    }

    public String getMainID() {
        return mainID;
    }

    public void setMainID(String mainID) {
        this.mainID = mainID;
    }

    public String getPetugas() {
        return petugas;
    }

    public void setPetugas(String petugas) {
        this.petugas = petugas;
    }

    public String getKondisi_peralatan() {
        return kondisi_peralatan;
    }

    public void setKondisi_peralatan(String kondisi_peralatan) {
        this.kondisi_peralatan = kondisi_peralatan;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(kode_mintanance);
        parcel.writeString(kode_aset);
        parcel.writeString(petugas);
        parcel.writeString(kondisi_peralatan);
        parcel.writeString(lokasi);
        parcel.writeString(status);
        parcel.writeString(tanggal);
        parcel.writeString(mainID);
    }
}
