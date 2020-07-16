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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.popularmovies.R;
import com.example.popularmovies.adapters.PosterListAdapter;
import com.example.popularmovies.database.models.MovieEntity;
import com.example.popularmovies.databinding.ActivityMainBinding;
import com.example.popularmovies.network.callbacks.MovieRequestFailed;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

enum LoadingState {LOADING, FINISHED, EMPTY}

public class MainActivity extends AppCompatActivity implements PosterListAdapter.ItemClickListener {
    private PosterListAdapter adapter;
    private Toast requestFailToast;
    private ActivityMainBinding binding;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        GridLayoutManager layoutManager = new GridLayoutManager(this, calculateNoOfColumns(this));
        binding.rvPostersList.setLayoutManager(layoutManager);

        adapter = new PosterListAdapter(new ArrayList<MovieEntity>(), this);
        binding.rvPostersList.setAdapter(adapter);

        MainViewModelFactory factory = new MainViewModelFactory(getApplication(), new MovieRequestFailed() {

            @Override
            public void moviesRequestFailed() {
                showRequestFailToast();
            }
        });

        viewModel = new ViewModelProvider(this, factory).get(MainViewModel.class);

        viewModel.getMovies().observe(this, new Observer<List<MovieEntity>>() {
            @Override
            public void onChanged(List<MovieEntity> movieEntities) {
                if (movieEntities != null && !movieEntities.isEmpty()) {
                    if (movieEntities.get(0).getId() != -1) {
                        adapter.update(movieEntities);
                        setLoadingState(LoadingState.FINISHED);
                    } else {
                        setLoadingState(LoadingState.EMPTY);
                    }

                } else {
                    setLoadingState(LoadingState.LOADING);
                }
            }
        });

        binding.tabLayoutFilters.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewModel.makeRepositoryRequest(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewModel.makeRepositoryRequest(tab.getPosition());
            }
        });

        binding.tabLayoutFilters.getTabAt(viewModel.getCurrentPos()).select();

        viewModel.makeRepositoryRequest(binding.tabLayoutFilters.getSelectedTabPosition());
    }


    private void setLoadingState(LoadingState state) {
        switch (state) {
            case LOADING:
                binding.rvPostersList.setVisibility(GONE);
                binding.pbPostersList.setVisibility(View.VISIBLE);
                binding.tvNoFaves.setVisibility(GONE);
                break;
            case FINISHED:
                binding.pbPostersList.setVisibility(GONE);
                binding.rvPostersList.setVisibility(View.VISIBLE);
                binding.tvNoFaves.setVisibility(GONE);
                break;
            case EMPTY:
                binding.pbPostersList.setVisibility(GONE);
                binding.rvPostersList.setVisibility(GONE);
                binding.tvNoFaves.setVisibility(View.VISIBLE);
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

    // Helper method to calculate the best amount of columns to be displayed on a device
    // provided by Udacity code reviewer
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 180;
        return (int) (dpWidth / scalingFactor);
    }
}

