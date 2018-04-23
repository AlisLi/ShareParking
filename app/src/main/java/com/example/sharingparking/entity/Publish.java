package com.example.sharingparking.entity;

/**
 * 实体类：车位发布
 * Created by Lizhiguo on 2018/3/26.
 */

public class Publish {
    private Integer publishId;          //发布编号
    private String publishStartTime;   //发布开始时间
    private String publishEndTime;  //发布截止时间
    private Integer user;//用户ID
    private Integer lock;   //锁
    private double parkingMoney;   //车位金额
    private Integer publishState;   //发布状态
    private Integer way;   //发布方式

    public Integer getPublishId() {
        return publishId;
    }

    public void setPublishId(Integer publishId) {
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

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Integer getLock() {
        return lock;
    }

    public void setLock(Integer lock) {
        this.lock = lock;
    }

    public double getParkingMoney() {
        return parkingMoney;
    }

    public void setParkingMoney(double parkingMoney) {
        this.parkingMoney = parkingMoney;
    }

    public Integer getPublishState() {
        return publishState;
    }

    public void setPublishState(Integer publishState) {
        this.publishState = publishState;
    }

    public Integer getWay() {
        return way;
    }

    public void setWay(Integer way) {
        this.way = way;
    }
}
