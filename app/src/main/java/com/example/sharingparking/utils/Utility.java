package com.example.sharingparking.utils;

import android.text.TextUtils;
import android.util.Log;

import com.example.sharingparking.entity.BlueTooth;
import com.example.sharingparking.entity.ParkingLock;
import com.example.sharingparking.entity.Publish;
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
    public static final String UTILITY_TAG = "Utility";
    /**
     * 解析和处理用户Json数据
     * @param response
     * @return
     */
    public static User handleUserResponse(String response){
        if(!TextUtils.isEmpty(response)){
            try {
                Log.d(UTILITY_TAG,"user : " + response);
                JSONObject userObject = new JSONObject(response);
                User user = getUserJson(userObject);
                return user;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
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
            return user;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
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
     * 解析和处理车位锁Json数据
     * @param response
     * @return
     */
    public static List<ParkingLock> handleLockResponse(String response){
        if(!TextUtils.isEmpty(response)){
            try {
                List<ParkingLock> list = new ArrayList<>();
                JSONArray jsonArray = new JSONArray(response);
                for(int i = 0;i < jsonArray.length();i++){
                    JSONObject locksJSONObject = jsonArray.getJSONObject(i);
                    ParkingLock parkingLock = getParkingLockJson(locksJSONObject);
                    list.add(parkingLock);
                }
                return list;

            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
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

            parkingLock.setLockId(jsonObject.getInt("lockId"));
            parkingLock.setUserId(jsonObject.getInt("userId"));
            parkingLock.setBlueToothId(jsonObject.getInt("blueToothId"));
            parkingLock.setAddress(jsonObject.getString("address"));
            parkingLock.setBattery(jsonObject.getInt("battery"));
            parkingLock.setInfrared(jsonObject.getInt("infrared"));
            parkingLock.setLed(jsonObject.getInt("led"));
            parkingLock.setLockState(jsonObject.getInt("lockState"));

            return parkingLock;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Publish> handlePublishResponse(String response){
        if(!TextUtils.isEmpty(response)){
            try {
                List<Publish> list = new ArrayList<>();
                JSONArray jsonArray = new JSONArray(response);
                for(int i = 0;i < jsonArray.length();i++){
                    JSONObject publishJSONObject = jsonArray.getJSONObject(i);
                    Publish publish = getPublishJson(publishJSONObject);
                    list.add(publish);
                }
                return list;

            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /**
     * 获取车位锁发布json数据
     * @param publishJSONObject
     * @return
     */
    private static Publish getPublishJson(JSONObject publishJSONObject) {
        try {
            Publish publish = new Publish();

            publish.setPublishId(publishJSONObject.getInt("publishId"));
            publish.setLockId(publishJSONObject.getInt("lockId"));
            publish.setPublishStartTime(publishJSONObject.getString("startTime"));
            publish.setPublishEndTime(publishJSONObject.getString("endTime"));
            publish.setParkingMoney(publishJSONObject.getDouble("parkingMoney"));
            publish.setPublishState(publishJSONObject.getInt("publishState"));

            return publish;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 处理和解析异常Json提示信息
     * @param response
     * @return
     */
    public static String handleMessageResponse(String response){
        if(!TextUtils.isEmpty(response)){
            try {
                Log.d(UTILITY_TAG,"error : " + response);
                JSONObject userObject = new JSONObject(response);
                String message = userObject.getString("msg");
                return message;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
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



}
