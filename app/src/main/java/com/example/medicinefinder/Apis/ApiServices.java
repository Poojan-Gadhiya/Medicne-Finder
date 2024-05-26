package com.example.medicinefinder.Apis;


import com.example.medicinefinder.Model.Result;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiServices {

    @FormUrlEncoded
    @POST("patient_login.php")
    Call<Result> Login(
            @Field("mobile_number") String mobile_number,
            @Field("password") String password
    );


    @FormUrlEncoded
    @POST("patient_reg.php")
    Call<Result> patient_reg(
            @Field("mobile_number") String mobile_number,
            @Field("Username") String Username,
            @Field("Password") String Password,
            @Field("Address") String Address,
            @Field("City") String City,
            @Field("State") String State
    );

    @FormUrlEncoded
    @POST("get_medicine_by_patient.php")
    Call<Result> get_medicine_by_patient(
            @Field("mobile_number") String mobile_number
    );

    @FormUrlEncoded
    @POST("get_pharmacy_by_patient.php")
    Call<Result> get_pharmacy_list(
            @Field("mobile_number") String mobile_number
    );

    @FormUrlEncoded
    @POST("Patient_profile.php")
    Call<Result> Get_Profile(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("Edit_patient_profile.php")
    Call<Result> edit_patient_profile(
            @Field("id") String id,
            @Field("mobile_number") String mobile_number,
            @Field("Username") String Username,
            @Field("Address") String Address,
            @Field("City") String City,
            @Field("State") String State
    );

    @FormUrlEncoded
    @POST("Cart.php")
    Call<Result> add_cart(
            @Field("user_id") String user_id,
            @Field("store_id") String store_id,
            @Field("medicine_id") String medicine_id,
            @Field("medicine_qty") String medicine_qty,
            @Field("price") String price
    );

    @FormUrlEncoded
    @POST("Rating_review.php")
    Call<Result> rating_review(
            @Field("rating_star") String rating_star,
            @Field("review") String review,
            @Field("Chemist_id") String Chemist_id
    );

    @FormUrlEncoded
    @POST("view_cart.php")
    Call<Result> View_cart(
            @Field("user_id") String user_id
    );


    @FormUrlEncoded
    @POST("Add_medicine_qty.php")
    Call<Result> add_qty(
            @Field("id") String id,
            @Field("medicine_qty") String medicine_qty

    );


    @FormUrlEncoded
    @POST("Add_medicine_qty.php")
    Call<Result> minuse_qty(
            @Field("id") String id,
            @Field("medicine_qty") String medicine_qty

    );

    @FormUrlEncoded
    @POST("Delete_medicine_qty.php")
    Call<Result> delte_medicine_qty(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("place_order.php")
    Call<Result> place_order(
            @Field("user_id") String user_id,
            @Field("Order_id") String Order_id,
            @Field("is_orderd") String is_orderd,
            @Field("Oeder_status") String Oeder_status,
            @Field("Payment_type") String Payment_type,
            @Field("paymentstatues") String paymentstatues,
            @Field("Order_total") String Order_total,
            @Field("prescription_photo") String prescription_photo

    );

    @FormUrlEncoded
    @POST("get_orderid_by_patient.php")
    Call<Result> orderid_list(
            @Field("user_id") String user_id
    );


    @FormUrlEncoded
    @POST("get_order_by_patient.php")
    Call<Result> Order_Detail_list(
            @Field("user_id") String user_id,
            @Field("Order_id") String Order_id
    );


    @FormUrlEncoded
    @POST("add_medicine_to_preorder.php")
    Call<Result> add_medicine_to_preorder(
            @Field("store_id") String store_id,
            @Field("user_id") String user_id,
            @Field("medicine_name") String medicine_name,
            @Field("med_qty") String med_qty
    );

    @FormUrlEncoded
    @POST("get_preorder_by_pharmacy_user.php")
    Call<Result> get_preorder_by_pharmacy_user(
            @Field("store_id") String store_id,
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("place_preorder.php")
    Call<Result> place_preorder(
            @Field("store_id") String store_id,
            @Field("user_id") String user_id,
            @Field("Order_id") String Order_id,
            @Field("med_photo") String med_photo,
            @Field("Order_status") String Order_status
    );


    @FormUrlEncoded
    @POST("chnage_order_statues.php")
    Call<Result> cancel_order(
            @Field("Oeder_status") String store_id,
            @Field("Order_id") String user_id
    );

    @FormUrlEncoded
    @POST("Delete_Medicine_preorder_cart.php")
    Call<Result> Delte_Preorder_Medicine_cart(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("get_Preorder_by_patient.php")
    Call<Result> view_preorder(
            @Field("user_id") String user_id
    );

}
