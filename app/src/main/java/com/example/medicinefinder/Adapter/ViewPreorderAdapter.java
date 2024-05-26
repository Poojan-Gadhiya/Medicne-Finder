package com.example.medicinefinder.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicinefinder.Activity.Myorder;
import com.example.medicinefinder.Activity.View_preorder;
import com.example.medicinefinder.Model.OrderidlistModel;
import com.example.medicinefinder.Model.ViewPreorderModel;
import com.example.medicinefinder.R;

import java.util.ArrayList;

public class ViewPreorderAdapter extends RecyclerView.Adapter<ViewPreorderAdapter.ViewHolder> {

    ArrayList<ViewPreorderModel> viewPreorderModels;
    ViewPreorderModel model;
    Context context;
    View_preorder view_preorder;

    public ViewPreorderAdapter(ArrayList<ViewPreorderModel> viewPreorderModels, Context context, View_preorder view_preorder) {
        this.viewPreorderModels = viewPreorderModels;
        this.context = context;
        this.view_preorder = view_preorder;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_view_preorder, parent, false);
        ViewPreorderAdapter.ViewHolder viewHolder = new ViewPreorderAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewPreorderAdapter.ViewHolder holder, int position) {
        model = viewPreorderModels.get(position);
        holder.item_med_name.setText(model.getMedicine_name());
        holder.item_med_qty.setText( model.getMed_qty());
        holder.txt_preorder_status.setText(model.getOrder_stage());

    }

    @Override
    public int getItemCount() {
        return viewPreorderModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView item_med_name, item_med_qty, txt_preorder_status;

        public ViewHolder(View list) {
            super(list);
            item_med_name = list.findViewById(R.id.item_med_name);
            item_med_qty = list.findViewById(R.id.item_med_qty);
            txt_preorder_status = list.findViewById(R.id.txt_preorder_status);
        }

        public void UpdateList(ArrayList<ViewPreorderModel> list) {
            viewPreorderModels = list;
            notifyDataSetChanged();
        }

    }
}
