package com.example.popularmovies;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {
    private static final Object LOCK = new Object();
    private static AppExecutors INSTANCE;
    private final Executor diskIO;

    private AppExecutors (Executor diskIO) {
        this.diskIO = diskIO;
    }

    public static AppExecutors getInstance() {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                INSTANCE = new AppExecutors(Executors.newSingleThreadExecutor());
            }
        }

        return INSTANCE;
    }

    public Executor diskIo() { return diskIO; }
}
