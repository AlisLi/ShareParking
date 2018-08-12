package com.example.sharingparking.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.sharingparking.R;
import com.example.sharingparking.entity.Ordering;
import com.zhl.cbdialog.CBDialogBuilder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zyao89.view.zloading.ZLoadingDialog;

import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;

import static com.example.sharingparking.common.Common.LOCK_ORDERING_REQUEST_ERROR;
import static com.example.sharingparking.common.Common.NET_URL_HEADER;
import static com.example.sharingparking.utils.CommonUtil.calculateTimeDifference;
import static com.example.sharingparking.utils.CommonUtil.cancelSecondDialog;
import static com.example.sharingparking.utils.CommonUtil.initTitle;
import static com.example.sharingparking.utils.CommonUtil.toast;
import static com.example.sharingparking.utils.Utility.handleMessageResponse;
import static com.example.sharingparking.utils.Utility.handleOrderResponse;
import static com.zyao89.view.zloading.Z_TYPE.DOUBLE_CIRCLE;

/**
 * 设置预定时间
 * Created by Lizhiguo on 2018/4/25.
 */

public class OrderingSetTimeActivity extends SetTimeActivity{

    private String TAG = "OrderingSetTimeActivity";

    private int userId;
    private int publishId;
    private Double parkingPrice;
    private EditText etParkingPrice;
    private TextView txtParkingPrice;

    //加载Dialog第三方类
    private ZLoadingDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

    }

    private void init(){
        userId = getIntent().getIntExtra("userId",0);
        publishId = getIntent().getIntExtra("publishId",0);
        parkingPrice = getIntent().getDoubleExtra("expense",0);

        txtTitle = (TextView) findViewById(R.id.txt_title_common);
        initTitle(txtTitle,getIntent().getStringExtra("title_text"));

        etParkingPrice = (EditText) findViewById(R.id.et_parking_price);
        txtParkingPrice = (TextView) findViewById(R.id.txt_parking_price);
        txtParkingPrice.setVisibility(View.GONE);
        etParkingPrice.setVisibility(View.GONE);
    }

    //重写保存时间
    @Override
    protected void protectedSaveTime() {

        requestOrdered(publishId);
    }

    /**
     * 预订车位
     * @param publishId
     */
    private void requestOrdered(Integer publishId) {

        if(calculateTimeDifference(startTime,endTime) == -1){
            toast(OrderingSetTimeActivity.this,"输入错误！");
        }else {
            duringDialog("正在预定...");

            Double price = calculateTimeDifference(startTime,endTime) * parkingPrice;
            Log.d(TAG,startTime + "");
            Ordering ordering = new Ordering();
            ordering.setExpense(price);
            ordering.setUserId(userId);
            ordering.setPublishId(publishId);
            ordering.setStartTime(startTime);
            ordering.setEndTime(endTime);

            Log.d(TAG,JSON.toJSONString(ordering));
            OkHttpUtils
                    .postString()
                    .url(NET_URL_HEADER + "order/doorder")
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .content(JSON.toJSONString(ordering))
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            e.printStackTrace();
                            toast(OrderingSetTimeActivity.this,LOCK_ORDERING_REQUEST_ERROR);
                            dialog.cancel();
                            duringDialog("预定失败");
                            cancelSecondDialog(dialog);
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Log.d(TAG,response);

                            List<Ordering> list = handleOrderResponse(response);

                            if(list != null){
                                //预订成功
                                sessionDialog(OrderingSetTimeActivity.this,"预订成功","查看订单","取消");
                            }else {
                                dialog.cancel();
                                duringDialog(handleMessageResponse(response));
                                cancelSecondDialog(dialog);
                            }
                        }
                    });
        }


    }

    /**
     * 创建预订成功对话框
     * @param context
     * @param title
     * @param btnConfirmText
     * @param btnCancelText
     */
    private void sessionDialog(Context context,String title,String btnConfirmText,String btnCancelText){

        //不同样式
//        CBDialogBuilder.DIALOG_STYLE_NORMAL
//        CBDialogBuilder.DIALOG_STYLE_PROGRESS
//        CBDialogBuilder.DIALOG_STYLE_PROGRESS_TITANIC
//        CBDialogBuilder.DIALOG_STYLE_PROGRESS_AVLOADING

        new CBDialogBuilder(context)
                .setTouchOutSideCancelable(false)// 设置是否点击对话框以外的区域dismiss对话框
                .showCancelButton(true) //是否显示取消按钮
                .setTitle(title)
                .setConfirmButtonText(btnConfirmText)
                .setCancelButtonText(btnCancelText)
                .setDialogAnimation(CBDialogBuilder.DIALOG_ANIM_SLID_BOTTOM) //设置对话框的动画样式
                .setDialoglocation(CBDialogBuilder.MSG_LAYOUT_CENTER)  //设置对话框位于屏幕的位置
                .setButtonClickListener(true, new CBDialogBuilder.onDialogbtnClickListener() { //添加按钮回调监听
                    @Override
                    public void onDialogbtnClick(Context context, Dialog dialog, int whichBtn) {
                        switch (whichBtn) {
                            case BUTTON_CONFIRM:
                                //去支付
                                Intent intent = new Intent(OrderingSetTimeActivity.this,RentMessageActivity.class);
                                intent.putExtra("title_text",getResources().getText(R.string.ordering_detail));
                                intent.putExtra("userId",userId);
                                startActivity(intent);
                                break;
                            case BUTTON_CANCEL:
                                //取消

                                break;
                            default:
                                break;
                        }
                    }
                }).create().show();
    }

    //dialog动画
    private void duringDialog(String dialogText){
        dialog = new ZLoadingDialog(OrderingSetTimeActivity.this);
        dialog.setLoadingBuilder(DOUBLE_CIRCLE)//设置类型
                .setLoadingColor(Color.BLACK)//颜色
                .setHintText(dialogText)
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(Color.GRAY)  // 设置字体颜色
                .setCanceledOnTouchOutside(false)
                .show();
    }


}
