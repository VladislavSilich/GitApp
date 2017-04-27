package com.example.lenovo.testgif.managers;

import com.example.lenovo.testgif.network.RestService;
import com.example.lenovo.testgif.network.ServiceGenerator;
import com.example.lenovo.testgif.network.req.UserLoginReq;
import com.example.lenovo.testgif.network.res.UserModelRes;

import retrofit2.Call;

/**
 * Created by Lenovo on 05.04.2017.
 */

public class DataManager {
    private static DataManager INSTANCE = null;
    private PreferencesManager mPreferencesManager;
    private RestService mRestService;
    private DataManager(){
    this.mPreferencesManager = new PreferencesManager();
    this.mRestService = ServiceGenerator.createService(RestService.class);
    }

    public static DataManager getInstance(){
        if (INSTANCE == null){
            INSTANCE = new DataManager();
        }
        return INSTANCE;
    }
    public PreferencesManager getPreferencesManager(){
        return mPreferencesManager;
    }

    public Call<UserModelRes> loginUser (UserLoginReq userLoginReq){
        return mRestService.loginUser(userLoginReq);
    }
}
