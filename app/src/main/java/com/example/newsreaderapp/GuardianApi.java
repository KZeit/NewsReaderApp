package com.example.newsreaderapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GuardianApi {
    @GET("search")
    Call<GuardianResponse> searchArticles(@Query("api-key") String apiKey, @Query("q") String query);
}
