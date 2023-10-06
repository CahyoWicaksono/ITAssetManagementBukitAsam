package com.example.itassetmanagementptbukitasam.model;

import android.os.Parcel;
import android.os.Parcelable;

public class KerusakanLogistikModel implements Parcelable {

    String namaKerusakan, kodeasetKerusakan, tanggalKerusakan, tipeKerusakan,
            tanggalPurchaseKerusakan, solusiKerusakan,damageLogistikID;

    KerusakanLogistikModel(){

    }
    public KerusakanLogistikModel(String namaKerusakan, String kodeasetKerusakan, String tanggalKerusakan, String tipeKerusakan, String tanggalPurchaseKerusakan, String solusiKerusakan, String damageLogistikID) {
        this.namaKerusakan = namaKerusakan;
        this.kodeasetKerusakan = kodeasetKerusakan;
        this.tanggalKerusakan = tanggalKerusakan;
        this.tipeKerusakan = tipeKerusakan;
        this.tanggalPurchaseKerusakan = tanggalPurchaseKerusakan;
        this.solusiKerusakan = solusiKerusakan;
        this.damageLogistikID = damageLogistikID;
    }

    protected KerusakanLogistikModel(Parcel in) {
        namaKerusakan= in.readString();
        kodeasetKerusakan= in.readString();
        tanggalKerusakan= in.readString();
        tipeKerusakan= in.readString();
        tanggalPurchaseKerusakan= in.readString();
        solusiKerusakan = in.readString();
        damageLogistikID = in.readString();
    }

    public String getNamaKerusakan() {
        return namaKerusakan;
    }

    public void setNamaKerusakan(String namaKerusakan) {
        this.namaKerusakan = namaKerusakan;
    }

    public String getKodeasetKerusakan() {
        return kodeasetKerusakan;
    }

    public void setKodeasetKerusakan(String kodeasetKerusakan) {
        this.kodeasetKerusakan = kodeasetKerusakan;
    }

    public String getTanggalKerusakan() {
        return tanggalKerusakan;
    }

    public void setTanggalKerusakan(String tanggalKerusakan) {
        this.tanggalKerusakan = tanggalKerusakan;
    }

    public String getTipeKerusakan() {
        return tipeKerusakan;
    }

    public void setTipeKerusakan(String tipeKerusakan) {
        this.tipeKerusakan = tipeKerusakan;
    }

    public String getTanggalPurchaseKerusakan() {
        return tanggalPurchaseKerusakan;
    }

    public void setTanggalPurchaseKerusakan(String tanggalPurchaseKerusakan) {
        this.tanggalPurchaseKerusakan = tanggalPurchaseKerusakan;
    }

    public String getSolusiKerusakan() {
        return solusiKerusakan;
    }

    public void setSolusiKerusakan(String solusiKerusakan) {
        this.solusiKerusakan = solusiKerusakan;
    }

    public String getDamageLogistikID() {
        return damageLogistikID;
    }

    public void setDamageLogistikID(String damageLogistikID) {
        this.damageLogistikID = damageLogistikID;
    }


    public static final Creator<KerusakanLogistikModel> CREATOR = new Creator<KerusakanLogistikModel>() {
        @Override
        public KerusakanLogistikModel createFromParcel(Parcel in) {
            return new KerusakanLogistikModel(in);
        }

        @Override
        public KerusakanLogistikModel[] newArray(int size) {
            return new KerusakanLogistikModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(namaKerusakan);
        parcel.writeString(kodeasetKerusakan);
        parcel.writeString(tanggalKerusakan);
        parcel.writeString(tipeKerusakan);
        parcel.writeString(tanggalPurchaseKerusakan);
        parcel.writeString(solusiKerusakan);
        parcel.writeString(damageLogistikID);

    }
}
