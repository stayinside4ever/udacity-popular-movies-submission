package com.example.popularmovies.network.callbacks;

import com.example.popularmovies.network.models.ReviewsResponse;
import com.example.popularmovies.network.models.TrailersResponse;

import java.util.List;

public interface DetailsNetworkRequestDone {
    void onReviewsFetched(List<ReviewsResponse> reviewsResponses);
    void onTrailersFetched(List<TrailersResponse> trailersResponses);
    void onRequestFailed();
}
