package com.example.sharingparking.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sharingparking.R;
import com.example.sharingparking.entity.Publish;

import java.util.List;

/**
 * Created by Lizhiguo on 2018/4/17.
 */

public class PublishedAdapter extends RecyclerView.Adapter<PublishedAdapter.ViewHolder> {

    private Context mContext;

    private List<Publish> mPublishes;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView txtPublishNo;
        TextView txtLockNo;
        TextView txtStartTime;
        TextView txtEndTime;
        TextView txtParkingPrice;
        Button btnCancelPublish;

        public ViewHolder(View view){
            super(view);
            cardView = (CardView) view;
            txtPublishNo = (TextView) view.findViewById(R.id.txt_publish_no);
            txtLockNo = (TextView) view.findViewById(R.id.txt_lock_no);
            txtStartTime = (TextView) view.findViewById(R.id.txt_publish_start_time);
            txtEndTime = (TextView) view.findViewById(R.id.txt_publish_end_time);
            txtParkingPrice = (TextView) view.findViewById(R.id.txt_parking_price);
            btnCancelPublish = (Button) view.findViewById(R.id.btn_cancel_publish);


        }
    }


    public PublishedAdapter(List<Publish> publishes){
        this.mPublishes = publishes;
    }

    @Override
    public PublishedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_publish,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Publish publish = mPublishes.get(position);
        holder.txtPublishNo.setText(publish.getPublishId());
        holder.txtLockNo.setText(publish.getLockId());
        holder.txtStartTime.setText(publish.getPublishStartTime());
        holder.txtEndTime.setText(publish.getPublishEndTime());
        holder.txtParkingPrice.setText(publish.getParkingMoney() + "");

    }

    @Override
    public int getItemCount() {
        return mPublishes.size();
    }
}
