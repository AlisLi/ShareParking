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
import com.example.sharingparking.adapter.PublishedAdapter;
import com.example.sharingparking.entity.Publish;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;

import static com.example.sharingparking.common.Common.LOCK_PUBLISH_CANCEL_ERROR;
import static com.example.sharingparking.common.Common.LOCK_PUBLISH_CANCEL_FAIL;
import static com.example.sharingparking.common.Common.LOCK_PUBLISH_REQUEST_ERROR;
import static com.example.sharingparking.common.Common.LOCK_PUBLISH_REQUEST_FAIL;
import static com.example.sharingparking.common.Common.NET_URL_HEADER;
import static com.example.sharingparking.utils.CommonUtil.initTitle;
import static com.example.sharingparking.utils.CommonUtil.toast;
import static com.example.sharingparking.utils.Utility.handleMessageResponse;
import static com.example.sharingparking.utils.Utility.handlePublishResponse;

/**
 * 发布车位详情
 * Created by Lizhiguo on 2018/4/17.
 */

public class PublishedActivity extends AppCompatActivity implements PublishedAdapter.PublishedInterface{

    private String TAG = "PublishedActivity";

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView txtTitle;

    private List<Publish> mPublishList = new ArrayList<>();
    private int userId;     //用户ID

    private PublishedAdapter mPublishAdapter;

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
        requestPublishMessage();
    }

    private void init() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_publish);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_publish);
        txtTitle = (TextView) findViewById(R.id.txt_title_common);
        initTitle(txtTitle,getIntent().getStringExtra("title_text"));

        //适配发布信息到RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mPublishAdapter = new PublishedAdapter(mPublishList);
        mRecyclerView.setAdapter(mPublishAdapter);

        //设置按钮点击事件接口监听
        mPublishAdapter.setPublishedInterface(this);

        //配置刷新列表
        //设置颜色
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        //配置监听器
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                requestPublishMessage();
            }
        });
    }

    //通过okHttpUtils请求发布信息
    private void requestPublishMessage() {

        OkHttpUtils
                .postString()
                .url(NET_URL_HEADER + "publish/querypublish")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content("{\"userId\":" + userId + "}")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        toast(PublishedActivity.this,LOCK_PUBLISH_REQUEST_ERROR);
                        //取消刷新效果
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d(TAG,response);
                        List<Publish> publishList = handlePublishResponse(response);
                        if(publishList != null){
                            //车位发布信息请求成功，更新UI
                            mPublishList.clear();
                            mPublishList.addAll(publishList);

                        }else if(handleMessageResponse(response) != null){
                            //提示错误信息
                            Toast.makeText(PublishedActivity.this,handleMessageResponse(response),
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            //车位信息请求失败
                            Toast.makeText(PublishedActivity.this,LOCK_PUBLISH_REQUEST_FAIL,
                                    Toast.LENGTH_SHORT).show();
                        }

                        //刷新UI界面
                        //放到外面由于多线程无法及时接收
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d(TAG,"请求完毕");
                                mPublishAdapter.notifyDataSetChanged();
                                //取消刷新效果
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                });

    }

    /**
     * 取消发布
     * @param publishId
     */
    @Override
    public void cancelPublished(Integer publishId) {
        OkHttpUtils
                .postString()
                .url(NET_URL_HEADER + "publish/calcelpublish\n")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content("{\"publishId\":" + publishId + "}")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        toast(PublishedActivity.this,LOCK_PUBLISH_CANCEL_ERROR);
                        //取消刷新效果
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d(TAG,response);

                        if(handleMessageResponse(response) != null){
                            String msg = handleMessageResponse(response);
                            if("success".equals(msg)){
                                //取消成功，刷新列表
                                mSwipeRefreshLayout.setRefreshing(true);
                                requestPublishMessage();
                            }else {
                                //提示错误信息
                                Toast.makeText(PublishedActivity.this,handleMessageResponse(response),
                                        Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            //车位信息取消发布请求失败
                            Toast.makeText(PublishedActivity.this,LOCK_PUBLISH_CANCEL_FAIL,
                                    Toast.LENGTH_SHORT).show();
                        }

                        //刷新UI界面
                        //放到外面由于多线程无法及时接收
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d(TAG,"请求完毕");
                                mPublishAdapter.notifyDataSetChanged();
                                //取消刷新效果
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                });
    }
}
