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
    private String posterPath;

    MovieRecyclerViewAdapter(Context context, int numItems) {
        mNumItems = numItems;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.grid_cell, parent, false);
        return new ViewHolder(view);
    }

//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        if (mContext != null) {
//            Picasso.with(mContext)
//                    .load("http://image.tmdb.org/t/p/w780/cGOPbv9wA5gEejkUN892JrveARt.jpg")
////                    .resizeDimen(R.dimen.poster_width, R.dimen.poster_height)
////                    .centerCrop()
//                    .into(holder.imageView);
//        } else {
//            Log.d("Null", "Null");
//        }
//    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mContext != null) {
            Picasso.with(mContext)
                    .load("http://image.tmdb.org/t/p/w780" + gsonTest()[position].poster_path)
//                    .resizeDimen(R.dimen.poster_width, R.dimen.poster_height)
//                    .centerCrop()
                    .into(holder.imageView);
        } else {
            Log.d("Null", "Null");
        }
    }

    private Movie[] gsonTest() {
        Gson gson = new Gson();

//        String testResponse = "[{\"vote_count\":5835,\"id\":346364,\"video\":false,\"vote_average\":7.1,\"title\":\"It\",\"popularity\":723.926632,\"poster_path\":\"\\/9E2y5Q7WlCVNEhP5GiVTjhEhx1o.jpg\",\"original_language\":\"en\",\"original_title\":\"It\",\"genre_ids\":[18,27,53],\"backdrop_path\":\"\\/tcheoA2nPATCm2vvXw2hVQoaEFD.jpg\",\"adult\":false,\"overview\":\"In a small town in Maine, seven children known as The Losers Club come face to face with life problems, bullies and a monster that takes the shape of a clown called Pennywise.\",\"release_date\":\"2017-09-05\"},{\"vote_count\":1495,\"id\":353486,\"video\":false,\"vote_average\":6.4,\"title\":\"Jumanji: Welcome to the Jungle\",\"popularity\":681.838101,\"poster_path\":\"\\/bXrZ5iHBEjH7WMidbUDQ0U2xbmr.jpg\",\"original_language\":\"en\",\"original_title\":\"Jumanji: Welcome to the Jungle\",\"genre_ids\":[28,12,35,10751],\"backdrop_path\":\"\\/rz3TAyd5kmiJmozp3GUbYeB5Kep.jpg\",\"adult\":false,\"overview\":\"The tables are turned as four teenagers are sucked into Jumanji's world - pitted against rhinos, black mambas and an endless variety of jungle traps and puzzles. To survive, they'll play as characters from the game.\",\"release_date\":\"2017-12-08\"},{\"vote_count\":5301,\"id\":211672,\"video\":false,\"vote_average\":6.4,\"title\":\"Minions\",\"popularity\":237.560284,\"poster_path\":\"\\/q0R4crx2SehcEEQEkYObktdeFy.jpg\",\"original_language\":\"en\",\"original_title\":\"Minions\",\"genre_ids\":[10751,16,12,35],\"backdrop_path\":\"\\/qLmdjn2fv0FV2Mh4NBzMArdA0Uu.jpg\",\"adult\":false,\"overview\":\"Minions Stuart, Kevin and Bob are recruited by Scarlet Overkill, a super-villain who, alongside her inventor husband Herb, hatches a plot to take over the world.\",\"release_date\":\"2015-06-17\"}]";
        String testResponse = "[{\"vote_count\":5835,\"id\":346364,\"video\":false,\"vote_average\":7.1,\"title\":\"It\",\"popularity\":723.926632,\"poster_path\":\"\\/9E2y5Q7WlCVNEhP5GiVTjhEhx1o.jpg\",\"original_language\":\"en\",\"original_title\":\"It\",\"genre_ids\":[18,27,53],\"backdrop_path\":\"\\/tcheoA2nPATCm2vvXw2hVQoaEFD.jpg\",\"adult\":false,\"overview\":\"In a small town in Maine, seven children known as The Losers Club come face to face with life problems, bullies and a monster that takes the shape of a clown called Pennywise.\",\"release_date\":\"2017-09-05\"},{\"vote_count\":1495,\"id\":353486,\"video\":false,\"vote_average\":6.4,\"title\":\"Jumanji: Welcome to the Jungle\",\"popularity\":681.838101,\"poster_path\":\"\\/bXrZ5iHBEjH7WMidbUDQ0U2xbmr.jpg\",\"original_language\":\"en\",\"original_title\":\"Jumanji: Welcome to the Jungle\",\"genre_ids\":[28,12,35,10751],\"backdrop_path\":\"\\/rz3TAyd5kmiJmozp3GUbYeB5Kep.jpg\",\"adult\":false,\"overview\":\"The tables are turned as four teenagers are sucked into Jumanji's world - pitted against rhinos, black mambas and an endless variety of jungle traps and puzzles. To survive, they'll play as characters from the game.\",\"release_date\":\"2017-12-08\"},{\"vote_count\":5301,\"id\":211672,\"video\":false,\"vote_average\":6.4,\"title\":\"Minions\",\"popularity\":237.560284,\"poster_path\":\"\\/q0R4crx2SehcEEQEkYObktdeFy.jpg\",\"original_language\":\"en\",\"original_title\":\"Minions\",\"genre_ids\":[10751,16,12,35],\"backdrop_path\":\"\\/qLmdjn2fv0FV2Mh4NBzMArdA0Uu.jpg\",\"adult\":false,\"overview\":\"Minions Stuart, Kevin and Bob are recruited by Scarlet Overkill, a super-villain who, alongside her inventor husband Herb, hatches a plot to take over the world.\",\"release_date\":\"2015-06-17\"},{\"vote_count\":3170,\"id\":181808,\"video\":false,\"vote_average\":7.2,\"title\":\"Star Wars: The Last Jedi\",\"popularity\":511.361559,\"poster_path\":\"\\/xGWVjewoXnJhvxKW619cMzppJDQ.jpg\",\"original_language\":\"en\",\"original_title\":\"Star Wars: The Last Jedi\",\"genre_ids\":[28,12,14,878],\"backdrop_path\":\"\\/5Iw7zQTHVRBOYpA0V6z0yypOPZh.jpg\",\"adult\":false,\"overview\":\"Rey develops her newly discovered abilities with the guidance of Luke Skywalker, who is unsettled by the strength of her powers. Meanwhile, the Resistance prepares to do battle with the First Order.\",\"release_date\":\"2017-12-13\"}]";

        Movie[] movie1 = gson.fromJson(testResponse, Movie[].class);
//        System.out.println("111 First id: " + movie1[0].title);
//        System.out.println("111 Second id: " + movie1[1].title);
//        System.out.println("111 First poster path: " + movie1[0].poster_path);
//
//        System.out.println("Test==" + gson.toJson(movie1));

//        String posterPath = movie1[0].poster_path;
        return movie1;
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
