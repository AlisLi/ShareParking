package com.example.sharingparking.entity;

/**
 * 用户类
 * Created by Lizhiguo on 2017/11/21.
 */

public class User {
    private String userName;    //用户名
    private String phoneNumber; //手机号
    private String password;    //密码
    private int age;            //年龄

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
