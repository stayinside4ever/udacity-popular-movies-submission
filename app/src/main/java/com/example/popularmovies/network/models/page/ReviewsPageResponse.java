package com.example.popularmovies.network.models.page;

import com.example.popularmovies.network.models.ReviewsResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewsPageResponse {

    @SerializedName("results")
    private List<ReviewsResponse> reviewsResponses;


    public List<ReviewsResponse> getReviewsResponses() {
        return reviewsResponses;
    }

    public void setReviewsResponses(List<ReviewsResponse> reviewsResponses) {
        this.reviewsResponses = reviewsResponses;
    }
}
