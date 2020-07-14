package com.example.popularmovies.network;

import android.util.Log;

import com.example.popularmovies.AppConstants;
import com.example.popularmovies.database.MovieEntity;
import com.example.popularmovies.network.callbacks.DetailsNetworkRequestDone;
import com.example.popularmovies.network.callbacks.MovieNetworkRequestDone;
import com.example.popularmovies.network.models.MovieResponse;
import com.example.popularmovies.network.models.page.MoviesPageResponse;
import com.example.popularmovies.network.models.page.ReviewsPageResponse;
import com.example.popularmovies.network.models.ReviewsResponse;
import com.example.popularmovies.network.models.page.TrailersPageResponse;
import com.example.popularmovies.network.models.TrailersResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtils {
    List<MovieResponse> movieResponses = new ArrayList<>();
    List<ReviewsResponse> reviewsResponses = new ArrayList<>();
    List<TrailersResponse> trailersResponses = new ArrayList<>();
    List<MovieEntity> movies = new ArrayList<>();
    MovieNetworkRequestDone moviesCallback;
    DetailsNetworkRequestDone detailsCallback;
    private static Retrofit retrofit;
    private static MoviesNetworkApi api;

    public NetworkUtils(MovieNetworkRequestDone moviesCallback) {
        this.moviesCallback = moviesCallback;
    }

    public NetworkUtils(DetailsNetworkRequestDone detailsCallback) {
        this.detailsCallback = detailsCallback;
    }

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(AppConstants.QUERIES_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

    public static MoviesNetworkApi getApiInstance() {
        if (api == null) {
            api = getRetrofitInstance().create(MoviesNetworkApi.class);
        }

        return api;
    }

    public void getPopularMoviesFromNetwork() {
        getApiInstance().getPopularMovies().enqueue(new Callback<MoviesPageResponse>() {
            @Override
            public void onResponse(Call<MoviesPageResponse> call, Response<MoviesPageResponse> response) {
                Log.d("NetworkUtils", "POPULAR RESPONSE SUCCESSFUL");
                if (!response.isSuccessful()) {
                    Log.w("NetworkUtils", response.code() + " " + response.message());
                } else {
                    movies.clear();
                    movieResponses = response.body().getMovieResponses();

                    if (!movieResponses.isEmpty()) {
                        for (MovieResponse movieResponse : movieResponses) {
                            movies.add(movieResponse.convertToMovieEntity());
                        }
                    }

                    moviesCallback.onMoviesFetched(movies);
                }
            }

            @Override
            public void onFailure(Call<MoviesPageResponse> call, Throwable t) {
                Log.e("NetworkUtils", "Request failed: " + t.getMessage());
                moviesCallback.onRequestFailed();
            }
        });
    }

    public void getTopRatedMoviesFromNetwork() {
        getApiInstance().getTopRatedMovies().enqueue(new Callback<MoviesPageResponse>() {
            @Override
            public void onResponse(Call<MoviesPageResponse> call, Response<MoviesPageResponse> response) {
                Log.d("NetworkUtils", "TOP RATED RESPONSE SUCCESSFUL");
                if (!response.isSuccessful()) {
                    Log.w("NetworkUtils", response.code() + " " + response.message());
                } else {
                    movies.clear();
                    movieResponses = response.body().getMovieResponses();

                    if (!movieResponses.isEmpty()) {
                        for (MovieResponse movieResponse : movieResponses) {
                            movies.add(movieResponse.convertToMovieEntity());
                        }
                    }

                    moviesCallback.onMoviesFetched(movies);
                }
            }

            @Override
            public void onFailure(Call<MoviesPageResponse> call, Throwable t) {
                Log.e("NetworkUtils", "Request failed: " + t.getMessage());
                moviesCallback.onRequestFailed();
            }
        });
    }

    public void getReviewsFromNetwork(int movieId) {
        getApiInstance().getMovieReviews(movieId).enqueue(new Callback<ReviewsPageResponse>() {
            @Override
            public void onResponse(Call<ReviewsPageResponse> call, Response<ReviewsPageResponse> response) {
                Log.d("NetworkUtils", "REVIEW RESPONSE SUCCESSFUL");
                if (!response.isSuccessful()) {
                    Log.w("NetworkUtils", response.code() + " " + response.message());
                } else {
                    reviewsResponses = response.body().getReviewsResponses();
                    detailsCallback.onReviewsFetched((reviewsResponses == null || reviewsResponses.isEmpty()) ? null : reviewsResponses);
                }
            }

            @Override
            public void onFailure(Call<ReviewsPageResponse> call, Throwable t) {
                Log.e("NetworkUtils", "Request failed: " + t.getMessage());
                detailsCallback.onRequestFailed();
            }
        });
    }

    public void getTrailersFromNetwork(int movieId) {
        getApiInstance().getMovieTrailers(movieId).enqueue(new Callback<TrailersPageResponse>() {
            @Override
            public void onResponse(Call<TrailersPageResponse> call, Response<TrailersPageResponse> response) {
                Log.d("NetworkUtils", "TRAILERS RESPONSE SUCCESSFUL");
                if (!response.isSuccessful()) {
                    Log.w("NetworkUtils", response.code() + " " + response.message());
                } else {
                    trailersResponses = response.body().getTrailersResponses();
                    detailsCallback.onTrailersFetched((trailersResponses == null || trailersResponses.isEmpty()) ? null : trailersResponses);
                }
            }

            @Override
            public void onFailure(Call<TrailersPageResponse> call, Throwable t) {
                Log.e("NetworkUtils", "Request failed: " + t.getMessage());
                detailsCallback.onRequestFailed();
            }
        });
    }
}

