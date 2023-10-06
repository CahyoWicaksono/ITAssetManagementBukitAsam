package com.example.itassetmanagementptbukitasam.model;


import android.os.Parcel;
import android.os.Parcelable;

public class SoftwareModel implements Parcelable {
    String software_name, brand_name,date_purchase, exparation, product_key, softwareid;

    SoftwareModel(){

    }

    public SoftwareModel(String product_key, String exparation, String date_purchase, String software_name, String brand_name, String softwareid) {
        this.software_name = software_name;
        this.brand_name = brand_name;
        this.date_purchase = date_purchase;
        this.exparation = exparation;
        this.product_key = product_key;
        this.softwareid = softwareid;
    }


    protected SoftwareModel(Parcel in) {
        software_name = in.readString();
        brand_name = in.readString();
        date_purchase = in.readString();
        exparation = in.readString();
        product_key = in.readString();
        softwareid = in.readString();

    }

    public static final Creator<SoftwareModel> CREATOR = new Creator<SoftwareModel>() {
        @Override
        public SoftwareModel createFromParcel(Parcel in) {
            return new SoftwareModel(in);
        }

        @Override
        public SoftwareModel[] newArray(int size) {
            return new SoftwareModel[size];
        }
    };

    public String getSoftware_name() {
        return software_name;
    }

    public void setSoftware_name(String software_name) {
        this.software_name = software_name;
    }

    public String getSoftwareid() {
        return softwareid;
    }

    public void setSoftwareid(String softwareid) {
        this.softwareid = softwareid;
    }


    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getDate_purchase() {
        return date_purchase;
    }

    public void setDate_purchase(String date_purchase) {
        this.date_purchase = date_purchase;
    }

    public String getExparation() {
        return exparation;
    }

    public void setExparation(String exparation) {
        this.exparation = exparation;
    }

    public String getProduct_key() {
        return product_key;
    }

    public void setProduct_key(String product_key) {
        this.product_key = product_key;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(software_name);
        parcel.writeString(brand_name);
        parcel.writeString(date_purchase);
        parcel.writeString(exparation);
        parcel.writeString(product_key);
        parcel.writeString(softwareid);
    }
}
