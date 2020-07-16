package com.example.popularmovies.database.converters;

import androidx.room.TypeConverter;

import com.example.popularmovies.network.models.ReviewsResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ReviewsTypeConverter {
    @TypeConverter
    public String fromReviewResponses(List<ReviewsResponse> reviewsResponses) {
        if (reviewsResponses == null) return null;

        Gson gson = new Gson();
        Type type = new TypeToken<List<ReviewsResponse>>() {
        }.getType();
        return gson.toJson(reviewsResponses, type);
    }

    @TypeConverter
    public List<ReviewsResponse> fromJson(String json) {
        if (json == null) return null;

        Gson gson = new Gson();
        Type type = new TypeToken<List<ReviewsResponse>>() {
        }.getType();
        return gson.fromJson(json, type);
    }
}
