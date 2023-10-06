package com.example.itassetmanagementptbukitasam.model;

import android.os.Parcel;
import android.os.Parcelable;

public class LocationModel implements Parcelable {

    String locationID;
    String kodeAsetLocation;
    String namaAsetLocation;
    String jumlahHRD;
    String jumlahClinic;
    String jumlahOprasional;
    String jumlahSafety;
    String jumlahLogistik;
    String jumlahHumas;
    String jumlahLab;
    String statusAsset;

    LocationModel(){

    }
    public LocationModel(String locationID, String kodeAsetLocation, String namaAsetLocation, String jumlahHRD, String jumlahClinic, String jumlahOprasional, String jumlahSafety, String jumlahLogistik, String jumlahHumas, String jumlahLab, String statusAsset) {
        this.locationID = locationID;
        this.kodeAsetLocation = kodeAsetLocation;
        this.namaAsetLocation = namaAsetLocation;
        this.jumlahHRD = jumlahHRD;
        this.jumlahClinic = jumlahClinic;
        this.jumlahOprasional = jumlahOprasional;
        this.jumlahSafety = jumlahSafety;
        this.jumlahLogistik = jumlahLogistik;
        this.jumlahHumas = jumlahHumas;
        this.jumlahLab = jumlahLab;
        this.statusAsset = statusAsset;
    }

    protected LocationModel(Parcel in) {
        kodeAsetLocation = in.readString();
        namaAsetLocation = in.readString();
        locationID = in.readString();
        jumlahHRD = in.readString();
        jumlahClinic = in.readString();
        jumlahLab = in.readString();
        jumlahHumas = in.readString();
        jumlahLogistik = in.readString();
        jumlahOprasional = in.readString();
        jumlahSafety = in.readString();
        statusAsset = in.readString();
    }

    public static final Creator<LocationModel> CREATOR = new Creator<LocationModel>() {
        @Override
        public LocationModel createFromParcel(Parcel in) {
            return new LocationModel(in);
        }

        @Override
        public LocationModel[] newArray(int size) {
            return new LocationModel[size];
        }
    };
    public String getKodeAsetLocation() {
        return kodeAsetLocation;
    }

    public void setKodeAsetLocation(String kodeAsetLocation) {
        this.kodeAsetLocation = kodeAsetLocation;
    }

    public String getNamaAsetLocation() {
        return namaAsetLocation;
    }

    public void setNamaAsetLocation(String namaAsetLocation) {
        this.namaAsetLocation = namaAsetLocation;
    }

    public String getJumlahHRD() {
        return jumlahHRD;
    }

    public void setJumlahHRD(String jumlahHRD) {
        this.jumlahHRD = jumlahHRD;
    }

    public String getJumlahClinic() {
        return jumlahClinic;
    }

    public void setJumlahClinic(String jumlahClinic) {
        this.jumlahClinic = jumlahClinic;
    }

    public String getJumlahOprasional() {
        return jumlahOprasional;
    }

    public void setJumlahOprasional(String jumlahOprasional) {
        this.jumlahOprasional = jumlahOprasional;
    }

    public String getJumlahSafety() {
        return jumlahSafety;
    }

    public void setJumlahSafety(String jumlahSafety) {
        this.jumlahSafety = jumlahSafety;
    }

    public String getJumlahLogistik() {
        return jumlahLogistik;
    }

    public void setJumlahLogistik(String jumlahLogistik) {
        this.jumlahLogistik = jumlahLogistik;
    }

    public String getJumlahHumas() {
        return jumlahHumas;
    }

    public void setJumlahHumas(String jumlahHumas) {
        this.jumlahHumas = jumlahHumas;
    }

    public String getJumlahLab() {
        return jumlahLab;
    }

    public void setJumlahLab(String jumlahLab) {
        this.jumlahLab = jumlahLab;
    }

    public String getStatusAsset() {
        return statusAsset;
    }

    public void setStatusAsset(String statusAsset) {
        this.statusAsset = statusAsset;
    }

    public String getLocationID() {
        return locationID;
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString (kodeAsetLocation);
        parcel.writeString (namaAsetLocation);
        parcel.writeString (jumlahHRD);
        parcel.writeString (jumlahClinic);
        parcel.writeString (jumlahLab);
        parcel.writeString (jumlahHumas);
        parcel.writeString (jumlahLogistik);
        parcel.writeString (jumlahOprasional);
        parcel.writeString (jumlahSafety);
        parcel.writeString (statusAsset);
        parcel.writeString (locationID);
    }
}
