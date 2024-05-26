package com.example.medicinefinder.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.example.medicinefinder.Adapter.MedicineListAdapter;
import com.example.medicinefinder.Apis.Api;
import com.example.medicinefinder.Apis.ApiServices;
import com.example.medicinefinder.Model.MedicineModel;
import com.example.medicinefinder.Model.Result;
import com.example.medicinefinder.Model.SharedPref;
import com.example.medicinefinder.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Medicine extends Fragment implements SwipeRefreshLayout.OnRefreshListener, MedicineListAdapter.Addtocart{

    ArrayList<MedicineModel> medicineModels = new ArrayList<>();
    MedicineListAdapter mrDoctorReportsAdapter;
    RecyclerView recyclerView;
    SharedPref sharedPref = new SharedPref();
    String TAG = "DrList";
    TextView no_data;
    LinearLayout linear_Txt_addtocart;

    String id;
    EditText search_filter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    String Medicine_id = "";
    String Medicine_price = "";
    MedicineModel medicineModel;
    String medicine_qty = "1";

    public Medicine() {

        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.drlist, container, false);
        init(view);
        no_data = view.findViewById(R.id.no_data);
        linear_Txt_addtocart = view.findViewById(R.id.linear_Txt_addtocart);
        return view;
    }

    void init(View view) {
        id = sharedPref.getId(getContext());
        recyclerView = view.findViewById(R.id.recylerview);
        mrDoctorReportsAdapter = new MedicineListAdapter(medicineModels, getContext(), Medicine.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mrDoctorReportsAdapter);
        search_filter = view.findViewById(R.id.search_filter);
        search_filter.setImeOptions(EditorInfo.IME_ACTION_DONE);
        recyclerView.setVisibility(View.VISIBLE);


        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeToRefresh);

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

        search_filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString().toUpperCase());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    void get_medicine_list() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices apiServices = retrofit.create(ApiServices.class);

        Call<Result> call = apiServices.get_medicine_by_patient("");

        call.enqueue(new Callback<Result>() {


            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                if (response.body() != null) {

                    if (response.body().getSuccess()) {
                        medicineModels.clear();
                        medicineModels.addAll(response.body().getMedicine_list());
                        mrDoctorReportsAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Somethings are Wrong", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void addtocart() {


    }


    @Override
    public void onResume() {
        super.onResume();
//        get_Doctors_reports(sharedPref.getId(getContext()));
        get_medicine_list();
    }

    void filter(String text) {
        ArrayList<MedicineModel> temp = new ArrayList<>();

        temp.clear();
        for (MedicineModel data : medicineModels) {
            if (data.getMedicine_name().toUpperCase().contains(text))   {
                temp.add(data);
                mrDoctorReportsAdapter.UpdateList(temp);
                recyclerView.setVisibility(View.VISIBLE);
            }
            else if (data.getAddress().toUpperCase().contains(text)) {
                temp.add(data);
                mrDoctorReportsAdapter.UpdateList(temp);
                recyclerView.setVisibility(View.VISIBLE);
            }
            else if(data.getShopname().toUpperCase().contains(text)) {
                temp.add(data);
                mrDoctorReportsAdapter.UpdateList(temp);
                recyclerView.setVisibility(View.VISIBLE);
            }

        }

    }

    @Override
    public void onRefresh() {
        onResume();
        mSwipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void add_to_cart(String med_id, String store_id, String price) {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices apiServices = retrofit.create(ApiServices.class);

        Call<Result> call = apiServices.add_cart(sharedPref.getId(getActivity()), store_id, med_id, "1", price);


        call.enqueue(new Callback<Result>() {


            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    if (response.body().getSuccess()) {
                        //Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                        linear_Txt_addtocart.setVisibility(View.VISIBLE);
                        linear_Txt_addtocart.postDelayed(new Runnable() {
                            public void run() {
                                linear_Txt_addtocart.setVisibility(View.GONE);
                            }
                        }, 3000);

                    } else {
                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Somethings are Wrong", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void check_already_add_medicine(String med_id) {
//        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage("Please wait...");
//        progressDialog.show();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Api.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        ApiServices apiServices = retrofit.create(ApiServices.class);


    }
}
