package com.ample.dumi.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


import com.ample.dumi.Activity.LoginActivity;

import java.util.HashMap;

/**
 * Created by admin on 07/20/2017.
 */

public class LoginSession {
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "LoginSession";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";
    public static final String KEY_USERID = "userid";
    public static final String KEY_PASSWORD = "password" ;

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    public static final String KEY_IMAGE = "image";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_PROFILEID = "profile_id";
    public static final String KEY_DOB = "dob";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_CONNECTION_LIMIT = "Connection_Limit";
    public static final String KEY_CONNECTION_LEFT = "Connection_Left";
    public static final String KEY_QID = "q_id";

    // Constructor
    public LoginSession(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String q_id, String profileid, String userid, String name, String email, String image, String gender, String password, String dob, String phone, String Connection_Limit, String Connection_Left){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_QID, q_id);
        editor.putString(KEY_PROFILEID, profileid);
        editor.putString(KEY_USERID, userid);
        editor.putString(KEY_NAME, name);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_IMAGE, image);
        editor.putString(KEY_GENDER, gender);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_DOB, dob);
        editor.putString(KEY_PHONE, phone);
        editor.putString(KEY_CONNECTION_LIMIT, Connection_Limit);
        editor.putString(KEY_CONNECTION_LEFT, Connection_Left);
        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_USERID, pref.getString(KEY_USERID, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        user.put(KEY_IMAGE, pref.getString(KEY_IMAGE, null));
        user.put(KEY_GENDER, pref.getString(KEY_GENDER, null));
        user.put(KEY_PROFILEID, pref.getString(KEY_PROFILEID, null));
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));
        user.put(KEY_DOB, pref.getString(KEY_DOB, null));
        user.put(KEY_PHONE, pref.getString(KEY_PHONE, null));
        user.put(KEY_CONNECTION_LIMIT, pref.getString(KEY_CONNECTION_LIMIT, null));
        user.put(KEY_CONNECTION_LEFT, pref.getString(KEY_CONNECTION_LEFT, null));
        user.put(KEY_QID, pref.getString(KEY_QID, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
       // editor.clear();
        editor.putBoolean(IS_LOGIN, false);
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
