package com.example.popularmovies.network.models.page;

import com.example.popularmovies.network.models.TrailersResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailersPageResponse {

    @SerializedName("results")
    private List<TrailersResponse> trailersResponses;


    public List<TrailersResponse> getTrailersResponses() {
        return trailersResponses;
    }

    public void setTrailersResponses(List<TrailersResponse> trailersResponses) {
        this.trailersResponses = trailersResponses;
    }
}
