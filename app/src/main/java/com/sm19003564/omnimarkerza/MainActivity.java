package com.sm19003564.omnimarkerza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
//Widget imports
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
//Firebase imports
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    ImageView iv_CurrencyConverter, iv_Profile, iv_Settings, iv_SafetyInformation, iv_Maps, iv_Favourites, iv_Weather;
    TextView tvMeasure;
    Settings settings;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference settingsRef = database.getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_CurrencyConverter = findViewById(R.id.iv_CurrencyConverter);
        iv_Profile = findViewById(R.id.iv_Profile);
        iv_Settings = findViewById(R.id.iv_Settings);
        iv_SafetyInformation = findViewById(R.id.iv_SafetyInformation);
        iv_Maps = findViewById(R.id.iv_Maps);
        iv_Favourites = findViewById(R.id.iv_Favourites);
        iv_Weather = findViewById(R.id.iv_Weather);
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

        iv_Weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, WeatherActivity.class);
                startActivity(i);
            }
        });

        iv_Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(i);
            }
        });

        iv_CurrencyConverter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CurrencyConverterActivity.class);
                startActivity(i);
            }
        });

        iv_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });

        iv_SafetyInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SafetyInfoActivity.class);
                startActivity(i);
            }
        });

        iv_Maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MapboxActivity.class);
                startActivity(i);
            }
        });

        iv_Favourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, FavouritePlacesActivity.class);
                startActivity(i);
            }
        });
    }
}