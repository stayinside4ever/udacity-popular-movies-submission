package com.example.popularmovies.network.models;

import com.example.popularmovies.AppConstants;
import com.example.popularmovies.database.MovieEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieResponse {
    @Expose
    @SerializedName("vote_average")
    private double voteAverage;

    @Expose
    @SerializedName("poster_path")
    private String posterPath;

    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("original_title")
    private String originalTitle;

    @Expose
    @SerializedName("overview")
    private String overview;

    @Expose
    @SerializedName("release_date")
    private String releaseDate;

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public MovieEntity convertToMovieEntity() {
        MovieEntity movie = new MovieEntity();
        movie.setTitle(getOriginalTitle());
        movie.setMovieId(getId());
        movie.setDescription(getOverview());
        movie.setImageUrl(AppConstants.IMAGES_BASE_URL + "w185" + getPosterPath()); // TODO: different sizes for different screens
        movie.setRating(getVoteAverage());
        movie.setReleaseDate(getReleaseDate());
        return movie;

    }

}
