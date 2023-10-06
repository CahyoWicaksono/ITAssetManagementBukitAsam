package com.example.itassetmanagementptbukitasam.model;

import java.util.List;

public class ResponseUser {
    private int kode;

    public int getKode() {
        return kode;
    }

    public void setKode(int kode) {
        this.kode = kode;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    private String pesan;

    public List<OfficerModel>getData(){
        return data;
    }
    public void setData(List<OfficerModel> data){
        this.data = getData();
    }
    private List<OfficerModel> data;
}
