package com.example.popularmovies;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.database.MovieEntity;
import com.example.popularmovies.network.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    NetworkUtils networkUtils;
    PosterListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView postersRecyclerView = (RecyclerView) findViewById(R.id.rv_posters_list);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        postersRecyclerView.setLayoutManager(layoutManager);

        adapter = new PosterListAdapter(new ArrayList<MovieEntity>());
        postersRecyclerView.setAdapter(adapter);

        networkUtils = new NetworkUtils(new NetworkRequestDone() {
            @Override
            public void onMoviesFetched(List<MovieEntity> movies) {
                adapter.update(movies);
            }
        });

        networkUtils.getMoviesFromNetwork("TODO");
    }

}

