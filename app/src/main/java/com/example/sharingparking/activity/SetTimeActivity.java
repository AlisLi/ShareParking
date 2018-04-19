package com.example.sharingparking.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sharingparking.R;
import com.example.sharingparking.slideDateTimePicker.SlideDateTimeListener;
import com.example.sharingparking.slideDateTimePicker.SlideDateTimePicker;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 设置开始时间和截止时间 父类
 * Created by Lizhiguo on 2018/4/17.
 */

public class SetTimeActivity extends AppCompatActivity{

    protected SimpleDateFormat mFormatter = new SimpleDateFormat("MMMM dd yyyy hh:mm aa");

    protected TextView txtTitle;
    protected EditText etStartTime;
    protected EditText etEndTime;

    //截止时间选择器监听器
    private SlideDateTimeListener endTimeListener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date)
        {
            //点击确定
            clickOk(etEndTime,date);
        }

        @Override
        public void onDateTimeCancel()
        {
            //点击取消
            clickCancel();
        }
    };

    //开始时间选择器监听器
    private SlideDateTimeListener startTimeListener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date)
        {
            //点击确定
            clickOk(etStartTime,date);
        }

        @Override
        public void onDateTimeCancel()
        {
            //点击取消
            clickCancel();
        }
    };

    //处理点击确定的事件
    private void clickOk(EditText editText,Date date) {
        //设置editText的值
        editText.setText(mFormatter.format(date));
    }

    //处理点击取消的事件
    private void clickCancel() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);

        init();


    }


    private void init() {

        etStartTime = (EditText) findViewById(R.id.et_start_time);
        etEndTime = (EditText) findViewById(R.id.et_end_time);

    }

    //点击开始时间editText
    public void setStartTime(View view){
        //显示化时间选择器
        new SlideDateTimePicker.Builder(getSupportFragmentManager())
                .setListener(startTimeListener) //设置监听器
                .setInitialDate(new Date())//初始化时间
                .setIs24HourTime(true)
                .build()
                .show();

    }
    //点击截止时间editText
    public void setEndTime(View view){

        new SlideDateTimePicker.Builder(getSupportFragmentManager())
                .setListener(endTimeListener) //设置监听器
                .setInitialDate(new Date())//初始化时间
                .setIs24HourTime(true)
                .build()
                .show();

    }

    //点击保存
    public void saveTime(View view){
        protectedSaveTime();
    }

    //子类重写点击保存的事件
    protected void protectedSaveTime() {

    }


}
