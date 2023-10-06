package com.example.itassetmanagementptbukitasam.model;

public class dataitem {
    String nama_aset, satuan, keterangan;
    int jumlah;

    public dataitem(String nama_aset, int jumlah, String satuan, String keterangan) {
        this.nama_aset       = nama_aset;
        this.jumlah     = jumlah;
        this.satuan     = satuan;
        this.keterangan = keterangan;
    }

    public String getNama_aset() {
        return this.nama_aset;
    }

    public int getJumlah() {
        return this.jumlah;
    }

    public String getSatuan() {
        return this.satuan;
    }

    public String getKeterangan() {
        return this.keterangan;
    }
}
