package com.example.medicinefinder.Model;

import com.google.gson.annotations.SerializedName;

public class Viewcartmodel {

    @SerializedName("id")
    private String id;

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("medicine_name")
    private String medicine_name;

    @SerializedName("medicine_qty")
    private String medicine_qty;

    @SerializedName("price")
    private String price;

    @SerializedName("Shopname")
    private String Shopname;

    @SerializedName("Address")
    private String Address;

    @SerializedName("store_id")
    private String store_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMedicine_name() { return medicine_name; }

    public void setMedicine_name(String medicine_name) {
        this.medicine_name = medicine_name;
    }

    public String getMedicine_qty() {
        return medicine_qty;
    }

    public void setMedicine_qty(String medicine_qty) {
        this.medicine_qty = medicine_qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getShopname() {
        return Shopname;
    }

    public void setShopname(String shopname) {
        Shopname = shopname;
    }

    public String getAddress() { return Address; }

    public void setAddress(String address) { Address = address; }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }
}
