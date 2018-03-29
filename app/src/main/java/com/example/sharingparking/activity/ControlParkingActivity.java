package com.example.sharingparking.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
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
import com.zyao89.view.zloading.ZLoadingDialog;

import static com.example.sharingparking.common.Common.TAG_SHOW;
import static com.zyao89.view.zloading.Z_TYPE.DOUBLE_CIRCLE;

/**
 * 活动：通过蓝牙控制车位
 * Created by Lizhiguo on 2018/3/18.
 */

public class ControlParkingActivity extends AppCompatActivity{

    private BluetoothAdapter mBluetoothAdapter;

    private ZLoadingDialog dialog;

    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    private BluetoothChatService mChatService = null;

    //蓝牙设备名称
    private String mConnectedDeviceName;


    @Override
    protected void onStart() {
        super.onStart();

        if (!mBluetoothAdapter.isEnabled()) {
            //直接打开；
            mBluetoothAdapter.enable();
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controlparking);

        //添加活动到ActivityList中(安全退出)
        SysApplication.getInstance().addActivity(this);

        //获取蓝牙适配器
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // 注册广播接收器。接收蓝牙发现讯息
        IntentFilter filter1 = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter1);

        //搜索蓝牙设备
        mBluetoothAdapter.startDiscovery();

        matchingBlueTooth();

        // Initialize the BluetoothChatService to perform bluetooth connections
        mChatService = new BluetoothChatService(this, mHandler);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mReceiver);

    }

    /**
     * 控制车位上升
     */
    public void up(View view){
        //发送‘1’控制上升
        sendMessage("1");
    }

    /**
     * 控制车位下降
     */
    public void down(View view){
        //发送‘1’控制下降
        sendMessage("0");
    }

    //定义广播接收
    private BroadcastReceiver mReceiver=new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {

            String action=intent.getAction();

            if(action.equals(BluetoothDevice.ACTION_FOUND))
            {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                String name = device.getName();
                if (name != null)
                    Log.d(TAG_SHOW, "发现设备:" + name);

                if (name != null && name.equals("HC-05")) {
                    Log.d(TAG_SHOW, "发现目标设备，开始线程连接!");

                    // 蓝牙搜索是非常消耗系统资源开销的过程，一旦发现了目标感兴趣的设备，可以考虑关闭扫描。
                    mBluetoothAdapter.cancelDiscovery();

                    //连接蓝牙
                    mChatService.connect(device,true);
                }

            }else if(action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)){
                mBluetoothAdapter.cancelDiscovery();
                Log.d(TAG_SHOW, "未发现目标设备!");
            }

        }
    };

    //蓝牙配对动画
    private void matchingBlueTooth(){
        dialog = new ZLoadingDialog(ControlParkingActivity.this);
        dialog.setLoadingBuilder(DOUBLE_CIRCLE)//设置类型
                .setLoadingColor(Color.BLACK)//颜色
                .setHintText("蓝牙连接中...")
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(Color.GRAY)  // 设置字体颜色
                .show();
    }

    // The Handler that gets information back from the BluetoothChatService
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
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    //发送数据

                    break;
                case MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    //读取数据

                    break;
                case MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    Toast.makeText(getApplicationContext(), "Connected to "
                            + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                            Toast.LENGTH_SHORT).show();
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


}
