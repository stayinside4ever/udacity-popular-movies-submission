package com.example.popularmovies.network;

import android.util.Log;

import com.example.popularmovies.AppConstants;
import com.example.popularmovies.NetworkRequestDone;
import com.example.popularmovies.database.MovieEntity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtils {
    List<MovieResponse> movieResponses = new ArrayList<>();
    List<MovieEntity> movies = new ArrayList<>();
    NetworkRequestDone callback;
    private static Retrofit retrofit;
    private static MoviesNetworkApi api;

    public NetworkUtils(NetworkRequestDone callback) {
        this.callback = callback;
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
        getApiInstance().getPopularMovies().enqueue(new Callback<PageResponse>() {
            @Override
            public void onResponse(Call<PageResponse> call, Response<PageResponse> response) {
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

                    callback.onMoviesFetched(movies);
                }
            }

            @Override
            public void onFailure(Call<PageResponse> call, Throwable t) {
               Log.e("NetworkUtils", "Request failed: " + t.getMessage());
            }
        });
    }

    public void getTopRatedMoviesFromNetwork() {
        getApiInstance().getTopRatedMovies().enqueue(new Callback<PageResponse>() {
            @Override
            public void onResponse(Call<PageResponse> call, Response<PageResponse> response) {
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

                    callback.onMoviesFetched(movies);
                }
            }

            @Override
            public void onFailure(Call<PageResponse> call, Throwable t) {
                Log.e("NetworkUtils", "Request failed: " + t.getMessage());
            }
        });
    }
}

