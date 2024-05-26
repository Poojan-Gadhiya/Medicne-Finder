package com.example.medicinefinder.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.medicinefinder.Apis.Api;
import com.example.medicinefinder.Apis.ApiServices;
import com.example.medicinefinder.Model.ProfileModel;
import com.example.medicinefinder.Model.Result;
import com.example.medicinefinder.Model.SharedPref;
import com.example.medicinefinder.R;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.medicinefinder.databinding.ActivityProfileBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Profile extends AppCompatActivity {

    ArrayList<ProfileModel> medicineModels = new ArrayList<>();
    EditText profile_edt_username,
            profile_edt_mobile_no,
            profile_edt_city,
            profile_edt_Address,
            profile_edt_State;

    Button btn_save_profile;

    TextView mr_name, mr_number;
    SharedPref sharedPref = new SharedPref();

    private AppBarConfiguration appBarConfiguration;
    private ActivityProfileBinding binding;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_profile);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        getprofile();

    }

    void init() {
        profile_edt_username = findViewById(R.id.profile_edt_username);
        profile_edt_mobile_no = findViewById(R.id.profile_edt_mobile_no);
        profile_edt_city = findViewById(R.id.profile_edt_city);
        profile_edt_Address = findViewById(R.id.profile_edt_Address);
        profile_edt_State = findViewById(R.id.profile_edt_State);
        btn_save_profile = findViewById(R.id.btn_save_profile);


        btn_save_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_patient_profile();
            }

        });
    }

    void getprofile() {
        final ProgressDialog progressDialog = new ProgressDialog(Profile.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices apiServices = retrofit.create(ApiServices.class);
        Log.d("PROFILE_TAG", "USER_ID : " + sharedPref.getId(Profile.this));
        Call<Result> call = apiServices.Get_Profile(sharedPref.getId(Profile.this));

        call.enqueue(new Callback<Result>() {

            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                if (response.body() != null) {

                    if (response.body().getSuccess()) {
                        profile_edt_username.setText(response.body().getProfile().get(0).getUsername());
                        profile_edt_mobile_no.setText(response.body().getProfile().get(0).getMobile_number());
                        profile_edt_mobile_no.setEnabled(false);
                        profile_edt_Address.setText(response.body().getProfile().get(0).getAddress());
                        profile_edt_city.setText(response.body().getProfile().get(0).getCity());
                        profile_edt_State.setText(response.body().getProfile().get(0).getState());
                    } else {
                        Toast.makeText(Profile.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(Profile.this, "Somethings are Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Profile.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    void edit_patient_profile() {
        final ProgressDialog progressDialog = new ProgressDialog(Profile.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices apiServices = retrofit.create(ApiServices.class);
        Log.d("REQUEST", "SIGN UP : " + "mobile_no - " + profile_edt_username.getText().toString());

        Call<Result> call = apiServices.edit_patient_profile(sharedPref.getId(Profile.this), profile_edt_mobile_no.getText().toString(), profile_edt_username.getText().toString()
                , profile_edt_Address.getText().toString(), profile_edt_city.getText().toString(),
                profile_edt_State.getText().toString());

        call.enqueue(new Callback<Result>() {

            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    Log.d("RESPONSE_MESSAGE", "SIGN UP : " + response.body().getMsg());
                    if (response.body().getSuccess()) {
                        Toast.makeText(Profile.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(Profile.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Profile.this, "Somethings are Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Profile.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Profile.this, Homenavigation.class));
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}