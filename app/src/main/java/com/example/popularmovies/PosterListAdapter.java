package com.example.popularmovies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class PosterListAdapter extends RecyclerView.Adapter<PosterViewHolder> {

    @NonNull
    @Override
    public PosterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_movie_poster, parent, false);

        return new PosterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PosterViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return 19; // TODO: implement
    }
}

class PosterViewHolder extends RecyclerView.ViewHolder {
    ImageView posterImageView;

    public PosterViewHolder(@NonNull View itemView) {
        super(itemView);
        posterImageView = (ImageView) itemView.findViewById(R.id.iv_list_poster);
    }

    void bind (int pos) {
        // TODO: replace with actual data
        int image = (pos % 2 == 0) ? R.drawable.dummy1 : R.drawable.dummy2;
        Picasso.get().load(image).into(posterImageView);
    }
}