package com.example.sharingparking.entity;

/**
 * 实体类：锁信息
 * Created by Lizhiguo on 2018/3/15.
 */

public class ParkingLock {
    private int lockId;     //锁编号
    private User user;     //用户
    private BlueTooth blueTooth;    //蓝牙
    private LockAddress lockAddress;     //车位位置
    private int lockState;  //锁的状态
    private int infrared;   //红外线状态
    private int led;        //LED灯状态
    private int battery;    //电池状态

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

    public LockAddress getLockAddress() {
        return lockAddress;
    }

    public void setLockAddress(LockAddress lockAddress) {
        this.lockAddress = lockAddress;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BlueTooth getBlueTooth() {
        return blueTooth;
    }

    public void setBlueTooth(BlueTooth blueTooth) {
        this.blueTooth = blueTooth;
    }
}
