package com.portfolio.moas.adam.popularmovies;

import android.content.Intent;
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

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private Intent detailActivityIntent;
    private String mJsonResponse;

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

    private void displayMovies(String response) {
        movieCount = MovieJSONUtils.getMovieCount(response);
        setUpAdapter(response);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "Clicked on: " + position + " position", Toast.LENGTH_SHORT).show();
        detailActivityIntent = new Intent(MainActivity.this, MovieDetailActivity.class);
        detailActivityIntent.putExtras(bundleMovieDataFromJson(position));
        startActivity(detailActivityIntent);
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
            mJsonResponse = jsonResponse;
            displayMovies(jsonResponse);
            super.onPostExecute(jsonResponse);
        }
    }

    private void showLoadingIndicator() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoadingIndicator() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    private Bundle bundleMovieDataFromJson(int position) {
        Bundle movieBundle = new Bundle();
        movieBundle.putString("title", MovieJSONUtils.getMovieTitle(mJsonResponse, position));
        movieBundle.putString("overview", MovieJSONUtils.getMovieOverview(mJsonResponse, position));
        movieBundle.putString("releaseDate", MovieJSONUtils.getMovieOReleaseDate(mJsonResponse, position));
        movieBundle.putDouble("voteAverage", MovieJSONUtils.getMovieVoteAverage(mJsonResponse, position));
        movieBundle.putString("posterPath", MovieJSONUtils.getMoviePosterPath(mJsonResponse, position));

        return movieBundle;
    }
}
