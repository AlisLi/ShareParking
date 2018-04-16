package com.example.sharingparking.activity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.example.sharingparking.R;
import com.example.sharingparking.SysApplication;

/**
 * Created by Lizhiguo on 2017/11/29.
 */

public class SettingActivity extends AppCompatActivity {

    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        //添加活动到ActivityList中(安全退出)
        SysApplication.getInstance().addActivity(this);
    }

    //注销用户
    public void finishUserMessage(View view){
        //从xml文件中装载LinearLayout对象
        LinearLayout finishLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_finish_app,null);
        //程序退出提醒框
        new AlertDialog.Builder(this).setView(finishLayout)
                .setTitle("提示").setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //清除保存的数据
                mSharedPreferences.edit().clear().commit();

                SysApplication.getInstance().exit();
            }
        }).show();
    }


}
