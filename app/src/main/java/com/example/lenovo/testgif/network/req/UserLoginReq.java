package com.example.lenovo.testgif.network.req;

/**
 * Created by Lenovo on 26.04.2017.
 */

public class UserLoginReq  {
    private String email;
    private String password;

    public UserLoginReq(String email, String password){
        this.email = email;
        this.password = password;
    }
}
