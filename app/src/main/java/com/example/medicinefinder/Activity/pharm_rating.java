package com.example.medicinefinder.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.example.medicinefinder.Adapter.pharmacyAdapter;
import com.example.medicinefinder.Apis.Api;
import com.example.medicinefinder.Apis.ApiServices;
import com.example.medicinefinder.Model.ChemistModel;
import com.example.medicinefinder.Model.Result;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Toast;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.medicinefinder.R;
import com.example.medicinefinder.databinding.ActivityPharmRatingBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class pharm_rating extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private AppBarConfiguration appBarConfiguration;
    private ActivityPharmRatingBinding binding;

    ArrayList<ChemistModel> mr_pharmacy_models = new ArrayList<>();
    pharmacyAdapter pharmacyAdapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPharmRatingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        init();
        get_pharmacy_list();

    }
    void init()
    {
        recyclerView = findViewById(R.id.recylerview);
        pharmacyAdapter = new pharmacyAdapter(mr_pharmacy_models, pharm_rating.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(pharm_rating.this));
        recyclerView.setAdapter(pharmacyAdapter);

        recyclerView.setVisibility(View.VISIBLE);



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

    void filter(String text) {
        ArrayList<ChemistModel> temp = new ArrayList<>();

        temp.clear();
        for (ChemistModel data : mr_pharmacy_models) {
            if (data.getShopname().toUpperCase().contains(text)) {
                temp.add(data);
                pharmacyAdapter.UpdateList(temp);
                recyclerView.setVisibility(View.VISIBLE);
            }
        }

    }

    void get_pharmacy_list() {
        final ProgressDialog progressDialog = new ProgressDialog(pharm_rating.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices apiServices = retrofit.create(ApiServices.class);

        Call<Result> call = apiServices.get_pharmacy_list("");

        call.enqueue(new Callback<Result>() {

            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    if (response.body().getSuccess()) {
                        mr_pharmacy_models.clear();
                        mr_pharmacy_models.addAll(response.body().getPharmacy_list());
                        pharmacyAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(pharm_rating.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(pharm_rating.this, "Somethings are Wrong", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(pharm_rating.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public void onRefresh() {
        onResume();
        get_pharmacy_list();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}