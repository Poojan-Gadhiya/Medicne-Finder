package com.example.medicinefinder.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.medicinefinder.Adapter.OrderdetailAdapter;
import com.example.medicinefinder.Apis.Api;
import com.example.medicinefinder.Apis.ApiServices;
import com.example.medicinefinder.Model.OrderdetailModel;
import com.example.medicinefinder.Model.Result;
import com.example.medicinefinder.Model.SharedPref;
import com.example.medicinefinder.R;
import com.example.medicinefinder.databinding.ActivityOrderDetailBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Order_detail extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    ArrayList<OrderdetailModel> orderdetailModels = new ArrayList<>();
    OrderdetailAdapter orderdetailAdapter;
    RecyclerView recyclerView;
    SharedPref sharedPref = new SharedPref();
    SwipeRefreshLayout mSwipeRefreshLayout;
    TextView Txt_total_price, total_mrp, Txt_Order_adress, Txt_Order_shopname, total_Order_quantity, total_order_mrp;
    TextView Txt_Orderid, Txt_payment_type, Txt_ispaid, Txt_orderid;
    String orderid;
    String total_qty = "";
    double total_price = 0;
    private AppBarConfiguration appBarConfiguration;
    private ActivityOrderDetailBinding binding;
    final int cancel_order_no=5;

    Button btn_cancel_order;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        orderid = getIntent().getExtras().getString("Orderid");
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        view_cart();

    }

    void init() {
        recyclerView = findViewById(R.id.recylerview);
        orderdetailAdapter = new OrderdetailAdapter(orderdetailModels, Order_detail.this, Order_detail.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(Order_detail.this));
        recyclerView.setAdapter(orderdetailAdapter);


        Txt_total_price = findViewById(R.id.Txt_total_price);
        Txt_Order_adress = findViewById(R.id.Txt_Order_adress);
        Txt_Order_shopname = findViewById(R.id.Txt_Order_shopname);
        total_Order_quantity = findViewById(R.id.total_Order_quantity);
        total_order_mrp = findViewById(R.id.total_order_mrp);
        total_mrp = findViewById(R.id.total_mrp);
        Txt_Orderid = findViewById(R.id.Txt_Orderid);
        Txt_payment_type = findViewById(R.id.Txt_payment_type);
        Txt_ispaid = findViewById(R.id.Txt_ispaid);
        Txt_orderid = findViewById(R.id.Txt_orderid);
        btn_cancel_order = findViewById(R.id.btn_cancel_order);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.green);

        mSwipeRefreshLayout.setOnRefreshListener(this);

        mSwipeRefreshLayout.post(new Runnable() {
                                     @Override
                                     public void run() {
                                         mSwipeRefreshLayout.setRefreshing(false);
                                         //Report_list.clear();
                                         // get_reports();
                                     }
                                 }
        );

        btn_cancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cnacel_order_patient();
            }
        });



    }

    void view_cart() {
        final ProgressDialog progressDialog = new ProgressDialog(Order_detail.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices apiServices = retrofit.create(ApiServices.class);

        Call<Result> call = apiServices.Order_Detail_list(sharedPref.getId(Order_detail.this), orderid);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                orderdetailModels.clear();
                if (response.body() != null) {
                    Log.d("ORDER_DATA", response.body().getOrder_Detail_list().get(0).getShopname());
                    if (response.body().getSuccess()) {
                        orderdetailModels.addAll(response.body().getOrder_Detail_list());
                        orderdetailAdapter.notifyDataSetChanged();
                        Txt_Orderid.setText(response.body().getOrder_Detail_list().get(0).getOrder_id());
                        Txt_Order_shopname.setText("Shopname:-" + response.body().getOrder_Detail_list().get(0).getShopname());
                        Txt_Order_adress.setText("Pickup Address:-" + response.body().getOrder_Detail_list().get(0).getAddress());
                        Txt_payment_type.setText(response.body().getOrder_Detail_list().get(0).getPayment_type());
                        Txt_ispaid.setText(response.body().getOrder_Detail_list().get(0).getIs_Paid());
                        Txt_orderid.setText(response.body().getOrder_Detail_list().get(0).getOrder_id());
                        total_order_mrp.setText(response.body().getOrder_Detail_list().get(0).getOrder_total());

//                        total_Order_quantity.setText("Pickup Address:-" + response.body().getOrder_Detail_list().get(0).getOrder_id());
//                        Txt_Order_adress.setText("Pickup Address:-" + response.body().getOrder_Detail_list().get(0).getAddress());
//                        Txt_Order_adress.setText("Pickup Address:-" + response.body().getOrder_Detail_list().get(0).getAddress());

//                        for (int i = 0; i < viewcartmodels.size(); i++) {
//                            double item_total_price = Double.parseDouble(viewcartmodels.get(i).getMedicine_qty()) * Double.parseDouble(viewcartmodels.get(i).getPrice());
//                            total_price += item_total_price;
//                        }
//                        Log.d("TOTAL_PRICE", "" + total_price);
//                        Txt_total_price.setText("₹" + (int) total_price);
//                        total_mrp.setText("₹" + (int) total_price);
//                        total_order_mrp.setText("₹" + (int) total_price);
                        for (int i = 0; i < orderdetailAdapter.getItemCount(); i++) {
                            total_qty = String.valueOf(orderdetailAdapter.getItemCount());
                        }
                        total_Order_quantity.setText(total_qty);
                    }
                } else {
                    Toast.makeText(Order_detail.this, "Somethings are Wrong", Toast.LENGTH_SHORT).show();
                }
                orderdetailAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Order_detail.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


   void Cnacel_order_patient()
    {
        final ProgressDialog progressDialog = new ProgressDialog(Order_detail.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices apiServices = retrofit.create(ApiServices.class);

        Call<Result> call = apiServices.cancel_order(String.valueOf(cancel_order_no),orderid);

        call.enqueue(new Callback<Result>() {

            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    if (response.body().getSuccess()) {
                        Toast.makeText(Order_detail.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(Order_detail.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Order_detail.this, "Somethings are Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Order_detail.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Order_detail.this, Homenavigation.class));
            }
        });
    }

    public void onRefresh() {
        onResume();
        view_cart();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}