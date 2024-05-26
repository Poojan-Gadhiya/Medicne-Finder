package com.example.medicinefinder.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class SharedPref {


    public String PREFS_NAME = "medical";

    public String id = "id";
    public String mobile_number = "mobile_number";
    public String Username = "Username";


    public static final String LOGGED_IN_PREF = "medical_login_status";


    static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setLoggedIn(Context context, boolean loggedIn) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGGED_IN_PREF, loggedIn);
        editor.apply();
//        return loggedIn;
    }

    public static boolean getLoggedStatus(Context context) {
        return getPreferences(context).getBoolean(LOGGED_IN_PREF, false);
    }


    public void set_profile(Context context, String id, String mobile_number, String Username) {

        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME, 0);//mode 0 always
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(this.id, id);
        editor.putString(this.mobile_number, mobile_number);
        editor.putString(this.Username, Username);
        editor.apply();

    }

    public String getId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getString(id, "");
    }

    public String getname(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getString(mobile_number, "");
    }

    public String getnumber(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getString(Username, "");
    }

    public void clear_data(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();

    }

}
