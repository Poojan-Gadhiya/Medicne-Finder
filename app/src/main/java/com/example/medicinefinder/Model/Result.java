package com.example.medicinefinder.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collection;

public class Result {


    @SerializedName("success")
    private Boolean success;

    @SerializedName("msg")
    private String msg;

    @SerializedName("profile_data")
    private ProfileModel profile_data;


    @SerializedName("medicine_list")
    private ArrayList<MedicineModel> medicine_list;

    @SerializedName("Pharmacy_list")
    private ArrayList<ChemistModel> Pharmacy_list;

    @SerializedName("profile")
    private ArrayList<ProfileModel> profile;

    @SerializedName("Cart_list")
    private ArrayList<Viewcartmodel> Cart_list;

    @SerializedName("Orderid_list")
    private ArrayList<OrderidlistModel> Orderid_list;

    @SerializedName("Order_Detail_list")
    private ArrayList<OrderdetailModel> Order_Detail_list;

    @SerializedName("preOrder_Detail_list")
    private ArrayList<PreorderCart> preOrder_Detail_list;

    @SerializedName("view_preorder_list")
    private ArrayList<ViewPreorderModel> view_preorder_list;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ProfileModel getProfile_data() {
        return profile_data;
    }

    public void setProfile_data(ProfileModel profile_data) {
        this.profile_data = profile_data;
    }

    public ArrayList<MedicineModel> getMedicine_list() {
        return medicine_list;
    }

    public void setMedicine_list(ArrayList<MedicineModel> medicine_list) { this.medicine_list = medicine_list; }

    public ArrayList<ChemistModel> getPharmacy_list() {
        return Pharmacy_list;
    }

    public void setPharmacy_list(ArrayList<ChemistModel> pharmacy_list) { Pharmacy_list = pharmacy_list; }

    public ArrayList<ProfileModel> getProfile() {
        return profile;
    }

    public void setProfile(ArrayList<ProfileModel> profile) {
        this.profile = profile;
    }

    public ArrayList<Viewcartmodel> getCart_list() {
        return Cart_list;
    }

    public void setCart_list(ArrayList<Viewcartmodel> cart_list) {
        Cart_list = cart_list;
    }

    public ArrayList<OrderidlistModel> getOrderid_list() { return Orderid_list; }

    public void setOrderid_list(ArrayList<OrderidlistModel> orderid_list) { Orderid_list = orderid_list; }

    public ArrayList<OrderdetailModel> getOrder_Detail_list() {
        return Order_Detail_list;
    }

    public void setOrder_Detail_list(ArrayList<OrderdetailModel> order_Detail_list) {
        Order_Detail_list = order_Detail_list;
    }


    public ArrayList<PreorderCart> getPreOrder_Detail_list() {
        return preOrder_Detail_list;
    }

    public void setPreOrder_Detail_list(ArrayList<PreorderCart> preOrder_Detail_list) {
        this.preOrder_Detail_list = preOrder_Detail_list;
    }

    public ArrayList<ViewPreorderModel> getView_preorder_list() {
        return view_preorder_list;
    }

    public void setView_preorder_list(ArrayList<ViewPreorderModel> view_preorder_list) {
        this.view_preorder_list = view_preorder_list;
    }


}
