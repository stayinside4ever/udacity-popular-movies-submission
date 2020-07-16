package com.example.popularmovies.ui;


import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.popularmovies.network.callbacks.MovieRequestFailed;

public class MainViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Application application;
    private final MovieRequestFailed callback;

    public MainViewModelFactory(Application application, MovieRequestFailed callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MainViewModel(application, callback);
    }
}