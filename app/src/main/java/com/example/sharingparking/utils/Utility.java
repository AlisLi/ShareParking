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
            user.setUserId(handleInteger(jsonObject,"userId"));
            user.setPhoneNumber(jsonObject.getString("phoneNumber"));
            user.setPassword(jsonObject.getString("password"));
            user.setUserMoney(jsonObject.getDouble("userMoney"));
            return user;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static BlueTooth handleBlueToothResponse(String response){
        if(!TextUtils.isEmpty(response)){
            try {
                JSONObject bluetoothObject = new JSONObject(response);
                BlueTooth blueTooth = getBlueToothJson(bluetoothObject);

                return blueTooth;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 解析注册锁的信息
     * @param response
     * @return
     */
    public static ParkingLock handleRegisterLockResponse(String response){
        if(!TextUtils.isEmpty(response)){
            try {

                JSONObject locksJSONObject = new JSONObject(response);
                ParkingLock parkingLock = getParkingLockJson(locksJSONObject);

                return parkingLock;

            } catch (JSONException e) {
                e.printStackTrace();
                return null;
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
            parkingLock.setLockId(handleInteger(jsonObject,"lockId"));
            parkingLock.setUserId(handleInteger(jsonObject,"userId"));
            parkingLock.setBlueToothId(handleInteger(jsonObject,"blueToothId"));
            parkingLock.setAddress(jsonObject.getString("address"));
            parkingLock.setBattery(handleInteger(jsonObject,"battery"));
            parkingLock.setInfrared(handleInteger(jsonObject,"infrared"));
            parkingLock.setLed(handleInteger(jsonObject,"led"));
            parkingLock.setLockState(handleInteger(jsonObject,"lockState"));

            return parkingLock;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 处理和解析车位发布Json信息
     * @param response
     * @return
     */
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
     * 处理和解析车位发布Json信息
     * @param response
     * @return
     */
    public static Publish handlePublishObjectResponse(String response){
        if(!TextUtils.isEmpty(response)){

            JSONObject publishJSONObject = new JSONObject();
            Publish publish = getPublishJson(publishJSONObject);

            return publish;
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

            publish.setPublishId(handleInteger(publishJSONObject,"publishId"));
            publish.setLock(handleInteger(publishJSONObject,"lockId"));
            publish.setPublishStartTime(publishJSONObject.getString("startTime"));
            publish.setPublishEndTime(publishJSONObject.getString("endTime"));
            publish.setParkingMoney(publishJSONObject.getDouble("parkingMoney"));
            publish.setPublishState(handleInteger(publishJSONObject,"publishState"));
            publish.setWay(handleInteger(publishJSONObject,"way"));
            publish.setUser(handleInteger(publishJSONObject,"userId"));

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
            blueTooth.setBluetoothId(handleInteger(jsonObject,"blueToothId"));
            blueTooth.setBluetoothName(jsonObject.getString("blueToothName"));
            blueTooth.setBluetoothPassword(jsonObject.getString("blueToothPassword"));
            blueTooth.setBluetoothState(handleInteger(jsonObject,"bluetoothState"));
            blueTooth.setBluetoothMAC(jsonObject.getString("macAddress"));

            return blueTooth;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static Integer handleInteger(JSONObject jsonObject,String key){

        try {
            Integer data = jsonObject.getInt(key);
            return data;
        } catch (JSONException e) {
            return null;
        }

    }

}
