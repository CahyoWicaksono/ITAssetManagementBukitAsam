package com.example.itassetmanagementptbukitasam.model;

import android.os.Parcel;
import android.os.Parcelable;

public class RollingModel implements Parcelable {

    String namaAssetRoll, kodeAssetRoll, divisiRoll, tanggalRoll, jumlahRoll, descRoll, rollingID, rollingLink;

    RollingModel(){

    }

    public RollingModel(String namaAssetRoll, String kodeAssetRoll, String divisiRoll, String tanggalRoll, String jumlahRoll, String descRoll, String rollingID, String rollingLink) {
        this.namaAssetRoll = namaAssetRoll;
        this.kodeAssetRoll = kodeAssetRoll;
        this.divisiRoll = divisiRoll;
        this.tanggalRoll = tanggalRoll;
        this.jumlahRoll = jumlahRoll;
        this.descRoll = descRoll;
        this.rollingID = rollingID;
        this.rollingLink = rollingLink;
    }



    public String getNamaAssetRoll() {
        return namaAssetRoll;
    }

    public void setNamaAssetRoll(String namaAssetRoll) {
        this.namaAssetRoll = namaAssetRoll;
    }

    public String getKodeAssetRoll() {
        return kodeAssetRoll;
    }

    public void setKodeAssetRoll(String kodeAssetRoll) {
        this.kodeAssetRoll = kodeAssetRoll;
    }

    public String getDivisiRoll() {
        return divisiRoll;
    }

    public void setDivisiRoll(String divisiRoll) {
        this.divisiRoll = divisiRoll;
    }

    public String getTanggalRoll() {
        return tanggalRoll;
    }

    public void setTanggalRoll(String tanggalRoll) {
        this.tanggalRoll = tanggalRoll;
    }

    public String getJumlahRoll() {
        return jumlahRoll;
    }

    public void setJumlahRoll(String jumlahRoll) {
        this.jumlahRoll = jumlahRoll;
    }

    public String getDescRoll() {
        return descRoll;
    }

    public void setDescRoll(String descRoll) {
        this.descRoll = descRoll;
    }

    public String getRollingID() {
        return rollingID;
    }

    public void setRollingID(String rollingID) {
        this.rollingID = rollingID;
    }

    public String getRollingLink() {
        return rollingLink;
    }

    public void setRollingLink(String rollingLink) {
        this.rollingLink = rollingLink;
    }

    protected RollingModel(Parcel in) {
        namaAssetRoll = in.readString();
        kodeAssetRoll = in.readString();
        divisiRoll = in.readString();
        tanggalRoll = in.readString();
        jumlahRoll = in.readString();
        descRoll = in.readString();
        rollingID = in.readString();
        rollingLink = in.readString();
    }

    public static final Creator<RollingModel> CREATOR = new Creator<RollingModel>() {
        @Override
        public RollingModel createFromParcel(Parcel in) {
            return new RollingModel(in);
        }

        @Override
        public RollingModel[] newArray(int size) {
            return new RollingModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(namaAssetRoll);
        parcel.writeString(kodeAssetRoll);
        parcel.writeString(divisiRoll);
        parcel.writeString(tanggalRoll);
        parcel.writeString(jumlahRoll);
        parcel.writeString(descRoll);
        parcel.writeString(rollingID);
        parcel.writeString(rollingLink);


    }
}
