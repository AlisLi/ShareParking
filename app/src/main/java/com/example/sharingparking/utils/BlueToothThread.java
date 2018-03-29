package com.example.sharingparking.utils;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.InputStream;
import java.util.UUID;

import static com.example.sharingparking.common.Common.TAG_SHOW;

/**
 * 工具类：实现蓝牙数据传输的线程类
 * Created by Lizhiguo on 2018/3/18.
 */

public class BlueToothThread extends Thread {
    private BluetoothDevice device;

    private final String MY_UUID = "00001101-0000-1000-8000-00805F9B34FB";

    public BlueToothThread(BluetoothDevice device) {
        this.device = device;
    }

    @Override
    public void run() {

        BluetoothSocket socket = null;

        try {
            socket = device.createRfcommSocketToServiceRecord(UUID.fromString(MY_UUID));

            Log.d(TAG_SHOW, "连接服务端...");
            socket.connect();
            Log.d(TAG_SHOW, "连接建立.");

            // 读数据
            readDataFromServer(socket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readDataFromServer(BluetoothSocket socket) {
        byte[] buffer = new byte[64];
        try {
            InputStream is = socket.getInputStream();

            int cnt = is.read(buffer);
            is.close();

            String s = new String(buffer, 0, cnt);
            Log.d(TAG_SHOW, "收到服务端发来数据:" + s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
