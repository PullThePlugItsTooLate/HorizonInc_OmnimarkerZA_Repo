package com.sm19003564.omnimarkerza;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button landmarks, favourites, settings, safetyInfo, currencyConverter, weather, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        landmarks = findViewById(R.id.btnMenuLandmarks);
        favourites = findViewById(R.id.btnMenuFavourite);
        settings = findViewById(R.id.btnMenuSettings);
        safetyInfo = findViewById(R.id.btnMenuSafetyInfo);
        currencyConverter = findViewById(R.id.btnMenuCurrency);
        weather = findViewById(R.id.btnMenuWeather);
        profile = findViewById(R.id.btnMenuProfile);






    }
}