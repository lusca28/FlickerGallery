package com.mindera.flickergallery.options;

import com.mindera.flickergallery.service.RetrofitService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {
    private final Retrofit retrofit;

    public RetrofitConfig() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public RetrofitService getKittensService() {
        return this.retrofit.create(RetrofitService.class);
    }
}
