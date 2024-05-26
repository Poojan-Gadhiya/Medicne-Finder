package com.example.medicinefinder.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.medicinefinder.Adapter.OrderAdapter;
import com.example.medicinefinder.Apis.Api;
import com.example.medicinefinder.Apis.ApiServices;
import com.example.medicinefinder.Model.Result;
import com.example.medicinefinder.Model.SharedPref;
import com.example.medicinefinder.Model.Viewcartmodel;
import com.example.medicinefinder.R;
import com.example.medicinefinder.databinding.ActivityOrderBinding;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Order extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final int SELECT_PICTURE = 1;
    ArrayList<Viewcartmodel> viewcartmodels = new ArrayList<>();
    OrderAdapter orderAdapter;
    RecyclerView recyclerView;
    SharedPref sharedPref = new SharedPref();
    SwipeRefreshLayout mSwipeRefreshLayout;
    TextView Txt_total_price, total_mrp, Txt_Order_adress, Txt_Order_shopname, total_Order_quantity, total_order_mrp,txt_is_Uploade_pres;
    Button btn_make_payment_or_order, btn_upload_prescription;
    RadioGroup radio_group_payment;
    RadioButton radio_card, radio_cod;
    String payment_type;
    String payment_statues;
    Bitmap bitmap_image;
    String image_base64 = "";
    Dialog dialog_select_image_choice;
    String total_qty = "";
    double total_price = 0;
    private ActivityOrderBinding binding;

    StringBuilder Orderid = new StringBuilder(100);

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    Toolbar toolbar;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderBinding.inflate(getLayoutInflater());
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
        dialog_select_image_choice = new Dialog(Order.this, R.style.MyAlertDialogStyle2);
        dialog_select_image_choice.setContentView(R.layout.dialog_image_pick);
        recyclerView = findViewById(R.id.recylerview);
        orderAdapter = new OrderAdapter(viewcartmodels, Order.this, Order.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(Order.this));
        recyclerView.setAdapter(orderAdapter);
        recyclerView.setVisibility(View.VISIBLE);
        Txt_total_price = findViewById(R.id.Txt_total_price);
        Txt_Order_adress = findViewById(R.id.Txt_Order_adress);
        txt_is_Uploade_pres = findViewById(R.id.txt_is_Uploade_pres);
        Txt_Order_shopname = findViewById(R.id.Txt_Order_shopname);
        total_Order_quantity = findViewById(R.id.total_Order_quantity);
        total_order_mrp = findViewById(R.id.total_order_mrp);
        total_mrp = findViewById(R.id.total_mrp);
        btn_make_payment_or_order = findViewById(R.id.btn_make_payment_or_order);
        radio_group_payment = findViewById(R.id.radio_group_payment);
        radio_card = findViewById(R.id.radio_card);
        btn_upload_prescription = findViewById(R.id.btn_upload_prescription);
        radio_cod = findViewById(R.id.radio_cod);

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


        btn_upload_prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_image();
            }
        });

        final RadioGroup group = (RadioGroup) findViewById(R.id.radio_group_payment);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = group.getCheckedRadioButtonId();
                switch (id) {
                    case R.id.radio_cod:
                        // Your code
                        payment_type = String.valueOf(1);
                        payment_statues = String.valueOf(1);
                        btn_make_payment_or_order.setText("Place Order");
                        // Toast.makeText(Order.this, Cod, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radio_card:
                        payment_type = String.valueOf(2);
                        payment_statues = String.valueOf(2);
                        btn_make_payment_or_order.setText("Make payment");
                        //Toast.makeText(Order.this, Card, Toast.LENGTH_SHORT).show();
                        // Your code
//                        radio_card.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                startActivity(new Intent(Order.this,Payment.class));
//                            }
//                        });

                        break;
                    default:
                        // Your code
                        break;
                }
            }

        });


        btn_make_payment_or_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radio_card.isChecked())
                {
                    Bundle bundle = new Bundle();
                    bundle.putString("image", image_base64);
                    bundle.putString("paymenttype", payment_type);
                    bundle.putString("paymentstatues", payment_statues);
                    bundle.putString("Totalprice", String.valueOf(total_price));
                    Intent intent = new Intent(Order.this, Payment.class);
                    intent.putExtras(bundle);
                    intent.putExtra("Orderid", String.valueOf(Orderid));
                    startActivity(intent);


                }
                else {


                    final Random myRandom = new Random();
                    String random = String.valueOf(myRandom.nextInt(1000));
                    SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
                    String currentDate = sdf.format(new Date());

                    Orderid.append("ORD");
                    Orderid.append(random);
                    Orderid.append(currentDate);
                    place_order(Orderid);
                }
            }
        });

    }

    void select_image() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // single image

        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == SELECT_PICTURE) {
                    if (data.getData() != null) {
                        Bitmap temp_bit_map = BitmapFactory.decodeStream(getContentResolver().openInputStream(data.getData()));
                        bitmap_image = temp_bit_map;
                        image_base64 = convert64(temp_bit_map);
                        txt_is_Uploade_pres.setVisibility(View.VISIBLE);
                        Toast.makeText(Order.this, "Prescription Selected...", Toast.LENGTH_SHORT).show();
                        Log.d("BASE64_IMAGE", "IMAGE DATA " + image_base64);
                    } else {
                        Toast.makeText(Order.this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                }  else {
                    Toast.makeText(Order.this, "Cancelled", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Order.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        dialog_select_image_choice.dismiss();
    }


    String convert64(Bitmap temp_img) {

        int nh = (int) (temp_img.getHeight() * (512.0 / temp_img.getWidth()));
        Bitmap scaled = Bitmap.createScaledBitmap(temp_img, 512, nh, true);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        scaled.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the bitmap object
        byte[] bimage = baos.toByteArray();
        String baseimage64 = Base64.encodeToString(bimage, Base64.DEFAULT);
        return baseimage64;
    }


    void view_cart() {
        final ProgressDialog progressDialog = new ProgressDialog(Order.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices apiServices = retrofit.create(ApiServices.class);

        Call<Result> call = apiServices.View_cart(sharedPref.getId(Order.this));

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                viewcartmodels.clear();
                if (response.body() != null) {

                    if (response.body().getSuccess()) {

                        viewcartmodels.addAll(response.body().getCart_list());
                        Txt_Order_shopname.setText("Shopname:-" + response.body().getCart_list().get(0).getShopname());
                        Txt_Order_adress.setText("Pickup Address:-" + response.body().getCart_list().get(0).getAddress());

                        for (int i = 0; i < viewcartmodels.size(); i++) {
                            double item_total_price = Double.parseDouble(viewcartmodels.get(i).getMedicine_qty()) * Double.parseDouble(viewcartmodels.get(i).getPrice());
                            total_price += item_total_price;
                        }
                        Log.d("TOTAL_PRICE", "" + total_price);
                        Txt_total_price.setText("₹" + (int) total_price);
                        total_mrp.setText("₹" + (int) total_price);
                        total_order_mrp.setText("₹" + (int) total_price);


                        for (int i = 0; i < orderAdapter.getItemCount(); i++) {
                            total_qty = String.valueOf(orderAdapter.getItemCount());
                        }
                        total_Order_quantity.setText(total_qty);
                    }
                } else {
                    Toast.makeText(Order.this, "Somethings are Wrong", Toast.LENGTH_SHORT).show();
                }
                orderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Order.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void place_order(StringBuilder orderid) {
        final ProgressDialog progressDialog = new ProgressDialog(Order.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices apiServices = retrofit.create(ApiServices.class);
        Log.d("DATE", "" + orderid);

        Call<Result> call = apiServices.place_order(sharedPref.getId(Order.this), orderid.toString(), String.valueOf(1), String.valueOf(1), payment_type, payment_statues, String.valueOf(total_price),image_base64);

        call.enqueue(new Callback<Result>() {

            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    Log.d("RESPONSE_MESSAGE", "SIGN UP : " + response.body().getMsg());
                    if (response.body().getSuccess()) {
                        Toast.makeText(Order.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Order.this, Homenavigation.class));

                    } else {
                        Toast.makeText(Order.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Order.this, "Somethings are Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Order.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Order.this, Homenavigation.class));
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