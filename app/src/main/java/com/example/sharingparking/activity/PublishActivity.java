package com.example.sharingparking.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import static com.example.sharingparking.common.Common.INPUT_NOT_COMPLETE;
import static com.example.sharingparking.utils.CommonUtil.toast;

/**
 * 发布车位
 * Created by Lizhiguo on 2018/4/17.
 */

public class PublishActivity extends SetTimeActivity {
    private int userId;
    private int lockId;

    //重写保存时间
    @Override
    protected void protectedSaveTime() {
        //检查是否为空
        if(isEmpty()){
            toast(PublishActivity.this,INPUT_NOT_COMPLETE);
        }else{
            //不为空，请求服务器注册
            requestPublish();
        }

    }

    private void requestPublish() {

    }

    private boolean isEmpty() {
        if("".equals(etStartTime) || "".equals(etEndTime)){
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //调用父类方法
        super.onCreate(savedInstanceState);

        userId = getIntent().getIntExtra("userId",0);
        lockId = getIntent().getIntExtra("lockId",0);

    }
}
