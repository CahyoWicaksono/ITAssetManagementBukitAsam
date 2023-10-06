package com.example.itassetmanagementptbukitasam.model;

import android.os.Parcel;
import android.os.Parcelable;

public class KerusakanModel implements Parcelable {

   String kode_kerusakan, kode_aset, tanggal, jenis_kerusakan, petugas, lokasi, solusi,penerapan,damageid;


   KerusakanModel(){

   }


   public KerusakanModel(String kode_kerusakan, String kode_aset,String damageid,String tanggal, String jenis_kerusakan, String petugas, String lokasi, String solusi, String penerapan) {
      this.kode_kerusakan = kode_kerusakan;
      this.kode_aset = kode_aset;
      this.tanggal = tanggal;
      this.jenis_kerusakan = jenis_kerusakan;
      this.petugas = petugas;
      this.lokasi = lokasi;
      this.solusi = solusi;
      this.penerapan = penerapan;
      this.damageid = damageid;
   }

   protected KerusakanModel(Parcel in) {
      kode_kerusakan = in.readString();
      kode_aset = in.readString();
      tanggal = in.readString();
      damageid = in.readString();
      jenis_kerusakan = in.readString();
      petugas = in.readString();
      lokasi = in.readString();
      solusi = in.readString();
      penerapan = in.readString();
   }

   public static final Creator<KerusakanModel> CREATOR = new Creator<KerusakanModel>() {
      @Override
      public KerusakanModel createFromParcel(Parcel in) {
         return new KerusakanModel(in);
      }

      @Override
      public KerusakanModel[] newArray(int size) {
         return new KerusakanModel[size];
      }
   };

   public String getKode_kerusakan() {
      return kode_kerusakan;
   }

   public void setKode_kerusakan(String kode_kerusakan) {
      this.kode_kerusakan = kode_kerusakan;
   }

   public String getDamageid() {
      return damageid;
   }

   public void setDamageid(String damageid) {
      this.damageid = damageid;
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

   public String getJenis_kerusakan() {
      return jenis_kerusakan;
   }

   public void setJenis_kerusakan(String jenis_kerusakan) {
      this.jenis_kerusakan = jenis_kerusakan; }

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

   public String getSolusi() {
      return solusi;
   }

   public void setSolusi(String solusi) {
      this.solusi = solusi;
   }

   public String getPenerapan() {
      return penerapan;
   }

   public void setPenerapan(String penerapan) {
      this.penerapan = penerapan;
   }


   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel parcel, int i) {
      parcel.writeString(kode_kerusakan);
      parcel.writeString(kode_aset);
      parcel.writeString(tanggal);
      parcel.writeString(jenis_kerusakan);
      parcel.writeString(petugas);
      parcel.writeString(lokasi);
      parcel.writeString(solusi);
      parcel.writeString(penerapan);
      parcel.writeString(damageid);
   }
}

