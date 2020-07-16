package com.example.popularmovies.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.databinding.ListItemTrailerBinding;
import com.example.popularmovies.network.models.TrailersResponse;

import java.util.List;

public class TrailersListAdapter extends RecyclerView.Adapter<TrailersListAdapter.TrailersViewHolder> {
    private List<TrailersResponse> data;
    private TrailerClickListener trailerClickListener;

    public TrailersListAdapter(List<TrailersResponse> data, TrailerClickListener trailerClickListener) {
        this.data = data;
        this.trailerClickListener = trailerClickListener;
    }

    public interface TrailerClickListener {
        void onClick(TrailersResponse trailer);
    }

    @NonNull
    @Override
    public TrailersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        ListItemTrailerBinding trailerBinding =
                ListItemTrailerBinding.inflate(layoutInflater, parent, false);
        return new TrailersViewHolder(trailerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailersViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void update(List<TrailersResponse> newData) {
        data = newData;
        notifyDataSetChanged();
    }

    class TrailersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ListItemTrailerBinding trailerBinding;

        public TrailersViewHolder(ListItemTrailerBinding trailerBinding) {
            super(trailerBinding.getRoot());
            this.trailerBinding = trailerBinding;
            trailerBinding.getRoot().setOnClickListener(this);
        }

        void bind(int pos) {
            trailerBinding.tvTrailer.setText(data.get(pos).getName());
        }

        @Override
        public void onClick(View v) {
            trailerClickListener.onClick(data.get(getAdapterPosition()));
        }
    }
}
