package com.example.popularmovies.network;

import com.example.popularmovies.AppConstants;
import com.example.popularmovies.network.models.page.MoviesPageResponse;
import com.example.popularmovies.network.models.page.ReviewsPageResponse;
import com.example.popularmovies.network.models.page.TrailersPageResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MoviesNetworkApi {

    @GET("movie/popular?api_key=" + AppConstants.API_KEY)
    Call<MoviesPageResponse> getPopularMovies();

    @GET("movie/top_rated?api_key=" + AppConstants.API_KEY)
    Call<MoviesPageResponse> getTopRatedMovies();

    @GET("movie/{id}/reviews?api_key=" + AppConstants.API_KEY)
    Call<ReviewsPageResponse> getMovieReviews(@Path("id") int id);

    @GET("movie/{id}/videos?api_key=" + AppConstants.API_KEY)
    Call<TrailersPageResponse> getMovieTrailers(@Path("id") int id);
}
