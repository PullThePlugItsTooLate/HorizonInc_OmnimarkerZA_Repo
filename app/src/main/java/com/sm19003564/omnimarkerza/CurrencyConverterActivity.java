package com.sm19003564.omnimarkerza;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.sm19003564.omnimarkerza.Retrofit.RetrofitBuilder;
import com.sm19003564.omnimarkerza.Retrofit.RetrofitInterface;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//Widget imports
//Retrofit imports

public class CurrencyConverterActivity extends AppCompatActivity {
    //Variable declaration
    Spinner spinnerFirstAmount, spinnerSecondAmount;
    EditText firstAmount;
    TextView secondAmount;
    Button convertButton;
    String currency1, currency2;

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
        String [] spinnerList  = {"AED - United Arab Emirates","AFN - Afghanistan","ALL - Albania","AMD - Armenia","ANG - Netherlands Antilles","AOA - Angola","ARS - Argentina","AUD - Australia","AWG - Aruba","AZN - Azerbaijan","BRL - Brazil","BSD - Bahamas","BTN - Bhutan","BWP - Botswana","BYN - Belarus","CAD - Canada","CDF - Democratic Republic of the Congo","CHF - Switzerland","CLP - Chile","CNY - China","COP - Colombia","CRC - Costa Rica","CUC - Cuba","CUP - Cuba","CVE - Cape Verde","CZK - Czech Republic","DJF - Djibouti","DZD - Algeria","EGP - Egypt","ETB - Ethiopia","EUR - European Union","FJD - Fiji","GBP - United Kingdom","GHS - Ghana","GNF - Guinea","GTQ - Guatemala","GYD - Guyana", "HKD - Hong Kong", "HRK - Croatia", "HTG - Haiti", "HUF - Hungary", "IDR - Indonesia", "INR - India", "IQD - Iraq", "IRR - Iran", "ISK - Iceland", "JMD - Jamaica", "JOD - Jordan", "JPY - Japan", "KES - Kenya", "KHR - Cambodia", "KID - Kiribati", "KMF - Comoros","KRW - South Korea", "KWD - Kuwait", "KYD - Cayman Islands", "KZT - Kazakhstan", "LAK - Laos", "LBP - Lebanon", "LKR - Sri Lanka", "LRD - Liberia", "LSL - Lesotho", "LYD - Libya", "MAD - Morocco", "MGA - Madagascar", "MMK - Myanmar", "MNT - Mongolia", "MUR - Mauritius", "MVR - Maldives", "MWK - Malawi", "MXN - Mexico", "MYR - Malaysia", "MZN - Mozambique", "NAD - Namibia", "NGN - Nigeria", "NOK - Norway", "NPR - Nepal", "NZD - New Zealand", "OMR - Oman", "PAB - Panama", "PEN - Peru", "PGK - Papua New Guinea", "PHP - Philippines", "PKR - Pakistan", "PLN - Poland", "PYG - Paraguay", "QAR - Qatar", "RON - Romania", "RSD - Serbia", "RUB - Russia", "RWF - Rwanda", "SAR - Saudi Arabia", "SCR - Seychelles", "SDG - Sudan", "SEK - Sweden", "SGD - Singapore", "SOS - Somalia", "SSP - South Sudan", "SYP - Syria", "THB - Thailand", "TND - Tunisia", "TRY - Turkey", "TVD - Tuvalu", "TWD - Taiwan", "TZS - Tanzania", "UAH - Ukraine", "UGX - Uganda", "USD - United States of America", "UYU - Uruguay", "UZS - Uzbekistan", "VES - Venezuela", "VND - Vietnam", "XAF - CEMAC", "XCD - Organisation of Eastern Caribbean States", "XDR - International Monetary Fund", "XOF - CFA", "YER - Yemen", "ZAR - South Africa", "ZMW - Zambia"};
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

                //get country currency code
                currency1 = spinnerFirstAmount.getSelectedItem().toString();
                currency2 = spinnerSecondAmount.getSelectedItem().toString();

                currency1 = currency1.substring(0,3);
                currency2 = currency2.substring(0,3);

                RetrofitInterface retrofitInterface = RetrofitBuilder.getRetrofitInstance().create(RetrofitInterface.class);

                //get conversion rates using api
                Call<JsonObject> call = retrofitInterface.getExchangeCurrency(currency1);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonObject res = response.body();
                        JsonObject rate = res.getAsJsonObject("conversion_rates");
                        double first = Double.valueOf(firstAmount.getText().toString());
                        //get rate of second spinner
                        double multiplier = Double.valueOf(rate.get(currency2).toString());
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
/**
 *
 *
 * -----------------------------CODE-ATTRIBUTION---------------------------------
 *  Resource Type: Web Article
 *  Available at: https://demonuts.com/android-spinner-searchable/
 *  Author: Demonuts editorial team
 *  Year: N/A
 *  Year used: 2021
 *  Date used: 20/06
 * -------------------------------------------------------------------------------
 */
/**
 *
 *
 * -----------------------------CODE-ATTRIBUTION---------------------------------
 *  Resource Type: Youtube Video
 *  Available at: https://www.youtube.com/watch?v=lKug3iqKujM&t=910s
 *  Author: Apprials https://www.youtube.com/channel/UCSprcv_Ih0DadqgXsuQ0Wig
 *  Year: 2020
 *  Year used: 2021
 *  Date used: 24/05
 * -------------------------------------------------------------------------------
 */