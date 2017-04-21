package com.example.lenovo.testgif;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView img = (ImageView)findViewById(R.id.img);


        Glide.with(this).load("http://media0.giphy.com/media/op7uqYWBm3R04/200.gif").asGif().crossFade().into(img);
    }
}
