package com.example.medicinefinder.Model;

import com.google.gson.annotations.SerializedName;

public class ProfileModel {

    @SerializedName("id")
    private String id;

    @SerializedName("mobile_number")
    private String mobile_number;

    @SerializedName("Username")
    private String Username;

    @SerializedName("Password")
    private String Password;

    @SerializedName("Address")
    private String Address;

    @SerializedName("City")
    private String City;

    @SerializedName("State")
    private String State;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }


}
