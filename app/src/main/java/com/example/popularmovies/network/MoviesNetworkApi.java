package com.example.popularmovies.network;

import com.example.popularmovies.AppConstants;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MoviesNetworkApi {

    @GET("movie/popular?api_key=" + AppConstants.API_KEY)
    Call<PageResponse> getPopularMovies();

    @GET("movie/top_rated?api_key=" + AppConstants.API_KEY)
    Call<PageResponse> getTopRatedMovies();
}
