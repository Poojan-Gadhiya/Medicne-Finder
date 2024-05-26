package com.example.medicinefinder.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicinefinder.Activity.Myorder;
import com.example.medicinefinder.Activity.Order_detail;
import com.example.medicinefinder.Activity.Preorder;
import com.example.medicinefinder.Fragment.Chemistlist;
import com.example.medicinefinder.Model.ChemistModel;
import com.example.medicinefinder.R;

import java.util.ArrayList;

public class MRChemistListAdapter extends RecyclerView.Adapter<MRChemistListAdapter.ViewHolder> {
    ArrayList<ChemistModel> mr_chemist_models;
    ChemistModel model;
    Context context;
    Fragment fragment;
    ShowReviewDialog showReviewDialog;

    public MRChemistListAdapter(ArrayList<ChemistModel> mr_chemist_models, Context context, Chemistlist chemistlist) {
        this.mr_chemist_models = mr_chemist_models;
        this.context = context;
        this.fragment = chemistlist;
        this.showReviewDialog = chemistlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_doctor_report, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        model = mr_chemist_models.get(position);
        holder.txt_Shopname.setText("Shopname:-" + model.getShopname());
        holder.Txt_address.setText("Address:-" + model.getAddress());
        holder.Txt_emailid.setText("emailid:-" + model.getEmail_id());
        holder.Txt_Mobileno.setText("Mobile number:-" + model.getMobile_Number());
        holder.Txt_City.setText("City:-" + model.getCity());
        holder.Txt_State.setText("State:-" + model.getState());
//        int count_done = Integer.parseInt(model.getTotal_visit()) - Integer.parseInt(model.getRemain_visit());
//        holder.counts.setText(count_done + " / " + model.getTotal_visit() + " Completed");
//        holder.counts.setText(count_done + " / " + model.getTotal_visit() + " Completed");


    }

    @Override
    public int getItemCount() {
        return mr_chemist_models.size();
    }

    public void UpdateList(ArrayList<ChemistModel> list) {
        mr_chemist_models = list;
        notifyDataSetChanged();
    }

    public interface ShowReviewDialog {
        public void shoow_review_dialog(String chemist_id);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_Shopname, Txt_address, Txt_emailid, Txt_Mobileno, Txt_City, Txt_State, show_details, counts;
        Button btn_review, btn_preorder;
        ImageView visit_done, visit_cancel;
        Dialog dailog_rating_review;
        ImageButton btn_delete_medicine;

        public ViewHolder(View list) {
            super(list);
            txt_Shopname = list.findViewById(R.id.txt_Shopname);
            Txt_address = list.findViewById(R.id.Txt_address);
            Txt_emailid = list.findViewById(R.id.Txt_emailid);
            Txt_Mobileno = list.findViewById(R.id.Txt_Mobileno);
            Txt_City = list.findViewById(R.id.Txt_City);
            Txt_State = list.findViewById(R.id.Txt_State);
            btn_preorder = list.findViewById(R.id.btn_preorder);

            btn_review = list.findViewById(R.id.btn_review);
            counts = list.findViewById(R.id.counts);


            btn_review.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showReviewDialog.shoow_review_dialog(mr_chemist_models.get(getAdapterPosition()).getId());
                }
            });


            btn_preorder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Preorder.class);
                    intent.putExtra("store_id", mr_chemist_models.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });

        }
    }

}
