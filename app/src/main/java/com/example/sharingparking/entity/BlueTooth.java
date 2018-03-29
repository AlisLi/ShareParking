package com.example.sharingparking.entity;

/**
 * 实体类：蓝牙
 * Created by Lizhiguo on 2018/3/26.
 */

public class BlueTooth {
    private int blueToothId;    //蓝牙编号
    private String BlueToothName;   //蓝牙名称
    private String BlueToothPassword;   //蓝牙密码
    private int BlueToothState;  //蓝牙状态

    public int getBlueToothId() {
        return blueToothId;
    }

    public void setBlueToothId(int blueToothId) {
        this.blueToothId = blueToothId;
    }

    public String getBlueToothName() {
        return BlueToothName;
    }

    public void setBlueToothName(String blueToothName) {
        BlueToothName = blueToothName;
    }

    public String getBlueToothPassword() {
        return BlueToothPassword;
    }

    public void setBlueToothPassword(String blueToothPassword) {
        BlueToothPassword = blueToothPassword;
    }

    public int getBlueToothState() {
        return BlueToothState;
    }

    public void setBlueToothState(int blueToothState) {
        BlueToothState = blueToothState;
    }
}
