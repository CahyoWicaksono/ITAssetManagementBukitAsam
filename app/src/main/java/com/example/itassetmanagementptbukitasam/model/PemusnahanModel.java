package com.example.itassetmanagementptbukitasam.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PemusnahanModel implements Parcelable {
    String namaAsetPem,typeAsetPem,kategoriPem,kodeAssetPem,linkImageDestruction, tanggalPem, keteranganPem,pemusnahanID,jumlahPem;

    PemusnahanModel (){

    }

    public PemusnahanModel(String namaAsetPem,String jumlahPem,String kodeAssetPem,String linkImageDestruction, String pemusnahanID,String typeAsetPem, String kategoriPem, String tanggalPem, String keteranganPem) {
        this.namaAsetPem = namaAsetPem;
        this.typeAsetPem = typeAsetPem;
        this.kategoriPem = kategoriPem;
        this.tanggalPem = tanggalPem;
        this.keteranganPem = keteranganPem;
        this.pemusnahanID = pemusnahanID;
        this.jumlahPem  = jumlahPem;
        this.kodeAssetPem = kodeAssetPem;
        this.linkImageDestruction = linkImageDestruction;

    }



    public String getNamaAsetPem() {
        return namaAsetPem;
    }

    public void setNamaAsetPem(String namaAsetPem) {
        this.namaAsetPem = namaAsetPem;
    }

    public String getLinkImageDestruction() {
        return linkImageDestruction;
    }

    public void setLinkImageDestruction(String linkImageDestruction) {
        this.linkImageDestruction =linkImageDestruction;
    }







    public String getJumlahPem() {
        return jumlahPem;
    }

    public void setJumlahPem(String jumlahPem) {
        this.jumlahPem = jumlahPem;
    }




    public String getKodeAssetPem() {
        return kodeAssetPem;
    }

    public void setKodeAssetPem(String kodeAssetPem) {
        this.kodeAssetPem = kodeAssetPem;
    }


    public String getPemusnahanID() {
        return pemusnahanID;
    }

    public void setPemusnahanID(String pemusnahanID) {
        this.pemusnahanID = pemusnahanID;
    }



    public String getTypeAsetPem() {
        return typeAsetPem;
    }

    public void setTypeAsetPem(String typeAsetPem) {
        this.typeAsetPem = typeAsetPem;
    }

    public String getkategoriPem() {
        return kategoriPem;
    }

    public void setkategoriPem(String kategoriPem) {
        this.kategoriPem = kategoriPem;
    }

    public String getTanggalPem() {
        return tanggalPem;
    }

    public void setTanggalPem(String tanggalPem) {
        this.tanggalPem = tanggalPem;
    }

    public String getKeteranganPem() {
        return keteranganPem;
    }

    public void setKeteranganPem(String keteranganPem) {
        this.keteranganPem = keteranganPem;
    }

    protected PemusnahanModel(Parcel in) {
        namaAsetPem = in.toString();
        typeAsetPem = in.toString();
        kategoriPem = in.toString();
        tanggalPem = in.toString();
        keteranganPem = in.toString();
        pemusnahanID = in.toString();
        jumlahPem = in.toString();
        kodeAssetPem = in.toString();
        linkImageDestruction = in.toString();
    }

    public static final Creator<PemusnahanModel> CREATOR = new Creator<PemusnahanModel>() {
        @Override
        public PemusnahanModel createFromParcel(Parcel in) {
            return new PemusnahanModel(in);
        }

        @Override
        public PemusnahanModel[] newArray(int size) {
            return new PemusnahanModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(namaAsetPem);
        parcel.writeString(typeAsetPem);
        parcel.writeString(kategoriPem);
        parcel.writeString(tanggalPem);
        parcel.writeString(keteranganPem);
        parcel.writeString(pemusnahanID);
        parcel.writeString(jumlahPem);
        parcel.writeString(kodeAssetPem);
        parcel.writeString(linkImageDestruction);

    }
}
