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

    @Query("SELECT * FROM movies WHERE id = :id")
    LiveData<MovieEntity> loadMovieById(int id);

    @Query("DELETE FROM movies WHERE ID = :id")
    void deleteMovieById(int id);
}
