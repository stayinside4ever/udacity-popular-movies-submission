package com.example.popularmovies.adapters;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.databinding.ListItemReviewBinding;
import com.example.popularmovies.network.models.ReviewsResponse;

import java.util.List;

public class ReviewsListAdapter extends RecyclerView.Adapter<ReviewsListAdapter.ReviewsViewHolder> {
    private List<ReviewsResponse> data;

    public ReviewsListAdapter(List<ReviewsResponse> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        ListItemReviewBinding reviewBinding =
                ListItemReviewBinding.inflate(layoutInflater, parent, false);
        return new ReviewsViewHolder(reviewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() { return data.size(); }

    public void update(List<ReviewsResponse> newData) {
        data = newData;
        notifyDataSetChanged();
    }

    class ReviewsViewHolder extends RecyclerView.ViewHolder {
        private final ListItemReviewBinding reviewBinding;

        public ReviewsViewHolder(ListItemReviewBinding reviewBinding) {
            super(reviewBinding.getRoot());
            this.reviewBinding = reviewBinding;
        }

        void bind (ReviewsResponse review) {
            reviewBinding.tvReviewAuthor.setText(review.getAuthor());
            reviewBinding.tvReviewContent.setText(review.getContent());
        }
    }
}
