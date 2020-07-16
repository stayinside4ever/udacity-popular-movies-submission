package com.example.popularmovies.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.popularmovies.AppExecutors;
import com.example.popularmovies.R;
import com.example.popularmovies.adapters.ReviewsListAdapter;
import com.example.popularmovies.adapters.TrailersListAdapter;
import com.example.popularmovies.database.AppDatabase;
import com.example.popularmovies.database.models.MovieEntity;
import com.example.popularmovies.databinding.ActivityDetailsBinding;
import com.example.popularmovies.network.NetworkUtils;
import com.example.popularmovies.network.callbacks.DetailsNetworkRequestDone;
import com.example.popularmovies.network.models.ReviewsResponse;
import com.example.popularmovies.network.models.TrailersResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.popularmovies.ui.DetailsActivity.ButtonState.ABLE_UNFAVOURITE;
import static com.example.popularmovies.ui.DetailsActivity.ButtonState.DETERMINE;

public class DetailsActivity extends AppCompatActivity implements TrailersListAdapter.TrailerClickListener {
    enum ButtonState { ABLE_FAVOURITE, ABLE_UNFAVOURITE, DETERMINE }

    public static final String EXTRA_MOVIE = "EXTRA_MOVIE";

    private ActivityDetailsBinding binding;
    private NetworkUtils networkUtils;
    private AppDatabase database;

    Toast requestFailToast;

    private MovieEntity movie;
    private List<ReviewsResponse> reviews;
    private List<TrailersResponse> trailers;
    private boolean reviewsLoaded = false;
    private boolean trailersLoaded = false;
    private boolean buttonSet = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = AppDatabase.getInstance(this);

        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.pbDetailsLoading.setVisibility(View.VISIBLE);
        binding.detailsContainer.setVisibility(View.GONE);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_MOVIE)) {
            movie = (MovieEntity) intent.getSerializableExtra(EXTRA_MOVIE);

            if (movie != null) {
                Picasso.get()
                        .load(movie.getImageUrl())
                        .placeholder(R.drawable.ic_baseline_image_24)
                        .error(R.drawable.ic_baseline_broken_image_24)
                        .into(binding.ivDetailsPoster);

                binding.tvTitle.setText(movie.getTitle());
                binding.tvRating.setText(String.valueOf(movie.getRating()));
                binding.tvDescription.setText(movie.getDescription());
                binding.tvReleaseDate.setText(movie.getReleaseDate());
            }
        } // TODO: Placeholder if movie doesnt exist

        networkUtils = new NetworkUtils(new DetailsNetworkRequestDone() {
            @Override
            public void onReviewsFetched(List<ReviewsResponse> reviewsResponses) {
                if (reviewsResponses != null) {
                    reviews = reviewsResponses;
                    binding.rvReviews.setLayoutManager(new LinearLayoutManager(getParent()));
                    binding.rvReviews.setAdapter(new ReviewsListAdapter(reviews));
                } else {
                    binding.llReviewsContainer.setVisibility(View.GONE);
                }

                reviewsLoaded = true;
                checkForDoneLoading();
            }

            @Override
            public void onTrailersFetched(List<TrailersResponse> trailersResponses) {
                if (trailersResponses != null) {
                    trailers = trailersResponses;
                    setupTrailersAdapter();
                } else {
                    binding.llTrailersContainer.setVisibility(View.GONE);
                }

                trailersLoaded = true;
                checkForDoneLoading();
            }

            @Override
            public void onRequestFailed() {
                makeRequestFailedToast();
            }
        });

        binding.btnFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFavourites();
            }
        });

        networkUtils.getReviewsFromNetwork(movie.getMovieId());
        networkUtils.getTrailersFromNetwork(movie.getMovieId());
        setButtonState(DETERMINE);
    }

    private void makeRequestFailedToast() {
        if (requestFailToast != null) {
            requestFailToast.cancel();
        }

        requestFailToast = Toast.makeText(this, R.string.loading_details_failed_message,
                Toast.LENGTH_LONG);
        requestFailToast.show();
    }

    private void checkForDoneLoading() {
        if (reviewsLoaded && trailersLoaded && buttonSet) {
            binding.pbDetailsLoading.setVisibility(View.GONE);
            binding.detailsContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(TrailersResponse trailer) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + trailer.getKey()));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    // cannot get TrailerClickListener as this from within DetailsNetworkRequestDone
    private void setupTrailersAdapter() {
        binding.rvTrailers.setLayoutManager(new LinearLayoutManager(this));
        binding.rvTrailers.setAdapter(new TrailersListAdapter(trailers, this));
    }

    private void toggleFavourites() {

        AppExecutors.getInstance().diskIo().execute(new Runnable() {
            @Override
            public void run() {
                MovieEntity dbMovie = database.moviesDao().loadMovieByMovieId(movie.getMovieId());
                if (dbMovie == null) {
                    dbMovie = new MovieEntity();
                    dbMovie.setTitle(movie.getTitle());
                    dbMovie.setMovieId(movie.getMovieId());
                    dbMovie.setDescription(movie.getDescription());
                    dbMovie.setImageUrl(movie.getImageUrl());
                    dbMovie.setRating(movie.getRating());
                    dbMovie.setReleaseDate(movie.getReleaseDate());
                    dbMovie.setReviews(reviews);
                    dbMovie.setTrailers(trailers);

                    Log.d(DetailsActivity.class.getSimpleName(), "Inserting new movie into DB");
                    database.moviesDao().insertMovie(dbMovie);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setButtonState(ABLE_UNFAVOURITE);
                        }
                    });
                } else {
                    Log.d(DetailsActivity.class.getSimpleName(), "Deleting from DB");
                    database.moviesDao().deleteMovieByMovieId(dbMovie.getMovieId());
                    runOnUiThread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    setButtonState(DETERMINE); // TODO: for some reason, ABLE_FAVOURITE does not update the UI???
                                }
                            }
                    );
                }
            }
        });

    }

    private void setButtonState(ButtonState state) {
        switch (state) {
            case ABLE_FAVOURITE:
                binding.btnFavourite.setText(R.string.add_to_favourites);
            case ABLE_UNFAVOURITE:
                binding.btnFavourite.setText(R.string.remove_from_favourites);
                break;
            default:
                AppExecutors.getInstance().diskIo().execute(new Runnable() {
                    @Override
                    public void run() {
                        MovieEntity dbMovie = database.moviesDao().loadMovieByMovieId(movie.getMovieId());
                        if (dbMovie == null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    binding.btnFavourite.setText(R.string.add_to_favourites);
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    binding.btnFavourite.setText(R.string.remove_from_favourites);
                                }
                            });
                        }
                    }
                });
        }

        buttonSet = true;
    }
}
