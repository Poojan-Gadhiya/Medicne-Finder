package com.example.medicinefinder.Model;

import com.google.gson.annotations.SerializedName;

public class OrderdetailModel {


    @SerializedName("id")
    private int id;

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("medicine_name")
    private String medicine_name;

    @SerializedName("medicine_qty")
    private String medicine_qty;

    @SerializedName("price")
    private String price;

    @SerializedName("Order_id")
    private String Order_id;

    @SerializedName("Order_stage")
    private String Order_stage;

    @SerializedName("store_id")
    private String store_id;

    @SerializedName("Shopname")
    private String Shopname;

    @SerializedName("Address")
    private String Address;

    @SerializedName("payment_type")
    private String payment_type;

    @SerializedName("is_Paid")
    private String is_Paid;

    @SerializedName("Order_total")
    private String Order_total;

    @SerializedName("Order_date")
    private String Order_date;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getUser_id() { return user_id; }

    public void setUser_id(String user_id) { this.user_id = user_id; }

    public String getMedicine_name() { return medicine_name; }

    public void setMedicine_name(String medicine_name) { this.medicine_name = medicine_name; }

    public String getMedicine_qty() { return medicine_qty; }

    public void setMedicine_qty(String medicine_qty) { this.medicine_qty = medicine_qty; }

    public String getPrice() { return price; }

    public void setPrice(String price) { this.price = price; }

    public String getOrder_id() { return Order_id; }

    public void setOrder_id(String order_id) { Order_id = order_id; }

    public String getOrder_stage() { return Order_stage; }

    public void setOrder_stage(String order_stage) { Order_stage = order_stage; }

    public String getStore_id() { return store_id; }

    public void setStore_id(String store_id) { this.store_id = store_id; }

    public String getShopname() { return Shopname; }

    public void setShopname(String shopname) { Shopname = shopname; }

    public String getAddress() { return Address; }

    public void setAddress(String address) { Address = address; }

    public String getPayment_type() { return payment_type; }

    public void setPayment_type(String payment_type) { this.payment_type = payment_type; }

    public String getIs_Paid() { return is_Paid; }

    public void setIs_Paid(String is_Paid) { this.is_Paid = is_Paid; }

    public String getOrder_total() { return Order_total; }

    public void setOrder_total(String order_total) { Order_total = order_total; }

    public String getOrder_date() { return Order_date; }

    public void setOrder_date(String order_date) { Order_date = order_date; }


}
