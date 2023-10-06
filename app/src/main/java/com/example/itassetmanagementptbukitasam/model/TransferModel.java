package com.example.itassetmanagementptbukitasam.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TransferModel implements Parcelable {

   String kode_transfer, kode_aset, tanggal, giver, receiver, devision,
           condition, status, description,transIF;

   TransferModel()
   {

   }

   public TransferModel(String kode_transfer, String kode_aset, String tanggal, String condition, String status, String description,
                        String giver, String receiver, String devision, String transIF) {
      this.kode_transfer = kode_transfer;
      this.kode_aset = kode_aset;
      this.tanggal = tanggal;
      this.giver = giver;
      this.receiver = receiver;
      this.devision = devision;
      this.condition = condition;
      this.status = status;
      this.description = description;
      this.transIF = transIF;

   }

   protected TransferModel(Parcel in) {
      kode_transfer = in.readString();
      kode_aset = in.readString();
      tanggal = in.readString();
      giver = in.readString();
      receiver = in.readString();
      devision = in.readString();
      condition = in.readString();
      status = in.readString();
      description = in.readString();
      transIF = in.readString();
   }

   public static final Creator<TransferModel> CREATOR = new Creator<TransferModel>() {
      @Override
      public TransferModel createFromParcel(Parcel in) {
         return new TransferModel(in);
      }

      @Override
      public TransferModel[] newArray(int size) {
         return new TransferModel[size];
      }
   };

   public String getKode_transfer() {
      return kode_transfer;
   }

   public void setKode_transfer(String kode_transfer) {
      this.kode_transfer = kode_transfer;
   }


   public String getTansIf() {
      return transIF;
   }

   public void setTransIF(String transIF) {
      this.transIF = transIF;
   }

   public String getKode_aset() {
      return kode_aset;
   }

   public void setKode_aset(String kode_aset) {
      this.kode_aset = kode_aset;
   }

   public String getTanggal() {
      return tanggal;
   }

   public void setTanggal(String tanggal) {
      this.tanggal = tanggal;
   }

   public String getGiver() {
      return giver;
   }

   public void setGiver(String giver) {
      this.giver = giver;
   }

   public String getReceiver() {
      return receiver;
   }

   public void setReceiver(String receiver) { this.receiver = receiver; }

   public String getDevision() {
      return devision;
   }

   public void setDevision(String devision) {
      this.devision = devision;
   }

   public String getCondition() { return condition; }

   public void setCondition(String condition) { this.condition = condition; }

   public String getStatus() {
      return status;
   }

   public void setStatus(String status) {
      this.status = status;
   }


   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }


   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel parcel, int i) {
      parcel.writeString(kode_transfer);
      parcel.writeString(kode_aset);
      parcel.writeString(tanggal);
      parcel.writeString(giver);
      parcel.writeString(receiver);
      parcel.writeString(devision);
      parcel.writeString(condition);
      parcel.writeString(status);
      parcel.writeString(description);
      parcel.writeString(transIF);
   }
}

