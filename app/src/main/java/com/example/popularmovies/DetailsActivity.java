package com.example.popularmovies;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.popularmovies.databinding.ActivityDetailsBinding;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE_TITLE = "EXTRA_MOVIE_TITLE";
    public static final String EXTRA_MOVIE_DESCRIPTION = "EXTRA_MOVIE_DESCRIPTION";
    public static final String EXTRA_MOVIE_RATING = "EXTRA_MOVIE_RATING";
    public static final String EXTRA_MOVIE_IMAGE_URL = "EXTRA_MOVIE_IMAGE_URL";
    public static final String EXTRA_MOVIE_RELEASE_DATE = "EXTRA_MOVIE_RELEASE_DATE";

    private ActivityDetailsBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_MOVIE_IMAGE_URL)) {
            Picasso.get()
                    .load(intent.getStringExtra(EXTRA_MOVIE_IMAGE_URL))
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .error(R.drawable.ic_baseline_broken_image_24)
                    .into(binding.ivDetailsPoster);
        }

        if (intent.hasExtra(EXTRA_MOVIE_TITLE)) {
            binding.tvTitle.setText(intent.getStringExtra(EXTRA_MOVIE_TITLE));
        }

        if (intent.hasExtra(EXTRA_MOVIE_RATING)) {
            binding.tvRating.setText(String.valueOf(intent.getDoubleExtra(EXTRA_MOVIE_RATING, 0.0)));
        }

        if (intent.hasExtra(EXTRA_MOVIE_DESCRIPTION)) {
            binding.tvDescription.setText(intent.getStringExtra(EXTRA_MOVIE_DESCRIPTION));
        }

        if (intent.hasExtra(EXTRA_MOVIE_RELEASE_DATE)) {
            binding.tvReleaseDate.setText(intent.getStringExtra(EXTRA_MOVIE_RELEASE_DATE));
        }
    }
}
