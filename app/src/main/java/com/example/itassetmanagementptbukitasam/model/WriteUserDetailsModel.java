package com.example.itassetmanagementptbukitasam.model;

public class WriteUserDetailsModel {

    public String Dob, gender, mobile;

    public WriteUserDetailsModel(){};

    public WriteUserDetailsModel( String textDob, String textGender, String textMobile) {
        this.Dob = textDob;
        this.gender = textGender;
        this.mobile = textMobile;

    }


}
