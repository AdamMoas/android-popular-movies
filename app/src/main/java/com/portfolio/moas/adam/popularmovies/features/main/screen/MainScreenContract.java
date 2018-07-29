package com.portfolio.moas.adam.popularmovies.features.main.screen;

import com.portfolio.moas.adam.popularmovies.BasePresenter;
import com.portfolio.moas.adam.popularmovies.BaseView;
import com.portfolio.moas.adam.popularmovies.data.model.Movie;

import java.util.ArrayList;

public interface MainScreenContract {

    interface View extends BaseView<Presenter> {
        void showLoadingIndicator();

        void hideLoadingIndicator();

        void displayError(int viewId, int messageResId);

        void setUpAdapter(int movieCount, ArrayList<Movie> movies);

    }

    interface Presenter extends BasePresenter {
        void fetchMovieDetails(String sortBy);

        void displayMovies();

        void loadMovieData(String sortBy);
    }

}
