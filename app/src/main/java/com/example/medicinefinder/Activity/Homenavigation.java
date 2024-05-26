package com.example.medicinefinder.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.medicinefinder.Adapter.MyAdapter;
import com.example.medicinefinder.Model.ProfileModel;
import com.example.medicinefinder.Model.SharedPref;
import com.example.medicinefinder.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class Homenavigation extends AppCompatActivity {

    LinearLayout home_task_layout, home_attandence_layout, nav_attandence, nav_task, nav_expense, nav_chemist, nav_doctor,
            nav_analysis, nav_profile, nav_reports;
    ImageView home_arow;

    String TAG = "Homenavigation..";
    SharedPref sharedPref = new SharedPref();
    TextView mr_name, mr_number;

    TabLayout tabLayout;
    ViewPager viewPager;
    DrawerLayout drawer;
    MyAdapter adapter;
    LinearLayout logout_button,View_cart,rating_review_btn,Order_button,View_preorder;
    LinearLayout profile;
    ArrayList<ProfileModel> profile_data = new ArrayList<>();
    TextView status;
    AlertDialog.Builder builder;
    Dialog adv_dialog;

    ImageView adv_image;
    TextView adv_message;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homenavigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.btn_color, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.btn_color));
        }
        init();
        onclick();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_to_cart, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_addcart:
                Intent i = new Intent(this,View_cart.class);
                this.startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void init() {


//        adv_dialog = new Dialog(Homenavigation.this, R.style.MyAlertDialogStyle);
//        adv_dialog.setContentView(R.layout.dialog_adverticement);
//
//        adv_image = adv_dialog.findViewById(R.id.adv_image);
//        adv_message = adv_dialog.findViewById(R.id.adv_text);

        status = findViewById(R.id.status);

        mr_name = findViewById(R.id.mr_name);
        mr_number = findViewById(R.id.mr_number);

        mr_name.setText(sharedPref.getname(Homenavigation.this));
        mr_number.setText(sharedPref.getnumber(Homenavigation.this));

//        home_task_layout = findViewById(R.id.home_task_layout);
//        home_attandence_layout = findViewById(R.id.home_attandence_layout);
//        home_arow = findViewById(R.id.home_arrow);

        nav_attandence = findViewById(R.id.nav_attandence);
        nav_task = findViewById(R.id.nav_task);
        nav_expense = findViewById(R.id.nav_expense);
        nav_chemist = findViewById(R.id.nav_chemist);
        nav_doctor = findViewById(R.id.nav_doctor);
        nav_analysis = findViewById(R.id.nav_analysis);
        nav_reports = findViewById(R.id.nav_reports);

        nav_profile = findViewById(R.id.nav_profile);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);



        tabLayout.addTab(tabLayout.newTab().setText("Medicine"));
        tabLayout.addTab(tabLayout.newTab().setText("Pharmacy"));

        adapter = new MyAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        rating_review_btn=findViewById(R.id.rating_review_btn);
        rating_review_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Homenavigation.this, pharm_rating.class));
            }
        });


        logout_button = findViewById(R.id.logout_button);

        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(Homenavigation.this,R.style.MyAlertDialogStyle)
                .setMessage("Do you want to logout?") .setTitle("Logout") .setIcon(R.drawable.logo_1)

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id) {
                                SharedPref.setLoggedIn(Homenavigation.this, false);
                                sharedPref.clear_data(Homenavigation.this);
                                startActivity(new Intent(Homenavigation.this, login.class));
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.dismiss();
                            }
                        })

                        .show();
            }
        });

        nav_profile=findViewById(R.id.nav_profile);
        nav_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Homenavigation.this, Profile.class));
            }
        });

        View_cart=findViewById(R.id.View_cart);
        View_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Homenavigation.this, com.example.medicinefinder.Activity.View_cart.class));
            }
        });


        View_preorder=findViewById(R.id.View_preorder);
        View_preorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Homenavigation.this, com.example.medicinefinder.Activity.View_preorder.class));
            }
        });

        Order_button=findViewById(R.id.Order_button);
        Order_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Homenavigation.this, com.example.medicinefinder.Activity.Myorder.class));
            }
        });

    }

    public void onclick() {
    }

    public void closeDrawer() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        get_profile();
    }
}
