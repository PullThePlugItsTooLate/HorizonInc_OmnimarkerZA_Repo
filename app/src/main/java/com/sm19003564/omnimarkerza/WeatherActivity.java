package com.sm19003564.omnimarkerza;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.TextView;

import java.nio.file.Files;

public class WeatherActivity extends AppCompatActivity {

    SearchView searchBar;
    TextView tvCity1, tvCity2, tvCity3, tvCity4, tvCity5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        searchBar = findViewById(R.id.svSearchBar);

        tvCity1 = findViewById(R.id.tvCity1);
        tvCity2 = findViewById(R.id.tvCity2);
        tvCity3 = findViewById(R.id.tvCity3);
        tvCity4 = findViewById(R.id.tvCity4);
        tvCity5 = findViewById(R.id.tvCity5);


    }
}