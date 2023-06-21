package com.example.wapp.adapter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.wapp.AuthenticationActivity;
import com.example.wapp.HomeActivity;
import com.example.wapp.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
         startActivity(new Intent(this, HomeActivity.class));
    }
}