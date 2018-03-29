package com.example.sharingparking.entity;

/**
 * 实体类：订单信息
 * Created by Lizhiguo on 2018/3/17.
 */

public class Ordering {
    private int OrderingId; //订单ID
    private Publish publish;    //发布信息
    private User user;  //租用者
    private String startTime;  //订单下单时间
    private String endTime;     //截止时间
    private int orderingState;  //订单状态
    private double expense;       //支付金额

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getOrderingState() {
        return orderingState;
    }

    public void setOrderingState(int orderingState) {
        this.orderingState = orderingState;
    }

    public double getExpense() {
        return expense;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }

    public Publish getPublish() {
        return publish;
    }

    public void setPublish(Publish publish) {
        this.publish = publish;
    }

    public int getOrderingId() {
        return OrderingId;
    }

    public void setOrderingId(int orderingId) {
        OrderingId = orderingId;
    }
}
