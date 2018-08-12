package com.example.sharingparking.entity.tempBean;

import java.io.Serializable;
import java.util.Date;

public class Ordering implements Serializable {
    private Integer orderingId;     //订单ID
    private User user;              //租用者
    private ParkingLock lock;       //车位锁
    private Date startTime;         //订单开始时间
    private Date endTime;           //截止时间
    private Integer orderingState;  //订单状态 1:未支付 2:支付成功 3:超时未支付 4:用户在未支付的情况下取消了 5:未支付的时候发布已经取消了
                                    //同理    11 12 13 14 15
    private Double expense;         //支付金额

    @Override
    public String toString() {
        return "Ordering{" +
                "orderingId=" + orderingId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", orderingState=" + orderingState +
                ", expense=" + expense +
                '}';
    }

    public void setOrderingId(Integer orderingId) {
        this.orderingId = orderingId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setLock(ParkingLock lock) {
        this.lock = lock;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setOrderingState(Integer orderingState) {
        this.orderingState = orderingState;
    }

    public void setExpense(Double expense) {
        this.expense = expense;
    }

    public Integer getOrderingId() {

        return orderingId;
    }

    public User getUser() {
        return user;
    }

    public ParkingLock getLock() {
        return lock;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Integer getOrderingState() {
        return orderingState;
    }

    public Double getExpense() {
        return expense;
    }
}
