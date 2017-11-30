package com.example.sharingparking.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sharingparking.R;
import com.example.sharingparking.widget.CircleFlowIndicator;
import com.example.sharingparking.widget.ViewFlow;

import java.util.ArrayList;

/**
 * Created by Lizhiguo on 2017/10/24.
 */

public class OneFragment extends Fragment{
    private View mView;
    //横向循环滚动的图片控件
    private ViewFlow mViewFlow;
    //指示滚动的小圆点
    private CircleFlowIndicator mFlowIndicator;
    //获取图片的url列表
    private ArrayList<String> imageUrlList = new ArrayList<String>();
    //获取点击图片后跳转的页面的url列表
    private ArrayList<String> linkUrlArray = new ArrayList<String>();




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if(mView==null){
            mView = inflater.inflate(R.layout.layout_one_frag,container,false);
        }

        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //初始化控件
        initView(mView);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void initView(View view){

    }


}
