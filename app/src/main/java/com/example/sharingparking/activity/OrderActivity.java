package com.example.sharingparking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sharingparking.R;
import com.example.sharingparking.SysApplication;
import com.example.sharingparking.adapter.GirdDropDownAdapter;
import com.example.sharingparking.adapter.ListDropDownAdapter;
import com.example.sharingparking.adapter.OrderingAdapter;
import com.example.sharingparking.baidumap.MapActivity;
import com.example.sharingparking.entity.Publish;
import com.example.sharingparking.widget.DropDownMenu;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;
import okhttp3.Call;
import okhttp3.MediaType;

import static com.example.sharingparking.common.Common.LOCK_ORDERING_REQUEST_ERROR;
import static com.example.sharingparking.common.Common.LOCK_ORDERING_REQUEST_FAIL;
import static com.example.sharingparking.common.Common.NET_URL_HEADER;
import static com.example.sharingparking.utils.CommonUtil.initTitle;
import static com.example.sharingparking.utils.CommonUtil.toast;
import static com.example.sharingparking.utils.Utility.handleMessageResponse;
import static com.example.sharingparking.utils.Utility.handlePublishResponse;

/**
 * 活动：显示附近已发布的车位，预订车位
 * Created by Lizhiguo on 2017/11/29.
 */

public class OrderActivity extends AppCompatActivity implements OrderingAdapter.OrderingInterface{

    private String TAG = "PublishedActivity";

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView txtTitle;

    private List<Publish> mPublishes = new ArrayList<>();
    private int userId;     //用户ID

    private OrderingAdapter mOrderingAdapter;

    /**
     * 筛选框
     */
    @BindView(R.id.dropDownMenu)
    DropDownMenu mDropDownMenu;
    //筛选框
    private List<View> popupViews = new ArrayList<>();
    //筛选框头部内容
    private String headers[] = {"距离最近","最近预定"};
    //筛选框数据
    private String [] selectConditions = {"距离最近","时间优先","评价优先"};
    private String [] recentOrdering = {"南昌","上海"};
    public static int ORDERING_CONDITION = 0;

    //筛选框适配器
    private ListDropDownAdapter selectConditionAdapter;
    private GirdDropDownAdapter mGirdDropDownAdapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_common);

        //添加活动到ActivityList中(安全退出)
        SysApplication.getInstance().addActivity(this);

        //从intent中获取数据
        userId = getIntent().getIntExtra("userId",0);

        ButterKnife.bind(this);
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

        //发起请求(仅查询)
        requestOrdering();

    }

    //请求订单信息
    private void requestOrdering() {
        OkHttpUtils
                .postString()
                .url(NET_URL_HEADER + "publish/querypublishnotmy")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content("{\"userId\":" + userId + "}")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        toast(OrderActivity.this,LOCK_ORDERING_REQUEST_ERROR);
                        //取消刷新效果
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d(TAG,response);
                        List<Publish> publishes = handlePublishResponse(response);
                        if(publishes != null){
                            //车位发布信息请求成功，更新UI
                            mPublishes.clear();
                            mPublishes.addAll(publishes);

                        }else if(handleMessageResponse(response) != null){
                            if("empty".equals(handleMessageResponse(response))){
                                mPublishes.clear();
                            }
                            //提示错误信息
                            Toast.makeText(OrderActivity.this,handleMessageResponse(response),
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            //车位信息请求失败
                            Toast.makeText(OrderActivity.this,LOCK_ORDERING_REQUEST_FAIL,
                                    Toast.LENGTH_SHORT).show();
                        }

                        //刷新UI界面
                        //放到外面由于多线程无法及时接收
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d(TAG,"请求完毕");
                                mOrderingAdapter.notifyDataSetChanged();
                                //取消刷新效果
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                });

    }

    private void init() {

        //初始化筛选条件筛选框
        final ListView selectConditionsView = new ListView(this);
        selectConditionsView.setDividerHeight(0);
        selectConditionAdapter = new ListDropDownAdapter(this, Arrays.asList(selectConditions));
        selectConditionsView.setAdapter(selectConditionAdapter);

        //初始化最近预定筛选框
        final ListView recentOrderingView = new ListView(this);
        mGirdDropDownAdapter = new GirdDropDownAdapter(this, Arrays.asList(recentOrdering));
        recentOrderingView.setDividerHeight(0);
        recentOrderingView.setAdapter(mGirdDropDownAdapter);


        //添加筛选框
        popupViews.add(selectConditionsView);
        popupViews.add(recentOrderingView);

        //添加点击事件
        selectConditionsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectConditionAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[0] : selectConditions[position]);
                mDropDownMenu.closeMenu();
            }
        });

        //初始化布局
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews,
                LayoutInflater.from(OrderActivity.this).inflate(R.layout.activity_ordering, null));

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_my_ordering);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_ordering);
        txtTitle = (TextView) findViewById(R.id.txt_title_common);
        initTitle(txtTitle,getIntent().getStringExtra("title_text"));

        //适配发布信息到RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mOrderingAdapter = new OrderingAdapter(mPublishes);
        mRecyclerView.setAdapter(mOrderingAdapter);

        //设置按钮点击事件接口监听
        mOrderingAdapter.setOrderingInterface(this);

        //配置刷新列表
        //设置颜色
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        //配置监听器
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                requestOrdering();
            }
        });

    }

    /**
     * 预订车位
     * @param publish
     */
    @Override
    public void orderingParking(Publish publish) {

        Intent intent = new Intent(this,OrderingSetTimeActivity.class);
        intent.putExtra("title_text",getResources().getText(R.string.set_rent_time));
        intent.putExtra("userId",userId);
        intent.putExtra("publishId",publish.getPublishId());
        intent.putExtra("expense",publish.getParkingMoney());
        startActivity(intent);

    }


    /**
     * 显示地图
     * @param publish
     */
    @Override
    public void showMap(Publish publish) {
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("positionPublish",publish);
        intent.putExtra("publishList", (Serializable) mPublishes);
        startActivity(intent);
    }




}
