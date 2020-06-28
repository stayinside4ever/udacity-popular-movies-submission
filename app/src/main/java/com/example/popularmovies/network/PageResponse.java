package com.example.popularmovies.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PageResponse {

    @SerializedName("results")
    private List<MovieResponse> movieResponses;


    public List<MovieResponse> getMovieResponses() {
        return movieResponses;
    }

    public void setMovieResponses(List<MovieResponse> movieResponses) {
        this.movieResponses = movieResponses;
    }
}
