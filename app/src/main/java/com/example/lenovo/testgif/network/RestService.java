package com.example.lenovo.testgif.network;

import com.example.lenovo.testgif.network.req.UserLoginReq;
import com.example.lenovo.testgif.network.res.UserModelRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Lenovo on 26.04.2017.
 */

public interface RestService {
    @POST("login")
    Call<UserModelRes> loginUser (@Body UserLoginReq req);
}
