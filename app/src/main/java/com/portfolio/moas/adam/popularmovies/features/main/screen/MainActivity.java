package com.portfolio.moas.adam.popularmovies.features.main.screen;

import android.content.Intent;
import android.os.Bundle;
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
import com.portfolio.moas.adam.popularmovies.data.model.MovieResponse;
import com.portfolio.moas.adam.popularmovies.data.remote.MovieDbService;
import com.portfolio.moas.adam.popularmovies.features.movie.detail.MovieDetailActivity;
import com.portfolio.moas.adam.popularmovies.utils.Constants;
import com.portfolio.moas.adam.popularmovies.utils.ErrorHelper;
import com.portfolio.moas.adam.popularmovies.utils.NetworkUtils;

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
            return;
        }
        showLoadingIndicator();

        Call<MovieResponse> call = movieDbService.getAllMovies(sortBy, apiKey);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<Movie> movies = response.body().getResults();
                    mMovies = movies;
                    movieCount = movies.size();
                    for (int i = 0; i < movies.size(); i++) {
                        System.out.println("Title: " + movies.get(i).getOverview());
                    }
                    hideLoadingIndicator();
                    displayMovies();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                hideLoadingIndicator();
                ErrorHelper.displayError(MainActivity.this,
                        findViewById(R.id.main_layout),
                        getString(R.string.api_response_failure_message));
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
            ErrorHelper.displayError(MainActivity.this,
                    findViewById(R.id.main_layout),
                    getString(R.string.network_error_message));
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent detailActivityIntent = new Intent(MainActivity.this, MovieDetailActivity.class);
        detailActivityIntent.putExtra(Constants.MOVIE_DETAIL_PARCEL_EXTRA, mMovies);
        detailActivityIntent.putExtra(Constants.MOVIE_POSITION_EXTRA, position);
        startActivity(detailActivityIntent);
    }

    private void loadMovieData(String sortBy) {
        if (NetworkUtils.isDeviceConnectedToNetwork(this)) {
            fetchMovieDetails(sortBy);
        } else {
            ErrorHelper.displayError(MainActivity.this,
                    findViewById(R.id.main_layout),
                    getString(R.string.network_error_message));
        }
    }

    private void showLoadingIndicator() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoadingIndicator() {
        progressBar.setVisibility(View.INVISIBLE);
    }

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
}
