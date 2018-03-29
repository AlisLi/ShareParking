package com.example.sharingparking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.sharingparking.R;
import com.example.sharingparking.SysApplication;
import com.example.sharingparking.adapter.LockAdapter;
import com.example.sharingparking.entity.ParkingLock;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

import static com.example.sharingparking.common.Common.LOCK_REQUEST_ERROR;
import static com.example.sharingparking.common.Common.NET_URL_HEADER;
import static com.example.sharingparking.utils.CommonUtil.toast;
import static com.example.sharingparking.utils.Utility.handleLockResponse;

/**
 * 活动：我的车位
 * Created by Lizhiguo on 2017/11/29.
 */

public class ParkingActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private List<ParkingLock> mParkingLockList = new ArrayList<>();
    private int userId;     //用户ID

    private LockAdapter mLockAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myparking);

        //添加活动到ActivityList中(安全退出)
        SysApplication.getInstance().addActivity(this);

        //从intent中获取数据
        Bundle bundle = getIntent().getBundleExtra("data");
        //获取数据
        userId = bundle.getInt("userId");

        init();


    }

    //跳转到注册车位界面
    public void toRegisterParking(View view){
        Intent intent = new Intent(this,RegisterParkingActivity.class);
        startActivity(intent);
    }

    private void init(){
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_my_parking);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_parking);

        //适配车位信息到RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mLockAdapter = new LockAdapter(mParkingLockList);
        mRecyclerView.setAdapter(mLockAdapter);

        //配置刷新列表
        //设置颜色
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        //配置监听器
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                refreshParking();
            }
        });

        /**
         * 设置首次进入刷新
         */
        mSwipeRefreshLayout.measure(0,0);
        mSwipeRefreshLayout.setRefreshing(true);


    }

    /**
     * 请求车位锁信息
     */
    private void requestLockMessage(){
        OkHttpUtils
                .post()
                .url(NET_URL_HEADER + "LockServlet")
                .addParams("userId",userId + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        toast(ParkingActivity.this,LOCK_REQUEST_ERROR);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        //清除原有数据
                        mParkingLockList.clear();
                        //获取解析的数据
                        mParkingLockList = handleLockResponse(response);
                    }
                });
    }

    //刷新车位信息
    public void refreshParking(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                //请求车位锁信息
                requestLockMessage();

                //刷新UI界面
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mLockAdapter.notifyDataSetChanged();
                        //取消刷新效果
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

}
