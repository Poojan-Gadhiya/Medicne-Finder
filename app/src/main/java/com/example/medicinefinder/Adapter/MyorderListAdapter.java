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
import com.example.medicinefinder.Model.OrderidlistModel;
import com.example.medicinefinder.R;

import java.util.ArrayList;

public class MyorderListAdapter extends RecyclerView.Adapter<MyorderListAdapter.ViewHolder> {
    ArrayList<OrderidlistModel> orderidlistModels;
    OrderidlistModel model;
    Context context;
    Myorder myorder;
    Orderdetail orderdetail;


    public MyorderListAdapter(ArrayList<OrderidlistModel> orderidlistModels, Context context, Myorder myorder) {
        this.orderidlistModels = orderidlistModels;
        this.context = context;
        this.myorder = myorder;
        this.orderdetail=(Orderdetail) myorder;

    }

    @NonNull
    @Override
    public MyorderListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_orderidlist, parent, false);
        MyorderListAdapter.ViewHolder viewHolder = new MyorderListAdapter.ViewHolder(listItem);
        return viewHolder;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyorderListAdapter.ViewHolder holder, int position) {
        model = orderidlistModels.get(position);
        holder.Txt_Orderid.setText(model.getOrder_id());
        holder.Txt_order_price.setText("\u20B9" + model.getOrder_total());
        holder.Txt_orderstage.setText(model.getOrder_stage());
        holder.Txr_orderdate.setText(model.getOrder_date());



    }

    @Override
    public int getItemCount() {
        return orderidlistModels.size();
    }

    public interface Orderdetail{
        public void order_datail(String orderid);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CharSequence orderid="";

        TextView Txt_Orderid,Txr_orderdate,Txt_orderstage,Txt_order_price,Txt_order_detail;

        public ViewHolder(View list) {
            super(list);
            Txt_Orderid = list.findViewById(R.id.Txt_Orderid);
            Txr_orderdate = list.findViewById(R.id.Txr_orderdate);
            Txt_orderstage = list.findViewById(R.id.Txt_orderstage);
            Txt_order_price = list.findViewById(R.id.Txt_order_price);
            Txt_order_detail = list.findViewById(R.id.Txt_order_detail);

            Txt_order_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    orderdetail.order_datail(orderidlistModels.get(getAdapterPosition()).getOrder_id());
                }
            });
        }


        public void UpdateList(ArrayList<OrderidlistModel> list) {
            orderidlistModels = list;
            notifyDataSetChanged();
        }

    }



}

//
