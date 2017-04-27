package com.example.lenovo.testgif.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lenovo.testgif.R;
import com.example.lenovo.testgif.managers.DataManager;
import com.example.lenovo.testgif.network.req.UserLoginReq;
import com.example.lenovo.testgif.network.res.UserModelRes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthActivity extends BaseActivity implements View.OnClickListener {

    private Button mSignIn;
    private TextView mRememberPassport;
    private EditText mLogin,mPassport;
    private CoordinatorLayout mCoordinatorLayout;
    private DataManager mDatamanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        mDatamanager = DataManager.getInstance();

        mSignIn = (Button)findViewById(R.id.btn_login);
        mRememberPassport = (TextView)findViewById(R.id.remember_txt);
        mLogin = (EditText)findViewById(R.id.edt_login_email);
        mPassport = (EditText)findViewById(R.id.edt_login_password);
        mCoordinatorLayout = (CoordinatorLayout)findViewById(R.id.main_coordinator_container);

        mSignIn.setOnClickListener(this);
        mRememberPassport.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                loginSuccess();
                break;
            case R.id.remember_txt:
                rememberPassword();
                break;
        }
    }

    public void showSnackBar (String message){
        Snackbar.make(mCoordinatorLayout,message,Snackbar.LENGTH_LONG).show();
    }

    public void rememberPassword(){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://devintensive.softdesign-apps.ru/forgotpass"));
        startActivity(intent);
    }

    public void loginSuccess(){
        showSnackBar("Вход");
    }

    public void signIn(){
        Call<UserModelRes> call = mDatamanager.loginUser(new UserLoginReq("email","password"));
        call.enqueue(new Callback<UserModelRes>() {
            @Override
            public void onResponse(Call<UserModelRes> call, Response<UserModelRes> response) {

            }

            @Override
            public void onFailure(Call<UserModelRes> call, Throwable t) {

            }
        });
    }
}

