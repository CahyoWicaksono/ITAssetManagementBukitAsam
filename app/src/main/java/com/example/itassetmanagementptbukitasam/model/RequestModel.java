package com.example.itassetmanagementptbukitasam.model;

import android.os.Parcel;
import android.os.Parcelable;

public class RequestModel implements Parcelable {

    String requestid,kodeAsetRequest, nameRequest, divisionRequest, amountRequest, dateRequest, descRequest, linkrequest;


    RequestModel(){

    }

    public RequestModel(String requestid, String kodeAsetRequest, String linkrequest,  String nameRequest, String divisionRequest, String amountRequest, String dateRequest, String descRequest) {
        this.requestid = requestid;
        this.nameRequest = nameRequest;
        this.divisionRequest = divisionRequest;
        this.amountRequest = amountRequest;
        this.dateRequest = dateRequest;
        this.descRequest = descRequest;
        this.kodeAsetRequest = kodeAsetRequest;
        this.linkrequest = linkrequest;
    }

    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }

    public String getNameRequest() {
        return nameRequest;
    }

    public void setNameRequest(String nameRequest) {
        this.nameRequest = nameRequest;
    }

    public String getKodeAsetRequest() {
        return kodeAsetRequest;
    }

    public void setKodeAsetRequest(String kodeAsetRequest) {
        this.kodeAsetRequest = kodeAsetRequest;
    }

    public String getLinkrequest() {
        return nameRequest;
    }

    public void setLinkrequest(String linkrequest) {
        this.linkrequest = linkrequest;
    }

    public String getDivisionRequest() {
        return divisionRequest;
    }

    public void setDivisionRequest(String divisionRequest) {
        this.divisionRequest = divisionRequest;
    }

    public String getAmountRequest() {
        return amountRequest;
    }

    public void setAmountRequest(String amountRequest) {
        this.amountRequest = amountRequest;
    }

    public String getDateRequest() {
        return dateRequest;
    }

    public void setDateRequest(String dateRequest) {
        this.dateRequest = dateRequest;
    }

    public String getDescRequest() {
        return descRequest;
    }

    public void setDescRequest(String descRequest) {
        this.descRequest = descRequest;
    }


    protected RequestModel(Parcel in) {
        requestid = in.toString();
        nameRequest = in.toString();
        divisionRequest = in.toString();
        amountRequest = in.toString();
        dateRequest = in.toString();
        descRequest = in.toString();
        kodeAsetRequest = in.toString();
        linkrequest = in.toString();
    }

    public static final Creator<RequestModel> CREATOR = new Creator<RequestModel>() {
        @Override
        public RequestModel createFromParcel(Parcel in) {
            return new RequestModel(in);
        }

        @Override
        public RequestModel[] newArray(int size) {
            return new RequestModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(requestid);
        parcel.writeString(nameRequest);
        parcel.writeString(divisionRequest);
        parcel.writeString(amountRequest);
        parcel.writeString(dateRequest);
        parcel.writeString(descRequest);
        parcel.writeString(kodeAsetRequest);
        parcel.writeString(linkrequest);
    }
}
