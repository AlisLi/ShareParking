package com.example.sharingparking.entity;

/**
 * 实体类：车位发布
 * Created by Lizhiguo on 2018/3/26.
 */

public class Publish {
    private int publishId;          //发布编号
    private String publishStartTime;   //发布开始时间
    private String publishEndTime;  //发布截止时间
    private ParkingLock mParkingLock;   //锁
    private double ParkingMoney;   //车位金额
    private int publishState;   //发布状态

    public int getPublishId() {
        return publishId;
    }

    public void setPublishId(int publishId) {
        this.publishId = publishId;
    }

    public String getPublishStartTime() {
        return publishStartTime;
    }

    public void setPublishStartTime(String publishStartTime) {
        this.publishStartTime = publishStartTime;
    }

    public String getPublishEndTime() {
        return publishEndTime;
    }

    public void setPublishEndTime(String publishEndTime) {
        this.publishEndTime = publishEndTime;
    }

    public ParkingLock getParkingLock() {
        return mParkingLock;
    }

    public void setParkingLock(ParkingLock parkingLock) {
        this.mParkingLock = parkingLock;
    }

    public double getParkingMoney() {
        return ParkingMoney;
    }

    public void setParkingMoney(double parkingMoney) {
        ParkingMoney = parkingMoney;
    }

    public int getPublishState() {
        return publishState;
    }

    public void setPublishState(int publishState) {
        this.publishState = publishState;
    }
}
