package com.example.itassetmanagementptbukitasam.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ComplaintModel  implements Parcelable {
    String complaintID;
    String complaintName;
    String dateComplaint;
    String complaintDivision;
    String complaintAsset;
    String descriptionComplaint;


    ComplaintModel(){

    }

    public ComplaintModel(String complaintID,String complaintName,String dateComplaint, String complaintDivision, String complaintAsset, String descriptionComplaint) {
        this.complaintName = complaintName;
        this.complaintID = complaintID;
        this.dateComplaint = dateComplaint;
        this.complaintDivision = complaintDivision;
        this.complaintAsset = complaintAsset;
        this.descriptionComplaint = descriptionComplaint;
    }


    protected ComplaintModel(Parcel in) {
        complaintID = in.readString();
        complaintAsset = in.readString();
        dateComplaint = in.readString();
        complaintDivision = in.readString();
        complaintAsset = in.readString();
        descriptionComplaint = in.readString();
    }

    public static final Creator<ComplaintModel> CREATOR = new Creator<ComplaintModel>() {
        @Override
        public ComplaintModel createFromParcel(Parcel in) {
            return new ComplaintModel(in);
        }

        @Override
        public ComplaintModel[] newArray(int size) {
            return new ComplaintModel[size];
        }
    };

    public String getComplaintID() {
        return complaintID;
    }

    public void getComplaintID(String complaintID) {
        this.complaintID = complaintID;
    }


    public String getComplaintName() {
        return complaintName;
    }

    public void setComplaintName(String complaintName) {
        this.complaintName = complaintName;
    }


    public String getDateComplaint() {
        return dateComplaint;
    }

    public void setDateComplaint(String dateComplaint) {
        this.dateComplaint = dateComplaint;
    }





    public String getComplaintDivision() {
        return complaintDivision;
    }

    public void setComplaintDivision(String complaintDivision) {
        this.complaintDivision = complaintDivision;
    }

    public String getComplaintAsset() {
        return complaintAsset;
    }

    public void setComplaintAsset(String complaintAsset) {
        this.complaintAsset = complaintAsset;
    }

    public String getDescriptionComplaint() {
        return descriptionComplaint;
    }

    public void setDescriptionComplaint(String descriptionComplaint) {
        this.descriptionComplaint = descriptionComplaint;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(complaintName);
        parcel.writeString(dateComplaint);
        parcel.writeString(complaintAsset);
        parcel.writeString(complaintDivision);
        parcel.writeString(descriptionComplaint);

    }
}
