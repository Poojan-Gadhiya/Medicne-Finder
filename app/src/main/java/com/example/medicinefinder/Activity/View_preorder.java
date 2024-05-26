package com.example.medicinefinder.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.medicinefinder.Adapter.MyorderListAdapter;
import com.example.medicinefinder.Adapter.ViewPreorderAdapter;
import com.example.medicinefinder.Apis.Api;
import com.example.medicinefinder.Apis.ApiServices;
import com.example.medicinefinder.Model.OrderidlistModel;
import com.example.medicinefinder.Model.Result;
import com.example.medicinefinder.Model.SharedPref;
import com.example.medicinefinder.Model.ViewPreorderModel;
import com.example.medicinefinder.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.util.Collections.addAll;

public class View_preorder extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {


    ArrayList<ViewPreorderModel> viewPreorderModels = new ArrayList<>();
    ViewPreorderAdapter viewPreorderAdapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    SharedPref sharedPref = new SharedPref();
Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_preorder);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
        viewpreordelist();
    }

    void init() {
        recyclerView = findViewById(R.id.recylerview);
        viewPreorderAdapter = new ViewPreorderAdapter(viewPreorderModels, View_preorder.this, View_preorder.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(View_preorder.this));
        recyclerView.setAdapter(viewPreorderAdapter);
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

    void viewpreordelist()
    {
        final ProgressDialog progressDialog = new ProgressDialog(View_preorder.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices apiServices = retrofit.create(ApiServices.class);

        Call<Result> call = apiServices.view_preorder(sharedPref.getId(View_preorder.this));
        Log.d("USER_ID",sharedPref.getId(View_preorder.this));
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();

                if (response.body() != null) {

                    if (response.body().getSuccess())
                    {
                        viewPreorderModels.clear();
                        viewPreorderModels.addAll(response.body().getView_preorder_list());
                        Log.d("data","Dtata"+addAll(response.body().getView_preorder_list()));
                        viewPreorderAdapter.notifyDataSetChanged();

                    }
                    else
                    {
                        Toast.makeText(View_preorder.this, "Data not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(View_preorder.this, "Somethings are Wrong", Toast.LENGTH_SHORT).show();
                }
                viewPreorderAdapter.notifyDataSetChanged();
                Log.d("data","Dtata"+addAll(response.body().getView_preorder_list()));
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(View_preorder.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onRefresh() {
        onResume();
        viewpreordelist();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}