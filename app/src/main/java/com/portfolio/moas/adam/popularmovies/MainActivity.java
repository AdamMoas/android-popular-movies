package com.portfolio.moas.adam.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieRecyclerViewAdapter.ItemClickListener {

    MovieRecyclerViewAdapter movieRecyclerViewAdapter;
    private int movieCount;

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private String mJsonResponse;
//    boolean isSortedByPopularity = true;

    public static final String SORT_BY_POPULARITY = "popularity";
    public static final String SORT_BY_TOP_RATED = "topRated";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.pb_loading);
        setUpRecyclerView();

        loadMovieData(SORT_BY_POPULARITY);
    }

    private void setUpRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.rv_movie_thumbnails);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        recyclerView.setHasFixedSize(true);
    }

    private void setUpAdapter(String response) {
        movieRecyclerViewAdapter = new MovieRecyclerViewAdapter(this, movieCount, response);
        movieRecyclerViewAdapter.setClickListener(this);
        recyclerView.setAdapter(movieRecyclerViewAdapter);
    }

    private void displayMovies(String response) {
        if (response != null) {
            movieCount = MovieJSONUtils.getMovieCount(response);
            setUpAdapter(response);
        } else {
            displayError(getString(R.string.network_error_message));
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent detailActivityIntent = new Intent(MainActivity.this, MovieDetailActivity.class);
        detailActivityIntent.putExtras(bundleMovieDataFromJson(position));
        startActivity(detailActivityIntent);
    }

    private void loadMovieData(String sortBy) {
        new FetchMoviePosterTask().execute(sortBy);
    }

    public class FetchMoviePosterTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            showLoadingIndicator();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String sortBy = params[0];
            URL movieDBURL = NetworkUtils.buildUrl(getString(R.string.TMDB_API_KEY), sortBy);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.settings_action) {
//            if (isSortedByPopularity) {
//                item.setTitle(R.string.popular_action);
//                loadMovieData(SORT_BY_POPULARITY);
//            } else {
//                item.setTitle(R.string.top_rated_action);
//                loadMovieData(SORT_BY_TOP_RATED);
//            }
//            isSortedByPopularity = !isSortedByPopularity;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.sort_popular_action:
                loadMovieData(SORT_BY_POPULARITY);
                break;
            case R.id.sort_top_rated_action:
                loadMovieData(SORT_BY_TOP_RATED);
                break;
            default:
                loadMovieData(SORT_BY_TOP_RATED);
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayError(String errorMessage) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.main_layout), errorMessage, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(getResources().getColor(R.color.errorRed));
        snackbar.show();
    }
}
