package com.example.sharingparking.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sharingparking.R;
import com.example.sharingparking.SysApplication;

/**
 * Created by Lizhiguo on 2017/10/19.
 */

public class MainActivity extends AppCompatActivity{

    private TextView txtUserName;   //用户名

    private String userName;    //用户名
    private int userId;     //用户ID



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //添加活动到ActivityList中
        SysApplication.getInstance().addActivity(this);

        Explode explode = new Explode();
        explode.setDuration(500);
        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);

        initView();

    }

    /**
     * 初始化控件
     */
    private void initView(){
        //接收前一个活动传的用户名,用户ID
        Intent intent = getIntent();
        userName = intent.getStringExtra("username");
        userId = intent.getIntExtra("userId",0);

        txtUserName = (TextView) findViewById(R.id.txt_username);
        txtUserName.setText(userName);
    }


    //跳转到我的钱包
    public void showWallet(View view){
        Intent intent = new Intent(this,WalletActivity.class);
        startActivity(intent);

    }

    //跳转到我的订单
    public void showOrder(View view){
        Intent intent = new Intent(this,OrderActivity.class);
        startActivity(intent);
    }

    //跳转到我的优惠
    public void showBenefit(View view){
        Intent intent = new Intent(this,BenefitActivity.class);
        startActivity(intent);
    }

    //跳转到我的车位
    public void showParking(View view){
        Intent intent = new Intent(this,ParkingActivity.class);

        startActivity(sendUserId(intent));
    }

    //跳转到设置
    public void showSetting(View view){
        Intent intent = new Intent(this,SettingActivity.class);
        startActivity(intent);
    }

    //从主界面跳转到租用详情
    public void toRentMessage(View view){
        Intent intent = new Intent(this,RentMessageActivity.class);
        startActivity(intent);
    }

    //从主界面跳转到我的车位
    public void toMyParking(View view){
        showParking(view);
    }

    //从主界面跳转到预定车位
    public void toOrderingParking(View view){

    }

    /**
     * 将用户ID传入下一个活动
     * @param intent
     * @return
     */
    private Intent sendUserId(Intent intent){
        Bundle bundle = new Bundle();
        bundle.putInt("userId",userId);
        intent.putExtra("data",bundle);

        return intent;
    }



    //退出程序
    @Override
    public void onBackPressed() {
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
                SysApplication.getInstance().exit();
            }
        }).show();
    }
}
