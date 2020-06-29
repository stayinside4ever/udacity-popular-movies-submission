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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_movie_poster,
                                                                parent, false);

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

    class PosterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView posterImageView;

        public PosterViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.iv_list_poster);
            itemView.setOnClickListener(this);
        }

        void bind (MovieEntity movie) {
            Picasso.get().load(movie.getImageUrl()).into(posterImageView);
        }

        @Override
        public void onClick(View v) {
            onClickListener.onItemClick(data.get(getAdapterPosition()));
        }
    }
}

