package com.example.popularmovies.network.models;

import com.google.gson.annotations.Expose;

public class TrailersResponse {
    @Expose
    private String name;

    @Expose
    private String key;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
