package com.example.medicinefinder.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicinefinder.Activity.Preorder;
import com.example.medicinefinder.Fragment.Medicine;
import com.example.medicinefinder.Model.MedicineModel;
import com.example.medicinefinder.Model.PreorderCart;
import com.example.medicinefinder.Model.SeniorModel;
import com.example.medicinefinder.Model.SharedPref;
import com.example.medicinefinder.R;

import java.util.ArrayList;

public class PreorderListAdapter extends RecyclerView.Adapter<PreorderListAdapter.ViewHolder> {
    ArrayList<PreorderCart> mr_doctor_models;
    PreorderCart model;
    Context context;
    Preorder preorder;
    DeleteMedicine deleteMedicine;


    public PreorderListAdapter(ArrayList<PreorderCart> mr_doctor_models, Context context, Preorder preorder) {
        this.mr_doctor_models = mr_doctor_models;
        this.context = context;
        this.preorder = preorder;
        this.deleteMedicine=(DeleteMedicine) preorder;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_preorder_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        model = mr_doctor_models.get(position);
        holder.item_med_name.setText(model.getMedicine_name());
//        holder.txt_med_description.setText("Description:-"+model.getMedicine_description());
        holder.item_med_qty.setText(model.getMed_qty());

    }

    @Override
    public int getItemCount() {
        return mr_doctor_models.size();
    }

    public interface DeleteMedicine{
        public void delet_medicine_preorder_cart(String id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView item_med_name, item_med_qty;
        ImageView item_img_Delete;

        public ViewHolder(View list) {
            super(list);
            item_med_name = list.findViewById(R.id.item_med_name);
            item_med_qty = list.findViewById(R.id.item_med_qty);
            item_img_Delete = list.findViewById(R.id.item_img_Delete);

            item_img_Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteMedicine.delet_medicine_preorder_cart(mr_doctor_models.get(getAdapterPosition()).getId());
                }
            });

        }
    }
}
