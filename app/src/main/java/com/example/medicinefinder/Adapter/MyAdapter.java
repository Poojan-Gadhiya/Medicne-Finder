package com.example.medicinefinder.Adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.medicinefinder.Fragment.Chemistlist;
import com.example.medicinefinder.Fragment.Medicine;

import java.util.ArrayList;

public class MyAdapter extends FragmentPagerAdapter {

    private final Context myContext;
    int totalTabs;




    public MyAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Medicine drList = new Medicine();
                return drList;
            case 1:
                Chemistlist chemistlist = new Chemistlist();
                return chemistlist;

            default:
                return null;
        }
    }

    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}