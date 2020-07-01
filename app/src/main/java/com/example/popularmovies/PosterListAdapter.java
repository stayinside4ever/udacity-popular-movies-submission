package com.example.popularmovies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.database.MovieEntity;
import com.example.popularmovies.databinding.ListItemMoviePosterBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PosterListAdapter extends RecyclerView.Adapter<PosterListAdapter.PosterViewHolder> {
    private List<MovieEntity> data;
    private ItemClickListener onClickListener;

    public PosterListAdapter(List<MovieEntity> data, ItemClickListener onClickListener) {
        this.data = data;
        this.onClickListener = onClickListener;
    }


    interface ItemClickListener {
        void onItemClick(MovieEntity movie);
    }

    @NonNull
    @Override
    public PosterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        ListItemMoviePosterBinding posterBinding =
                ListItemMoviePosterBinding.inflate(layoutInflater, parent, false);
        return new PosterViewHolder(posterBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PosterViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void update(List<MovieEntity> newData) {
        data = newData;
        notifyDataSetChanged();
    }

    class PosterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ListItemMoviePosterBinding posterBinding;

        public PosterViewHolder(ListItemMoviePosterBinding posterBinding) {
            super(posterBinding.getRoot());
            this.posterBinding = posterBinding;
            posterBinding.getRoot().setOnClickListener(this);
        }

        void bind (MovieEntity movie) {
            Picasso.get()
                    .load(movie.getImageUrl())
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .error(R.drawable.ic_baseline_broken_image_24)
                    .into(posterBinding.ivListPoster);
        }

        @Override
        public void onClick(View v) {
            onClickListener.onItemClick(data.get(getAdapterPosition()));
        }
    }
}

