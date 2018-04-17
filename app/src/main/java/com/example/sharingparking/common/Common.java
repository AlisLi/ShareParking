package com.example.sharingparking.common;

/**
 * Created by Lizhiguo on 2017/11/21.
 */

public class Common {

    //访问服务器的url前置部分
   public static final String NET_URL_HEADER = "http://192.168.1.107:8080/sharepark/";

    public static final String TAG_SHOW = "lzg";

    /**
     * 登录
     */
    public static final String LOGIN_ERROR = "登录异常";
    public static final String LOGIN_PASSWORD_MISTAKE = "登录密码错误";
    public static final String LOGIN_SUCCEED = "登录成功";
    public static final String LOGIN_FAIL = "登录失败";
    public static final String LOGIN_NO_REGISTER = "未注册";

    /**
     * 注册
     */
    public static final String REGISTER_USER_ERROR = "注册异常";
    public static final String REGISTER_USER_EXIST = "已经注册";
    public static final String REGISTER_USER_SUCCEED = "注册成功";
    public static final String REGISTER_USER_FAIL = "注册失败";

    /**
     * 车位锁控制
     */
    public static final String LOCK_UP = "1#";  //控制车位上升
    public static final String LOCK_DOWN = "0#";  //控制车位下降
    public static final String LOCK_DOWN_STATE = "1";  //车位锁处于0度
    public static final String LOCK_BAR_STATE = "2";  //车位锁处于0度-90度,且有障碍物
    public static final String LOCK_DOWNING_STATE = "3";  //车位锁处于下降状态
    public static final String LOCK_UPPING_STATE = "3";  //车位锁处于上升状态
    public static final String LOCK_UP_STATE = "4";  //车位锁处于90度
    public static final String LOCK_OVER_UP_STATE = "5";  //车位锁处于90度-180度

    /**
     * 车位锁注册
     */
    public  static final String LOCK_REGISTER_ERROR = "车位锁注册异常";
    public  static final String LOCK_REGISTER_FAIL = "车位锁注册失败";

    /**
     * 车位锁显示
     */
    public static final String LOCK_REQUEST_ERROR = "车位锁信息获取异常";
    public static final String LOCK_REQUEST_FAIL = "车位锁信息获取失败";

    /**
     * 车位发布信息
     */
    public static final String LOCK_PUBLISH_ERROR = "车位锁发布信息异常";
    public static final String LOCK_PUBLISH_FAIL = "车位锁发布信息失败";




}
