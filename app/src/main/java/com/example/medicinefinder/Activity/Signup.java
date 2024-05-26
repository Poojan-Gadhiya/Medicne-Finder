package com.example.medicinefinder.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.medicinefinder.Apis.Api;
import com.example.medicinefinder.Apis.ApiServices;
import com.example.medicinefinder.Model.Result;
import com.example.medicinefinder.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class  Signup extends AppCompatActivity {


    EditText edt_username, mobile_no, password, edt_State, edt_Address;
    Spinner city_spinner;
    Button btn_sign_up;
    Toolbar toolbar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        toolbar = findViewById(R.id.toolbar);
        init();
    }


    void init() {
        password = findViewById(R.id.password);
        edt_State = findViewById(R.id.edt_State);
        edt_Address = findViewById(R.id.edt_Address);
        city_spinner = findViewById(R.id.city_spinner);
        mobile_no = findViewById(R.id.mobile_no);
        edt_username = findViewById(R.id.edt_username);
        btn_sign_up = findViewById(R.id.btn_sign_up);
        btn_back_login = findViewById(R.id.btn_back_login);

        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate_data();
            }
        });

        btn_back_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signup.this, login.class));
            }
        });


    }

    void validate_data() {

        if (edt_username.getText().toString().equals("")) {
            edt_username.setError("Required");
        } else if (mobile_no.getText().toString().equals("")) {
            mobile_no.setError("Required");
        } else if (mobile_no.getText().toString().length() != 10) {
            mobile_no.setError("Enter Valid Mobile no.");
        } else if (password.getText().toString().equals("")) {
            password.setError("Required");
        } else if (edt_Address.getText().toString().equals("")) {
            edt_Address.setError("Required");
        } else if (edt_State.getText().toString().equals("")) {
            edt_State.setError("Required");
        } else if (city_spinner.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please Select City", Toast.LENGTH_SHORT).show();
        } else {
            do_signup();
        }
    }


    void do_signup() {
        final ProgressDialog progressDialog = new ProgressDialog(Signup.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices apiServices = retrofit.create(ApiServices.class);

        Log.d("REQUEST", "SIGN UP : "+"mobile_no - " + mobile_no.getText().toString());
        Log.d("REQUEST", "SIGN UP : "+"edt_username - " + edt_username.getText().toString());
        Log.d("REQUEST", "SIGN UP : "+"password - " + password.getText().toString());
        Log.d("REQUEST", "SIGN UP : "+"edt_Address - " +  edt_Address.getText().toString());
        Log.d("REQUEST", "SIGN UP : "+"city_spinner - " + city_spinner.getSelectedItem().toString().trim());
        Log.d("REQUEST", "SIGN UP : "+"edt_State - " + edt_State.getText().toString());

        Call<Result> call = apiServices.patient_reg(mobile_no.getText().toString(), edt_username.getText().toString(),
                password.getText().toString(), edt_Address.getText().toString(), city_spinner.getSelectedItem().toString().trim(),
                edt_State.getText().toString());

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    Log.d("RESPONSE_MESSAGE", "SIGN UP : " + response.body().getMsg());
                    if (response.body().getSuccess()) {
                        Toast.makeText(Signup.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Signup.this, login.class));
                        finish();
                    } else {
                        Toast.makeText(Signup.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Signup.this, "Somethings are Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Signup.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }    TextView btn_back_login;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
