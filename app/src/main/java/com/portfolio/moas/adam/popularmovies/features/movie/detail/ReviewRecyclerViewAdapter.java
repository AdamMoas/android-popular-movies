package com.portfolio.moas.adam.popularmovies.features.movie.detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.portfolio.moas.adam.popularmovies.R;
import com.portfolio.moas.adam.popularmovies.data.model.Review;

import java.util.List;

/**
 * Created by adam on 05/03/2018.
 */

public class ReviewRecyclerViewAdapter extends RecyclerView.Adapter<ReviewRecyclerViewAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private Context context;
    private int numItems;
    private List<Review> reviews;

    ReviewRecyclerViewAdapter(Context context, int numItems, List<Review> reviews) {
        this.numItems = numItems;
        this.context = context;
        layoutInflater = LayoutInflater.from(this.context);
        this.reviews = reviews;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.review_cell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String reviewContent = reviews.get(position).getContent();
        System.out.println("Content:   " + reviewContent);
        holder.textView.setText(reviewContent);
    }

    @Override
    public int getItemCount() {
        return numItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.review_content);
        }

    }
}

