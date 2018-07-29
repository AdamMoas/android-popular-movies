package com.portfolio.moas.adam.popularmovies.features.main.screen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.portfolio.moas.adam.popularmovies.R;
import com.portfolio.moas.adam.popularmovies.data.ApiUtils;
import com.portfolio.moas.adam.popularmovies.data.model.Movie;
import com.portfolio.moas.adam.popularmovies.data.remote.MovieDbService;
import com.portfolio.moas.adam.popularmovies.utils.ActivityUtils;
import com.portfolio.moas.adam.popularmovies.utils.NetworkUtils;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public static final String MAIN_SCREEN_ID = "MAIN_SCREEN_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Get the requested task id
        String mainScreenId = getIntent().getStringExtra(MAIN_SCREEN_ID);

        ArrayList<Movie> movies = new ArrayList<>();

        MovieDbService movieDbService = ApiUtils.getMovieDbService();
        boolean isDeviceConnected = NetworkUtils.isDeviceConnectedToNetwork(this);

        MainScreenFragment mainScreenFragment = (MainScreenFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (mainScreenFragment == null) {
            mainScreenFragment = MainScreenFragment.newInstance(mainScreenId);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    mainScreenFragment, R.id.contentFrame);
        }

        new MainScreenPresenter(
                mainScreenFragment,
                movieDbService,
                movies,
                isDeviceConnected);

    }
}
