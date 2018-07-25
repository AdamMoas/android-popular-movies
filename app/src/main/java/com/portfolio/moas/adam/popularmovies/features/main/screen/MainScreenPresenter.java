package com.portfolio.moas.adam.popularmovies.features.main.screen;

import android.support.annotation.NonNull;

import com.portfolio.moas.adam.popularmovies.data.remote.MovieDbService;

import static com.google.common.base.Preconditions.checkNotNull;


public class MainScreenPresenter implements MainScreenContract.Presenter {

    private MainScreenContract.View mainScreenView;
    private MovieDbService movieDbService;

    MainScreenPresenter(@NonNull MainScreenContract.View mainScreenView,
                        @NonNull MovieDbService movieDbService) {
        this.mainScreenView = checkNotNull(mainScreenView);
        this.movieDbService = checkNotNull(movieDbService);
    }

    @Override
    public void start() {
    }
}
