package com.aspprothes.biometricandwithlockauthentication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setNavigationBarColor(getResources().getColor(R.color.sky));
        this.getWindow().setStatusBarColor(getResources().getColor(R.color.sky));
        setContentView(R.layout.home_page);
    }
}