package com.example.popularmovies.network.models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class ReviewsResponse implements Serializable {
    @Expose
    private String author;

    @Expose
    private String content;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
