package com.example.sharingparking.entity;

/**
 * 实体类：锁信息
 * Created by Lizhiguo on 2018/3/15.
 */

public class ParkingLock {
    private int lockId;     //锁编号
    private int userId;     //用户ID
    private int blueToothId;    //蓝牙ID
    private String address;     //车位位置
    private int lockState;  //锁的状态
    private int infrared;   //红外线状态
    private int led;        //LED灯状态
    private int battery;    //电池状态

    public ParkingLock() {
    }

    public ParkingLock(int lockId, int userId, String address) {
        this.lockId = lockId;
        this.userId = userId;
        this.address = address;
    }

    public int getLockId() {
        return lockId;
    }

    public void setLockId(int lockId) {
        this.lockId = lockId;
    }

    public int getLockState() {
        return lockState;
    }

    public void setLockState(int lockState) {
        this.lockState = lockState;
    }

    public int getInfrared() {
        return infrared;
    }

    public void setInfrared(int infrared) {
        this.infrared = infrared;
    }

    public int getLed() {
        return led;
    }

    public void setLed(int led) {
        this.led = led;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBlueToothId() {
        return blueToothId;
    }

    public void setBlueToothId(int blueToothId) {
        this.blueToothId = blueToothId;
    }
}
