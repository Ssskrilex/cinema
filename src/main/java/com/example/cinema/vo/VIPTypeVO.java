package com.example.cinema.vo;

import com.example.cinema.po.VIPType;

public class VIPTypeVO {
    private int id;
    private  String name;
    private  String description;
    private double price;
    private double amount;
    private double discount;

    public VIPTypeVO(VIPType vipType) {
        this.id = vipType.getId();
        this.name = vipType.getName();
        this.description = vipType.getDescription();
        this.price = vipType.getPrice();
        this.amount = vipType.getAmount();
        this.discount = vipType.getDiscount();

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
