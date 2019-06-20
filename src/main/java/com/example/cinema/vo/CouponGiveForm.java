package com.example.cinema.vo;

public class CouponGiveForm {
    private int userId;
    private int couponId;

    CouponGiveForm(int a, int b){
        this.userId = a;
        this.couponId = b;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }
}
