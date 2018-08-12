package com.example.sharingparking.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sharingparking.R;
import com.example.sharingparking.SysApplication;
import com.example.sharingparking.adapter.RentMessageAdapter;
import com.example.sharingparking.entity.Ordering;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zyao89.view.zloading.ZLoadingDialog;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;

import static com.example.sharingparking.common.Common.NET_URL_HEADER;
import static com.example.sharingparking.common.Common.USER_ORDERING_PAY_ERROR;
import static com.example.sharingparking.common.Common.USER_ORDERING_PAY_FAIL;
import static com.example.sharingparking.common.Common.USER_ORDERING_PAY_SUCCESS;
import static com.example.sharingparking.common.Common.USER_ORDERING_REQUEST_ERROR;
import static com.example.sharingparking.common.Common.USER_ORDERING_REQUEST_FAIL;
import static com.example.sharingparking.utils.CommonUtil.cancelSecondDialog;
import static com.example.sharingparking.utils.CommonUtil.toast;
import static com.example.sharingparking.utils.Utility.handleListOrderResponse;
import static com.example.sharingparking.utils.Utility.handleMessageResponse;
import static com.example.sharingparking.utils.Utility.handlePay;
import static com.zyao89.view.zloading.Z_TYPE.DOUBLE_CIRCLE;

/**
 * 活动：租用详情
 * Created by Lizhiguo on 2018/3/17.
 */

public class RentMessageActivity extends AppCompatActivity implements RentMessageAdapter.RentInterface {

    private String TAG = "RentMessageActivity";

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView txtTitleText;

    private List<Ordering> mOrderingList = new ArrayList<>();

    private RentMessageAdapter mRentMessageAdapter;

    private Integer userId;

    //加载Dialog第三方类
    private ZLoadingDialog dialog;

    @Override
    protected void onStart() {
        super.onStart();
        /**
         * 设置刷新
         * activity重新显示或首次进入后，请求车位信息
         */
        mSwipeRefreshLayout.measure(0,0);
        mSwipeRefreshLayout.setRefreshing(true);

        //发起请求
        requsetOrdering();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rentmessage);

        //添加活动到ActivityList中(安全退出)
        SysApplication.getInstance().addActivity(this);

        init();

    }

    private void init() {

        userId = getIntent().getIntExtra("userId",0);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_rent_message);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_rent_message);
        txtTitleText = (TextView) findViewById(R.id.txt_title_common);
        txtTitleText.setText(getIntent().getStringExtra("title_text"));

        //配置刷新列表
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

            @Override
            public void onRefresh() {
                requsetOrdering();
            }

        });

        //适配车位信息到RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRentMessageAdapter = new RentMessageAdapter(mOrderingList);
        mRecyclerView.setAdapter(mRentMessageAdapter);

        mRentMessageAdapter.setRentInterface(this);
    }

    private void requsetOrdering() {
        OkHttpUtils
                .postString()
                .url(NET_URL_HEADER + "user/getorderingsbyuserid")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content("{\"userId\":" + userId + "}")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        toast(RentMessageActivity.this,USER_ORDERING_REQUEST_ERROR);
                        //取消刷新效果
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d(TAG,response);
                        List<Ordering> list = handleListOrderResponse(response);

                        if(list != null){
                            mOrderingList.clear();
                            //请求成功
                            mOrderingList.addAll(list);
                        }else if(handleMessageResponse(response) != null){
                            //提示错误信息
                            Toast.makeText(RentMessageActivity.this,handleMessageResponse(response),
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            //车位信息请求失败
                            Toast.makeText(RentMessageActivity.this,USER_ORDERING_REQUEST_FAIL,
                                    Toast.LENGTH_SHORT).show();
                        }

                        //刷新UI界面
                        //放到外面由于多线程无法及时接收
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d(TAG,"请求完毕");
                                mRentMessageAdapter.notifyDataSetChanged();
                                //取消刷新效果
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        });

                    }
                });
    }

    //控制车位
    @Override
    public void rentControlParking(Ordering ordering) {

        Intent intent = new Intent(RentMessageActivity.this,ControlParkingActivity.class);
        intent.putExtra("text_title",getResources().getText(R.string.control_by_bluetooth_method));
        intent.putExtra("blueToothId",ordering.getPublish().getLock().getBlueToothId());
        intent.putExtra("lockId",ordering.getPublish().getLock().getLockId());
        startActivity(intent);

    }

    //确定租用
    @Override
    public void rentConfirm(Ordering ordering) {

        if(ordering.getOrderingState() == 6){
            //如果是正在使用，则调用结束租用

        }else {
            //改变预定状态为已支付
            requestChangePayedState(ordering);
        }

    }

    /**
     * 请求改变状态为已支付
     */
    private void requestChangePayedState(Ordering ordering) {
        duringDialog("正在支付...");
        Log.d(TAG,ordering.getOrderingId() + "");

        OkHttpUtils
                .postString()
                .url(NET_URL_HEADER + "order/dopay")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content("{\"orderingId\":" + ordering.getOrderingId() + "}")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        e.printStackTrace();
                        dialog.cancel();
                        duringDialog(USER_ORDERING_PAY_ERROR);
                        cancelSecondDialog(dialog);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d(TAG,response);
                        //判断是否支付成功
                        boolean isSucceed = handlePay(response);

                        if(isSucceed){
                            dialog.cancel();
                            duringDialog(USER_ORDERING_PAY_SUCCESS);
                            cancelSecondDialog(dialog);
                            requsetOrdering();
                        }else {
                            dialog.cancel();
                            duringDialog(USER_ORDERING_PAY_FAIL);
                            cancelSecondDialog(dialog);
                        }



                    }
                });

    }

    //dialog动画
    private void duringDialog(String dialogText){
        dialog = new ZLoadingDialog(RentMessageActivity.this);
        dialog.setLoadingBuilder(DOUBLE_CIRCLE)//设置类型
                .setLoadingColor(Color.BLACK)//颜色
                .setHintText(dialogText)
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(Color.GRAY)  // 设置字体颜色
                .setCanceledOnTouchOutside(false)
                .show();
    }

}
