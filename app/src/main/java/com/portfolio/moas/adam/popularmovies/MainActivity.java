package com.portfolio.moas.adam.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieRecyclerViewAdapter.ItemClickListener {

    MovieRecyclerViewAdapter movieRecyclerViewAdapter;
    private final int NUMBER_OF_COLUMNS = 2;
    private int movieCount;
    private static final String temporaryMockAPIResponse = "[{\"vote_count\":5835,\"id\":346364,\"video\":false,\"vote_average\":7.1,\"title\":\"It\",\"popularity\":723.926632,\"poster_path\":\"\\/9E2y5Q7WlCVNEhP5GiVTjhEhx1o.jpg\",\"original_language\":\"en\",\"original_title\":\"It\",\"genre_ids\":[18,27,53],\"backdrop_path\":\"\\/tcheoA2nPATCm2vvXw2hVQoaEFD.jpg\",\"adult\":false,\"overview\":\"In a small town in Maine, seven children known as The Losers Club come face to face with life problems, bullies and a monster that takes the shape of a clown called Pennywise.\",\"release_date\":\"2017-09-05\"},{\"vote_count\":1495,\"id\":353486,\"video\":false,\"vote_average\":6.4,\"title\":\"Jumanji: Welcome to the Jungle\",\"popularity\":681.838101,\"poster_path\":\"\\/bXrZ5iHBEjH7WMidbUDQ0U2xbmr.jpg\",\"original_language\":\"en\",\"original_title\":\"Jumanji: Welcome to the Jungle\",\"genre_ids\":[28,12,35,10751],\"backdrop_path\":\"\\/rz3TAyd5kmiJmozp3GUbYeB5Kep.jpg\",\"adult\":false,\"overview\":\"The tables are turned as four teenagers are sucked into Jumanji's world - pitted against rhinos, black mambas and an endless variety of jungle traps and puzzles. To survive, they'll play as characters from the game.\",\"release_date\":\"2017-12-08\"},{\"vote_count\":5301,\"id\":211672,\"video\":false,\"vote_average\":6.4,\"title\":\"Minions\",\"popularity\":237.560284,\"poster_path\":\"\\/q0R4crx2SehcEEQEkYObktdeFy.jpg\",\"original_language\":\"en\",\"original_title\":\"Minions\",\"genre_ids\":[10751,16,12,35],\"backdrop_path\":\"\\/qLmdjn2fv0FV2Mh4NBzMArdA0Uu.jpg\",\"adult\":false,\"overview\":\"Minions Stuart, Kevin and Bob are recruited by Scarlet Overkill, a super-villain who, alongside her inventor husband Herb, hatches a plot to take over the world.\",\"release_date\":\"2015-06-17\"},{\"vote_count\":3170,\"id\":181808,\"video\":false,\"vote_average\":7.2,\"title\":\"Star Wars: The Last Jedi\",\"popularity\":511.361559,\"poster_path\":\"\\/xGWVjewoXnJhvxKW619cMzppJDQ.jpg\",\"original_language\":\"en\",\"original_title\":\"Star Wars: The Last Jedi\",\"genre_ids\":[28,12,14,878],\"backdrop_path\":\"\\/5Iw7zQTHVRBOYpA0V6z0yypOPZh.jpg\",\"adult\":false,\"overview\":\"Rey develops her newly discovered abilities with the guidance of Luke Skywalker, who is unsettled by the strength of her powers. Meanwhile, the Resistance prepares to do battle with the First Order.\",\"release_date\":\"2017-12-13\"}]";

    private ProgressBar progressBar;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.pb_loading);
        setUpRecyclerView();

        loadMovieData();
    }

    private void setUpRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.rv_movie_thumbnails);
        recyclerView.setLayoutManager(new GridLayoutManager(this, NUMBER_OF_COLUMNS));
        recyclerView.setHasFixedSize(true);
    }

    private void setUpAdapter(String response) {
        movieRecyclerViewAdapter = new MovieRecyclerViewAdapter(this, movieCount, response);
        movieRecyclerViewAdapter.setClickListener(this);
        recyclerView.setAdapter(movieRecyclerViewAdapter);
    }

    private void renderMovies(String response) {
        movieCount = MovieJSONUtils.getMovieCount(temporaryMockAPIResponse); // TODO temporary until real response is fixed then swap for response parameter
        setUpAdapter(response);
        System.out.println("Response == " + response);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "Clicked on: " + position + " position", Toast.LENGTH_SHORT).show();
    }

    private void loadMovieData() {
        new FetchMoviePosterTask().execute();
    }

    public class FetchMoviePosterTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            showLoadingIndicator();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {

            URL movieDBURL = NetworkUtils.buildUrl(getString(R.string.TMDB_API_KEY));
            Log.d("MovieDBURL", movieDBURL.toString());

            try {
                return NetworkUtils
                        .getResponseFromHttpUrl(movieDBURL);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String jsonResponse) {
            hideLoadingIndicator();
            renderMovies(temporaryMockAPIResponse); // TODO switch temp for jsonResponse once fixed
            super.onPostExecute(jsonResponse);
        }
    }

    private void showLoadingIndicator() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoadingIndicator() {
        progressBar.setVisibility(View.INVISIBLE);
    }
}
