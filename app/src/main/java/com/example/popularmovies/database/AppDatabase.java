package com.example.popularmovies.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.popularmovies.database.converters.ReviewsTypeConverter;
import com.example.popularmovies.database.converters.TrailersTypeConverter;
import com.example.popularmovies.database.models.MovieEntity;

@Database(entities = {MovieEntity.class}, version = 1, exportSchema = false)
@TypeConverters({ReviewsTypeConverter.class, TrailersTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "movies_database";
    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .build();
            }
        }
        return INSTANCE;
    }

    public abstract MoviesDao moviesDao();
}
