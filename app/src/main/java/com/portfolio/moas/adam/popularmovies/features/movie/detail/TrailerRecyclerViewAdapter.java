package com.portfolio.moas.adam.popularmovies.features.movie.detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.portfolio.moas.adam.popularmovies.R;
import com.portfolio.moas.adam.popularmovies.data.model.Trailer;
import com.portfolio.moas.adam.popularmovies.features.ItemClickListener;

import java.util.List;

/**
 * Created by adam on 23/02/2018.
 */

public class TrailerRecyclerViewAdapter extends RecyclerView.Adapter<TrailerRecyclerViewAdapter.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private ItemClickListener mClickListener;
    private Context mContext;
    private int mNumItems;
    private List<Trailer> mTrailers;

    TrailerRecyclerViewAdapter(Context context, int numItems, List<Trailer> trailers) {
        mNumItems = numItems;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mTrailers = trailers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.trailer_cell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerRecyclerViewAdapter.ViewHolder holder, int position) {
        String trailerName = mTrailers.get(position).getName();
        holder.textView.setText(trailerName);
    }

    @Override
    public int getItemCount() {
        return mNumItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.trailer_name_test);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }


}
