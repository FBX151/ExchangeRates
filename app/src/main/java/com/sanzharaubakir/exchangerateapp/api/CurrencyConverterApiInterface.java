package com.sanzharaubakir.exchangerateapp.api;

import java.util.Map;

import io.reactivex.Observable;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CurrencyConverterApiInterface {

    public static final String BASE_URL = "https://free.currconv.com";

    @GET("/api/v7/convert?compact=ultra")
    Observable<Map<String, Float>> getExchangeRates(@Query("q") String rates, @Query("apiKey") String apiKey);

}
