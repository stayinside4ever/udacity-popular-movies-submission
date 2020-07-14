package com.example.popularmovies.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.popularmovies.R;
import com.example.popularmovies.databinding.ActivityDetailsBinding;
import com.example.popularmovies.network.DetailsNetworkRequestDone;
import com.example.popularmovies.network.NetworkUtils;
import com.example.popularmovies.network.models.ReviewsResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE_TITLE = "EXTRA_MOVIE_TITLE";
    public static final String EXTRA_MOVIE_DESCRIPTION = "EXTRA_MOVIE_DESCRIPTION";
    public static final String EXTRA_MOVIE_RATING = "EXTRA_MOVIE_RATING";
    public static final String EXTRA_MOVIE_IMAGE_URL = "EXTRA_MOVIE_IMAGE_URL";
    public static final String EXTRA_MOVIE_RELEASE_DATE = "EXTRA_MOVIE_RELEASE_DATE";
    public static final String EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID";

    private ActivityDetailsBinding binding;
    NetworkUtils networkUtils;

    Toast requestFailToast;

    private int movieId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.pbDetailsLoading.setVisibility(View.VISIBLE); // TODO: loading stuff
        binding.detailsContainer.setVisibility(View.GONE);

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

        if (intent.hasExtra(EXTRA_MOVIE_ID)) {
            movieId = intent.getIntExtra(EXTRA_MOVIE_ID, -1);
        }

        networkUtils = new NetworkUtils(new DetailsNetworkRequestDone() {
            @Override
            public void onReviewsFetched(List<ReviewsResponse> reviewsResponses) {
                // TODO: load reviews into recyclerview, make another one for trailers
                binding.pbDetailsLoading.setVisibility(View.GONE);
                binding.detailsContainer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onRequestFailed() {
                makeRequestFailedToast();
            }
        });

        networkUtils.getReviewsFromNetwork(movieId);
    }

    private void makeRequestFailedToast() {
        if (requestFailToast != null) {
            requestFailToast.cancel();
        }

        requestFailToast = Toast.makeText(this, R.string.loading_details_failed_message,
                Toast.LENGTH_LONG);
        requestFailToast.show();
    }
}
