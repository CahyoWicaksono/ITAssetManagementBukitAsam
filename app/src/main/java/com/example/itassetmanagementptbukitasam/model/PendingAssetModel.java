package com.example.itassetmanagementptbukitasam.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PendingAssetModel implements Parcelable {


    String pendingID,namaAsetPending, kodeAsetPending, tanggalAsetPending, kategoriPending, supplierPending, jumlahAsetPending, keteranganPending;

    PendingAssetModel (){

    }

    public PendingAssetModel(String pendingID, String namaAsetPending, String kodeAsetPending, String keteranganPending,String jumlahAsetPending, String tanggalAsetPending, String kategoriPending, String supplierPending) {
        this.namaAsetPending = namaAsetPending;
        this.kodeAsetPending = kodeAsetPending;
        this.tanggalAsetPending = tanggalAsetPending;
        this.kategoriPending = kategoriPending;
        this.supplierPending = supplierPending;
        this.jumlahAsetPending = jumlahAsetPending;
        this.keteranganPending = keteranganPending;
        this.pendingID  = pendingID;
    }
    protected PendingAssetModel(Parcel in) {
        namaAsetPending = in.toString();
        kodeAsetPending = in.toString();
        tanggalAsetPending = in.toString();
        kategoriPending = in.toString();
        supplierPending = in.toString();
        jumlahAsetPending = in.toString();
        keteranganPending = in.toString();
        pendingID = in.toString();
    }
    public static final Creator<PendingAssetModel> CREATOR = new Creator<PendingAssetModel>() {
        @Override
        public PendingAssetModel createFromParcel(Parcel in) {
            return new PendingAssetModel(in);
        }

        @Override
        public PendingAssetModel[] newArray(int size) {
            return new PendingAssetModel[size];
        }
    };


    public String getNamaAsetPending() {
        return namaAsetPending;
    }

    public void setNamaAsetPending(String namaAsetPending) {
        this.namaAsetPending = namaAsetPending;
    }

    public String getPendingID() {
        return pendingID;
    }

    public void setPendingID(String pendingID) {
        this.pendingID = pendingID;
    }

    public String getKeteranganPending() {
        return keteranganPending;
    }

    public void setKeteranganPending(String keteranganPending) {
        this.keteranganPending = keteranganPending;
    }

    public String getJumlahAsetPending() {
        return jumlahAsetPending;
    }

    public void setJumlahAsetPending(String jumlahAsetPending) {
        this.jumlahAsetPending = jumlahAsetPending;
    }


    public String getKodeAsetPending() {
        return kodeAsetPending;
    }

    public void setKodeAsetPending(String kodeAsetPending) {
        this.kodeAsetPending = kodeAsetPending;
    }

    public String getTanggalAsetPending() {
        return tanggalAsetPending;
    }

    public void setTanggalAsetPending(String tanggalAsetPending) {
        this.tanggalAsetPending = tanggalAsetPending;
    }

    public String getKategoriPending() {
        return kategoriPending;
    }

    public void setKategoriPending(String kategoriPending) {
        this.kategoriPending = kategoriPending;
    }

    public String getSupplierPending() {
        return supplierPending;
    }

    public void setSupplierPending(String supplierPending) {
        this.supplierPending = supplierPending;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(namaAsetPending);
        parcel.writeString(kodeAsetPending);
        parcel.writeString(tanggalAsetPending);
        parcel.writeString(kategoriPending);
        parcel.writeString(supplierPending);
        parcel.writeString(jumlahAsetPending);
        parcel.writeString(keteranganPending);
        parcel.writeString(pendingID);

    }
}
