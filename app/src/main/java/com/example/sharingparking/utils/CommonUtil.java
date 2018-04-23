package com.example.sharingparking.utils;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 工具类：公共工具类
 * Created by Lizhiguo on 2018/3/17.
 */

public class CommonUtil {

    /**
     * 显示Toast
     */
    public static void toast(Context context,String content){
        Toast.makeText(context,content,Toast.LENGTH_LONG).show();
    }

    /**
     * 计算租用时间
     */
    public static String caculateRentTime(String endTime,String startTime){
        return 10 + "";
    }

    /**
     * 将车位地址链接
     * 下划线分割
     */
    public static String linkParkingAddress(String locationAddress,String detailAddress){

        String address = locationAddress + "_" + detailAddress;

        return address;
    }


    /**
     * 将车位地址拆分
     */
    public static String[] splitParkingAddress(String address){

        String[] addresses = address.split("_");

        return addresses;
    }

    /**
     * 初始化title
     */
    public  static void initTitle(TextView txtTitle,String titleText){
        txtTitle.setText(titleText);//设置标题
    }

    /**
     * 验证是否为Double字符串
     */
    public static boolean isDoubleNumber(String string){

        try {
            Double number = Double.parseDouble(string);
            return true;
        }catch (Exception e){
            return false;
        }

    }

}
