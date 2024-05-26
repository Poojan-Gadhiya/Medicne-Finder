package com.example.medicinefinder.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.medicinefinder.Adapter.MRChemistListAdapter;
import com.example.medicinefinder.Apis.Api;
import com.example.medicinefinder.Apis.ApiServices;
import com.example.medicinefinder.Model.ChemistModel;
import com.example.medicinefinder.Model.Result;
import com.example.medicinefinder.Model.SharedPref;
import com.example.medicinefinder.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Chemistlist extends Fragment implements SwipeRefreshLayout.OnRefreshListener, MRChemistListAdapter.ShowReviewDialog {

    ArrayList<ChemistModel> mr_chemist_models = new ArrayList<>();
    MRChemistListAdapter mrChemistListAdapter;
    RecyclerView recyclerView;
    SharedPref sharedPref = new SharedPref();
    String TAG = "DrList";

    String id;
    TextView no_data;
    EditText search_filter;
    SwipeRefreshLayout mSwipeRefreshLayout;

    Dialog dailog_rating_review;
    TextView txt_rating_txt;
    RatingBar rbStars;
    EditText edt_review;
    Button btn_Send_feedback,btn_preorder;
    String Chemist_id = "";

    public Chemistlist() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.drlist, container, false);
        init(view);

        no_data = view.findViewById(R.id.no_data);

        // get_Chemist_reports(sharedPref.getId(getContext()));
        return view;
    }

    void init(View view) {
        id = sharedPref.getId(getContext());
        recyclerView = view.findViewById(R.id.recylerview);
        mrChemistListAdapter = new MRChemistListAdapter(mr_chemist_models, getContext(), Chemistlist.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mrChemistListAdapter);
        search_filter = view.findViewById(R.id.search_filter);
        search_filter.setImeOptions(EditorInfo.IME_ACTION_DONE);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);



        dailog_rating_review = new Dialog(getActivity(), R.style.MyAlertDialogStyle);
        dailog_rating_review.setContentView(R.layout.dailog_rating_review);


        txt_rating_txt = dailog_rating_review.findViewById(R.id.txt_rating_txt);
        rbStars = dailog_rating_review.findViewById(R.id.rbStars);
        btn_Send_feedback = dailog_rating_review.findViewById(R.id.btn_Send_feedback);
        edt_review = dailog_rating_review.findViewById(R.id.edt_review);


        rbStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating == 0) {
                    txt_rating_txt.setText("Very Dissatisfied");
                } else if (rating == 1) {
                    txt_rating_txt.setText("Dissatisfied");
                } else if (rating == 2 || rating == 3) {
                    txt_rating_txt.setText("OK");
                } else if (rating == 4) {
                    txt_rating_txt.setText("Satisfied");
                } else if (rating == 5) {
                    txt_rating_txt.setText("Very Satisfied");
                } else {

                }
            }
        });

        btn_Send_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit_rating_rview();
            }
        });



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
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

     void submit_rating_rview() {

         final ProgressDialog progressDialog = new ProgressDialog(getActivity());
         progressDialog.setMessage("Please wait...");
         progressDialog.show();

         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl(Api.BASE_URL)
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();

         ApiServices apiServices = retrofit.create(ApiServices.class);

         Log.d("REQUEST", "SIGN UP : " + "mobile_no - " + rbStars.toString());
         Log.d("REQUEST", "SIGN UP : " + "edt_username - " + edt_review.getText().toString());
         Log.d("REQUEST", "SIGN UP : " + "password - " + Chemist_id.toString());

         Call<Result> call = apiServices.rating_review(String.valueOf(rbStars.getRating()), edt_review.getText().toString(), Chemist_id.toString());

         call.enqueue(new Callback<Result>() {
             @Override
             public void onResponse(Call<Result> call, Response<Result> response) {

                 progressDialog.dismiss();
                 if (response.body() != null)
                 {
                     Log.d("RESPONSE_MESSAGE", "SIGN UP : " + response.body().getMsg());
                     if (response.body().getSuccess())
                     {
                         Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                         dailog_rating_review.dismiss();

                     }
                     else
                     {
                         Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                     }
                 }
                 else
                 {
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


    void get_pharmacy_list() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
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
                        mr_chemist_models.clear();
                        mr_chemist_models.addAll(response.body().getPharmacy_list());
                        mrChemistListAdapter.notifyDataSetChanged();
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


    void filter(String text) {
        ArrayList<ChemistModel> temp = new ArrayList<>();

        temp.clear();
        for (ChemistModel data : mr_chemist_models) {
            if (data.getShopname().toUpperCase().contains(text)) {
                temp.add(data);
                mrChemistListAdapter.UpdateList(temp);
                recyclerView.setVisibility(View.VISIBLE);
            }
            else if (data.getAddress().toUpperCase().contains(text)) {
                temp.add(data);
                mrChemistListAdapter.UpdateList(temp);
                recyclerView.setVisibility(View.VISIBLE);
            }

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        get_pharmacy_list();
    }

    @Override
    public void onRefresh() {
        onResume();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void shoow_review_dialog(String chemist_id) {
//        dailog_rating_review = new Dialog(getActivity(), R.style.MyAlertDialogStyle);
//        dailog_rating_review.setContentView(R.layout.dailog_rating_review);

        dailog_rating_review.show();
        rbStars.setRating(0);
        edt_review.setText("");
        Chemist_id = chemist_id;
        Log.d("REQUEST", "SIGN UP : " + "mobile_no - " + Chemist_id.toString());

    }
}
