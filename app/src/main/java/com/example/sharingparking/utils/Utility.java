package com.example.sharingparking.utils;

import android.text.TextUtils;

import com.example.sharingparking.entity.BlueTooth;
import com.example.sharingparking.entity.ParkingLock;
import com.example.sharingparking.entity.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 解析和处理各种Json数据
 * Created by Lizhiguo on 2018/3/15.
 */

public class Utility {

    /**
     * 解析和处理用户Json数据
     * @param response
     * @return
     */
    public static List<User> handleUserResponse(String response){
        if(!TextUtils.isEmpty(response)){
            try {
                List<User> list = new ArrayList<>();
                JSONArray users = new JSONArray(response);
                for(int i = 0;i < users.length();i++){
                    JSONObject userObject = users.getJSONObject(i);
                    User user = getUserJson(userObject);
                    list.add(user);
                }
                return list;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static List<BlueTooth> handleBlueToothResponse(String response){
        if(!TextUtils.isEmpty(response)){
            try {
                List<BlueTooth> list = new ArrayList<>();
                JSONArray blueTooths = new JSONArray(response);
                for(int i = 0;i < blueTooths.length();i++){
                    JSONObject bluetoothObject = blueTooths.getJSONObject(i);
                    BlueTooth blueTooth = getBlueToothJson(bluetoothObject);
                    list.add(blueTooth);
                }
                return list;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 解析和处理用户Json数据
     * @param response
     * @return
     */
    public static List<ParkingLock> handleLockResponse(String response){
        if(!TextUtils.isEmpty(response)){
            try {
                List<ParkingLock> list = new ArrayList<>();
                JSONArray locks = new JSONArray(response);
                for(int i = 0;i < locks.length();i++){
                    JSONObject locksJSONObject = locks.getJSONObject(i);
                    ParkingLock parkingLock = new ParkingLock();
                    User user = getUserJson(locksJSONObject);
                    parkingLock.setLockId(locksJSONObject.getInt("lockId"));
                    parkingLock.setAddress(locksJSONObject.getString("address"));
                    parkingLock.setBattery(locksJSONObject.getInt("battery"));
                    parkingLock.setInfrared(locksJSONObject.getInt("infrared"));
                    parkingLock.setLed(locksJSONObject.getInt("led"));
                    parkingLock.setLockState(locksJSONObject.getInt("lockState"));
                    parkingLock.setUser(user);
                    list.add(parkingLock);
                }
                return list;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }



    /**
     * 获取用户Json数据
     * @param jsonObject
     * @return
     */
    private static User getUserJson(JSONObject jsonObject){
        User user = new User();

        try {
            user.setUserName(jsonObject.getString("userName"));
            user.setUserId(jsonObject.getInt("userId"));
            user.setPhoneNumber(jsonObject.getString("phoneNumber"));
            user.setPassword(jsonObject.getString("password"));
            user.setUserMoney(jsonObject.getDouble("userMoney"));
            user.setLastLoginTime(jsonObject.getString("lastLoginTime"));
            return user;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取蓝牙json数据
     * @param jsonObject
     * @return
     */
    private static BlueTooth getBlueToothJson(JSONObject jsonObject){
        BlueTooth blueTooth = new BlueTooth();
        try {
            blueTooth.setBlueToothId(jsonObject.getInt("blueToothId"));
            blueTooth.setBlueToothName(jsonObject.getString("blueToothName"));
            blueTooth.setBlueToothPassword(jsonObject.getString("blueToothPassword"));
            blueTooth.setBlueToothState(jsonObject.getInt("blueToothState"));

            return blueTooth;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取车位锁json数据
     * @param jsonObject
     * @return
     */
    private static ParkingLock getParkingLockJson(JSONObject jsonObject){

        try {
            ParkingLock parkingLock = new ParkingLock();
            User user = getUserJson(jsonObject);
            BlueTooth blueTooth = getBlueToothJson(jsonObject);
            parkingLock.setLockId(jsonObject.getInt("lockId"));
            parkingLock.setAddress(jsonObject.getString("address"));
            parkingLock.setBattery(jsonObject.getInt("battery"));
            parkingLock.setInfrared(jsonObject.getInt("infrared"));
            parkingLock.setLed(jsonObject.getInt("led"));
            parkingLock.setLockState(jsonObject.getInt("lockState"));
            parkingLock.setUser(user);
            parkingLock.setBlueTooth(blueTooth);

            return parkingLock;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
