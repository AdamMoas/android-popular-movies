package com.portfolio.moas.adam.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

/**
 * Created by adam on 15/01/2018.
 */

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private ItemClickListener mClickListener;
    private Context mContext;
    private int mNumItems;
    private String mJSONResponse;

    private static final String MOVIE_POSTER_IMAGE_SIZE = "w780";
    private static final String MOVIE_POSTER_BASE_URL = "http://image.tmdb.org/t/p/" + MOVIE_POSTER_IMAGE_SIZE;

    MovieRecyclerViewAdapter(Context context, int numItems, String jsonResponse) {
        mNumItems = numItems;
        mContext = context;
        mJSONResponse = jsonResponse;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.grid_cell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String posterImagePath = MovieJSONUtils.getPosterPath(mJSONResponse, position);

        if (mContext != null) {
            Picasso.with(mContext)
                    .load(MOVIE_POSTER_BASE_URL + posterImagePath)
                    .into(holder.imageView);
        } else {
            Log.d("Picasso", "Picasso context is null");
        }
    }

    @Override
    public int getItemCount() {
        return mNumItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.cell_image);
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

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
