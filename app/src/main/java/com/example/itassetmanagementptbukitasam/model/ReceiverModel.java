package com.example.itassetmanagementptbukitasam.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ReceiverModel implements Parcelable {
    String namaAsetPen, kodeAsetPen, tanggalPen, jumlahPen, kondisiPen, divisiPen, penerimaanID;

    ReceiverModel(){

    }

    public ReceiverModel(String namaAsetPen, String kodeAsetPen, String tanggalPen, String jumlahPen, String kondisiPen, String divisiPen, String penerimaanID) {
        this.namaAsetPen = namaAsetPen;
        this.kodeAsetPen = kodeAsetPen;
        this.tanggalPen = tanggalPen;
        this.jumlahPen = jumlahPen;
        this.kondisiPen = kondisiPen;
        this.divisiPen = divisiPen;
        this.penerimaanID = penerimaanID;
    }


    public String getNamaAsetPen() {
        return namaAsetPen;
    }

    public void setNamaAsetPen(String namaAsetPen) {
        this.namaAsetPen = namaAsetPen;
    }

    public String getKodeAsetPen() {
        return kodeAsetPen;
    }

    public void setKodeAsetPen(String kodeAsetPen) {
        this.kodeAsetPen = kodeAsetPen;
    }

    public String getTanggalPen() {
        return tanggalPen;
    }

    public void setTanggalPen(String tanggalPen) {
        this.tanggalPen = tanggalPen;
    }

    public String getJumlahPen() {
        return jumlahPen;
    }

    public void setJumlahPen(String jumlahPen) {
        this.jumlahPen = jumlahPen;
    }

    public String getKondisiPen() {
        return kondisiPen;
    }

    public void setKondisiPen(String kondisiPen) {
        this.kondisiPen = kondisiPen;
    }

    public String getDivisiPen() {
        return divisiPen;
    }

    public void setDivisiPen(String divisiPen) {
        this.divisiPen = divisiPen;
    }

    public String getPenerimaanID() {
        return penerimaanID;
    }

    public void setPenerimaanID(String penerimaanID) {
        this.penerimaanID = penerimaanID;
    }

    protected ReceiverModel(Parcel in) {
        namaAsetPen = in.readString();
        kodeAsetPen = in.readString();
        tanggalPen = in.readString();
        jumlahPen = in.readString();
        kondisiPen = in.readString();
        divisiPen = in.readString();
        penerimaanID = in.readString();
    }

    public static final Creator<ReceiverModel> CREATOR = new Creator<ReceiverModel>() {
        @Override
        public ReceiverModel createFromParcel(Parcel in) {
            return new ReceiverModel(in);
        }

        @Override
        public ReceiverModel[] newArray(int size) {
            return new ReceiverModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(namaAsetPen);
        parcel.writeString(kodeAsetPen);
        parcel.writeString(tanggalPen);
        parcel.writeString(jumlahPen);
        parcel.writeString(kondisiPen);
        parcel.writeString(divisiPen);
        parcel.writeString(namaAsetPen);
    }
}
