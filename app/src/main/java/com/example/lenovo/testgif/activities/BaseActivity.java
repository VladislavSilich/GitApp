package com.example.lenovo.testgif.activities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.lenovo.testgif.R;

/**
 * Created by Lenovo on 23.03.2017.
 */

public class BaseActivity extends AppCompatActivity {
    protected ProgressDialog mProgressDialog;
    static final String TAG = "BaseActivity";

    public void showProgress() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this, R.style.custom_dialog);
            mProgressDialog.setCancelable(false);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mProgressDialog.show();
            mProgressDialog.setContentView(R.layout.progress_splash);
        }
        else {
            mProgressDialog.show();
            mProgressDialog.setContentView(R.layout.progress_splash);
        }
    }
    public void  hideProgress(){
            if (mProgressDialog != null){
                if(mProgressDialog.isShowing()){
                    mProgressDialog.hide();
                }
            }
    }
    public void showError(String message,Exception error){
        showToast(message);
        Log.d(TAG,String.valueOf(error));
    }

    public void showToast(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
}
