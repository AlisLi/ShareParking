package com.example.sharingparking.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sharingparking.R;
import com.example.sharingparking.entity.Ordering;

import java.util.List;

import static com.example.sharingparking.common.Common.ORDERING_STATE;
import static com.example.sharingparking.utils.CommonUtil.dateToFormDate;
import static com.example.sharingparking.utils.CommonUtil.splitParkingAddress;

/**
 * 租用信息适配器
 * Created by Lizhiguo on 2018/3/17.
 */

public class RentMessageAdapter extends RecyclerView.Adapter<RentMessageAdapter.ViewHolder>{
    private String TAG = "RentMessageAdapter";

    private Context mContext;

    private List<Ordering> mOrderingList;

    private RentInterface mRentInterface;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
//        TextView txtParkingOwner;   //车位主
        TextView txtParkingAddress; //车位地址
        TextView txtParkingDetailAddress;//详细地址
//        TextView txtParkingOwnerPhone;//车位主联系方式
        TextView txtParkingPrice;//车位价格
        TextView txtParkingExpense;//费用
        TextView txtRentTime;//租用时间
        Button btnControl;
        Button btnConfirm;
        TextView txtOrderingId;//订单编号
        TextView txtParkingOwnerId;//车位主id
        TextView txtOrderingState;

        public ViewHolder(View view){
            super(view);
            cardView = (CardView) view;
//            txtParkingOwner = (TextView) view.findViewById(R.id.txt_parking_owner);
            txtOrderingId = (TextView) view.findViewById(R.id.txt_ordering_id);
            txtParkingOwnerId = (TextView) view.findViewById(R.id.txt_parking_owner_id);
            txtOrderingState = (TextView) view.findViewById(R.id.txt_ordering_state);
            txtParkingAddress = (TextView) view.findViewById(R.id.txt_parking_address);
            txtParkingDetailAddress = (TextView) view.findViewById(R.id.txt_parking_detail_address);
//            txtParkingOwnerPhone = (TextView) view.findViewById(R.id.txt_parking_owner_phone);
//            txtParkingPrice = (TextView) view.findViewById(R.id.txt_parking_price);
            txtParkingExpense = (TextView) view.findViewById(R.id.txt_rent_expense);
            txtRentTime = (TextView) view.findViewById(R.id.txt_rent_time);
            btnControl = (Button) view.findViewById(R.id.btn_rent_control);
            btnConfirm = (Button) view.findViewById(R.id.btn_rent_confirm);

        }
    }

    public RentMessageAdapter(List<Ordering> orderingList){
        this.mOrderingList = orderingList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_rentmessage,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ordering ordering = mOrderingList.get(position);
//        holder.txtParkingOwner.setText(ordering.getUser().getUserName());
//        holder.txtParkingOwnerPhone.setText(ordering.getUser().getPhoneNumber());
        holder.txtOrderingId.setText(ordering.getOrderingId() + "");
        holder.txtParkingOwnerId.setText(ordering.getPublish().getLock().getUser().getUserId() + "");
        Log.d(TAG,ordering.getPublish().getLock().getAddress() + "");
        String [] address = splitParkingAddress(ordering.getPublish().getLock().getAddress());
        holder.txtParkingAddress.setText(address[0]);
        holder.txtParkingDetailAddress.setText(address[1]);
//        holder.txtParkingPrice.setText(ordering.getPublish().getParkingMoney() + "");
        holder.txtParkingExpense.setText(ordering.getExpense() + "");
        holder.txtRentTime.setText(dateToFormDate(ordering.getStartTime()) + "-" + dateToFormDate(ordering.getEndTime()));

        if(ordering.getOrderingState() != 6){
            //如果订单没有支付或者不在使用时间段内，隐藏控制按钮
            holder.btnControl.setVisibility(View.INVISIBLE);

            if(ordering.getOrderingState() == 1){
                holder.btnConfirm.setText("去支付");
                holder.txtOrderingState.setText(ORDERING_STATE[1]);
            }else if(ordering.getOrderingState() == 2){
                //支付成功，但未到使用时间
                holder.btnConfirm.setVisibility(View.INVISIBLE);
                holder.txtOrderingState.setText(ORDERING_STATE[2]);

            }else{
                //订单失效的状态，去掉按钮
                holder.btnConfirm.setVisibility(View.GONE);
                holder.txtOrderingState.setText(ORDERING_STATE[3]);
            }
        }else{
            //状态6：正在使用（已经支付且在预定时间段内）
            holder.btnControl.setVisibility(View.VISIBLE);
            holder.btnConfirm.setVisibility(View.VISIBLE);
            holder.btnConfirm.setText("结束租用");
            holder.txtOrderingState.setText(ORDERING_STATE[6]);
        }

        holder.btnControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRentInterface.rentControlParking(ordering);
            }
        });

        holder.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mRentInterface.rentConfirm(ordering);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mOrderingList.size();
    }

    public interface RentInterface{
        //控制车位
        public void rentControlParking(Ordering ordering);
        //确定订单
        public void rentConfirm(Ordering ordering);
    }

    public void setRentInterface(RentInterface rentInterface){

        this.mRentInterface = rentInterface;

    }

}
