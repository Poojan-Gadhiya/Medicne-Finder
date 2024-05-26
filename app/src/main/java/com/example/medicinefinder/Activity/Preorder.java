package com.example.medicinefinder.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicinefinder.Adapter.OrderdetailAdapter;
import com.example.medicinefinder.Adapter.PreorderListAdapter;
import com.example.medicinefinder.Apis.Api;
import com.example.medicinefinder.Apis.ApiServices;
import com.example.medicinefinder.Model.PreorderCart;
import com.example.medicinefinder.Model.Result;
import com.example.medicinefinder.Model.SharedPref;
import com.example.medicinefinder.R;
import com.example.medicinefinder.databinding.ActivityPreorderBinding;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Preorder extends AppCompatActivity implements View.OnClickListener,PreorderListAdapter.DeleteMedicine {


    ImageView medicine_image;
    ImageButton btn_camera;
    Dialog dialog_select_image_choice;
    TextView select_image_gallary, capture_image_camera;
    Bitmap bitmap_image;
    private static final int Order_status=1;

    String image_base64 = "";
    private static final int CAMERA_REQUEST = 1888;
    private static final int SELECT_PICTURE = 1;
    private static final int PICK_IMAGE_MULTIPLE = 1;
    String store_id;
    Button btn_select_image, btn_add_medicine, place_order;

    SharedPref sharedPref = new SharedPref();
    EditText edt_medicine_name, edt_medicine_qty;
    ArrayList<PreorderCart> preorderCarts = new ArrayList<>();

    RecyclerView recyclerView;
    PreorderListAdapter preorderListAdapter;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preorder);
        store_id = getIntent().getExtras().getString("store_id");
        Log.d("store_id", store_id);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        get_preorder_cart();
    }


    void init() {
        dialog_select_image_choice = new Dialog(Preorder.this, R.style.MyAlertDialogStyle2);
        dialog_select_image_choice.setContentView(R.layout.dialog_image_pick);
        capture_image_camera = dialog_select_image_choice.findViewById(R.id.capture_image_camera);
        select_image_gallary = dialog_select_image_choice.findViewById(R.id.select_image_gallary);
        capture_image_camera.setOnClickListener(Preorder.this);
        select_image_gallary.setOnClickListener(Preorder.this);
        medicine_image = findViewById(R.id.medicine_image);
        btn_select_image = findViewById(R.id.btn_select_image);
        btn_select_image.setOnClickListener(this);
        btn_add_medicine = findViewById(R.id.btn_add_medicine);
        btn_add_medicine.setOnClickListener(this);

        place_order = findViewById(R.id.place_order);
        place_order.setOnClickListener(this);

        edt_medicine_name = findViewById(R.id.edt_medicine_name);
        edt_medicine_qty = findViewById(R.id.edt_medicine_qty);

        recyclerView = findViewById(R.id.recylerview);
        preorderListAdapter = new PreorderListAdapter(preorderCarts, Preorder.this,Preorder.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(Preorder.this));
        recyclerView.setAdapter(preorderListAdapter);
    }

    void select_image_option() {
        dialog_select_image_choice.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.capture_image_camera:
                capture_images();
                break;

            case R.id.select_image_gallary:
                select_image();
                break;

            case R.id.btn_select_image:
                select_image_option();
                break;

            case R.id.btn_add_medicine:
                validate_data();
                break;

            case R.id.place_order:
                place_preorder();
                break;

        }
    }

    void validate_data() {
        if (edt_medicine_name.getText().toString().equals("")) {
            edt_medicine_name.setError("Required");
        } else if (edt_medicine_qty.getText().toString().equals("")) {
            edt_medicine_qty.setError("Required");
        } else {
            add_midecine_to_preorder_cart();
        }
    }

    void select_image() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);
    }

    void capture_images() {

        Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // single image

        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == SELECT_PICTURE) {
                    if (data.getData() != null) {
                        Bitmap temp_bit_map = BitmapFactory.decodeStream(getContentResolver().openInputStream(data.getData()));
                        bitmap_image = temp_bit_map;
                        medicine_image.setImageBitmap(bitmap_image);
                        image_base64 = convert64(temp_bit_map);
                        Log.d("BASE64_IMAGE", "IMAGE DATA " + image_base64);
                    } else {
                        Toast.makeText(Preorder.this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                } else if (requestCode == CAMERA_REQUEST) {
                    Bitmap temp_bit_map = (Bitmap) data.getExtras().get("data");
                    bitmap_image = temp_bit_map;
                    medicine_image.setImageBitmap(bitmap_image);
                    image_base64 = convert64(temp_bit_map);
                    Log.d("BASE64_IMAGE", "IMAGE DATA " + image_base64);
                    Date currentTime = Calendar.getInstance().getTime();
//                    File wallpaperDirectory = new File("/sdcard/DesignImages/");
//                    wallpaperDirectory.mkdirs();
//                    File file = new File(wallpaperDirectory, "img_" + currentTime + ".jpg");


                    ContextWrapper cw = new ContextWrapper(getApplicationContext());
                    File directory = cw.getDir("imageDir", MODE_PRIVATE);
                    File file = new File(directory, "testing" + currentTime + ".jpg");


                    if (!file.exists()) {
                        Log.d("path", file.toString());
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(file);
                            temp_bit_map.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            fos.flush();
                            fos.close();
                        } catch (java.io.IOException e) {
                            e.printStackTrace();
                        }
                    }


                } else {
                    Toast.makeText(Preorder.this, "Cancelled", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Preorder.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        dialog_select_image_choice.dismiss();
    }

    String convert64(Bitmap temp_img) {

        int nh = (int) (temp_img.getHeight() * (512.0 / temp_img.getWidth()));
        Bitmap scaled = Bitmap.createScaledBitmap(temp_img, 512, nh, true);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        scaled.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the bitmap object
        byte[] bimage = baos.toByteArray();
        String baseimage64 = Base64.encodeToString(bimage, Base64.DEFAULT);
        return baseimage64;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    void add_midecine_to_preorder_cart() {
        final ProgressDialog progressDialog = new ProgressDialog(Preorder.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices apiServices = retrofit.create(ApiServices.class);

        Call<Result> call = apiServices.add_medicine_to_preorder(store_id, sharedPref.getId(Preorder.this), edt_medicine_name.getText().toString().trim(), edt_medicine_qty.getText().toString().trim());

        call.enqueue(new Callback<Result>() {

            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    Log.d("RESPONSE_MESSAGE", "SIGN UP : " + response.body().getMsg());
                    if (response.body().getSuccess()) {
                        Toast.makeText(Preorder.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        edt_medicine_name.setText("");
                        edt_medicine_qty.setText("");
                        get_preorder_cart();
                    } else {
                        Toast.makeText(Preorder.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Preorder.this, "Somethings are Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Preorder.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Preorder.this, Homenavigation.class));
            }
        });
    }

    void get_preorder_cart() {
        final ProgressDialog progressDialog = new ProgressDialog(Preorder.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices apiServices = retrofit.create(ApiServices.class);

        Call<Result> call = apiServices.get_preorder_by_pharmacy_user(store_id, sharedPref.getId(Preorder.this));

        call.enqueue(new Callback<Result>() {

            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    Log.d("RESPONSE_MESSAGE", "SIGN UP : " + response.body().getMsg());
                    if (response.body().getSuccess()) {
                        preorderCarts.clear();
                        preorderCarts.addAll(response.body().getPreOrder_Detail_list());
                        preorderListAdapter.notifyDataSetChanged();
                        if (preorderCarts.size() > 0) {
                            recyclerView.setVisibility(View.VISIBLE);
                        } else {
                            recyclerView.setVisibility(View.GONE);
                        }
                        Log.d("PREORDER_DATA", preorderCarts.get(0).getMedicine_name());
//                        Toast.makeText(Preorder.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
//                        Toast.makeText(Preorder.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Preorder.this, "Somethings are Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Preorder.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Preorder.this, Homenavigation.class));
            }
        });
    }

    void place_preorder() {
        final ProgressDialog progressDialog = new ProgressDialog(Preorder.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        final Random myRandom = new Random();
        String random = String.valueOf(myRandom.nextInt(1000));
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
        String currentDate = sdf.format(new Date());
        StringBuilder Orderid = new StringBuilder(100);
        Orderid.append("ORD");
        Orderid.append(random);
        Orderid.append(currentDate);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices apiServices = retrofit.create(ApiServices.class);

        Call<Result> call = apiServices.place_preorder(store_id, sharedPref.getId(Preorder.this), Orderid.toString(), image_base64,String.valueOf(Order_status));

        call.enqueue(new Callback<Result>() {

            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    Log.d("RESPONSE_MESSAGE", "SIGN UP : " + response.body().getMsg());
                    if (response.body().getSuccess()) {
                        Toast.makeText(Preorder.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(Preorder.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Preorder.this, "Somethings are Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Preorder.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Preorder.this, Homenavigation.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void delet_medicine_preorder_cart(String id) {
        final ProgressDialog progressDialog = new ProgressDialog(Preorder.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices apiServices = retrofit.create(ApiServices.class);

        Call<Result> call = apiServices.Delte_Preorder_Medicine_cart(id);

        call.enqueue(new Callback<Result>() {

            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    Log.d("RESPONSE_MESSAGE", "SIGN UP : " + response.body().getMsg());
                    if (response.body().getSuccess()) {
                        Toast.makeText(Preorder.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        recreate();
                    } else {
                        Toast.makeText(Preorder.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Preorder.this, "Somethings are Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Preorder.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Preorder.this, Homenavigation.class));
            }
        });

    }
}