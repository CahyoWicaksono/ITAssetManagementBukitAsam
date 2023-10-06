package com.example.itassetmanagementptbukitasam.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CheckingModel implements Parcelable {

    String namaAsetCheck, kodeAsetCheck, tanggalCheck, statusCheck, maintenanceCheck,typeCheck, lokasiCheck,petugasCheck, checkID;

    CheckingModel(){

    }

    public CheckingModel(String namaAsetCheck, String kodeAsetCheck, String tanggalCheck,  String typeCheck,  String statusCheck, String maintenanceCheck, String lokasiCheck, String petugasCheck, String checkID) {
        this.namaAsetCheck = namaAsetCheck;
        this.kodeAsetCheck = kodeAsetCheck;
        this.tanggalCheck = tanggalCheck;
        this.statusCheck = statusCheck;
        this.maintenanceCheck = maintenanceCheck;
        this.lokasiCheck = lokasiCheck;
        this.petugasCheck = petugasCheck;
        this.typeCheck = typeCheck;
        this.checkID = checkID;
    }

    public String getNamaAsetCheck() {
        return namaAsetCheck;
    }

    public void setNamaAsetCheck(String namaAsetCheck) {
        this.namaAsetCheck = namaAsetCheck;
    }

    public String getKodeAsetCheck() {
        return kodeAsetCheck;
    }

    public void setKodeAsetCheck(String kodeAsetCheck) {
        this.kodeAsetCheck = kodeAsetCheck;
    }

    public String getTanggalCheck() {
        return tanggalCheck;
    }

    public void setTanggalCheck(String tanggalCheck) {
        this.tanggalCheck = tanggalCheck;
    }

    public String getStatusCheck() {
        return statusCheck;
    }

    public void setStatusCheck(String statusCheck) {
        this.statusCheck = statusCheck;
    }

    public String getMaintenanceCheck() {
        return maintenanceCheck;
    }

    public void setMaintenanceCheck(String maintenanceCheck) {
        this.maintenanceCheck = maintenanceCheck;
    }

    public String getLokasiCheck() {
        return lokasiCheck;
    }

    public void setLokasiCheck(String lokasiCheck) {
        this.lokasiCheck = lokasiCheck;
    }

    public String getPetugasCheck() {
        return petugasCheck;
    }

    public void setPetugasCheck(String petugasCheck) {
        this.petugasCheck = petugasCheck;
    }

    public String getCheckID() {
        return checkID;
    }

    public void setCheckID(String checkID) {
        this.checkID = checkID;
    }

    public String getTypeCheck() {
        return typeCheck;
    }

    public void setTypeCheck(String typeCheck) {
        this.typeCheck = typeCheck;
    }


    protected CheckingModel(Parcel in) {
        namaAsetCheck = in.readString();
        kodeAsetCheck = in.readString();
        tanggalCheck = in.readString();
        statusCheck = in.readString();
        maintenanceCheck = in.readString();
        lokasiCheck = in.readString();
        petugasCheck = in.readString();
        typeCheck = in.readString();
        checkID = in.readString();

    }

    public static final Creator<CheckingModel> CREATOR = new Creator<CheckingModel>() {
        @Override
        public CheckingModel createFromParcel(Parcel in) {
            return new CheckingModel(in);
        }

        @Override
        public CheckingModel[] newArray(int size) {
            return new CheckingModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(namaAsetCheck);
        parcel.writeString(kodeAsetCheck);
        parcel.writeString(tanggalCheck);
        parcel.writeString(statusCheck);
        parcel.writeString(maintenanceCheck);
        parcel.writeString(lokasiCheck);
        parcel.writeString(petugasCheck);
        parcel.writeString(typeCheck);
        parcel.writeString(checkID);
    }
}
