package com.example.popularmovies.network;

import com.example.popularmovies.network.models.ReviewsResponse;

import java.util.List;

public interface DetailsNetworkRequestDone {
    void onReviewsFetched(List<ReviewsResponse> reviewsResponses);
    void onRequestFailed();
}
