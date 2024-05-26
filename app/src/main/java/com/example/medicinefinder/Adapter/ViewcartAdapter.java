package com.example.medicinefinder.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicinefinder.Model.Viewcartmodel;
import com.example.medicinefinder.R;
import com.example.medicinefinder.Activity.View_cart;

import java.util.ArrayList;

public class ViewcartAdapter extends RecyclerView.Adapter<ViewcartAdapter.ViewHolder> {
    ArrayList<Viewcartmodel> viewcartmodels;
    Viewcartmodel model;
    boolean open_details = false;
    Updateqty updateqty;
    View_cart view_cart;
    Context context;

    public ViewcartAdapter(ArrayList<Viewcartmodel> viewcartmodels, Context context, View_cart view_cart) {
        this.viewcartmodels = viewcartmodels;
        this.context = context;
        this.view_cart = view_cart;
        this.updateqty = (Updateqty) view_cart;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_cart, parent, false);
        ViewcartAdapter.ViewHolder viewHolder = new ViewcartAdapter.ViewHolder(listItem);
        return viewHolder;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewcartAdapter.ViewHolder holder, int position) {
        model = viewcartmodels.get(position);
        holder.txt_medicine_name.setText(model.getMedicine_name());
        holder.txt_Shopname.setText("Shopname:-" + model.getShopname());
        holder.Txt_price.setText("\u20B9" + model.getPrice());
        holder.Txt_quantity.setText(model.getMedicine_qty());


    }

    @Override
    public int getItemCount() {
        return viewcartmodels.size();
    }

    public interface Updateqty {
        public void update_qty(String id, int qty);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_medicine_name, btn_delete_medicine, Txt_med_quantity, txt_Shopname, Txt_price, Txt_quantity;
        ImageView img_mius, img_plus;


        public ViewHolder(View list) {
            super(list);
            txt_medicine_name = list.findViewById(R.id.txt_medicine_name);
            txt_Shopname = list.findViewById(R.id.txt_Shopname);
//            Txt_med_quantity = list.findViewById(R.id.Txt_med_quantity);
            Txt_price = list.findViewById(R.id.Txt_price);
            img_mius = list.findViewById(R.id.img_mius);
            img_plus = list.findViewById(R.id.img_plus);
            Txt_quantity = list.findViewById(R.id.Txt_quantity);
//            btn_delete_medicine = list.findViewById(R.id.btn_delete_medicine);


            img_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateqty.update_qty(viewcartmodels.get(getAdapterPosition()).getId(), Integer.parseInt(viewcartmodels.get(getAdapterPosition()).getMedicine_qty()) + 1);
                }
            });

            img_mius.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    updateqty.update_qty(viewcartmodels.get(getAdapterPosition()).getId(), Integer.parseInt(viewcartmodels.get(getAdapterPosition()).getMedicine_qty()) - 1);
                }
            });
        }

        public void UpdateList(ArrayList<Viewcartmodel> list) {
            viewcartmodels = list;
            notifyDataSetChanged();
        }

    }


}
