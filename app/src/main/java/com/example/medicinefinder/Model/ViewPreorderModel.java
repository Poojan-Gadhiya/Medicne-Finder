package com.example.medicinefinder.Model;

import com.google.gson.annotations.SerializedName;

public class ViewPreorderModel {

    @SerializedName("id")
    private String id;

    @SerializedName("medicine_name")
    private String medicine_name;

    @SerializedName("med_qty")
    private String med_qty;

    @SerializedName("Order_stage")
    private String Order_stage;

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

    public String getMed_qty() {
        return med_qty;
    }

    public void setMed_qty(String med_qty) {
        this.med_qty = med_qty;
    }

    public String getOrder_stage() {
        return Order_stage;
    }

    public void setOrder_stage(String order_stage) {
        Order_stage = order_stage;
    }
}
