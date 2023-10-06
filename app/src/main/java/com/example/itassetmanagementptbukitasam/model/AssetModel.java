package com.example.itassetmanagementptbukitasam.model;

import android.os.Parcel;
import android.os.Parcelable;

public class AssetModel implements Parcelable {
    String kode_aset, nama_aset, jenis, merk, spesifikasi, supplier,purchase,period_awa,period_akhir, jumlah, assetID, assetLink;

    AssetModel(){

    }
    public AssetModel (String kode_aset,String purchase,String assetID, String period_awa,String period_akhir, String supplier, String nama_aset,String jenis, String merk, String spesifikasi, String jumlah, String assetLink) {
        this.kode_aset = kode_aset;
        this.nama_aset = nama_aset;
        this.jenis = jenis;
        this.merk = merk;
        this.spesifikasi = spesifikasi;
        this.jumlah = jumlah;
        this.supplier = supplier;
        this.purchase = purchase;
        this.period_awa = period_awa;
        this.period_akhir = period_akhir;
        this.assetLink = assetLink;
        this.assetID = assetID;
    }

    protected AssetModel(Parcel in) {
        kode_aset = in.readString();
        nama_aset = in.readString();
        jenis = in.readString();
        merk = in.readString();
        spesifikasi = in.readString();
        supplier = in.readString();
        purchase = in.readString();
        period_awa = in.readString();
        period_akhir = in.readString();
        jumlah = in.readString();
        assetLink = in.readString();
    }

    public static final Creator<AssetModel> CREATOR = new Creator<AssetModel>() {
        @Override
        public AssetModel createFromParcel(Parcel in) {
            return new AssetModel(in);
        }

        @Override
        public AssetModel[] newArray(int size) {
            return new AssetModel[size];
        }
    };
    public String getKode_aset() {
        return kode_aset;
    }

    public void setKode_aset(String kode_aset) {
        this.kode_aset = kode_aset;
    }

    public String getNama_aset() {
        return nama_aset;
    }

    public void setNama_aset(String nama_aset) {
        this.nama_aset = nama_aset;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getSpesifikasi() {
        return spesifikasi;
    }

    public void setSpesifikasi(String spesifikasi) {
        this.spesifikasi = spesifikasi;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getPurchase() {
        return purchase;
    }

    public void setPurchase(String purchase) {
        this.purchase = purchase;
    }

    public String getPeriod_awa() {
        return period_awa;
    }

    public void setPeriod_awa(String period_awa) {
        this.period_awa = period_awa;
    }

    public String getPeriod_akhir() {
        return period_akhir;
    }

    public void setPeriod_akhir(String period_akhir) {
        this.period_akhir = period_akhir;
    }

    public String getAssetID() {
        return assetID;
    }

    public void setAssetID(String assetID) {
        this.assetID = assetID;
    }

    public String getAssetLink() {
        return assetLink;
    }

    public void setAssetLink(String assetLink) {
        this.assetID = assetLink;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(kode_aset);
        parcel.writeString(nama_aset);
        parcel.writeString(jenis);
        parcel.writeString(merk);
        parcel.writeString(spesifikasi);
        parcel.writeString(supplier);
        parcel.writeString(jumlah);
        parcel.writeString(purchase);
        parcel.writeString(period_awa);
        parcel.writeString(period_akhir);
        parcel.writeString(assetID);
        parcel.writeString(assetLink);

    }
}
