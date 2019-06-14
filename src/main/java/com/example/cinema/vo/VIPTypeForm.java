package com.example.cinema.vo;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by liying on 2019/4/20.
 */
public class VIPTypeForm {
    /**
     * 会员卡id
     */
    private int id;
    /**
     * 会员卡名称
     */
    private String name;
    /**
     * 会员卡描述
     */
    private String description;
    /**
     * 会员卡价格
     */
    private double price;

    /**
     * 会员卡充值金额
     */
    private double amount;

    /**
     * 会员卡优惠金额
     */
    private double discount;

    public VIPTypeForm() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
