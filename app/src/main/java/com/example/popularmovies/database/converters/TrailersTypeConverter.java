package com.example.popularmovies.database.converters;

import androidx.room.TypeConverter;

import com.example.popularmovies.network.models.TrailersResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class TrailersTypeConverter {
    @TypeConverter
    public String fromTrailerResponses(List<TrailersResponse> trailersResponses) {
        if (trailersResponses == null) return null;

        Gson gson = new Gson();
        Type type = new TypeToken<List<TrailersResponse>>() {
        }.getType();
        return gson.toJson(trailersResponses, type);
    }

    @TypeConverter
    public List<TrailersResponse> fromJson(String json) {
        if (json == null) return null;

        Gson gson = new Gson();
        Type type = new TypeToken<List<TrailersResponse>>() {
        }.getType();
        return gson.fromJson(json, type);
    }
}
