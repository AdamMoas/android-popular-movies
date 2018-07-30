package com.portfolio.moas.adam.popularmovies.features.movie.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.portfolio.moas.adam.popularmovies.R;
import com.portfolio.moas.adam.popularmovies.data.ApiUtils;
import com.portfolio.moas.adam.popularmovies.data.model.Movie;
import com.portfolio.moas.adam.popularmovies.data.model.Review;
import com.portfolio.moas.adam.popularmovies.data.model.Trailer;
import com.portfolio.moas.adam.popularmovies.data.remote.MovieDbService;
import com.portfolio.moas.adam.popularmovies.utils.ActivityUtils;
import com.portfolio.moas.adam.popularmovies.utils.Constants;

import java.util.ArrayList;
import java.util.List;


public class MovieDetailActivity extends AppCompatActivity {

    public static final String MOVIE_DETAIL_ID = "MOVIE_DETAIL_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);


        String movieDetailId = getIntent().getStringExtra(MOVIE_DETAIL_ID);

        MovieDbService movieDbService = ApiUtils.getMovieDbService();

        ArrayList<Movie> movies = getIntent().getParcelableArrayListExtra(Constants.MOVIE_DETAIL_PARCEL_EXTRA);
        List<Trailer> trailers = new ArrayList<>();
        List<Review> reviews = new ArrayList<>();

        MovieDetailFragment movieDetailFragment = (MovieDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (movieDetailFragment == null) {
            movieDetailFragment = MovieDetailFragment.newInstance(movieDetailId);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    movieDetailFragment, R.id.contentFrame);
        }

        new MovieDetailPresenter(
                movieDetailFragment,
                movieDbService,
                movies,
                trailers,
                reviews);

    }
}
