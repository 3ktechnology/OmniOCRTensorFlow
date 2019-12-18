package com.omniocr.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.omniocr.MainApplication;

public class Preference {

    public static void setPreferences(String key, String value) {

        SharedPreferences.Editor editor = MainApplication.instance.getSharedPreferences(
                "WED_APP", Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getPreferences(String key) {
        SharedPreferences prefs = MainApplication.instance.getSharedPreferences("WED_APP",
                Context.MODE_PRIVATE);
        String text = prefs.getString(key, "");
        return text;
    }

    public static void setBoolean(String key, Boolean value) {

        SharedPreferences.Editor editor = MainApplication.instance.getSharedPreferences(
                "WED_APP", Context.MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static Boolean getBoolean(String key) {
        SharedPreferences prefs = MainApplication.instance.getSharedPreferences("WED_APP",
                Context.MODE_PRIVATE);
        return prefs.getBoolean(key, false);
    }

}
