package com.portfolio.moas.adam.popularmovies.features.main.screen;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.portfolio.moas.adam.popularmovies.R;
import com.portfolio.moas.adam.popularmovies.data.ApiUtils;
import com.portfolio.moas.adam.popularmovies.data.model.Movie;
import com.portfolio.moas.adam.popularmovies.data.model.MoviesResponse;
import com.portfolio.moas.adam.popularmovies.data.remote.MovieDbService;
import com.portfolio.moas.adam.popularmovies.features.movie.detail.MovieDetailActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.portfolio.moas.adam.popularmovies.utils.Constants.SORT_BY_POPULARITY;
import static com.portfolio.moas.adam.popularmovies.utils.Constants.SORT_BY_TOP_RATED;

public class MainActivity extends AppCompatActivity implements MovieRecyclerViewAdapter.ItemClickListener {

    MovieRecyclerViewAdapter movieRecyclerViewAdapter;
    private int movieCount;

    @BindView(R.id.pb_loading)
    ProgressBar progressBar;
    @BindView(R.id.rv_movie_thumbnails)
    RecyclerView recyclerView;

    private MovieDbService movieDbService;
    private ArrayList<Movie> mMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        movieDbService = ApiUtils.getMovieDbService();

        setUpRecyclerView();
        loadMovieData(SORT_BY_POPULARITY);
    }


    private void fetchMovieDetails(String sortBy) {
        String apiKey = getString(R.string.TMDB_API_KEY);
        if (apiKey.isEmpty()) {
            Log.d("API-KEY", "API Key missing");
        }
        showLoadingIndicator();

        Call<MoviesResponse> call = movieDbService.getAllMovies(sortBy, apiKey);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<Movie> movies = response.body().getResults();
                    mMovies = movies;
                    movieCount = movies.size();
                    System.out.println("Title: " + movies.get(0).getTitle());
                    hideLoadingIndicator();
                    displayMovies();
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                hideLoadingIndicator();
                displayError(getString(R.string.api_response_failure_message));
                Log.d("Failure", t.toString());
            }
        });

    }

    private void setUpRecyclerView() {
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        recyclerView.setHasFixedSize(true);
    }

    private void setUpAdapter(int movieCount) {
        movieRecyclerViewAdapter = new MovieRecyclerViewAdapter(this, movieCount, mMovies);
        movieRecyclerViewAdapter.setClickListener(this);
        recyclerView.setAdapter(movieRecyclerViewAdapter);
    }

    private void displayMovies() {
        if (mMovies != null && movieCount != 0) {
            setUpAdapter(movieCount);
        } else {
            displayError(getString(R.string.network_error_message));
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent detailActivityIntent = new Intent(MainActivity.this, MovieDetailActivity.class);
//        detailActivityIntent.putExtras(bundleMovieDataFromJson(position));
        detailActivityIntent.putExtra("dataParcelTest", mMovies);
        detailActivityIntent.putExtra("dataPosition", position);
        startActivity(detailActivityIntent);
    }

    private void loadMovieData(String sortBy) {
        if (isDeviceConnectedToNetwork()) {
//            new FetchMoviePosterTask().execute(sortBy); // TODO test
            fetchMovieDetails(sortBy);
            Log.d("Connection tag", "Device connected to the internet");
        } else {
            Log.d("Connection tag", "Device NOT connected to the internet");
            displayError("Network error. Please reconnect and try again.");
        }
    }

    // Suggested in code review. Referenced from: https://developer.android.com/training/monitoring-device-state/connectivity-monitoring.html
    private boolean isDeviceConnectedToNetwork() {
        boolean isConnected = false;
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return isConnected;
    }

//    public class FetchMoviePosterTask extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected void onPreExecute() {
//            showLoadingIndicator();
//            super.onPreExecute();
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            String sortBy = params[0];
//            URL movieDBURL = NetworkUtils.buildUrl(getString(R.string.TMDB_API_KEY), sortBy);
//            Log.d("MovieDBURL", movieDBURL.toString());
//            try {
//                return NetworkUtils
//                        .getResponseFromHttpUrl(movieDBURL);
//            } catch (Exception e) {
//                e.printStackTrace();
//                return null;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(String jsonResponse) {
//            hideLoadingIndicator();
//            mJsonResponse = jsonResponse;
////            displayMovies(jsonResponse);
//            Log.d("RetrofitTest", jsonResponse);
//            super.onPostExecute(jsonResponse);
//        }
//    }

    private void showLoadingIndicator() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoadingIndicator() {
        progressBar.setVisibility(View.INVISIBLE);
    }

//    private Bundle bundleMovieDataFromJson(int position) {
//        Bundle movieBundle = new Bundle();
////        movieBundle.putString(getString(R.string.title_key), MovieJSONUtils.getMovieTitle(mJsonResponse, position));
////        movieBundle.putString(getString(R.string.overview_key), MovieJSONUtils.getMovieOverview(mJsonResponse, position));
////        movieBundle.putString(getString(R.string.release_date_key), MovieJSONUtils.getMovieOReleaseDate(mJsonResponse, position));
////        movieBundle.putDouble(getString(R.string.vote_average_key), MovieJSONUtils.getMovieVoteAverage(mJsonResponse, position));
////        movieBundle.putString(getString(R.string.poster_path_key), MovieJSONUtils.getMoviePosterPath(mJsonResponse, position));
////        movieBundle.putInt(getString(R.string.movie_trailer_key), MovieJSONUtils.getMovieId(mJsonResponse, position));
//
////        movieBundle.putString(getString(R.string.title_key), mMovies.get(position).getTitle());
//
//        return movieBundle;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

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
