package com.mindera.flickergallery.service;

import com.mindera.flickergallery.to.Kitten;
import com.mindera.flickergallery.to.KittenSizes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {
    String URL_BASE = "https://api.flickr.com/";
    String API_KEY = "9a95c68a9c6ec61104cd3967dcbb8bd3";
    String TAGS_KITTEN = "kitten";
    String FORMAT = "json";
    int NO_JSON_CALLBACK = 1;

    @GET("services/rest/")
    Call<Kitten> getKittens(@Query("method") String method,
                            @Query("api_key") String apiKey,
                            @Query("tags") String tags,
                            @Query("page") int page,
                            @Query("format") String format,
                            @Query("nojsoncallback") int noJsonCallback);

    @GET("services/rest/")
    Call<KittenSizes> getKittensSizes(@Query("method") String method,
                                      @Query("api_key") String apiKey,
                                      @Query("photo_id") String tags,
                                      @Query("format") String format,
                                      @Query("nojsoncallback") int noJsonCallback);
}
