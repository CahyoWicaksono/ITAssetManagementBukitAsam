package com.example.itassetmanagementptbukitasam.model;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderModel implements Parcelable {
    String namaAsetOrder, kodeAsetOrder, jenisAsetOrder, tanggalOrder, divisiOrder, orderID, jumlahOrder;

    OrderModel(){

    }
    public OrderModel(String namaAsetOrder, String kodeAsetOrder, String jenisAsetOrder, String tanggalOrder, String divisiOrder, String orderID, String jumlahOrder) {
        this.namaAsetOrder = namaAsetOrder;
        this.kodeAsetOrder = kodeAsetOrder;
        this.jenisAsetOrder = jenisAsetOrder;
        this.tanggalOrder = tanggalOrder;
        this.divisiOrder = divisiOrder;
        this.orderID = orderID;
        this.jumlahOrder = jumlahOrder;
    }
    protected OrderModel(Parcel in) {
        namaAsetOrder  = in.readString();
        kodeAsetOrder  = in.readString();
        jenisAsetOrder  = in.readString();
        tanggalOrder  = in.readString();
        divisiOrder  = in.readString();
        orderID  = in.readString();
        jumlahOrder  = in.readString();
    }

    public static final Creator<OrderModel> CREATOR = new Creator<OrderModel>() {
        @Override
        public OrderModel createFromParcel(Parcel in) {
            return new OrderModel(in);
        }

        @Override
        public OrderModel[] newArray(int size) {
            return new OrderModel[size];
        }
    };

    public String getNamaAsetOrder() {
        return namaAsetOrder;
    }

    public void setNamaAsetOrder(String namaAsetOrder) {
        this.namaAsetOrder = namaAsetOrder;
    }

    public String getKodeAsetOrder() {
        return kodeAsetOrder;
    }

    public void setKodeAsetOrder(String kodeAsetOrder) {
        this.kodeAsetOrder = kodeAsetOrder;
    }

    public String getJenisAsetOrder() {
        return jenisAsetOrder;
    }

    public void setJenisAsetOrder(String jenisAsetOrder) {
        this.jenisAsetOrder = jenisAsetOrder;
    }

    public String getTanggalOrder() {
        return tanggalOrder;
    }

    public void setTanggalOrder(String tanggalOrder) {
        this.tanggalOrder = tanggalOrder;
    }

    public String getDivisiOrder() {
        return divisiOrder;
    }

    public void setDivisiOrder(String divisiOrder) {
        this.divisiOrder = divisiOrder;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getJumlahOrder() {
        return jumlahOrder;
    }

    public void setJumlahOrder(String jumlahOrder) {
        this.jumlahOrder = jumlahOrder;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(namaAsetOrder);
        parcel.writeString(kodeAsetOrder);
        parcel.writeString(jenisAsetOrder);
        parcel.writeString(tanggalOrder);
        parcel.writeString(divisiOrder);
        parcel.writeString(orderID);
        parcel.writeString(jumlahOrder);

    }
}
