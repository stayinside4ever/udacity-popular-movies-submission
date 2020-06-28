package com.example.popularmovies;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.database.MovieEntity;
import com.example.popularmovies.network.NetworkUtils;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

enum LoadingState { LOADING, FINISHED }

public class MainActivity extends AppCompatActivity{
    NetworkUtils networkUtils;
    PosterListAdapter adapter;
    RecyclerView postersRecyclerView;
    ProgressBar progressBar;
    TabLayout tabFilters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        postersRecyclerView = (RecyclerView) findViewById(R.id.rv_posters_list);
        progressBar = (ProgressBar) findViewById(R.id.pb_posters_list);
        tabFilters = (TabLayout) findViewById(R.id.tab_layout_filters);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        postersRecyclerView.setLayoutManager(layoutManager);

        adapter = new PosterListAdapter(new ArrayList<MovieEntity>());
        postersRecyclerView.setAdapter(adapter);

        networkUtils = new NetworkUtils(new NetworkRequestDone() {
            @Override
            public void onMoviesFetched(List<MovieEntity> movies) {
                adapter.update(movies);
                setLoadingState(LoadingState.FINISHED);
            }
        });

        tabFilters.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        networkUtils.getPopularMoviesFromNetwork();
                        setLoadingState(LoadingState.LOADING); // TODO: handle failures, no connection
                        break;
                    case 1:
                        networkUtils.getTopRatedMoviesFromNetwork();
                        setLoadingState(LoadingState.LOADING); // TODO: handle failures, no connection
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        networkUtils.getPopularMoviesFromNetwork();
        setLoadingState(LoadingState.LOADING); // TODO: handle failures, no connection
    }

    private void setLoadingState(LoadingState state) {
        switch (state) {
            case LOADING:
                postersRecyclerView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                break;
            case FINISHED:
                progressBar.setVisibility(View.GONE);
                postersRecyclerView.setVisibility(View.VISIBLE);
                break;
        }
    }

}

