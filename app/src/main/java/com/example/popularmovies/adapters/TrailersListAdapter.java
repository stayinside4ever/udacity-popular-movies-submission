package com.example.popularmovies.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.databinding.ListItemTrailerBinding;
import com.example.popularmovies.network.models.TrailersResponse;

import java.util.List;

public class TrailersListAdapter extends RecyclerView.Adapter<TrailersListAdapter.TrailersViewHolder> {
    private List<TrailersResponse> data;

    public TrailersListAdapter(List<TrailersResponse> data) {
        this.data = data;
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
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void update(List<TrailersResponse> newData) {
        data = newData;
        notifyDataSetChanged();
    }

    class TrailersViewHolder extends RecyclerView.ViewHolder {
        private final ListItemTrailerBinding trailerBinding;
        private TrailersResponse trailer;

        public TrailersViewHolder(ListItemTrailerBinding trailerBinding) {
            super(trailerBinding.getRoot());
            this.trailerBinding = trailerBinding;
        }

        void bind(TrailersResponse trailer) {
            this.trailer = trailer;
            trailerBinding.tvTrailer.setText(trailer.getName());
        }
    }
}
