package com.example.itassetmanagementptbukitasam.model;

import java.util.List;

public class ResponseAssets {
    private int kode;
    private String pesan;

    public int getKode() { return kode; }

    public void setKode(int kode){ this.kode = kode; }

    public String getPesan() {
        return pesan;
    }

    public List<AssetModel> getData() {
        return data;
    }

    public void setData(List<AssetModel> data) {
        this.data = data;
    }

    private List<AssetModel> data;
}
