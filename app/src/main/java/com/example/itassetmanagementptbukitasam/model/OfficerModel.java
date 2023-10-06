package com.example.itassetmanagementptbukitasam.model;

import android.os.Parcel;
import android.os.Parcelable;

public class OfficerModel implements Parcelable {
    String kode_petugas, email,no_telp, nama_petugas, Devisi, OfficerId, alamat_petugas;

    OfficerModel()
    {

    }

    public OfficerModel(String kode_petugas,String alamat_petugas, String email, String no_telp, String nama_petugas, String devisi,String officerId) {
        this.kode_petugas = kode_petugas;
        this.email = email;
        this.no_telp = no_telp;
        this.nama_petugas = nama_petugas;
        this.Devisi = devisi;
        this.OfficerId = officerId;
        this.alamat_petugas = alamat_petugas;
    }

    protected OfficerModel(Parcel in) {
        kode_petugas = in.readString();
        email = in.readString();
        no_telp = in.readString();
        nama_petugas = in.readString();
        Devisi = in.readString();
        OfficerId = in.readString();
        alamat_petugas = in.readString();
    }

    public static final Creator<OfficerModel> CREATOR = new Creator<OfficerModel>() {
        @Override
        public OfficerModel createFromParcel(Parcel in) {
            return new OfficerModel(in);
        }

        @Override
        public OfficerModel[] newArray(int size) {
            return new OfficerModel[size];
        }
    };

    public String getKode_petugas() {
        return kode_petugas;
    }

    public void setKode_petugas(String kode_petugas) {
        this.kode_petugas = kode_petugas;
    }


    public String getOfficerId() {return OfficerId;}

    public void setOfficerId(String officerId) {this.OfficerId = officerId;}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) { this.email = email; }

    public String getNo_telp() { return no_telp;}

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }

    public String getNama_petugas() {
        return nama_petugas;
    }

    public void setNama_petugas(String nama_petugas) {
        this.nama_petugas = nama_petugas;
    }

    public String getAlamat_petugas() {
        return alamat_petugas;
    }

    public void setAlamat_petugas(String alamat_petugas) {this.alamat_petugas = alamat_petugas;}


    public String getDevisi() {
        return Devisi;
    }

    public void setDevisi(String devisi) {
        Devisi = devisi;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(kode_petugas);
        parcel.writeString(email);
        parcel.writeString(no_telp);
        parcel.writeString(nama_petugas);
        parcel.writeString(Devisi);
        parcel.writeString(OfficerId);
        parcel.writeString(alamat_petugas);
    }
}

