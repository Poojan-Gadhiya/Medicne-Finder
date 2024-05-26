package com.example.medicinefinder.Model;

import com.google.gson.annotations.SerializedName;

public class OrderidlistModel {


    @SerializedName("user_id")
    private String user_id;

    @SerializedName("Order_id")
    private String Order_id;

    @SerializedName("Order_date")
    private String Order_date;

    @SerializedName("Order_stage")
    private String Order_stage;


    @SerializedName("Order_total")
    private String Order_total;


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getOrder_id() { return Order_id; }

    public void setOrder_id(String order_id) {
        Order_id = order_id;
    }

    public String getOrder_date() {
        return Order_date;
    }

    public void setOrder_date(String order_date) {
        Order_date = order_date;
    }

    public String getOrder_stage() {
        return Order_stage;
    }

    public void setOrder_stage(String order_stage) {
        Order_stage = order_stage;
    }

    public String getOrder_total() {
        return Order_total;
    }

    public void setOrder_total(String order_total) {
        Order_total = order_total;
    }
}
