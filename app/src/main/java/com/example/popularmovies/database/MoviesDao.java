package com.example.popularmovies.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.popularmovies.database.models.MovieEntity;

import java.util.List;

@Dao
public interface MoviesDao {

    @Insert
    void insertMovie(MovieEntity movieEntity);

    @Query("SELECT * FROM movies ORDER BY id")
    LiveData<List<MovieEntity>> loadAllFavourites();

    @Query("SELECT * FROM movies WHERE movie_id = :movieId")
    MovieEntity loadMovieByMovieId(int movieId);

    @Query("DELETE FROM movies WHERE movie_id = :movieId")
    void deleteMovieByMovieId(int movieId);
}
