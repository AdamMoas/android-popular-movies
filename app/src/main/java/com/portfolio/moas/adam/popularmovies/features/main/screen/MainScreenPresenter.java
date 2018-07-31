package com.portfolio.moas.adam.popularmovies.features.main.screen;

import android.support.annotation.NonNull;
import android.util.Log;

import com.portfolio.moas.adam.popularmovies.R;
import com.portfolio.moas.adam.popularmovies.data.model.Movie;
import com.portfolio.moas.adam.popularmovies.data.model.MovieResponse;
import com.portfolio.moas.adam.popularmovies.data.remote.MovieDbService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.portfolio.moas.adam.popularmovies.utils.Secrets.API_KEY;


public class MainScreenPresenter implements MainScreenContract.Presenter {

    private MainScreenContract.View mainScreenView;
    private MovieDbService movieDbService;
    private ArrayList<Movie> movieList;
    private int movieCount;
    private boolean isDeviceConnected;

    MainScreenPresenter(@NonNull MainScreenContract.View mainScreenView,
                        @NonNull MovieDbService movieDbService,
                        @NonNull ArrayList<Movie> movies,
                        boolean isDeviceConnected) {
        this.mainScreenView = checkNotNull(mainScreenView);
        this.movieDbService = checkNotNull(movieDbService);
        this.movieList = checkNotNull(movies);
        this.isDeviceConnected = isDeviceConnected;
        mainScreenView.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void fetchMovieDetails(String sortBy) {
        if (API_KEY.isEmpty()) {
            Log.d("API-KEY", "API Key missing");
            return;
        }

        mainScreenView.showLoadingIndicator();

        Call<MovieResponse> call = movieDbService.getAllMovies(sortBy, API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ArrayList<Movie> movies = response.body().getResults();
                    movieList = movies;
                    movieCount = movies.size();
                    for (int i = 0; i < movies.size(); i++) {
                        System.out.println("Title: " + movies.get(i).getOverview());
                    }
                    mainScreenView.hideLoadingIndicator();
                    displayMovies();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                mainScreenView.hideLoadingIndicator();
                mainScreenView.displayError(R.id.main_layout, R.string.api_response_failure_message);
                Log.d("Failure", t.toString());
            }
        });
    }

    @Override
    public void displayMovies() {
        if (movieList != null && movieCount != 0) {
            mainScreenView.setUpAdapter(movieCount, movieList);
        } else {
            mainScreenView.displayError(R.id.main_layout, R.string.network_error_message);
        }
    }

    @Override
    public void loadMovieData(String sortBy) {
        if (isDeviceConnected) {
            fetchMovieDetails(sortBy);
        } else {
            mainScreenView.displayError(R.id.main_layout, R.string.network_error_message);
        }
    }
}
