package com.example.cinema.vo;

import com.example.cinema.po.VIPChargeRecord;

import java.sql.Timestamp;

public class VIPChargeRecordVO {
    /**
     * 记录id
     */
    private int id;

    /**
     * 用户id
     */
    private int userId;

    /**
     * 会员卡id
     */
    private int VIPId;

    /**
     * 会员卡充值金额
     */
    private double amount;

    /**
     * 记录时间
     */
    private Timestamp recordTime;

    public VIPChargeRecordVO(VIPChargeRecord record) {
        this.id = record.getId();
        this.userId = record.getUserId();
        this.VIPId = record.getVIPId();
        this.amount = record.getAmount();
        this.recordTime = record.getRecordTime();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVIPId() {
        return VIPId;
    }

    public void setVIPId(int VIPId) {
        this.VIPId = VIPId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Timestamp getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Timestamp recordTime) {
        this.recordTime = recordTime;
    }
}
