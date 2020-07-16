package com.example.popularmovies.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.popularmovies.R;
import com.example.popularmovies.adapters.PosterListAdapter;
import com.example.popularmovies.database.AppDatabase;
import com.example.popularmovies.database.models.MovieEntity;
import com.example.popularmovies.databinding.ActivityMainBinding;
import com.example.popularmovies.network.NetworkUtils;
import com.example.popularmovies.network.callbacks.MovieNetworkRequestDone;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

enum LoadingState {LOADING, FINISHED}

public class MainActivity extends AppCompatActivity implements PosterListAdapter.ItemClickListener {
    private NetworkUtils networkUtils;
    private PosterListAdapter adapter;
    private Toast requestFailToast;
    private ActivityMainBinding binding;
    private AppDatabase database;

    private static final String KEY_CURRENT_TAB = "KEY_CURRENT_TAB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = AppDatabase.getInstance(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        GridLayoutManager layoutManager = new GridLayoutManager(this, calculateNoOfColumns(this));
        binding.rvPostersList.setLayoutManager(layoutManager);

        adapter = new PosterListAdapter(new ArrayList<MovieEntity>(), this);
        binding.rvPostersList.setAdapter(adapter);

        networkUtils = new NetworkUtils(new MovieNetworkRequestDone() {
            @Override
            public void onMoviesFetched(List<MovieEntity> movies) {
                adapter.update(movies);
                setLoadingState(LoadingState.FINISHED);
            }

            @Override
            public void onRequestFailed() {
                showRequestFailToast();
            }
        });

        binding.tabLayoutFilters.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                makeRepositoryRequest(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                makeRepositoryRequest(tab.getPosition());
            }
        });

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_CURRENT_TAB)) {
            int pos = savedInstanceState.getInt(KEY_CURRENT_TAB);
            binding.tabLayoutFilters.getTabAt(pos).select();
            makeRepositoryRequest(pos); // TODO: for some reason, does not update recyclerview with correct data
        }


        networkUtils.getPopularMoviesFromNetwork();
        setLoadingState(LoadingState.LOADING);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_TAB, binding.tabLayoutFilters.getSelectedTabPosition());
    }


    private void setLoadingState(LoadingState state) {
        switch (state) {
            case LOADING:
                binding.rvPostersList.setVisibility(View.GONE);
                binding.pbPostersList.setVisibility(View.VISIBLE);
                break;
            case FINISHED:
                binding.pbPostersList.setVisibility(View.GONE);
                binding.rvPostersList.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(MovieEntity movie) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.EXTRA_MOVIE, movie);
        startActivity(intent);
    }

    private void showRequestFailToast() {
        if (requestFailToast != null) {
            requestFailToast.cancel();
        }

        requestFailToast = Toast.makeText(this, R.string.loading_movies_failed_message,
                Toast.LENGTH_LONG);
        requestFailToast.show();
    }

    private void makeRepositoryRequest(int tabPosition) {
        switch (tabPosition) {
            case 0:
                networkUtils.getPopularMoviesFromNetwork();
                setLoadingState(LoadingState.LOADING);
                break;
            case 1:
                networkUtils.getTopRatedMoviesFromNetwork();
                setLoadingState(LoadingState.LOADING);
                break;
            case 2:
                final LiveData<List<MovieEntity>> favourites = database.moviesDao().loadAllFavourites();
                setLoadingState(LoadingState.LOADING);
                favourites.observe(this, new Observer<List<MovieEntity>>() {
                    @Override
                    public void onChanged(List<MovieEntity> movieEntities) {
                        favourites.removeObserver(this);
                        adapter.update(movieEntities);
                    }
                });
                setLoadingState(LoadingState.FINISHED);
        }
    }

    // Helper method to calculate the best amount of columns to be displayed on a device
    // provided by Udacity code reviewer
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 180;
        return (int) (dpWidth / scalingFactor);
    }
}

