package com.example.sharingparking.activity;

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
import com.example.sharingparking.adapter.PublishAdapter;
import com.example.sharingparking.entity.Publish;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;

import static com.example.sharingparking.common.Common.LOCK_PUBLISH_ERROR;
import static com.example.sharingparking.common.Common.LOCK_PUBLISH_FAIL;
import static com.example.sharingparking.common.Common.NET_URL_HEADER;
import static com.example.sharingparking.utils.CommonUtil.initTitle;
import static com.example.sharingparking.utils.CommonUtil.toast;
import static com.example.sharingparking.utils.Utility.handleMessageResponse;
import static com.example.sharingparking.utils.Utility.handlePublishResponse;

/**
 * 发布车位详情
 * Created by Lizhiguo on 2018/4/17.
 */

public class PublishActivity extends AppCompatActivity{

    private String TAG = "PublishActivity";

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView txtTitle;

    private List<Publish> mPublishList = new ArrayList<>();
    private int userId;     //用户ID

    private PublishAdapter mPublishAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);

        //添加活动到ActivityList中
        SysApplication.getInstance().addActivity(this);

        //从intent中获取数据
        userId = getIntent().getIntExtra("userId",0);
        init();

    }

    private void init() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_publish);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_publish);
        txtTitle = (TextView) findViewById(R.id.txt_title_common);
        initTitle(txtTitle,getIntent().getStringExtra("title_text"));

        //适配发布信息到RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mPublishAdapter = new PublishAdapter(mPublishList);
        mRecyclerView.setAdapter(mPublishAdapter);

        //配置刷新列表
        //设置颜色
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        //配置监听器
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                refreshPublish();
            }
        });
    }

    //刷新发布信息
    public void refreshPublish(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                //请求发布信息
                requestPublishMessage();

                //刷新UI界面
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mPublishAdapter.notifyDataSetChanged();
                        //取消刷新效果
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    //通过okHttpUtils请求发布信息
    private void requestPublishMessage() {

        OkHttpUtils
                .postString()
                .url(NET_URL_HEADER + "")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content("{\"userId\":" + userId + "}")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        toast(PublishActivity.this,LOCK_PUBLISH_ERROR);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d(TAG,response);
                        List<Publish> publishList = handlePublishResponse(response);
                        if(publishList != null){
                            //车位发布信息请求成功，更新UI
                            mPublishList.clear();
                            mPublishList = publishList;

                        }else if(handleMessageResponse(response) != null){
                            //提示错误信息
                            Toast.makeText(PublishActivity.this,handleMessageResponse(response),
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            //车位信息请求失败
                            Toast.makeText(PublishActivity.this,LOCK_PUBLISH_FAIL,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

}
