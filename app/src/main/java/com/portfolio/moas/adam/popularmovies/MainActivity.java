package com.portfolio.moas.adam.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieRecyclerViewAdapter.ItemClickListener {

    MovieRecyclerViewAdapter movieRecyclerViewAdapter;
    private final int NUMBER_OF_COLUMNS = 2;
    private String jsonResponse;
    private int numberofMovieItems = 0; //Todo needs refactoring into own method to return number of movies

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gsonTest();

        setUpRecyclerView();

        loadMovieData();

    }

    private void setUpRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_movie_thumbnails);
        recyclerView.setLayoutManager(new GridLayoutManager(this, NUMBER_OF_COLUMNS));
        movieRecyclerViewAdapter = new MovieRecyclerViewAdapter(this, numberofMovieItems);
        movieRecyclerViewAdapter.setClickListener(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(movieRecyclerViewAdapter);
    }

    private String gsonTest() {
        Gson gson = new Gson();

        String testResponse = "[{\"vote_count\":5835,\"id\":346364,\"video\":false,\"vote_average\":7.1,\"title\":\"It\",\"popularity\":723.926632,\"poster_path\":\"\\/9E2y5Q7WlCVNEhP5GiVTjhEhx1o.jpg\",\"original_language\":\"en\",\"original_title\":\"It\",\"genre_ids\":[18,27,53],\"backdrop_path\":\"\\/tcheoA2nPATCm2vvXw2hVQoaEFD.jpg\",\"adult\":false,\"overview\":\"In a small town in Maine, seven children known as The Losers Club come face to face with life problems, bullies and a monster that takes the shape of a clown called Pennywise.\",\"release_date\":\"2017-09-05\"},{\"vote_count\":1495,\"id\":353486,\"video\":false,\"vote_average\":6.4,\"title\":\"Jumanji: Welcome to the Jungle\",\"popularity\":681.838101,\"poster_path\":\"\\/bXrZ5iHBEjH7WMidbUDQ0U2xbmr.jpg\",\"original_language\":\"en\",\"original_title\":\"Jumanji: Welcome to the Jungle\",\"genre_ids\":[28,12,35,10751],\"backdrop_path\":\"\\/rz3TAyd5kmiJmozp3GUbYeB5Kep.jpg\",\"adult\":false,\"overview\":\"The tables are turned as four teenagers are sucked into Jumanji's world - pitted against rhinos, black mambas and an endless variety of jungle traps and puzzles. To survive, they'll play as characters from the game.\",\"release_date\":\"2017-12-08\"},{\"vote_count\":5301,\"id\":211672,\"video\":false,\"vote_average\":6.4,\"title\":\"Minions\",\"popularity\":237.560284,\"poster_path\":\"\\/q0R4crx2SehcEEQEkYObktdeFy.jpg\",\"original_language\":\"en\",\"original_title\":\"Minions\",\"genre_ids\":[10751,16,12,35],\"backdrop_path\":\"\\/qLmdjn2fv0FV2Mh4NBzMArdA0Uu.jpg\",\"adult\":false,\"overview\":\"Minions Stuart, Kevin and Bob are recruited by Scarlet Overkill, a super-villain who, alongside her inventor husband Herb, hatches a plot to take over the world.\",\"release_date\":\"2015-06-17\"},{\"vote_count\":3170,\"id\":181808,\"video\":false,\"vote_average\":7.2,\"title\":\"Star Wars: The Last Jedi\",\"popularity\":511.361559,\"poster_path\":\"\\/xGWVjewoXnJhvxKW619cMzppJDQ.jpg\",\"original_language\":\"en\",\"original_title\":\"Star Wars: The Last Jedi\",\"genre_ids\":[28,12,14,878],\"backdrop_path\":\"\\/5Iw7zQTHVRBOYpA0V6z0yypOPZh.jpg\",\"adult\":false,\"overview\":\"Rey develops her newly discovered abilities with the guidance of Luke Skywalker, who is unsettled by the strength of her powers. Meanwhile, the Resistance prepares to do battle with the First Order.\",\"release_date\":\"2017-12-13\"}]";

        Movie[] movie1 = gson.fromJson(testResponse, Movie[].class);
//        System.out.println("111 First id: " + movie1[0].title);
//        System.out.println("111 Second id: " + movie1[1].title);
//        System.out.println("111 First poster path: " + movie1[0].poster_path);
//
//        System.out.println("Test==" + gson.toJson(movie1));

        String posterPath = movie1[0].poster_path;
        numberofMovieItems = movie1.length;
        return posterPath;
    }


    private void loadMovieData() {
        new FetchMoviePosterTask().execute();
    }


    private void displayPicassoImage(String posterPath) {
//        Picasso.with(this).load("http://i.imgur.com/DvpvklR.png").into(imageView);
//        Picasso.with(this).load("http://image.tmdb.org/t/p/w500/2SEgJ0mHJ7TSdVDbkGU061tR33K.jpg").into(imageView);
//        Picasso.with(this).load("http://image.tmdb.org/t/p/w500" + posterPath).into(imageView);
    }


    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "Clicked on: " + position + " position", Toast.LENGTH_SHORT).show();
    }


    public class FetchMoviePosterTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {

            URL movieDBURL = NetworkUtils.buildUrl(getString(R.string.TMDB_API_KEY));
            Log.d("MovieDBURL", movieDBURL.toString());

            try {
                String jsonMovieDBResponse = NetworkUtils
                        .getResponseFromHttpUrl(movieDBURL);

                return jsonMovieDBResponse;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            System.out.println("responseOutput: " + s);
            jsonResponse = s;
            super.onPostExecute(s);
        }
    }


}
