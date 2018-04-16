package com.example.sharingparking.entity;

/**
 * 用户类
 * Created by Lizhiguo on 2017/11/21.
 */

public class User {
    private int userId;     //用户ID
    private String userName;    //用户名
    private String phoneNumber; //手机号
    private String password;    //密码
    private double userMoney;   //余额

    public User() {
    }

    public User(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public User(String userName, String phoneNumber, String password, double userMoney) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.userMoney = userMoney;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getUserMoney() {
        return userMoney;
    }

    public void setUserMoney(double userMoney) {
        this.userMoney = userMoney;
    }

}
