package com.example.medicinefinder.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.medicinefinder.Adapter.MyorderListAdapter;
import com.example.medicinefinder.Apis.Api;
import com.example.medicinefinder.Apis.ApiServices;
import com.example.medicinefinder.Model.OrderidlistModel;
import com.example.medicinefinder.Model.Result;
import com.example.medicinefinder.Model.SharedPref;
import com.example.medicinefinder.R;
import com.example.medicinefinder.databinding.ActivityMyorderBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Myorder extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, MyorderListAdapter.Orderdetail {

    ArrayList<OrderidlistModel> orderidlistModels = new ArrayList<>();
    MyorderListAdapter myorderListAdapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    SharedPref sharedPref = new SharedPref();

    TextView Txt_Orderid, Txr_orderdate, Txt_orderstage, Txt_order_price, Txt_order_detail;

    private AppBarConfiguration appBarConfiguration;
    private ActivityMyorderBinding binding;
    Toolbar toolbar;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyorderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        orderidlist();

    }

    void init() {
        recyclerView = findViewById(R.id.recylerview);
        myorderListAdapter = new MyorderListAdapter(orderidlistModels, Myorder.this, Myorder.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(Myorder.this));
        recyclerView.setAdapter(myorderListAdapter);
        recyclerView.setVisibility(View.VISIBLE);

        Txt_Orderid = findViewById(R.id.Txt_Orderid);
        Txr_orderdate = findViewById(R.id.Txr_orderdate);
        Txt_orderstage = findViewById(R.id.Txt_orderstage);
        Txt_order_price = findViewById(R.id.Txt_order_price);


        Txt_order_detail = findViewById(R.id.Txt_order_detail);


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
    }

    void orderidlist() {
        final ProgressDialog progressDialog = new ProgressDialog(Myorder.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices apiServices = retrofit.create(ApiServices.class);

        Call<Result> call = apiServices.orderid_list(sharedPref.getId(Myorder.this));

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                orderidlistModels.clear();
                if (response.body() != null) {

                    if (response.body().getSuccess()) {

                        orderidlistModels.addAll(response.body().getOrderid_list());
//                        if(response.body().getOrderid_list().get(0).getOrder_stage()=="Canceld by patient")
//                        {
//
//
//                        }
//                        Txt_Orderid.setText(response.body().getOrderid_list().get(0).getOrder_id());
//                        Txr_orderdate.setText(response.body().getOrderid_list().get(0).getOrder_date());
//                        Txt_order_price.setText(response.body().getOrderid_list().get(0).getOrder_total());
//                        Txt_orderstage.setText(response.body().getOrderid_list().get(0).getOrder_stage());
                    }
                } else {
                    Toast.makeText(Myorder.this, "Somethings are Wrong", Toast.LENGTH_SHORT).show();
                }
                myorderListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Myorder.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onRefresh() {
        onResume();
        orderidlist();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void order_datail(String orderid) {
        Log.d("order_id", "" + orderid);
        Intent intent = new Intent(Myorder.this, Order_detail.class);
        intent.putExtra("Orderid", orderid);
        Log.d("order_id", "" + orderid);
        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }


}