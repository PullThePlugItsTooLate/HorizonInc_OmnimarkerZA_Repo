package com.sm19003564.omnimarkerza;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class WeatherActivity extends AppCompatActivity {

    EditText etCity,etCountry;
    TextView tvResult, tvHeading, tvTemp, tvFeelsLike, tvHumidity, tvDescription, tvWind, tvCloud, tvPressure;
    SearchView searchView;
    private final String url = "http://api.openweathermap.org/data/2.5/weather";
    private final String appid = "33435a0b46b4d4dd2bd5e3a89d2cbf92";
    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

       // etCity = findViewById(R.id.etCity);
        etCountry = findViewById(R.id.etCountry);
        tvResult = findViewById(R.id.tvResult);
        searchView = findViewById(R.id.SearchBar);
       /* tvHeading = findViewById(R.id.tvHeading);
        tvFeelsLike = findViewById(R.id.tvFeelsLike);
        tvHumidity = findViewById(R.id.tvHumidity);
        tvDescription = findViewById(R.id.tvDescription);
        tvWind = findViewById(R.id.tvWind);
        tvCloud = findViewById(R.id.tvCloud);
        tvPressure = findViewById(R.id.tvPressure);
        tvTemp = findViewById(R.id.tvTemp); */


        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });
    }


    public void getWeatherDetails(View view)
    {

        String tempUrl = "";
        String city = searchView.getQuery().toString().trim();
        String country = etCountry.getText().toString().trim();

        if(city.equals(""))
        {
            Toast.makeText(WeatherActivity.this, "Please fill in the above field", Toast.LENGTH_SHORT).show();
        }else
            {
                if(!country.equals(""))
                {
                 tempUrl = url + "?q=" + city + "," + country + "&appid=" + appid;
                } else
                    {
                        tempUrl = url + "?q=" + city + "&appid=" + appid;
                    }

                StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.d("response", response);
                        String output = "";
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                            JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                            String description = jsonObjectWeather.getString("description");
                            JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                            double temp = jsonObjectMain.getDouble("temp") - 273.15;
                            double feelslike = jsonObjectMain.getDouble("feels_like") - 273.15;
                            float pressure = jsonObjectMain.getInt("pressure");
                            int humidity = jsonObjectMain.getInt("humidity");
                            JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
                            String wind = jsonObjectWind.getString("speed");
                            JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
                            String clouds = jsonObjectClouds.getString("all");
                            JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
                            String countryName = jsonObjectSys.getString("country");
                            String cityName = jsonResponse.getString("name");
                           // tvResult.setTextColor();

                            output += "\t\t\t\t\tCurrent Weather of " + cityName + " (" + countryName + ")\n\n"
                                    + "\n Temp - " + df.format(temp) + " °C"
                                    + "\n Feels like - " + df.format(feelslike) + " °C"
                                    + "\n Humidity - " + humidity + " %"
                                    + "\n Description - " + description
                                    + "\n Wind Speed - " + wind + "m/s (meters per second)"
                                    + "\n Cloudiness - " + clouds + "%"
                                    + "\n Pressure - " + pressure + "hPa" ;

                             tvResult.setText(output);




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //  TextView tvResult, tvHeading, tvTemp, tvFeelsLike, tvHumidity, tvDescription, tvWind, tvCloud, tvPressure;
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(WeatherActivity.this,"City cannot be found, please try again", Toast.LENGTH_SHORT).show();
                    }
                });

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);

            }


    }
}