package com.example.sharingparking.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sharingparking.R;

/**
 * Created by Lizhiguo on 2018/4/25.
 */

public class OrderingAdapter extends RecyclerView.Adapter<OrderingAdapter.ViewHolder> {


    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView txtLockNo;
        TextView txtStartTime;
        TextView txtEndTime;
        TextView txtParkingPrice;
        TextView txtParkingAddress;
        Button btnOrderingParking;

        public ViewHolder(View view){
            super(view);
            cardView = (CardView) view;
            txtLockNo = (TextView) view.findViewById(R.id.txt_lock_no);
            txtStartTime = (TextView) view.findViewById(R.id.txt_publish_start_time);
            txtEndTime = (TextView) view.findViewById(R.id.txt_publish_end_time);
            txtParkingPrice = (TextView) view.findViewById(R.id.txt_parking_price);
            txtParkingAddress = (TextView) view.findViewById(R.id.txt_parking_address);
            btnOrderingParking = (Button) view.findViewById(R.id.btn_ordering_parking);

        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }



}
