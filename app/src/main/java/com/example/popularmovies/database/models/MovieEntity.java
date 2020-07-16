package com.example.popularmovies.database.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.popularmovies.network.models.ReviewsResponse;
import com.example.popularmovies.network.models.TrailersResponse;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "movies")
public class MovieEntity implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "movie_id")
    private int movieId;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "image_url")
    private String imageUrl;

    @ColumnInfo(name = "rating")
    private double rating;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "release_date")
    private String releaseDate;

    @ColumnInfo
    private List<ReviewsResponse> reviews;

    @ColumnInfo
    private List<TrailersResponse> trailers;

    public int getId() {
        return id;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ReviewsResponse> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewsResponse> reviews) {
        this.reviews = reviews;
    }

    public List<TrailersResponse> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<TrailersResponse> trailers) {
        this.trailers = trailers;
    }
}