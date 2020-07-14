package com.example.popularmovies.network.models.page;

import com.example.popularmovies.network.models.MovieResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesPageResponse {

    @SerializedName("results")
    private List<MovieResponse> movieResponses;


    public List<MovieResponse> getMovieResponses() {
        return movieResponses;
    }

    public void setMovieResponses(List<MovieResponse> movieResponses) {
        this.movieResponses = movieResponses;
    }
}
