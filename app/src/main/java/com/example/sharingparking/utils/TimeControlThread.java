package com.example.sharingparking.utils;

import com.example.sharingparking.interfaces.DialogCallback;

/**
 * Created by Lizhiguo on 2018/4/7.
 */

public class TimeControlThread implements Runnable {

    private long startTime;
    private long time;  //存在时间
    private DialogCallback mDialogCallback;

    public TimeControlThread(long startTime, long time,DialogCallback dialogCallback) {
        this.startTime = startTime;
        this.time = time;
        this.mDialogCallback = dialogCallback;
    }

    @Override
    public void run() {
        while (System.currentTimeMillis() - startTime < time) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        mDialogCallback.callback();

    }
}
