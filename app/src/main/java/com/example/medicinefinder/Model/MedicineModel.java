package com.example.medicinefinder.Model;

import com.google.gson.annotations.SerializedName;

public class MedicineModel {

    @SerializedName("id")
    private  String id;

    @SerializedName("medicine_name")
    private  String medicine_name;

    @SerializedName("medicine_description")
    private  String medicine_description;

    @SerializedName("price")
    private  String price;

    @SerializedName("med_tabs")
    private   String med_tabs;

    @SerializedName("med_mg_ml")
    private  String med_mg_ml;

    @SerializedName("med_stock")
    private   String med_stock;

    @SerializedName("store_id")
    private  String store_id;

    @SerializedName("Shopname")
    private  String Shopname;


    @SerializedName("Address")
    private  String Address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMedicine_name() {
        return medicine_name;
    }

    public void setMedicine_name(String medicine_name) {
        this.medicine_name = medicine_name;
    }

    public String getMedicine_description() {
        return medicine_description;
    }

    public void setMedicine_description(String medicine_description) {
        this.medicine_description = medicine_description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMed_tabs() {
        return med_tabs;
    }

    public void setMed_tabs(String med_tabs) {
        this.med_tabs = med_tabs;
    }

    public String getMed_mg_ml() {
        return med_mg_ml;
    }

    public void setMed_mg_ml(String med_mg_ml) {
        this.med_mg_ml = med_mg_ml;
    }

    public String getMed_stock() {
        return med_stock;
    }

    public void setMed_stock(String med_stock) {
        this.med_stock = med_stock;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getShopname() {
        return Shopname;
    }

    public void setShopname(String shopname) {
        Shopname = shopname;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
