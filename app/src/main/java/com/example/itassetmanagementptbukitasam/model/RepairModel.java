package com.example.itassetmanagementptbukitasam.model;

import android.os.Parcel;
import android.os.Parcelable;

public class RepairModel implements Parcelable {

   String kode_perbaikan, kode_aset, tanggal, petugas, lokasi, status,RepairID;

   RepairModel()
   {

   }

   public RepairModel(String kode_perbaikan,String kode_aset, String repairID, String tanggal, String petugas,String lokasi,String status ) {
      this.kode_perbaikan = kode_perbaikan;
      this.kode_aset = kode_aset;
      this.tanggal = tanggal;
      this.petugas = petugas;
      this.lokasi = lokasi;
      this.RepairID = repairID;
      this.status = status;

   }

   protected RepairModel(Parcel in) {
      kode_perbaikan = in.readString();
      kode_aset = in.readString();
      tanggal = in.readString();
      petugas = in.readString();
      lokasi = in.readString();
      status = in.readString();
      RepairID = in.readString();
   }

   public static final Creator<RepairModel> CREATOR = new Creator<RepairModel>() {
      @Override
      public RepairModel createFromParcel(Parcel in) {
         return new RepairModel(in);
      }

      @Override
      public RepairModel[] newArray(int size) {
         return new RepairModel[size];
      }
   };

   public String getKode_perbaikan() {
      return kode_perbaikan;
   }

   public void setKode_perbaikan(String kode_perbaikan) {
      this.kode_perbaikan = kode_perbaikan;
   }

   public String getRepairID() {
      return RepairID;
   }

   public void setRepairID(String repairID) {
      this.RepairID = repairID;
   }

   public String getKode_aset() {
      return kode_aset;
   }

   public void setKode_aset(String kode_aset) {
      this.kode_aset = kode_aset;
   }

   public String getPetugas() {
      return petugas;
   }

   public void setPetugas(String petugas) {
      this.petugas = petugas;
   }

   public String getLokasi() {
      return lokasi;
   }

   public void setLokasi(String lokasi) {
      this.lokasi = lokasi;
   }

   public String getTanggal() {
      return tanggal;
   }

   public void setTanggal(String tanggal) {
      this.tanggal = tanggal;
   }

   public String getStatus() {
      return status;
   }

   public void setStatus(String status) {
      this.status = status;
   }


   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel parcel, int i) {
      parcel.writeString(kode_perbaikan);
      parcel.writeString(kode_aset);
      parcel.writeString(tanggal);
      parcel.writeString(petugas);
      parcel.writeString(lokasi);
      parcel.writeString(status);
      parcel.writeString(RepairID);
   }
}

