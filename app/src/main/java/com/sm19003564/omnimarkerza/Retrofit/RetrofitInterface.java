package com.sm19003564.omnimarkerza.Retrofit;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitInterface {
    @GET("v6/af7b34484b40826382b451c1/latest/{currency}")
    Call<JsonObject> getExchangeCurrency(@Path("currency") String currency);
}
