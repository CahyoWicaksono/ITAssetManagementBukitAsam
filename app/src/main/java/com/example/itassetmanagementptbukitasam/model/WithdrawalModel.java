package com.example.itassetmanagementptbukitasam.model;

import android.os.Parcel;
import android.os.Parcelable;

public class WithdrawalModel implements Parcelable {

   private String kode_penarikan, kode_aset, tanggal,withId, devisi,
           jumlah_penarikan, alasan , keterangan_penarikan, petugas;

   WithdrawalModel()
   {

   }

   public WithdrawalModel(String kode_penarikan, String withId,  String kode_aset, String tanggal, String  devisi, String jumlah_penarikan, String alasan, String keterangan_penarikan, String petugas) {
      this.kode_penarikan = kode_penarikan;
      this.kode_aset = kode_aset;
      this.tanggal = tanggal;
      this.alasan = alasan;
      this.withId = withId;
      this.devisi = devisi;
      this.jumlah_penarikan = jumlah_penarikan;
      this.petugas = petugas;
      this.keterangan_penarikan = keterangan_penarikan;

   }

   protected WithdrawalModel(Parcel in) {
      kode_penarikan = in.readString();
      kode_aset = in.readString();
      tanggal = in.readString();
      withId = in.readString();
      devisi = in.readString();
      jumlah_penarikan = in.readString();
      alasan = in.readString();
      keterangan_penarikan = in.readString();
      petugas = in.readString();
   }

   public static final Creator<WithdrawalModel> CREATOR = new Creator<WithdrawalModel>() {
      @Override
      public WithdrawalModel createFromParcel(Parcel in) {
         return new WithdrawalModel(in);
      }

      @Override
      public WithdrawalModel[] newArray(int size) {
         return new WithdrawalModel[size];
      }
   };

   public String getKode_penarikan() {
      return kode_penarikan;
   }

   public void setKode_penarikan(String kode_permintaan) {this.kode_penarikan = kode_penarikan;}

   public String getKode_aset() {
      return kode_aset;
   }

   public void setKode_aset(String kode_aset) {
      this.kode_aset = kode_aset;
   }

   public String getDevisi() {
      return devisi;
   }

   public void setDevisi(String devisi) {
      this.devisi = devisi;
   }

   public String getJumlah_penarikan() {
      return jumlah_penarikan;
   }

   public void setJumlah_penarikan(String jumlah_penarikan) {this.jumlah_penarikan = jumlah_penarikan; }

   public String getAlasan() {
      return alasan;
   }

   public void setAlasan(String alasan) {
      this.alasan = alasan;
   }

   public String getTanggal() {
      return tanggal;
   }

   public void setTanggal(String tanggal) {
      this.tanggal = tanggal;
   }

   public String getKeterangan_penarikan() {
      return keterangan_penarikan;
   }

   public void setKeterangan_penarikan(String keterangan_penarikan) {this.keterangan_penarikan = keterangan_penarikan;}

   public String getPetugas() {
      return petugas;
   }

   public void setPetugas(String petugas) {this.petugas = petugas;}




   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel parcel, int i) {
      parcel.writeString(kode_penarikan);
      parcel.writeString(kode_aset);
      parcel.writeString(tanggal);
      parcel.writeString(withId);
      parcel.writeString(devisi);
      parcel.writeString(jumlah_penarikan);
      parcel.writeString(alasan);
      parcel.writeString(keterangan_penarikan);
      parcel.writeString(petugas);
   }
}

