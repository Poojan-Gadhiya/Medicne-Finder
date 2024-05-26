package com.example.medicinefinder.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicinefinder.Model.ChemistModel;
import com.example.medicinefinder.R;

import java.util.ArrayList;

public class pharmacyAdapter extends RecyclerView.Adapter<pharmacyAdapter.ViewHolder> {
    ArrayList<ChemistModel> mr_pharmacy_models;
    ChemistModel model;
    Context context;


    public pharmacyAdapter(ArrayList<ChemistModel> mr_pharmacy_models, Context context) {
        this.mr_pharmacy_models = mr_pharmacy_models;
        this.context = context;
    }

    @NonNull
    @Override
    public pharmacyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_pharm, parent, false);
        pharmacyAdapter.ViewHolder viewHolder = new pharmacyAdapter.ViewHolder(listItem);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull pharmacyAdapter.ViewHolder holder, int position) {
        model = mr_pharmacy_models.get(position);
        holder.feedbck_txt_Shopname.setText("username:-" + model.getShopname());
        holder.feedback_Txt_address.setText("ratings-" + model.getAddress());

//        int count_done = Integer.parseInt(model.getTotal_visit()) - Integer.parseInt(model.getRemain_visit());
//        holder.counts.setText(count_done + " / " + model.getTotal_visit() + " Completed");
//        holder.counts.setText(count_done + " / " + model.getTotal_visit() + " Completed");

//
//        holder. rbStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                if(rating==0)
//                {
//                    holder.txt_rating_txt.setText("Very Dissatisfied");
//                }
//                else if(rating==1)
//                {
//                    holder. txt_rating_txt.setText("Dissatisfied");
//                }
//                else if(rating==2 || rating==3)
//                {
//                    holder. txt_rating_txt.setText("OK");
//                }
//                else if(rating==4)
//                {
//                    holder.txt_rating_txt.setText("Satisfied");
//                }
//                else if(rating==5)
//                {
//                    holder. txt_rating_txt.setText("Very Satisfied");
//                }
//                else
//                {
//
//                }
//            }
//        });
//
//        holder. btn_Send_feedback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "Feedback submitted", Toast.LENGTH_SHORT).show();
//
//            }
//        });



    }

    @Override
    public int getItemCount() {
        return mr_pharmacy_models.size();
    }

    public void UpdateList(ArrayList<ChemistModel> list) {
        mr_pharmacy_models = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView feedbck_txt_Shopname, feedback_Txt_address, counts;
        Button rating_review_btn;
        Dialog dailog_rating_review;

//        TextView txt_rating_txt;
//        RatingBar rbStars;
//        EditText edt_review;
//        Button btn_Send_feedback;



        public ViewHolder(View list) {
            super(list);
            feedbck_txt_Shopname = list.findViewById(R.id.feedbck_txt_Shopname);
            feedback_Txt_address = list.findViewById(R.id.feedback_Txt_address);
            counts = list.findViewById(R.id.counts);
            rating_review_btn = list.findViewById(R.id.rating_review_btn);




            dailog_rating_review = new Dialog(context, R.style.MyAlertDialogStyle);
            dailog_rating_review.setContentView(R.layout.dailog_rating_review);
            rating_review_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dailog_rating_review.setContentView(R.layout.dailog_rating_review);
                    dailog_rating_review.show();
                }
            });

//            txt_rating_txt = dailog_rating_review.findViewById(R.id.tvFeedback);
//            rbStars = dailog_rating_review.findViewById(R.id.rbStars);
//            btn_Send_feedback = dailog_rating_review.findViewById(R.id.btnSend);
//
//
//            rbStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//                @Override
//                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                    if(rating==0)
//                    {
//                        txt_rating_txt.setText("Very Dissatisfied");
//                    }
//                    else if(rating==1)
//                    {
//                        txt_rating_txt.setText("Dissatisfied");
//                    }
//                    else if(rating==2 || rating==3)
//                    {
//                        txt_rating_txt.setText("OK");
//                    }
//                    else if(rating==4)
//                    {
//                        txt_rating_txt.setText("Satisfied");
//                    }
//                    else if(rating==5)
//                    {
//                        txt_rating_txt.setText("Very Satisfied");
//                    }
//                    else
//                    {
//
//                    }
//                }
//            });
//
//            btn_Send_feedback.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(context, "Feedback submitted", Toast.LENGTH_SHORT).show();
//
//                }
//            });






        }
    }
}
