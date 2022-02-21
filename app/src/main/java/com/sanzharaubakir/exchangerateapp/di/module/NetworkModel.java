package com.sanzharaubakir.exchangerateapp.di.module;

import com.sanzharaubakir.exchangerateapp.api.CurrencyConverterApiInterface;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModel {

    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(Retrofit.Builder builder) {
        return createRetrofit(builder, CurrencyConverterApiInterface.BASE_URL);
    }

    @Singleton
    @Provides
    CurrencyConverterApiInterface provideCurrencyConverterApiInterface(Retrofit retrofit) {
        return retrofit.create(CurrencyConverterApiInterface.class);
    }

    private Retrofit createRetrofit(Retrofit.Builder builder, String url) {
        return builder
                .baseUrl(url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
