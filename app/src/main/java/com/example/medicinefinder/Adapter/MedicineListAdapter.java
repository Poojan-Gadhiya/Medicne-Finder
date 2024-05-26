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


import com.example.medicinefinder.Fragment.Medicine;
import com.example.medicinefinder.Model.MedicineModel;
import com.example.medicinefinder.Model.SeniorModel;
import com.example.medicinefinder.Model.SharedPref;
import com.example.medicinefinder.R;

import java.util.ArrayList;

public class MedicineListAdapter extends RecyclerView.Adapter<MedicineListAdapter.ViewHolder> {
    ArrayList<MedicineModel> mr_doctor_models;
    MedicineModel model;
    boolean open_details = false;
    Context context;
    Dialog dailog_medicine;
    Fragment fragment;
    Medicine medicine;
    Addtocart addtocart;


    ArrayList<SeniorModel> senior_list;
    ArrayList<String> senior_name;
    ArrayAdapter seniorAdapter;
    SharedPref sharedPref = new SharedPref();
    String id;


    public MedicineListAdapter(ArrayList<MedicineModel> mr_doctor_models, Context context, Medicine medicine) {
        this.mr_doctor_models = mr_doctor_models;
        this.context = context;
        this.fragment = medicine;
        this.addtocart = medicine;
        id = sharedPref.getId(context);
        this.medicine = medicine;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_medicine, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        model = mr_doctor_models.get(position);
        holder.Txt_Medicine_name.setText("Medicine:-" + model.getMedicine_name());
//        holder.txt_med_description.setText("Description:-"+model.getMedicine_description());
        holder.txt_med_shopname.setText("Shopname:-" + model.getShopname());
        holder.txt_shop_address.setText("Address:-" + model.getAddress());
        holder.txt_Med_price.setText("Price(₹):-" + model.getPrice());
        holder.Txt_Med_price.setText("₹" + model.getPrice());
//        holder.txt_Med_ml_mg.setText("Ml/Mg:-"+model.getMed_mg_ml());
        holder.txt_Med_stock.setText("Available stock(Strip):-" + model.getMed_stock());
//        holder.Txt_tabs.setText("Tab in one strip:-"+model.getMed_tabs());


        //holder.hospital_name.setText(model.getMedicine_decs());
//        int count_done = Integer.parseInt(model.getTotal_visit()) - Integer.parseInt(model.getRemain_visit());
//        holder.counts.setText(count_done + " / " + model.getTotal_visit() + " Completed");

    }

    @Override
    public int getItemCount() {
        return mr_doctor_models.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void UpdateList(ArrayList<MedicineModel> list) {
        mr_doctor_models = list;
        notifyDataSetChanged();
    }

    public interface Addtocart {
        public void add_to_cart(String id, String store_id, String price);

        public void check_already_add_medicine(String med_id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Txt_Medicine_name, txt_med_description, txt_med_shopname, txt_shop_address, txt_Med_price, Txt_Med_price, txt_Med_ml_mg, txt_Med_stock, Txt_tabs, hospital_name, show_details, counts;
        ImageButton btn_info;
        ImageView visit_done, visit_cancel;
        GridLayout gridLayout;
        LinearLayout details, mr_details;
        CardView mr_dr_report_card;
        Button btn_addto_cart;


        public ViewHolder(View list) {
            super(list);

//            gridLayout = list.findViewById(R.id.staff_rec_design);
            Txt_Medicine_name = list.findViewById(R.id.Txt_Medicine_name);
//            txt_med_description = list.findViewById(R.id.txt_med_description);
            txt_med_shopname = list.findViewById(R.id.txt_med_shopname);
            txt_shop_address = list.findViewById(R.id.txt_shop_address);
            txt_Med_price = list.findViewById(R.id.txt_Med_price);
            Txt_Med_price = list.findViewById(R.id.Txt_Med_price);
//            txt_Med_ml_mg = list.findViewById(R.id.txt_Med_ml_mg);
            txt_Med_stock = list.findViewById(R.id.txt_Med_stock);
//            Txt_tabs = list.findViewById(R.id.Txt_tabs);
            counts = list.findViewById(R.id.counts);


            btn_info = list.findViewById(R.id.btn_info);
            btn_addto_cart = list.findViewById(R.id.btn_addto_cart);
            visit_done = list.findViewById(R.id.visit_done);
            visit_cancel = list.findViewById(R.id.visit_cancel);

            dailog_medicine = new Dialog(context, R.style.MyAlertDialogStyle);
            dailog_medicine.setContentView(R.layout.dailog_medicine_info);

            btn_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                    View dailogView = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.dailog_medicine_info, null);
                    TextView txt_med_description, Txt_tabs, txt_Med_ml_mg;
                    txt_med_description = dailogView.findViewById(R.id.txt_med_description);
                    Txt_tabs = dailogView.findViewById(R.id.Txt_tabs);
                    txt_Med_ml_mg = dailogView.findViewById(R.id.txt_Med_ml_mg);


                    txt_med_description.setText("Description:-" + mr_doctor_models.get(getAdapterPosition()).getMedicine_description());
                    txt_Med_ml_mg.setText("Ml/Mg:-" + mr_doctor_models.get(getAdapterPosition()).getMed_mg_ml());
                    Txt_tabs.setText("Tab in one strip:-" + mr_doctor_models.get(getAdapterPosition()).getMed_tabs());

                    builder.setView(dailogView);
                    builder.setCancelable(true);
                    builder.show();
                }
            });

            btn_addto_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //addtocart.check_already_add_medicine(mr_doctor_models.get(getAdapterPosition()).getId());
                    addtocart.add_to_cart(mr_doctor_models.get(getAdapterPosition()).getId(), mr_doctor_models.get(getAdapterPosition()).getStore_id(), mr_doctor_models.get(getAdapterPosition()).getPrice());

                }
            });
        }
    }


}
