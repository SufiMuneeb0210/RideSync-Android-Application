package com.example.ridesync;

import android.content.Context;
import android.content.SharedPreferences;

public class _9_3_SessionManager {
    private static final String PREF_NAME = "UserPrefs";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_JOB = "job";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_ACTIVITY_NAME = "ActivityName";
    private static final String KEY_BALANCE="Balance";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public _9_3_SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveUserDetails(String name, String phone, String password, String gender, String job, String email) {
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_PHONE, phone);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_GENDER, gender);
        editor.putString(KEY_JOB, job);
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }

    public void setBalance(int balance)
    {
        editor.putInt(KEY_BALANCE,balance);
        editor.apply();
    }

    public void setActivityName(String ActivityName) {
        editor.putString(KEY_ACTIVITY_NAME, ActivityName);
        editor.apply();
    }

    public void setLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.apply();
    }
    public void clearUserDetails() {
        editor.clear();
        editor.apply();
    }

    public String getName() {
        return sharedPreferences.getString(KEY_NAME, "");
    }

    public String getPhone() {
        return sharedPreferences.getString(KEY_PHONE, "");
    }

    public String getPassword() {
        return sharedPreferences.getString(KEY_PASSWORD, "");
    }

    public String getGender() {
        return sharedPreferences.getString(KEY_GENDER, "");
    }

    public String getJob() {
        return sharedPreferences.getString(KEY_JOB, "");
    }

    public String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, "");
    }
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }
    public String getActivityName() {
        return sharedPreferences.getString(KEY_ACTIVITY_NAME, "");
    }

    public int getBalance() {
        return sharedPreferences.getInt(KEY_BALANCE, 0);
    }
}
