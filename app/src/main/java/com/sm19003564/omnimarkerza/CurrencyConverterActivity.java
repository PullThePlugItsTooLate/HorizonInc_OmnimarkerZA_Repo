package com.sm19003564.omnimarkerza;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class CurrencyConverterActivity extends AppCompatActivity {

    Spinner spinnerFirstAmount, spinnerSecondAmount;
    EditText firstAmount, secondAmount;
    TextView tvCurrencyResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);

        spinnerFirstAmount = findViewById(R.id.spinnerFirstAmount);
        spinnerSecondAmount = findViewById(R.id.spinnerSecondAmount);

        firstAmount = findViewById(R.id.etFirstAmount);
        secondAmount = findViewById(R.id.etSecondAmount);

        tvCurrencyResults = findViewById(R.id.tvCurrencyResults);

    }
}