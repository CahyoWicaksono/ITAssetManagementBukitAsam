package com.example.itassetmanagementptbukitasam.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ResponseModel implements Parcelable {


    String namaResponse, namaAssetResponse, divisiResponse, tanggalResponse, statusResponse,responseID;

    ResponseModel(){

    }

    public ResponseModel(String namaResponse,String responseID, String namaAssetResponse, String divisiResponse, String tanggalResponse, String statusResponse) {
        this.namaResponse = namaResponse;
        this.namaAssetResponse = namaAssetResponse;
        this.divisiResponse = divisiResponse;
        this.tanggalResponse = tanggalResponse;
        this.statusResponse = statusResponse;
        this.responseID = responseID;
    }

    public String getNamaResponse() {
        return namaResponse;
    }

    public void setNamaResponse(String namaResponse) {
        this.namaResponse = namaResponse;
    }

    public String getNamaAssetResponse() {
        return namaAssetResponse;
    }

    public void setNamaAssetResponse(String namaAssetResponse) {
        this.namaAssetResponse = namaAssetResponse;
    }

    public String getDivisiResponse() {
        return divisiResponse;
    }

    public void setDivisiResponse(String divisiResponse) {
        this.divisiResponse = divisiResponse;
    }

    public String getTanggalResponse() {
        return tanggalResponse;
    }

    public void setTanggalResponse(String tanggalResponse) {
        this.tanggalResponse = tanggalResponse;
    }

    public String getStatusResponse() {
        return statusResponse;
    }

    public void setStatusResponse(String statusResponse) {
        this.statusResponse = statusResponse;
    }

    public String getResponseID() {
        return responseID;
    }

    public void setResponseID(String responseID) {
        this.responseID = responseID;
    }



    protected ResponseModel(Parcel in) {
        namaAssetResponse = in.readString();in.readString();
        namaResponse = in.readString();
        divisiResponse = in.readString();
        tanggalResponse = in.readString();
        statusResponse = in.toString();
        responseID = in.toString();
    }

    public static final Creator<ResponseModel> CREATOR = new Creator<ResponseModel>() {
        @Override
        public ResponseModel createFromParcel(Parcel in) {
            return new ResponseModel(in);
        }

        @Override
        public ResponseModel[] newArray(int size) {
            return new ResponseModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(namaAssetResponse);
        parcel.writeString(namaResponse);
        parcel.writeString(divisiResponse);
        parcel.writeString(tanggalResponse);
        parcel.writeString(statusResponse);
        parcel.writeString(responseID);
    }
}
