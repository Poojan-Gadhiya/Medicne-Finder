package com.example.medicinefinder.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicinefinder.Activity.Order_detail;
import com.example.medicinefinder.Model.OrderdetailModel;
import com.example.medicinefinder.R;

import java.util.ArrayList;

public class OrderdetailAdapter extends RecyclerView.Adapter<OrderdetailAdapter.ViewHolder> {

    ArrayList<OrderdetailModel> orderdetailModels;
    OrderdetailModel model;
    Context context;
    Order_detail order_detail;

    public OrderdetailAdapter(ArrayList<OrderdetailModel> orderdetailModels, Context context, Order_detail order_detail) {
        this.orderdetailModels = orderdetailModels;
        this.context = context;
        this.order_detail = order_detail;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_order_dtail, parent, false);
        OrderdetailAdapter.ViewHolder viewHolder = new OrderdetailAdapter.ViewHolder(listItem);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull OrderdetailAdapter.ViewHolder holder, int position) {
        model = orderdetailModels.get(position);
        holder.txt_order_medicine_name.setText(model.getMedicine_name());
        holder.Txt_order_price.setText("\u20B9" + model.getPrice());
        holder.Txt_order_med_quantity.setText("Qty:-" + model.getMedicine_qty());

    }

    @Override
    public int getItemCount() {
        return orderdetailModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_order_medicine_name, Txt_order_price, Txt_order_med_quantity,
                txt_Shopname, Txt_price, Txt_quantity, Txt_order_detail;


        public ViewHolder(View list) {
            super(list);
            txt_order_medicine_name = list.findViewById(R.id.txt_order_medicine_name);
            Txt_order_price = list.findViewById(R.id.Txt_order_price);
            Txt_order_med_quantity = list.findViewById(R.id.Txt_order_med_quantity);
            Txt_price = list.findViewById(R.id.Txt_price);
            Txt_quantity = list.findViewById(R.id.Txt_quantity);

        }


        public void UpdateList(ArrayList<OrderdetailModel> list) {
            orderdetailModels = list;
            notifyDataSetChanged();
        }


    }
}
