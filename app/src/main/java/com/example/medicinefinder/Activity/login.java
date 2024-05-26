package com.example.medicinefinder.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.medicinefinder.Apis.Api;
import com.example.medicinefinder.Apis.ApiServices;
import com.example.medicinefinder.Model.Result;
import com.example.medicinefinder.Model.SharedPref;
import com.example.medicinefinder.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class login extends AppCompatActivity {

    Button btn_login;
    EditText mobile_no, password;
    TextView txt_create_account, txt_forgot_password;
    ListView list_item;
    ArrayList<String> city_name = new ArrayList<>();
    ArrayAdapter adapter;

    Dialog forgot_dialog;

    SharedPref sharedPref=new SharedPref();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        check_login();
    }

    private void check_login() {
        if (SharedPref.getLoggedStatus(context)){
            startActivity(new Intent(context,Homenavigation.class));
            finish();
        }
    }

    void init() {
        context = login.this;
        btn_login = findViewById(R.id.btn_login);
        mobile_no = findViewById(R.id.mobile_no);//all this is used to interact with xml and java
        password = findViewById(R.id.password);

        forgot_dialog = new Dialog(login.this, R.style.MyAlertDialogStyle);
        forgot_dialog.setContentView(R.layout.dialog_forgot_password);

        txt_create_account = findViewById(R.id.txt_create_account);
        txt_forgot_password = findViewById(R.id.txt_forgot_password);


        adapter = new ArrayAdapter(login.this, android.R.layout.simple_list_item_1, city_name);

        txt_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, Signup.class));
            }
        });

        txt_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgot_dialog.show();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate_data();
            }
        });


    }
    void validate_data() {

        if (mobile_no.getText().toString().equals("")) {
            mobile_no.setError("Required");
        } else if (mobile_no.getText().toString().length() != 10) {
            mobile_no.setError("Enter valid mobile no.");
        } else if (password.getText().toString().equals("")) {
            password.setError("Required");
        } else {
            do_login();
        }

    }



    void do_login() {
        final ProgressDialog progressDialog = new ProgressDialog(login.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices apiServices = retrofit.create(ApiServices.class);

        Log.d("REQUEST", "SIGN UP : " + "mobile_no - " + mobile_no.getText().toString());
        Log.d("REQUEST", "SIGN UP : " + "password - " + password.getText().toString());
        Call<Result> call = apiServices.Login(mobile_no.getText().toString(), password.getText().toString());

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    Log.d("RESPONSE_MESSAGE", "Login : " + response.body().getMsg());
                    if (response.body().getSuccess()) {
                        Toast.makeText(login.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        sharedPref.set_profile(context,response.body().getProfile_data().getId(),response.body().getProfile_data().getMobile_number(),response.body().getProfile_data().getUsername());
                        sharedPref.setLoggedIn(context,true);
                        startActivity(new Intent(login.this, Homenavigation.class));
                        finish();
                    } else {
                        Toast.makeText(login.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(login.this, "Somethings are Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(login.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}