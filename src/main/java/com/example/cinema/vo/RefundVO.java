package com.example.cinema.vo;

import com.example.cinema.po.Refund;

public class RefundVO {
    private int id;
    private String description;
    private int sid;
    private int time;
    private int percent;

    public RefundVO(Refund refund){
        this.id = refund.getId();
        this.description = refund.getDescription();
        this.percent = refund.getPercent();
        this.sid = refund.getSid();
        this.time = refund.getTime();
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }
}
