package com.example.medicinefinder.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.medicinefinder.Adapter.ViewcartAdapter;
import com.example.medicinefinder.Apis.Api;
import com.example.medicinefinder.Apis.ApiServices;
import com.example.medicinefinder.Model.Result;
import com.example.medicinefinder.Model.SharedPref;
import com.example.medicinefinder.Model.Viewcartmodel;
import com.example.medicinefinder.R;
import com.example.medicinefinder.databinding.ActivityViewCartBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class View_cart extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, ViewcartAdapter.Updateqty {

    ArrayList<Viewcartmodel> viewcartmodels = new ArrayList<>();
    ViewcartAdapter viewcartAdapter;
    RecyclerView recyclerView;
    SharedPref sharedPref = new SharedPref();
    SwipeRefreshLayout mSwipeRefreshLayout;
    String add_qty = "";
    String minus_qty = "";
    String minus_id = "";
    int Minus_qty = 0;
    LinearLayout cart_details;
    TextView Txt_total_price,total_mrp,total_quantity;
    ImageView cart_empty;
    Button btn_place_order,btn_cart_add_medicne;

    private ActivityViewCartBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    Toolbar toolbar;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        view_cart();

    }

    void init() {
        recyclerView = findViewById(R.id.recylerview);
        viewcartAdapter = new ViewcartAdapter(viewcartmodels, View_cart.this, View_cart.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(View_cart.this));
        recyclerView.setAdapter(viewcartAdapter);
        recyclerView.setVisibility(View.VISIBLE);
        Txt_total_price = findViewById(R.id.Txt_total_price);
        total_mrp = findViewById(R.id.total_mrp);
        total_quantity = findViewById(R.id.total_quantity);
        cart_details = findViewById(R.id.cart_details);
        cart_empty = findViewById(R.id.cart_empty);
        btn_place_order = findViewById(R.id.btn_place_order);
        btn_cart_add_medicne = findViewById(R.id.btn_cart_add_medicne);


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

        btn_place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(View_cart.this, Order.class));
            }
        });
    }

    void view_cart() {
        final ProgressDialog progressDialog = new ProgressDialog(View_cart.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices apiServices = retrofit.create(ApiServices.class);

        Call<Result> call = apiServices.View_cart(sharedPref.getId(View_cart.this));
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                viewcartmodels.clear();
                if (response.body() != null) {

                    if (response.body().getSuccess()) {
                        try {
                            viewcartmodels.addAll(response.body().getCart_list());
                        } catch (Exception e) {
                            Log.e("ERROR_TAG", "Error adding items to viewcartmodels: " + e.getMessage());
                        }


                        //viewcartmodels.addAll(response.body().getCart_list());
                        double total_price = 0;
                        for (int i = 0; i < viewcartmodels.size(); i++) {
                            double item_total_price = Double.parseDouble(viewcartmodels.get(i).getMedicine_qty()) * Double.parseDouble(viewcartmodels.get(i).getPrice());
                            total_price += item_total_price;
                        }
                        Log.d("TOTAL_PRICE", "" + total_price);
                        Txt_total_price.setText("₹" + (int) total_price);
                        total_mrp.setText("₹" + (int) total_price);

                        String total_qty="";
                        for(int i=0;i< viewcartAdapter.getItemCount();i++){
                            total_qty = String.valueOf(viewcartAdapter.getItemCount());
                        }
                        total_quantity.setText(total_qty);

                    } else {
                        cart_details.setVisibility(View.GONE);
                        cart_empty.setVisibility(View.VISIBLE);
                        cart_empty.setImageResource(R.drawable.emptycart);
                        btn_place_order.setClickable(false);
                        btn_cart_add_medicne.setVisibility(View.VISIBLE);
                        btn_cart_add_medicne.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(View_cart.this, Homenavigation.class));
                            }
                        });
                        //Toast.makeText(View_cart.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(View_cart.this, "Somethings are Wrong", Toast.LENGTH_SHORT).show();
                }
                viewcartAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(View_cart.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }



    void add_qty(String id, int qty) {
        final ProgressDialog progressDialog = new ProgressDialog(View_cart.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices apiServices = retrofit.create(ApiServices.class);
        Call<Result> call = apiServices.add_qty(id, add_qty = String.valueOf(qty + 1));
        call.enqueue(new Callback<Result>() {

            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    Log.d("RESPONSE_MESSAGE", "SIGN UP : " + response.body().getMsg());
                    if (response.body().getSuccess()) {
                        Toast.makeText(View_cart.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        view_cart();
                    } else {
                        Toast.makeText(View_cart.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(View_cart.this, "Somethings are Wrong", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(View_cart.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void minus_qty(String min_id, int Min_qty) {
//        final ProgressDialog progressDialog = new ProgressDialog(View_cart.this);
//        progressDialog.setMessage("Please wait...");
//        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices apiServices = retrofit.create(ApiServices.class);
        Call<Result> call = apiServices.minuse_qty(min_id, String.valueOf(Min_qty));

        call.enqueue(new Callback<Result>() {

            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
//                progressDialog.dismiss();
                if (response.body() != null) {
                    Log.d("RESPONSE_MESSAGE", "SIGN UP : " + response.body().getMsg());
                    if (response.body().getSuccess()) {
//                        Toast.makeText(View_cart.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        view_cart();
                    } else {
//                        Toast.makeText(View_cart.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(View_cart.this, "Somethings are Wrong", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<Result> call, Throwable t) {
//                progressDialog.dismiss();
                Toast.makeText(View_cart.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    void delteqty(String id, int qty) {
        final ProgressDialog progressDialog = new ProgressDialog(View_cart.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices apiServices = retrofit.create(ApiServices.class);

        Call<Result> call = apiServices.delte_medicine_qty(id);
        call.enqueue(new Callback<Result>() {


            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                if (response.body() != null) {

                    if (response.body().getSuccess()) {
                        Toast.makeText(View_cart.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        viewcartAdapter.notifyDataSetChanged();
                        view_cart();
                    } else {
                        Toast.makeText(View_cart.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(View_cart.this, "Somethings are Wrong", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(View_cart.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onRefresh() {
        onResume();
        view_cart();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void update_qty(String id, int qty) {
        minus_id = id;
        Minus_qty = qty;

        if (Minus_qty < 1) {
            delteqty(id, qty);
        } else {
            minus_qty(minus_id, Minus_qty);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}