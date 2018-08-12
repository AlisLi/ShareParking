package com.example.sharingparking.entity;

import java.util.Date;

/**
 * 实体类：订单信息
 * Created by Lizhiguo on 2018/3/17.
 */

public class Ordering {
    private Integer OrderingId; //订单ID
    private Publish mPublish;    //发布信息
    private User user;  //租用者
    private Date startTime;  //订单下单时间
    private Date endTime;     //截止时间
    private Integer orderingState;  //订单状态
    private double expense;       //支付金额
    private Integer publishId;
    private Integer userId;


    public Integer getPublishId() {
        return publishId;
    }

    public void setPublishId(Integer publishId) {
        this.publishId = publishId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrderingId() {
        return OrderingId;
    }

    public void setOrderingId(Integer orderingId) {
        OrderingId = orderingId;
    }

    public Publish getPublish() {
        return mPublish;
    }

    public void setPublish(Publish publish) {
        mPublish = publish;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getOrderingState() {
        return orderingState;
    }

    public void setOrderingState(Integer orderingState) {
        this.orderingState = orderingState;
    }

    public double getExpense() {
        return expense;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }
}
