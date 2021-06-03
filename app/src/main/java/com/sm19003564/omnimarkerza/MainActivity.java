package com.sm19003564.omnimarkerza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    Button btnLandmarks, btnFavourites, btnSettings, btnSafetyInfo, btnCurrencyConverter, btnWeather, btnProfile;
    TextView tvMeasure;
    Settings settings;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference settingsRef = database.getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());


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
        tvMeasure = findViewById(R.id.tvMeasure);
        settings = new Settings();


        settingsRef.child("SettingsData").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataInfo: snapshot.getChildren()){
                    settings = dataInfo.getValue(Settings.class);
                }
                if (settings.isMetric()){
                    tvMeasure.setText("Metric");
                }else{
                    if (settings.isImperial()){
                        tvMeasure.setText("Imperial");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(i);
            }
        });

        btnCurrencyConverter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CurrencyConverterActivity.class);
                startActivity(i);
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });

        btnSafetyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SafetyInfoActivity.class);
                startActivity(i);
            }
        });

        btnWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, WeatherActivity.class);
                startActivity(i);
            }
        });

        btnLandmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(i);
            }
        });


    }
}