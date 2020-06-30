package com.example.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE_TITLE = "EXTRA_MOVIE_TITLE";
    public static final String EXTRA_MOVIE_DESCRIPTION = "EXTRA_MOVIE_DESCRIPTION";
    public static final String EXTRA_MOVIE_RATING = "EXTRA_MOVIE_RATING";
    public static final String EXTRA_MOVIE_IMAGE_URL = "EXTRA_MOVIE_IMAGE_URL";
    public static final String EXTRA_MOVIE_RELEASE_DATE = "EXTRA_MOVIE_RELEASE_DATE";

    private ImageView posterImage;
    private TextView titleTextView;
    private TextView ratingTextView;
    private TextView descriptionTextView;
    private TextView releaseDateTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        posterImage = findViewById(R.id.iv_details_poster);
        titleTextView = findViewById(R.id.tv_title);
        ratingTextView = findViewById(R.id.tv_rating);
        descriptionTextView = findViewById(R.id.tv_description);
        releaseDateTextView = findViewById(R.id.tv_release_date);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_MOVIE_IMAGE_URL)) {
            Picasso.get().load(intent.getStringExtra(EXTRA_MOVIE_IMAGE_URL)).into(posterImage);
        }

        if (intent.hasExtra(EXTRA_MOVIE_TITLE)) {
            titleTextView.setText(intent.getStringExtra(EXTRA_MOVIE_TITLE));
        }

        if (intent.hasExtra(EXTRA_MOVIE_RATING)) {
            ratingTextView.setText(String.valueOf(intent.getDoubleExtra(EXTRA_MOVIE_RATING, 0.0)));
        }

        if (intent.hasExtra(EXTRA_MOVIE_DESCRIPTION)) {
            descriptionTextView.setText(intent.getStringExtra(EXTRA_MOVIE_DESCRIPTION));
        }

        if (intent.hasExtra(EXTRA_MOVIE_RELEASE_DATE)) {
            releaseDateTextView.setText(intent.getStringExtra(EXTRA_MOVIE_RELEASE_DATE));
        }
    }
}
