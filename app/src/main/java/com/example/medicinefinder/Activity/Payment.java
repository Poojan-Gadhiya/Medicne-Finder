package com.example.medicinefinder.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.braintreepayments.cardform.view.CardForm;
import com.example.medicinefinder.Apis.Api;
import com.example.medicinefinder.Apis.ApiServices;
import com.example.medicinefinder.Model.Result;
import com.example.medicinefinder.Model.SharedPref;
import com.example.medicinefinder.R;
import com.example.medicinefinder.databinding.ActivityPaymentBinding;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Payment extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityPaymentBinding binding;

    AlertDialog.Builder alertBuilder;
    SharedPref sharedPref = new SharedPref();

    String payment_type,payment_statues,total_price,image_base64;

    StringBuilder Orderid = new StringBuilder(100);

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();

        Intent intent = this.getIntent();

//        orderid= intent.getStringExtra("Orderid");
//         orderid = bundle.getString("Orderid");
         payment_type = bundle.getString("paymenttype");
         payment_statues = bundle.getString("paymentstatues");
         total_price = bundle.getString("Totalprice");
         image_base64 = bundle.getString("image");


//        Toast.makeText(Payment.this,Orderid , Toast.LENGTH_LONG).show();



        CardForm cardForm = (CardForm) findViewById(R.id.card_form);
        Button buy = findViewById(R.id.btnBuy);
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .cardholderName(CardForm.FIELD_REQUIRED)
                .postalCodeRequired(true)
                .mobileNumberRequired(true)
                .mobileNumberExplanation("SMS is required on this number")
                .actionLabel("Purchase")
                .setup(Payment.this);
        cardForm.getCvvEditText().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cardForm.isValid()) {
                    alertBuilder = new AlertDialog.Builder(Payment.this);
                    alertBuilder.setTitle("Confirm before purchase");
                    alertBuilder.setMessage("Card number: " + cardForm.getCardNumber() + "\n" +
                            "Card expiry date: " + cardForm.getExpirationDateEditText().getText().toString() + "\n" +
                            "Card CVV: " + cardForm.getCvv() + "\n" +
                            "Postal code: " + cardForm.getPostalCode() + "\n" +
                            "Phone number: " + cardForm.getMobileNumber());
                    alertBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            final Random myRandom = new Random();
                            String random = String.valueOf(myRandom.nextInt(1000));
                            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
                            String currentDate = sdf.format(new Date());
                            Orderid.append("ORD");
                            Orderid.append(random);
                            Orderid.append(currentDate);
                            place_order(Orderid);


                        }
                    });
                    alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = alertBuilder.create();
                    alertDialog.show();

                } else {
                    Toast.makeText(Payment.this, "Please complete the form", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    void place_order(StringBuilder orderid)
    {
        final ProgressDialog progressDialog = new ProgressDialog(Payment.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices apiServices = retrofit.create(ApiServices.class);

        Call<Result> call = apiServices.place_order(sharedPref.getId(Payment.this), String.valueOf(orderid), String.valueOf(1), String.valueOf(1), payment_type, payment_statues, String.valueOf(total_price),image_base64);

        call.enqueue(new Callback<Result>() {

            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    Log.d("RESPONSE_MESSAGE", "SIGN UP : " + response.body().getMsg());
                    if (response.body().getSuccess()) {
                        Toast.makeText(Payment.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(Payment.this, "Thank you for purchase", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Payment.this, Homenavigation.class));

                    } else {
                        Toast.makeText(Payment.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Payment.this, "Somethings are Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Payment.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Payment.this, Homenavigation.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }


}