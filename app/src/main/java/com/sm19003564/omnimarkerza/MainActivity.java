package com.sm19003564.omnimarkerza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnLandmarks, btnFavourites, btnSettings, btnSafetyInfo, btnCurrencyConverter, btnWeather, btnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLandmarks = findViewById(R.id.btnMenuLandmarks);
        btnFavourites = findViewById(R.id.btnMenuFavourite);
        btnSettings = findViewById(R.id.btnMenuSettings);
        btnSafetyInfo = findViewById(R.id.btnMenuSafetyInfo);
        btnCurrencyConverter = findViewById(R.id.btnMenuCurrency);
        btnWeather = findViewById(R.id.btnMenuWeather);
        btnProfile = findViewById(R.id.btnMenuProfile);

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(i);
            }
        });

    }
}