package com.sm19003564.omnimarkerza;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.sm19003564.omnimarkerza.Retrofit.RetrofitBuilder;
import com.sm19003564.omnimarkerza.Retrofit.RetrofitInterface;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Math.round;

public class CurrencyConverterActivity extends AppCompatActivity {
    //declare variables
    Spinner spinnerFirstAmount, spinnerSecondAmount;
    EditText firstAmount, secondAmount;
    Button convertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);
        //Initialization
        spinnerFirstAmount = findViewById(R.id.spinnerFirstAmount);
        spinnerSecondAmount = findViewById(R.id.spinnerSecondAmount);

        firstAmount = findViewById(R.id.etFirstAmount);
        secondAmount = findViewById(R.id.etSecondAmount);

        convertButton = findViewById(R.id.convertButton);

        //Populate Spinners
        String [] spinnerList  = {"AED","AFN","ALL","AMD","ANG","AOA","ARS","AUD","AWG","AZN","BRL","BSD","BTN","BWP","BYN","CAD","CDF","CHF","CLP","CNY","COP","CRC","CUC","CUP","CVE","CZK","DJF","DZD","EGP","ETB","EUR","FJD","GBP","GHS","GNF","GTQ","GYD", "HKD", "HRK", "HTG", "HUF", "IDR", "ILS", "INR", "IQD", "IRR", "ISK", "JMD", "JOD", "JPY", "KES", "KHR", "KID", "KMF","KRW", "KWD", "KYD", "KZT", "LAK", "LBP", "LKR", "LRD", "LSL", "LYD", "MAD", "MGA", "MMK", "MNT", "MRU", "MUR", "MVR", "MWK", "MXN", "MYR", "MZN", "NAD", "NGN", "NOK", "NPR", "NZD", "OMR", "PAB", "PEN", "PGK", "PHP", "PKR", "PLN", "PYG", "QAR", "RON", "RSD", "RUB", "RWF", "SAR", "SCR", "SDG", "SEK", "SGD", "SOS", "SSP", "SYP", "THB", "TND", "TRY", "TVD", "TWD", "TZS", "UAH", "UGX", "USD", "UYU", "UZS", "VES", "VND", "XAF", "XCD", "XDR", "XOF", "YER", "ZAR", "ZMW",};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spinnerList);
        spinnerFirstAmount.setAdapter(adapter);
        spinnerSecondAmount.setAdapter(adapter);

        //on button clicked
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check for empty text boxes and invalid amounts
                if ((firstAmount.getText().toString().trim().length() == 0) || ((Double.parseDouble(firstAmount.getText().toString())) <= 0)) {
                    Toast.makeText(getApplicationContext(), "Please enter a valid amount.", Toast.LENGTH_SHORT).show();
                    return;
                }
                //check for 2 different currencies
                if(spinnerFirstAmount.getSelectedItem().toString() == spinnerSecondAmount.getSelectedItem().toString()) {
                    Toast.makeText(getApplicationContext(), "Please select 2 different currencies ", Toast.LENGTH_SHORT).show();
                    return;
                }

                RetrofitInterface retrofitInterface = RetrofitBuilder.getRetrofitInstance().create(RetrofitInterface.class);
                //get conversion rates using api
                Call<JsonObject> call = retrofitInterface.getExchangeCurrency(spinnerFirstAmount.getSelectedItem().toString());
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonObject res = response.body();
                        JsonObject rate = res.getAsJsonObject("conversion_rates");
                        double first = Double.valueOf(firstAmount.getText().toString());
                        //get rate of second spinner
                        double multiplier = Double.valueOf(rate.get(spinnerSecondAmount.getSelectedItem().toString()).toString());
                        //multiply
                        double result = first * multiplier;
                        //format result
                        DecimalFormat df = new DecimalFormat("#.##");
                        //display result
                        secondAmount.setText(String.valueOf(df.format(result)));
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });
            }


        });
    }
}