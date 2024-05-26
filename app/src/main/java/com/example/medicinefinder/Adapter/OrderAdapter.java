package com.example.medicinefinder.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicinefinder.Activity.Order;
import com.example.medicinefinder.Model.Viewcartmodel;
import com.example.medicinefinder.R;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    ArrayList<Viewcartmodel> viewcartmodels;
    Viewcartmodel model;
    Context context;
    Order order;


    public OrderAdapter(ArrayList<Viewcartmodel> viewcartmodels, Context context, Order order) {
        this.viewcartmodels = viewcartmodels;
        this.context = context;
        this.order = order;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_order, parent, false);
        OrderAdapter.ViewHolder viewHolder = new OrderAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        model = viewcartmodels.get(position);
        holder.txt_order_medicine_name.setText(model.getMedicine_name());
        holder.Txt_order_price.setText("\u20B9" + model.getPrice());
        holder.Txt_order_med_quantity.setText("Qty:-"+model.getMedicine_qty());


    }

    @Override
    public int getItemCount() {
        return viewcartmodels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_order_medicine_name, Txt_order_price, Txt_order_med_quantity,
                txt_Shopname, Txt_price, Txt_quantity,Txt_order_detail;


        public ViewHolder(View list) {
            super(list);
            txt_order_medicine_name = list.findViewById(R.id.txt_order_medicine_name);
            Txt_order_price = list.findViewById(R.id.Txt_order_price);
            Txt_order_med_quantity = list.findViewById(R.id.Txt_order_med_quantity);
            Txt_price = list.findViewById(R.id.Txt_price);
            Txt_quantity = list.findViewById(R.id.Txt_quantity);

        }


        public void UpdateList(ArrayList<Viewcartmodel> list) {
            viewcartmodels = list;
            notifyDataSetChanged();
        }


    }

}
