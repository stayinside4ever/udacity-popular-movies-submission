package com.example.popularmovies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.database.MovieEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PosterListAdapter extends RecyclerView.Adapter<PosterViewHolder> {

    List<MovieEntity> data;

    public PosterListAdapter(List<MovieEntity> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public PosterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_movie_poster, parent, false);

        return new PosterViewHolder(view);
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
}

class PosterViewHolder extends RecyclerView.ViewHolder {
    ImageView posterImageView;

    public PosterViewHolder(@NonNull View itemView) {
        super(itemView);
        posterImageView = (ImageView) itemView.findViewById(R.id.iv_list_poster);
    }

    void bind (MovieEntity movie) {
        Picasso.get().load(movie.getImageUrl()).into(posterImageView);
    }
}