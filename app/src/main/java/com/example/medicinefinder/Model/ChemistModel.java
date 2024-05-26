package com.example.medicinefinder.Model;

import com.google.gson.annotations.SerializedName;

public class ChemistModel {


    @SerializedName("id")
    private String id;

    @SerializedName("Shopname")
    private String Shopname;

    @SerializedName("Email_id")
    private String Email_id;

    @SerializedName("Mobile_Number")
    private  String Mobile_Number;

    @SerializedName("Address")
    private String Address;

    @SerializedName("City")
    private String City;

    @SerializedName("State")
    private String State;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getShopname() { return Shopname; }

    public void setShopname(String shopname) { Shopname = shopname; }

    public String getEmail_id() { return Email_id; }

    public void setEmail_id(String email_id) { Email_id = email_id; }

    public String getMobile_Number() { return Mobile_Number; }

    public void setMobile_Number(String mobile_Number) { Mobile_Number = mobile_Number; }

    public String getAddress() { return Address; }

    public void setAddress(String address) { Address = address; }

    public String getCity() { return City; }

    public void setCity(String city) { City = city; }

    public String getState() { return State; }

    public void setState(String state) { State = state; }
}
