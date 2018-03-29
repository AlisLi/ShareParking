package com.example.sharingparking.utils;

import android.content.Context;
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
}
