package com.example.lenovo.testgif.managers;

import android.content.SharedPreferences;
import android.net.Uri;

import com.example.lenovo.testgif.utils.ConstantManager;
import com.example.lenovo.testgif.utils.NetworkActivityApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 05.04.2017.
 */

public class PreferencesManager {

    private SharedPreferences mSharedPreferences;

    private static final String[] USER_FIELDS = {ConstantManager.USER_PHONE_KEY,ConstantManager.USER_MAIL_KEY,
            ConstantManager.USER_VK_KEY,ConstantManager.USER_GIT_KEY,ConstantManager.USER_BIO_KEY};

    public PreferencesManager(){
        this.mSharedPreferences = NetworkActivityApplication.getSharedPreferences();
    }

    public void saveUserProfileData(List<String> userFields){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        for (int i = 0; i < USER_FIELDS.length;i++){
            editor.putString(USER_FIELDS[i],userFields.get(i));
        }
        editor.apply();
    }

    public List<String > loadUserProfileData (){
        List<String> userFields = new ArrayList<>();
        userFields.add(mSharedPreferences.getString(ConstantManager.USER_PHONE_KEY,"null"));
        userFields.add(mSharedPreferences.getString(ConstantManager.USER_MAIL_KEY,"null"));
        userFields.add(mSharedPreferences.getString(ConstantManager.USER_VK_KEY,"null"));
        userFields.add(mSharedPreferences.getString(ConstantManager.USER_GIT_KEY,"null"));
        userFields.add(mSharedPreferences.getString(ConstantManager.USER_BIO_KEY,"null"));
        return userFields;
    }
    public void SaveUserPhoto (Uri uri){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(ConstantManager.USER_PHOTO_KEY,uri.toString());
        editor.apply();
    }

    public Uri LoadUserPhoto (){
        String tempUri = mSharedPreferences.getString(ConstantManager.USER_PHOTO_KEY,
                "android.resource://com.example.lenovo.testgif/drawable/photouser");
        return Uri.parse(tempUri);
    }
}
