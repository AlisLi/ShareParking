package com.example.sharingparking.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.sharingparking.R;
import com.example.sharingparking.SysApplication;
import com.example.sharingparking.utils.BluetoothChatService;
import com.example.sharingparking.utils.BluetoothReceiver;
import com.example.sharingparking.utils.ClsUtils;
import com.zyao89.view.zloading.ZLoadingDialog;

import java.util.Set;

import static com.example.sharingparking.utils.CommonUtil.toast;
import static com.zyao89.view.zloading.Z_TYPE.DOUBLE_CIRCLE;

/**
 * 活动：通过蓝牙控制车位
 * Created by Lizhiguo on 2018/3/18.
 */

public class ControlParkingActivity extends AppCompatActivity implements BluetoothReceiver.BluetoothReceiverMessage{
    private String TAG = "ControlParkingActivity";

    // 来自BluetoothChatService Handler的信息类型
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    //蓝牙适配器
    private BluetoothAdapter mBluetoothAdapter;
    //请求打开蓝牙回应码
    private static final int BLUETOOTH_RESPONSE = 1;
    //加载Dialog第三方类
    private ZLoadingDialog dialog;

    private BluetoothChatService mChatService = null;

    //蓝牙设备名称
    private String mConnectedDeviceName = "SHARKING_LOCK";
    //蓝牙设备地址
    private String mConnectedDeviceAddress = "98:D3:32:30:CA:36";
    //蓝牙设备
    private BluetoothDevice device = null;
    //车位锁状态
    private String lockState = "0";
    //蓝牙设备PIN
    private String PIN = "1111";
    //蓝牙广播接收器
    private BluetoothReceiver mBluetoothReceiver;

    @Override
    protected void onStart() {
        super.onStart();
    }

    //蓝牙未打开则打开蓝牙
    private void openBlueTooth() {
        if (!mBluetoothAdapter.isEnabled()) {
            //通过这个方法来请求打开我们的蓝牙设备
           Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
           startActivityForResult(intent,BLUETOOTH_RESPONSE);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controlparking);

        mBluetoothReceiver = new BluetoothReceiver();
        // 动态注册注册广播接收器。接收蓝牙发现讯息
        IntentFilter btFilter = new IntentFilter();
        btFilter.setPriority(1000);
        btFilter.addAction(BluetoothDevice.ACTION_FOUND);
        btFilter.addAction(BluetoothDevice.ACTION_PAIRING_REQUEST);
        this.registerReceiver(mBluetoothReceiver,btFilter);

        //设置广播信息接口监听器
        mBluetoothReceiver.setReceiverMessageListener(this);


        //添加活动到ActivityList中(安全退出)
        SysApplication.getInstance().addActivity(this);

        //获取蓝牙适配器
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // Initialize the BluetoothChatService to perform bluetooth connections
        mChatService = new BluetoothChatService(this, mHandler);

        //请求打开蓝牙
        openBlueTooth();

        //请求判断蓝牙是否提前打开
        //如果没有提前打开则不会执行这句，执行openBluetooth的响应结果
        //如果提前打开则执行下面
        if(mBluetoothAdapter.isEnabled()){
            duringDialog("正在连接蓝牙！");
            searchBlueTooth();
        }



    }

    //搜索蓝牙设备
    private void searchBlueTooth() {
        //判断蓝牙是否已经绑定
        if(isBond()){
            Log.d(TAG,"蓝牙已经绑定");
            //已经绑定，直接连接蓝牙
            Log.d(TAG,device.getName() + "1");

        }else{
            Log.d(TAG,"搜索蓝牙中");
            //搜索蓝牙设备
            mBluetoothAdapter.startDiscovery();
        }
    }

    //判断蓝牙是否已经绑定
    private boolean isBond() {
        Set<BluetoothDevice> bondDevices = mBluetoothAdapter.getBondedDevices();
        Log.d(TAG,bondDevices.size() + "");
        for(BluetoothDevice bond : bondDevices){
            //设备已经配对
            Log.d(TAG,bond.getName());
            if(bond.getName().equals(mConnectedDeviceName)){
                device = bond;
                Log.d(TAG,device.getName());
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBluetoothAdapter.cancelDiscovery();
        unregisterReceiver(mBluetoothReceiver);
        try {
            Log.d(TAG,device + "  " + device.getBondState());
            ClsUtils.removeBond(device.getClass(), device);
        } catch (Exception e) {
            Log.d(TAG,"解除绑定失败！");
            e.printStackTrace();
        }
    }

    /**
     * 控制车位上升
     */
    public void up(View view){
        //发送‘1#’控制上升
        sendMessage("1#");
    }

    /**
     * 控制车位下降
     */
    public void down(View view){
        //发送‘0#’控制下降
        sendMessage("0#");
    }

    /**
     * 重新连接蓝牙
     */
    public void reconnect(View view){
//        if(device == null){
//            openBlueTooth();
//            searchBlueTooth();
//        }
    }

    //dialog动画
    private void duringDialog(String dialogText){
        dialog = new ZLoadingDialog(ControlParkingActivity.this);
        dialog.setLoadingBuilder(DOUBLE_CIRCLE)//设置类型
                .setLoadingColor(Color.BLACK)//颜色
                .setHintText(dialogText)
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(Color.GRAY)  // 设置字体颜色
                .setCanceledOnTouchOutside(false)
                .show();
    }

    //设置1秒后，取消dialog显示
    public void cancleSecondDialog(){
        new Handler().postDelayed(new Runnable(){
            public void run() {
                dialog.cancel();
            }
        }, 500);
    }

    // 通过Handle获取BluetoothService返回的信息
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothChatService.STATE_CONNECTED:
                            //蓝牙已连接

                            break;
                        case BluetoothChatService.STATE_CONNECTING:
                            //蓝牙正在连接

                            break;
                        case BluetoothChatService.STATE_LISTEN:
                        case BluetoothChatService.STATE_NONE:
                            //蓝牙未连接

                            break;
                    }
                    break;
                case MESSAGE_WRITE:
                    //发送数据返回的结果

                    break;
                case MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    //读取数据
                    Log.d(TAG,readMessage);
                    if("4".equals(readMessage)){
                        Log.d(TAG,readMessage);
                        if(!lockState.equals(readMessage)){
                            Log.d(TAG,readMessage);
                            duringDialog("锁正在打开！");
                        }
                    }else if("1".equals(readMessage)){
                        dialog.cancel();
                        duringDialog("打开成功");
                        cancleSecondDialog();
                    }else if("5".equals(readMessage)){
                        if(!lockState.equals(readMessage)){
                            Log.d(TAG,readMessage);
                            duringDialog("锁正在关闭！");
                        }
                    }else if("2".equals(readMessage)){
                        dialog.cancel();
                        duringDialog("关闭成功");
                        cancleSecondDialog();
                    }else if("3".equals(readMessage)){
                        if(!lockState.equals(readMessage)){
                            toast(ControlParkingActivity.this,"上方有障碍物！");
                        }
                    }
                    lockState = readMessage;
                    break;
                case MESSAGE_DEVICE_NAME:
                    // 保存已连接的设备名称
                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    Toast.makeText(getApplicationContext(), "Connected to "
                            + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    //取消搜索dialog
                    dialog.cancel();
                    //显示蓝牙连接成功dialog
                    duringDialog("蓝牙连接成功");
                    //蓝牙连接成功，0.5s后取消dialogUI显示
                    cancleSecondDialog();

                    break;
                case MESSAGE_TOAST:

                    break;
            }
        }
    };

    /**
     * Sends a message.
     *
     * @param message A string of text to send.
     */
    private void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            mChatService.write(send);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case BLUETOOTH_RESPONSE:
                if(resultCode == RESULT_OK){
                    //获得蓝牙权限成功
                    //需要再写一次，oncreate里的下面的方法无法执行
                    duringDialog("正在连接蓝牙！");
                    //搜索蓝牙
                    searchBlueTooth();
                }else if(resultCode == RESULT_CANCELED){
                    //获得蓝牙权限失败
                    toast(ControlParkingActivity.this,"蓝牙权限获取失败，请打开蓝牙！");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void setMessage(BluetoothDevice device) {
        if(device != null){
            Log.d(TAG,"蓝牙绑定成功，开始连接！");
            this.device = device;
            mChatService.connect(device,true);
        }
    }
}
