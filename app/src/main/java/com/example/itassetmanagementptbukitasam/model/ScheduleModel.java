package com.example.itassetmanagementptbukitasam.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ScheduleModel implements Parcelable {
    String taskSchedule, dateSchedule, timeSchedule,locationSchedule,assetSchedule, deploySchedule,scheduleID;

    ScheduleModel(){

    }

    public ScheduleModel(String taskSchedule, String dateSchedule, String timeSchedule, String locationSchedule, String assetSchedule, String deploySchedule, String scheduleID) {
        this.taskSchedule = taskSchedule;
        this.dateSchedule = dateSchedule;
        this.timeSchedule = timeSchedule;
        this.locationSchedule = locationSchedule;
        this.assetSchedule = assetSchedule;
        this.deploySchedule = deploySchedule;
        this.scheduleID = scheduleID;
    }


    protected ScheduleModel(Parcel in) {
        taskSchedule = in.readString();
        dateSchedule = in.readString();
        timeSchedule = in.readString();
        locationSchedule = in.readString();
        assetSchedule = in.readString();
        deploySchedule = in.readString();
        scheduleID = in.readString();
    }



    public static final Creator<ScheduleModel> CREATOR = new Creator<ScheduleModel>() {
        @Override
        public ScheduleModel createFromParcel(Parcel in) {
            return new ScheduleModel(in);
        }

        @Override
        public ScheduleModel[] newArray(int size) {
            return new ScheduleModel[size];
        }
    };

    public String getTaskSchedule() {
        return taskSchedule;
    }

    public void setTaskSchedule(String taskSchedule) {
        this.taskSchedule = taskSchedule;
    }

    public String getDateSchedule() {
        return dateSchedule;
    }

    public void setDateSchedule(String dateSchedule) {
        this.dateSchedule = dateSchedule;
    }

    public String getTimeSchedule() {
        return timeSchedule;
    }

    public void setTimeSchedule(String timeSchedule) {
        this.timeSchedule = timeSchedule;
    }

    public String getLocationSchedule() {
        return locationSchedule;
    }

    public void setLocationSchedule(String locationSchedule) {
        this.locationSchedule = locationSchedule;
    }

    public String getAssetSchedule() {
        return assetSchedule;
    }

    public void setAssetSchedule(String assetSchedule) {
        this.assetSchedule = assetSchedule;
    }

    public String getDeploySchedule() {
        return deploySchedule;
    }

    public void setDeploySchedule(String deploySchedule) {
        this.deploySchedule = deploySchedule;
    }

    public String getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(String scheduleID) {
        this.scheduleID = scheduleID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(taskSchedule);
        parcel.writeString(dateSchedule);
        parcel.writeString(timeSchedule);
        parcel.writeString(locationSchedule);
        parcel.writeString(assetSchedule);
        parcel.writeString(deploySchedule);
        parcel.writeString(scheduleID);
    }
}
